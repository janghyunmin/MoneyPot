package quantec.com.moneypot.ModelCommon.dModel;

public class TransChartList {

    int xPlot;
    float rate;
    String date;

    public TransChartList(int xPlot, float rate, String date) {
        this.xPlot = xPlot;
        this.rate = rate;
        this.date = date;
    }

    public int getxPlot() {
        return xPlot;
    }

    public void setxPlot(int xPlot) {
        this.xPlot = xPlot;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}