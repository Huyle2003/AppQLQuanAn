package com.example.appqlquanan.ui.nhanvien;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appqlquanan.MainActivity;
import com.example.appqlquanan.R;
import com.example.appqlquanan.trangchu;

import java.util.ArrayList;

public class addnv extends AppCompatActivity {
    Button btnback, btnthemnv;
    RadioButton rdbtnql,rdbtnpv,rdbtndb, rdbtnnam,rdbtnnu,rdbtnkhac;
    EditText editname, editttk, editpassnv, editsdt, editns,editmnv;
    SQLiteDatabase mydatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_addnv);
        editname = findViewById(R.id.editname);
        editttk= findViewById(R.id.editttk);
        editpassnv= findViewById(R.id.editpassnv);
        editsdt= findViewById(R.id.editsdt);
        editmnv= findViewById(R.id.editmnv);
        editns= findViewById(R.id.editns);
        rdbtnql= findViewById(R.id.rdbtnql);
        rdbtnpv= findViewById(R.id.rdbtnpv);
        rdbtndb= findViewById(R.id.rdbtndb);
        rdbtnnam= findViewById(R.id.rdbtnnam);
        rdbtnnu= findViewById(R.id.rdbtnnu);
        rdbtnkhac= findViewById(R.id.rdbtnkhac);
        btnback= findViewById(R.id.btnback);
        btnthemnv= findViewById(R.id.btnthemnv);
        mydatabase = openOrCreateDatabase("qlqa.db", Context.MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS tbnv (manv TEXT PRIMARY KEY, hoten TEXT,tenTaiKhoan TEXT,matKhau TEXT,soDienThoai TEXT,namSinh INTEGER,quyen TEXT,gioiTinh TEXT)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        btnthemnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các trường trong giao diện
                String manv = editname.getText().toString();
                String hoten = editname.getText().toString();
                String tenTaiKhoan = editttk.getText().toString();
                String matKhau = editpassnv.getText().toString();
                String soDienThoai = editsdt.getText().toString();
                String namSinh = editns.getText().toString();

                // Lấy quyền
                String quyen = "";
                if (rdbtnql.isChecked()) {
                    quyen = "Quản lý";
                } else if (rdbtnpv.isChecked()) {
                    quyen = "Phục vụ";
                } else if (rdbtndb.isChecked()) {
                    quyen = "Đầu bếp";
                }

                // Lấy giới tính
                String gioiTinh = "";
                if (rdbtnnam.isChecked()) {
                    gioiTinh = "Nam";
                } else if (rdbtnnu.isChecked()) {
                    gioiTinh = "Nữ";
                } else if (rdbtnkhac.isChecked()) {
                    gioiTinh = "Khác";
                }

                ContentValues myvalue = new ContentValues();
                myvalue.put("manv", manv);
                myvalue.put("hoten", hoten);
                myvalue.put("tenTaiKhoan", tenTaiKhoan);
                myvalue.put("matKhau", matKhau);
                myvalue.put("soDienThoai", soDienThoai);
                myvalue.put("namSinh", namSinh);
                myvalue.put("quyen", quyen);
                myvalue.put("gioiTinh", gioiTinh);

                String msg = "";
                if (mydatabase.insert("tbnv", null, myvalue) == -1) {
                    msg = "Fail to Insert Record!";
                } else {
                    msg = "Insert record Successfully";
                }
                Toast.makeText(addnv.this, msg, Toast.LENGTH_SHORT).show();



                // Kiểm tra các trường bắt buộc có dữ liệu
                if (hoten.isEmpty() || tenTaiKhoan.isEmpty() || matKhau.isEmpty() || soDienThoai.isEmpty() || namSinh.isEmpty()) {
                    Toast.makeText(addnv.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }




            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(addnv.this, NhanvienFragment.class);
                startActivity(myIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}