package quantec.com.moneypot.Database.Room.Local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import quantec.com.moneypot.Database.Room.Entry.RoomEntity;


/**
 *
 * 마이그레이션 사용 방법
 * 1. RommEntity에서 추가할 칼럼을 정의해준다
 * 2-1. 기존 테이블에서 칼럼을 추가할 경우 아래 주석처리된 Add column 과 같이 추가시킬 칼럼을 설정해준다
 * 2-2. 칼럼 추가 후에 마이그레이션 값은 다음과 같이 new Migration(1, 2) 가 되며 이후 부터 2, 3 -> 3,4 -> 5,6 순서로 진행하게된다
 * 2-3. 마이그레이션을 설정해 준뒤에 아래의 데이터베이스 생성쪽에   .addMigrations(MIGRATION_2_3) 이부분을 추가해준다
 * 2-4. 그리고 반드시 version 값도 증가시켜준다 1->2 , 2->3 ...
 *
 * 추가사항 -> 테스트 해보지는 않았지만 칼럼을 추가하는 경우가 아니라 칼럼을 삭제하거나 혹은 다 지우는 경우에는 마이그레이션 부분에서 ADD COLUMN 만 해주는것이 아니라 DELETE 등으로 기존의 칼럼을 지워줘야 할것이다
 * 이렇게 해주지 않고서 ADD동작이 아닌 다른 동작을 해주었을때 다음과 같은 오류가 떴다
 *
 */
//    //Add column
//    static final Migration MIGRATION_2_3 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("ALTER TABLE recentlySearch_table "
//                    + " ADD COLUMN test INTEGER NOT NULL default 0");
//        }
//    };

@Database(entities = {RoomEntity.class}, version = 1, exportSchema = true)
public abstract class SearchRoomDatabase extends RoomDatabase {

//    //Add column
//    static final Migration MIGRATION_2_3 = new Migration(3, 5) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("ALTER TABLE recentlySearch_table "
//                    + " ADD COLUMN test INTEGER NOT NULL default 0");
//        }
//    };

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
//                            .addMigrations(MIGRATION_2_3)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}