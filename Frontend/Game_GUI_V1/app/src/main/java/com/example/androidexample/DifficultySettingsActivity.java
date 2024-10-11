package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DifficultySettingsActivity extends AppCompatActivity {
    //Define input fields and buttons
    private Button homeBtn, getRequestBtn, postRequestBtn, putRequestBtn, deleteRequestBtn;
    private EditText etObject, etDamage, etId;

    //Backend URL
    private static final String BASE_URL = "http://coms-3090-031.class.las.iastate.edu:8080/Dk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_settings);  //Link to the activity_difficulty_settings.xml layout

        //Initialize the input fields and buttons
        homeBtn = findViewById(R.id.btnHome);
        getRequestBtn = findViewById(R.id.btnGetRequest);
        postRequestBtn = findViewById(R.id.btnPostRequest);
        putRequestBtn = findViewById(R.id.btnPutRequest);
        deleteRequestBtn = findViewById(R.id.btnDeleteRequest);
        etObject = findViewById(R.id.etObject);
        etDamage = findViewById(R.id.etDamage);
        etId = findViewById(R.id.etId);

        //Set click listener for the Home button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to the HomeActivity
                Intent homeIntent = new Intent(DifficultySettingsActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();  //Close the current activity
            }
        });

        //Set click listener for GET
        getRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performGetRequest();
            }
        });

        //Set click listener for POST
        postRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performPostRequest();
            }
        });

        //Set click listener for PUT
        putRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performPutRequest();
            }
        });

        //Set click listener for DELETE
        deleteRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performDeleteRequest();
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////

    //Method to perform GET request
    private void performGetRequest() {
        String url = BASE_URL;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            StringBuilder sb = new StringBuilder();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                sb.append("ID: ").append(object.getInt("id")).append(", ");
                                sb.append("Object: ").append(object.getString("object")).append(", ");
                                sb.append("Damage: ").append(object.getInt("damage")).append("\n");
                            }

                            //Pass the formatted string data to the new activity
                            Intent displayIntent = new Intent(DifficultySettingsActivity.this, DisplayObjectsActivity.class);
                            displayIntent.putExtra("objectsData", sb.toString());
                            startActivity(displayIntent);  //Start the new activity to display objects

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DifficultySettingsActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DifficultySettingsActivity.this, "GET Request Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
    }

    //Method to perform POST request
    private void performPostRequest() {
        String url = BASE_URL;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("object", etObject.getText().toString());
            requestBody.put("damage", Integer.parseInt(etDamage.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DifficultySettingsActivity.this, "POST Request Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DifficultySettingsActivity.this, "POST Request Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(postRequest);
    }

    //Method to perform PUT request (with object and ID checks)
    private void performPutRequest() {
        String id = etId.getText().toString().trim();  // Get the ID from input
        String objectName = etObject.getText().toString().trim();  //Get the object name from input
        String damageStr = etDamage.getText().toString().trim();  //Get the damage value from input

        if (id.isEmpty() || objectName.isEmpty() || damageStr.isEmpty()) {
            Toast.makeText(this, "Please enter an ID, Object Name, and Damage value to update", Toast.LENGTH_SHORT).show();
            return;
        }

        int objectId;
        int damage;
        try {
            objectId = Integer.parseInt(id);  //Parse ID as an integer
            damage = Integer.parseInt(damageStr);  //Parse damage as an integer
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers for ID and Damage", Toast.LENGTH_SHORT).show();
            return;
        }

        //First, perform a GET request to check if the object with the given ID and name exists
        String getUrl = BASE_URL + "/" + objectId;
        StringRequest getRequest = new StringRequest(Request.Method.GET, getUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            //Check if the retrieved object matches the given object name
                            String existingObjectName = jsonResponse.optString("object", "");
                            int existingId = jsonResponse.optInt("id", -1);

                            //If the object and ID match, proceed to update the damage value
                            if (existingObjectName.equalsIgnoreCase(objectName) && existingId == objectId) {
                                //Now perform the PUT request to update the damage value
                                performUpdateDamageRequest(objectId, objectName, damage);
                            } else {
                                Toast.makeText(DifficultySettingsActivity.this, "Object name or ID does not match any existing record.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DifficultySettingsActivity.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DifficultySettingsActivity.this, "GET Request Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add GET request to request queue
        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
    }

    //Method to perform the actual PUT request after validation
    private void performUpdateDamageRequest(int objectId, String objectName, int newDamage) {
        String url = BASE_URL + "/" + objectId;

        //Create JSON object for request body with updated damage
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("id", objectId);  //Include the ID in the request body
            requestBody.put("object", objectName);  //Set object name
            requestBody.put("damage", newDamage);  //Update damage value
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(DifficultySettingsActivity.this, "Failed to create JSON object", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create PUT request to update the object damage
        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DifficultySettingsActivity.this, "PUT Request Successful: Object damage updated.", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DifficultySettingsActivity.this, "PUT Request Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add the PUT request to the Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(putRequest);
    }

    //Method to perform DELETE request
    private void performDeleteRequest() {
        String id = etId.getText().toString();
        if (id.isEmpty()) {
            Toast.makeText(this, "Please enter an ID to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = BASE_URL + "/" + id;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DifficultySettingsActivity.this, "DELETE Request Successful", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DifficultySettingsActivity.this, "DELETE Request Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Add request to Volley request queue
        VolleySingleton.getInstance(this).addToRequestQueue(deleteRequest);
    }
}