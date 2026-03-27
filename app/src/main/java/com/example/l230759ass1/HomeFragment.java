package com.example.l230759ass1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        viewPager.setAdapter(new HomeTabAdapter(requireActivity()));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) ->
                tab.setText(position == 0 ? "Now Showing" : "Coming Soon")
        ).attach();

        // Setup three-dots menu icon
        ImageView menuDots = view.findViewById(R.id.imgMenuDots);
        menuDots.setOnClickListener(v -> showPopupMenu(v));
    }

    private void showPopupMenu(View anchor) {
        PopupMenu popup = new PopupMenu(requireContext(), anchor);
        popup.getMenu().add("View Last Booking");
        popup.setOnMenuItemClickListener(item -> {
            if ("View Last Booking".equals(item.getTitle())) {
                showLastBooking();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showLastBooking() {
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("last_booking", Context.MODE_PRIVATE);
        String movie = prefs.getString("movie_name", null);
        int seats = prefs.getInt("seat_count", 0);
        float price = prefs.getFloat("total_price", 0f);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        if (movie == null || seats == 0) {
            builder.setMessage("No previous booking found.");
        } else {
            builder.setMessage("Last Booking\nMovie: " + movie +
                    "\nSeats: " + seats +
                    "\nTotal Price: $" + String.format("%.2f", price));
        }
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    static class HomeTabAdapter extends FragmentStateAdapter {
        public HomeTabAdapter(@NonNull FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return position == 0 ? new NowShowingFragment() : new ComingSoonFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}