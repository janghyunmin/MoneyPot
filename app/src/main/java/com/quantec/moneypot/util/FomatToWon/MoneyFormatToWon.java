package com.quantec.moneypot.util.FomatToWon;

import java.text.DecimalFormat;

public class MoneyFormatToWon {

    public static String moneyFormatToWon(String originNumber) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        return String.valueOf(decimalFormat.format(Long.valueOf(originNumber)));
    }
}
