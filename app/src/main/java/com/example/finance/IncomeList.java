package com.example.finance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class IncomeList extends ArrayAdapter<Income> {
    private Activity context;
    List<Income> incomes;

    public IncomeList(Activity context, List<Income> incomes) {
        super(context, R.layout.layout_income_list, incomes);
        this.context = context;
        this.incomes = incomes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_income_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Income income = incomes.get(position);
        textViewName.setText(income.getIncome());
        textViewGenre.setText(income.getIncategory());

        return listViewItem;
    }
}