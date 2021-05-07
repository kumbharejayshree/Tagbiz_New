package com.tagloy.tagbiz.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tagloy.tagbiz.R;
import com.tagloy.tagbiz.activity.MainNavActivity;
import com.tagloy.tagbiz.activity.OutletActivity;
import com.tagloy.tagbiz.models.Creative;
import com.tagloy.tagbiz.models.NoticeBoard;
import com.tagloy.tagbiz.models.NoticeBoardJsonData;
import com.tagloy.tagbiz.models.Organization;
import com.tagloy.tagbiz.utils.ApiManager;
import com.tagloy.tagbiz.utils.AppConfig;
import com.tagloy.tagbiz.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNoticeBoard extends Fragment {
    RadioGroup mRGrpColor, mRGrpOrientation;
    private RadioButton mRdBtVertical, mRdBtnHorozontal, mRdBtnBlue, mRdBtnGreen, mRdBtnRed;
    CardView cardView, cardViewHorizontal;
    private EditText edtHeading1, edtHeading2, edtHeading3, edtHeading4, edtHeading5, edtHeading6, edtHeading7, edtHeading8, edtHeading9, edtHeading10, edtFooter;
    TextView txtHeading1, txtHeading2, txtHeading3, txtHeading4, txtHeading5, txtHeading6, txtHeading7, txtHeading8, txtHeading9, txtHeading10, txtFooter, txthoriZontal1, txthoriZontal2, txthoriZontal3, txthoriZontal4, txthoriZontal5, txthoriZontal6;
    ImageView iv;
    private Context mContext;
    private String org_id, token, org_name;


    Button preview;
    File myDir;
    File file;

    public AddNoticeBoard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Notice Board");
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active, container, false);
        org_id = PreferenceHelper.getValueString(mContext, AppConfig.ORG_ID);
        Log.e("Model", org_id.toString());
        token = PreferenceHelper.getValueString(mContext, AppConfig.USER_TOKEN);
        org_name = PreferenceHelper.getValueString(mContext, AppConfig.ORG_NAME);

        Log.e("Model", org_name);
        //getNoticeBoardData();
        getData();

        InputMethodManager ipmm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        ipmm.hideSoftInputFromWindow(null, 0);

        TextView Orientation = (TextView) view.findViewById(R.id.orientation);
        //Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(), "fonts/aaleway_regular.ttf");
        //Orientation.setTypeface(custom_font);
        TextView txtBoardColor = (TextView) view.findViewById(R.id.txtBoardColor);
        //txtBoardColor.setTypeface(custom_font);


        edtHeading1 = (EditText) view.findViewById(R.id.edtHeading1);
        ///edtHeading1.scrollTo(500, edtHeading1.getTop());
        edtHeading1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.edtHeading1) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });
        edtHeading2 = (EditText) view.findViewById(R.id.edtHeading2);
        edtHeading3 = (EditText) view.findViewById(R.id.edtHeading3);
        edtHeading4 = (EditText) view.findViewById(R.id.edtHeading4);
        edtHeading5 = (EditText) view.findViewById(R.id.edtHeading5);
        edtHeading6 = (EditText) view.findViewById(R.id.edtHeading6);
        edtHeading7 = (EditText) view.findViewById(R.id.edtHeading7);
        edtHeading8 = (EditText) view.findViewById(R.id.edtHeading8);
        edtHeading9 = (EditText) view.findViewById(R.id.edtHeading9);
        edtHeading10 = (EditText) view.findViewById(R.id.edtHeading10);
        //edtFooter = (EditText) view.findViewById(R.id.edtFooter);

        txtHeading1 = (TextView) view.findViewById(R.id.heading1);
        //Typeface heading1 = Typeface.createFromAsset(mContext.getAssets(), "fonts/DKBreakfastBurrito.otf");
        //txtHeading1.setTypeface(heading1);
        txtHeading2 = (TextView) view.findViewById(R.id.heading2);
        //txtHeading2.setTypeface(heading1);
        txtHeading3 = (TextView) view.findViewById(R.id.heading3);
        //txtHeading3.setTypeface(custom_font);
        txtHeading4 = (TextView) view.findViewById(R.id.heading4);
        //txtHeading4.setTypeface(custom_font);
        txtHeading5 = (TextView) view.findViewById(R.id.heading5);
        //txtHeading5.setTypeface(custom_font);
        txtHeading6 = (TextView) view.findViewById(R.id.heading6);
        //txtHeading6.setTypeface(custom_font);
        txtHeading7 = (TextView) view.findViewById(R.id.heading7);
        //txtHeading7.setTypeface(custom_font);
        txtHeading8 = (TextView) view.findViewById(R.id.heading8);
        //txtHeading8.setTypeface(custom_font);
        txtHeading9 = (TextView) view.findViewById(R.id.heading9);
        //txtHeading9.setTypeface(custom_font);
        txtHeading10 = (TextView) view.findViewById(R.id.heading10);
        //txtHeading10.setTypeface(custom_font);
        txtFooter = (TextView) view.findViewById(R.id.footer);
        //txtFooter.setTypeface(custom_font);


        txthoriZontal1 = (TextView) view.findViewById(R.id.horiHeading1);
        //txthoriZontal1.setTypeface(heading1);

        txthoriZontal2 = (TextView) view.findViewById(R.id.horiHeading2);
        //txthoriZontal2.setTypeface(heading1);
        txthoriZontal3 = (TextView) view.findViewById(R.id.horiHeading3);
        //txthoriZontal3.setTypeface(custom_font);
        txthoriZontal4 = (TextView) view.findViewById(R.id.horiHeading4);
        //txthoriZontal4.setTypeface(custom_font);
        txthoriZontal5 = (TextView) view.findViewById(R.id.horiHeading5);
        //txthoriZontal5.setTypeface(custom_font);
        txthoriZontal6 = (TextView) view.findViewById(R.id.horiHeading6);
        //txthoriZontal6.setTypeface(custom_font);
        preview = (Button) view.findViewById(R.id.BtnPreview);
        cardView = (CardView) view.findViewById(R.id.card1);
        cardViewHorizontal = (CardView) view.findViewById(R.id.card2);
        mRGrpColor = (RadioGroup) view.findViewById(R.id.rdGrpColor);
        mRGrpOrientation = (RadioGroup) view.findViewById(R.id.rgOrientation);
        mRdBtnBlue = (RadioButton) view.findViewById(R.id.rdBtnBlue);
        //mRdBtnBlue.setTypeface(custom_font);
        mRdBtnGreen = (RadioButton) view.findViewById(R.id.rdBtnGreen);
        //mRdBtnGreen.setTypeface(custom_font);
        mRdBtnRed = (RadioButton) view.findViewById(R.id.rdBtnRed);
        //mRdBtnRed.setTypeface(custom_font);
        mRdBtVertical = (RadioButton) view.findViewById(R.id.rdBtnVertical);
        //mRdBtVertical.setTypeface(custom_font);
        mRdBtnHorozontal = (RadioButton) view.findViewById(R.id.rdBtnHorizontal);
        //mRdBtnHorozontal.setTypeface(custom_font);
        cardView.setBackgroundResource(R.drawable.notice_board_border);
        boolean checked = mRdBtnBlue.isChecked();

        mRGrpColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (mRdBtnBlue.isChecked()) {
                    cardView.setBackgroundResource(R.drawable.notice_board_border);
                    cardViewHorizontal.setBackgroundResource(R.drawable.notice_board_horizontal);
                } else if (mRdBtnGreen.isChecked()) {
                    cardView.setBackgroundResource(R.drawable.green_notice_board_vertical);
                    cardViewHorizontal.setBackgroundResource(R.drawable.green_horizontal);

                } else if (mRdBtnRed.isChecked()) {
                    cardView.setBackgroundResource(R.drawable.red_notice_board_vertical);
                    cardViewHorizontal.setBackgroundResource(R.drawable.red_horizontal);

                }
            }
        });
        mRGrpOrientation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (mRdBtVertical.isChecked()) {
                    cardView.setVisibility(View.VISIBLE);
                    cardViewHorizontal.setVisibility(View.GONE);
                    cardView.setBackgroundResource(R.drawable.notice_board_border);
                    mRdBtnBlue.setChecked(true);
                } else if (mRdBtnHorozontal.isChecked()) {

                    cardView.setVisibility(View.GONE);
                    cardViewHorizontal.setVisibility(View.VISIBLE);
                    cardViewHorizontal.setBackgroundResource(R.drawable.notice_board_horizontal);
                    mRdBtnBlue.setChecked(true);

                }


            }
        });
        edtHeading1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading1.setText(edtHeading1.getText().toString());
                txthoriZontal1.setText(edtHeading1.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading2.setText(edtHeading2.getText().toString());
                txthoriZontal2.setText(edtHeading2.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading3.setText(edtHeading3.getText().toString());
                txthoriZontal3.setText(edtHeading3.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading4.setText(edtHeading4.getText().toString());
                txthoriZontal4.setText(edtHeading4.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading5.setText(edtHeading5.getText().toString());
                txthoriZontal5.setText(edtHeading5.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading6.setText(edtHeading6.getText().toString());
                txthoriZontal6.setText(edtHeading6.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading7.setText(edtHeading7.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading8.setText(edtHeading8.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading9.setText(edtHeading9.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtHeading10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtHeading10.setText(edtHeading10.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtFooter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtFooter.setText(edtFooter.getText().toString());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //updateNoticeBoard();
                final Dialog dialog = new Dialog(getContext());
                if (edtHeading3.getText().toString().trim().length() != 0 && edtHeading4.getText().toString().trim().length() != 0) {
                    updatedata();
                    //updateNoticeBoard();
                    dialog.show();
                    //Toast.makeText(mContext, "save Data", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(mContext, "Please Enter the Content1 ! &  Content2", Toast.LENGTH_SHORT).show();
                }

                Window window = dialog.getWindow();
                window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
                dialog.setContentView(R.layout.image_dailog);

                iv = (ImageView) dialog.findViewById(R.id.menuIcon);
                ImageView imageView = new ImageView(getContext());
                iv.setDrawingCacheEnabled(true);
                if (mRdBtVertical.isChecked()) {
                    iv.setImageBitmap(convertToBitmap(cardView));


                } else if (mRdBtnHorozontal.isChecked())
                    iv.setImageBitmap(convertToBitmapNew(cardViewHorizontal));


                Button no = (Button) dialog.findViewById(R.id.cancel_action);
                Button yes = (Button) dialog.findViewById(R.id.upload_action);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iv.buildDrawingCache(true);
                        Bitmap bitmap = iv.getDrawingCache();
                        Matrix matrix = new Matrix();
                        matrix.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, 1080, 1920), Matrix.ScaleToFit.CENTER);
                        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        saveImageToExternalStorage(bitmap1);
                        //updateNoticeBoard();
                        //uploadToS3();

                        dialog.dismiss();
                    }
                });

            }
        });

        return view;


    }


    protected Bitmap convertToBitmap(CardView view) {
        int totalHeight = view.getHeight();
        int totalWidth = view.getWidth();
        float percent = 0.999f;//use this value to scale bitmap to specific size

        Bitmap canvasBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.scale(percent, percent);
        view.draw(canvas);

        return canvasBitmap;
    }

    protected Bitmap convertToBitmapNew(CardView view) {
        int totalHeight = view.getHeight();
        int totalWidth = view.getWidth();
        float percent = 0.999f;//use this value to scale bitmap to specific size
        Bitmap canvasBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.scale(percent, percent);
        view.draw(canvas);

        return canvasBitmap;
    }


    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String dirName = Environment.getExternalStorageDirectory().getPath()
                + "/MyAppFolder/MyApp" + ".png";
        //String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        MediaScannerConnection.scanFile(getContext(),
                new String[]{dirName}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        myDir = new File(dirName + "/saved_images_1");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadToS3() {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                mContext,
                AppConfig.IDENTITY_POOL,
                Regions.AP_SOUTH_1
        );
        AmazonS3 amazonS3 = new AmazonS3Client(credentialsProvider);
        amazonS3.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        TransferUtility transferUtility = TransferUtility.builder().context(mContext)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .s3Client(amazonS3).build();
        final TransferObserver observer = transferUtility.upload(AppConfig.BIZ_BUCKET, file.getName(), file, CannedAccessControlList.PublicRead);
        setTransferListener(observer);
    }

    public void setTransferListener(final TransferObserver observer) {
        //final ProgressBar progressBar = ((Activity) context).findViewById(R.id.publishLoader);
        //final Button button = ((Activity)context).findViewById(R.id.publishButton);
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state == TransferState.COMPLETED) {
                    //progressBar.setVisibility(View.GONE);
                    String url = "https://" + AppConfig.BIZ_BUCKET + ".s3.amazonaws.com/" + observer.getKey();
                    Log.e("ImageURL", url);
                    //publishImage(hashes, url);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                //progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(int id, Exception ex) {
                //button.setClickable(true);
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, "Unable to publish! Check network and Retry", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updatedata(){
        JSONObject jsonApiOject = new JSONObject();
        JSONObject jsonBlackBoard = new JSONObject();
        JSONObject jsonContent = new JSONObject();

        String color = "";
        if (mRdBtnGreen.isChecked()) {
            color = "green";
        } else if (mRdBtnBlue.isChecked()) {
            color = "black";
        } else if (mRdBtnRed.isChecked()) {
            color = "red";
        }
        String orentation = "";
        if (mRdBtVertical.isChecked()) {
            orentation = "V";
        } else if (mRdBtnHorozontal.isChecked()) {
            orentation = "H";
        }


        try {
            jsonBlackBoard.put("board_color", color);
            jsonBlackBoard.put("oname", org_name);
            jsonBlackBoard.put("heading", txtHeading2.getText().toString());
            jsonContent.put("content1", txtHeading3.getText().toString());
            jsonContent.put("content2", txtHeading4.getText().toString());
            jsonContent.put("content3", txtHeading5.getText().toString());
            jsonContent.put("content4", txtHeading6.getText().toString());
            jsonContent.put("content5", txtHeading7.getText().toString());
            jsonContent.put("content6", txtHeading8.getText().toString());
            jsonContent.put("content7", txtHeading9.getText().toString());
            jsonContent.put("content8", txtHeading10.getText().toString());
            jsonBlackBoard.put("content", jsonContent);

            jsonBlackBoard.put("footer", txtFooter.getText().toString());

            jsonApiOject.put("venue_id", org_id);
            jsonApiOject.put("is_black_board", 1);
            jsonApiOject.put("black_board_json", jsonBlackBoard.toString());
            jsonApiOject.put("is_preview", 0);
            jsonApiOject.put("type", orentation);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String result = jsonApiOject.toString();
        Log.e("gggg", result);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.UPDATE_NOTICEBOARD_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseJson = new JSONObject(response);
                    boolean is_success = responseJson.getBoolean("is_success");
                    if (is_success) {
                        String status = responseJson.getString("status_code");
                        if (status.equals("200")) {
                            Toast.makeText(mContext, "Save successfully!", Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    Toast.makeText(mContext, "Network error! Please try again in some time", Toast.LENGTH_SHORT).show();
                    Log.d("Login API","Fail");
                    return;
                }
                String body;
                try {
                    int statusCode = error.networkResponse.statusCode;
                    Log.d("Status",String.valueOf(statusCode));
                    if (statusCode == 500) {
                        body = new String(error.networkResponse.data, "UTF-8");
                        JSONObject resultJson = new JSONObject(body);
                        String message = resultJson.getString("message");
                        Log.d("Message", message);
                        String result = resultJson.getString("result");
                        Log.d("Result", result);
                    } else if (statusCode == 401) {
                        Toast.makeText(mContext, "Please check credentials!", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException | JSONException ue) {
                    ue.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<>();
                parameter.put("Content-Type", "application/json");
                parameter.put("Authorization", token);
                return parameter;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                final String request = jsonApiOject.toString();
                try {
                    return request.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", request, "utf-8");
                    return null;
                }
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    /*public void updateNoticeBoard() {
        //final String token = PreferenceHelper.getValueString(mContext, AppConfig.USER_TOKEN);
        //Log.e("TOKEN", token);
        JSONObject jsonApiOject = new JSONObject();
        JSONObject jsonBlackBoard = new JSONObject();
        JSONObject jsonContent = new JSONObject();

        String color = "";
        if (mRdBtnGreen.isChecked()) {
            color = "green";
        } else if (mRdBtnBlue.isChecked()) {
            color = "black";
        } else if (mRdBtnRed.isChecked()) {
            color = "red";
        }
        String orentation = "";
        if (mRdBtVertical.isChecked()) {
            orentation = "V";
        } else if (mRdBtnHorozontal.isChecked()) {
            orentation = "H";
        }


        try {
            jsonBlackBoard.put("board_color", color);
            jsonBlackBoard.put("oname", txtHeading1.getText().toString());
            jsonBlackBoard.put("heading", txtHeading2.getText().toString());
            jsonContent.put("content1", txtHeading3.getText().toString());
            jsonContent.put("content2", txtHeading4.getText().toString());
            jsonContent.put("content3", txtHeading5.getText().toString());
            jsonContent.put("content4", txtHeading6.getText().toString());
            jsonContent.put("content5", txtHeading7.getText().toString());
            jsonContent.put("content6", txtHeading8.getText().toString());
            jsonContent.put("content7", txtHeading9.getText().toString());
            jsonContent.put("content8", txtHeading10.getText().toString());
            jsonBlackBoard.put("content", jsonContent);

            jsonBlackBoard.put("footer", txtFooter.getText().toString());

            jsonApiOject.put("venue_id", org_id);
            jsonApiOject.put("is_black_board", 1);
            jsonApiOject.put("black_board_json", jsonBlackBoard.toString());
            jsonApiOject.put("is_preview", 0);
            jsonApiOject.put("type", orentation);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String result = jsonApiOject.toString();
        Log.e("gggg", result);

        ApiManager.getClient().NoticeBoardData(token, jsonApiOject.toString())
                .enqueue(new Callback<com.tagloy.tagbiz.models.NoticeBoard>() {
                    @Override
                    public void onResponse(Call<com.tagloy.tagbiz.models.NoticeBoard> call, Response<com.tagloy.tagbiz.models.NoticeBoard> response) {

                        String status = response.body().getResult().getBlackBoardJson().toString();
                        Log.e("xaa", status);
                        //Toast.makeText(getContext(), "Save the Data", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<com.tagloy.tagbiz.models.NoticeBoard> call, Throwable t) {
                        Log.e("Error", t.getMessage());
                        Toast.makeText(getContext(),
                                "Error is " + t.getMessage()
                                , Toast.LENGTH_SHORT).show();

                    }
                });
    }
*/
  /*  public void getNoticeBoardData() {
        //final String token = PreferenceHelper.getValueString(mContext, AppConfig.USER_TOKEN);
        ApiManager.getClient().getNoticeBoardData(token, org_id, "14356564655").enqueue(new Callback<com.tagloy.tagbiz.models.NoticeBoard>() {
            @Override
            public void onResponse(Call<com.tagloy.tagbiz.models.NoticeBoard> call, Response<com.tagloy.tagbiz.models.NoticeBoard> response) {

                if (response.isSuccessful()) {
                    if (response.body().getIsSuccess() == true) {
                        final com.tagloy.tagbiz.models.NoticeBoard.Result data = response.body().getResult();
                        Gson gson = new Gson();
                        NoticeBoardJsonData black_board_json = gson.fromJson(data.getBlackBoardJson(), NoticeBoardJsonData.class);
                        String color = black_board_json.getBoardColor().toString();
                        switch (color) {
                            case "black":
                                mRdBtnBlue.setChecked(true);
                                cardView.setBackgroundResource(R.drawable.notice_board_border);

                                break;
                            case "green":
                                mRdBtnGreen.setChecked(true);
                                cardView.setBackgroundResource(R.drawable.green_notice_board_vertical);


                                break;
                            case "red":
                                mRdBtnRed.setChecked(true);
                                cardView.setBackgroundResource(R.drawable.red_notice_board_vertical);

                                break;
                        }

                        txtHeading1.setText(black_board_json.getOname().toString());
                        //edtHeading1.setText(txtHeading1.getText().toString());
                        //edtHeading1.setSelection(edtHeading1.getText().length());


                        txtHeading2.setText(black_board_json.getHeading().toString());
                        //edtHeading2.setText(txtHeading2.getText().toString());

                        txtFooter.setText(black_board_json.getFooter().toString());
                        //edtFooter.setText(txtFooter.getText().toString());

                        txtHeading3.setText(black_board_json.getContent().getContent1().toString());
                        //edtHeading3.setText(txtHeading3.getText().toString());

                        txtHeading4.setText(black_board_json.getContent().getContent2().toString());
                        //edtHeading4.setText(txtHeading4.getText().toString());

                        txtHeading5.setText(black_board_json.getContent().getContent3().toString());
                        //edtHeading5.setText(txtHeading5.getText().toString());

                        txtHeading6.setText(black_board_json.getContent().getContent4().toString());
                        //edtHeading6.setText(txtHeading6.getText().toString());

                        txtHeading7.setText(black_board_json.getContent().getContent5().toString());
                        //edtHeading7.setText(txtHeading7.getText().toString());

                        txtHeading8.setText(black_board_json.getContent().getContent6().toString());
                        //edtHeading8.setText(txtHeading8.getText().toString());

                        txtHeading9.setText(black_board_json.getContent().getContent7().toString());
                        //edtHeading9.setText(txtHeading9.getText().toString());

                        txtHeading10.setText(black_board_json.getContent().getContent8().toString());
                        //edtHeading10.setText(txtHeading10.getText().toString());


                    }
                }


            }

            @Override
            public void onFailure(Call<NoticeBoard> call, Throwable t) {

            }
        });

    }*/

    public void getData() {
        try {

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("venue_id", org_id);
            jsonObject.put("timestamp", "14356564655");
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.GET_NOTICEBOARD_URL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject stringResponse = new JSONObject(response);
                        boolean success = stringResponse.getBoolean("is_success");
                        String requestCode = stringResponse.getString("status_code");
                        Log.e("JAYU", requestCode);

                        String message = stringResponse.getString("message");
                        if (success) {
                            if (requestCode.equals("200")) {
                                Log.e("Jayu", "Success");
                                String result = stringResponse.getString("result");
                                Log.e("JAYU", result);
                                JSONObject resultResponse = new JSONObject(result);
                                String blackBoard = resultResponse.getString("black_board_json");
                                JSONObject jsonObject1 = new JSONObject(blackBoard);
                                String color = jsonObject1.getString("board_color");
                                switch (color) {
                                    case "black":
                                        mRdBtnBlue.setChecked(true);
                                        cardView.setBackgroundResource(R.drawable.notice_board_border);
                                        break;
                                    case "green":
                                        mRdBtnGreen.setChecked(true);
                                        cardView.setBackgroundResource(R.drawable.green_notice_board_vertical);
                                        break;
                                    case "red":
                                        mRdBtnRed.setChecked(true);
                                        cardView.setBackgroundResource(R.drawable.red_notice_board_vertical);
                                        break;
                                }
                                txtHeading1.setText(org_name);
                                txtHeading2.setText(jsonObject1.getString("heading"));
                                txtFooter.setText(jsonObject1.getString("footer"));
                                String content = jsonObject1.getString("content");
                                JSONObject jsonObject2 = new JSONObject(content);
                                txtHeading3.setText(jsonObject2.getString("content1"));
                                txtHeading4.setText(jsonObject2.getString("content2"));
                                txtHeading5.setText(jsonObject2.getString("content3"));
                                txtHeading6.setText(jsonObject2.getString("content4"));
                                txtHeading7.setText(jsonObject2.getString("content5"));
                                txtHeading8.setText(jsonObject2.getString("content6"));
                                txtHeading9.setText(jsonObject2.getString("content7"));
                                txtHeading10.setText(jsonObject2.getString("content8"));
                                Log.e("JAYU", blackBoard);
                                Log.e("JAYU", content);



                            }
                        } else {
                            Log.d("Login", "Failure");
                            if (requestCode.equals("401")) {
                                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login API", "Failure");
                    if (error == null || error.networkResponse == null) {
                        Toast.makeText(mContext, "Network error! Please try again in some time", Toast.LENGTH_SHORT).show();
                        Log.d("Login API", "Fail");
                        return;
                    }

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> parameter = new HashMap<>();
                    parameter.put("Content-Type", "application/json; charset=utf-8");
                    parameter.put("Authorization", token);
                    return parameter;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    final String request = jsonObject.toString();
                    try {
                        return request.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", request, "utf-8");
                        return null;
                    }
                }
            };
            queue.add(stringRequest);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

}
