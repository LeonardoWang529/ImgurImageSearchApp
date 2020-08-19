package com.fieldwire.imgurimagesearchapp.data.imgurImageModule;

import com.fieldwire.imgurimagesearchapp.data.Result;
import com.fieldwire.imgurimagesearchapp.data.model.FWGalleryItem;
import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class FWImgurImageRepository {
    static FWImgurImageRepository instance;
    private FWImgurImageDataSource dataSource;
    private List<FWImageItem> fwImageItemList;

    synchronized public static FWImgurImageRepository getInstance(FWImgurImageDataSource dataSource){
        if(instance == null){
            instance = new FWImgurImageRepository(dataSource);
        }
        return instance;
    }

    private FWImgurImageRepository(FWImgurImageDataSource dataSource){
        this.dataSource = dataSource;
        fwImageItemList =  new ArrayList<>();
    }

    public void clearSearchData(){
        fwImageItemList =  new ArrayList<>();
    }

    public Callable<Result> getSearchImages(String sort, String window, String page, String keyWord){
        Callable<Result> callable = new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Result r = dataSource.getSearchImages(sort,window,page,keyWord,fwImageItemList);
                if(r instanceof Result.Success) fwImageItemList = ((Result.Success<List<FWImageItem>>) r).getData();
                return r;
            }
        };
        return callable;
    }

}
