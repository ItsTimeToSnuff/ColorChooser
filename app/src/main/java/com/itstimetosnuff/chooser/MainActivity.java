package com.itstimetosnuff.chooser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private MainMenuFragment menuFragment = new MainMenuFragment();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
        sharedPreferences = getSharedPreferences(MainActivity.class.getCanonicalName(), MODE_PRIVATE);
        loadFragment(menuFragment);
    }

    void loadFragment(Fragment fragment) {
        fragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.cont, fragment);
        fragmentTransaction.commit();
    }
}