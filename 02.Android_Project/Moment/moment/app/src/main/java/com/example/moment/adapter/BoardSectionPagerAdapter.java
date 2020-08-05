package com.example.moment.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.moment.fragment.LatelyListFragment;
import com.example.moment.fragment.NearListFragment;
import com.example.moment.fragment.PanoListFragment;
import com.example.moment.fragment.PopularListFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardSectionPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mData;
    private ArrayList<String> fragmentTitleList =
            new ArrayList<>(Arrays.asList("최신글","베스트","파노라마","내주변"));
    public BoardSectionPagerAdapter(FragmentManager fm){
        super(fm);
        mData = new ArrayList<>();
        mData.add(new LatelyListFragment());
        mData.add(new PopularListFragment());
        mData.add(new PanoListFragment());
        mData.add(new NearListFragment());
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //탭레이아웃이 간단하게 쓸수 있게 타이틀을 가져옴
        return fragmentTitleList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
