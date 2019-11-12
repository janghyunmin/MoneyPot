package com.quantec.moneypot.database.room.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.quantec.moneypot.database.room.entry.RoomEntity;

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

}

