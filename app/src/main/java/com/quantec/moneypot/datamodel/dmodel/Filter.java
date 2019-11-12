package com.quantec.moneypot.datamodel.dmodel;

public class Filter {
    String name;
    String descript;
    String elName;
    String nameSort;
    int elNameSort;

    public Filter() {
    }

    public Filter(String name, String descript, String elName) {
        this.name = name;
        this.descript = descript;
        this.elName = elName;
    }

    public Filter(String name, String nameSort){
        this.name = name;
        this.nameSort = nameSort;
    }

    public Filter(String elName, int elNameSort){
        this.elName = elName;
        this.elNameSort = elNameSort;
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

    public String getElName() {
        return elName;
    }

    public void setElName(String elName) {
        this.elName = elName;
    }

    public String getNameSort() {
        return nameSort;
    }

    public void setNameSort(String nameSort) {
        this.nameSort = nameSort;
    }

    public int getElNameSort() {
        return elNameSort;
    }

    public void setElNameSort(int elNameSort) {
        this.elNameSort = elNameSort;
    }
}
