package quantec.com.moneypot.DataModel.dModel;

import android.graphics.drawable.Drawable;

public class ModelMini {

    String title;
    Drawable image;

    public ModelMini(String title, Drawable image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
