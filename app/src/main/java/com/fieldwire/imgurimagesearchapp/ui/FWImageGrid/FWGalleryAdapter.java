package com.fieldwire.imgurimagesearchapp.ui.FWImageGrid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldwire.imgurimagesearchapp.R;
import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class FWGalleryAdapter extends ListAdapter<FWImageItem, FWGalleryAdapter.GalleryViewHolder> {

    public interface GalleryAdapterListener {
        void onGalleryClicked(View view, FWImageItem imageItem);
    }

    private final GalleryAdapterListener listener;
    private final int itemLayout;

    FWGalleryAdapter(GalleryAdapterListener listener) {
        super(FWImageItem.DIFF_CALLBACK);
        this.listener = listener;
        this.itemLayout = R.layout.cat_transition_gallery_grid_item;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GalleryViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false),
                listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder galleryViewHolder, int i) {
        galleryViewHolder.bind(getItem(i));
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private final GalleryAdapterListener listener;

        private final View container;
        private final ImageView galleryImage;
        private final TextView galleryTitle;

        GalleryViewHolder(View view, GalleryAdapterListener listener) {
            super(view);
            this.listener = listener;

            container = view.findViewById(R.id.album_item_container);
            galleryImage = view.findViewById(R.id.album_image);
            galleryTitle = view.findViewById(R.id.album_title);
        }

        public void bind(FWImageItem album) {
            ViewCompat.setTransitionName(container, album.getTitle());
            container.setOnClickListener(v -> listener.onGalleryClicked(container, album));
            Picasso.get().load("https://i.imgur.com/" + album.getId() + ".jpg").resize(100,100).into(galleryImage);
            galleryTitle.setText(album.getTitle());
        }
    }
}

