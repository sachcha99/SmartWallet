package com.example.finance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_Income extends AppCompatActivity {

    EditText incomeTxt;
    Button btnSubmit;
    Spinner spinner;
    DatabaseReference dbRef;

    Income income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income);


        Button in =findViewById(R.id.bt1);
        Button en =findViewById(R.id.bt2);

        en.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(add_Income.this,Expences_details.class);
                startActivity(intent);

            }

        });

        in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(add_Income.this,income_details.class);
                startActivity(intent);

            }

        });

        incomeTxt = (EditText) findViewById(R.id.incomeTxt);
        spinner = (Spinner) findViewById(R.id.spinnerIn);

        btnSubmit = (Button) findViewById(R.id.btnIN);


        income = new Income();

        dbRef = FirebaseDatabase.getInstance().getReference("Income");

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                String incomeT = incomeTxt.getText().toString().trim();
                String incomeC = spinner.getSelectedItem().toString();

                //checking if the value is provided
                if (!TextUtils.isEmpty(incomeT)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Income
                    String id = dbRef.push().getKey();

                    //creating an Income Object
                    Income incomes = new Income(id,incomeT,incomeC);

                    //Saving the Income
                    dbRef.child(id).setValue(incomes);

                    //setting edittext to blank again
                    incomeTxt.setText("");

                    //displaying a success toast
                    Toast.makeText(getApplicationContext(), "Income added", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(add_Income.this,income_details.class);
                    startActivity(intent);
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(getApplicationContext(), "Please enter an Income", Toast.LENGTH_LONG).show();
                }





                    }

        });


    }

}
