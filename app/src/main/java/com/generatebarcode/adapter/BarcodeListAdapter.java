package com.generatebarcode.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onbarcode.barcode.android.IBarcode;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.generatebarcode.R;
import com.generatebarcode.activity.MenuNavigationActivity;
import com.generatebarcode.utils.EventClicked;
import com.generatebarcode.utils.EventClickedForImage;
import com.generatebarcode.utils.EventClickedForInfo;

public class BarcodeListAdapter extends RecyclerView.Adapter<BarcodeListAdapter.ViewHolder> {
    private Context context;
    private List<Map> data;

    public BarcodeListAdapter(Context context, List<Map> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BarcodeListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.barcode_list_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BarcodeListAdapter.ViewHolder viewHolder, final int position) {

        Map map = data.get(position);
        viewHolder.mCodeName.setText("" + map.get("code_name"));
        viewHolder.mCodeDescription.setText("" + map.get("code_des"));
        if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.CODE128)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.code128);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.CODE39)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.code39);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.CODE93)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.code93);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.EAN13)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.ean13);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.EAN8)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.ean8);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.CODABAR)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.codabar);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.UPCE)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.upce);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.UPCA)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.upca);
        } else if ((Integer.parseInt(map.get("code_type").toString()) == IBarcode.ITF14)) {
            viewHolder.mBarcodeIcon.setImageResource(R.drawable.itf14);
        }

        viewHolder.details_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map map = data.get(position);
                String data = (String) map.get("code_type") + "split" + (String) map.get("code_des") + "split" + (String) map.get("code_name") + "split" + (String) map.get("code_info");
                EventBus.getDefault().post(new EventClickedForInfo(data));
            }
        });


        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = data.get(position);
                String data = (String) map.get("code_type") + "split" + (String) map.get("code_des") + "split" + (String) map.get("code_name") + "split" + (String) map.get("code_info");
                EventBus.getDefault().post(new EventClicked(data));
            }
        });

        viewHolder.mBarcodeImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuNavigationActivity.boolFOrEvent = true;
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) viewHolder.mBarcodeIcon.getDrawable());
                Bitmap bitmap = bitmapDrawable .getBitmap();
                EventBus.getDefault().post(new EventClickedForImage(bitmap));
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.code_name)
        TextView mCodeName;
        @Bind(R.id.code_description)
        TextView mCodeDescription;
        @Bind(R.id.main_layout)
        LinearLayout mMainLayout;
        @Bind(R.id.barcode_icon)
        ImageView mBarcodeIcon;
        @Bind(R.id.details_icon)
        LinearLayout details_icon;
        @Bind(R.id.barcode_image_layout)
        LinearLayout mBarcodeImageLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}