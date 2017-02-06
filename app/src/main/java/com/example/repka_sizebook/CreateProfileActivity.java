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


/**
 * This activity is used to create new profile for the
 * main activity. In this activity, a form is created for
 * user to fill out to create a new profile. After the
 * wanted fields have been filled out, the create profile
 * button is pressed to create the profile. The only necessary
 * field is the name field, as this is used to name to profile.
 * After the profile variables are set, the profile list array
 * is loaded from the save file, and the new profile is appended
 * before the profile list is saved once again to the file.
 * Then, the activity ends and returns to the main activity.
 * @author Derek.R
 * @version 1.2
 * @since 1.0
 */

public class CreateProfileActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";

    private Profile newProfile;
    private ArrayList<Profile> profileList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button createProfileButton = (Button) findViewById(R.id.createRecord);
        /**
         * This is the listener for the create profile button, which
         * is called when the button is pressed. The editText fields are
         * assigned to instance variables, and the name field is checked.
         * If no name is inputted, a toast is raised prompting the user to
         * enter a name before continuing. The profiles variables are then
         * assigned to the strings or doubles inputted in the editText
         * fields. The new profile is then appended to the profileList,
         * and then is saved to the save file before returning to the
         * main activity.
         */
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
                    newProfile = new Profile(name.getText().toString());
                    newProfile.setName(name.getText().toString());
                    Boolean NegativeFlag = false;
                    String expectedPattern = "yyyy-MM-dd";
                    SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
                    try
                    {
                        newProfile.setDate(formatter.parse(date.getText().toString()));
                    }
                    catch (ParseException e)
                    {
                        if(!date.getText().toString().isEmpty()) {
                            Toast.makeText(CreateProfileActivity.this, "Wrong Date Format", Toast.LENGTH_SHORT).show();
                        }
                    }
                    /*Check the values entered in the EditText fields, to prevent
                    cast errors when converting text to doubles.
                    */
                    if (!neck.getText().toString().isEmpty()){
                        if(!(Double.valueOf(neck.getText().toString()) < 0)){
                            newProfile.setNeck(Double.valueOf( neck.getText().toString()));
                            NegativeFlag = true;
                        }

                    }
                    if (!bust.getText().toString().isEmpty()){
                        if(!(Double.valueOf(bust.getText().toString()) < 0)) {
                            newProfile.setBust(Double.valueOf(bust.getText().toString()));
                            NegativeFlag = true;
                        }
                    }
                    if (!chest.getText().toString().isEmpty()){
                        if(!(Double.valueOf(chest.getText().toString()) < 0)) {
                            newProfile.setChest(Double.valueOf(chest.getText().toString()));
                            NegativeFlag = true;
                        }
                    }
                    if (!waist.getText().toString().isEmpty()){
                        if(!(Double.valueOf(waist.getText().toString()) < 0)) {
                            newProfile.setWaist(Double.valueOf(waist.getText().toString()));
                            NegativeFlag = true;
                        }
                    }
                    if (!hip.getText().toString().isEmpty()){
                        if(!(Double.valueOf(hip.getText().toString()) < 0)) {
                            newProfile.setHip(Double.valueOf(hip.getText().toString()));
                            NegativeFlag = true;
                        }
                    }
                    if (!inseam.getText().toString().isEmpty()) {
                        if (!(Double.valueOf(inseam.getText().toString()) < 0)) {
                            newProfile.setInseam(Double.valueOf(inseam.getText().toString()));
                            NegativeFlag = true;
                        }
                    }

                    newProfile.setComment(comment.getText().toString());
                    if (NegativeFlag){
                        Toast.makeText(CreateProfileActivity.this, "Some Values not written due to negative number.", Toast.LENGTH_SHORT).show();
                    }
                    loadFromFile();
                    profileList.add(newProfile);
                    saveInFile();
                    finish();
                }

            }
        });
    }

    /**
     * Loads profiles from the save file.
     * @throws RuntimeException if there is an error reading the file
     * @exception FileNotFoundException if the file is not created
     */
    public void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-28 13:15
            profileList = gson.fromJson(in, new TypeToken<ArrayList<Profile>>(){}.getType());
            fis.close();

        } catch (FileNotFoundException e) {
            profileList = new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Saves profiles to the save file.
     * @throws RuntimeException if there is an error reading the file
     * @exception FileNotFoundException if the file is not created
     */

    public void saveInFile() {
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
