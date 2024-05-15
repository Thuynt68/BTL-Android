package com.example.apptruyen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apptruyen.data.DatabaseDocTruyen;
import com.example.apptruyen.model.TaiKhoan;
import com.example.apptruyen.data.DatabaseDocTruyen;
import com.example.apptruyen.model.TaiKhoan;

public class ManDangKy extends AppCompatActivity {


    EditText edtDKTaiKhoan,edtDKMatKhau,edtDKEmail;
    Button btnDKDangKy,btnDKDangNhap;

    DatabaseDocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_ky);

        AnhXa();

        databaseDocTruyen = new DatabaseDocTruyen(this);

        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtDKTaiKhoan.getText().toString();
                String matkhau = edtDKMatKhau.getText().toString();
                String email = edtDKEmail.getText().toString();

                TaiKhoan taikhoan1 = CreateTaiKhoan();
                TaiKhoan taiKhoanTonTai = databaseDocTruyen.getTaiKhoanByTaiKhoan(taikhoan);
                if (taiKhoanTonTai != null) {
                    Toast.makeText(ManDangKy.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    Log.e("Thông báo : ", "Tài khoản đã tồn tại");
                }
                else if(taikhoan.equals("") || matkhau.equals("") || email.equals("")){
                    Toast.makeText(ManDangKy.this,"Bạn chưa nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    Log.e("Thông báo : ","Bạn chưa nhập đầy đủ thông tin");
                }
                else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(ManDangKy.this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(matkhau.length() < 8){
                    Toast.makeText(ManDangKy.this, "Mật khẩu chứa ít nhất 8 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                    //Nếu đầy đủ thông tin
                else{
                        databaseDocTruyen.AddTaiKhoan(taikhoan1);
                        Toast.makeText(ManDangKy.this,"Đăng ký thành công ",Toast.LENGTH_SHORT).show();
                    }
                }
        });
        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private TaiKhoan CreateTaiKhoan(){
        String taikhoan = edtDKTaiKhoan.getText().toString();
        String matkhau = edtDKMatKhau.getText().toString();
        String email = edtDKEmail.getText().toString();
        int phanquyen = 1;

        TaiKhoan tk = new TaiKhoan(taikhoan,matkhau,email,phanquyen);

        return tk;
    }
    private void AnhXa() {
        edtDKEmail = findViewById(R.id.dkEmail);
        edtDKMatKhau = findViewById(R.id.dkMatKhau);
        edtDKTaiKhoan = findViewById(R.id.dkTaiKhoan);
        btnDKDangKy = findViewById(R.id.dkDangKy);
        btnDKDangNhap = findViewById(R.id.dkDangNhap);

    }
}