package com.example.laptopapp.Data;

import android.content.Context;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laptopapp.Adapter.promotionAdapter;
import com.example.laptopapp.Model.Event;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loadEvent {
    ArrayList<Event> events = new ArrayList<>();
   public void takeallEvent(Context aContext, ListView listView){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
     apiService.event().enqueue(new Callback<ArrayList<Event>>() {
         @Override
         public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
             events = response.body();
             taketotalnotification(events);
             promotionAdapter promotionAdapter = new promotionAdapter(aContext,events);
             listView.setAdapter(promotionAdapter);
         }

         @Override
         public void onFailure(Call<ArrayList<Event>> call, Throwable t) {

         }
     });

    }
    public void taketotalnotification(ArrayList<Event> event){
       events = event;
    }

}
