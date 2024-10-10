package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class LoginActivity extends AppCompatActivity {

    // Define input fields and button
    private EditText etUsername, etPassword;
    //etEmail == etUsername  update this
    private Button loginBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  //Link to the activity_login.xml layout

        //Initialize the input fields and button
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.btnLogin);
        homeBtn = findViewById(R.id.btnHome);

        //Set click listener for the login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                //Validate email and password
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || password.length() > 10) {
                    Toast.makeText(LoginActivity.this, "Password must be 6-10 digits long", Toast.LENGTH_SHORT).show();
                } else {
                    // Call verifyEmail to make GET request and check if email exists
                    verifyUsername(username, password);
                }
            }
        });

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate back to the HomeActivity
                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current LoginActivity
            }
        });
    }
    //GET Request to verify username (email)

    //for testing errors
    private void verifyUsername(final String emailID, final String password) {
        // Backend URL to verify if the email exists (replace with your actual URL)
        String url = "http://coms-3090-031.class.las.iastate.edu:8080/Persons?email=" + emailID;

        //GET request using Volley
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LoginActivity", "Response: " + response);  //Log the raw response for debugging

                        try {
                            //Parse as JSONArray if the response is an array
                            JSONArray jsonArray = new JSONArray(response);

                            boolean emailExists = false;

                            //Iterate through the array to find a matching emailId
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userObject = jsonArray.getJSONObject(i);
                                String dbEmail = userObject.optString("emailId", "");
                                String dbName = userObject.optString("name", "");

                                if (dbEmail.equals(emailID)) {
                                    // Email found in the database
                                    emailExists = true;
                                    Toast.makeText(LoginActivity.this, "Email verified, logging in...", Toast.LENGTH_SHORT).show();
                                    //Call the method to update password or proceed to lobby
                                    //updateUserPassword(emailID, password);
                                    Intent loginIntent = new Intent(LoginActivity.this, JoinLobbyActivity.class);
                                    startActivity(loginIntent);  //Start JoinLobbyActivity
                                    break;  // Exit loop once email is found
                                }
                            }

                            if (!emailExists) {
                                // If email is not found in any of the objects
                                Toast.makeText(LoginActivity.this, "Email not found!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the request queue
        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
    }


//    private void verifyUsername(final String emailID, final String password) {
//        // Backend URL to verify if the email exists (replace with your actual URL)
//        String url = "http://coms-3090-031.class.las.iastate.edu:8080/Persons?email=" + emailID;
//
//        // GET request using Volley
//        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            // Assuming the response is a JSON array
//                            JSONArray jsonArray = new JSONArray(response);
//
//                            if (jsonArray.length() > 0) {
//                                // Email exists; now you can check the password
//                                JSONObject userObject = jsonArray.getJSONObject(0);
//                                String dbEmail = userObject.optString("emailId");
//                                String dbName = userObject.optString("name");
//
//                                if (dbEmail.equals(emailID)) {
//                                    // Email is verified; proceed to PUT request to update password or login
//                                    Toast.makeText(LoginActivity.this, "Email verified, logging in...", Toast.LENGTH_SHORT).show();
//                                    // You can call login or update password here
//                                    updateUserPassword(emailID, password);
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Email not found!", Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Email not found!", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(LoginActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginActivity.this, "Request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        // Add the request to the request queue
//        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
//    }

    //POST Request to create a new user (Signup)
    private void signUpUser(final String emailID, final String password) {
        //Replace with backend URL
        /////////////////////////////////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/signup";
        /////////////////////////////////////////////////////////////////////////////////////////
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("username", emailID);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");

                            if (success) {
                                Toast.makeText(LoginActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                Intent joinLobbyIntent = new Intent(LoginActivity.this, JoinLobbyActivity.class);
                                startActivity(joinLobbyIntent);
                            } else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    //PUT Request to update user information
    private void updateUserPassword(final String username, final String newPassword) {
        // Backend URL to update password
        String url = "http://coms-3090-031.class.las.iastate.edu:8080/Persons";

        // Create JSON object for the PUT request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", newPassword);  // Include the new password field as needed by your backend
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // PUT request using Volley
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            boolean success = response.optBoolean("success", false);
                            String message = response.optString("message", "No message");

                            if (success) {
                                Toast.makeText(LoginActivity.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                Intent joinLobbyIntent = new Intent(LoginActivity.this, JoinLobbyActivity.class);
                                startActivity(joinLobbyIntent);
                            } else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the request queue
        VolleySingleton.getInstance(this).addToRequestQueue(putRequest);
    }
}





