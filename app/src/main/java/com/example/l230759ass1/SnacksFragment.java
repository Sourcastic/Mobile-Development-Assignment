package com.example.l230759ass1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    private ArrayList<Snack> snackList;

    public SnacksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_snacks, container, false);

        ListView listView = view.findViewById(R.id.lvSnacks);
        Button btnContinue = view.findViewById(R.id.btnConfirm);

        snackList = new ArrayList<>();

        snackList.add(new Snack(R.drawable.popcorn, "Popcorn", 5.00));
        snackList.add(new Snack(R.drawable.nachos, "Nachos", 6.50));
        snackList.add(new Snack(R.drawable.soft_drink, "Soft Drink", 3.00));
        snackList.add(new Snack(R.drawable.candy_mix, "Candy Mix", 4.00));

        SnackAdapter adapter = new SnackAdapter(requireContext(), snackList);
        listView.setAdapter(adapter);

        btnContinue.setOnClickListener(v -> {

            int qtyPopcorn = snackList.get(0).getQuantity();
            int qtyNachos = snackList.get(1).getQuantity();
            int qtyDrink = snackList.get(2).getQuantity();
            int qtyCandy = snackList.get(3).getQuantity();

            double total = 0;
            for (Snack s : snackList) {
                total += s.getPrice() * s.getQuantity();
            }

            MainActivity activity = (MainActivity) requireActivity();
            activity.setSnacksData(total, qtyPopcorn, qtyNachos, qtyDrink, qtyCandy);

            startActivity(new Intent(requireContext(), TicketSummary.class));
        });

        return view;
    }
}