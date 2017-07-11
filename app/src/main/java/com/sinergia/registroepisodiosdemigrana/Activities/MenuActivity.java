package com.sinergia.registroepisodiosdemigrana.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinergia.registroepisodiosdemigrana.Activities.DB.DBHelper;
import com.sinergia.registroepisodiosdemigrana.R;

import java.util.HashMap;
import java.util.Map;

import Utiles.RestClient;

public class MenuActivity extends AppCompatActivity {

    private String token = "";
    private String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle b = getIntent().getExtras();
        this.token = b.getString("token");
        this.URL = b.getString("URL");

        ImageButton btnRegistrarEpisodio = (ImageButton) findViewById(R.id.btnRegistrarEpisodio);
        Button btnConsultarDictamen = (Button)findViewById(R.id.btnConsultarDictamen);

        btnRegistrarEpisodio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, RegistrarEpisodioActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("URL", URL);
                startActivity(intent);
            }
        });

        btnConsultarDictamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(MenuActivity.this);
                String URL = dbHelper.GetLastURL().getURL().toString() + "/crearEpisodio";

                RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                Toast.makeText(MenuActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", "error");
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("cedula", "Alif");
                        params.put("nombre", "http://itsalif.info");
                        params.put("nivelDolor", "http://itsalif.info");
                        params.put("descripcion", "http://itsalif.info");

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.salir)
                .setMessage(R.string.confirmacion_salida)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
