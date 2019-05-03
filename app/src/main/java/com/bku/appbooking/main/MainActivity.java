package com.bku.appbooking.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.bku.appbooking.R;
import com.bku.appbooking.main.fragment.CartFragment;
import com.bku.appbooking.main.fragment.HistoryFragment;
import com.bku.appbooking.main.fragment.HomeFragment;
import com.bku.appbooking.main.fragment.PersonFragment;

public class MainActivity extends AppCompatActivity {
    CartFragment cartFragment;
    HistoryFragment historyFragment;
    HomeFragment homeFragment;
    PersonFragment personFragment;

    private Fragment getCartFragment(){
        if (cartFragment == null){
            cartFragment = new CartFragment();
        }
        return cartFragment;
    }

    private Fragment getHistoryFragment(){
        if (historyFragment == null){
            historyFragment = new HistoryFragment();
        }
        return historyFragment;
    }

    private Fragment getHomeFragment(){
        if (homeFragment == null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    private Fragment getPersonFragment(){
        if (personFragment == null){
            personFragment = new PersonFragment();
        }
        return personFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartFragment = null;
        historyFragment = null;
        homeFragment = null;
        personFragment = null;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = getHomeFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = getHistoryFragment();
                            break;
                        case R.id.nav_person:
                            selectedFragment = getPersonFragment();
                            break;
                        case R.id.nav_cart:
                            selectedFragment = getCartFragment();
                            break;
                        default:
                            selectedFragment =  getHomeFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
