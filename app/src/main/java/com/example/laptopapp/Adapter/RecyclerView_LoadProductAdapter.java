package com.example.laptopapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptopapp.Activity.CartActivity;
import com.example.laptopapp.Activity.MainActivity;
import com.example.laptopapp.Activity.ProductDetails;
import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.Model.Laptop;
import com.example.laptopapp.R;
import com.example.laptopapp.Session.SessionManagement;
import com.example.laptopapp.dataCart.CartStoge;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Aws on 28/01/2018.
 */

public class RecyclerView_LoadProductAdapter extends RecyclerView.Adapter<RecyclerView_LoadProductAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Laptop> listData;
    private ArrayList<Cart> listCarts;
    private  MainActivity mainActivity;
    public RecyclerView_LoadProductAdapter(Context mContext, ArrayList<Laptop> listData, MainActivity mainActivity) {
        this.mContext = mContext;
        this.listData = listData;
        this.mainActivity = mainActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.listproduct, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Laptop laptop = listData.get(position);
        holder.tv_productprice.setText(NumberFormat.getNumberInstance(Locale.US).format(laptop.getPrice_outcome()));
        holder.tv_productname.setText(laptop.getProduct_name());
        if(laptop.getQuantity() > 0){
            holder.quanlity_íntore.setText("Còn hàng");
        }else{
            holder.quanlity_íntore.setText("Hết Hàng");
            holder.quanlity_íntore.setTextColor(Color.RED);
            holder.addtocart.setEnabled(false);
        }
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(mContext);
                String user_id = sessionManagement.getSession();
                if (user_id.equals("-1")){
                    mainActivity.signIn();
                }else {
                    CartStoge cartStoge = new CartStoge(mContext);
                    listCarts = cartStoge.getallcart(user_id);
                    boolean checkexistproduct = false;
                    int quantilyincart = 0;
                    for (int i = 0; i < listCarts.size(); i++){
                        if(listCarts.get(i).getProduct_id().equals(laptop.getProduct_id())){
                            checkexistproduct = true;
                            quantilyincart = Integer.parseInt(listCarts.get(i).getQuality());
                            break;
                        }
                    }
                    if (checkexistproduct == true){
                        cartStoge.updateCart(user_id,laptop.getProduct_id(),String.valueOf(quantilyincart+1));
                        Intent intent = new Intent(mContext, CartActivity.class);
                        mContext.startActivity(intent);
                    }else{
                        cartStoge.addtocart(laptop,user_id);
                        Intent intent = new Intent(mContext, CartActivity.class);
                        mContext.startActivity(intent);
                    }
                }

            }
        });
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(laptop.getImg().get(0).trim());
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            int resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                holder.img_laptop.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            System.out.println(e);
            holder.img_laptop.setImageResource(R.drawable.logo);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra("product_ID",laptop.getProduct_id());
                intent.putExtra("status",holder.quanlity_íntore.getText().toString());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_productname, tv_productprice, addtocart, quanlity_íntore;
        ImageView img_laptop;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_productprice = (TextView) itemView.findViewById(R.id.productprice);
            tv_productname = (TextView) itemView.findViewById(R.id.productname);
            img_laptop = (ImageView) itemView.findViewById(R.id.productimage);
            cardView = (CardView) itemView.findViewById(R.id.Card);
            quanlity_íntore = (TextView) itemView.findViewById(R.id.quanlity_instore);
            addtocart = (TextView) itemView.findViewById(R.id.addtocart);
        }
    }


}
