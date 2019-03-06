package com.example.ogzkaras.diyet_takvimi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView name, surname, username, useremail, userpassword;
    Button btnregister;
    CheckBox erkek, kadin;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        erkek = findViewById(R.id.erkek);
        kadin = findViewById(R.id.kadin);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        userpassword = findViewById(R.id.userpassword);
        btnregister = findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userregister();
            }
        });
        erkek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickerkek();
            }
        });
        kadin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickkadin();
            }
        });
    }

    public void onclickerkek() {
        gender = "true";
    }

    public void onclickkadin() {
        gender = "false";
    }

    public void userregister() {
        Intent intent = new Intent(getApplicationContext(), Register2Activity.class);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("surname", surname.getText().toString());
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("useremail", useremail.getText().toString());
        intent.putExtra("userpassword", userpassword.getText().toString());
        intent.putExtra("gender", gender.trim());
        startActivity(intent);

    }
}
