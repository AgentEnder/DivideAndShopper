package com.craigorycoppola.divideandshopper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Product> products;
    ArrayList<String> shoppers;
    LinearLayout product_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        products = new ArrayList<>();
        shoppers = new ArrayList<>();
        shoppers.add("Kristen");
        shoppers.add("Dalton");
        shoppers.add("Craigory");
        shoppers.add("Isaiah");
        product_display = findViewById(R.id.product_bar_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.add_product_popup, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                popupView.findViewById(R.id.add_product_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText nameText = popupView.findViewById(R.id.new_product_name);
                        final EditText priceText = popupView.findViewById(R.id.new_product_price);

                        double price = 0;
                        try {
                            price = Double.parseDouble(priceText.getText().toString());
                        } catch (NumberFormatException parseError) {

                        }
                        products.add(new Product(nameText.getText().toString(), price));
                        popupWindow.dismiss();
                        refreshProductDisplay();
                    }
                });

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressWarnings("InflateParams")
    public void refreshProductDisplay() {
        product_display.removeAllViews();
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (final Product product : products) {
            final View productView = inflater.inflate(R.layout.product_bar, null);
            productView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View popupView = inflater.inflate(R.layout.shopper_select_popup, null);
                    // create the popup window
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    boolean focusable = true; // lets taps outside the popup also dismiss it
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    popupWindow.showAtLocation(productView, Gravity.CENTER, 0, 0);
                    final LinearLayout container = popupView.findViewById(R.id.shopperContainer);
                    container.removeAllViews();
                    final Hashtable<String, CheckBox> lookup = new Hashtable<>();
                    for (String shopper : shoppers) {
                        CheckBox checkBox = new CheckBox(getApplicationContext());
                        checkBox.setText(shopper);
                        checkBox.setTextSize(24);
                        checkBox.setTextColor(getColor(R.color.white));
                        container.addView(checkBox);
                        lookup.put(shopper, checkBox);
                    }
                    popupView.findViewById(R.id.shopper_confirm_btn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (String s : shoppers
                                    ) {
                                if (lookup.get(s).isChecked()) {
                                    product.add_buyer(s);
                                }
                            }
                        }
                    });
                }
            });
            final TextView quantityView = productView.findViewById(R.id.qDisplay);
            String display = product.get_name() + "($" + String.valueOf(product.get_price()) + ")";
            ((TextView) productView.findViewById(R.id.product_bar_name)).setText(display);
            quantityView.setText(String.valueOf(product.get_quantity()));
            productView.findViewById(R.id.qDecrease).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.decrement_quantity();
                    int q = product.get_quantity();
                    if (q < 1) products.remove(product);
                    refreshProductDisplay();
                    quantityView.setText(String.valueOf(q));
                }
            });
            productView.findViewById(R.id.qIncrease).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product.increment_quantity();
                    int q = product.get_quantity();
                    if (q < 1) products.remove(product);
                    refreshProductDisplay();
                    quantityView.setText(String.valueOf(q));
                }
            });
            productView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            product_display.addView(productView);
        }
    }
}
