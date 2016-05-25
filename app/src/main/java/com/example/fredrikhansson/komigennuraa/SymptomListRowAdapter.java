package com.example.fredrikhansson.komigennuraa;

/**
 * Created by Felix on 2016-05-22.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class SymptomListRowAdapter extends ArrayAdapter<String> {

    private final Activity activity;
    private final List<String> symptoms;
    private String selectedSymptom;
    private boolean symptomSet;

    public SymptomListRowAdapter(Activity act, List<String> arrayList, String selectedSymptom) {
        super(act,android.R.layout.simple_list_item_1, arrayList);
        this.selectedSymptom=selectedSymptom;
        this.activity = act;
        this.symptoms = arrayList;
        symptomSet=false;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        String item = getItem(position);

        return (item.equals(selectedSymptom)) ? 0 : 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position,convertView,parent);

        switch (getItemViewType(position)) {
            case 0:
                view.setBackgroundColor(this.getContext().getResources().getColor(R.color.lightblue));
                break;
            case 1:
                break;
        }

        return view;
    }

}
