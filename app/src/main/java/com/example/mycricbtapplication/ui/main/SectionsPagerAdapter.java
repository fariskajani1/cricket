package com.example.mycricbtapplication.ui.main;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mycricbtapplication.AccelerationLiveValues;
import com.example.mycricbtapplication.All_Fragment;
import com.example.mycricbtapplication.ClockFragment;
import com.example.mycricbtapplication.LineFragment;
import com.example.mycricbtapplication.SoundLiveData;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{
            "Temp", "Gyro", "Accel", "Sound Impect", " Gyro & Accel& Impect "
    };

    private static final Fragment[] TAB_FRAGMENTS = new Fragment[]{
            new ClockFragment(), // temp
            new LineFragment(), // gyro
            new AccelerationLiveValues(), // accel
            new SoundLiveData(), // Sound
            new All_Fragment()  // test
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return TAB_FRAGMENTS[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_FRAGMENTS.length;
    }
}