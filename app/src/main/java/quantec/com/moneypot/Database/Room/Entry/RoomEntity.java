package quantec.com.moneypot.Database.Room.Entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@Entity(tableName="recentlySearch_table")
public class RoomEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String searchTitle;

    @ColumnInfo(name = "room_code")
    public int searchCode;


//    @ColumnInfo(name = "hoho")
//    public int hoho;
//
//    @ColumnInfo(name = "hoho2")
//    public int hoho2;
//
//    @ColumnInfo(name = "test")
//    public int test;

    public RoomEntity(String searchTitle, int searchCode) {
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

    public int getSearchCode() {
        return searchCode;
    }


}
