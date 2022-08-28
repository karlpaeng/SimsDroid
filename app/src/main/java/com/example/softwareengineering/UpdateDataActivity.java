package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDataActivity extends AppCompatActivity {

    TextView productName2;
    TextView price2;
    TextView amountInStock2;
    ProductModel productModel;
    ProductModel productModel2 = new ProductModel();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateDataActivity.this);
        try {
            productModel = databaseHelper.getTempObject();
        }catch (Exception e){
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);


        productName2 = findViewById(R.id.productName2);
        price2 = findViewById(R.id.price2);
        amountInStock2 = findViewById(R.id.amountInStock2);
//

        productName2.setText(productModel.getProductName());
        price2.setText(productModel.getPrice());
        amountInStock2.setText(""+productModel.getQuantity());


//    String product = productName2.getText().toString();
//    String price = price2.getText().toString();
//    int amt = Integer.parseInt(amountInStock2.getText().toString());


        Button update1 = findViewById(R.id.update1);
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String product = productName2.getText().toString();
                    String price = price2.getText().toString();
                    int amt = Integer.parseInt(amountInStock2.getText().toString());
                    int id = productModel.getId();

                    productModel2.setId(id);
                    productModel2.setBarcode(productModel.getBarcode());
                    productModel2.setProductName(product);
                    productModel2.setPrice(price);
                    productModel2.setQuantity(amt);
                    //Toast.makeText(getApplicationContext(), productModel2.toString(), Toast.LENGTH_SHORT).show();
                    databaseHelper.newTempUpdate(productModel2);
                    databaseHelper.editInventoryProduct();
                    Intent intent = new Intent(getApplicationContext(), ViewInventoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e){
                    //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    //@Override
//    protected void onNewIntent(Intent intent){
//        super.onNewIntent(intent);
//        ProductModel productModel = (ProductModel) getIntent().getSerializableExtra("ID");
//        if(productModel != null){
//            Toast.makeText(getApplicationContext(), productModel.toString(), Toast.LENGTH_SHORT).show();
//            //productModel.toString();
//            //databaseHelper.editInventoryProduct(Integer.parseInt(uid), productName2.getText().toString(), Double.parseDouble(price2.getText().toString()), Integer.parseInt(amountInStock2.getText().toString()));
//        }
//    }
}

