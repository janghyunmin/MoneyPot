package com.quantec.moneypot.database.room.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.quantec.moneypot.database.room.entry.RoomEntity;
import com.quantec.moneypot.database.room.entry.RoomEntity2;

@Dao
public interface RoomDao {

    @Insert
    void insert(RoomEntity roomEntity);

    @Update
    void update(RoomEntity roomEntity);

    @Delete
    void delete(RoomEntity roomEntity);

    @Query("DELETE FROM recentlySearch_table")
    void deleteAllSearchs();

    @Query("SELECT * FROM recentlySearch_table ORDER BY id DESC")
    LiveData<List<RoomEntity>> getAllSearchs();

    @Query("SELECT * FROM recentlySearch_table WHERE room_code LIKE :code")
    RoomEntity findCode(String code);

    @Query("SELECT * FROM recentlySearch_table")
    List<RoomEntity> findAll();



    @Insert
    void insert2(RoomEntity2 roomEntity2);

    @Query("DELETE FROM search_table")
    void delete2();

    @Query("SELECT * FROM search_table ORDER BY id DESC")
    LiveData<List<RoomEntity2>> getAllSearched();

//    @Query("SELECT * FROM search_table WHERE name LIKE :name OR elStock LIKE :elStock OR descript LIKE :descript")
//    RoomEntity2 findStock(String name, String elStock, String descript);

    @Query("SELECT * FROM search_table WHERE name LIKE :name OR elStock LIKE :elStock OR descript LIKE :descript")
    List<RoomEntity2> findStock(String name, String elStock, String descript);

    @Query("SELECT * FROM search_table")
    List<RoomEntity2> findall();
}


