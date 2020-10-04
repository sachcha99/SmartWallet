package com.example.smartwallet2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class add_categories extends AppCompatActivity {

    Button addcategorybutton;
    EditText addcategory;
    Spinner catrgoryType;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        Spinner myspinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(add_categories.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdapter);

        addcategorybutton = findViewById(R.id.addcategorybutton);
        catrgoryType = (Spinner)findViewById(R.id.spinner);
        addcategory = findViewById(R.id.addcategory);


        fStore      = FirebaseFirestore.getInstance();


        addcategorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CollectionReference df = fStore.collection("Categories");
                Map<String,Object> categoryinfo = new HashMap<>();

                categoryinfo.put("addcategoryname", addcategory.getText().toString());
                categoryinfo.put("catrgoryType", catrgoryType.getSelectedItem().toString());

                df.add(categoryinfo);
                Toast.makeText(add_categories.this, "Categories Added !", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), view_income_cat.class));


            }
        });


    }
}