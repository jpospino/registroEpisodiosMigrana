package com.sinergia.registroepisodiosdemigrana.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sinergia.registroepisodiosdemigrana.Activities.DB.DBHelper;
import com.sinergia.registroepisodiosdemigrana.Activities.Dto.LoginResult;
import com.sinergia.registroepisodiosdemigrana.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DBHelper dbHelper = new DBHelper(this);

        if(dbHelper.GetURLCount() == 0)
            dbHelper.InsertContract(1,"www.google.com");

        final EditText txtUsuario =  (EditText) findViewById(R.id.txtUsuario);
        final EditText txtContrasena =  (EditText) findViewById(R.id.txtContrasena);
        final Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        final ImageButton btnConfigurarURL = (ImageButton) findViewById(R.id.user_profile_photo);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtContrasena.length() == 0 || txtUsuario.length() == 0) {
                    Toast.makeText(LoginActivity.this,R.string.ingresar_usuario_contrasena,Toast.LENGTH_LONG).show();
                    return;
                }

                DBHelper dbHelper = new DBHelper(LoginActivity.this);
                String URL = dbHelper.GetLastURL().getURL().toString() + "/login";

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Gson gson = new Gson();
                                LoginResult loginResult = gson.fromJson(response.toString(), LoginResult.class);


                                if( loginResult.getStatus().toUpperCase().equals("OK"))
                                {
                                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                    intent.putExtra("token", loginResult.getToken().toUpperCase() );
                                    startActivity(intent);
                                    txtContrasena.setText("");
                                }
                                else {
                                    Toast.makeText(LoginActivity.this,R.string.error_login,Toast.LENGTH_LONG).show();
                                    txtUsuario.setText("");
                                    txtContrasena.setText("");
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(LoginActivity.this,R.string.error_service_login,Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("usuario", txtUsuario.getText().toString());
                        params.put("nombre", txtContrasena.getText().toString());

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });

        btnConfigurarURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CapturarURLActivity.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
