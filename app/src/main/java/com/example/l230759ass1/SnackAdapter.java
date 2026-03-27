package com.example.l230759ass1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SnackAdapter extends ArrayAdapter<Snack> {

    public SnackAdapter(Context context, ArrayList<Snack> snacks) {
        super(context, 0, snacks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.snack_item, parent, false);
        }

        Snack snack = getItem(position);

        ImageView img = convertView.findViewById(R.id.imgSnack);
        TextView name = convertView.findViewById(R.id.txtSnackName);
        TextView price = convertView.findViewById(R.id.txtSnackPrice);
        TextView qty = convertView.findViewById(R.id.txtQuantity);
        TextView plus = convertView.findViewById(R.id.btnPlus);
        TextView minus = convertView.findViewById(R.id.btnMinus);

        img.setImageResource(snack.getImageResId());
        name.setText(snack.getName());
        price.setText("$" + snack.getPrice());
        qty.setText(String.valueOf(snack.getQuantity()));

        plus.setOnClickListener(v -> {
            snack.increment();
            qty.setText(String.valueOf(snack.getQuantity()));
        });

        minus.setOnClickListener(v -> {
            snack.decrement();
            qty.setText(String.valueOf(snack.getQuantity()));
        });

        return convertView;
    }
}