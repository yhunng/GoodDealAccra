package com.sleekjob.gooddealaccra;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddDealActivity extends AppCompatActivity {
    Bitmap bitmap;
    final int GALLERY_REQUEST = 2200;
    ImageView imageView;
    Button button, submit;
    String title, email, location, description, contact, price, discount;
    EditText mAddTitle, mAddEmail, mAddLocation, mAddDescription, mAddContact, mAddPrice, mAddDiscount;
    AlertDialog.Builder builder;
    Toolbar toolbar;
    ProgressBar progressBar;
    RelativeLayout progressRelative;
    TextInputLayout titleInput, emailInput, locationInput, contactInput, priceInput, discountInput, descriptionInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);



        imageView = (ImageView) findViewById(R.id.mAddImage);
        progressBar = (ProgressBar) findViewById(R.id.AddProgress);
        button = (Button) findViewById(R.id.mAddButton);
        submit = (Button) findViewById(R.id.mAddSubmit);
        mAddTitle = (EditText) findViewById(R.id.mAddTitle);
        mAddEmail = (EditText) findViewById(R.id.mAddEmail);
        mAddLocation = (EditText) findViewById(R.id.mAddLocation);
        mAddDescription = (EditText) findViewById(R.id.mAddDescription);
        mAddContact = (EditText) findViewById(R.id.mAddContact);
        mAddPrice = (EditText) findViewById(R.id.mAddPrice);
        mAddDiscount = (EditText) findViewById(R.id.mAddDiscount);
        progressRelative = (RelativeLayout) findViewById(R.id.progressRelative);

        titleInput = (TextInputLayout)findViewById(R.id.TitleLayout);
        emailInput = (TextInputLayout)findViewById(R.id.EmailLayout);
        locationInput = (TextInputLayout)findViewById(R.id.LocationLayout);
        contactInput = (TextInputLayout)findViewById(R.id.ContactLayout);
        priceInput = (TextInputLayout)findViewById(R.id.PriceLayout);
        discountInput = (TextInputLayout) findViewById(R.id.DiscountLayout);
        descriptionInput = (TextInputLayout)findViewById(R.id.DescriptionLayout);
        builder = new AlertDialog.Builder(AddDealActivity.this);
        toolbar = (Toolbar) findViewById(R.id.mAddToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.app_name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DealSubmit();
            }
        });
    }

    public void selectImage(){

        title = mAddTitle.getText().toString().trim();
        email = mAddEmail.getText().toString().trim();
        location = mAddLocation.getText().toString().trim();
        description = mAddDescription.getText().toString().trim();
        price = mAddPrice.getText().toString().trim();
        discount = mAddDiscount.getText().toString().trim();
        contact = mAddContact.getText().toString().trim();



        if (email.length() == 0) {
            emailInput.setError("This Field is Required");

        }  else if (!email.contains("@") || !email.contains(".") ) {
            emailInput.setError("Please enter a valid Email");

        }  else if (title.length() == 0) {
            titleInput.setError("This Field is Required");

        }  else if (title.length() > 25) {
            titleInput.setError("Should Not Be More Than 25 Characters ");

        }  else if (location.length() == 0) {
            locationInput.setError("This Field is Required");

        }  else if (location.length() > 30) {
            titleInput.setError("Should Not Be More Than 30 Characters ");

        } else if (description.length() == 0) {
            descriptionInput.setError("This Field is Required");

        }  else if (description.length() > 250) {
            descriptionInput.setError("Should Not Be More Than 250 Characters ");

        } else if (discount.length() == 0) {
            discountInput.setError("This Field is Required");

        } else if (contact.length() == 0) {
            contactInput.setError("This Field is Required");

        } else if (price.length() == 0) {
            priceInput.setError("This Field is Required");

        } else {


            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, GALLERY_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                button.setText("Change Deal Image");

            } catch (IOException e) {
                Log.d("Mainv",e.getMessage());
                e.printStackTrace();
            }


        }
    }

    public void DealSubmit() {


        progressRelative.setVisibility(View.VISIBLE);
        String url = "http://gooddealaccra.sleekjob.com/api/deals/new";

        StringRequest request = new StringRequest(Request.Method.POST, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressRelative.setVisibility(View.GONE);
                        Log.d("Mainv", response);
                        builder.setTitle("Deal Submitted");
                        builder.setMessage(response);
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressRelative.setVisibility(View.GONE);
                Log.d("Mainv", error.toString());

                builder.setTitle("Something Went Wrong");
                builder.setMessage("There Was a problem While Submitting Your Deal. Please Retry Now or Try Again Later");
                builder.setCancelable(false);
                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DealSubmit();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                           finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("title", title);
                param.put("location", location);
                param.put("description", description);
                param.put("contact", contact);
                param.put("price", price);
                param.put("discount", discount);
                param.put("email", email);
                param.put("status", "unapproved");
                param.put("image", imageToString(bitmap));
                return param;
            }
        };
        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(policy);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

    }



    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte,Base64.NO_WRAP);

    }
}
