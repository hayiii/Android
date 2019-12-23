package com.example.mistery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainView extends AppCompatActivity {
    private TabLayout tabLayout;
    private Fragment []fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);   //隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_main_view);
        fragments = DataGenerator.getFragments("TabLayout Tab");

        initView();
    }

    private void initView(){
        tabLayout = findViewById(R.id.bottom_tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

                //改变Tab状态
                for (int i=0;i<tabLayout.getTabCount();i++){
                    View view = tabLayout.getTabAt(i).getCustomView();
                    ImageView icon = view.findViewById(R.id.tab_content_image);
                    TextView text = view.findViewById(R.id.tab_content_text);
                    if (i == tab.getPosition()){
                        icon.setImageDrawable(getResources().getDrawable(DataGenerator.mTabResPressed[i]));
                        text.setTextColor(getResources().getColor(android.R.color.black));
                    }else {
                        icon.setImageDrawable(getResources().getDrawable(DataGenerator.mTabRes[i]));
                        text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i=0;i<4;i++){
            tabLayout.addTab(tabLayout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
        }

    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = fragments[0];
                break;
            case 1:
                fragment = fragments[1];
                break;
            case 2:
                fragment = fragments[2];
                break;
            case 3:
                fragment = fragments[3];
        }
        if (fragment!=null)
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
    }

}
