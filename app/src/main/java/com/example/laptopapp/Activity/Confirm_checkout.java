package com.example.laptopapp.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laptopapp.Adapter.CheckoutListAdapter;
import com.example.laptopapp.Data.CreateOrder;
import com.example.laptopapp.Model.Cart;
import com.example.laptopapp.Model.Order;
import com.example.laptopapp.Model.UpdateUser;
import com.example.laptopapp.Model.User;
import com.example.laptopapp.Model.Voucher;
import com.example.laptopapp.Model.listProduct;
import com.example.laptopapp.R;
import com.example.laptopapp.Sendmail.JavaMailAPI;
import com.example.laptopapp.Session.SessionManagement;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;
import com.example.laptopapp.dataCart.CartStoge;

import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;

public class Confirm_checkout extends AppCompatActivity {
    ListView listView;
    EditText name, address, phone;
    ImageView back;
    TextView total_price;
    Button updateinfo, checkout_momo;
    RelativeLayout btn_dathang;
    ArrayList<Cart> carts = new ArrayList<>();
    private ArrayList<Voucher> vouchers;
    AutoCompleteTextView tv_voucher;
    String user_id;
    CartStoge cartStoge = new CartStoge(Confirm_checkout.this);
    private static final String[] listvoucher = new String[100];
    ArrayAdapter<String> stringArrayAdapter;
    List<String> vouchercode;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "DSK GAMING";
    private String merchantCode = "MOMOPMMO20210812";
    private String merchantNameLabel = "FPT plytechnich";
    private String description = "Laptop";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_cart_activity);
        ButterKnife.bind(this);
        SessionManagement sessionManagement = new SessionManagement(Confirm_checkout.this);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        listView = (ListView) findViewById(R.id.list_checkout);
        btn_dathang = (RelativeLayout) findViewById(R.id.btn_dathang);
        total_price = (TextView) findViewById(R.id.tv_totalprice_confirm);
        updateinfo = (Button) findViewById(R.id.confirminfo);
        checkout_momo = (Button) findViewById(R.id.checkout_momo);
        back = (findViewById(R.id.icon_back_confirm));
        name = findViewById(R.id.txt_checkoutname);
        address = findViewById(R.id.txt_checkoutaddress);
        phone = findViewById(R.id.txt_checkoutphonenumber);
        tv_voucher = findViewById(R.id.voucherconfirm);
        isEmpty(address);
        isEmpty(phone);
        user_id = sessionManagement.getSession(); // goi session set user_id
        carts = cartStoge.getallcart(sessionManagement.getSession()); // goi sqlite de lay thong tin gio hang
        // chay vong for den lay gia
        int totalprice = 0;
        for (int i = 0; i < carts.size(); i++) {
            totalprice += Float.parseFloat(carts.get(i).getPrice()) * Integer.parseInt(carts.get(i).getQuality());
            total_price.setText("Tổng tiền : " + NumberFormat.getNumberInstance(Locale.US).format(totalprice));
            amount = String.valueOf(totalprice / 100);
        }
        //set gia tri vao cho adapter
        CheckoutListAdapter checkoutListAdapter = new CheckoutListAdapter(Confirm_checkout.this, carts);
        listView.setAdapter(checkoutListAdapter);
        vouchers = new ArrayList<>();
        vouchercode = new ArrayList<>();
        //call api load voucher
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.voucher().enqueue(new Callback<ArrayList<Voucher>>() {
            @Override
            public void onResponse(Call<ArrayList<Voucher>> call, Response<ArrayList<Voucher>> response) {
                vouchers = response.body();
                for (Voucher voucher : vouchers) {
                    if (voucher.isStatus() == true) {
                        vouchercode.add(voucher.getVoucher_code());
                    }
                }
                stringArrayAdapter = new ArrayAdapter<String>(Confirm_checkout.this, android.R.layout.simple_dropdown_item_1line, vouchercode);
                tv_voucher.setAdapter(stringArrayAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Voucher>> call, Throwable t) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isPhoneNumber(phone.getText().toString()) == false) {
                    phone.setError("This is not phone number");
                    updateinfo.setEnabled(false);
                } else {
                    updateinfo.setEnabled(true);
                }
            }
        });
        tv_voucher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (Voucher voucher : vouchers) {
                    if (voucher.getVoucher_code().equals(tv_voucher.getText().toString())) {
                        if (voucher.isStatus() == false) {
                            tv_voucher.setError("Code không khả dụng");
                            break;
                        }
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_out();
            }
        });
        updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser updateUser = new UpdateUser(phone.getText().toString(), address.getText().toString());
                Retrofit retrofit = APIClient.getClient();
                ApiService apiService = retrofit.create(ApiService.class);
                apiService.Updateuser(sessionManagement.getSession(), updateUser).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
        checkout_momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment();
            }
        });
    }

// khi start activity
    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(Confirm_checkout.this);
        CreateOrder createOrder = new CreateOrder();
        createOrder.takeinformuser(name, phone, address, sessionManagement.getSession(), btn_dathang);
    }
    //ham check so dien thoai
    boolean isPhoneNumber(CharSequence phone) {
        boolean pattern = Pattern.compile("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})").matcher(phone).matches();
        return pattern;
    }
 // ham check nhap trong
    private boolean isEmpty(EditText etText) {
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etText.getText().toString().trim().length() == 0) {
                    etText.setError("Không thể để trống");
                    etText.findFocus();
                    btn_dathang.setEnabled(false);
                    return;

                } else {
                    btn_dathang.setEnabled(true);
                }
            }
        });
        return true;
    }
// hien thi thong bao mua hang thanh cong
    public void contactDialog() {
        Button btn_home;
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopup = getLayoutInflater().inflate(R.layout.popupcheckoutsuccess, null);
        btn_home = contactPopup.findViewById(R.id.trangchu);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Confirm_checkout.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.show();
    }
// goi request MoMo pay
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME, merchantName);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_CODE, merchantCode);
        eventValue.put(MoMoParameterNamePayment.AMOUNT, amount);
        eventValue.put(MoMoParameterNamePayment.DESCRIPTION, description);
        //client Optional
        eventValue.put(MoMoParameterNamePayment.FEE, fee);
        eventValue.put(MoMoParameterNamePayment.MERCHANT_NAME_LABEL, merchantNameLabel);
        eventValue.put(MoMoParameterNamePayment.REQUEST_ID, merchantCode + "-" + UUID.randomUUID().toString());
        eventValue.put(MoMoParameterNamePayment.PARTNER_CODE, "MOMOPMMO20210812");
        JSONObject objExtraData = new JSONObject();
        eventValue.put(MoMoParameterNamePayment.EXTRA_DATA, objExtraData.toString());
        eventValue.put(MoMoParameterNamePayment.REQUEST_TYPE, "payment");
        eventValue.put(MoMoParameterNamePayment.LANGUAGE, "vi");
        eventValue.put(MoMoParameterNamePayment.EXTRA, "");
        //Request momo app
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }

// tra Result cua momo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                if (data.getIntExtra("status", -1) == 0) {


                    if (data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:
                        check_out();
                        System.out.println("OK ");
                    } else {

                    }
                } else if (data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null ? data.getStringExtra("message") : "Thất bại";

                } else if (data.getIntExtra("status", -1) == 2) {

                } else {

                }
            } else {

            }
        } else {
        }
    }
//tao ham check out
    public void check_out() {
        String id_voucher = "";
        for (Voucher voucher : vouchers) {
            if (voucher.getVoucher_code().equals(tv_voucher.getText().toString())) {
                if (voucher.isStatus() == true) {
                    id_voucher = tv_voucher.getText().toString();
                    break;
                }
            }
        }
        ArrayList<listProduct> listProducts = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            listProducts.add(new listProduct(user_id, id_voucher, carts.get(i).getProduct_id(), Integer.parseInt(carts.get(i).getQuality()), Float.parseFloat(carts.get(i).getPrice())));
        }
        Order orders = new Order(listProducts);
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.order(orders).enqueue(new Callback<Array>() {
            @Override
            public void onResponse(Call<Array> call, Response<Array> response) {
                int code = response.code();
                if (code == 200) {
                    CreateOrder createOrder = new CreateOrder();
                    createOrder.sendmail(user_id, Confirm_checkout.this);
                    contactDialog();
                    cartStoge.deleteAllCart(user_id);
                } else {
                    Toast.makeText(Confirm_checkout.this,"Sản phầm đã hết hàng",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Array> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

}
