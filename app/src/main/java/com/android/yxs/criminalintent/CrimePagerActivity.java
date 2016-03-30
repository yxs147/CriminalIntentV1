package com.android.yxs.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCrimes=CrimeLab.get(this).getCrimes();

        //添加ViewPager到Layout
        mViewPager=new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        FragmentManager fm=getSupportFragmentManager();
        //设置ViewPage的Adapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                //返回一个详细页的Frament
                //详细页Fragment 实现了newInstance方法 可以直接传递参数
                return CrimeFragment.newInstance(crime.getmID());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        //获取Fragment通过Intent传递过来的参数
        UUID crimeID=(UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        //设置ViewPager的选中Page
        for (int i=0;i<mCrimes.size();i++){
            if(mCrimes.get(i).getmID().equals(crimeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
