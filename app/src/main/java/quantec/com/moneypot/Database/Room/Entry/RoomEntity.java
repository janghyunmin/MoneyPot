package quantec.com.moneypot.Database.Room.Entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="recentlySearch_table")
public class RoomEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String searchTitle;

    @ColumnInfo(name = "room_code")
    public int searchCode;

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
