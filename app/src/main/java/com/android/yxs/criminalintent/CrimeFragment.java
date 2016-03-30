package com.android.yxs.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    //用来接收Activity传递的参数
    public static final String EXTRA_CRIME_ID = "com.android.yxs.criminalintent.crimeid";
    public static final String DIALOG_DATE = "date";
    public static final String DIALOG_TIME="time";
    public static final int REQUEST_DATE = 0;
    public static final int REQUEST_TIME = 1;
    private Crime mCrime;

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mTimeButton;

    public CrimeFragment() {
        // Required empty public constructor
    }


    //Fragment 接收参数定义的方法
    //放到Bundle里面 Create的时候可以取到 旋转屏幕也不会丢失
    public static CrimeFragment newInstance(UUID crimeId) {
        CrimeFragment fragment = new CrimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //读取ID
        if (getArguments() != null) {
            UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
            mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //加载Layout文件做View
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //mDateButton.setText(android.text.format.DateFormat.format("EEEE,MMM dd,yyyy", mCrime.getmDate()));
        //mDateButton.setEnabled(false);
        mDateButton = (Button) view.findViewById(R.id.crime_date);

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //打开Fragment 并传递参数
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
//                //设置弹出的Fragment 的TargetFragment 用来接收弹出Fragment的返回值
//                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
//                dialog.show(fm, DIALOG_DATE);


                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChoseDateFragment dialog = ChoseDateFragment.newInstance(mCrime.getmDate());
                //设置弹出的Fragment 的TargetFragment 用来接收弹出Fragment的返回值
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);

            }
        });

        mTimeButton=(Button)view.findViewById(R.id.crime_time);

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开Fragment 并传递参数
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getmDate());
//                //设置弹出的Fragment 的TargetFragment 用来接收弹出Fragment的返回值
//                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
//                dialog.show(fm, DIALOG_TIME);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                ChoseDateFragment dialog = ChoseDateFragment.newInstance(mCrime.getmDate());
                //设置弹出的Fragment 的TargetFragment 用来接收弹出Fragment的返回值
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(fm, DIALOG_TIME);
            }
        });

        SetBtnDateText();
        mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.ismSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });

        return view;
    }

    //接收DatePickerFragment传来的参数
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE||requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setmDate(date);
            SetBtnDateText();
        }
    }

    public void SetBtnDateText(){
        mDateButton.setText(android.text.format.DateFormat.format("EEEE,MMM dd,yyyy ", mCrime.getmDate()));
        mTimeButton.setText(android.text.format.DateFormat.format("kk:mm", mCrime.getmDate()));
    }

}
