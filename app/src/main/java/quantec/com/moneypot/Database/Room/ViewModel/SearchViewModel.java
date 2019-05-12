package quantec.com.moneypot.Database.Room.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import quantec.com.moneypot.Database.Room.Entry.RoomEntity;
import quantec.com.moneypot.Database.Room.Repository.SearchRepository;

public class SearchViewModel extends AndroidViewModel {
    private SearchRepository searchRepository;
    private LiveData<List<RoomEntity>> mAllSearch;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        searchRepository = new SearchRepository(application);
        mAllSearch = searchRepository.getAllSearchs();
    }

    public List<RoomEntity> getFindAll(){
        return searchRepository.findAll();
    }

    public RoomEntity getFindCode(String code){
        return searchRepository.findCdoe(code);
    }

    public LiveData<List<RoomEntity>> getmAllSearch() {
        return mAllSearch;
    }

    public void insert(RoomEntity roomEntity) {
        searchRepository.insert(roomEntity);
    }

    public void update(RoomEntity roomEntity){
        searchRepository.update(roomEntity);
    }

    public void delete(RoomEntity roomEntity){
        searchRepository.delete(roomEntity);
    }

    public void deleteAll() {
        searchRepository.deleteAllSearchs();
    }

}
