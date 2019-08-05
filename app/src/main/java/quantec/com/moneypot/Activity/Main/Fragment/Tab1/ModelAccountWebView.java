package quantec.com.moneypot.Activity.Main.Fragment.Tab1;

public class ModelAccountWebView {

    boolean empty;
    String url;

    public ModelAccountWebView(boolean empty, String url) {
        this.empty = empty;
        this.url = url;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
