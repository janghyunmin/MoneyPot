package quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage4.Model.dModel;

public class ModelRankList {

    int rank;
    String name;
    double rate;
    int imageUrl;

    public ModelRankList(int rank, String name, double rate, int imageUrl) {
        this.rank = rank;
        this.name = name;
        this.rate = rate;
        this.imageUrl = imageUrl;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
