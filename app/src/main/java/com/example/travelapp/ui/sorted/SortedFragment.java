package com.example.travelapp.ui.sorted;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.travelapp.DestinationActivity;
import com.example.travelapp.NavigationDrawerActivity;
import com.example.travelapp.R;
import com.example.travelapp.databinding.FragmentSortedBinding;
import com.example.travelapp.ui.all.AllFragment;


public class SortedFragment extends Fragment {

    private FragmentSortedBinding binding;
    LinearLayout linearLayout;
    Context thisContext;
    NavigationDrawerActivity navigationDrawerActivity;
    Intent destinationIntent;
    SortedViewModel sortedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sortedViewModel =
                new ViewModelProvider(this).get(SortedViewModel.class);

        thisContext = container.getContext();

        destinationIntent = new Intent(thisContext, DestinationActivity.class);
        navigationDrawerActivity = (NavigationDrawerActivity) getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sorted, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sortedViewModel = new ViewModelProvider(this).get(SortedViewModel.class);
        binding.setLifecycleOwner(this);

        sortDestination("asc");
        binding.imageView.setVisibility(View.INVISIBLE);


        binding.radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.ascending:
                    sortedViewModel.setSelectedOption(1);
                    sortDestination("asc");
                    binding.imageView.setVisibility(View.VISIBLE);
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.ascending));
                    break;
                case R.id.descending:
                    sortedViewModel.setSelectedOption(2);
                    sortDestination("des");
                    binding.imageView.setVisibility(View.VISIBLE);
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.descinding));
                    break;
            }
            binding.imageView.setVisibility(View.INVISIBLE);
        });
    }


    public void sortDestination(String sortMethod) {
        navigationDrawerActivity = (NavigationDrawerActivity) getActivity();
        Cursor sortedDes = navigationDrawerActivity.sortedFragment(sortMethod);
        int i = 1;

        while (sortedDes.moveToNext()) {
            if (i == 1) {
                binding.city1.setText(sortedDes.getString(0));
                binding.cost1.setText(sortedDes.getString(1));
                binding.city1.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city1.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });

            } else if (i == 2) {
                binding.city2.setText(sortedDes.getString(0));
                binding.cost2.setText(sortedDes.getString(1));
                binding.city2.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city2.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            } else if (i == 3) {
                binding.city3.setText(sortedDes.getString(0));
                binding.cost3.setText(sortedDes.getString(1));
                binding.city3.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city3.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            } else if (i == 4) {
                binding.city4.setText(sortedDes.getString(0));
                binding.cost4.setText(sortedDes.getString(1));
                binding.city4.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city4.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            } else if (i == 5) {
                binding.city5.setText(sortedDes.getString(0));
                binding.cost5.setText(sortedDes.getString(1));
                binding.city5.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city5.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            } else if (i == 6) {
                binding.city6.setText(sortedDes.getString(0));
                binding.cost6.setText(sortedDes.getString(1));
                binding.city6.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city6.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            } else if (i == 7) {
                binding.city7.setText(sortedDes.getString(0));
                binding.cost7.setText(sortedDes.getString(1));
                binding.city7.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city7.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            } else if (i == 8) {
                binding.city8.setText(sortedDes.getString(0));
                binding.cost8.setText(sortedDes.getString(1));
                binding.city8.setOnClickListener(view -> {
                    DestinationActivity.city = binding.city8.getText().toString();
                    SortedFragment.this.startActivity(destinationIntent);
                });
            }


            i++;
        }

    }

}
