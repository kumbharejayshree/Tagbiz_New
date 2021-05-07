package com.tagloy.tagbiz.fragment;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;
import com.tagloy.tagbiz.R;
import com.tagloy.tagbiz.adapter.NoticeBoardAdapter;


public class NoticeBoard extends Fragment {
    private NoticeBoardAdapter noticeBoardAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    String message, organizationId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        Bundle bundle = this.getArguments();
        //message = bundle.getString("message");
        //organizationId = bundle.getString("org");
        //Log.e("NB", message);
        viewPager = (ViewPager) view.findViewById(R.id.NoticeBoardViewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.NoticeBoardTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Add NoticeBoard"));
        tabLayout.addTab(tabLayout.newTab().setText("Live"));
        tabLayout.addTab(tabLayout.newTab().setText("Archive"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        noticeBoardAdapter = new NoticeBoardAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(noticeBoardAdapter);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#000000"));


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }



}
