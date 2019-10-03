package com.generatebarcode.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.generatebarcode.R;
import com.generatebarcode.fragment.BarcodeFragment;

public class AndroidBarcodeActivity extends Activity {
    FrameLayout frameLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_barcode);
        frameLayout= (FrameLayout) findViewById(R.id.frameLayout);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout, new BarcodeFragment(),"Login");
        ft.commit();

      /*  ButterKnife.bind(this);
        Intent intent = getIntent();
        String code_type = intent.getStringExtra("code_type");
        String input_data = intent.getStringExtra("input_data");

        AndroidBarcodeView view = new AndroidBarcodeView(this, input_data, code_type);
        main_layout.addView(view);*/


    }

}