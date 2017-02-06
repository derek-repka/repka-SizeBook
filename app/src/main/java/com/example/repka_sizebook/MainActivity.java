package com.example.repka_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This activity is the main activity of the application, and both the
 * CreateProfile and Edit activities receive their intent from here.
 * In this activity, there is a TextView at the beginning of the page
 * that has the total number of records that the application currently has.
 * These profile records are stored in profileList arrayList, and are created
 * in the profile class. From here, clicking on any of the profiles in the
 * ListView will launch the EditActivity, which allows for editing, as well
 * as outright deleting the profile. Alternatively, clicking the NewProfile
 * button allows for a new profile to be created and added to the ArrayList.
 * Lastly, the profileList is stored in the file file.sav, in the JSON format.
 * @author Derek.R
 * @version 1.2
 * @since 1.0
 */

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView recordsList;
    private TextView mainTitle;

    private ArrayList<Profile> profileList;
    private ArrayAdapter<Profile> adapter;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordsList = (ListView) findViewById(R.id.recordView);
        recordsList.setOnItemClickListener(new ListClickHandler());

        Button newProfileButton = (Button) findViewById(R.id.addRecord);

        /**
         * This is the listener for the new profile button, which is called when
         * the button is pressed. A new intent is created, which launches the
         * create profile activity.
         */
        newProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent ProfileActivity = new Intent(MainActivity.this, CreateProfileActivity.class);
                startActivity(ProfileActivity);
                adapter.notifyDataSetChanged();

            }
        });
    }
    /**
     * Called after onCreate has finished.
     * It loads the profileList from the save file, sets the textField to
     * the appropriate amount of profiles, and sets up the adapter for
     * the ArrayList.
     * @see #loadFromFile()
     */

    @Override
    public void onStart(){
        super.onStart();
        loadFromFile();
        mainTitle = (TextView) findViewById(R.id.mainTitle);
        mainTitle.setText("You have " + profileList.size() + " records.");

        adapter = new ArrayAdapter<>(this,R.layout.list_item, profileList);
        recordsList.setAdapter(adapter);
    }

    /**
     * This is the listener for all profiles in the ListView, which is
     * called when any element in the ListView is pushed.
     * It creates an new intent and includes the position of the pushed profile,
     * so that the profile can be modified/deleted in the edit activity.
     */
    public class ListClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            setResult(RESULT_OK);
            Intent editIntent = new Intent();
            editIntent.setClass(MainActivity.this, EditActivity.class);
            editIntent.putExtra("position", position);
            startActivity(editIntent);
        }

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
}
