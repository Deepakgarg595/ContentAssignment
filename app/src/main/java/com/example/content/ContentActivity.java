package com.example.content;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.content.dataModel.ContentModel;
import com.example.content.viewModel.MyViewModel;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {

    private ArrayList<ContentModel> modelRecyclerArrayList;
    private ContentAdapter adapter;
    private MyViewModel myViewModel;
    private RecyclerView myRecyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        myRecyclerView=(RecyclerView) findViewById(R.id.myRecyclerView);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        adapter=new ContentAdapter(ContentActivity.this);
        myRecyclerView.setItemViewCacheSize(50);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        myRecyclerView.setAdapter(adapter);

        myViewModel.loadData().observe(this, new Observer<ArrayList<ContentModel>>() {
            @Override
            public void onChanged(ArrayList<ContentModel> countries) {
                if(countries!=null){
                    modelRecyclerArrayList = countries;
                    adapter.updateList(modelRecyclerArrayList);

                }
                //adapter.notifyDataSetChanged();

            }
        });
    }
}