package com.example.softwareengineering;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class ScanProductActivity extends AppCompatActivity{

    //private final static int MY_REQUEST_CODE = 1;
    private TextView prodName;
    private TextView price;
    private EditText quantity;
    private String barCode;

    private Button up;
    private Button down;
    private int qty;
    ProductModel productModel;
    ProductModel productModel2;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_product);


        prodName = findViewById(R.id.productName);
        price = findViewById(R.id.price);

        quantity = findViewById(R.id.qty);
        quantity.setVisibility(View.INVISIBLE);


        up = findViewById(R.id.upBtn);
        up.setVisibility(View.INVISIBLE);
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



        down = findViewById(R.id.downBtn);
        down.setVisibility(View.INVISIBLE);
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


        Button btscan = findViewById(R.id.btscan);
        btscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanProductActivity.this);
                integrator.setPrompt("For flash use volume up key");
                integrator.setBeepEnabled(false);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(Capture.class);
                integrator.initiateScan();
            }
        });


        Button nxtBtn = findViewById(R.id.next_button);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOrder();
            }
        });


        Button noBarCode = findViewById(R.id.noBarCode);
        noBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcode_act = new Intent(ScanProductActivity.this, NoBarcodeActivity.class);
                startActivity(barcode_act);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentresult.getContents() != null) {
            databaseHelper = new DatabaseHelper(ScanProductActivity.this);
            try {
                barCode = intentresult.getContents();
                if(databaseHelper.ifOrderBarcodeExists(barCode)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScanProductActivity.this);
                    builder.setTitle("Failed");
                    builder.setMessage("You already scanned the product");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            Intent intent = new Intent(getApplicationContext(), ScanProductActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }


                productModel = databaseHelper.searchBarcode(barCode);
                prodName.setText(productModel.getProductName());
                price.setText(productModel.getPrice());

                if (!prodName.getText().toString().equals("") && !price.getText().toString().equals("")) {
                    quantity.setVisibility(View.VISIBLE);
                    up.setVisibility(View.VISIBLE);
                    down.setVisibility(View.VISIBLE);
                }

            } catch (Exception e){
                //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("No Available barcode", e.toString());

            }

        } else {
            Toast.makeText(getApplicationContext(), "You did not scan anything", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkOrder() {
        String s = quantity.getText().toString();
        if (prodName != null && price != null) {
            if (s.equals("0") || s.equals("")) {
                builderPopUp("please insert quantity");
            } else {
                Double newPrice = Double.parseDouble(productModel.getPrice())*Integer.parseInt(s);
                productModel2 = new ProductModel(productModel.getId(), barCode, productModel.getProductName(), newPrice.toString(), Integer.parseInt(s));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanProductActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ScanProductActivity.this);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
//                Intent goToMain2 = new Intent(getApplicationContext(), MainActivity2.class);
//                goToMain2.putExtra("prodName", prodName.getText().toString());
//                goToMain2.putExtra("price", price.getText().toString());
//                startActivity(goToMain2);
                finish();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }





}
