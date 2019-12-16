package com.quantec.moneypot.database.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.quantec.moneypot.database.room.entry.RoomEntity2;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;

import java.util.List;

public class SearchRepository2 {

    //DAO의 멤버변수와 search를 넣을 리스트 변수를 만들어줌
    private RoomDao roomDao;
    private LiveData<List<RoomEntity2>> mAllSearch;

    public SearchRepository2(Application application){
        SearchRoomDatabase db = SearchRoomDatabase.getINSTANCE(application);
        //RoomDatabase에 있는 Dao를 가져온다.
        roomDao = db.roomDao();
        //Dao의 쿼리를 이용하여 저장되어있는 모든 search를 가져온다.
        mAllSearch = roomDao.getAllSearched();
    }


    ///
    //
    public List<RoomEntity2> findAll(){
        return roomDao.findall();
    }
    //

    ///
    public List<RoomEntity2> findStock(String name, String elStock, String descript){
        return roomDao.findStock(name, elStock, descript);
    }
//    public RoomEntity2 findStock(String name, String elStock, String descript){
//        return roomDao.findStock(name, elStock, descript);
//    }
    ///

    public LiveData<List<RoomEntity2>> getAllSearchs(){
        return mAllSearch;
    }

    //search를 추가하는 함수
    public void insert2(RoomEntity2 roomEntity){
        new insertAsyncTask(roomDao).execute(roomEntity);
    }

//    //search를 업데이트 하는 함수
//    public void update2(RoomEntity2 roomEntity2){
//        new UpdateAsyncTask(roomDao).execute(roomEntity2);
//    }

    //search를 업데이트 하는 함수
    public void updateData(int follow, String code){
        roomDao.updateData(follow, code);
    }

    //search를 삭제하는 함수
    public void delete2(RoomEntity2 roomEntity) {
        new DeleteAsyncTask(roomDao).execute(roomEntity);
    }

    //search를 전부 삭제하는 함수
    public void deleteAllSearchs(){
        new DeleteAllAsyncTask(roomDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<RoomEntity2, Void, Void> {
        private RoomDao mAsyncTaskDao;
        public insertAsyncTask(RoomDao roomDao) {
            mAsyncTaskDao = roomDao;
        }
        @Override
        protected Void doInBackground(RoomEntity2... roomEntities) {
            mAsyncTaskDao.insert2(roomEntities[0]);
            return null;
        }
    }
//    private static class UpdateAsyncTask extends AsyncTask<RoomEntity2, Void, Void> {
//        private RoomDao mAsyncTaskDao;
//        public UpdateAsyncTask(RoomDao roomDao) {
//            mAsyncTaskDao = roomDao;
//        }
//        @Override
//        protected Void doInBackground(RoomEntity2... roomEntity2s) {
////            mAsyncTaskDao.update2(roomEntity2s[0]);
//            mAsyncTaskDao.update2(0,"");
//            return null;
//        }
//    }
    private static class DeleteAsyncTask extends AsyncTask<RoomEntity2, Void, Void> {
        private RoomDao mAsyncTaskDao;
        public DeleteAsyncTask(RoomDao roomDao) {
            mAsyncTaskDao = roomDao;
        }
        @Override
        protected Void doInBackground(RoomEntity2... roomEntities) {
            mAsyncTaskDao.delete2();
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