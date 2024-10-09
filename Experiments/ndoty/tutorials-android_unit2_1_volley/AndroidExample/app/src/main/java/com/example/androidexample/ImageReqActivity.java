package com.example.androidexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class ImageReqActivity extends AppCompatActivity {

    private Button btnImageReq;
    private ImageView imageView;

    public static final String URL_IMAGE = "https://static.wikia.nocookie.net/starwars/images/9/92/Just_wrong.jpg/revision/latest?cb=20061006174720";
    //public static final String URL_IMAGE = "https://cdn.discordapp.com/attachments/1278701294613303296/1289681878290534429/image.png?ex=67039898&is=67024718&hm=34fece694ef93bde862e54561452eb828545221c76e1a2d302fabbff7928013d&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_req);

        btnImageReq = (Button) findViewById(R.id.btnImageReq);
        imageView = (ImageView) findViewById(R.id.imgView);

        btnImageReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {makeImageRequest();}
        });
    }

    /**
     * Making image request
     * */
    private void makeImageRequest() {

        ImageRequest imageRequest = new ImageRequest(
            URL_IMAGE,
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    // Display the image in the ImageView
                    imageView.setImageBitmap(response);
                }
            },
            0, // Width, set to 0 to get the original width
            0, // Height, set to 0 to get the original height
            ImageView.ScaleType.FIT_XY, // ScaleType
            Bitmap.Config.RGB_565, // Bitmap config

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle errors here
                    Log.e("Volley Error", error.toString());
                }
            }
        );

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);
    }

}