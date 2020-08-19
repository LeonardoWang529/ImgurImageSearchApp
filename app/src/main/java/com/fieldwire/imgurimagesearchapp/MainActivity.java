package com.fieldwire.imgurimagesearchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.fieldwire.imgurimagesearchapp.databinding.ActivityFwimagegridBinding;
import com.fieldwire.imgurimagesearchapp.databinding.ActivityMainBinding;
import com.fieldwire.imgurimagesearchapp.ui.FWImageGrid.FWImageGridActivity;

// THIS activity is opened for future log in feature
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchKeyword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int k, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (k == KeyEvent.KEYCODE_ENTER)) {
                    if(binding.searchKeyword.getText() != null){
                        Intent i = new Intent(view.getContext(), FWImageGridActivity.class);
                        i.putExtra("SEARCHKEYWORD",binding.searchKeyword.getText().toString());
                        startActivity(i);
                        return true;
                    }
                }
                return false;
            }
        });

    }
}