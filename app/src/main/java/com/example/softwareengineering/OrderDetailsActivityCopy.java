package com.example.softwareengineering;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class OrderDetailsActivityCopy extends AppCompatActivity {

    Button done_button;
    ListView lv;

    TextView ordernum;
    TextView date;
    TextView time;
    TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        DatabaseHelper databaseHelper = new DatabaseHelper(OrderDetailsActivityCopy.this);

        lv = findViewById(R.id.listView);
        ordernum = findViewById(R.id.orderTxtView);
        date = findViewById(R.id.dateTextView);
        time = findViewById(R.id.timeTextView);
        total = findViewById(R.id.totalTextView);

//        showProductOnListView(databaseHelper);
        Bundle extras = getIntent().getExtras();
        String idOrder = "";
        long orderID=0;
        //Intent i = getIntent();
        try {

            if (extras != null) {

                idOrder = extras.getString("orderId");
                orderID = Long.parseLong(idOrder);

                OrderDetails od = databaseHelper.getOD(orderID);
                ordernum.setText(idOrder);
                date.setText(""+od.getDate());
                time.setText(""+od.getTime());
                total.setText(""+od.getTotal());
                //Toast.makeText(OrderDetailsActivity.this, total.getText().toString(), Toast.LENGTH_SHORT).show();

            }

            //String idOrder = getIntent().getExtras().getString("orderId");





        }catch(Exception e){
            //Toast.makeText(OrderDetailsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("error", e.toString());
        }
        List<String> list = databaseHelper.viewSingleOrder(orderID);
        ArrayAdapter orderArrayAdapter = new ArrayAdapter<String>(OrderDetailsActivityCopy.this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(orderArrayAdapter);


        done_button = findViewById(R.id.done);
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });





    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {


            Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

//    private void showProductOnListView(DatabaseHelper databaseHelper){
//        ArrayAdapter productArrayAdapter = new ArrayAdapter<ProductModel>(OrderDetailsActivity.this, android.R.layout.simple_list_item_1, databaseHelper.viewSingleOrder(orderID));
//        lv.setAdapter(productArrayAdapter);
//    }
}