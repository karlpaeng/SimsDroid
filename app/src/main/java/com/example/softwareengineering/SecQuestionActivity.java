package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SecQuestionActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;

    EditText ans1;
    EditText ans2;

    Button cont2;

    private String username;
    private String password;
    String selected_question1;
    String selected_question2;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_question);

        databaseHelper = new DatabaseHelper(SecQuestionActivity.this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            username = getIntent().getStringExtra("username");
            password = getIntent().getStringExtra("password");
        }



            spinner1 = (Spinner) findViewById(R.id.spinner2);
            spinner2 = (Spinner) findViewById(R.id.spinner);


            selected_question1 = spinner1.getSelectedItem().toString();
            selected_question2 = spinner2.getSelectedItem().toString();


            ans1 = findViewById(R.id.answer1);
            ans2 = findViewById(R.id.answer2);

            cont2 = findViewById(R.id.cont2);
            cont2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        selected_question1 = spinner1.getSelectedItem().toString();
                        selected_question2 = spinner2.getSelectedItem().toString();
                        UserLogin userLogin = new UserLogin(
                                1,
                                username,
                                password,
                                selected_question1,
                                ans1.getText().toString(),
                                selected_question2,
                                ans2.getText().toString());

                        if (databaseHelper.newUserLogin(userLogin)) {
                            Toast.makeText(getApplicationContext(), "Account Successfully created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SecQuestionActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        Log.d("Error", e.toString());
                    }
                }
            });









    }
}