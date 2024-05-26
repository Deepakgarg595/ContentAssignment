package com.example.content.retrofit;


import android.content.Context;

public class ApiCall {
    private static APIService service;

    public static ApiCall getInstance(Context context) {
        if (service == null) {
            service = RestClient.getClient();
        }
        return new ApiCall();
    }




//    public void add_photo_mock_drill(MultipartBody.Part imageData,final IApiCallback iApiCallback) {
//        Call<IncidentMediaModel> call = service.add_photo_mock_drill( imageData);
//        call.enqueue(new Callback<IncidentMediaModel>() {
//            @Override
//            public void onResponse(Call<IncidentMediaModel> call, Response<IncidentMediaModel> response) {
//                iApiCallback.onSuccess("add_photo",response,null);
//            }
//            @Override
//            public void onFailure(Call<IncidentMediaModel> call, Throwable t) {
//                iApiCallback.onFailure("" + t.getMessage());
//            }
//        });
//    }

  }