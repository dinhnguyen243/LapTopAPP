package com.example.laptopapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptopapp.Activity.CartActivity;
import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.R;
import com.example.laptopapp.Session.SessionManagement;
import com.example.laptopapp.dataCart.CartStoge;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartListAdapter extends BaseAdapter {

    private ArrayList<Cart> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    int quanlity = 0;
    private TextView totalPrice;

    public CartListAdapter(Context aContext, ArrayList<Cart> listData, TextView totalPrice) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        this.totalPrice = totalPrice;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_cart, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_product);
            holder.NameView = (TextView) convertView.findViewById(R.id.textView_NameProduct);
            holder.PriceView = (TextView) convertView.findViewById(R.id.price_product_cart);
            holder.quanlity = (TextView) convertView.findViewById(R.id.quanlity_incard);
            holder.up_icon = (ImageView) convertView.findViewById(R.id.addquanlity);
            holder.down_icon = (ImageView) convertView.findViewById(R.id.sunquanlity);
            holder.delete_icon = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cart cart = this.listData.get(position);
        String temp_quality = cart.getQuality();
        CartStoge cartStoge = new CartStoge(context);
        SessionManagement sessionManagement = new SessionManagement(context);
        String user_id = sessionManagement.getSession();
        quanlity = Integer.parseInt(temp_quality);
        holder.up_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quanlity++;
                holder.quanlity.setText(String.valueOf(quanlity));
                cartStoge.updateCart(user_id, cart.getProduct_id(), String.valueOf(quanlity));
                holder.PriceView.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(Float.parseFloat(cart.getPrice()) * quanlity));
                int totalprice = 0;
                listData = new ArrayList<>();
                listData = cartStoge.getallcart(user_id);
                for (int i = 0; i < listData.size(); i++) {
                    totalprice += Float.parseFloat(listData.get(i).getPrice()) * Integer.parseInt(listData.get(i).getQuality());
                    totalPrice.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(totalprice));
                }

            }
        });
        holder.down_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quanlity == 1) {
                    holder.down_icon.setEnabled(true);
                    holder.quanlity.setText("1");
                } else {
                    quanlity--;
                    cartStoge.updateCart(user_id, cart.getProduct_id(), String.valueOf(quanlity));
                    holder.quanlity.setText(String.valueOf(quanlity));
                    holder.PriceView.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(Float.parseFloat(cart.getPrice()) * quanlity));
                    int totalprice = 0;
                    listData = new ArrayList<>();
                    listData = cartStoge.getallcart(user_id);
                    for (int i = 0; i < listData.size(); i++) {
                        totalprice += Float.parseFloat(listData.get(i).getPrice()) * Integer.parseInt(listData.get(i).getQuality());
                        totalPrice.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(totalprice));
                    }
                }
            }
        });
        holder.delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("Delete this?").setMessage("Are you sure?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartStoge.deleteCart(cart.getProduct_id(), user_id);
                        listData.remove(position);
                        int totalprice = 0;
                        listData = new ArrayList<>();
                        listData = cartStoge.getallcart(user_id);
                        for (int i = 0; i < listData.size(); i++) {
                            totalprice += Float.parseFloat(listData.get(i).getPrice()) * Integer.parseInt(listData.get(i).getQuality());
                            totalPrice.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(totalprice));
                        }
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();


            }
        });
        int totalprice = 0;
        for (int i = 0; i < listData.size(); i++) {
            totalprice += Float.parseFloat(listData.get(i).getPrice()) * Integer.parseInt(listData.get(i).getQuality());
            totalPrice.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(totalprice));
        }
        holder.quanlity.setText(String.valueOf(quanlity));
        holder.NameView.setText(cart.getProduct_name());
        holder.PriceView.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(Float.parseFloat(cart.getPrice())));

        try {
            URL url = new URL(cart.getImg().trim());
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            int resCode = httpConn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                holder.flagView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            System.out.println(e);
            holder.flagView.setImageResource(R.drawable.logo);
        }
        return convertView;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    static class ViewHolder {
        ImageView flagView, up_icon, down_icon, delete_icon;
        TextView NameView, quanlity;
        TextView PriceView;
    }


}


