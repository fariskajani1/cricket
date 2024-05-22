package com.example.mycricbtapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;


public class ClockFragment extends Fragment {
        private StateViewModel model;
        public ClockFragment() {

        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            model=new ViewModelProvider(requireActivity()).get(StateViewModel.class);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_clock, container, false);

            HalfGauge gauge = view.findViewById(R.id.halfGauge);

            Range range3 = new Range();
            range3.setColor(Color.parseColor("#00b20b"));
            range3.setFrom(0);
            range3.setTo(25);



            gauge.addRange(range3);



            gauge.setMinValue(0);
            gauge.setMaxValue(100);
            gauge.setValue(0);

            model.temperature.observe(getViewLifecycleOwner(), new Observer<Double>() {
                @Override
                public void onChanged(Double val) {
                    gauge.setValue(val);

                }
            });
            return view;
        }
    }