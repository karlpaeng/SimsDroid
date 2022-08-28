package com.example.softwareengineering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassActivity extends AppCompatActivity {

    TextView q1;
    TextView q2;
    EditText ans1;
    EditText ans2;
    Button _continue;
    UserLogin userLogin = new UserLogin();

    DatabaseHelper databaseHelper = new DatabaseHelper(ForgotPassActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);

        userLogin = databaseHelper.getAllLoginInfo();

        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);


        q1.setText(userLogin.getSq1());
        q2.setText(userLogin.getSq2());

        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);

        _continue = findViewById(R.id.button6);
        _continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ans1.getText().toString().equals(userLogin.getSa1()) && ans2.getText().toString().equals(userLogin.getSa2())){
                    Intent intent = new Intent(getApplicationContext(), ForgotPass2Activity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Answer", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}