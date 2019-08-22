package quantec.com.moneypot.Activity.propensity;

public class ModelPropensity {

    int type;
    String text;

    public ModelPropensity(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
