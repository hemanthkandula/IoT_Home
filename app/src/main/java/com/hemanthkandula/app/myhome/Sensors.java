package com.hemanthkandula.app.myhome;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hemanthkandula.app.myhome.Databases.SqliteDB;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class Sensors extends AppCompatActivity {

    public static final String UserPref = "Userpref";
    public static final String Regikey = "RegiKey";
    public static final String Passkey = "PassKey";
    public static final String Clusterkey = "Clusterkey";
    static final Integer CALL = 0x1;
    String a = "";
    SwipeRefreshLayout mSwipeRefreshLayout;
    String mob, pass;
    HashMap<String, String> queryValues;
    String get, ksid, coll, sex, acc, mno, email;
    SharedPreferences prefs;
    EditText title;
    MenuItem item1;
    ArrayList<HashMap<String, String>> ParticipantsList = new ArrayList<>();
    SqliteDB sql = new SqliteDB(this);
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    //ProgressDialog prgDialog;
    private RadioButton First, Second, Third, Noplace;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mob = getIntent().getStringExtra("mob");
        pass = getIntent().getStringExtra("pass");
        getSupportActionBar().setTitle(mob);

//        prgDialog = new ProgressDialog(this);
//        prgDialog.setMessage(" Please wait...");
//        prgDialog.setCancelable(true);
        ParticipantsList = sql.getAllUsers();


        System.out.println(ParticipantsList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mAdapter = new MyRecyclerViewAdapter(movieList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);

        recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        // recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));


        prepareMovieData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                syncSQLiteMySQLDB();

                mAdapter.notifyDataSetChanged();

//                Intent Cl = new Intent(getApplicationContext(), Sensors.class);
//                Cl.putExtra("mob",mob);
//                Cl.putExtra("pass",pass);
//                startActivity(Cl);
//                finish();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    private void prepareMovieData() {
        Movie movie;
        int i;
        for (i = 0; i < ParticipantsList.size(); i++) {
            movie = new Movie("Temp:" + ParticipantsList.get(i).get("temp"), "Humid" + ParticipantsList.get(i).get("humid"), "Time:" + ParticipantsList.get(i).get("time"), "");
            movieList.add(movie);
        }
        mAdapter.notifyItemChanged(i);      //  mAdapter.notifyDataSetChanged();

    }


    private void syncSQLiteMySQLDB() {

        class LoginAsync extends AsyncTask<String, String, String> {

            //   ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                progressDialog = new ProgressDialog(Sensors.this, R.style.);
//                progressDialog.setIndeterminate(true);
//                progressDialog.setMessage("making  no DUE...");
//                progressDialog.show();

            }


            @Override
            protected String doInBackground(String... params) {

                InputStream is = null;
                String result = null;
                try {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                    nameValuePairs.add(new BasicNameValuePair("mob", mob));
                    nameValuePairs.add(new BasicNameValuePair("pass", pass));


                    //nameValuePairs.add(new BasicNameValuePair("Naame", Name));
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://rcssastra.96.lt/app/sensors.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    System.out.println(nameValuePairs.toString());
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    Log.d("reuslt", result);
                } catch (Exception anyError) {

                    return null;
                }
//                catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                // progressDialog.dismiss();
                System.out.println(result);

                String res = null;
                String s = "";
                if (result != null) {
                    sql.onchange();

                    s = result.trim();
                    updateSQLite(result);

                }


                // progressDialog.cancel();
                if (result == null) {
                    return;
                }

            }

        }

        LoginAsync la = new LoginAsync();
        la.execute();

        //  }
//        catch (Exception e){
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
//
//        }


    }


    public void updateSQLite(String response) {

        try {
            JSONArray arr = new JSONArray(response);
            System.out.println(arr.length());
            if (arr.length() != 0) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = (JSONObject) arr.get(i);


                    queryValues = new HashMap<String, String>();
                    queryValues.put("humid", obj.get("humid").toString());
                    queryValues.put("temp", obj.get("temp").toString());
                    queryValues.put("time", obj.get("time").toString());


                    sql.insertUser(queryValues);


                    recview();


                }
//                updateMySQLSyncSts(gson.toJson(Eventsynclist));

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void recview() {

        ParticipantsList = sql.getAllUsers();
        // System.out.println(EventsList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MyRecyclerViewAdapter(movieList);


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);

        recyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));
        // recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        movieList.clear();


        prepareMovieData();
        mAdapter = new MyRecyclerViewAdapter(movieList);
        mAdapter.notifyDataSetChanged();

    }


}
