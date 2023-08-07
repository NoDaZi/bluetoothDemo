package com.example.bledemo.datastore;

import android.os.Handler;
import android.os.Looper;


import java.util.prefs.Preferences;

public class DataStoreManager {

    public static final DataStoreManager instance = new DataStoreManager();

    private static final Handler dataStoreManagerHandler = new Handler(Looper.getMainLooper());

    private DataStoreManager(){}

    //private RxDataStore<Preferences> dataStore;

}
