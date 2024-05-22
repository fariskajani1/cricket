package com.example.mycricbtapplication;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class LineFragment extends Fragment {

    private LineChart lineChart;
    private StateViewModel model;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model=new ViewModelProvider(requireActivity()).get(StateViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_line, container, false);

        lineChart=view.findViewById(R.id.chart1);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);

        LineData data = new LineData();
        data.addDataSet(createSet());
        data.addDataSet(createSet2());
        data.addDataSet(createSet3());

        lineChart.setData(data);
        lineChart.setNoDataText("no chart data");
        lineChart.invalidate();

        model.gyroX.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double integer) {
                addEntry(integer,0);

            }
        });

        model.gyroY.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double integer) {
                addEntry(integer,1);

            }
        });

        model.gyroZ.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double integer) {
                addEntry(integer,2);

            }
        });




        return view;


    }
    private void addEntry(double value,int index) {

        LineData data = lineChart.getData();

        ILineDataSet randomSet= data.getDataSetByIndex(index);
        data.addEntry(new Entry(randomSet.getEntryCount(),(float)value),index);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRangeMaximum(60);
        //chart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        lineChart.moveViewTo(data.getEntryCount() - 61, 50f, YAxis.AxisDependency.LEFT);

    }
    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "GeroX");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
    private LineDataSet createSet2() {

        LineDataSet set = new LineDataSet(null, "GeroY");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(255, 255, 0));
        set.setCircleColor(Color.rgb(0, 99, 99));
        set.setHighLightColor(Color.rgb(0, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

    private LineDataSet createSet3() {

        LineDataSet set = new LineDataSet(null, "GeroZ");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(0, 99, 180));
        set.setCircleColor(Color.rgb(0, 0, 99));
        set.setHighLightColor(Color.rgb(0, 0, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }
}