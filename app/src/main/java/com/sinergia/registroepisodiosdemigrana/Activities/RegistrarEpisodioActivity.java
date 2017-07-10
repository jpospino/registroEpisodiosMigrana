package com.sinergia.registroepisodiosdemigrana.Activities;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sinergia.registroepisodiosdemigrana.Activities.Dto.Episodio;
import com.sinergia.registroepisodiosdemigrana.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistrarEpisodioActivity extends AppCompatActivity {

    private Episodio episodioRegistrar;
    private String AudioSavePathInDevice = null;
    public static final int RequestPermissionCode = 1;
    private MediaRecorder mediaRecorder ;
    private Boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        episodioRegistrar = new Episodio();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_episodio);

        final LinearLayout controlesLayout = (LinearLayout) findViewById(R.id.controlesLayout);
        final LinearLayout controlesLayout2 = (LinearLayout) findViewById(R.id.controlesLayout2);
        final LinearLayout controlesLayout3 = (LinearLayout) findViewById(R.id.controlesLayout3);

        controlesLayout.setVisibility(LinearLayout.VISIBLE);
        controlesLayout2.setVisibility(LinearLayout.GONE);
        controlesLayout3.setVisibility(LinearLayout.GONE);

        final Button btnAnterior = (Button) findViewById(R.id.btnAnterior);
        final Button btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        final ImageButton btnGrabar = (ImageButton) findViewById(R.id.btnGrabar);

        final DatePicker datePickerFechaEpisodio = (DatePicker) findViewById(R.id.datePickerFechaEpisodio);
        final TimePicker timePickerHoraEpisodio = (TimePicker) findViewById(R.id.timePickerHoraEpisodio);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()) {

                    if(!isRecording) {
                        AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath().toString()
                                                + "/"
                                                + CreateRandomAudioFileName()
                                                + "_audio.3gp";

                        isRecording = true;
                        MediaRecorderReady();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                            isRecording = true;
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Toast.makeText(RegistrarEpisodioActivity.this, R.string.inicio_grabacion, Toast.LENGTH_LONG).show();
                    }
                    else {
                        isRecording = false;
                        MediaRecorderStop();

                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(AudioSavePathInDevice);
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mediaPlayer.start();
                        Toast.makeText(RegistrarEpisodioActivity.this, R.string.escuchando_grabacion, Toast.LENGTH_LONG).show();

                        new AlertDialog.Builder( RegistrarEpisodioActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle(R.string.enviar)
                                .setMessage(R.string.confirmacion_enviar)
                                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }

                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        File file = new File(AudioSavePathInDevice);
                                        if(file.exists()){
                                            file.delete();
                                            Toast.makeText(RegistrarEpisodioActivity.this,
                                                    R.string.archivo_borrado, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }).show();
                    }
                    } else {
                        requestPermission();
                    }}
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(controlesLayout.getVisibility() == LinearLayout.VISIBLE) {
                    //completarDiaFecha(datePickerFechaEpisodio);
                    controlesLayout.setVisibility(LinearLayout.GONE);
                    controlesLayout2.setVisibility(LinearLayout.VISIBLE);
                }
                else {
                    if(controlesLayout2.getVisibility() == LinearLayout.VISIBLE){
                        //completarHoraFecha(timePickerHoraEpisodio);
                        controlesLayout2.setVisibility(LinearLayout.GONE);
                        controlesLayout3.setVisibility(LinearLayout.VISIBLE);
                    }
                }
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(controlesLayout.getVisibility() == LinearLayout.VISIBLE) {
                    onBackPressed();
                }
                else {
                    if(controlesLayout2.getVisibility() == LinearLayout.VISIBLE){
                        controlesLayout.setVisibility(LinearLayout.VISIBLE);
                        controlesLayout2.setVisibility(LinearLayout.GONE);
                    }
                    else {
                        if(controlesLayout3.getVisibility() == LinearLayout.VISIBLE){
                            controlesLayout3.setVisibility(LinearLayout.GONE);
                            controlesLayout2.setVisibility(LinearLayout.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.salir)
                .setMessage(R.string.confirmacion_regreso)
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

    private void completarDiaFecha(DatePicker datePickerFechaEpisodio){
        int day = datePickerFechaEpisodio.getDayOfMonth();
        int month = datePickerFechaEpisodio.getMonth() + 1;
        int year = datePickerFechaEpisodio.getYear();

        episodioRegistrar.setFechaHora(new Date(year, month, day));
    }
    private void completarHoraFecha(TimePicker timePickerHoraEpisodio){
        int hour = timePickerHoraEpisodio.getHour();
        int minute = timePickerHoraEpisodio.getMinute();

        Date fechaHora = episodioRegistrar.getFechaHora();
        fechaHora.setHours(hour);
        fechaHora.setMinutes(minute);
        episodioRegistrar.setFechaHora(fechaHora);
    }

    private String CreateRandomAudioFileName(){
        DateFormat df = new SimpleDateFormat("yyyy_mm_dd_HH_mm");
        return df.format(Calendar.getInstance().getTime());
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(RegistrarEpisodioActivity.this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
    private void MediaRecorderReady(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }
    private void MediaRecorderStop(){
        mediaRecorder.stop();
        Toast.makeText(RegistrarEpisodioActivity.this, R.string.gracion_completada, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(RegistrarEpisodioActivity.this, R.string.permiso_concedido, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegistrarEpisodioActivity.this,R.string.permiso_negado,Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}