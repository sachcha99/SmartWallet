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

public class add_Expences extends AppCompatActivity {

    EditText encomeTxt;
    Button btnSubmit;
    Spinner spinner;
    DatabaseReference dbRef;

    Expences expence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expences);

        Button in =findViewById(R.id.bt1);
        Button en =findViewById(R.id.bt2);

        en.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(add_Expences.this,Expences_details.class);
                startActivity(intent);

            }

        });

        in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(add_Expences.this,income_details.class);
                startActivity(intent);

            }

        });


        encomeTxt = (EditText) findViewById(R.id.incomeTxt);
        spinner = (Spinner) findViewById(R.id.spinnerIn);

        btnSubmit = (Button) findViewById(R.id.btnIN);


        expence = new Expences();

        dbRef = FirebaseDatabase.getInstance().getReference("Expence");

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                String expenceT = encomeTxt.getText().toString().trim();
                String expenceC = spinner.getSelectedItem().toString();

                //checking if the value is provided
                if (!TextUtils.isEmpty(expenceT)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Income
                    String id = dbRef.push().getKey();

                    //creating an Income Object
                    Expences expences = new Expences(id,expenceT,expenceC);

                    //Saving the Income
                    dbRef.child(id).setValue(expences);

                    //setting edittext to blank again
                    encomeTxt.setText("");

                    //displaying a success toast
                    Toast.makeText(getApplicationContext(), "Expence added", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(add_Expences.this,Expences_details.class);
                    startActivity(intent);
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(getApplicationContext(), "Please enter an Expence", Toast.LENGTH_LONG).show();
                }





                    }

        });


    }

}
