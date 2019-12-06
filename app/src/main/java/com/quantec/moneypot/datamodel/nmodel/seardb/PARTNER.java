package com.quantec.moneypot.datamodel.nmodel.seardb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PARTNER {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("grade")
    @Expose
    private Integer grade;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
