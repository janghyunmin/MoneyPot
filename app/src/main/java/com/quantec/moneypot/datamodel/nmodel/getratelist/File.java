package com.quantec.moneypot.datamodel.nmodel.getratelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {
    @SerializedName("part")
    @Expose
    private String part;
    @SerializedName("fileSize")
    @Expose
    private Integer fileSize;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("fileFullPath")
    @Expose
    private String fileFullPath;

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

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
