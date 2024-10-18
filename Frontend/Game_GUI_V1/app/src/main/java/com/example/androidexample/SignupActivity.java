package com.example.androidexample;

import android.content.Intent;  //Import for using Intents
import android.os.Bundle;  //Import for onCreate and Bundle class
import android.util.Patterns;  //Import for email pattern validation
import android.view.View;  //Import for View class
import android.widget.Button;  //Import for Button class
import android.widget.EditText;  //Import for EditText class
import android.widget.Toast;  //Import for Toast class
import androidx.appcompat.app.AppCompatActivity;  //Import for AppCompatActivity
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class SignupActivity extends AppCompatActivity {

    //Define input fields and buttons
    private EditText etUsername, etPassword;
    private Button signupBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);  //Link to the activity_signup.xml layout

        //Initialize the input fields and buttons
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        signupBtn = findViewById(R.id.btnSignup);
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the signup button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                //Validate email and password
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter both name and password", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 4 || password.length() > 8) {
                    Toast.makeText(SignupActivity.this, "Password must be 4-8 digits long", Toast.LENGTH_SHORT).show();
                } else {
                    //Call signUpUser method to make POST request
                    signUpUser(email, password);
                }
            }
        });

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate back to the HomeActivity
                Intent homeIntent = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current LoginActivity
            }
        });
    }

    private void signUpUser(final String username, final String password) {
        //First, verify if the username exists before making the POST request
        verifyUsernameAvailability(username, password);
    }

    //Method to verify if the username is available
    private void verifyUsernameAvailability(final String username, final String password) {
        //Backend URL
        String url = "http://coms-3090-031.class.las.iastate.edu:8080/Player";

        //GET request using Volley to check if username already exists
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Parse the response as a JSONArray
                            JSONArray jsonArray = new JSONArray(response);
                            boolean usernameExists = false;

                            //Iterate through the array to check if the username exists
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userObject = jsonArray.getJSONObject(i);
                                String dbUsername = userObject.optString("name", "");  //Use "name" for username

                                if (dbUsername.equals(username)) {
                                    usernameExists = true;
                                    break;
                                }
                            }

                            if (usernameExists) {
                                //If username already exists, show a message and prevent signup
                                Toast.makeText(SignupActivity.this, "Username is already taken. Please choose another.", Toast.LENGTH_SHORT).show();
                            } else {
                                //If username does not exist, proceed with signup
                                createNewUser(username, password);  //Call method to create a new user
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SignupActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, "Request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
    }

    //Method to create a new user using POST request
    private void createNewUser(final String username, final String password) {
        //Backend URL for creating a new user (replace with your actual URL)
        String url = "http://coms-3090-031.class.las.iastate.edu:8080/Player";

        //Create JSON object for request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", username);  //Use the username as the name field
            requestBody.put("password", password);  //Include password as needed by backend
        } catch (JSONException e) {
            e.printStackTrace();
        }

            //Create POST request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Since no errors, successful signup
                            Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();

                            //Navigate to Join Lobby Activity
                            Intent joinLobbyIntent = new Intent(SignupActivity.this, JoinLobbyActivity.class);
                            startActivity(joinLobbyIntent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Show error message if something goes wrong
                    Toast.makeText(SignupActivity.this, "Request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        //Add request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}

