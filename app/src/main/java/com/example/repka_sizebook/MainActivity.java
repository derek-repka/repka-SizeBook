package com.example.repka_sizebook;

import android.content.Context;
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
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private ListView recordsList;
    private TextView mainTitle;

    private ArrayList<Profile> profileList;
    private ArrayAdapter<Profile> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordsList = (ListView) findViewById(R.id.recordView);
        recordsList.setOnItemClickListener(new ListClickHandler());

        Button newProfileButton = (Button) findViewById(R.id.addRecord);

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
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        mainTitle = (TextView) findViewById(R.id.mainTitle);
        mainTitle.setText("You have " + profileList.size() + " records.");

        adapter = new ArrayAdapter<Profile>(this,R.layout.list_item, profileList);
        recordsList.setAdapter(adapter);
    }

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
