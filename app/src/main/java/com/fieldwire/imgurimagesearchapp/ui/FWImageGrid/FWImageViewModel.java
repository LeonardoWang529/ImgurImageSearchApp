package com.fieldwire.imgurimagesearchapp.ui.FWImageGrid;

import com.fieldwire.imgurimagesearchapp.data.Result;
import com.fieldwire.imgurimagesearchapp.data.imgurImageModule.FWImgurImageRepository;
import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FWImageViewModel extends ViewModel {
    private FWImgurImageRepository fwImgurImageRepository;
    MutableLiveData<SearchResult> _fwGallerySearchResult = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();

    FWImageViewModel(FWImgurImageRepository fwImgurImageRepository){this.fwImgurImageRepository = fwImgurImageRepository;}

    public LiveData<SearchResult> getSearchResult() {return _fwGallerySearchResult; }

    public void setFWImageItemLiveData(String sort, String window, String page, String keyword){
        disposables.add(Observable.fromCallable(fwImgurImageRepository.getSearchImages("","",page,keyword))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ( result -> {
                    if(result instanceof Result.Success) {
                        List<FWImageItem> data = ((Result.Success<List<FWImageItem>>) result).getData();
                        _fwGallerySearchResult.setValue(new SearchResult(data));
                    }else{
                        Exception data = ((Result.Error) result).getException();
                        _fwGallerySearchResult.setValue(new SearchResult(data.getMessage()));
                    }
                }));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        fwImgurImageRepository.clearSearchData();
        distroyDisposables();
    }

    public void clearDisposables(){
        if (disposables!=null && !disposables.isDisposed()) {
            disposables.clear();
        }
    }

    public void distroyDisposables(){
        if (disposables!=null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
