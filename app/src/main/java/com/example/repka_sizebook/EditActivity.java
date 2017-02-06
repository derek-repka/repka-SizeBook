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
import java.util.Date;


/**
 * This activity is used to both edit parameters or to delete
 * a profile. If the user chooses to delete the profile, a warning
 * prompt is raised to confirm that the user wants to delete the
 * profile. As well, by clicking on any ot=f the parameters, a
 * dialog is opened to get the user to change the variable value.
 * Lastly, there is home button that ends the activity and returns
 * the user to the main activity page, where they can view all
 * created profiles.
 * @author Derek.R
 * @version 1.2
 * @since 1.0
 */

public class EditActivity extends AppCompatActivity {
    private ListView dataView;
    private static final String FILENAME = "file.sav";
    private Profile selectedProfile;
    private ArrayList<Profile> profileList;
    private ArrayList<String> dataList = new ArrayList<>();;
    private ArrayAdapter<String> adapter;
    private Integer position;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        loadFromFile();
        /*Get the selected profile position, and the get the
        selected profile.*/
        position = intent.getIntExtra("position", 0);
        selectedProfile = profileList.get(position);

        //Fill the ListView with readable data.
        fillDataList();

        //Set up the ListView and the adapter.
        dataView = (ListView) findViewById(R.id.recordView);
        dataView.setOnItemClickListener(new ListClickHandler());

        adapter = new ArrayAdapter<>(this, R.layout.list_item, dataList);
        dataView.setAdapter(adapter);

        Button deleteButton = (Button) findViewById(R.id.deleteRecord);

        /**
         * This is the listener of the delete button.
         * When pressed the user is prompted to if they want
         * to delete to profile. If they do, the profile is removed
         * from the profileList before the list is written back to
         * the file, and the activity ends. If they choose to not
         * delete the profile, the dialog window simply closes.
         */
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Taken from http://stackoverflow.com/questions/18799216/how-to-make-a-edittext-box-in-a-dialog
                //2017-02-04
                AlertDialog.Builder alert = new AlertDialog.Builder(EditActivity.this);
                //Set the alert message and title.
                alert.setMessage("Are you sure you want to delete?");
                alert.setTitle("Delete");

                //Assign the positve and negative buttons in the aleret.
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
                //Display the alert.
                alert.show();
            }
        });

        Button homeButton = (Button) findViewById(R.id.home);

        /**
        * This is the listener for the home button. When pressed,
        * the activity is ended, and the user is returned to the main
        * activity.
        */
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    /**
     * This is the listener for all fields in the ListView, which is
     * called when any element in the ListView is pushed.
     * It creates an new alert and prompts the user for a new value,
     * so that the selected parameter can be changed in the profile.
     */
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
                    /*Switch to determine which parameter to change.
                    If any of the numerical values are going to be set to negative,
                    a toast is raised and no changes are mode.
                     */
                    switch(selectedPosition){
                        case 0:
                            selectedProfile.setName(changedValue);
                            break;

                        case 1:
                            String expectedPattern = "yyyy-MM-dd";
                            SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
                            try
                            {
                                selectedProfile.setDate(formatter.parse(changedValue));
                            }
                            catch (ParseException e)
                            {
                                Toast.makeText(EditActivity.this, "Wrong Date Format", Toast.LENGTH_SHORT).show();
                            }

                            break;

                        case 2:
                            if (Double.valueOf(changedValue) < 0){
                                Toast.makeText(EditActivity.this, "Value cannot be negative", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedProfile.setNeck(Double.valueOf(changedValue));
                            break;

                        case 3:
                            if (Double.valueOf(changedValue) < 0){
                                Toast.makeText(EditActivity.this, "Value cannot be negative", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedProfile.setBust(Double.valueOf(changedValue));
                            break;

                        case 4:
                            if (Double.valueOf(changedValue) < 0){
                                Toast.makeText(EditActivity.this, "Value cannot be negative", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedProfile.setChest(Double.valueOf(changedValue));
                            break;

                        case 5:
                            if (Double.valueOf(changedValue) < 0){
                                Toast.makeText(EditActivity.this, "Value cannot be negative", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedProfile.setWaist(Double.valueOf(changedValue));
                            break;

                        case 6:
                            if (Double.valueOf(changedValue) < 0){
                                Toast.makeText(EditActivity.this, "Value cannot be negative", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedProfile.setHip(Double.valueOf(changedValue));
                            break;

                        case 7:
                            if (Double.valueOf(changedValue) < 0){
                                Toast.makeText(EditActivity.this, "Value cannot be negative", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedProfile.setInseam(Double.valueOf(changedValue));
                            break;

                        default:
                            selectedProfile.setComment(changedValue);

                    }
                    /*Change the parameter, write it to file, and then
                    update the strings with the new value.*/
                    profileList.set(position, selectedProfile);
                    saveInFile();
                    dataList.clear();
                    fillDataList();
                    adapter.notifyDataSetChanged();

                }
            });

            //On cancel, do nothing but close the alert.
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            //show the alert.
            alert.show();
        }

    }

    /**
     * The fillDataList method that gets the variables from the
     * chosen profile, and creates readable strings to be put
     * into the ListView.
     */

    public void fillDataList(){
        Double num;

        dataList.add("Name: " + selectedProfile.getName());

        Date date = selectedProfile.getDate();
        //If there is no date in the profile, leave it blank.
        if (date == null){
            dataList.add("Date: ");
        }
        //When there is a date, convert it to the right format.
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(date);
            dataList.add("Date: " + formattedDate);
        }

        num = selectedProfile.getNeck();
        dataList.add("Neck Circumference(in.): " + ((num == null) ? "" : num));

        num = selectedProfile.getBust();
        dataList.add("Bust Circumference(in.): " + ((num == null) ? "" : num));

        num = selectedProfile.getChest();
        dataList.add("Chest Circumference(in.): " + ((num == null) ? "" : num));

        num = selectedProfile.getWaist();
        dataList.add("Waist Circumference(in.): " + ((num == null) ? "" : num));

        num = selectedProfile.getHip();
        dataList.add("Hip Circumference(in.): " + ((num == null) ? "" : num));

        num = selectedProfile.getInseam();
        dataList.add("Inseam(in.): " + ((num == null) ? "" : num));

        dataList.add("Comments: " + selectedProfile.getComment());
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
            profileList = new ArrayList<Profile>();
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
