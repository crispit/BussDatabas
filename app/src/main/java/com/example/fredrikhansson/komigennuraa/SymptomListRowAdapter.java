package com.example.fredrikhansson.komigennuraa;

/**
 * Created by Felix on 2016-05-22.
 */

import android.app.Activity;
import android.content.Context;
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
    private String symptom;
    private final int row;
    private String selectedSymptom;

    public SymptomListRowAdapter(Activity act, List<String> arrayList, String selectedSymptom) {
        super(act,android.R.layout.simple_list_item_1, arrayList);
        this.selectedSymptom=selectedSymptom;
        this.activity = act;
        this.row = R.layout.row;
        this.symptoms = arrayList;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        //here set your color as per position
        if (selectedSymptom.equals(symptoms.get(position))){
            view.setBackgroundResource(R.drawable.list_bg_uncompleted);
        }

        return view;
    }

    public class ViewHolder {

        public TextView errorSymptom, errorGrade, errorComment, errorDate;

    }
}
