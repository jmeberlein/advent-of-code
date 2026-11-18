HAI 1.4
CAN HAS STDIO?
CAN HAS STRING?

O HAI IM array
    I HAS A length ITZ 0

    HOW IZ I pushin YR item
        ME HAS A SRS ME'Z length ITZ item
        ME'Z length R SUM OF ME'Z length AN 1
    IF U SAY SO
KTHX

HOW IZ I readin YR file
    I HAS A res ITZ LIEK A array
    I HAS A line
    IM IN YR outer UPPIN YR t1 TIL BOTH SAEM line AN ""
        line R ""
        I HAS A char
        IM IN YR inner UPPIN YR t2 TIL ANY OF BOTH SAEM char AN "," AN BOTH SAEM char AN "-" AN BOTH SAEM char AN "" MKAY
            char R I IZ STDIO'Z LUK YR file AN YR 1 MKAY
            BOTH OF DIFFRINT char AN "," AN DIFFRINT char AN "-", O RLY?
                YA RLY, line R SMOOSH line AN char MKAY
            OIC
        IM OUTTA YR inner
        DIFFRINT line AN "", O RLY?
            YA RLY, res IZ pushin YR line MKAY
        OIC
    IM OUTTA YR outer
    FOUND YR res
IF U SAY SO

HOW IZ I checkin YR number
    I HAS A len ITZ I IZ STRING'Z LEN YR number MKAY
    DIFFRINT MOD OF len AN 2 AN 0, O RLY?
        YA RLY, FOUND YR FAIL
    OIC
    len R QUOSHUNT OF len AN 2
    IM IN YR loop UPPIN YR idx TIL BOTH SAEM idx AN len
        DIFFRINT I IZ STRING'Z AT YR number AN YR idx MKAY AN I IZ STRING'Z AT YR number AN YR SUM OF len AN idx MKAY, O RLY?
            YA RLY, FOUND YR FAIL
        OIC
    IM OUTTA YR loop
    FOUND YR WIN
IF U SAY SO

HOW IZ I checkin_range YR start AN YR end
    I HAS A start_idx ITZ MAEK start A NUMBR
    I HAS A len ITZ SUM OF 1 AN DIFF OF MAEK end A NUMBR AN MAEK start A NUMBR
    I HAS A sum ITZ 0
    IM IN YR loop UPPIN YR offset TIL BOTH SAEM offset AN len
        I HAS A test ITZ SUM OF start_idx AN offset
        I IZ checkin YR MAEK test A YARN MKAY, O RLY?
            YA RLY, sum R SUM OF sum AN test
        OIC
    IM OUTTA YR loop
    FOUND YR sum
IF U SAY SO

I HAS A file_name
GIMMEH file_name
I HAS A file ITZ I IZ STDIO'Z OPEN YR file_name AN YR "r" MKAY
I HAS A lines ITZ I IZ readin YR file MKAY

I HAS A sum ITZ 0
IM IN YR loop UPPIN YR idx TIL BOTH SAEM idx AN QUOSHUNT OF lines'Z length AN 2
    sum R SUM OF sum AN I IZ checkin_range YR lines'Z SRS PRODUKT OF idx AN 2 AN YR lines'Z SRS SUM OF 1 AN PRODUKT OF idx AN 2 MKAY
IM OUTTA YR loop
VISIBLE sum

KTHXBYE