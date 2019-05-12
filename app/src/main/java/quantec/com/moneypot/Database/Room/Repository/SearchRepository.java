package quantec.com.moneypot.Database.Room.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.Local.RoomDao;
import quantec.com.moneypot.Database.Room.Local.SearchRoomDatabase;

public class SearchRepository {

    //DAO의 멤버변수와 search를 넣을 리스트 변수를 만들어줌
    private RoomDao roomDao;
    private LiveData<List<RoomEntity>> mAllSearch;

    public SearchRepository(Application application){
        SearchRoomDatabase db = SearchRoomDatabase.getINSTANCE(application);
        //RoomDatabase에 있는 Dao를 가져온다.
        roomDao = db.roomDao();
        //Dao의 쿼리를 이용하여 저장되어있는 모든 search를 가져온다.
        mAllSearch = roomDao.getAllSearchs();
    }

    //
    public List<RoomEntity> findAll(){
        return roomDao.findAll();
    }
    //

    ///
    public RoomEntity findCdoe(String code){
        return roomDao.findCode(code);
    }
    ///

    public LiveData<List<RoomEntity>> getAllSearchs(){
        return mAllSearch;
    }

    //search를 추가하는 함수
    public void insert(RoomEntity roomEntity){
        new insertAsyncTask(roomDao).execute(roomEntity);
    }

    //search를 업데이트 하는 함수
    public void update(RoomEntity roomEntity){
        new UpdateAsyncTask(roomDao).execute(roomEntity);
    }

    //search를 삭제하는 함수
    public void delete(RoomEntity roomEntity) {
        new DeleteAsyncTask(roomDao).execute(roomEntity);
    }

    //search를 전부 삭제하는 함수
    public void deleteAllSearchs(){
        new DeleteAllAsyncTask(roomDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<RoomEntity, Void, Void> {
        private RoomDao mAsyncTaskDao;
        public insertAsyncTask(RoomDao roomDao) {
            mAsyncTaskDao = roomDao;
        }
        @Override
        protected Void doInBackground(RoomEntity... roomEntities) {
            mAsyncTaskDao.insert(roomEntities[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<RoomEntity, Void, Void> {
        private RoomDao mAsyncTaskDao;
        public UpdateAsyncTask(RoomDao roomDao) {
            mAsyncTaskDao = roomDao;
        }
        @Override
        protected Void doInBackground(RoomEntity... roomEntities) {
            mAsyncTaskDao.update(roomEntities[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<RoomEntity, Void, Void> {
        private RoomDao mAsyncTaskDao;
        public DeleteAsyncTask(RoomDao roomDao) {
            mAsyncTaskDao = roomDao;
        }
        @Override
        protected Void doInBackground(RoomEntity... roomEntities) {
            mAsyncTaskDao.delete(roomEntities[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private RoomDao mAsyncTaskDao;
        public DeleteAllAsyncTask(RoomDao roomDao) {
            mAsyncTaskDao = roomDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllSearchs();
            return null;
        }
    }

}