package com.example.content.retrofit;

import com.example.content.constants.Constant;
import com.example.content.dataModel.ContentModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
public interface APIService {

    @GET(Constant.Services.ContentApi)
    Call<ArrayList<ContentModel>> getContent();

}
