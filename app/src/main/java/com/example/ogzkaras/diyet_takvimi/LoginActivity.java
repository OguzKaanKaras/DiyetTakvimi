package com.example.ogzkaras.diyet_takvimi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {
    TextView signup;
    EditText username, userpassword;
    Button btnlogin;

    private static final String loginUrl = "http://diyet.atwebpages.com/giris";
    SharedPreferences preferences;//preferences referansı
    SharedPreferences.Editor editor; //preferences editor nesnesi referansı .prefernces nesnesine veri ekleyip cıkarmak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//preferences objesi
        editor = preferences.edit(); //aynı şekil editor nesnesi oluşturuluyor

        username = findViewById(R.id.username);
        userpassword = findViewById(R.id.userpassword);
        signup = findViewById(R.id.signup);
        btnlogin = findViewById(R.id.btnUserLogin);


      if (preferences.getBoolean("login", false)) {
          Intent i = new Intent(getApplicationContext(), MenuActivity.class);
          startActivity(i);
          finish();
      }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    public void userLogin() {
        final StringRequest request = new StringRequest(StringRequest.Method.POST, loginUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String result=jsonObject.getString("result");
                    JSONObject profile= new JSONObject(result);
                    String usertoken=profile.getString("token");
                    String userid=profile.getString("user_id");

                    if (success == "true") {
                        System.out.println(result);
                        String userName = username.getText().toString().trim();
                        String userPassword = userpassword.getText().toString().trim();
                        editor.putString("usertoken",usertoken);
                        editor.putString("username", userName);
                        editor.putString("userpassword", userPassword);
                        editor.putBoolean("login", true);
                        editor.putString("userid",userid);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                       startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "kullanıcı adı veya parola yanlış", Toast.LENGTH_LONG).show();
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
                params.put("username", username.getText().toString().trim());
                params.put("password", userpassword.getText().toString().trim());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

}
