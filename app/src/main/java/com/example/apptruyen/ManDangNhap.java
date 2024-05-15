package com.example.apptruyen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apptruyen.data.DatabaseDocTruyen;
import com.example.apptruyen.data.DatabaseDocTruyen;

public class ManDangNhap extends AppCompatActivity {

    // tạo biến cho màn đăng nhập
    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;


    // để tạo đối tượng cho database đọc truyện
    DatabaseDocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);
        AnhXa();

        databaseDocTruyen = new DatabaseDocTruyen(this);
        // tạo sự kiện click button chuyển sang màn hình đăng ký với intent
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManDangNhap.this,ManDangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                // sử dụng con trỏ để lấy dữ liệu gọi tới, get Data để lấy tất cả tài khoản ở database
                Cursor cursor = databaseDocTruyen.getData();
                // thực hiện vòng lặp để lấy dữ liệu từ cursor với movetoNext() di chuyển tiếp
//                while (cursor.moveToNext()){
//                    String datatentaikhoan = cursor.getString(1);
//                    String datamatkhau = cursor.getString(2);
//
//                    if(datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)){
//                        int phanquyen = cursor.getInt(4);
//                        int idd = cursor.getInt(0);
//                        String tentk = cursor.getString(1);
//                        String email = cursor.getString(3);
//
//                        // chuyển màn hình qua MainActivity
//                        Intent intent = new Intent(ManDangNhap.this,MainActivity.class);
//
//                        // gửi dữ liệu qua Activity là MainActivity
//                        intent.putExtra("phanq",phanquyen);
//                        intent.putExtra("idd",idd);
//                        intent.putExtra("email",email);
//                        intent.putExtra("tentaikhoan",tentk);
//
//                        startActivity(intent);
//                        Log.e("Đăng nhập : ","Thành công");
//                    }
//                    else{
//                        Log.e("Đăng nhập : ","Không Thành công");
//                    }
//                }
                boolean found = false; // Biến cờ để kiểm tra xem có tài khoản phù hợp không

                while (cursor.moveToNext()) {
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)) {
                        found = true; // Đánh dấu rằng đã tìm thấy tài khoản phù hợp
                        int phanquyen = cursor.getInt(4);
                        int idd = cursor.getInt(0);
                        String tentk = cursor.getString(1);
                        String email = cursor.getString(3);

                        // chuyển màn hình qua MainActivity
                        Intent intent = new Intent(ManDangNhap.this, MainActivity.class);

                        // gửi dữ liệu qua Activity là MainActivity
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);

                        startActivity(intent);
                        Log.e("Đăng nhập : ", "Thành công");
                        break; // Kết thúc vòng lặp khi đã tìm thấy tài khoản
                    }
                }

                // Kiểm tra xem có tài khoản phù hợp hay không
                if (!found) {
                    Log.e("Đăng nhập : ", "Không Thành công");
                    Toast.makeText(ManDangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

                cursor.moveToFirst();
                cursor.close();
            }
        });
    }
    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.taikhoan);
        edtMatKhau = findViewById(R.id.matkhau);
        btnDangNhap = findViewById(R.id.dangnhap);
        btnDangKy = findViewById(R.id.dangky);
    }
}