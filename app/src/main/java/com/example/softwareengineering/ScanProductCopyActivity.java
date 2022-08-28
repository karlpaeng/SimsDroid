package com.example.softwareengineering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanProductCopyActivity extends AppCompatActivity {


    private TextView prodName;
    private TextView price;
    private EditText quantity;

    private Button up;
    private Button down;
    private int qty;
    ProductModel productModel;
    ProductModel productModel2;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_product_copy);

        prodName = findViewById(R.id.productName3);
        price = findViewById(R.id.price3);

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                productModel =  (ProductModel) getIntent().getSerializableExtra("productModel");
                //Toast.makeText(getApplicationContext(), productModel.toString1(), Toast.LENGTH_SHORT).show();
                prodName.setText(productModel.getProductName());
                price.setText(productModel.getPrice());
            }
        } catch (Exception e){
            Log.d("error: ", e.toString());
        }

        quantity = findViewById(R.id.qty2);


        up = findViewById(R.id.upBtn2);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().equals("")) quantity.setText("0");
                if (Integer.parseInt(quantity.getText().toString().trim()) > 98)
                    quantity.setText("99");
                qty = Integer.parseInt(quantity.getText().toString().trim());
                qty++;
                quantity.setText(String.valueOf(qty));
            }
        });



        down = findViewById(R.id.downBtn2);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity.getText().toString().equals("")) quantity.setText("0");
                qty = Integer.parseInt(quantity.getText().toString().trim());
                if (qty < 1) {
                } else {
                    qty--;
                    quantity.setText(String.valueOf(qty));
                }

            }
        });



        Button nxtBtn = findViewById(R.id.next_button3);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOrder();
            }
        });



    }


    public void checkOrder() {
        databaseHelper = new DatabaseHelper(ScanProductCopyActivity.this);
        String s = quantity.getText().toString();
        if (prodName != null && price != null) {
            if (s.equals("0") || s.equals("")) {
                builderPopUp("please insert quantity");
            } else {
                Double newPrice = Double.parseDouble(productModel.getPrice())*Integer.parseInt(s);
                productModel2 = new ProductModel(productModel.getId(), productModel.getBarcode(), productModel.getProductName(), newPrice.toString(), Integer.parseInt(s));
                databaseHelper.addOrderProduct(productModel2);
                //Toast.makeText(getApplicationContext(), productModel.toString1(), Toast.LENGTH_SHORT).show();
                finishAct("Product successfully added");
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else {
            builderPopUp("Please insert a product");
        }
    }

    public void builderPopUp(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanProductCopyActivity.this);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void finishAct(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanProductCopyActivity.this);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                finish();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }



}