package com.quantec.moneypot.database.room.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="search_table")
public class RoomEntity2 {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String searchTitle;

    @ColumnInfo(name = "type")
    public int type;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "elStock")
    public String elStock;

    @ColumnInfo(name = "descript")
    public String descript;

    @ColumnInfo(name = "rate")
    public double rate;

    @ColumnInfo(name = "follow")
    public int follow;


    public RoomEntity2(String searchTitle, int type, String code, String name, String elStock, String descript, double rate, int follow) {
        this.searchTitle = searchTitle;
        this.type = type;
        this.code = code;
        this.name = name;
        this.elStock = elStock;
        this.descript = descript;
        this.rate = rate;
        this.follow = follow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getElStock() {
        return elStock;
    }

    public String getDescript() {
        return descript;
    }

    public double getRate() {
        return rate;
    }

    public int getFollow() {
        return follow;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElStock(String elStock) {
        this.elStock = elStock;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }
}