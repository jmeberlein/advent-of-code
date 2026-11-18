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
    I HAS A ptr ITZ 50
    I HAS A line ITZ I IZ readin YR file MKAY
    IM IN YR loop UPPIN YR tmp TIL BOTH SAEM line AN ""
        I HAS A adj ITZ MAEK I IZ gettin_suffix_of YR line AN YR 1 MKAY A NUMBR

        BOTH SAEM I IZ STRING'Z AT YR line AN YR 0 MKAY AN "R", O RLY?
            YA RLY, ptr R SUM OF ptr AN adj
            NO WAI, ptr R DIFF OF SUM OF ptr AN 100 AN adj
        OIC

        ptr R MOD OF ptr AN 100

        BOTH SAEM ptr AN 0, O RLY?
            YA RLY, res R SUM OF res AN 1
        OIC

        line R I IZ readin YR file MKAY
    IM OUTTA YR loop

    VISIBLE res
KTHXBYE