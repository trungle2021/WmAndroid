package com.example.wmandroid;

import static com.example.wmandroid.Utils.Regex.isValidEmail;
import static com.example.wmandroid.Utils.Regex.isValidName;
import static com.example.wmandroid.Utils.Regex.isValidPhone;
import static com.example.wmandroid.Utils.Regex.phone_vietnamese;
import static com.example.wmandroid.Utils.SD_CLIENT.stepSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.example.wmandroid.DTO.RegisterCustomerDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.Utils.Regex;
import com.example.wmandroid.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;
    StepsView stepsView;
    EditText firstName;
    EditText lastName;
    EditText phone;
    EditText email;
    EditText address;
    RadioGroup radioGenderGroup;
    RadioButton radioButton;
    RegisterCustomerDTO registerDTO = new RegisterCustomerDTO();


    int current_state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        init();

        stepsView = signUpBinding.stepsView;

        stepsView.setLabels(stepSignUp)
                .setBarColorIndicator(Color.BLACK)
                .setProgressColorIndicator(getResources().getColor(R.color.colorAccent))
                .setLabelColorIndicator(getResources().getColor(com.anton46.stepsview.R.color.orange))
                .setCompletedPosition(0)
                .drawView();

        stepsView.setCompletedPosition(current_state);
        int length = stepSignUp.length;

        signUpBinding.btnSubmitPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validSignUp1()) {
                    if (current_state < (length - 1)) {
                        current_state += 1;
                        signUpBinding.stepsView.setCompletedPosition(current_state).drawView();
                    }

                    Intent intent = new Intent(SignUpActivity.this, SignUp2Activity.class);
                    intent.putExtra("personalInfo", registerDTO);
                    intent.putExtra("current_state",current_state);
                    intent.putExtra("length",length);
                    startActivity(intent);
                }

            }
        });


    }

    public boolean validSignUp1() {
        if (TextUtils.isEmpty(firstName.getText()) ||
                TextUtils.isEmpty(lastName.getText()) ||
                TextUtils.isEmpty(phone.getText()) ||
                TextUtils.isEmpty(email.getText()) ||
                TextUtils.isEmpty(address.getText())) {
            Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!isValidName(firstName.getText().toString()) || !isValidName(lastName.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "Please enter a valid name containing only letters and spaces. Special characters and numbers are not allowed.", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!isValidPhone(phone.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "The phone number you entered does not correct. Please enter a valid phone number starting with 84 or 0", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!isValidEmail(email.getText().toString())) {
                Toast.makeText(SignUpActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return false;
            }
            registerDTO.setFirst_name(firstName.getText().toString());
            registerDTO.setLast_name(lastName.getText().toString());
            registerDTO.setPhone(phone.getText().toString());
            registerDTO.setEmail(email.getText().toString());
            registerDTO.setAddress(address.getText().toString());
            registerDTO.setGender(getGender());
            return true;
        }
    }


    public void init() {
        firstName = signUpBinding.tvFirstname;
        lastName = signUpBinding.tvLastName;
        phone = signUpBinding.tvPhone;
        email = signUpBinding.tvEmail;
        address = signUpBinding.tvAddress;
        radioGenderGroup = signUpBinding.radioGenderGroup;
    }

    public String getGender() {
        // get selected radio button from radioGroup
        int selectedId = radioGenderGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }


}