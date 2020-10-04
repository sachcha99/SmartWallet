package com.example.finance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class income_details extends AppCompatActivity {

    public static final String INCOME_VALUE = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String INCOME_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";


    Button buttonAdd;
    ListView listViewIncome;
    TextView txtTot;

    String Total;

    //a list to store all the artist from firebase database
    List<Income> incomes;

    //our database reference object
    DatabaseReference databaseIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_details);

        Button in =findViewById(R.id.bt1);
        Button en =findViewById(R.id.bt2);
        en.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(income_details.this,Expences_details.class);
                startActivity(intent);

            }

        });

        in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(income_details.this,income_details.class);
                startActivity(intent);

            }

        });

        //getting the reference of artists node
        databaseIncome = FirebaseDatabase.getInstance().getReference("Income");

        //getting views
        listViewIncome = (ListView) findViewById(R.id.in_listview);
        txtTot = (TextView) findViewById(R.id.lb_intot);
        buttonAdd = (Button) findViewById(R.id.AddIN);

        //list to store artists
        incomes = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(income_details.this,add_Income.class);
                startActivity(intent);
            }
        });

        listViewIncome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Income income = incomes.get(i);
                showUpdateDeleteDialog(income.getId(), income.getIncome());
                return false;
            }
        });









     /*   listViewIncome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                //getting the selected artist
                Income income = incomes.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent
                intent.putExtra(INCOME_ID, income.getId());
                intent.putExtra(INCOME_VALUE, income.getIncome());

                //starting the activity with intent
                startActivity(intent);
                return false;
            }
        });*/
    }




    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseIncome.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                incomes.clear();
                double tot=0;

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Income income = postSnapshot.getValue(Income.class);

                     tot = tot + Double.parseDouble(postSnapshot.child("income").getValue(String.class));


                    //adding artist to the list
                    incomes.add(income);
                }
                Total= String.valueOf(tot);
                txtTot.setText("TOTAL INCOME : LKR "+Total);

                //creating adapter
                IncomeList Incomeadapter = new IncomeList(income_details.this, incomes);
                //attaching adapter to the listview
                listViewIncome.setAdapter(Incomeadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String Id, String income) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        editTextName.setText(income);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(income);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateArtist(Id, name, genre);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(Id);
                b.dismiss();
            }
        });
    }


    private boolean updateArtist(String id, String name, String genre) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Income").child(id);

        //updating artist
        Income income = new Income(id, name, genre);
        dR.setValue(income);
        Toast.makeText(getApplicationContext(), "Income Entry Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Income").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Income Entry Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}
