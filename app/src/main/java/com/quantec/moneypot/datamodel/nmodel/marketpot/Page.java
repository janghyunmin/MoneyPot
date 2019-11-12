package com.quantec.moneypot.datamodel.nmodel.marketpot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("firstPage")
    @Expose
    private Boolean firstPage;
    @SerializedName("lastPage")
    @Expose
    private Boolean lastPage;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("sort")
    @Expose
    private Sort sort;
    @SerializedName("totalElements")
    @Expose
    private Integer totalElements;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }
}
