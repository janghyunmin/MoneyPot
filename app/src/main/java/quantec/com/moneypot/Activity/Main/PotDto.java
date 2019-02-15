package quantec.com.moneypot.Activity.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PotDto {

    String name;
    String descript;
    ArrayList<PotEls> potEls;
    int investType;
    Long minPrice;
    ArrayList<String> stCodes;

    public PotDto(String name, String descript, ArrayList<PotEls> potEls, int investType, Long minPrice, ArrayList<String> stCodes) {
        this.name = name;
        this.descript = descript;
        this.potEls = potEls;
        this.investType = investType;
        this.minPrice = minPrice;
        this.stCodes = stCodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public ArrayList<PotEls> getPotEls() {
        return potEls;
    }

    public void setPotEls(ArrayList<PotEls> potEls) {
        this.potEls = potEls;
    }

    public int getInvestType() {
        return investType;
    }

    public void setInvestType(int investType) {
        this.investType = investType;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public ArrayList<String> getStCodes() {
        return stCodes;
    }

    public void setStCodes(ArrayList<String> stCodes) {
        this.stCodes = stCodes;
    }

    @Override
    public String toString() {
        return "{" +
                "name=" + name +
                ", descript=" + descript +
                ", potEls=" + potEls +
                ", investType=" + investType +
                ", minPrice=" + minPrice +
                ", stCodes=" + stCodes +
                "}";
    }
}
