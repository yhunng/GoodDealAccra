package com.sleekjob.gooddealaccra;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import static android.app.Activity.RESULT_OK;


public class AddDealFragment extends Fragment {
    Bitmap bitmap;
    final int GALLERY_REQUEST = 2200;
    ImageView imageView;
    Button button, submit;
    String title, email, location, description, contact, price, discount;
    EditText mAddTitle, mAddEmail, mAddLocation, mAddDescription, mAddContact, mAddPrice, mAddDiscount;
    AlertDialog.Builder builder;
    Toolbar toolbar;
    ProgressBar progressBar;
    TextInputLayout titleInput, emailInput, locationInput, contactInput, priceInput, discountInput, descriptionInput;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_deal_fragment, container, false);

        imageView = (ImageView)v.findViewById(R.id.mAddImage);
        progressBar = (ProgressBar) v.findViewById(R.id.AddProgress);
        button = (Button) v.findViewById(R.id.mAddButton);
        submit = (Button) v.findViewById(R.id.mAddSubmit);
        mAddTitle = (EditText) v.findViewById(R.id.mAddTitle);
        mAddEmail = (EditText) v.findViewById(R.id.mAddEmail);
        mAddLocation = (EditText) v.findViewById(R.id.mAddLocation);
        mAddDescription = (EditText) v.findViewById(R.id.mAddDescription);
        mAddContact = (EditText) v.findViewById(R.id.mAddContact);
        mAddPrice = (EditText) v.findViewById(R.id.mAddPrice);
        mAddDiscount = (EditText) v.findViewById(R.id.mAddDiscount);

        titleInput = (TextInputLayout)v.findViewById(R.id.TitleLayout);
        emailInput = (TextInputLayout)v.findViewById(R.id.EmailLayout);
        locationInput = (TextInputLayout)v.findViewById(R.id.LocationLayout);
        contactInput = (TextInputLayout)v.findViewById(R.id.ContactLayout);
        priceInput = (TextInputLayout)v.findViewById(R.id.PriceLayout);
        discountInput = (TextInputLayout)v.findViewById(R.id.DiscountLayout);
        descriptionInput = (TextInputLayout)v.findViewById(R.id.DescriptionLayout);

        builder = new AlertDialog.Builder(getActivity());
        toolbar = (Toolbar) v.findViewById(R.id.mAddToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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
        return v;

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

        } else if (discount.length() > 2) {
            discountInput.setError("Should Not Be More Than 2 Digits (Do you want to kill your business?..lol) ");

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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
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


            progressBar.setVisibility(View.VISIBLE);
            String url = "http://gooddealaccra.sleekjob.com/api/deals/new";

            StringRequest request = new StringRequest(Request.Method.POST, url, new
                    Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            Log.d("Mainv", response);
                            builder.setTitle("Deal Submitted");
                            builder.setMessage(response);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mAddTitle.setText("");
                                    mAddEmail.setText("");
                                    mAddLocation.setText("");
                                    mAddDescription.setText("");
                                    mAddPrice.setText("");
                                    mAddDiscount.setText("");
                                    mAddContact.setText("");
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Log.d("Mainv", error.toString());

                    builder.setTitle("Something Went Wrong");
                    builder.setMessage("There Was a problem While Submitting Your Deal. Please Retry Now or Try Again Later");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DealSubmit();
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RecyclerFragment fragment = new RecyclerFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.AddFrame,fragment).commit();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

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
            MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

    }



    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageByte,Base64.NO_WRAP);

    }
}
