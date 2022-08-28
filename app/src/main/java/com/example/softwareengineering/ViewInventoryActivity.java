package com.example.softwareengineering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewInventoryActivity extends AppCompatActivity {


    ListView lv;
    Button remove;
    Button update;
    Button search;
    ProductModel productModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inventory);

        lv = findViewById(R.id.listView);

        DatabaseHelper databaseHelper = new DatabaseHelper(ViewInventoryActivity.this);
        showProductOnListView(databaseHelper);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //when item is selected/highlighted
                productModel = (ProductModel) parent.getItemAtPosition(position);
                //Toast.makeText(ViewInventoryActivity.this, productModel.toString1(), Toast.LENGTH_SHORT).show();

            }
        });

        remove = findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    databaseHelper.removeInventoryProduct(productModel.getId());
                    showProductOnListView(databaseHelper);
                } catch (Exception e){
                    Toast.makeText(ViewInventoryActivity.this, "select a product to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });


        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    databaseHelper.newTempUpdate(productModel);
                    Intent intent = new Intent(ViewInventoryActivity.this, UpdateDataActivity.class);
                    startActivity(intent);
                } catch(Exception e){
                    Toast.makeText(getApplicationContext(), "please select product to update", Toast.LENGTH_SHORT).show();
                    Log.d("Error", e.toString());
                }
            }
        });

        search = findViewById(R.id.done5);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewInventoryActivity.this);

                final EditText input = new EditText(ViewInventoryActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                input.setLayoutParams(lp);


                builder.setTitle("Search");
                builder.setMessage("please enter a product name:");
                builder.setView(input);
                builder.setPositiveButton("search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        showProductSearchOnListView(databaseHelper, input.getText().toString());
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
            }
        });

    }

    private void showProductOnListView(DatabaseHelper databaseHelper){
            ArrayAdapter productArrayAdapter = new ArrayAdapter<ProductModel>(ViewInventoryActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getEveryOne());
            lv.setAdapter(productArrayAdapter);
    }


    private void showProductSearchOnListView(DatabaseHelper databaseHelper, String s){
        ArrayAdapter productArrayAdapter = new ArrayAdapter<ProductModel>(ViewInventoryActivity.this, android.R.layout.simple_list_item_1, databaseHelper.searchInventoryProducts(s));
        lv.setAdapter(productArrayAdapter);
    }





}