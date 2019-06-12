package quantec.com.moneypot.DataModel.nModel;

import java.util.ArrayList;

public class PotDto {

    ArrayList<String> codes;
    int investType;
    Long minPrice;

    public PotDto(ArrayList<String> codes, int investType, Long minPrice) {
        this.codes = codes;
        this.investType = investType;
        this.minPrice = minPrice;
    }

    public ArrayList<String> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<String> codes) {
        this.codes = codes;
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

    @Override
    public String toString() {
        return "{" +
                "codes=" + codes +
                ", investType=" + investType +
                ", minPrice=" + minPrice +
                "}";
    }
}
