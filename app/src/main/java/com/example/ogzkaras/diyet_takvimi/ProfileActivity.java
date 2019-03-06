package com.example.ogzkaras.diyet_takvimi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String loginUrl = "http://diyet.atwebpages.com/user/";
    private static final String logoutUrl = "http://diyet.atwebpages.com/cikis";
    String name, surname, username, email, age, gender, height, weight, target_weight;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.searchcalori:
                        Intent intent1 = new Intent(getApplicationContext(), SearchCaloriActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.profile:
                        Intent intent4 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.calculate:
                        Intent intent3 = new Intent(getApplicationContext(), CalculateActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menu:
                        Intent intent2 = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });

        BottomNavigationView topNavigationView = (BottomNavigationView) findViewById(R.id.top_navigation);
        topNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.settings:
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.logout:
                        logout();
                        break;
                }
                return true;
            }
        });

        String usertoken = preferences.getString("usertoken", "");
        final StringRequest request = new StringRequest(StringRequest.Method.GET, loginUrl + usertoken, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(getApplicationContext(), "response yok", Toast.LENGTH_LONG).show();
                }
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String result = jsonObject.getString("result");
                    JSONObject profile = new JSONObject(result);
                    username = profile.getString("username");
                    name = profile.getString("name");
                    surname = profile.getString("surname");
                    height = profile.getString("height");
                    weight = profile.getString("weight");
                    target_weight = profile.getString("target_weight");
                    age = profile.getString("age");
                    gender = profile.getString("gender");
                    email = profile.getString("email");

                    if (success == "true") {
                        if (gender == "0") {
                            gender = "Kadın";
                        } else {
                            gender = "Erkek";
                        }
                        String userName = preferences.getString("username", "");
                        textView1.setText("Ad:" + name);
                        textView2.setText("Soyad:" + surname);
                        textView3.setText("Kullanıcı adı:" + username);
                        textView4.setText("Mail adresi:" + email);
                        textView5.setText("Yaş:" + age);
                        textView6.setText("Cinsiyet:" + gender);
                        textView7.setText("Boy:" + height + "cm");
                        textView8.setText("Kilo:" + weight);
                        textView9.setText("Hedeflenen kilo:" + target_weight);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }

    public void logout() {
        final String userid = preferences.getString("userid", "");
        final StringRequest request1 = new StringRequest(StringRequest.Method.POST, logoutUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Toast.makeText(getApplicationContext(), "response yok", Toast.LENGTH_LONG).show();
                }
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if (success == "true") {
                        editor.clear();
                        editor.putBoolean("login", false);
                        editor.commit();
                        Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent1);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", userid);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request1);

    }

}
