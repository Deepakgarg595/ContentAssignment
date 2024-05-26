package com.example.content.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.content.dataModel.ContentModel;
import com.example.content.retrofit.MyRepo;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {

    private final MyRepo repository ;


    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepo(application);

    }
    public MutableLiveData<ArrayList<ContentModel>> loadData() {
        return repository.callAPI();
    }
}