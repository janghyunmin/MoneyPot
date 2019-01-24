package quantec.com.moneypot.Activity.PortProfileModify.Model.nModel;

import java.util.ArrayList;

public class ModelImageSavedData {

    String File;
    String type;
    String upload;

    ArrayList<Product> product = new ArrayList<>();

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public ArrayList<Product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<Product> product) {
        this.product = product;
    }

    public class Product {

        String name;
        String icon;
        String descript;
        int photo;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }

        public int getPhoto() {
            return photo;
        }

        public void setPhoto(int photo) {
            this.photo = photo;
        }
    }
}
