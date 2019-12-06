package com.quantec.moneypot.datamodel.nmodel.seardb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {
    @SerializedName("fileSize")
    @Expose
    private Integer fileSize;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("fileFullPath")
    @Expose
    private String fileFullPath;

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
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
