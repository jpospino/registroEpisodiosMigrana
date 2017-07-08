package com.sinergia.registroepisodiosdemigrana.Activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinergia.registroepisodiosdemigrana.R;

import java.util.HashMap;
import java.util.Map;

import Utiles.RestClient;


public class LoginActivity extends AppCompatActivity {

    private String URLServicios = "http://wwww.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtUsuario =  (EditText) findViewById(R.id.txtUsuario);
        final EditText txtContrasena =  (EditText) findViewById(R.id.txtContrasena);
        final Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        final ImageButton btnConfigurarURL = (ImageButton) findViewById(R.id.user_profile_photo);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsuario.getText().toString().toUpperCase().equals("JUAN") && txtContrasena.getText().toString().toUpperCase().equals("123456"))
                {
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    String token = "ABC123456";
                    intent.putExtra("token", token);
                    intent.putExtra("URL", URLServicios);
                    startActivity(intent);
                    txtContrasena.setText("");
                }
                else {

                }
            }
        });

        btnConfigurarURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CapturarURLActivity.class);
                intent.putExtra("URL", URLServicios);
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2) {
            URLServicios = data.getStringExtra("URL");
        }
    }
}
