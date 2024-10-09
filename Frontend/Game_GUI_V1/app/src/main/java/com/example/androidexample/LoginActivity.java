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
                //Retrieve user input
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                //Validate email and password inputs
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || password.length() > 10) {
                    Toast.makeText(LoginActivity.this, "Password must be 6-10 digits long", Toast.LENGTH_SHORT).show();
                } else {
                    // Call the GET request to verify email
                    verifyUsername(username, password);

                    //Implement signup logic here
                    Toast.makeText(LoginActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                    //Navigate to Join Lobby page upon successful signup
                    Intent joinLobbyIntent = new Intent(LoginActivity.this, JoinLobbyActivity.class);
                    startActivity(joinLobbyIntent);
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
    private void verifyUsername(final String email, final String password) {
        //Replace with backend URL
        ///////////////////////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/verifyEmail?email=" + email;
        ///////////////////////////////////////////////////////////////////////////////

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String userId = jsonResponse.getString("userId");

                            if (success) {
                                // If email exists, proceed to check password
                                if (password.equals(userId)) {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent joinLobbyIntent = new Intent(LoginActivity.this, JoinLobbyActivity.class);
                                    startActivity(joinLobbyIntent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    //POST Request to create a new user (Signup)
    private void signUpUser(final String username, final String password) {
        //Replace with backend URL
        /////////////////////////////////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/signup";
        /////////////////////////////////////////////////////////////////////////////////////////
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
    private void updateUser(final String username, final String password) {
        //Replace with backend URL
        ////////////////////////////////////////////////////////////////
        String url = "https://your-backend-url.com/user/update";
        ////////////////////////////////////////////////////////////////
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
                                Toast.makeText(LoginActivity.this, "Update Successful", Toast.LENGTH_SHORT).show();
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




}





