package generatebarcode.com.generatebarcode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import generatebarcode.com.generatebarcode.R;
import generatebarcode.com.generatebarcode.adapter.RandomGrideViewAdapter;
import generatebarcode.com.generatebarcode.model.DataModel;
import generatebarcode.com.generatebarcode.utils.Helpers;

public class RandomGenerateActivity extends AppCompatActivity implements RandomGrideViewAdapter.ItemListener  {
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    String description;
    int code_type;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        Intent intent = getIntent();
        description = intent.getStringExtra("description");
        code_type = Integer.parseInt(intent.getStringExtra("code_type"));
        quantity = Integer.parseInt(intent.getStringExtra("bitmap"));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
       // arrayList = new ArrayList<>();
       // arrayList.add(new DataModel(description, 1, code_type));
        RandomGrideViewAdapter adapter = new RandomGrideViewAdapter(RandomGenerateActivity.this, generateBarcode(description,quantity,code_type), this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

       /* AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);*/


        /**
         Simple GridLayoutManager that spans two columns
         **/
        GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(DataModel item) {

       // Toast.makeText(getApplicationContext(), item.description + " is clicked", Toast.LENGTH_SHORT).show();

    }

    public ArrayList<DataModel> generateBarcode(String description,int quantity,int code_type){
         arrayList = new ArrayList<>();
         for (int i=0;i<quantity;i++){
             String input_data = Helpers.barcodeRandomString(code_type);
             arrayList.add(new DataModel(input_data, Helpers.barcodeBitmap(code_type,input_data,500,250), description));
         }
        return arrayList;
    }
}
