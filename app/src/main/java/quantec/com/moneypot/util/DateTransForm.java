package quantec.com.moneypot.util;

public class DateTransForm {
    public static String dateTransForm(String date){

        String[] dateForm = date.split("-");
        dateForm[0] = dateForm[0].substring(2, 4);

        return dateForm[0]+"."+dateForm[1]+"."+dateForm[2];
    }
}
