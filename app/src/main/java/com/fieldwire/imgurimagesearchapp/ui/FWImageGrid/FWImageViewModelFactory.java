package com.fieldwire.imgurimagesearchapp.ui.FWImageGrid;

import com.fieldwire.imgurimagesearchapp.data.imgurImageModule.FWImgurImageDataSource;
import com.fieldwire.imgurimagesearchapp.data.imgurImageModule.FWImgurImageRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FWImageViewModelFactory  implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FWImageViewModel.class)) {
            return (T) new FWImageViewModel(FWImgurImageRepository.getInstance(new FWImgurImageDataSource()));
        }else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
