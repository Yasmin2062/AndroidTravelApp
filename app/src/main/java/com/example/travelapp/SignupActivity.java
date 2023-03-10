package com.example.travelapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] continent = {"Asia", "Africa", "North America", "Europe"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, continent);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Button addCustomerButton = findViewById(R.id.btnSignUp);

        final EditText emailEditText =
                findViewById(R.id.edtSignUpEmail);
        final EditText firstNameEditText =
                findViewById(R.id.edtSignUpFirstName);
        final EditText lastNameEditText =
                findViewById(R.id.edtSignUpLastName);
        final EditText PassEditText =
                findViewById(R.id.edtSignUpPassword);

        final EditText confirmPassEditText =
                findViewById(R.id.edtSignUpConfirmPassword);


        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newUser = new User();
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
                Pattern pat = Pattern.compile(emailRegex);

                if (emailEditText.getText().toString().isEmpty() || !pat.matcher(emailEditText.getText().toString()).matches()) {
                    emailEditText.setError("PLEASE ENTER A VALID EMAIL ADDRESS");
                    emailEditText.requestFocus();
                } else {
                    newUser.setEmail(emailEditText.getText().toString());
                }

                if (firstNameEditText.getText().toString().isEmpty() || firstNameEditText.getText().toString().length() > 20 || firstNameEditText.getText().toString().length() < 3) {
                    firstNameEditText.setError("PLEASE ENTER A VALID FIRST NAME");
                    firstNameEditText.requestFocus();

                } else {
                    newUser.setFirstName(firstNameEditText.getText().toString());

                }

                if (lastNameEditText.getText().toString().isEmpty() || lastNameEditText.getText().toString().length() > 20 || lastNameEditText.getText().toString().length() < 3) {
                    lastNameEditText.setError("PLEASE ENTER A VALID LAST NAME");
                    lastNameEditText.requestFocus();

                } else {
                    newUser.setLastName(lastNameEditText.getText().toString());

                }

                if (PassEditText.getText().toString().isEmpty() || PassEditText.getText().toString().length() > 15 || PassEditText.getText().toString().length() < 8
                        || !PassEditText.getText().toString().matches(".*\\d.*")
                        || !PassEditText.getText().toString().matches(".*[a-z].*")
                        || !PassEditText.getText().toString().matches(".*[A-Z].*")) {
                    PassEditText.setError("PLEASE ENTER A VALID PASSWORD");
                    PassEditText.requestFocus();

                } else if (PassEditText.getText().toString().compareTo(confirmPassEditText.getText().toString()) != 0) {
                    PassEditText.setError("PASSWORD DOES NOT MATCH");
                    PassEditText.requestFocus();
                } else {
                    newUser.setPassword(PassEditText.getText().toString());
                }
                newUser.setDestination(spin.getSelectedItem().toString());


                DataBaseHelper dataBaseHelper = new
                        DataBaseHelper(SignupActivity.this, "TRAVEL_APP", null, 1);
                if (newUser.getEmail() != null && newUser.getPassword() != null && newUser.getFirstName() != null && newUser.getLastName() != null) {
                    Cursor searchUser = dataBaseHelper.searchUser(emailEditText.getText().toString());
                    if (searchUser.moveToFirst()) {
                        if (searchUser.getString(0).compareTo(emailEditText.getText().toString()) == 0) {
                            emailEditText.setError("USER ALREADY EXISTS");
                            emailEditText.requestFocus();
                        }
                    } else {
                        dataBaseHelper.insertUser(newUser);
                        NavigationDrawerActivity.user = newUser;
                      ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(SignupActivity.this);
                      connectionAsyncTask.execute("https://run.mocky.io/v3/d1a9c002-6e88-4d1e-9f39-930615876bca");
                        try {
                            connectionAsyncTask.get();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        // SEND THE USER'S PREFERRED CONTINENT TO NAVIGATION ACTIVITY
                      String Preferredcontinent = spin.getSelectedItem().toString();
                      Intent intent = new Intent(SignupActivity.this, NavigationDrawerActivity.class);
                      intent.putExtra("message_key", Preferredcontinent);
                        dataBaseHelper.close();
                      SignupActivity.this.startActivity(intent);
                      finish();
                 }
                    searchUser.close();
                } else {
                    Toast.makeText(SignupActivity.this, "ERROR SIGN UP",
                            Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /**
     * Called when the user touches the button
     */
    public void signIn(View view) {
        // Do something in response to button click
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        SignupActivity.this.startActivity(intent);
    }

    public void addDestinations(List<Destination> destinations) {
        DataBaseHelper dataBaseHelper = new
                DataBaseHelper(SignupActivity.this, "TRAVEL_APP", null, 1);
        for (int i = 0; i < destinations.size(); i++)
            dataBaseHelper.insertDestination(destinations.get(i));
        dataBaseHelper.close();

    }

}