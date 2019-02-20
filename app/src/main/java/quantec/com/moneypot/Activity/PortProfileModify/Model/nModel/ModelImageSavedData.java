package quantec.com.moneypot.Activity.PortProfileModify.Model.nModel;

import java.util.ArrayList;

public class ModelImageSavedData {

    int errorcode;
    int totalElements;
    Content content = new Content();
    Page page;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class Content {
        String code;
        String fileName;
        int fileSize;
        String oldFileName;
        int oldFileSize;
        String dailyFolderPath;
        boolean dailyFolder;
        String home;
        String fileFullPath;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getFileSize() {
            return fileSize;
        }

        public void setFileSize(int fileSize) {
            this.fileSize = fileSize;
        }

        public String getOldFileName() {
            return oldFileName;
        }

        public void setOldFileName(String oldFileName) {
            this.oldFileName = oldFileName;
        }

        public int getOldFileSize() {
            return oldFileSize;
        }

        public void setOldFileSize(int oldFileSize) {
            this.oldFileSize = oldFileSize;
        }

        public String getDailyFolderPath() {
            return dailyFolderPath;
        }

        public void setDailyFolderPath(String dailyFolderPath) {
            this.dailyFolderPath = dailyFolderPath;
        }

        public boolean isDailyFolder() {
            return dailyFolder;
        }

        public void setDailyFolder(boolean dailyFolder) {
            this.dailyFolder = dailyFolder;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getFileFullPath() {
            return fileFullPath;
        }

        public void setFileFullPath(String fileFullPath) {
            this.fileFullPath = fileFullPath;
        }
    }
    public class Page{}


//    String File;
//    String type;
//    String upload;
//
//    ArrayList<Product> product = new ArrayList<>();
//
//    public String getFile() {
//        return File;
//    }
//
//    public void setFile(String file) {
//        File = file;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getUpload() {
//        return upload;
//    }
//
//    public void setUpload(String upload) {
//        this.upload = upload;
//    }
//
//    public ArrayList<Product> getProduct() {
//        return product;
//    }
//
//    public void setProduct(ArrayList<Product> product) {
//        this.product = product;
//    }
//
//    public class Product {
//
//        String name;
//        String icon;
//        String descript;
//        int photo;
//
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getIcon() {
//            return icon;
//        }
//
//        public void setIcon(String icon) {
//            this.icon = icon;
//        }
//
//        public String getDescript() {
//            return descript;
//        }
//
//        public void setDescript(String descript) {
//            this.descript = descript;
//        }
//
//        public int getPhoto() {
//            return photo;
//        }
//
//        public void setPhoto(int photo) {
//            this.photo = photo;
//        }
//    }
}
