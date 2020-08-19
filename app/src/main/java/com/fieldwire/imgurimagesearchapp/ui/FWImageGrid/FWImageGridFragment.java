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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fieldwire.imgurimagesearchapp.R;
import com.fieldwire.imgurimagesearchapp.data.model.FWImageItem;
import com.fieldwire.imgurimagesearchapp.utils.FWLogs;
import com.google.android.material.transition.Hold;
import com.google.android.material.transition.MaterialSharedAxis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;


/** A Fragment that hosts a toolbar and a child fragment with a image data. */
public class FWImageGridFragment extends Fragment
    implements FWGalleryAdapter.GalleryAdapterListener, OnMenuItemClickListener {

  public static final String TAG = "FWImageGridFragment";
  private static final int GRID_SPAN_COUNT = 3;

  private FrameLayout listContainer;

  private boolean loading = true;
  private boolean listSorted = true;

  FWImageViewModel viewModel;

  FWGalleryAdapter adapter = new FWGalleryAdapter(this);
  List<FWImageItem> imageItems = new ArrayList<>();

  @Override
  public void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);
    // Use a Hold transition to keep this fragment visible beneath the MaterialContainerTransform
    // that transitions to the image details screen. Without a Hold, this fragment would disappear
    // as soon its container is replaced with a new Fragment.
    Hold hold = new Hold();
    // Add root view as target for the Hold so that the entire view hierarchy is held in place as
    // one instead of each child view individually. Helps keep shadows during the transition.
    hold.addTarget(R.id.container);
    setExitTransition(hold);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater layoutInflater,
      @Nullable ViewGroup viewGroup,
      @Nullable Bundle bundle) {
    return layoutInflater.inflate(R.layout.cat_transition_image_library_fragment, viewGroup, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
    Toolbar toolbar = view.findViewById(R.id.toolbar);
    listContainer = view.findViewById(R.id.list_container);
    toolbar.setOnMenuItemClickListener(this);
    MaterialSharedAxis sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.Z, true);
    setList(listSorted, sharedAxis);
  }

  @Override
  public void onGalleryClicked(View view, FWImageItem imageItem) {
    FWImageFragment fragment = FWImageFragment.newInstance(imageItem.getId());

    getParentFragmentManager()
        .beginTransaction()
        .addSharedElement(view, ViewCompat.getTransitionName(view))
        .replace(R.id.fragment_container, fragment, FWImageFragment.TAG)
        .addToBackStack(FWImageFragment.TAG)
        .commit();
  }

  @Override
  public boolean onMenuItemClick(MenuItem menuItem) {
    if(!loading) {
      MaterialSharedAxis sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.Y, true);
      setList(!listSorted, sharedAxis);
    }
    return true;
  }

  private void setList( boolean listSorted, Transition transition) {
    this.listSorted = listSorted;

    // Use a Transition to animate the removal and addition of the RecyclerViews.
    RecyclerView recyclerView = createRecyclerView();
    transition.addTarget(recyclerView);
    View currentRecyclerView = listContainer.getChildAt(0);
    if (currentRecyclerView != null) {
      transition.addTarget(currentRecyclerView);
    }
    TransitionManager.beginDelayedTransition(listContainer, transition);

    recyclerView.setAdapter(adapter);

    viewModel =  new ViewModelProvider(getActivity(), new FWImageViewModelFactory()).get(FWImageViewModel.class);
    ProgressBar progressBar = getActivity().findViewById(R.id.loading_progressbar);
    loading = true;
    viewModel.getSearchResult().observe(getActivity(), new Observer<SearchResult>() {
      @Override
      public void onChanged(SearchResult searchResult) {
        if (searchResult == null) {
          return;
        }
        progressBar.setVisibility(View.GONE);
        if (searchResult.getError() != null) {
          showFailedMessage(searchResult.getError());
        }
        if (searchResult.getSuccess() != null) {
          updateFrontList(searchResult.getSuccess());
        }
        loading = false;
      }
    });

    listContainer.removeAllViews();
    listContainer.addView(recyclerView);

  }

  private void updateFrontList(List<FWImageItem> model) {
    FWLogs.i(TAG,"Success");
    if(!model.isEmpty()) {
      imageItems = new ArrayList<>(model);
      if (!listSorted) {
        Collections.reverse(imageItems);
      }
      adapter.submitList(imageItems);
    }
  }

  private void showFailedMessage(String errorString) {
    Toast.makeText(getContext(), errorString, Toast.LENGTH_LONG).show();
  }

  private RecyclerView createRecyclerView() {
    Context context = requireContext();
    RecyclerView recyclerView = new RecyclerView(context);
    recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    int verticalPadding = context.getResources().getDimensionPixelSize(R.dimen.album_list_padding_vertical);
    recyclerView.setPadding(0, verticalPadding, 0, verticalPadding);
    recyclerView.setHasFixedSize(true);
    recyclerView.setItemViewCacheSize(100);
    recyclerView.setClipToPadding(false);
    recyclerView.setLayoutManager(new GridLayoutManager(context, GRID_SPAN_COUNT));

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == imageItems.size() - 1) {
          if(!recyclerView.isAnimating() && !recyclerView.isComputingLayout()) {
            ((FWImageGridActivity) getActivity()).setFWImageItemLiveData("", "");
          }
        }
      }
    });
    return recyclerView;
  }

}
