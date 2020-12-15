package com.ryms.airlinemanagement.Admin.Fetch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ryms.airlinemanagement.Admin.Fetch.FetchFlights.FetchFlights;
import com.ryms.airlinemanagement.Admin.Fetch.FetchUsers.FetchUsers;
import com.ryms.airlinemanagement.R;

public class AdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
    }

    public void goToFetchUser(View view){
        Intent intent = new Intent(this, FetchUsers.class);
        startActivity(intent);
    }

    public void goToFetchFlights(View view){
        Intent intent = new Intent(this, FetchFlights.class);
        startActivity(intent);
    }

    public void goToFetchPilots(View view){
        Intent intent = new Intent(this, FetchFlights.class);
        startActivity(intent);
    }
}