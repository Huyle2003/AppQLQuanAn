package com.example.appqlquanan.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appqlquanan.R;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private Button btnaddban;
    private ListView lvtable;
    EditText editaddban;
    private ArrayList<String> arrlist;
    private ArrayAdapter<String> adapter;
    SQLiteDatabase mydatabase;

    // ViewModel
    private SlideshowViewModel slideshowViewModel;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Khởi tạo ViewModel
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        // Inflate layout của Fragment
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // Liên kết các phần tử giao diện bằng findViewById trên view
        btnaddban = view.findViewById(R.id.btnaddban);
        lvtable = view.findViewById(R.id.lvtableban);
        editaddban = view.findViewById(R.id.editaddban);

        // Thiết lập ArrayList và ArrayAdapter cho ListView
        arrlist = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrlist);
        lvtable.setAdapter(adapter);
        mydatabase = requireContext().openOrCreateDatabase("qlqa.db", Context.MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS tbban (maban TEXT PRIMARY KEY, tenban TEXT)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        btnaddban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maban = "MB" + (arrlist.size() + 1); // Tạo mã bàn theo thứ tự
                String tenban = editaddban.getText().toString();
                ContentValues myvalue = new ContentValues();
                myvalue.put("maban", maban);
                myvalue.put("tenban", tenban);
                String msg = "";
                if (mydatabase.insert("tbban", null, myvalue) == -1) {
                    msg = "Fail to Insert Record!";
                } else {
                    msg = "Insert record Successfully";
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();

                arrlist.clear();  // Xóa danh sách cũ
                Cursor c = mydatabase.query("tbban", null, null, null, null, null, null);
                while (c.moveToNext()) {
                    String data = "Mã bàn: " + c.getString(0) + ", Tên bàn: " + c.getString(1); // Lấy maban và tenban
                    arrlist.add(data);  // Thêm vào danh sách
                }
                c.close();
                adapter.notifyDataSetChanged();
            }
        });
        lvtable.setOnItemLongClickListener((parent, view1, position, id) -> {
            // Hiển thị AlertDialog khi nhấn giữ
            new AlertDialog.Builder(getContext())
                    .setTitle("Chọn thao tác")
                    .setMessage("Vui lòng chọn !")
                    .setNegativeButton("Xóa", (dialog, which) -> {
                        // Xóa nhân viên khỏi cơ sở dữ liệu
                        String selectedMaban = arrlist.get(position).replace("Mã bàn: ", "").split(",")[0];
                        deleteban(selectedMaban);
                        arrlist.clear();
                        Cursor c = mydatabase.query("tbban", null, null, null, null, null, null);
                        while (c.moveToNext()) {
                            String data = "Mã bàn: " + c.getString(0) + ", Tên bàn: " + c.getString(1);
                            arrlist.add(data);
                        }
                        c.close();
                        adapter.notifyDataSetChanged();

                    })
                    .setNeutralButton("Hủy", null)
                    .show();
            return true;
        });

        return view;
    }
    private void deleteban(String maban) {
        // Hàm xóa nhân viên từ cơ sở dữ liệu dựa vào mã bàn
        mydatabase.delete("tbban", "maban=?", new String[]{maban});
        Toast.makeText(getContext(), "Đã xóa bàn", Toast.LENGTH_SHORT).show();
    }

    private void addItemToList() {
        // Thêm dữ liệu vào ArrayList và cập nhật adapter
        arrlist.add("New Item " + (arrlist.size() + 1));
        adapter.notifyDataSetChanged();
    }
}
