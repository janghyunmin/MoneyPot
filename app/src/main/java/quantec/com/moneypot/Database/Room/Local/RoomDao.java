package quantec.com.moneypot.Database.Room.Local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import quantec.com.moneypot.Database.Room.Entry.RoomEntity;

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

