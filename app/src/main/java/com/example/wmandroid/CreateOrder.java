package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateOrder extends AppCompatActivity {




    EditText txtName;
    EditText txtFee;
    Button btnCreate;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        init();
        Intent intent=getIntent();
        id= Integer.parseInt(intent.getStringExtra("id"));
        Toast.makeText(CreateOrder.this, id, Toast.LENGTH_SHORT).show();
    }
    private void init()
    {



    }



}