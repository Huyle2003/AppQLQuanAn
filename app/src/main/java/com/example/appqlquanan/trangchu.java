package com.example.appqlquanan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appqlquanan.databinding.ActivityTrangchuBinding;

public class trangchu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityTrangchuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrangchuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarTrangchu.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_thongke, R.id.nav_banan, R.id.nav_thucdon,R.id.nav_nhanvien,R.id.nav_luong, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_trangchu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        // Thêm OnDestinationChangedListener để cập nhật tiêu đề trên toolbar
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_thucdon) {
                getSupportActionBar().setTitle("Thực đơn");
            } else if (destination.getId() == R.id.nav_home) {
                getSupportActionBar().setTitle("Trang chủ");
            } else if (destination.getId() == R.id.nav_thongke) {
                getSupportActionBar().setTitle("Thống kê");
            } else if (destination.getId() == R.id.nav_banan) {
                getSupportActionBar().setTitle("Bàn ăn");
            } else if (destination.getId() == R.id.nav_nhanvien) {
            getSupportActionBar().setTitle("Nhân viên");
            }else if (destination.getId() == R.id.nav_luong) {
                getSupportActionBar().setTitle("Nhân viên");
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trangchu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_trangchu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}