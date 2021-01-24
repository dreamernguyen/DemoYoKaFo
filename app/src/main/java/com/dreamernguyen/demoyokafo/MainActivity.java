package com.dreamernguyen.demoyokafo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    BottomNavigationView bottomNavigationView;
    BottomNavAdapter viewPagerAdapter;
    ViewPager viewPager;
    private String TAG = "Teooo mainactivity";
    private String serverURL = "http://192.168.31.45:3000";
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(serverURL);
        }catch (URISyntaxException e){
            throw new RuntimeException(e);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);



//        Call<TinNhan> call = ApiService.apiService.getTest();
//        call.enqueue(new Callback<TinNhan>() {
//            @Override
//            public void onResponse(Call<TinNhan> call, Response<TinNhan> response) {
//                TinNhan tinNhan = response.body();
//                Log.d(TAG, "onResponse: "+tinNhan.getNoiDung());
//            }
//
//            @Override
//            public void onFailure(Call<TinNhan> call, Throwable t) {
//                Log.d(TAG, "onFailure: "+t.getMessage());
//            }
//        });

        mSocket.connect();
        if(mSocket.connected()){
            Log.d("-----", "onCreate: kết nối");
        }else {
            Log.d("----", "onCreate: Chưa kết nối");
        }
//        mSocket.on("nhanThongBao", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Toast.makeText(getApplicationContext(), args[0].toString(), Toast.LENGTH_LONG).show();
//                Log.d("----", "onCreate: Chưa kết nối"+args[0]);
////                mSocket.emit("nhanThongBao");
//            }
//        });
        test();

        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home :
                        viewPager.setCurrentItem(0,false);
                        HomeFragment homeFragment = (HomeFragment) viewPager.getAdapter().instantiateItem(viewPager,0);
                        homeFragment.reloadData();
                        break;
                    case R.id.action_message :
                        viewPager.setCurrentItem(1,false);
                        MessageFragment messageFragment = (MessageFragment) viewPager.getAdapter().instantiateItem(viewPager,1);
                        messageFragment.reloadData();
                        break;
                    case R.id.action_notification :
                        viewPager.setCurrentItem(2,false);
                        NotificationFragment notificationFragment = (NotificationFragment) viewPager.getAdapter().instantiateItem(viewPager,2);
                        notificationFragment.reloadData();
                        break;
                    case R.id.action_profile :
                        viewPager.setCurrentItem(3,false);
                        break;
                }
                return true;
            }
        });
        viewPagerAdapter = new BottomNavAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_message).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_notification).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if(bottomNavigationView.getMenu().findItem(R.id.action_home).isChecked()){
            if(backPressedTime + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
                return;
            }else {
                Toast.makeText(this, "Nhấn back thêm 1 lần nữa để thoát", Toast.LENGTH_SHORT).show();
            }
            backPressedTime = System.currentTimeMillis();
        }
        else {
            viewPager.setCurrentItem(0);
            HomeFragment homeFragment = (HomeFragment) viewPager.getAdapter().instantiateItem(viewPager,0);
            homeFragment.reloadData();
        }


    }
    public void test(){
        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit("testab","abc");
                mSocket.on("thongBao", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.d(TAG, "call: "+args[0]);
                    }
                });
            }
        });
    }

}