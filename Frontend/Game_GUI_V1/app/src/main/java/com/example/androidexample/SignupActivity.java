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
                //Retrieve user input
                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                //Validate email and password
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignupActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || password.length() > 10) {
                    Toast.makeText(SignupActivity.this, "Password must be 6-10 digits long", Toast.LENGTH_SHORT).show();
                } else {
                    //Implement signup logic here
                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    //Navigate to Join Lobby page upon successful signup
                    Intent joinLobbyIntent = new Intent(SignupActivity.this, JoinLobbyActivity.class);
                    startActivity(joinLobbyIntent);
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

    //GET Request to verify username (email)
    private void verifyEmail(final String username, final String password) {
        //Replace with backend URL
        //////////////////////////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/verifyEmail?email=" + username;
        //////////////////////////////////////////////////////////////////////////////////
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String userId = jsonResponse.getString("userId");

                            if (success) {
                                //If email exists, proceed to check password
                                if (password.equals(userId)) {
                                    Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                    Intent joinLobbyIntent = new Intent(SignupActivity.this, JoinLobbyActivity.class);
                                    startActivity(joinLobbyIntent);
                                } else {
                                    Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
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

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    //POST Request to create a new user (Signup)
    private void signUpUser(final String username, final String password) {
        //Replace with backend URL
        //////////////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/signup";
        //////////////////////////////////////////////////////////////////////
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("username", username);
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
                                Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                Intent joinLobbyIntent = new Intent(SignupActivity.this, JoinLobbyActivity.class);
                                startActivity(joinLobbyIntent);
                            } else {
                                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
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

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    //PUT Request to update user information
    private void updateUser(final String username, final String password) {
        //Replace with backend URL
        ///////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/update";
        ///////////////////////////////////////////////////////////////
        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");

                            if (success) {
                                Toast.makeText(SignupActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
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

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
