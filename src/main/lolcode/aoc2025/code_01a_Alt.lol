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

IM IN YR loop UPPIN YR idx TIL BOTH SAEM idx AN lines'Z length
    VISIBLE lines'Z SRS idx
IM OUTTA YR loop

KTHXBYE