package com.example.laptopapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CheckoutListAdapter extends BaseAdapter {
    private ArrayList<Cart> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    int quanlity = 0;
    public CheckoutListAdapter(Context aContext, ArrayList<Cart> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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
            convertView = layoutInflater.inflate(R.layout.list_item_checkout, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_product);
            holder.NameView = (TextView) convertView.findViewById(R.id.textView_NameProduct);
            holder.PriceView = (TextView) convertView.findViewById(R.id.price_product_cart);
            holder.quanlity = (TextView) convertView.findViewById(R.id.quanlity_incard);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

      Cart cart = this.listData.get(position);
        String temp_quality = cart.getQuality();
        quanlity = Integer.parseInt(temp_quality);
        holder.quanlity.setText("x" + String.valueOf(quanlity));
        holder.NameView.setText(cart.getProduct_name());
        holder.PriceView.setText(NumberFormat.getNumberInstance(Locale.US).format(Float.parseFloat(cart.getPrice())* quanlity));

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
        ImageView flagView;
        TextView NameView, quanlity;
        TextView PriceView;
    }
}


