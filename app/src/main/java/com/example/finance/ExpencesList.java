package com.example.finance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExpencesList extends ArrayAdapter<Expences> {
    private Activity context;
    List<Expences> expences;

    public ExpencesList(Activity context, List<Expences> expences) {
        super(context, R.layout.layout_expence_list, expences);
        this.context = context;
        this.expences = expences;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_expence_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Expences expence = expences.get(position);
        textViewName.setText(expence.getExpen());
        textViewGenre.setText(expence.getEncategory());

        return listViewItem;
    }
}