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

import com.example.laptopapp.Model.Event;
import com.example.laptopapp.Model.Voucher;
import com.example.laptopapp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class promotionAdapter extends BaseAdapter {

    private ArrayList<Event> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public promotionAdapter(Context aContext, ArrayList<Event> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_promotion, null);
            holder = new ViewHolder();
            holder.flagView = (ImageView) convertView.findViewById(R.id.imageView_promotion);
            holder.NameView = (TextView) convertView.findViewById(R.id.promotion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Event promotion = this.listData.get(position);
        holder.NameView.setText(promotion.getTitle());
        try {
            URL url = new URL(promotion.getLink_img().trim());
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
    static class ViewHolder {
        ImageView flagView;
        TextView NameView;
    }
}


