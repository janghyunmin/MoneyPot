package quantec.com.moneypot.DataModel.nModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import quantec.com.moneypot.DataModel.nModel.zimpotlist.Content;

public class ModelZimPotList {

    @SerializedName("content")
    @Expose
    private List<Content> content = null;
    @SerializedName("noContent")
    @Expose
    private Boolean noContent;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Boolean getNoContent() {
        return noContent;
    }

    public void setNoContent(Boolean noContent) {
        this.noContent = noContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }
}
