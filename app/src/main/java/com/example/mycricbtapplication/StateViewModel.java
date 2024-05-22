package com.example.mycricbtapplication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StateViewModel extends ViewModel {
    public MutableLiveData<Double> temperature =new MutableLiveData<>();

    public MutableLiveData<Double> gyroX =new MutableLiveData<>();
    public MutableLiveData<Double> gyroY =new MutableLiveData<>();
    public MutableLiveData<Double> gyroZ =new MutableLiveData<>();

    public MutableLiveData<Double> accX =new MutableLiveData<>();
    public MutableLiveData<Double> accY =new MutableLiveData<>();
    public MutableLiveData<Double> accZ =new MutableLiveData<>();

    public MutableLiveData<Double> soundLiveM =new MutableLiveData<>();

}
