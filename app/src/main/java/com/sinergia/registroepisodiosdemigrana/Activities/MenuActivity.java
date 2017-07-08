package com.sinergia.registroepisodiosdemigrana.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.sinergia.registroepisodiosdemigrana.R;

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

        btnRegistrarEpisodio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, RegistrarEpisodioActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("URL", URL);
                startActivity(intent);
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
