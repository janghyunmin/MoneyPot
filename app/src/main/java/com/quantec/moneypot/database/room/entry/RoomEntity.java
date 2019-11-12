package com.quantec.moneypot.database.room.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="recentlySearch_table")
public class RoomEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String searchTitle;

    @ColumnInfo(name = "room_code")
    public String searchCode;

//    @ColumnInfo(name = "hoho")
//    public int hoho;
//    @ColumnInfo(name = "hoho2")
//    public int hoho2;
//    @ColumnInfo(name = "test")
//    public int test;

    public RoomEntity(String searchTitle, String searchCode) {
        this.searchTitle = searchTitle;
        this.searchCode = searchCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSearchTItle() {
        return searchTitle;
    }

    public String getSearchCode() {
        return searchCode;
    }


}
