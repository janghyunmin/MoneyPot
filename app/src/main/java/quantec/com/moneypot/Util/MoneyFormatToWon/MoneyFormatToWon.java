package quantec.com.moneypot.Util.MoneyFormatToWon;

import java.text.DecimalFormat;

public class MoneyFormatToWon {

    public static String moneyFormatToWon(String originNumber) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        return String.valueOf(decimalFormat.format(Integer.parseInt(originNumber)));
    }
}
