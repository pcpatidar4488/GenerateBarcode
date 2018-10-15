package generatebarcode.com.generatebarcode.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onbarcode.barcode.android.IBarcode;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.activity.AndroidBarcodeView;

/**
 * Created by Beryl on 9/6/2018.
 */

public class BarcodeFragment extends Fragment {

    String code_type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.layout_fragment, container, false);

        Intent intent = getActivity().getIntent();
        code_type = intent.getStringExtra("code_type");
        String input_data = intent.getStringExtra("input_data");
        AndroidBarcodeView view = new AndroidBarcodeView(getActivity(), input_data, code_type);
        int type = Integer.parseInt(code_type);
        // Gets linearlayout
        LinearLayout main_layout = (LinearLayout) v.findViewById(R.id.main_layout);
// Gets the layout params that will allow you to resize the layout
        ViewGroup.LayoutParams params = main_layout.getLayoutParams();
// Changes the height and width to the specified *pixels*
        if (type == IBarcode.CODE128){
            params.width = 400;
            params.height = 300;
        }else if (type == IBarcode.CODE39){

        }else if (type == IBarcode.CODE93){

        }else if (type == IBarcode.EAN13){

        }else if (type == IBarcode.EAN8){

        }else if (type == IBarcode.CODABAR){

        }else if (type == IBarcode.UPCE){

        }else if (type == IBarcode.UPCA){

        }else if (type == IBarcode.ITF14){

        }
        main_layout.setLayoutParams(params);
        main_layout.addView(view);


    /*   // LinearLayout ll=(LinearLayout)findViewById(com.onbarcode.barcode.android.R.id.linearLayout1);
      //  System.out.println(ll.getWidth()+" "+ll.getHeight());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getWidth(),view.getHeight());
        YourView yourView = new YourView(getBaseContext());
       // view.setBackgroundColor(Color.WHITE);
        main_layout.addView(main_layout,params);*/

        return v;
    }
}
