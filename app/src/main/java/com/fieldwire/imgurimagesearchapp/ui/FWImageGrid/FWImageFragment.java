/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fieldwire.imgurimagesearchapp.ui.FWImageGrid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fieldwire.imgurimagesearchapp.R;
import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;
import com.google.android.material.transition.MaterialContainerTransform;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


/** A Fragment that displays an Image's details. */
public class FWImageFragment extends Fragment {
  public static final String TAG = "FWImageFragment";
  private static final String IMAGE_ID_KEY = "album_id_key";

  public static FWImageFragment newInstance(String albumId) {
    FWImageFragment fragment = new FWImageFragment();
    Bundle bundle = new Bundle();
    bundle.putString(IMAGE_ID_KEY, albumId);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);
    setUpTransitions();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
    return layoutInflater.inflate(R.layout.cat_transition_image_gallery_fragment, viewGroup, false);
  }

  FWImageViewModel viewModel;

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
    View container = view.findViewById(R.id.container);
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    ImageView albumImage = view.findViewById(R.id.album_image);
    TextView albumTitle = view.findViewById(R.id.album_title);

    String albumId = getArguments().getString(IMAGE_ID_KEY);

    FWImageItem fwImageItem = null;

    viewModel =  new ViewModelProvider(getActivity(), new FWImageViewModelFactory()).get(FWImageViewModel.class);
    for(FWImageItem f: viewModel.getSearchResult().getValue().getSuccess()) if(f.getId().equals(albumId)) fwImageItem = f;

    // Set the transition name which matches the list/grid item to be transitioned from for
    // the shared element transition.
    ViewCompat.setTransitionName(container, fwImageItem.getTitle());

    // Set up toolbar
    ViewCompat.setElevation(toolbar, 0F);
    toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

    // Set up gallery info area
    Picasso.get().load("https://i.imgur.com/" + fwImageItem.getId() + ".jpg").into(albumImage);
    albumTitle.setText(fwImageItem.getTitle().toString());
  }

  private void setUpTransitions() {
    MaterialContainerTransform transform = new MaterialContainerTransform();
    setSharedElementEnterTransition(transform);
  }

}
