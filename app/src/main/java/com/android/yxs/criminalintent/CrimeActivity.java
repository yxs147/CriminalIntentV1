package com.android.yxs.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
        if(fragment==null){
            //传参数
            UUID crimeid=(UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
            fragment= CrimeFragment.newInstance(crimeid);
            fm.beginTransaction().add(R.id.fragmentContainer,fragment).commit();
        }
    }
}
