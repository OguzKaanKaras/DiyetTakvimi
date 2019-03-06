package com.example.ogzkaras.diyet_takvimi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case  R.id.searchcalori:
                        Intent intent1=new Intent(getApplicationContext(),SearchCaloriActivity.class);
                        startActivity(intent1);
                        break;
                    case  R.id.menu:
                        Intent intent2=new Intent(getApplicationContext(),MenuActivity.class);
                        startActivity(intent2);
                        break;
                    case  R.id.calculate:
                        Intent intent3=new Intent(getApplicationContext(),CalculateActivity.class);
                        startActivity(intent3);
                        break;
                    case  R.id.profile:
                        Intent intent4=new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent4);
                        break;

                }
                return true;
            }
        });
    }
}
