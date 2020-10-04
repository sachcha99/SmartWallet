package com.example.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditSavings extends AppCompatActivity {
    Button editsavingBtn,deletesavingBtn;
    EditText editsavingname,editsavingamount;
    FirebaseFirestore fStore;
    private SavingModel savm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_savings);

        editsavingBtn = findViewById(R.id.editsavingBtn);
        editsavingname = findViewById(R.id.editsavingname);
        editsavingamount = findViewById(R.id.editsavingvalue);
        deletesavingBtn =findViewById(R.id.deletesavingBtn);


        fStore      = FirebaseFirestore.getInstance();

        savm = (SavingModel) getIntent().getSerializableExtra("model");

        editsavingname.setText(savm.getAddsavingname());
        editsavingamount.setText(savm.getAddsavingamount());


        editsavingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                    SavingModel savings = new SavingModel(
                            savm.getId(),editsavingname.getText().toString(),editsavingamount.getText().toString()
                    );



                    fStore.collection("Savings").document(savm.getId())
                            .set(savings)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(EditSavings.this,"Savings Updated",Toast.LENGTH_LONG).show();
                                }
                            });

                    startActivity(new Intent(getApplicationContext(), ViewSavings.class));



            }
        });

        deletesavingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("Savings").document(savm.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditSavings.this,"Savings Deleted",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), ViewSavings.class));
                            }
                        });

            }
        });

    }



}