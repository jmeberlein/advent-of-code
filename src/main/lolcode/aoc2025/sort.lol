HAI 1.4
O HAI IM array
    I HAS A length ITZ 0

    HOW IZ I pushin YR item
        ME HAS A SRS ME'Z length ITZ item
        ME'Z length R SUM OF ME'Z length AN 1
    IF U SAY SO

    HOW IZ I sortin
        IM IN YR loop_i UPPIN YR i TIL BOTH SAEM i AN ME'Z length
            I HAS A j ITZ i
            I HAS A x ITZ ME'Z SRS i
            IM IN YR loop_j UPPIN YR tmp WILE BOTH OF DIFFRINT 0 AN BIGGR OF 0 AN j AN ...
                    DIFFRINT x AN BIGGR OF x AN ME'Z SRS DIFF OF j AN 1
                ME'Z SRS j R ME'Z SRS DIFF OF j AN 1
                j R DIFF OF j AN 1
            IM OUTTA YR loop_j
            ME'Z SRS j R x
        IM OUTTA YR loop_i
    IF U SAY SO

    HOW IZ I printin
        VISIBLE "{"!
        IM IN YR loop UPPIN YR i TIL BOTH SAEM i AN ME'Z length
            BOTH SAEM i AN 0, O RLY?
                YA RLY, VISIBLE ME'Z SRS i!
                NO WAI, VISIBLE ", " AN ME'Z SRS i!
            OIC
        IM OUTTA YR loop
        VISIBLE "}"
    IF U SAY SO
KTHX

I HAS A arr ITZ LIEK A array
arr IZ pushin YR 0 MKAY
arr IZ pushin YR 2 MKAY
arr IZ pushin YR 4 MKAY
arr IZ pushin YR 3 MKAY
arr IZ pushin YR 7 MKAY
arr IZ pushin YR 1 MKAY
arr IZ pushin YR 6 MKAY
arr IZ pushin YR 9 MKAY
arr IZ pushin YR 8 MKAY
arr IZ pushin YR 5 MKAY

arr IZ printin MKAY
arr IZ sortin MKAY
arr IZ printin MKAY

KTHXBYE