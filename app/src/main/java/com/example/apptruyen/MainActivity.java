package com.example.apptruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.apptruyen.adapter.adapterTruyen;
import com.example.apptruyen.adapter.adapterchuyenmuc;
import com.example.apptruyen.adapter.adapterthongtin;
import com.example.apptruyen.data.DatabaseDocTruyen;
import com.example.apptruyen.model.TaiKhoan;
import com.example.apptruyen.model.Truyen;
import com.example.apptruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listViewNew, listViewThongTin;
    DrawerLayout drawerLayout;
    String email, tentaikhoan;

    ArrayList <Truyen> TruyenArraylist;

    adapterTruyen adapterTruyen;
    ArrayList<chuyenmuc> chuyenmucArrayList;

    ArrayList<TaiKhoan> taiKhoanArrayList;
    DatabaseDocTruyen databaseDocTruyen;
    adapterthongtin adapterthongtin;
    adapterchuyenmuc adapterchuyenmuc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseDocTruyen = new DatabaseDocTruyen(this);

        // nhận dữ liệu ở màn đăng nhập gửi
        Intent intentpq = getIntent();
        int i = intentpq.getIntExtra("phanq",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        AnhXa();
        ActionBar();
        ActionViewFlipper();

        // bat su kien click item
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ManNoiDung.class);

                String tent = TruyenArraylist.get(position).getTenTruyen();
                String noidung = TruyenArraylist.get(position).getNoiDung();
                intent.putExtra("tentruyen", tent);
                startActivity(intent);
            }
        });

         // bat click item cho listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    if(i == 2) {

                    }else{
                        Toast.makeText(MainActivity.this, "Bạn không có quyền đăng bài", Toast.LENGTH_SHORT).show();
                        Log.e("Đăng bài: ", "Bạn không có quyền");
                    }
                }
                else if(position == 1){

                }
                else if(position == 2){
                    finish();
                }
            }
        });


    }

    // Thanh actionbar với toolbar
//
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    // phương thức chho chạy quảng cáo với ViewFilpper
    private void ActionViewFlipper() {
        // mảng chứa tấm ảnh cho quảng cáo;
        ArrayList<String> mangquangcao = new ArrayList<>();
        // add ảnh vào mảng
        mangquangcao.add("https://images.toplist.vn/images/800px/cau-chuyen-co-tich-viet-nam-hay-nhat-43873.jpg");
        mangquangcao.add("https://images.toplist.vn/images/800px/cay-tre-tram-dot-43889.jpg");
        mangquangcao.add("https://images.toplist.vn/images/800px/hon-truong-ba-da-hang-thit-43888.jpg");
        mangquangcao.add("https://images.toplist.vn/images/800px/su-tich-da-trang-xe-cat-bien-dong-43881.jpg");
        mangquangcao.add("https://images.toplist.vn/images/800px/su-tich-cay-vu-sua-43890.jpg");
        mangquangcao.add("https://images.toplist.vn/images/800px/tich-chu-43894.jpg");
        mangquangcao.add("https://toplist.vn/top-list/cau-chuyen-co-tich-viet-nam-hay-nhat-4726.htm");

        // thực hiện vòng lặp for gắn ảnh vào Imageview, rồi từ ImageView lên app

        for(int i = 0; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            // Sử dụng hàm thư viện Piscasso
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            // phương thức chỉnh tấm hình vừa khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Thêm ảnh từ imageview vào ViewFlipper
            viewFlipper.addView(imageView);
        }
        //Thiết lập tự chạy cho viewFlipper chạy trong 5 giây
        viewFlipper.setFlipInterval(5000);
        //viewFlipper run
        viewFlipper.setAutoStart(true);
        //Gọi animation cho in và out . Animation giúp cho nó biến dổi giữa các giao diện hình ảnh
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        //Gọi animation vào ViewFlippẻ
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    // phương thức ánh xạ
    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        listViewNew =  findViewById(R.id.listviewnew);
        listView = findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin = findViewById(R.id.listviewthongtin);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        TruyenArraylist  = new ArrayList<>();

        Cursor cursor1 = databaseDocTruyen.getData1();
        while (cursor1.moveToNext()){
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArraylist.add(new Truyen(id, tentruyen, noidung, anh, id_tk));

            adapterTruyen = new adapterTruyen(getApplicationContext(), TruyenArraylist);
            listViewNew.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();
        // Thoong tin
        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan, email));
        adapterthongtin = new adapterthongtin(this, R.layout.navigation_thongtin, taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        // chuyen muc
        chuyenmucArrayList = new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Đăng bài", R.drawable.ic_baseline_post_add_24));
        chuyenmucArrayList.add(new chuyenmuc("Thông tin", R.drawable.ic_baseline_face_24));
        chuyenmucArrayList.add(new chuyenmuc("Đăng xuất", R.drawable.ic_logout));

        adapterchuyenmuc = new adapterchuyenmuc(this, R.layout.chuyenmuc, chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);
    }
    //Nạp một menu tìm kiếm vào actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    //Bắt sự kiện khi click vào search
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this,ManTimKiem.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}