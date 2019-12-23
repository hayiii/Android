package com.example.mistery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mistery.fragments.ChatFragment;
import com.example.mistery.fragments.SearchFragment;
import com.example.mistery.fragments.SettingFragment;
import com.example.mistery.fragments.ThreadFragment;

public class DataGenerator {
    public static final int []mTabRes = new int[]{R.drawable.chat_before,R.drawable.xs_before,R.drawable.search_before,R.drawable.settings_before};
    public static final int []mTabResPressed= new int[]{R.drawable.chat_after,R.drawable.xs_after,R.drawable.search_after,R.drawable.settings_after};
    public static final String []mTabTitle = new String[]{"聊天","线索","搜证","设置"};

    public static Fragment[] getFragments(String from){
        Fragment fragments[] = new Fragment[4];
        fragments[0] = ChatFragment.newInstance(from);
        fragments[1] = ThreadFragment.newInstance(from);
        fragments[2] = SearchFragment.newInstance(from);
        fragments[3] = SettingFragment.newInstance(from);

        return fragments;
    }

    public static View getTabView(Context context,int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item,null);
        ImageView tabIcon = view.findViewById(R.id.tab_content_image);
        tabIcon.setImageDrawable(context.getResources().getDrawable(DataGenerator.mTabRes[position]));
        TextView tabText = view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);

        return view;
    }
}
