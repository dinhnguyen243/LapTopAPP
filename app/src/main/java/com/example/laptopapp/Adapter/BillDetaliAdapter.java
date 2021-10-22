package com.example.laptopapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laptopapp.Model.Bill;
import com.example.laptopapp.Model.BillDetail;
import com.example.laptopapp.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class BillDetaliAdapter extends BaseAdapter {
    private ArrayList<BillDetail> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public BillDetaliAdapter(Context aContext, ArrayList<BillDetail> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_productbill, null);
            holder = new ViewHolder();
            holder.product_id = (TextView) convertView.findViewById(R.id.nameproductbill);
            holder.billdetail_price = (TextView) convertView.findViewById(R.id.total_price_billdetali);
            holder.Bill_date = (TextView) convertView.findViewById(R.id.datebilldetail);
            holder.Bill_quanliti = (TextView) convertView.findViewById(R.id.quanlity_billdetali);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillDetail bill = this.listData.get(position);
        Date temp_date = null;
        String Date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            temp_date = sdf.parse(bill.getCreated_at());
            Date = output.format(temp_date);
        }catch (Exception e){

        }
      holder.product_id.setText("Mã sản phẩm : " + bill.getProduct_id());
      holder.billdetail_price.setText("Tổng giá : " + NumberFormat.getNumberInstance(Locale.US).format(bill.getLast_price()));
      holder.Bill_date.setText("Ngày : " + Date);
      holder.Bill_quanliti.setText("Số lượng : " + bill.getQuantity());
        return convertView;
    }
    static class ViewHolder {
        TextView product_id,billdetail_price,Bill_date, Bill_quanliti;
    }
}


