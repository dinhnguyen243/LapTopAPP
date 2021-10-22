package com.example.laptopapp.Data;

import android.content.Context;
import android.os.StrictMode;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.laptopapp.Model.Order;
import com.example.laptopapp.Model.User;
import com.example.laptopapp.Model.listProduct;
import com.example.laptopapp.Sendmail.JavaMailAPI;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateOrder {
    ArrayList<listProduct> listProduct;
    public void takeinformuser(TextView name, TextView phone, TextView address, String user_id, RelativeLayout btn_dathang){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.Takeinformuser(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                name.setText(user.getFullname());
                phone.setText(user.getPhone());
                address.setText(user.getAddress());
                if(address.getText().toString().isEmpty() && phone.getText().toString().isEmpty()){
                    address.setError("Địa chỉ trống");
                    phone.setError("Số điện thoại trống");
                    btn_dathang.setEnabled(false);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    public void sendmail(String user_id,Context context){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.Takeinformuser(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
              User user = response.body();
              String Sub = "Xác Nhận Thanh Toán";
              String mess =  "Cảm ơn bạn đã mua hàng tại LTG laptop !!!" + "\n Vui Lòng Kiểm tra hàng cẩn thận trước khi nhận hàng \n" +"Mọi chi tiết xin liên hệ 19001588!!";
              sendMail(user.getEmail(),Sub,mess,context);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    private void sendMail(String mail, String subject, String message, Context context) {
        JavaMailAPI javaMailAPI = new JavaMailAPI(context, mail, subject, message);
        javaMailAPI.execute();

    }
}
