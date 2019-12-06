package com.quantec.moneypot.database.room.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.quantec.moneypot.database.room.entry.RoomEntity2;
import com.quantec.moneypot.database.room.repository.SearchRepository2;

import java.util.List;

public class SearchViewModel2 extends AndroidViewModel {
    private SearchRepository2 searchRepository;
    private LiveData<List<RoomEntity2>> mAllSearch;

    public SearchViewModel2(@NonNull Application application) {
        super(application);
        searchRepository = new SearchRepository2(application);
        mAllSearch = searchRepository.getAllSearchs();
    }
    public List<RoomEntity2> getFindAll(){
        return searchRepository.findAll();
    }

//    public RoomEntity2 getfindSearch(String name, String elStock, String descript){
//        return searchRepository.findStock(name, elStock, descript);
//    }
    public List<RoomEntity2> getfindSearch(String name, String elStock, String descript){
        return searchRepository.findStock(name, elStock, descript);
    }

    public LiveData<List<RoomEntity2>> getmAllSearch() {
        return mAllSearch;
    }

    public void insert2(RoomEntity2 roomEntity) {
        searchRepository.insert2(roomEntity);
    }

    public void delete2(RoomEntity2 roomEntity){
        searchRepository.delete2(roomEntity);
    }

    public void deleteAll() {
        searchRepository.deleteAllSearchs();
    }

}
