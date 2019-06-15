package com.intpro.decepticons;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserTypeSelectionFragment extends Fragment implements View.OnClickListener {

    private TextView type;
    private TextView details;
    private ImageView image;
    private Button btn1;
    private Button btn2;

    private int page;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_type_selection, container, false);
        image = view.findViewById(R.id.iv_user_type_selection);
        type = view.findViewById(R.id.tv_user_type_selection_Type);
        details = view.findViewById(R.id.tv_user_type_selection_description);

        page = getArguments().getInt("page");

        if (page == 0) {
            btn1 = view.findViewById(R.id.btn_user_type_selection);
            btn1.setOnClickListener(this);
        }
        else if (page == 1) {
            btn2 = view.findViewById(R.id.btn_user_type_selection);
            btn2.setOnClickListener(this);
        }

        setPage(page);
        return view;
    }

    void setPage(int page){
        if (page == 0){
            Glide.with(getContext()).load("https://www.crossroadshospice.com/media/2991/patient-dignity-at-end-of-life.jpg").into(image);
            type.setText(R.string.patient);
            details.setText(R.string.patient_description);
            btn1.setText(R.string.become_patient);
        }
        if (page == 1){
            Glide.with(getContext()).load("https://i.kinja-img.com/gawker-media/image/upload/s---6whp4y6--/c_scale,f_auto,fl_progressive,q_80,w_800/lj6mnjgopjduugxaxpvk.jpg").into(image);
            type.setText(R.string.driver);
            details.setText(R.string.driver_description);
            btn2.setText(R.string.become_driver);
        }
    }



    @Override
    public void onClick(View v) {
        if (v == btn1 ){
            Intent intent = new Intent(getContext(), PatientLog.class);
            startActivity(intent);
        }
        if( v== btn2){
            Intent intent = new Intent(getContext(), DriverLog.class);
            startActivity(intent);
        }
    }

}
