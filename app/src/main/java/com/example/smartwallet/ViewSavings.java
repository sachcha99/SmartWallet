package com.example.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ViewSavings extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_savings);

        fStore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.savingview);



        //query
        Query query = fStore.collection("Savings");
        //recyle option
        FirestoreRecyclerOptions<SavingModel> options = new FirestoreRecyclerOptions.Builder<SavingModel>()
                .setQuery(query, new SnapshotParser<SavingModel>() {
                    @NonNull
                    @Override
                    public SavingModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return new SavingModel(snapshot.getId(),snapshot.getString("addsavingname"),snapshot.getString("addsavingamount"));
                    }
                })
                .build();

        adapter = new FirestoreRecyclerAdapter<SavingModel, SavingViewHolder>(options) {
            @NonNull
            @Override
            public SavingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_saving_item, parent, false);

                return new SavingViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SavingViewHolder holder, int position, @NonNull SavingModel model) {
                holder.model = model;
                holder.listaddsavingname.setText(model.getAddsavingname());
                holder.listaddsavingamount.setText(model.getAddsavingamount());





            }
        };


        //view holder

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this ));
        mFirestoreList.setAdapter(adapter);




    }

    private class SavingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public SavingModel model;
        private TextView listaddsavingname;;
        private TextView listaddsavingamount;



        public SavingViewHolder(@NonNull View itemView) {
            super(itemView);
            listaddsavingname = itemView.findViewById(R.id.listaddsavingname);
            listaddsavingamount = itemView.findViewById(R.id.listaddsavingamount);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent   = new Intent(getApplicationContext(), EditSavings.class);
            intent.putExtra("model", model);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}