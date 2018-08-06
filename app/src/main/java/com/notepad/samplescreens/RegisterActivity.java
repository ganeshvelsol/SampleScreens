package com.notepad.samplescreens;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity
{
public static ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         viewPager = (ViewPager) findViewById(R.id.home_screen_viewpager);
        viewPager.setClipToPadding(true);
        viewPager.setPageMargin(10);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegistrationFragment(), "signup");
        adapter.addFragment(new LoginFragment(), "signIn");
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_home_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        getFragmentManager().beginTransaction();

    }
    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
             return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

}
