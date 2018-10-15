package generatebarcode.com.generatebarcode.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.onbarcode.barcode.android.IBarcode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.adapter.BarcodeListAdapter;
import generatebarcode.com.generatebarcode.utils.EventClicked;
import generatebarcode.com.generatebarcode.utils.EventClickedForImage;
import generatebarcode.com.generatebarcode.utils.EventClickedForInfo;
import generatebarcode.com.generatebarcode.utils.Helpers;

public class MenuNavigationActivity extends AppCompatActivity implements View.OnClickListener {
    SlidingPaneLayout mSlidingPanel;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BarcodeListAdapter mAdapter;
    private AlertDialog dialog;
    AlertDialog dialogPreview;
    boolean isExit;
    Boolean boolCheck = false;
    public static Boolean boolFOrEvent = false;
    String randomCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menunavigation_layout);
        ButterKnife.bind(this);

        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);
        mSlidingPanel.setPanelSlideListener(panelListener);
        mSlidingPanel.setParallaxDistance(100);
        mSlidingPanel.setSliderFadeColor(ContextCompat.getColor(this, android.R.color.transparent));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Menu");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu_home);
        }

        // Barcode list
        List<Map> list = new ArrayList<>();
        list = Helpers.barcodeList();

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BarcodeListAdapter(getApplicationContext(), list);
        mRecyclerView.setAdapter(mAdapter);
    }

    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener() {

        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub

        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mSlidingPanel.isOpen()) {
                    mSlidingPanel.closePane();
                } else {
                    mSlidingPanel.openPane();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                setButtonSelected(view);
                Toast.makeText(this, "Button dashboard clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                setButtonSelected(view);
                Toast.makeText(this, "Button explore clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                setButtonSelected(view);
                Toast.makeText(this, "Button profile clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                setButtonSelected(view);
                Toast.makeText(this, "Button messages clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button5:
                setButtonSelected(view);
                Toast.makeText(this, "Button setting clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLogout:
                mSlidingPanel.closePane();
                Toast.makeText(this, "Button logout clicked!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void setButtonSelected(final View v) {
        mSlidingPanel.closePane();
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.menuContainer);
        for (int index = 0; index < (viewGroup).getChildCount(); ++index) {
            View nextChild = (viewGroup).getChildAt(index);
            nextChild.setBackgroundColor(ContextCompat.getColor(MenuNavigationActivity.this, android.R.color.transparent));
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                v.setBackground(ContextCompat.getDrawable(MenuNavigationActivity.this, R.drawable.menunavigation_menu_selected_bg));
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void event_click_alert(EventClicked response) {
        String res[] = response.getPosition().split("split");
        final int code_type = Integer.parseInt(res[0]);
        final String code_des = res[1];
        final String code_name = res[2];
        final String code_info = res[3];
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_dialog, null, false);
        TextView codeName = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        final LinearLayout random_layout = (LinearLayout) dialogForm.findViewById(R.id.random_layout);
        final EditText random_description = (EditText) dialogForm.findViewById(R.id.random_description);
        final EditText random_quantity = (EditText) dialogForm.findViewById(R.id.random_quantity);
        final LinearLayout self_layout = (LinearLayout) dialogForm.findViewById(R.id.self_layout);
        final EditText input_data = (EditText) dialogForm.findViewById(R.id.input_data);
        codeName.setText(code_name);
        input_data.setHint(code_des);
        validationCheck(code_type, input_data);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        RadioGroup radioGroup = (RadioGroup) dialogForm.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().contains("Random Generate")) {
                        boolCheck = false;
                        random_layout.setVisibility(View.VISIBLE);
                        self_layout.setVisibility(View.GONE);
                    } else {
                        boolCheck = true;
                        random_layout.setVisibility(View.GONE);
                        self_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        Button buttonSubmit = (Button) dialogForm.findViewById(R.id.buttonSubmit);
        Button buttonPreview = (Button) dialogForm.findViewById(R.id.buttonPreview);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolCheck) {
                    if (!input_data.getText().toString().equals("")) {
                        Boolean bool = submitFun(code_type, input_data);
                        if (bool) {
                            Intent intent = new Intent(MenuNavigationActivity.this, SelfGenerateActivity.class);
                            intent.putExtra("input_data", input_data.getText().toString());
                            intent.putExtra("code_type", "" + code_type);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(MenuNavigationActivity.this, "Input data can't be empty!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!random_description.getText().toString().equals("")) {
                        if (!random_quantity.getText().toString().equals("")) {
                            if (Integer.parseInt(random_quantity.getText().toString()) >= 1) {
                                Intent intent = new Intent(MenuNavigationActivity.this, RandomGenerateActivity.class);
                                intent.putExtra("description", random_description.getText().toString());
                                intent.putExtra("bitmap", random_quantity.getText().toString());
                                intent.putExtra("code_type", "" + code_type);
                                startActivity(intent);
                            } else {
                                random_quantity.setError("Quantity greater than or equal to 1!");
                            }
                        } else {
                            random_quantity.setError("Quantity greater than or equal to 1!");
                        }
                    } else {
                        random_description.setError("Description can't be empty!");
                    }
                }
            }
        });
        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolFOrEvent = false;
                String input_data1 = "";
                Bitmap bitmap = null;
                if (boolCheck) {
                    if (!input_data.getText().toString().equals("")) {
                        input_data1 = input_data.getText().toString();
                         bitmap = Helpers.barcodeBitmap(code_type, input_data1, 900, 700);
                        if (bitmap!=null){
                            randomCode = input_data1;
                            EventBus.getDefault().post(new EventClickedForImage(bitmap));
                        }else {
                            randomCode = "Image is empty";
                        }
                    } else {
                        input_data.setError("Input data can't be empty!");
                    }
                } else {
                    if (bitmap!=null){
                        input_data1 = Helpers.barcodeRandomString(code_type);
                        randomCode = input_data1;
                        EventBus.getDefault().post(new EventClickedForImage(Helpers.barcodeBitmap(code_type, input_data1, 900, 700)));
                    }else {
                        randomCode = "Image is empty";
                    }
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                boolCheck = false;
            }
        });

    }


    void validationCheck(int code_type, EditText input_data) {
        if (code_type == IBarcode.CODE128) {
            // input_data.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        } else if (code_type == IBarcode.CODE39) {
            input_data.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        } else if (code_type == IBarcode.CODE93) {

        } else if (code_type == IBarcode.EAN13) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
              input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        } else if (code_type == IBarcode.EAN8) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
              input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } else if (code_type == IBarcode.CODABAR) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (code_type == IBarcode.UPCE) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
             input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } else if (code_type == IBarcode.UPCA) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
              input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        } else if (code_type == IBarcode.ITF14) {
            input_data.setInputType(InputType.TYPE_CLASS_NUMBER);
             input_data.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
        }
    }

    Boolean submitFun(int code_type, EditText input_data) {
        Boolean bool = false;
        if (code_type == IBarcode.CODE128) {
            bool = true;
        } else if (code_type == IBarcode.CODE39) {
            if (input_data.getText().toString().contains("_")) {
                input_data.setError("No special  _  character required!");
            } else {
                bool = true;
            }
        } else if (code_type == IBarcode.CODE93) {
            bool = true;
        } else if (code_type == IBarcode.EAN13) {
            if (input_data.getText().toString().length() == 13) {
                bool = true;
            } else {
                input_data.setError("Input Data is 12 digit!");
            }
        } else if (code_type == IBarcode.EAN8) {
            if (input_data.getText().toString().length() == 8) {
                bool = true;
            } else {
                input_data.setError("Input Data is 7 digit!");
            }
        } else if (code_type == IBarcode.CODABAR) {
            bool = true;
        } else if (code_type == IBarcode.UPCE) {
            if (input_data.getText().toString().length() == 8) {
                bool = true;
            } else {
                input_data.setError("Input Data is 6 digit!");
            }
        } else if (code_type == IBarcode.UPCA) {
            if (input_data.getText().toString().length() == 12) {
                bool = true;
            } else {
                input_data.setError("Input Data is 12 digit!");
            }
        } else if (code_type == IBarcode.ITF14) {
            if (input_data.getText().toString().length() == 14) {
                bool = true;
            } else {
                input_data.setError("Input Data is 14 digit!");
            }
        }
        return bool;
    }

    @Subscribe
    public void eventClickAlertForInfo(EventClickedForInfo response) {
        String res[] = response.getPosition().split("split");
        final String code_name = res[2];
        final String code_info = res[3];
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_info_dialog, null, false);
        final TextView codeName = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        final TextView infoData = (TextView) dialogForm.findViewById(R.id.info_data);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        codeName.setText(code_name);
        infoData.setText(code_info);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Subscribe
    public void eventClickAlertForInfo(EventClickedForImage response) {
        Bitmap bitmap = response.getPosition();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogForm = inflater.inflate(R.layout.barcode_image_view_dialog, null, false);
        ImageView mImageView = (ImageView) dialogForm.findViewById(R.id.image_view);
        LinearLayout cancel = (LinearLayout) dialogForm.findViewById(R.id.cancel);
        TextView codeNameTxt = (TextView) dialogForm.findViewById(R.id.codeNameTxt);
        mImageView.setImageBitmap(bitmap);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogForm);
        builder.create();
        if (boolFOrEvent) {
            codeNameTxt.setVisibility(View.GONE);
            dialog = builder.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        } else {
            codeNameTxt.setVisibility(View.VISIBLE);
            if (!randomCode.equals("")) {
                codeNameTxt.setText(randomCode);
            }
            dialogPreview = builder.create();
            dialogPreview.show();
            dialogPreview.setCanceledOnTouchOutside(false);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolFOrEvent) {
                    dialog.dismiss();
                } else {
                    dialogPreview.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isExit) {
            super.onBackPressed();
        }
        isExit = true;
        Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isExit = false;
            }
        }, 2000);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
