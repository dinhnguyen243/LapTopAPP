package com.example.laptopapp.Activity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptopapp.Adapter.promotionAdapter;
import com.example.laptopapp.Data.loadEvent;
import com.example.laptopapp.Model.Event;
import com.example.laptopapp.Model.Id_token;
import com.example.laptopapp.Model.Login;
import com.example.laptopapp.Model.User;
import com.example.laptopapp.R;
import com.example.laptopapp.Session.SessionManagement;
import com.example.laptopapp.api.APIClient;
import com.example.laptopapp.api.ApiService;
import com.example.laptopapp.fragment.frm_billdetail;
import com.example.laptopapp.fragment.frm_contact;
import com.example.laptopapp.fragment.frm_detailspromotion;
import com.example.laptopapp.fragment.frm_history;
import com.example.laptopapp.fragment.frm_homepage;
import com.example.laptopapp.fragment.frm_listnewproduct;
import com.example.laptopapp.fragment.frm_notification;
import com.example.laptopapp.fragment.frm_product;
import com.example.laptopapp.fragment.frm_promotion;
import com.example.laptopapp.fragment.frm_search;
import com.example.laptopapp.menu.DrawerAdapter;
import com.example.laptopapp.menu.DrawerItem;
import com.example.laptopapp.menu.SimpleItem;
import com.example.laptopapp.menu.SpaceItem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_NEWPRODUCT = 1;
    private static final int POS_LISTPRODUCT = 2;
    private static final int POS_PROMOTION = 3;
    private static final int POS_HISTORY = 4;
    private static final int POS_CONTACT = 5;
    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    static public TextView label;
    MenuItem menuItem;
    // badge text view
    TextView badgeCounter;
    private List<Event> event;
    // change the number to see badge in action
    private int pendingNotifications = 0;
    private static final int RC_SIGN_IN = 999;
    private GoogleSignInClient mGoogleSignInClient;
    EditText tv_searchname;
    ImageView search_icon, icon_cart;
    TextView signoutButton;
    SignInButton signInButton;
    ImageView img;
    TextView tv_name;
    Login user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_searchname = findViewById(R.id.tv_searchname);
        icon_cart = findViewById(R.id.icon_cart);
        search_icon = findViewById(R.id.icon_search_ek1);
        label = findViewById(R.id.label);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contactDialog();
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();
        // tim text view khach hang va hinh avatar de o day vi no nam trong menu_left_drawer
        tv_name = findViewById(R.id.txt_Customername);
        img = findViewById(R.id.img_avatar);
        // go to cart
        icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });
        //search
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("name", tv_searchname.getText().toString());
                frm_product product = new frm_product();
                transaction.replace(R.id.container, product);
                product.setArguments(bundle);
                slidingRootNav.closeMenu();
                transaction.commit();
                label.setText("Tìm Kiếm ");
            }
        });


        // login google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail().requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        SignInButton signout = findViewById(R.id.sign_out_button);
        signoutButton = (TextView) signout.getChildAt(0);
        signoutButton.setText("Sign Out");
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });
// drawer cho menu
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();
        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_NEWPRODUCT),
                createItemFor(POS_LISTPRODUCT),
                createItemFor(POS_PROMOTION),
                createItemFor(POS_HISTORY),
                createItemFor(POS_CONTACT),
                new SpaceItem(48)));
        adapter.setListener(this);
        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setSelected(POS_DASHBOARD);
    }
// chuyen giua cac fragmnet
    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                frm_homepage homepage = new frm_homepage();
                transaction.replace(R.id.container, homepage);
                transaction.addToBackStack("Home");
                slidingRootNav.closeMenu();
                label.setText("Trang Chủ");
                transaction.commit();
                break;
            case 1:
                frm_listnewproduct listnewproduct = new frm_listnewproduct();
                transaction.replace(R.id.container, listnewproduct);
                transaction.addToBackStack("Home");
                slidingRootNav.closeMenu();
                transaction.commit();
                label.setText("Máy Mới");
                break;
            case 2:
                frm_promotion promotion = new frm_promotion();
                transaction.replace(R.id.container, promotion);
                transaction.addToBackStack("Khuyenmai");
                slidingRootNav.closeMenu();
                transaction.commit();
                label.setText("Khuyến Mãi");
                break;
            case 3:
                Bundle bundle = new Bundle();
                bundle.putInt("brand", -1);
                frm_product product = new frm_product();
                product.setArguments(bundle);
                transaction.replace(R.id.container, product);
                transaction.addToBackStack("Home");
                slidingRootNav.closeMenu();
                transaction.commit();
                label.setText("Danh Sách");
                break;
            case 4:
                frm_history history = new frm_history();
                transaction.replace(R.id.container, history);
                transaction.addToBackStack("Home");
                slidingRootNav.closeMenu();
                transaction.commit();
                label.setText("Lịch Sử");
                break;
            case 5:
                frm_contact contact = new frm_contact();
                transaction.replace(R.id.container, contact);
                transaction.addToBackStack("Home");
                slidingRootNav.closeMenu();
                transaction.commit();
                label.setText("Liên Hệ");
                break;
            case 8:
                finish();
        }
    }
// show fragment promotion va search
    public void showFragment(String show, int value) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show.equals("promotion") && value == -2) {
            frm_promotion promotion = new frm_promotion();
            transaction.replace(R.id.container, promotion);
            transaction.commit();
            label.setText("Khuyến Mãi");
        } else if (show.equals("product") && value != -2) {
            Bundle bundle = new Bundle();
            bundle.putInt("brand", value);
            System.out.println(value);
            frm_product product = new frm_product();
            transaction.replace(R.id.container, product);
            product.setArguments(bundle);
            transaction.commit();
            label.setText("Thương Hiệu Nổi Bật");

        }
    }
// show fragment detalis promotion
    public void showdetailspromotion(String id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id_voucher", id);
        frm_detailspromotion detalispromotion = new frm_detailspromotion();
        transaction.replace(R.id.container, detalispromotion);
        detalispromotion.setArguments(bundle);
        transaction.commit();
        label.setText("Chi tiết Voucher");
    }
// show detail bill
    public void showdetailsbill(String id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("bill_id", id);
        frm_billdetail detalispromotion = new frm_billdetail();
        transaction.replace(R.id.container, detalispromotion);
        detalispromotion.setArguments(bundle);
        transaction.commit();
        label.setText("Chi tiết lịch sử");
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
// show pop up quang cao
    public void contactDialog() {
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View contactPopup = getLayoutInflater().inflate(R.layout.popup, null);
        dialogBuilder.setView(contactPopup);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.trongsuot);
        dialog.show();

    }
// tao nut chuong thong bao
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        menuItem = menu.findItem(R.id.nav_notification);
        // check if any pending notification
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // load thong bao event
        event = new ArrayList<>();
        Retrofit retrofit = APIClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.event().enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                event = response.body();
                pendingNotifications = event.size();
                if (pendingNotifications == 0) {
                    // if no pending notification remove badge
                    menuItem.setActionView(null);
                    menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            frm_notification notification = new frm_notification();
                            transaction.replace(R.id.container, notification);
                            transaction.commit();
                            label.setText("Thông báo");
                            return false;
                        }
                    });
                } else {
                    // if notification than set the badge icon layout
                    menuItem.setActionView(R.layout.notification_badge);
                    // get the view from the nav item
                    View view = menuItem.getActionView();
                    // get the text view of the action view for the nav item
                    badgeCounter = view.findViewById(R.id.badge_counter);
                    // set the pending notifications value
                    badgeCounter.setText(String.valueOf(pendingNotifications));
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            frm_notification notification = new frm_notification();
                            transaction.replace(R.id.container, notification);
                            transaction.commit();
                            label.setText("Thông báo");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {

            }
        });
        return super.onCreateOptionsMenu(menu);
    }
// goi tra ve luc dang nhap
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
// xu ly dang nhap
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            System.out.println(account.getIdToken());
            tv_name.setText(account.getDisplayName());
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(account.getPhotoUrl().toString());
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.connect();
                int resCode = httpConn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = httpConn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    img.setImageBitmap(bitmap);
                    img.setMinimumHeight(128);
                    img.setMinimumHeight(128);
                }
            } catch (Exception e) {
                System.out.println(e);
                img.setImageResource(R.drawable.logo);
            }
            Retrofit retrofit = APIClient.getClient();
            ApiService apiService = retrofit.create(ApiService.class);
            Id_token id_token = new Id_token(account.getIdToken());
            apiService.login(id_token).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    user = response.body();
                    try {
                        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                        sessionManagement.saveSession(user.getUser().get_id());
                    }catch (Exception e){

                    }
                    try{
                        if (user.getUser().isStatus() == false) {
                            signOut();
                            new AlertDialog.Builder(MainActivity.this).setTitle("Your account has been locked").setMessage("you want to login with another account?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    signIn();
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    signOut();
                                    return;
                                }
                            }).show();
                        }
                    }catch (Exception e){

                    }

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    System.out.println("loi " + t);
                }
            });
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println(e);

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {

    }
    // ham dang nhap
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        signInButton.setVisibility(View.INVISIBLE);
        signoutButton.setVisibility(View.VISIBLE);
    }
//ham logout
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                        sessionManagement.removeSession();
                        tv_name.setText("");
                        img.setImageResource(R.drawable.logo);
                        signInButton.setVisibility(View.VISIBLE);
                        signoutButton.setVisibility(View.INVISIBLE);
                    }
                });
    }
// khi start activity check qua thoi gian dang nhap va account co bi khoa k ?
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            tv_name.setText(personName);
            System.out.println(acct.getIdToken());
            Uri personPhoto = acct.getPhotoUrl();
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(acct.getPhotoUrl().toString());
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.connect();
                int resCode = httpConn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = httpConn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    img.setImageBitmap(bitmap);
                    img.setMinimumHeight(128);
                    img.setMinimumHeight(128);
                }
            } catch (Exception e) {
                System.out.println(e);
                img.setImageResource(R.drawable.logo);
            }
            Retrofit retrofit = APIClient.getClient();
            ApiService apiService = retrofit.create(ApiService.class);
            Id_token id_token = new Id_token(acct.getIdToken());
            apiService.login(id_token).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    user = response.body();
                    SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                    try {
                        sessionManagement.saveSession(user.getUser().get_id());
                        if (user.getUser().isStatus() == false) {
                            signOut();
                            new AlertDialog.Builder(MainActivity.this).setTitle("Tài khoản của bạn đã bị khóa").setMessage("Bạn có muốn đăng nhập tài khác?").setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    signIn();
                                }
                            }).setNegativeButton("Để sau", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                        }
                        System.out.println("success");
                    } catch (Exception e) {
                        signOut();
                        new AlertDialog.Builder(MainActivity.this).setTitle("tài khoản của bạn đã quá thời gian đăng nhập").setMessage("Bạn có muốn đăng nhập lại không?").setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signIn();
                            }
                        }).setNegativeButton("Để sau", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).show();
                    }

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    System.out.println("loi " + t);
                }
            });
            signInButton.setVisibility(View.INVISIBLE);
            signoutButton.setVisibility(View.VISIBLE);
        } else {
            signInButton.setVisibility(View.VISIBLE);
            signoutButton.setVisibility(View.INVISIBLE);
        }

    }
}