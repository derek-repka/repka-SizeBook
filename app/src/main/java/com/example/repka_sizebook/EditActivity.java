package com.example.repka_sizebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.ArrayList;


/**
 * Created by Derek.R on 2017-02-03.
 */

public class EditActivity extends AppCompatActivity {
    private ListView dataView;
    private static final String FILENAME = "file.sav";
    private Profile selectedProfile;
    private ArrayList<Profile> profileList;
    private ArrayList<String> dataList = new ArrayList<>();;
    private ArrayAdapter<String> adapter;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        loadFromFile();
        position = intent.getIntExtra("position", 0);
        selectedProfile = profileList.get(position);

        fillDataList();

        dataView = (ListView) findViewById(R.id.recordView);
        dataView.setOnItemClickListener(new ListClickHandler());

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, dataList);
        dataView.setAdapter(adapter);

        Button deleteButton = (Button) findViewById(R.id.deleteRecord);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Taken from http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
                //2017-02-04
                AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);
                alert.setMessage("Are you sure you want to delete?");
                alert.setTitle("Delete");

                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            profileList.remove(selectedProfile);
                            saveInFile();
                            finish();
                        }

                    });
                alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                alert.show();
            }
        });

        Button homeButton = (Button) findViewById(R.id.home);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    public class ListClickHandler implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(final AdapterView<?> adapter2, View view, final int selectedPosition, long id) {
            setResult(RESULT_OK);
            //Taken from http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
            //2017-02-04
            AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);
            final EditText edittext = new EditText(EditActivity.this);
            alert.setMessage("What value do you want?");
            alert.setTitle("Change Selected Value");

            alert.setView(edittext);

            alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String changedValue = edittext.getText().toString();
                    switch(selectedPosition){
                        case 0:
                            selectedProfile.setName(changedValue);
                            break;

                        case 1:
                            selectedProfile.setDate(changedValue);
                            break;

                        case 2:
                            selectedProfile.setNeck(Integer.valueOf(changedValue));
                            break;

                        case 3:
                            selectedProfile.setBust(Integer.valueOf(changedValue));
                            break;

                        case 4:
                            selectedProfile.setChest(Integer.valueOf(changedValue));
                            break;

                        case 5:
                            selectedProfile.setWaist(Integer.valueOf(changedValue));
                            break;

                        case 6:
                            selectedProfile.setHip(Integer.valueOf(changedValue));
                            break;

                        case 7:
                            selectedProfile.setInseam(Integer.valueOf(changedValue));
                            break;

                        default:
                            selectedProfile.setComment(changedValue);

                    }
                    profileList.set(position, selectedProfile);
                    saveInFile();
                    dataList.clear();
                    fillDataList();
                    adapter.notifyDataSetChanged();

                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            alert.show();
        }

    }

    private void fillDataList(){
        Integer integer;
        dataList.add("Name: " + selectedProfile.getName());
        dataList.add("Date: " + selectedProfile.getDate());
        integer = selectedProfile.getNeck();
        dataList.add("Neck Circumference(in.): " + ((integer == null) ? "" : integer));

        integer = selectedProfile.getBust();
        dataList.add("Bust Circumference(in.): " + ((integer == null) ? "" : integer));

        integer = selectedProfile.getChest();
        dataList.add("Chest Circumference(in.): " + ((integer == null) ? "" : integer));

        integer = selectedProfile.getWaist();
        dataList.add("Waist Circumference(in.): " + ((integer == null) ? "" : integer));

        integer = selectedProfile.getHip();
        dataList.add("Hip Circumference(in.): " + ((integer == null) ? "" : integer));

        integer = selectedProfile.getInseam();
        dataList.add("Inseam(in.): " + ((integer == null) ? "" : integer));

        dataList.add("Comments: " + selectedProfile.getComment());
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
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
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
