package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //    private String url = "https://jsonplaceholder.typicode.com/users/1";
    private String url = "http://coms-3090-031.class.las.iastate.edu:8080/Player";
    //private String url = "http://coms-3090-031.class.las.iastate.edu:8080/laptops";

    private Spinner spMethod;
    private EditText etUrl;
    private EditText etRequest;
    private TextView tvResponse;
    private Button btnSend;

    private String method;
    private String requestBody;
    private String responseBody;

    private TextView messageText;   // define message textview variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize UI elements */
        etUrl = findViewById(R.id.etUrl);
        etRequest = findViewById(R.id.etRequest);
        tvResponse = findViewById(R.id.tvResponse);
        btnSend = findViewById(R.id.sendBtn);

        etUrl.setText(url);

        // method spinner
        Spinner spMethod = findViewById(R.id.spMethod);
        String[] methods = new String[]{"GET", "POST", "DELETE", "PUT"};   //add delete as option for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, methods);
        spMethod.setAdapter(adapter);
        spMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                method = (String) parent.getItemAtPosition(position);
                if (method.equals("GET")) etRequest.setText("Leave this field empty");
                else if (method.equals("POST"))etRequest.setText("Enter JSON object here");
                else if (method.equals("DELETE"))etRequest.setText("Enter ID number to delete");   //Define what needs to be entered for deletion
                else if (method.equals("PUT"))etRequest.setText("Enter new user information");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                method = "GET";
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                url = etUrl.getText().toString();
                requestBody = etRequest.getText().toString();
                if (method.equals("GET")) getRequest();
                else if (method.equals("POST")) postRequest();
                else if (method.equals("DELETE")) deleteRequest();   //delete request when delete is clicked
                else putRequest();
            }
        });
    }

    private void getRequest() {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
                        // JSONObject object = new JSONObject(response);
                        tvResponse.setText("Response is: "+ response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvResponse.setText("That didn't work!" + error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("param1", "value1");
                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void postRequest() {

        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject(etRequest.getText().toString());
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tvResponse.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvResponse.setText(error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("param1", "value1");
                params.put("param2", "value2");
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    //Added a delete request to delete a specific ID
    private void deleteRequest() {
        // Construct the full URL by appending the ID from the EditText field
        String deleteUrl = url + "/" + etRequest.getText().toString();

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvResponse.setText("Delete successful: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvResponse.setText("Delete failed: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(deleteRequest);
    }








    private void putrequest(){
        String putUrl = url + "/" + etRequest.getText().toString(); // Make sure this is the correct ID or endpoint

        // Convert input to JSONObject
        JSONObject putBody = null;
        try {
            putBody = new JSONObject(etRequest.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(
                Request.Method.PUT,
                putUrl,
                putBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tvResponse.setText("Update successful: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvResponse.setText("Update failed: " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                // Add any headers you need
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(putRequest);
    }








}
