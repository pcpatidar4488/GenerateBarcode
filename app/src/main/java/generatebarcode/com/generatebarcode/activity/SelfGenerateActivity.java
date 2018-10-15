package generatebarcode.com.generatebarcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.utils.Helpers;

public class SelfGenerateActivity extends AppCompatActivity {
    EditText textInput;
    Button tombolGenerate;
    ImageView gambarBarcode;
    TextView textnya;
    Bitmap bitmap = null;
    int code_type;
    String input_data;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_latest);
        /** hubungkan objek di layout ke variabelnya */
        textInput = (EditText) findViewById(R.id.input_isi);
        tombolGenerate = (Button) findViewById(R.id.btn_generate);
        gambarBarcode = (ImageView) findViewById(R.id.img_barcode);
        textnya = (TextView) findViewById(R.id.txt);


        Intent intent = getIntent();
        code_type = Integer.parseInt(intent.getStringExtra("code_type"));
        input_data = intent.getStringExtra("input_data");
        bitmap = Helpers.barcodeBitmap(code_type,input_data,600,300);
        if ( bitmap!=null){
            gambarBarcode.setImageBitmap(bitmap);
            textnya.setText(input_data);
        }else {
            textnya.setText("Barcode is empty");
        }
       /* try {
            bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_128, 600, 300);
            gambarBarcode.setImageBitmap(bitmap);
            textnya.setText(input_data);
        } catch (WriterException e) {
            e.printStackTrace();
        }*/
    }
}
