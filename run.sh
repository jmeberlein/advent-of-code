#!/usr/bin/env bash
set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

if [[ $# -lt 3 || $# -gt 4 ]]; then
    echo "Usage: $0 <language> <year> <day> [input_file_or_suffix]" >&2
    echo "Examples:" >&2
    echo "  $0 perl 2025 9                # Uses 09.txt" >&2
    echo "  $0 perl 2025 9 09_test4.txt   # Uses 09_test4.txt" >&2
    echo "  $0 perl 2025 9 test4          # Also resolves to 09_test4.txt" >&2
    exit 1
fi

LANG="${1,,}"
YEAR="$2"
DAY=$(printf "%02d" "$((10#$3))")
INPUT_ARG="${4:-}"

INPUT_DIR="$REPO_ROOT/src/main/resources/input/aoc${YEAR}"

if [[ -z "$INPUT_ARG" ]]; then
    # Default to the main input file when omitted
    INPUT_PATH="$INPUT_DIR/${DAY}.txt"
elif [[ -f "$INPUT_ARG" ]]; then
    # Exact path (relative or absolute) from current working directory
    INPUT_PATH="$(realpath "$INPUT_ARG")"
elif [[ -f "$INPUT_DIR/$INPUT_ARG" ]]; then
    # Exact filename inside the year's input directory (e.g., "09_test4.txt")
    INPUT_PATH="$INPUT_DIR/$INPUT_ARG"
elif [[ -f "$INPUT_DIR/${DAY}_${INPUT_ARG#\_}.txt" ]]; then
    # Suffix shorthand (e.g., "test4" or "_test4" -> "09_test4.txt")
    INPUT_PATH="$INPUT_DIR/${DAY}_${INPUT_ARG#\_}.txt"
else
    echo "Error: Could not resolve input file for '$INPUT_ARG' in '$INPUT_DIR'" >&2
    exit 1
fi

case "$LANG" in
    kotlin|kt)
        cd "$REPO_ROOT"
        MVN_CMD="mvn"
        [[ -x "./mvnw" ]] && MVN_CMD="./mvnw"

        # Use an older JDK if system default is Java 25 and pom.xml is on Kotlin 1.9.20:
        # export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"

        # 1. Compile Kotlin & JMH sources quietly
        "$MVN_CMD" -q compile

        # 2. Build the runtime classpath (cached in target/classpath.txt when pom.xml hasn't changed)
        CP_FILE="$REPO_ROOT/target/classpath.txt"
        if [[ ! -f "$CP_FILE" || "$REPO_ROOT/pom.xml" -nt "$CP_FILE" ]]; then
            "$MVN_CMD" -q dependency:build-classpath -Dmdep.outputFile="$CP_FILE"
        fi

        # 3. Run MainKt directly without triggering JMH or exec-maven-plugin
        java -cp "$REPO_ROOT/target/classes:$(cat "$CP_FILE")" \
            com.adventofcode.MainKt "$YEAR" "$DAY" "$INPUT_PATH"
        ;;

    perl|pl)
        SCRIPT="$REPO_ROOT/src/main/perl/aoc${YEAR}/Day${DAY}.pl"
        if [[ ! -f "$SCRIPT" ]]; then
            echo "Error: Perl script not found at '$SCRIPT'" >&2
            exit 1
        fi
        cat "$INPUT_PATH" | perl "$SCRIPT"
        ;;

    ruby|rb)
        SCRIPT="$REPO_ROOT/src/main/ruby/aoc${YEAR}/day_${DAY}.rb"
        if [[ ! -f "$SCRIPT" ]]; then
            SCRIPT="$REPO_ROOT/src/main/ruby/aoc${YEAR}/Day${DAY}.rb"
        fi
        if [[ ! -f "$SCRIPT" ]]; then
            echo "Error: Ruby script not found at '$SCRIPT'" >&2
            exit 1
        fi
        ruby "$SCRIPT" "$INPUT_PATH"
        ;;

    *)
        echo "Error: Unsupported language '$LANG'. Expected: kotlin, perl, or ruby." >&2
        exit 1
        ;;
esac