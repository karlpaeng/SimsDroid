package com.example.softwareengineering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class RegisterProductActivity extends AppCompatActivity {

    private String barCode;
    private EditText productName1;
    private EditText price;
    private EditText amountInStock;
    private TextView barcode_scanned;
    ProductModel productModel;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_product);

        barcode_scanned = findViewById(R.id.barcode_scanned);
        barcode_scanned.setVisibility(View.INVISIBLE);

        Button next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (barCode != null) {
                    try {
                        productModel = new ProductModel(-1,  barCode, productName1.getText().toString(),price.getText().toString(), Integer.parseInt(amountInStock.getText().toString()));
                        databaseHelper.addInventoryProduct(productModel);

                        barcode_scanned.setVisibility(View.INVISIBLE);
                        barCode = null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProductActivity.this);
                        builder.setMessage("Product was added to the Inventory");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                goToMain();
                                dialogInterface.dismiss();
                            }
                        });

                        builder.setNeutralButton("Add Product again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });

                        builder.show();

                    } catch (Exception e) {
                        if (e.toString().contains("EditText.getText()")) {
                            Toast.makeText(RegisterProductActivity.this, "No barcode", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(RegisterProductActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        productModel = new ProductModel(-1, "error", "n/a", "n/a", 0);
                    }


                    databaseHelper = new DatabaseHelper(RegisterProductActivity.this);

                } else { Toast.makeText(RegisterProductActivity.this, "Please Insert Barcode", Toast.LENGTH_SHORT).show(); }
            }
        });




        Button btscan2 = findViewById(R.id.btscan2);
        btscan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(RegisterProductActivity.this);
                integrator.setPrompt("For flash use volume up key");
                integrator.setBeepEnabled(false);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(Capture.class);
                integrator.initiateScan();
            }
        });

        Button no_barcode = findViewById(R.id.no_barcode);
        no_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                productName1 = findViewById(R.id.productName1);
                price = findViewById(R.id.price);
                amountInStock = findViewById(R.id.amountInStock);

                try{
                if(productName1 != null && price != null && amountInStock != null){
                    productModel = new ProductModel(-1,  "N/A", productName1.getText().toString(),price.getText().toString(), Integer.parseInt(amountInStock.getText().toString()));
                    databaseHelper = new DatabaseHelper(RegisterProductActivity.this);
                    databaseHelper.addInventoryProduct(productModel);
                    productName1=price=amountInStock=null;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProductActivity.this);
                    builder.setMessage("Product was added to the Inventory");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            goToMain();
                            dialogInterface.dismiss();
                        }
                    });

                    builder.setNeutralButton("Add Product again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });

                    builder.show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProductActivity.this);
//                    builder.setMessage("Product was added to the Inventory");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int which) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    builder.show();

                } else {
                    Toast.makeText(RegisterProductActivity.this, "Please complete the inputs", Toast.LENGTH_SHORT).show();
                }
                }catch (Exception e) {Toast.makeText(RegisterProductActivity.this, e.toString(), Toast.LENGTH_SHORT).show();}

            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentresult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentresult.getContents() != null){

            databaseHelper = new DatabaseHelper(RegisterProductActivity.this);
            barcode_scanned.setVisibility(View.VISIBLE);
            barCode = intentresult.getContents();

            try {
                if (barCode != null || !barCode.equals("")) {
                    if (databaseHelper.ifInventoryProductExists(barCode)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProductActivity.this);
                        builder.setMessage("This product's barcode already exists, it's existing data can only be updated");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                barcode_scanned.setText("barcode already exists!");
                                int color = Integer.parseInt("DF6154", 16)+0xFF000000;
                                barcode_scanned.setTextColor(color);
                                barCode = null;
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            } catch (Exception e) {
                Log.d("ifprodexists", e.toString());
            }



            productName1 = findViewById(R.id.productName1);
            price = findViewById(R.id.price);
            amountInStock = findViewById(R.id.amountInStock);


        } else {
            Toast.makeText(getApplicationContext(), "You did not scan anything", Toast.LENGTH_SHORT).show();
        }
    }



    protected static void toast_and_delay(String msg, Context context){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        }, 3000);
    }

    protected void goToMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void builderPopUp(String s, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(s);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


}