package com.example.softwareengineering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;

public class NoBarcodeActivity extends AppCompatActivity {

    private Button select;
    private Button button2;
    private ListView lv;
    private EditText search1;
    ProductModel productModel;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        databaseHelper = new DatabaseHelper(NoBarcodeActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_barcode);

        lv = findViewById(R.id.noBarcodeListView);
        DatabaseHelper databaseHelper = new DatabaseHelper(NoBarcodeActivity.this);
        showProductOnListView(databaseHelper);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                productModel = (ProductModel) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), productModel.toString1(), Toast.LENGTH_SHORT).show();
            }
        });



        select = findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.ifOrderNameExists(productModel.getProductName())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoBarcodeActivity.this);
                    builder.setTitle("Failed");
                    builder.setMessage("You already scanned the product");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent intent = new Intent(getApplicationContext(), NoBarcodeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    //databaseHelper.addOrderProduct(productModel);
                    //databaseHelper.addNoBc(productModel);
                    Intent intent = new Intent(getApplicationContext(), ScanProductCopyActivity.class);
                    intent.putExtra("productModel", productModel);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        search1 = findViewById(R.id.search1);
        showProductSearchOnListView(databaseHelper, search1.getText().toString());

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductSearchOnListView(databaseHelper, search1.getText().toString());
            }
        });


    }

    public void showProductOnListView(DatabaseHelper databaseHelper){
        ArrayAdapter productArrayAdapter = new ArrayAdapter<ProductModel>(NoBarcodeActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getEveryOne());
        lv.setAdapter(productArrayAdapter);

    }

    private void showProductSearchOnListView(DatabaseHelper databaseHelper, String s){
        ArrayAdapter productArrayAdapter = new ArrayAdapter<ProductModel>(NoBarcodeActivity.this, android.R.layout.simple_list_item_1, databaseHelper.searchInventoryProducts(s));
        lv.setAdapter(productArrayAdapter);
    }



}