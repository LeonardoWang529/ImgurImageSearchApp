package com.fieldwire.imgurimagesearchapp.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class FWImageItem {
    String id;
    String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static final DiffUtil.ItemCallback<FWImageItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FWImageItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull FWImageItem newItem, @NonNull FWImageItem oldItem) {
                    return newItem.title.equals(oldItem.title);
                }

                @Override
                public boolean areContentsTheSame(@NonNull FWImageItem newItem, @NonNull FWImageItem oldItem) {
                    return false;
                }
            };

}
