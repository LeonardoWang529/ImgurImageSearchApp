package com.fieldwire.imgurimagesearchapp.ui.FWImageGrid;

import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;

import java.util.List;

import androidx.annotation.Nullable;

class SearchResult {
    @Nullable
    private List<FWImageItem> success;
    @Nullable
    private String error;

    SearchResult(@Nullable String error) {
        this.error = error;
    }

    SearchResult(@Nullable List<FWImageItem> success) {
        this.success = success;
    }

    @Nullable
    List<FWImageItem> getSuccess() {
        return success;
    }

    @Nullable
    String getError() {
        return error;
    }
}
