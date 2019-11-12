package com.quantec.moneypot.datamodel.nmodel.lifelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("fileSize")
    @Expose
    private Integer fileSize;
    @SerializedName("oldFileName")
    @Expose
    private String oldFileName;
    @SerializedName("oldFileSize")
    @Expose
    private Integer oldFileSize;
    @SerializedName("dailyFolderPath")
    @Expose
    private String dailyFolderPath;
    @SerializedName("dailyFolder")
    @Expose
    private Boolean dailyFolder;
    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("fileFullPath")
    @Expose
    private String fileFullPath;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public Integer getOldFileSize() {
        return oldFileSize;
    }

    public void setOldFileSize(Integer oldFileSize) {
        this.oldFileSize = oldFileSize;
    }

    public String getDailyFolderPath() {
        return dailyFolderPath;
    }

    public void setDailyFolderPath(String dailyFolderPath) {
        this.dailyFolderPath = dailyFolderPath;
    }

    public Boolean getDailyFolder() {
        return dailyFolder;
    }

    public void setDailyFolder(Boolean dailyFolder) {
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
