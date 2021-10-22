package com.example.laptopapp.Data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Adapter.RecyclerView_LoadProductAdapter;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loadData extends AsyncTask {
    private ProgressDialog mProgressDialog;
    private Context aContext;
    private RecyclerView myrv;
    ArrayList<Laptop> laptops = new ArrayList<>();
    ArrayList<Laptop> brandlist = new ArrayList<>();
    ArrayList<Laptop> namelist = new ArrayList<>();
    private String type;
    private String name;
    MainActivity mainActivity;
    public loadData(Context aContext, RecyclerView myrv, String type, String name, MainActivity mainActivity) {
        this.aContext = aContext;
        this.myrv = myrv;
        this.type = type;
        this.name = name;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        mProgressDialog = ProgressDialog.show(aContext, "load", "Please wait...", false, false);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mProgressDialog.dismiss();
        //Show success toast
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.laptop().enqueue(new Callback<ArrayList<Laptop>>() {
            @Override
            public void onResponse(Call<ArrayList<Laptop>> call, Response<ArrayList<Laptop>> response) {
                laptops = response.body();
                System.out.println("main success");
                try{
                    if (type.equals("Full") && name == null){
                        RecyclerView_LoadProductAdapter myAdapter = new RecyclerView_LoadProductAdapter(aContext,laptops,mainActivity);
                        myrv.setLayoutManager(new GridLayoutManager(aContext, 2));
                        myrv.setAdapter(myAdapter);
                    }else if(type != "" && name == null){
                        for (Laptop laptop : laptops){
                            if(laptop.getBrand().toLowerCase().equals(type.toLowerCase())){
                                brandlist.add(laptop);
                            }
                        }
                        RecyclerView_LoadProductAdapter myAdapter = new RecyclerView_LoadProductAdapter(aContext,brandlist,mainActivity);
                        myrv.setLayoutManager(new GridLayoutManager(aContext, 2));
                        myrv.setAdapter(myAdapter);
                    }else if(type.equals("") && name != null){
                        for (Laptop laptop : laptops){
                            if(laptop.getProduct_name().toLowerCase().contains(name.toLowerCase())){
                                namelist.add(laptop);
                            }
                        }
                        RecyclerView_LoadProductAdapter myAdapter = new RecyclerView_LoadProductAdapter(aContext,namelist,mainActivity);
                        myrv.setLayoutManager(new GridLayoutManager(aContext, 2));
                        myrv.setAdapter(myAdapter);
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<ArrayList<Laptop>> call, Throwable t) {
                System.out.println("fail" + t);
            }
        });
        return null;
    }
}
