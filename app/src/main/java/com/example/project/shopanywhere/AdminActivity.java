package com.example.project.shopanywhere;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project.shopanywhere.R;
import com.example.project.shopanywhere.fragments.AddNewAdmin;
import com.example.project.shopanywhere.fragments.AddStore;
import com.example.project.shopanywhere.fragments.AddStoreItem;
import com.example.project.shopanywhere.fragments.ManageAdmin;
import com.example.project.shopanywhere.fragments.ManageItem;
import com.example.project.shopanywhere.fragments.ManageStore;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bar = getSupportActionBar();
        bar.setTitle("Add Store");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new AddStore()).commit();
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





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addstore)
            SwapFragment(new AddStore(),"Add Store");
        else if (id == R.id.additem)
            SwapFragment(new AddStoreItem(),"Add Item");
        else if (id == R.id.manageitems)
            SwapFragment(new ManageItem(),"Manage Item");
        else if (id == R.id.managestore)
            SwapFragment(new ManageStore(),"Manage Store");
        else if(id == R.id.addadmin)
            SwapFragment(new AddNewAdmin(),"Add Admin");
        else if(id == R.id.manageAdmin)
            SwapFragment(new ManageAdmin(),"Manage Admin");
        else
            finish();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SwapFragment(Fragment fragment,String name){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        bar.setTitle(name);
    }
}
