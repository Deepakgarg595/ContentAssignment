package com.example.content;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.content.dataModel.ContentModel;
import com.example.content.utility.ConnectivityReceiver;
import com.example.content.viewModel.MyViewModel;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityListener {

    private ArrayList<ContentModel> modelRecyclerArrayList;
    private ContentAdapter adapter;
    private MyViewModel myViewModel;
    private RecyclerView myRecyclerView;
    private ConnectivityReceiver connectivityReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        myRecyclerView=(RecyclerView) findViewById(R.id.myRecyclerView);

        connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.setListener(this);

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, intentFilter);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        adapter=new ContentAdapter(ContentActivity.this);

        myRecyclerView.setItemViewCacheSize(50);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        myRecyclerView.setAdapter(adapter);

        loadImages();
    }

    private void loadImages() {
        myViewModel.loadData().observe(this, new Observer<ArrayList<ContentModel>>() {
            @Override
            public void onChanged(ArrayList<ContentModel> countries) {
                if(countries!=null){
                    modelRecyclerArrayList = countries;
                    adapter.updateList(modelRecyclerArrayList);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            retryApiCall();
        }
    }

    private void retryApiCall() {
        loadImages();
    }
}