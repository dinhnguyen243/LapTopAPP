package com.example.laptopapp.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.R;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;
import com.example.laptopapp.dataCart.CartStoge;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class laptopData {
    Laptop laptops;

    public Laptop takeallinfo(String productID, TextView name, TextView price, TextView masp, ImageSlider imageSlider, YouTubePlayerView view) {
        System.out.println("run");
        laptops = new Laptop();
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.detail(productID).enqueue(new Callback<Laptop>() {
            @Override
            public void onResponse(Call<Laptop> call, Response<Laptop> response) {
                laptops = response.body();
                name.setText(laptops.getProduct_name());
                price.setText(NumberFormat.getNumberInstance(Locale.US).format(laptops.getPrice_outcome()));
                masp.setText(laptops.getProduct_id());
                List<SlideModel> slideModels = new ArrayList<>();
                for(int i = 0; i < laptops.getImg().size(); i++){
                    slideModels.add(new SlideModel("https://admin-laptop-app.herokuapp.com/public/img/"+laptops.getImg().get(i).trim(),null));
                }
                imageSlider.setImageList(slideModels,true);
                String id_video = laptops.getLink_review().get(0).split("v=")[1].toString();
                view.addYouTubePlayerListener(new YouTubePlayerListener() {
                    @Override
                    public void onReady(YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(id_video, 0);
                    }

                    @Override
                    public void onStateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlayerState playerState) {

                    }

                    @Override
                    public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {

                    }

                    @Override
                    public void onPlaybackRateChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackRate playbackRate) {

                    }

                    @Override
                    public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError playerError) {

                    }

                    @Override
                    public void onCurrentSecond(YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoDuration(YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoLoadedFraction(YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoId(YouTubePlayer youTubePlayer, String s) {

                    }

                    @Override
                    public void onApiChange(YouTubePlayer youTubePlayer) {

                    }
                });

            }

            @Override
            public void onFailure(Call<Laptop> call, Throwable t) {
                System.out.println("not ok ");
            }
        });
        return laptops;
    }

    public void takeinfodetails(String productID, TextView masp, TextView tensp, TextView brand, TextView ram, TextView rom, TextView CPU, TextView Core, TextView mausac, TextView manhinh, TextView kichthuoc, TextView VGA, TextView os, ImageView img) {
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.detail(productID).enqueue(new Callback<Laptop>() {
            @Override
            public void onResponse(Call<Laptop> call, Response<Laptop> response) {
                laptops = response.body();
                masp.setText(laptops.getProduct_id());
                tensp.setText(laptops.getProduct_name());
                brand.setText(laptops.getBrand());
                ram.setText(laptops.getRam());
                rom.setText(laptops.getHard_disk());
                CPU.setText(laptops.getCpu());
                Core.setText(laptops.getCore());
                mausac.setText(laptops.getColor());
                manhinh.setText(laptops.getMonitor());
                kichthuoc.setText(laptops.getSize());
                VGA.setText(laptops.getVga());
                os.setText(laptops.getOs());
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    URL url = new URL("https://admin-laptop-app.herokuapp.com/public/img/"+laptops.getImg().get(0).trim());
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

            @Override
            public void onFailure(Call<Laptop> call, Throwable t) {

            }
        });
    }
    public void addtocartlaptop(String Product_id, String user_id, Context context){
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.detail(Product_id).enqueue(new Callback<Laptop>() {
            @Override
            public void onResponse(Call<Laptop> call, Response<Laptop> response) {
                laptops = response.body();
                CartStoge cartStoge = new CartStoge(context);
                cartStoge.addtocart(laptops,user_id);
            }

            @Override
            public void onFailure(Call<Laptop> call, Throwable t) {

            }
        });
    }

}
