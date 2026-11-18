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
        IM IN YR inner UPPIN YR t2 TIL EITHER OF BOTH SAEM char AN ":)" AN BOTH SAEM char AN ""
            char R I IZ STDIO'Z LUK YR file AN YR 1 MKAY
            DIFFRINT char AN ":)", O RLY?
                YA RLY, line R SMOOSH line AN char MKAY
            OIC
        IM OUTTA YR inner
        DIFFRINT line AN "", O RLY?
            YA RLY, res IZ pushin YR line MKAY
        OIC
    IM OUTTA YR outer
    FOUND YR res
IF U SAY SO

HOW IZ I searchin YR string AN YR start AN YR end
    I HAS A len ITZ I IZ STRING'Z LEN YR string MKAY
    I HAS A idx_max ITZ start
    I HAS A idx ITZ SUM OF start AN 1
    I HAS A max ITZ MAEK I IZ STRING'Z AT YR string AN YR start MKAY A NUMBR
    I HAS A curr
    IM IN YR loop UPPIN YR tmp TIL BOTH SAEM idx AN end
        curr R MAEK I IZ STRING'Z AT YR line AN YR idx MKAY A NUMBR
        DIFFRINT curr AN SMALLR OF curr AN max, O RLY?
            YA RLY
                max R curr
                idx_max R idx
        OIC
        idx R SUM OF idx AN 1
    IM OUTTA YR loop
    FOUND YR idx_max
IF U SAY SO

I HAS A file_name
GIMMEH file_name
I HAS A file ITZ I IZ STDIO'Z OPEN YR file_name AN YR "r" MKAY
I HAS A lines ITZ I IZ readin YR file MKAY

I HAS A sum ITZ 0
I HAS A len
I HAS A curr
I HAS A start
I HAS A end
I HAS A line
IM IN YR file_loop UPPIN YR line_num TIL BOTH SAEM line_num AN lines'Z length
    line R lines'Z SRS line_num
    len R I IZ STRING'Z LEN YR line MKAY
    curr R 0
    start R 0
    IM IN YR inner_loop UPPIN YR idx TIL BOTH SAEM idx AN 12
        end R DIFF OF len AN DIFF OF 11 AN idx
        start R I IZ searchin YR line AN YR start AN YR end MKAY
        curr R SUM OF PRODUKT OF curr AN 10 AN I IZ STRING'Z AT YR line AN YR start MKAY
        start R SUM OF start AN 1
    IM OUTTA YR inner_loop
    sum R SUM OF sum AN curr
    VISIBLE line AN " :: " AN curr
IM OUTTA YR file_loop
VISIBLE sum

KTHXBYE