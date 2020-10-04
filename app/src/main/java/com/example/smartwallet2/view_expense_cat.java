package com.example.smartwallet2;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class view_expense_cat extends AppCompatActivity {
    private FirebaseFirestore fStore;
    private RecyclerView mFirestoreList;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense_cat);

        fStore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.view_income_cat);



        //query
        CollectionReference db = fStore.collection("Categories");
        Query query = db.whereEqualTo("catrgoryType", "Expenses");
        //recyle option
        FirestoreRecyclerOptions<incomemodel> options = new FirestoreRecyclerOptions.Builder<incomemodel>()
                .setQuery(query, new SnapshotParser<incomemodel>() {
                    @NonNull
                    @Override
                    public incomemodel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        return new incomemodel(snapshot.getId(),snapshot.getString("addcategoryname"),snapshot.getString("catrgoryType"));
                    }
                })
                .build();

        adapter = new FirestoreRecyclerAdapter<incomemodel, view_expense_cat.ExpenseViewHolder>(options) {
            @NonNull
            @Override
            public view_expense_cat.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item, parent, false);

                return new view_expense_cat.ExpenseViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull view_expense_cat.ExpenseViewHolder holder, int position, @NonNull incomemodel model) {
                holder.model = model;
                holder.addcategory.setText(model.getAddcategory());
                holder.catrgoryType.setText(model.getCatrgoryType());





            }
        };


        //view holder

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this ));
        mFirestoreList.setAdapter(adapter);




    }

    private class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public incomemodel model;
        private TextView addcategory;;
        private TextView catrgoryType;



        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            addcategory = itemView.findViewById(R.id.addcategory);
            catrgoryType = itemView.findViewById(R.id.catrgoryType);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent   = new Intent(getApplicationContext(), edit_income.class);
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