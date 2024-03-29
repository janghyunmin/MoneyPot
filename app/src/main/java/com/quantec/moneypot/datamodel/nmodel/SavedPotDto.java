package com.quantec.moneypot.datamodel.nmodel;

public class SavedPotDto {

    String code;
    String name;
    String descript;

    public SavedPotDto(String code, String name, String descript) {
        this.code = code;
        this.name = name;
        this.descript = descript;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", name=" + name +
                ", descript=" + descript +
                "}";
    }
}
