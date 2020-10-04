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

public class Expences_details extends AppCompatActivity {

    public static final String EXPENCE_VALUE = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String EXPENCE_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";


    Button buttonAdd;
    ListView listViewExpence;
    TextView txtTot;

    String Total;

    //a list to store all the artist from firebase database
    List<Expences> expences;

    //our database reference object
    DatabaseReference databaseExpence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expences_details);

        Button in =findViewById(R.id.bt1);
        Button en =findViewById(R.id.bt2);

        en.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Expences_details.this,Expences_details.class);
                startActivity(intent);

            }

        });

        in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Expences_details.this,income_details.class);
                startActivity(intent);

            }

        });
        //getting the reference of artists node
        databaseExpence = FirebaseDatabase.getInstance().getReference("Expence");

        //getting views
        listViewExpence = (ListView) findViewById(R.id.in_listview);
        txtTot = (TextView) findViewById(R.id.lb_intot);
        buttonAdd = (Button) findViewById(R.id.AddIN);

        //list to store artists
        expences = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Expences_details.this,add_Expences.class);
                startActivity(intent);
            }
        });


        listViewExpence.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Expences expence = expences.get(i);
                showUpdateDeleteDialog(expence.getId(), expence.getExpen());

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
        databaseExpence.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                expences.clear();
                double tot=0;

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Expences expence = postSnapshot.getValue(Expences.class);

                     tot =tot + Double.parseDouble(postSnapshot.child("expen").getValue(String.class));


                    //adding artist to the list
                    expences.add(expence);
                }
                Total= String.valueOf(tot);
                txtTot.setText("TOTAL EXPENSES : LKR "+Total);


                //creating adapter
                ExpencesList Expenceadapter = new ExpencesList(Expences_details.this, expences);
                //attaching adapter to the listview
                listViewExpence.setAdapter(Expenceadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String Id, String income) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_exp, null);
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
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Expence").child(id);

        //updating artist
        Expences income = new Expences(id, name, genre);
        dR.setValue(income);
        Toast.makeText(getApplicationContext(), "Expense Entry Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Expence").child(id);

        //removing artist
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Expense Entry Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}
