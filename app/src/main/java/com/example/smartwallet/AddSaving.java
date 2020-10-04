package com.example.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddSaving extends AppCompatActivity {
    Button addsavingbtn;
    EditText addsavingname,addsavingamount;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving);

        addsavingbtn = findViewById(R.id.addsavingBtn);
        addsavingname = findViewById(R.id.addsavingname);
        addsavingamount = findViewById(R.id.addsavingamount);


        fStore      = FirebaseFirestore.getInstance();


        addsavingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference df = fStore.collection("Savings");
                Map<String,Object> savinginfo = new HashMap<>();
                savinginfo.put("addsavingname", addsavingname.getText().toString() );
                savinginfo.put("addsavingamount", addsavingamount.getText().toString());

                df.add(savinginfo);
                Toast.makeText(AddSaving.this, "Savings Added !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ViewSavings.class));

            }
        });

    }
}