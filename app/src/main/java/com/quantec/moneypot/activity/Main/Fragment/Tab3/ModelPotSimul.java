package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPotSimul {

    int status;
    long timestamp;
    int totalElements;
    boolean noContent;
    Content content = new Content();
    Page page;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isNoContent() {
        return noContent;
    }

    public void setNoContent(boolean noContent) {
        this.noContent = noContent;
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

        @SerializedName("propensity")
        @Expose
        private Integer propensity;
        @SerializedName("codes")
        @Expose
        private String codes;
        @SerializedName("core")
        @Expose
        private Core core;
        @SerializedName("chart")
        @Expose
        private List<Chart> chart = null;

        public Integer getPropensity() {
            return propensity;
        }

        public void setPropensity(Integer propensity) {
            this.propensity = propensity;
        }

        public String getCodes() {
            return codes;
        }

        public void setCodes(String codes) {
            this.codes = codes;
        }

        public Core getCore() {
            return core;
        }

        public void setCore(Core core) {
            this.core = core;
        }

        public List<Chart> getChart() {
            return chart;
        }

        public void setChart(List<Chart> chart) {
            this.chart = chart;
        }
    }

    public class Page {

    }
}
