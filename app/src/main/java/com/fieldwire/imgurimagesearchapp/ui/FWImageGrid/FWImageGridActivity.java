package com.fieldwire.imgurimagesearchapp.ui.FWImageGrid;

import android.os.Bundle;

import com.fieldwire.imgurimagesearchapp.R;
import com.fieldwire.imgurimagesearchapp.databinding.ActivityFwimagegridBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

/** A Activity that hosts future fragment. */
public class FWImageGridActivity extends AppCompatActivity {

    ActivityFwimagegridBinding binding;
    FWImageViewModel viewModel;
    String keyword="";
    int pageCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFwimagegridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        keyword = getIntent().getStringExtra("SEARCHKEYWORD");
        viewModel = new ViewModelProvider(this, new FWImageViewModelFactory()).get(FWImageViewModel.class);
        setFWImageItemLiveData("","");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FWImageGridFragment())
                .commit();
    }

    public void setFWImageItemLiveData(String sort, String window){
        viewModel.setFWImageItemLiveData(sort,window,Integer.toString(pageCount),keyword);
        pageCount++;
    }
}
