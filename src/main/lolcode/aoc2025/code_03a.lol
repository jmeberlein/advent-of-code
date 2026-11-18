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

I HAS A file_name
GIMMEH file_name
I HAS A file ITZ I IZ STDIO'Z OPEN YR file_name AN YR "r" MKAY
I HAS A lines ITZ I IZ readin YR file MKAY

I HAS A sum ITZ 0
I HAS A line
I HAS A idx
I HAS A idx_max
I HAS A len
I HAS A max
I HAS A curr
IM IN YR file_loop UPPIN YR line_num TIL BOTH SAEM line_num AN lines'Z length
    line R lines'Z SRS line_num
    idx R 1
    idx_max R 0
    len R I IZ STRING'Z LEN YR line MKAY
    max R MAEK I IZ STRING'Z AT YR line AN YR 0 MKAY A NUMBR
    IM IN YR first_digit UPPIN YR tmp TIL BOTH SAEM idx AN DIFF OF len AN 1
        curr R MAEK I IZ STRING'Z AT YR line AN YR idx MKAY A NUMBR
        DIFFRINT curr AN SMALLR OF curr AN max, O RLY?
            YA RLY
                max R curr
                idx_max R idx
        OIC
        idx R SUM OF idx AN 1
    IM OUTTA YR first_digit
    sum R SUM OF sum AN PRODUKT OF max AN 10

    max R MAEK I IZ STRING'Z AT YR line AN YR SUM OF idx_max AN 1 MKAY A NUMBR
    idx R SUM OF idx_max AN 2
    IM IN YR second_digit UPPIN YR tmp TIL BOTH SAEM idx AN len
        curr R MAEK I IZ STRING'Z AT YR line AN YR idx MKAY A NUMBR
        DIFFRINT curr AN SMALLR OF curr AN max, O RLY?
            YA RLY, max R curr
        OIC
        idx R SUM OF idx AN 1
    IM OUTTA YR second_digit
    sum R SUM OF sum AN max
IM OUTTA YR file_loop

VISIBLE sum

KTHXBYE