package com.intpro.decepticons;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button patient, driver, emerg;
    ImageView ambuimage, hospi;

    private ViewPager viewPager;
    private UserTypeViewPagerAdapter userTypeViewPagerAdapter;
    private Button patientButton;
    private Button driverButton;
    public static String TAG = "";

    Dialog dialog;
    TextView timer_text;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener fireBaseAuthListener;

    Timer timer = new Timer();
    int time = 0;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private TextToSpeech myTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();



        //initializeTextToSpeech(null);

        fireBaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null)
                {

                }
            }
        };

        // animation
        ambuimage = findViewById(R.id.ambImage);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.left_to_rigth);
        ambuimage.setAnimation(animation1);

        // set view page
        viewPager = findViewById(R.id.vp_main_user_selection);
        userTypeViewPagerAdapter = new UserTypeViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(userTypeViewPagerAdapter);


    }

    @Override
    protected void onStart() {
        try {
            boolean s = (boolean)getIntent().getExtras().get("Accident");
            String sn;
            if (s){
                sn = "ks";
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.if_your_are_not_blind_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();
                Button cancel_button = dialog.findViewById(R.id.dialog_button);
                timer_text = dialog.findViewById(R.id.timer_text_view);
                startTimer();
                initializeTextToSpeech(sn);
                timer_text.setText("Time is counting");
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timer.cancel();
                        dialog.dismiss();
                        myTTS.stop();
                    }
                });

            }
        }catch (Exception e){

        }
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;

            case 1:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            break;
        }

    }

    private void initializeTextToSpeech(final String s) {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (s == null) {
                    if (myTTS.getEngines().size() == 0) {
                        Toast.makeText(MainActivity.this, "There is no TTS engine on your device",
                                Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        myTTS.setLanguage(Locale.UK);
                        myTTS.setSpeechRate(1);
                        aiVoice("Welcome to ambula. If you are a blind please remain 10 seconds, Thank you");


                    }
                }
                else {
                    myTTS.setLanguage(Locale.UK);
                    myTTS.setSpeechRate(1);
                    aiVoice("Fall Detected.... Are you ok. We are calling an emergency ambulance. ");
                }
            }



        });
    }

    private void aiVoice(String s)
    {
        if(Build.VERSION.SDK_INT >= 21)
        {
            myTTS.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else
        {
            myTTS.speak(s, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    private void startTimer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                    time++;
                    String a = String.valueOf(time);
                    //timer_text.setText(a);

                    if(time == 10)
                    {
                        final String email = "emergency@gmail.com";
                        final String pass = "123456";
                        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Intent intent = new Intent(MainActivity.this, EmergencyMap.class);
                                intent.putExtra(TAG,"book");
                                startActivity(intent);
                                finish();
                                return;
                            }
                        });
                    }
            }
        }, 30, 1000);

    }



}


