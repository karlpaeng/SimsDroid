package com.example.softwareengineering;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

//import android.text.format.Time;

//import java.sql.Time;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;


public class MainActivity2 extends AppCompatActivity {

    Button check_out_btn, button8;
    ListView lv;
    ProductModel productModel;
    ArrayAdapter productArrayAdapter;
    TextView total;
    //Time now = new Time();//Time.getCurrentTimezone());
    DatabaseHelper databaseHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_order);

        lv = findViewById(R.id.orderlistView);

        databaseHelper = new DatabaseHelper(MainActivity2.this);
        showCurrentOrderOnListView(databaseHelper);

        total = findViewById(R.id.total4);
        total.setText(databaseHelper.orderTotal());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //when item is selected/highlighted
                productModel = (ProductModel) parent.getItemAtPosition(position);
                //Toast.makeText(MainActivity2.this, productModel.toString1(), Toast.LENGTH_SHORT).show();

            }
        });

        //productArrayAdapter = new ArrayAdapter<ProductModel>(MainActivity2.this, android.R.layout.simple_list_item_1);
        //viewAllProds(databaseHelper);
        showCurrentOrderOnListView(databaseHelper);


        check_out_btn = findViewById(R.id.check_out_btn);
        check_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //now.setToNow();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
                String currentDate = sdf.format(new Date());
                String currentTime = stf.format(new Date());

                //String date = "" + now.year + "-" + now.month + "-" + now.monthDay;
                //String time = now.format("%k:%M:%S");


                if(databaseHelper.addTransacRecord(currentDate, currentTime)) {

                    //Toast.makeText(MainActivity2.this, ""+date+time, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity2.this, OrderDetailsActivity.class);

                    String lastOrderId = databaseHelper.getLastOrderID() + "";

                    intent.putExtra("orderId", lastOrderId);

                    startActivity(intent);
                    //Toast.makeText(MainActivity2.this, ""+currentDate+currentTime, Toast.LENGTH_SHORT).show();
                }
            }
        });

        button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    databaseHelper.removeOrderProduct(productModel);
                    total.setText(databaseHelper.orderTotal());
                    showCurrentOrderOnListView(databaseHelper);
                } catch (Exception e){
                    //Toast.makeText(MainActivity2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });



        Button addMoreButton = findViewById(R.id.add_more);
        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan_product();
            }
        });



    }




    public void scan_product(){
        Intent intent = new Intent(this, ScanProductActivity.class);
        startActivity(intent);
    }

    public void showCurrentOrderOnListView(DatabaseHelper databaseHelper){
        try {
            final Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.droid_sans_mono);
            ArrayAdapter productArrayAdapter = new ArrayAdapter<ProductModel>(MainActivity2.this, android.R.layout.simple_list_item_1, databaseHelper.viewAllOrderProducts()) {
                @NonNull
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    AppCompatTextView item = (AppCompatTextView) super.getView(position, convertView, parent);
                    item.setTypeface(typeface);
                    return item;
                }
            };
            lv.setAdapter(productArrayAdapter);
        }catch (Exception e){
            Log.d("error", e.toString());
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            databaseHelper = new DatabaseHelper(MainActivity2.this);
            databaseHelper.clearCurrentOrder();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}