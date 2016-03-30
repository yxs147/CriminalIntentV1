package com.android.yxs.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by yxs on 16/3/30.
 */
public class ChoseDateFragment extends DialogFragment {

    public static final String EXTRA_DATE = "com.android.yxs.criminalintent.date";
    private Date mDate;
    private Button btnChoseDate;
    private Button btnChoseTime;
    private TextView tvChoseDateTime;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);
        View v = getActivity().getLayoutInflater().inflate(R.layout.chose_date, null);
        tvChoseDateTime= (TextView) v.findViewById(R.id.tv_show_date_chose);
        tvChoseDateTime.setText(android.text.format.DateFormat.format("EEEE,MMM dd,yyyy kk:mm", mDate));

        btnChoseDate= (Button) v.findViewById(R.id.btn_chose_date);
        btnChoseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开Fragment 并传递参数
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDate);
                //设置弹出的Fragment 的TargetFragment 用来接收弹出Fragment的返回值
                dialog.setTargetFragment(getTargetFragment(), CrimeFragment.REQUEST_DATE);
                dialog.show(fm, CrimeFragment.DIALOG_DATE);
                dismiss();
            }
        });

        btnChoseTime= (Button) v.findViewById(R.id.btn_chose_time);
        btnChoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开Fragment 并传递参数
                FragmentManager fm = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mDate);
                //设置弹出的Fragment 的TargetFragment 用来接收弹出Fragment的返回值
                dialog.setTargetFragment(getTargetFragment(), CrimeFragment.REQUEST_TIME);
                dialog.show(fm, CrimeFragment.DIALOG_TIME);
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.date_picker_title)
                .setView(v)
                .setNegativeButton("Cancel",null)
                .create();
    }

    public static ChoseDateFragment newInstance(Date date) {

        Bundle args = new Bundle();
        ChoseDateFragment fragment = new ChoseDateFragment();
        args.putSerializable(EXTRA_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }
}
