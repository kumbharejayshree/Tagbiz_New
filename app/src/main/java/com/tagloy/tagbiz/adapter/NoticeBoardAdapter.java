package com.tagloy.tagbiz.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tagloy.tagbiz.fragment.AddNoticeBoard;
import com.tagloy.tagbiz.fragment.ArchiveFragment;
import com.tagloy.tagbiz.fragment.LiveNoticeBoard;

/**
 * Created by User on 4/19/2021.
 */

public class NoticeBoardAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;


    public NoticeBoardAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AddNoticeBoard activeFragment = new AddNoticeBoard();
                return activeFragment;
            case 1:
                LiveNoticeBoard liveFragment = new LiveNoticeBoard();
                return liveFragment;
            case 2:
                ArchiveFragment archiveFragment = new ArchiveFragment();
                return archiveFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
