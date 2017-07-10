package com.sinergia.registroepisodiosdemigrana.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.sinergia.registroepisodiosdemigrana.Activities.DB.DBHelper;
import com.sinergia.registroepisodiosdemigrana.R;

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
                if(txtUsuario.getText().toString().toUpperCase().equals("JUAN") && txtContrasena.getText().toString().toUpperCase().equals("123456"))
                {
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    String token = "ABC123456";
                    intent.putExtra("token", token);
                    startActivity(intent);
                    txtContrasena.setText("");
                }
                else {
                    Toast.makeText(LoginActivity.this,R.string.error_login,Toast.LENGTH_LONG).show();
                    txtUsuario.setText("");
                    txtContrasena.setText("");
                }
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
