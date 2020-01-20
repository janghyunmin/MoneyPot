package com.quantec.moneypot.util.FomatToWon;

import java.text.DecimalFormat;

public class MoneyFormatToWon {

    public static String moneyFormatToWon(int originNumber) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        return String.valueOf(decimalFormat.format(originNumber));
    }
}
