package com.example.appqlquanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edittk, editpass;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        edittk = findViewById(R.id.edittk);
        editpass = findViewById(R.id.editpass);
        btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edittk.getText().toString();
                String pass = editpass.getText().toString();
                if(user.equals("admin") && pass.equals("1")){
                    Toast.makeText(MainActivity.this,"Chúc mừng, bạn đã đăng nhập thành công", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(MainActivity.this, trangchu.class);
                    startActivities(new Intent[]{myIntent});

                }else{
                    Toast.makeText(MainActivity.this,"Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}