package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPass2Activity extends AppCompatActivity {

    EditText user1;
    EditText pass1;
    EditText confpass1;

    Button button6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass2);

        user1 = findViewById(R.id.newuser);
        pass1 = findViewById(R.id.newpass);
        confpass1 = findViewById(R.id.confirmnewpass);

        button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = user1.getText().toString();
                String pass = pass1.getText().toString();
                String confpass = confpass1.getText().toString();

                try {

                    if(!user.equals("") && !pass.equals("") && !confpass.equals("")){
                        if (pass.equals(confpass)) {
                            Intent intent = new Intent(getApplicationContext(), SecQuestionActivity.class);
                            intent.putExtra("username", user);
                            intent.putExtra("password", pass);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "password not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}