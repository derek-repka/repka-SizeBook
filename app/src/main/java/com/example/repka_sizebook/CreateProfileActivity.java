package com.example.repka_sizebook;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Derek.R on 2017-02-04.
 */

public class CreateProfileActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";

    private Profile newProfile;
    private ArrayList<Profile> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button createProfileButton = (Button) findViewById(R.id.createRecord);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);

                EditText name = (EditText) findViewById(R.id.name);
                EditText date = (EditText) findViewById(R.id.date);
                EditText neck = (EditText) findViewById(R.id.neck);
                EditText bust = (EditText) findViewById(R.id.bust);
                EditText chest = (EditText) findViewById(R.id.chest);
                EditText waist = (EditText) findViewById(R.id.waist);
                EditText hip = (EditText) findViewById(R.id.hip);
                EditText inseam = (EditText) findViewById(R.id.inseam);
                EditText comment = (EditText) findViewById(R.id.comment);

                if(name.getText().toString().isEmpty()){
                    Toast.makeText(CreateProfileActivity.this, "Name Is Required", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Taken from http://stackoverflow.com/questions/12575990/calendar-date-to-yyyy-mm-dd-format-in-java
                    //2017-02-05
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date myDate = df.parse(date.getText().toString());
                        cal.setTime(myDate);
                        cal.get(Calendar.DAY_OF_MONTH);
                        cal.get(Calendar.MONTH);
                        cal.get(Calendar.YEAR);
                    } catch (ParseException e) {
                    e.printStackTrace();
                    }

                    newProfile = new Profile(name.getText().toString());
                    newProfile.setName(name.getText().toString());
                    newProfile.setDate(df.format(cal.getTime()));
                    if (!neck.getText().toString().isEmpty()){
                        newProfile.setNeck(Integer.valueOf( neck.getText().toString()));
                    }
                    if (!bust.getText().toString().isEmpty()){
                        newProfile.setBust(Integer.valueOf( bust.getText().toString()));
                    }
                    if (!chest.getText().toString().isEmpty()){
                        newProfile.setChest(Integer.valueOf( chest.getText().toString()));
                    }
                    if (!waist.getText().toString().isEmpty()){
                        newProfile.setWaist(Integer.valueOf( waist.getText().toString()));
                    }
                    if (!hip.getText().toString().isEmpty()){
                        newProfile.setHip(Integer.valueOf( hip.getText().toString()));
                    }
                    if (!inseam.getText().toString().isEmpty()){
                        newProfile.setInseam(Integer.valueOf( inseam.getText().toString()));
                    }

                    newProfile.setComment(comment.getText().toString());
                    loadFromFile();
                    profileList.add(newProfile);
                    saveInFile();
                    finish();
                }

            }
        });
    }

    private void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-28 13:15
            profileList = gson.fromJson(in, new TypeToken<ArrayList<Profile>>(){}.getType());
            fis.close();

        } catch (FileNotFoundException e) {
            profileList = new ArrayList<Profile>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(profileList, out);

            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
