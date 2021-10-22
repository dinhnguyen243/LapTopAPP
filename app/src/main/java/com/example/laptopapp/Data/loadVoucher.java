package com.example.laptopapp.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Adapter.promotionAdapter;
import com.example.laptopapp.Adapter.voucherAdapter;
import com.example.laptopapp.Model.Event;
import com.example.laptopapp.Model.Voucher;
import com.example.laptopapp.R;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class loadVoucher {
    ArrayList<Voucher> vouchers = new ArrayList<>();
    public void takeallvoucher(Context aContext, ListView listView, MainActivity mainActivity){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
       apiService.voucher().enqueue(new Callback<ArrayList<Voucher>>() {
           @Override
           public void onResponse(Call<ArrayList<Voucher>> call, Response<ArrayList<Voucher>> response) {
               vouchers = response.body();
               ArrayList<Voucher> tempvoucher = new ArrayList<>();
               for(Voucher voucher : vouchers){
                   if (voucher.isStatus() == true){
                       tempvoucher.add(voucher);
                   }
               }
               voucherAdapter promotionAdapter = new voucherAdapter(aContext,tempvoucher);
               listView.setAdapter(promotionAdapter);
               listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      mainActivity.showdetailspromotion(vouchers.get(position).get_id());
                   }
               });
           }

           @Override
           public void onFailure(Call<ArrayList<Voucher>> call, Throwable t) {

           }
       });
    }
    public void takedetalisvoucher(ImageView img,TextView title, TextView date, TextView discount, TextView code, String id){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.voucher().enqueue(new Callback<ArrayList<Voucher>>() {
            @Override
            public void onResponse(Call<ArrayList<Voucher>> call, Response<ArrayList<Voucher>> response) {
                vouchers = response.body();
                Date temp_date = null;
                String Date = "";
                for(Voucher voucher : vouchers){
                    if(voucher.get_id().equals(id)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        try {
                            temp_date = sdf.parse(voucher.getCreated_at());
                           Date = output.format(temp_date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        title.setText(voucher.getDescription());
                        date.setText(Date);
                        discount.setText("Giảm "+voucher.getDiscount() +"% cho mã code : ");
                        code.setText(voucher.getVoucher_code());
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            URL url = new URL(voucher.getImage().trim());
                            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                            httpConn.connect();
                            int resCode = httpConn.getResponseCode();
                            if (resCode == HttpURLConnection.HTTP_OK) {
                                InputStream in = httpConn.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(in);
                                img.setImageBitmap(bitmap);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                            img.setImageResource(R.drawable.logo);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Voucher>> call, Throwable t) {

            }
        });
    }
}
