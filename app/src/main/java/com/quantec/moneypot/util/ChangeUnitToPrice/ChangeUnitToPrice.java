package com.quantec.moneypot.util.ChangeUnitToPrice;

import com.quantec.moneypot.util.removezero.RemoveZero;

public class ChangeUnitToPrice {

    public static String changeUnitToPrice(String finalPrice) {

        if(finalPrice.length() <= 4){
            return (finalPrice+"만원");
        }else{

            String uc, man;
            if(finalPrice.length() == 5){
                uc = finalPrice.substring(0, 1);
                man = finalPrice.substring(1);
            }else {
                uc = finalPrice.substring(0, 2);
                man = finalPrice.substring(2);
            }

            if(man.startsWith("0")){
                man = RemoveZero.removeZero(man);
            }

            if(man.length() != 0){
                return (uc+"억 "+man+"만원");
            }else{
                return (uc+"억");
            }

        }
    }
}
