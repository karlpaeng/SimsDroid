package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    ListView lv;
    Button done_button;
    Transactions transaction;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        DatabaseHelper databaseHelper = new DatabaseHelper(TransactionActivity.this);
        lv = findViewById(R.id.listView);


        try {
            showProductOnListView(databaseHelper);
        }catch (Exception e){
            //Toast.makeText(TransactionActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("error", e.toString());
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //when item is selected/highlighted
                transaction = (Transactions) parent.getItemAtPosition(position);
                //Toast.makeText(ViewInventoryActivity.this, productModel.toString1(), Toast.LENGTH_SHORT).show();

            }
        });


        done_button = findViewById(R.id.view);
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), OrderDetailsActivityCopy.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("orderId", "" + transaction.getOrderId());
                    startActivity(intent);
                }catch(Exception e){
                    Toast.makeText(TransactionActivity.this, "Select something to view", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showProductOnListView(DatabaseHelper databaseHelper){
        ArrayAdapter productArrayAdapter = new ArrayAdapter<Transactions>(TransactionActivity.this, android.R.layout.simple_list_item_1, databaseHelper.viewTransacHistory());
        lv.setAdapter(productArrayAdapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}