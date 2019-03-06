package com.example.ogzkaras.diyet_takvimi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Register2Activity extends AppCompatActivity {
    private static final String loginUrl = "http://diyet.atwebpages.com/kayit";
    TextView userage, userheight, userweight, usertargetweight;
    Button btnregister2;

    SharedPreferences preferences; //preferences nesne referansı
    SharedPreferences.Editor editor; //preferences editor nesnesi referansı .prefernces nesnesine veri ekleyip cıkarmak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        userage = (findViewById(R.id.userage));
        userheight = findViewById(R.id.userheight);
        userweight = findViewById(R.id.userweight);
        usertargetweight = findViewById(R.id.usertargetweight);
        btnregister2 = findViewById(R.id.btnregister2);

        System.out.println(userage);
        btnregister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userregister2();
            }
        });
    }

    public void userregister2() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences nesnesi oluşturuluyor ve prefernces referansına bağlanıyor
        editor = preferences.edit();

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String surname = intent.getStringExtra("surname");
        final String username = intent.getStringExtra("username");
        final String useremail = intent.getStringExtra("useremail");
        final String userpassword = intent.getStringExtra("userpassword");
        final String gender = intent.getStringExtra("gender");


        final StringRequest request = new StringRequest(StringRequest.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String result=jsonObject.getString("result");
                    JSONObject profile=new JSONObject(result);
                    String usertoken=profile.getString("token");
                    String userid=profile.getString("id");
                    if (success == "true") {
                        editor.putString("usertoken",usertoken);
                        editor.putString("userid",userid);
                        editor.putBoolean("login", true);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } else {
                        String error = jsonObject.getString("error");
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
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
                params.put("name", name.toString().trim());
                params.put("surname", surname.toString().trim());
                params.put("username", username.toString().trim());
                params.put("password", userpassword.toString().trim());
                params.put("email", useremail.toString().trim());
                params.put("age", userage.getText().toString().trim());
                params.put("height", userheight.getText().toString().trim());
                params.put("weight", userweight.getText().toString().trim());
                params.put("target_weight", usertargetweight.getText().toString().trim());
                params.put("gender", (gender.toString().trim()));
                System.out.println(gender);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
