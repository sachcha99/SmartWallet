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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class edit_income extends AppCompatActivity {


    Button editcategorybutton,deletecategorybutton;
    EditText edit_category;
    Spinner edit_catrgoryType;
    FirebaseFirestore fStore;
    private incomemodel savm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);


        Spinner myspinner = (Spinner) findViewById(R.id.edit_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(edit_income.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdapter);


        editcategorybutton = findViewById(R.id.editcategorybutton);
        deletecategorybutton = findViewById(R.id.deletecategorybutton);
        edit_catrgoryType = (Spinner)findViewById(R.id.edit_spinner);
        edit_category = findViewById(R.id.edit_category);

        fStore      = FirebaseFirestore.getInstance();

        savm = (incomemodel) getIntent().getSerializableExtra("model");


        edit_category.setText(savm.getAddcategory());




        editcategorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                incomemodel category = new incomemodel(
                        savm.getId(),edit_category.getText().toString(),edit_catrgoryType.getSelectedItem().toString()
                );



                fStore.collection("Categories").document(savm.getId())
                        .set(category)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(edit_income.this,"Categories Updated",Toast.LENGTH_LONG).show();
                            }
                        });

                startActivity(new Intent(getApplicationContext(), view_income_cat.class));



            }
        });

        deletecategorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("Categories").document(savm.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(edit_income.this,"Categories Deleted",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), view_income_cat.class));
                            }
                        });

            }
        });


    }
}