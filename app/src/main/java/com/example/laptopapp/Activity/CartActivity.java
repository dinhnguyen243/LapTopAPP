package com.example.laptopapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.laptopapp.Adapter.CartListAdapter;
import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.R;
import com.example.laptopapp.Session.SessionManagement;
import com.example.laptopapp.dataCart.CartStoge;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    ListView listView;
     ImageView muahang;
    ImageView back;
    float totalprice;
    ArrayList<Cart> carts = new ArrayList<>();
    CartStoge cartStoge = new CartStoge(CartActivity.this);
    TextView tv_totalprice ;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_page);
        muahang = (ImageView) findViewById(R.id.dathang);
        listView = (ListView) findViewById(R.id.list_cart);
        tv_totalprice = (TextView) findViewById(R.id.tv_totalprice_cart);
        SessionManagement sessionManagement = new SessionManagement(CartActivity.this); // goi session
         String user_id = sessionManagement.getSession(); // set session
        carts = cartStoge.getallcart(user_id); // load trong database sqlite //////////
        if(carts.size() == 0){
            muahang.setEnabled(false);
        }else{
            muahang.setEnabled(true);
        }
        load(); // goi ham load
        back = (ImageView) findViewById(R.id.icon_back_cart); // tao nut back
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, Confirm_checkout.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CartActivity.this, ProductDetails.class);
                intent.putExtra("product_ID",carts.get(position).getProduct_id());
                startActivity(intent);
            }
        });

    }
    public void load(){
        CartListAdapter cartListAdapter = new CartListAdapter(CartActivity.this,carts,tv_totalprice);
        listView.setAdapter(cartListAdapter);
        cartListAdapter.notifyDataSetChanged();
    }
}

