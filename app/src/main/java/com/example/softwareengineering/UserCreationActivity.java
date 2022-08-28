package com.example.softwareengineering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserCreationActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText confirmpass;
    Button button;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_creation);

        databaseHelper = new DatabaseHelper(UserCreationActivity.this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmpass = findViewById(R.id.confirmpass);


        button = findViewById(R.id.cont);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();
                String confPass = confirmpass.getText().toString();

                try {
                    if(!user.equals("") && !pass.equals("") && !confPass.equals("")){
                        if (pass.equals(confPass)) {
                            Intent intent = new Intent(getApplicationContext(), SecQuestionActivity.class);
                            intent.putExtra("username", username.getText().toString());
                            intent.putExtra("password", password.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "password not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                    //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserCreationActivity.this);
            builder.setMessage("Do you want to exit app?");
            builder.setPositiveButton("exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    System.exit(0);
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            builder.show();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}