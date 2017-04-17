package com.hemanthkandula.app.myhome;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



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


public class MainActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    TextView login;
    TextView ks,org;
    String n,p,c;

    SharedPreferences prefs;
    public static final String UserPref = "Userpref";
    public static final String Regikey = "RegiKey";
    public static final String Passkey = "PassKey";
    public static final String Clusterkey = "Clusterkey";




    ProgressDialog prgDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Splash","1");

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        getSupportActionBar().hide();
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        ks = (TextView) findViewById(R.id.ks);
        org = (TextView) findViewById(R.id.org);
        user.setTypeface(custom_font);
        Log.d("Splash","2");

        pass.setTypeface(custom_font);
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/HACKED.ttf");
        Typeface custom_font3 = Typeface.createFromAsset(getAssets(), "fonts/RockSalt.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");

        ks.setTypeface(custom_font1);
        org.setTypeface(custom_font3);
        login = (TextView) findViewById(R.id.login);
        login.setTypeface(custom_font2);

        Log.d("Splash","3");
        prefs = getApplicationContext().getSharedPreferences(UserPref, Context.MODE_PRIVATE);
        Log.d("Splash","4");

        // regno =getPreference(this,Regikey);
        // password =getPreference(this,Passkey);
        if (prefs.contains(Regikey)&&prefs.contains(Passkey))
        {
            Log.d("Splash","5");
            n = prefs.getString(Regikey, null);
            p = prefs.getString(Passkey,null);
            Log.d("Splash","505");
            if (prefs.contains(Clusterkey)){
                Log.d("Splash","6");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                intent.putExtra("Cluster", prefs.getString(Clusterkey,null));
                Log.d("LoginClus", prefs.getString(Clusterkey,null));
                finish();
                Log.d("Splash","7");
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0,0);
            }

            Log.d("prefregi",n);
            Log.d("prefpass",p);

            user.setText(n);
            Log.d("getpref","I came get ");

            pass.setText(p);


        }



        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here

                    login();
                }
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!isNetworkConnected())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AppCompatAlertDialogStyle);
                    builder.setMessage("Connect to Internet to Authenticate!!")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    Intent i = new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(i);
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                login();
            }
        });


    }
    public void onLogin(View view) {
        Intent it = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(it);
    }


    private void login() {
        if (!validate()) {
            onLoginFailed();
        } else {
            String u,pas;
            n = user.getText().toString();
            p = pass.getText().toString();

            System.out.println("password:" + p);
            //userchanged(n);
//           setPreference(this,regno,Regikey);
//            setPreference(this,password,Passkey);
//

            try {


                if (prefs.contains(Regikey)) {
                    u = prefs.getString(Regikey, null);
                    //pas = prefs.getString(Passkey, null);
                    if (!u.equals(n)) {
                        login(n, p);
                        System.out.println("i droped");
                    } else {
                        login(u, p);
                    }

                } else {
                    login(n, p);
                }

            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void userchanged(String now){

        String regn;

        p = prefs.getString(Passkey,null);
        regn = prefs.getString(Passkey,null);

        if(!now.equals(regn)){
            // sql.dropDB();

        }
    }

    private void login(final String regno, String password) {
        prefs = getApplicationContext().getSharedPreferences(UserPref, 0);
        final SharedPreferences.Editor editor = prefs.edit();
        //  try {
        editor.putString(Regikey, regno);
        editor.putString(Passkey, password);
        Boolean a = editor.commit();
        System.out.println("commit");
        System.out.println(a);
        class LoginAsync extends AsyncTask<String, String, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

//                    prgDialog = new ProgressDialog(MainActivity.this,
//                            R.style.AppTheme_Dark_Dialog);

                prgDialog = new ProgressDialog(MainActivity.this,
                        R.style.AppCompatAlertDialogStyle);
                prgDialog.setIndeterminate(true);

                prgDialog.setMessage("Authenticating...");
                prgDialog.show();
                prgDialog.setCancelable(false);

            }


            @Override
            protected String doInBackground(String... params) {

                String regno = params[0];
                String password = params[1];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("RegNo", regno));
                nameValuePairs.add(new BasicNameValuePair("Password", password));
                String result = null;
                System.out.println(regno+password);
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://rcssastra.96.lt/app/login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    Log.d("reuslt",result);
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
                System.out.println(result);
                String res = null;
                String s = "";
                if(result!=null)
                {   s = result.trim();
                    res ="vjhjhg";}



                if (s != null && !s.equalsIgnoreCase("Failure") && res != null) {
//                    SharedPreferences sharedpreferences = getSharedPreferences(UserPref, Context.MODE_PRIVATE);
//
//
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                    editor.putString(Regikey, regno);
//                    editor.putString(Passkey, password);

                    prgDialog.dismiss();
                    if (!prefs.contains(Clusterkey)) {
                        //String Cluster = prefs.getString(Clusterkey, null);

                        editor.putString(Clusterkey, s);
                        editor.commit();}

//                        }else if (!prefs.getString(Clusterkey,null).equals(s)){
//                            editor.putString(Clusterkey, s);
//                            SqliteDB sqliteDB =new SqliteDB(getApplicationContext());
//                            sqliteDB.onchange();
//
//                        }



                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                    intent.putExtra("Cluster", prefs.getString(Clusterkey,null) );
                    // Log.d("LoginClus", prefs.getString(Clusterkey,null));
                    finish();
                    startActivity(intent);
                } else if (s.equalsIgnoreCase("Failure")) {
                    prgDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }else if(result==null)
                {
                    Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();
                    return;
                }

            }

        }
        if(!isNetworkConnected())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AppCompatAlertDialogStyle);
            builder.setMessage("Connect to Internet to Authenticate!!")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                            Intent i = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }else {
            LoginAsync la = new LoginAsync();
            la.execute(regno, password);
        }
        //  }
//        catch (Exception e){
//            Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
//
//        }
    }



    private boolean validate() {
        boolean valid = true;
        String reg1 = user.getText().toString();
        String pass1 = pass.getText().toString();
        if(reg1.isEmpty() || reg1.length() !=10)
        {
            user.setError("Enter a valid login ID");
            valid = false;
        }
        else
        {
            user.setError(null);
        }
        if(pass1.isEmpty() )
        {
            pass.setError("Enter a valid password");
            valid = false;
        }
        else
        {
            pass.setError(null);
        }
        return valid;
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login.setEnabled(true);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing..")
                .setMessage("Are you sure you want to Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}