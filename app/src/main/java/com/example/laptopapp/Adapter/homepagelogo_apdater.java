package com.example.laptopapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptopapp.Interface.Itemclick;
import com.example.laptopapp.Model.Logo;
import com.example.laptopapp.R;

import java.util.ArrayList;


public class homepagelogo_apdater extends RecyclerView.Adapter<homepagelogo_apdater.iconhomepageViewHodler> {
    private static OnItemClickListener listener;
    Context context;
    ArrayList<Logo> List;
    int row = -1;
   Itemclick itemclick;
   boolean check = true;
   boolean select = true;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public homepagelogo_apdater(Context context, ArrayList<Logo> list, Itemclick itemclick) {
        this.context = context;
        this.List = list;
        this.itemclick = itemclick;
    }

    @NonNull
    @Override
    public iconhomepageViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iconhomepage_item, parent, false);
        return new iconhomepageViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull iconhomepageViewHodler holder, int position) {
        holder.logo_image.setImageResource(List.get(position).getImage());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row = position;
                notifyDataSetChanged();
                if(position == 0){
                    itemclick.callback(position);
                }else if(position == 1){
                    itemclick.callback(position);
                }else if(position == 2){
                    itemclick.callback(position);
                }else if(position == 3){
                    itemclick.callback(position);
                }else if(position == 4) {
                    itemclick.callback(position);
                }
            }
        });
        if (row == position) {
            holder.linearLayout.setBackgroundResource(R.drawable.logohome_bg);
        } else {
            holder.linearLayout.setBackgroundResource(R.drawable.logoicon_select);
        }
    }

    @Override
    public int getItemCount() {
        if (List != null) {
            return List.size();
        }
        return 0;
    }

    public static final class iconhomepageViewHodler extends RecyclerView.ViewHolder {
        ImageView logo_image;
        LinearLayout linearLayout;

        public iconhomepageViewHodler(@NonNull View itemView) {
            super(itemView);
            logo_image = itemView.findViewById(R.id.icon_logo);
            linearLayout = itemView.findViewById(R.id.liner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
