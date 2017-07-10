package com.sinergia.registroepisodiosdemigrana.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sinergia.registroepisodiosdemigrana.Activities.DB.DBHelper;
import com.sinergia.registroepisodiosdemigrana.Activities.Dto.URLDto;
import com.sinergia.registroepisodiosdemigrana.R;

public class CapturarURLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturar_url);

        Intent intent = getIntent();

        final EditText txtURLServicios = (EditText) findViewById(R.id.txtURLServicios);
        Button btnSalir = (Button) findViewById(R.id.btnSalir);

        DBHelper dbHelper = new DBHelper(this);

        txtURLServicios.setText(dbHelper.GetLastURL().getURL());

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(CapturarURLActivity.this);
                URLDto ultimo = dbHelper.GetLastURL();
                dbHelper.InsertContract(ultimo.getId() + 1, txtURLServicios.getText().toString());
                setResult(RESULT_OK, null);
                finish();
            }
        });

    }
}
