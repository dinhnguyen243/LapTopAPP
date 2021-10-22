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

import com.example.laptopapp.Model.Bill;
import com.example.laptopapp.Model.Voucher;
import com.example.laptopapp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class BillAdapter extends BaseAdapter {

    private ArrayList<Bill> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public BillAdapter(Context aContext, ArrayList<Bill> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_history, null);
            holder = new ViewHolder();
            holder.Bill_id = (TextView) convertView.findViewById(R.id.bill_id);
            holder.Bill_total = (TextView) convertView.findViewById(R.id.total_price_bill);
            holder.Bill_date = (TextView) convertView.findViewById(R.id.datebill);
            holder.Bill_status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Bill bill = this.listData.get(position);
      holder.Bill_id.setText("Mã bill : " + bill.getBill_id());
      holder.Bill_total.setText("Tổng giá : " + NumberFormat.getNumberInstance(Locale.US).format(bill.getTotal_price()));
        Date temp_date = null;
        String Date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       try{
           temp_date = sdf.parse(bill.getCreated_at());
           Date = output.format(temp_date);
       }catch (Exception e){

       }

      holder.Bill_date.setText("Ngày : " + Date);
      String Status;
      if(bill.isStatus() == true){
          Status = "Đã giao hàng";
      }else{
          Status = "Đang giao hàng";
      }
      holder.Bill_status.setText("Tình Trạng : " + Status );

        return convertView;
    }
    static class ViewHolder {
        TextView Bill_id,Bill_total,Bill_date, Bill_status;
    }
}


