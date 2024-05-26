package com.example.content.retrofit;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.content.dataModel.ContentModel;
import com.example.content.dataModel.Thumbnail;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRepo {
    private final MutableLiveData<ArrayList<ContentModel>> contents;
    private final ArrayList<ContentModel> content;

    public MyRepo(Application application) {
        content = new ArrayList<>();
        contents = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<ContentModel>> callAPI(){
        Call<ArrayList<ContentModel>> call = RestClient.getClient().getContent();
        call.enqueue(new Callback<ArrayList<ContentModel>>() {

            @Override
            public void onResponse(Call<ArrayList<ContentModel>> call, Response<ArrayList<ContentModel>> response) {
                if(response.code() == 200) {
                    contents.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ContentModel>> call, Throwable t) {
                contents.postValue(null);
                System.out.println("t.getMessage() = " + t.getMessage());

            }
        });
        return contents;
    }
}