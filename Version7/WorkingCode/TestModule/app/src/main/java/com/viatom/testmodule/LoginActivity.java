package com.viatom.testmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;

    TextView username;
    TextView password;
    TextView domain;
    public static final String SHARED_PREFS= "sharedPrefs";
    public static int state;
    public static  String user,p,d;
    private final String URLlogin="https://cloudclinicdevapi.azurewebsites.net/oauth/token";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (TextView) findViewById(R.id.textusername);
        password = (TextView) findViewById(R.id.textpassword);
        domain = (TextView) findViewById(R.id.textdomain);
        btn_login = (Button) findViewById(R.id.bt_login);
        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        user = sh.getString("user", "");
        d = sh.getString("d", "");
        p = sh.getString("p", "");



        Toast.makeText(getApplicationContext(),String.valueOf(state),Toast.LENGTH_LONG);

        if(state==1)
        {
            Toast toast=Toast.makeText(getApplicationContext(),user+" "+d+" "+p,Toast.LENGTH_LONG);
            toast.setMargin(50,50);
            toast.show();



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                username.setText(user);
            domain.setText(d);
            password.setText(p);
            }
            });
            goToMain();
        }
        else if(state==0)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    username.setText(user);
                    domain.setText(d);
                    password.setText(p);
                }
            });
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        user=username.getText().toString();
        p=password.getText().toString();
        d=domain.getText().toString();


//                editor.commit();

                signIn();
                //
            }
        });

    }

    private synchronized void signIn() {

        StringRequest request = new StringRequest(Request.Method.POST, URLlogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast toast=Toast.makeText(getApplicationContext(),"Success!!!",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
               goToMain();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast=Toast.makeText(getApplicationContext(),"Error Wrong credentials!!!",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                user="";
                Log.e("error is ", "" + error);
            }
        }) {


            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("grant_type", "password");
                params.put("username", LoginActivity.user);
                params.put("domain",LoginActivity.d);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("user",username.getText().toString());
        editor.putString("p",password.getText().toString());
        editor.putString("d",domain.getText().toString());

        editor.apply();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }

public void goToMain()
{
    Intent i = new Intent(this, MainActivity.class);
    startActivity(i);
}


}