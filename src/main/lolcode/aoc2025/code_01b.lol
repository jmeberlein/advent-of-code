HAI 1.4
    CAN HAS STDIO?
    CAN HAS STRING?

    HOW IZ I readin YR file
        I HAS A res ITZ ""
        I HAS A char
        IM IN YR loop UPPIN YR tmp TIL EITHER OF BOTH SAEM char AN ":)" AN BOTH SAEM char AN ""
            char R I IZ STDIO'Z LUK YR file AN YR 1 MKAY
            DIFFRINT char AN ":)", O RLY?
                YA RLY, res R SMOOSH res AN char MKAY
            OIC
        IM OUTTA YR loop
        FOUND YR res
    IF U SAY SO

    HOW IZ I gettin_suffix_of YR string AN YR start
        I HAS A res ITZ ""
        I HAS A idx ITZ start
        I HAS A len ITZ I IZ STRING'Z LEN YR string MKAY
        OBTW
            LOLCODE apparently automatically sets the loop variable to 0,
            so I have to use a temporary variable instead of looping over
            the index directly,
        TLDR
        IM IN YR loop UPPIN YR tmp TIL BOTH SAEM idx AN len
            res R SMOOSH res AN I IZ STRING'Z AT YR string AN YR idx MKAY MKAY
            idx R SUM OF idx AN 1
        IM OUTTA YR loop
        FOUND YR res
    IF U SAY SO

    I HAS A file_name
    GIMMEH file_name
    I HAS A file ITZ I IZ STDIO'Z OPEN YR file_name AN YR "r" MKAY
    
    I HAS A res ITZ 0
    I HAS A prev ITZ 50
    I HAS A line ITZ I IZ readin YR file MKAY
    IM IN YR loop UPPIN YR tmp TIL BOTH SAEM line AN ""
        I HAS A adj ITZ MAEK I IZ gettin_suffix_of YR line AN YR 1 MKAY A NUMBR
        res R SUM OF res AN QUOSHUNT OF adj AN 100
        adj R MOD OF adj AN 100

        I HAS A curr
        BOTH SAEM I IZ STRING'Z AT YR line AN YR 0 MKAY AN "R", O RLY?
            YA RLY, curr R SUM OF prev AN adj
            NO WAI, curr R DIFF OF prev AN adj
        OIC

        BOTH SAEM curr AN 0, O RLY?
            YA RLY, res R SUM OF res AN 1
            MEBBE BOTH SAEM 100 AN SMALLR OF curr AN 100
                res R SUM OF res AN 1
                curr R DIFF OF curr AN 100
            MEBBE DIFFRINT curr AN BIGGR OF curr AN 0
                DIFFRINT prev AN 0, O RLY?
                    YA RLY, res R SUM OF res AN 1
                OIC
                curr R SUM OF curr AN 100
        OIC

        prev R curr
        line R I IZ readin YR file MKAY
    IM OUTTA YR loop

    VISIBLE res
KTHXBYE