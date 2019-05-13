package com.bku.appbooking.main;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.appbooking.R;
import com.bku.appbooking.login.LoginActivity;
import com.bku.appbooking.main.fragment.cart.CartFragment;
import com.bku.appbooking.main.fragment.history.HistoryFragment;
import com.bku.appbooking.main.fragment.home.HomeFragment;
import com.bku.appbooking.main.fragment.person.PersonFragment;
import com.bku.appbooking.search.SearchActivity;
import com.bku.appbooking.ultis.CentralRequestQueue;
import com.bku.appbooking.ultis.UserInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CartFragment cartFragment;
    HistoryFragment historyFragment;
    HomeFragment homeFragment;
    PersonFragment personFragment;
    ImageView imageView;
    TextView txName, txEmail;

    public final int IMAGE_PICKER = 1;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private Fragment getCartFragment() {
        if (cartFragment == null) {
            cartFragment = new CartFragment();
        }
        return cartFragment;
    }

    private Fragment getHistoryFragment() {
        if (historyFragment == null) {
            historyFragment = new HistoryFragment();
        }
        return historyFragment;
    }

    private Fragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    private Fragment getPersonFragment() {
        if (personFragment == null) {
            personFragment = new PersonFragment();
        }
        return personFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }
        setContentView(R.layout.activity_main);
        final String resultFragment = getIntent().getStringExtra("fragment");
        if ("cart".equals(resultFragment)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CartFragment()).commit();
        }
        cartFragment = null;
        historyFragment = null;
        homeFragment = null;
        personFragment = null;
        CentralRequestQueue.getInstance().setupAndStart(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        findViewById(R.id.left_aboutUs).setOnClickListener(this);
        findViewById(R.id.left_rate).setOnClickListener(this);
        findViewById(R.id.img_avatar).setOnClickListener(this);
        findViewById(R.id.left_logOut).setOnClickListener(this);
        findViewById(R.id.left_Login).setOnClickListener(this);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        imageView = findViewById(R.id.img_avatar);
        txName = findViewById(R.id.left_name);
        txEmail = findViewById(R.id.left_email);
        String name = UserInfo.getInstance().getName();
        String email = UserInfo.getInstance().getMail();
        String accessToken = UserInfo.getInstance().getAccessToken();
        if (accessToken == null || accessToken.equals("")) {
            LinearLayout leftLogout = (LinearLayout) findViewById(R.id.left_logOut);
            leftLogout.setVisibility(LinearLayout.GONE);
            LinearLayout leftLogin = (LinearLayout) findViewById(R.id.left_Login);
            leftLogin.setVisibility(View.VISIBLE);
        } else {
            LinearLayout leftLogin = (LinearLayout) findViewById(R.id.left_Login);
            leftLogin.setVisibility(LinearLayout.GONE);
            LinearLayout leftLogout = (LinearLayout) findViewById(R.id.left_logOut);
            leftLogout.setVisibility(View.VISIBLE);
        }
        txName.setText(name);
        txEmail.setText(email);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.ic_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
                            selectedFragment = getHomeFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_aboutUs:
                openDialogAboutUs();
                break;
            case R.id.left_rate:
                openRate();
                break;
            case R.id.img_avatar:
                OpenGallery();
                break;
            case R.id.left_logOut:
                Logout();
                break;
            case R.id.left_Login:
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;

        }
    }

    private void openRate() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_infomation);
        dialog.setCanceledOnTouchOutside(false);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
//        TextView title = dialog.findViewById(R.id.dialog_title);
        TextView content = dialog.findViewById(R.id.dialog_content);
        final RatingBar ratingBar = dialog.findViewById(R.id.simpleRatingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_ATOP);
        ratingBar.setVisibility(View.VISIBLE);

//        title.setText(getString(R.string.title_rateApp));
        content.setText(getString(R.string.content_rateApp));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String totalStars = "Total Stars:: " + ratingBar.getNumStars();
                String rating = "Rating :: " + ratingBar.getRating();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Cảm ơn sự góp ý của bạn", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    private void openDialogAboutUs() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_infomation);
        dialog.setCanceledOnTouchOutside(false);

        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
//        TextView title = dialog.findViewById(R.id.dialog_title);
        TextView content = dialog.findViewById(R.id.dialog_content);
//        title.setText("abc");
        content.setText(getString(R.string.content_aboutUs));
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void OpenGallery() {
        Intent getImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getImageIntent.setType("image/*");
        startActivityForResult(getImageIntent, IMAGE_PICKER);
    }

    private void Logout() {
        UserInfo.getInstance().setMail("");
        UserInfo.getInstance().setName("");
        UserInfo.getInstance().setAccessToken("");
        txName.setText(UserInfo.getInstance().getName());
        txEmail.setText(UserInfo.getInstance().getMail());

        LinearLayout leftLogout = (LinearLayout) this.findViewById(R.id.left_logOut);
        leftLogout.setVisibility(LinearLayout.GONE);
        LinearLayout leftLogin = (LinearLayout) this.findViewById(R.id.left_Login);
        leftLogin.setVisibility(View.VISIBLE);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            imageView.setImageURI(fullPhotoUri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }
}
