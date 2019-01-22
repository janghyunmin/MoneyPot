package quantec.com.moneypot.Database.Room.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import quantec.com.moneypot.Database.Room.Entry.RoomEntity;

@Database(entities = {RoomEntity.class}, version = 1, exportSchema = false)
public abstract class SearchRoomDatabase extends RoomDatabase {

    //데이터베이스와 연결되는 DAO
    //DAO는 abstract로 "getter"을 제공
    public abstract RoomDao roomDao();
    //singleton pattern, room database 는 한개 만 존재
    private static SearchRoomDatabase INSTANCE;

    public static SearchRoomDatabase getINSTANCE(final Context context) {
        if(INSTANCE == null){
            //동시에 2개의 INSTANCE가 생성되는 것을 막기위한 synchronized
            synchronized (SearchRoomDatabase.class) {
                if(INSTANCE == null) {
                    //데이터 베이스 생성부분
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SearchRoomDatabase.class, "recentlySearch_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
