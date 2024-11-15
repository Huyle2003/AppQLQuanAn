package com.example.appqlquanan.ui.nhanvien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import com.example.appqlquanan.R;

import java.util.ArrayList;

public class NhanvienFragment extends Fragment {
    Button btnaddnv;
    ListView lvtablenv;
    ArrayList<String> arrlistnv;
    ArrayAdapter<String> adapter;
    SQLiteDatabase mydatabase;

    public NhanvienFragment() {
        // Constructor mặc định cần thiết
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Nạp giao diện cho fragment này
        View view = inflater.inflate(R.layout.fragment_nhanvien, container, false);

        // Khởi tạo các view
        btnaddnv = view.findViewById(R.id.btnaddban);
        lvtablenv = view.findViewById(R.id.lvtableban);
        arrlistnv = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrlistnv);
        lvtablenv.setAdapter(adapter);

        // Mở cơ sở dữ liệu
        mydatabase = getActivity().openOrCreateDatabase("qlqa.db", getContext().MODE_PRIVATE, null);

        // Tải dữ liệu từ cơ sở dữ liệu
        loadData();

        // Đặt listener cho nút để mở activity mới
        btnaddnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), addnv.class);
                startActivity(myIntent);
            }
        });

        lvtablenv.setOnItemLongClickListener((parent, view1, position, id) -> {
            // Hiển thị AlertDialog khi nhấn giữ
            new AlertDialog.Builder(getContext())
                    .setTitle("Chọn thao tác")
                    .setMessage("Bạn muốn làm gì với nhân viên này?")
                    .setNegativeButton("Xóa", (dialog, which) -> {
                        // Xóa nhân viên khỏi cơ sở dữ liệu
                        String selectedManv = arrlistnv.get(position).split("\n")[0].replace("Mã NV: ", "");
                        deleteNhanVien(selectedManv);
                        loadData(); // Tải lại dữ liệu sau khi xóa
                    })
                    .setNeutralButton("Hủy", null)
                    .show();
            return true;
        });

        return view;
    }
    private void deleteNhanVien(String manv) {
        // Hàm xóa nhân viên từ cơ sở dữ liệu dựa vào mã nhân viên
        mydatabase.delete("tbnv", "manv=?", new String[]{manv});
        Toast.makeText(getContext(), "Đã xóa nhân viên", Toast.LENGTH_SHORT).show();
    }


    private void loadData() {
        // Xóa danh sách trước khi nạp dữ liệu mới
        arrlistnv.clear();

        // Truy vấn bảng tbnv để lấy dữ liệu
        Cursor cursor = mydatabase.query("tbnv", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String manv = cursor.getString(cursor.getColumnIndex("manv"));
                @SuppressLint("Range") String hoten = cursor.getString(cursor.getColumnIndex("hoten"));
                @SuppressLint("Range") String tenTaiKhoan = cursor.getString(cursor.getColumnIndex("tenTaiKhoan"));
                @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex("matKhau"));
                @SuppressLint("Range") String soDienThoai = cursor.getString(cursor.getColumnIndex("soDienThoai"));
                @SuppressLint("Range") String namSinh = cursor.getString(cursor.getColumnIndex("namSinh"));
                @SuppressLint("Range") String quyen = cursor.getString(cursor.getColumnIndex("quyen"));
                @SuppressLint("Range") String gioiTinh = cursor.getString(cursor.getColumnIndex("gioiTinh"));

                // Thêm thông tin nhân viên vào danh sách (có thể định dạng lại nếu cần)
                arrlistnv.add(
                        "Mã NV: " + manv + "\n" +
                                "Họ tên: " + hoten + "\n" +
                                "Tên tài khoản: " + tenTaiKhoan + "\n" +
                                "Mật khẩu: " + matKhau + "\n" +
                                "Số điện thoại: " + soDienThoai + "\n" +
                                "Năm sinh: " + namSinh + "\n" +
                                "Quyền: " + quyen + "\n" +
                                "Giới tính: " + gioiTinh + "\n" +
                                "-------------------------"
                );

            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

        // Thông báo adapter để cập nhật lại ListView
        adapter.notifyDataSetChanged();
    }
}
