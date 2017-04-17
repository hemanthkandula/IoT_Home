package com.hemanthkandula.app.myhome;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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
import java.util.List;

public class HomeActivity extends AppCompatActivity {


    SharedPreferences prefs;

    public static final String lightkey = "light";
    public static final String doorkey = "door";
    public static final String windowkey = "window";
    public static final String fankey = "fan";


    public static final String UserPref = "Userpref";
    public static final String Regikey = "RegiKey";
    public static final String Passkey = "PassKey";
    public static final String Clusterkey = "Clusterkey";


    Switch fan,light,window,door;
    String  doo,lig,fa,win;
    Button sens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

getdata();
         fan = (Switch)findViewById(R.id.fan);

         light = (Switch)findViewById(R.id.light);

         window = (Switch)findViewById(R.id.window);
         door = (Switch)findViewById(R.id.door);

sens = (Button)findViewById(R.id.sens);
doo="0";
fa="0";
        lig="0";
        win="0";
sens.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(HomeActivity.this,Sensors.class));

    }
});
        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    fa = "1";
                }
                else fa="0";
                changeprefs();
                getdata();

//                System.out.println(
//                        doo + " ");
            }
        });


        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    lig = "1";
                }
                else lig="0";
                changeprefs();
                getdata();

//                System.out.println(
//                        lig + " ");
            }
        });


        door.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    doo = "1";
                }
                else doo="0";
                changeprefs();
                getdata();
                System.out.println(
                        "door  " + " is ding checked");
            }
        });

        window.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    win = "1";
                }
                else win="0";
                changeprefs();getdata();
                System.out.println(
                        "door  " + " is ding checked");
            }
        });



//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    getdata();
//                }
//            }
//        }).start();
//
//
//getdata();




    }





    private void getdata() {

        class LoginAsync extends AsyncTask<String, String, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }


            @Override
            protected String doInBackground(String... params) {

                InputStream is = null;
                String result = null;
                try {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("mob", prefs.getString(Regikey,null)));

                    nameValuePairs.add(new BasicNameValuePair("pass", prefs.getString(Passkey,null)));
                    nameValuePairs.add(new BasicNameValuePair("door", String.valueOf(doo)));

                    nameValuePairs.add(new BasicNameValuePair("light", String.valueOf(lig)));
                    nameValuePairs.add(new BasicNameValuePair("fan", String.valueOf(fa)));
                    nameValuePairs.add(new BasicNameValuePair("window", String.valueOf(win)));
System.out.println(nameValuePairs);
//Sy

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://rcssastra.96.lt/app/put.php");
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

                    s = result.trim();

                    try {
                        JSONArray arr = new JSONArray(result);
                        System.out.println(arr);
                        if (arr.length() != 0) {
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = (JSONObject) arr.get(i);


//                                    lig = obj.get("light").toString();
//                                fa = obj.get("fan").toString();
//                                        doo = obj.get("door").toString();
//                                        win = obj.get("window").toString();

                                prefs = getApplicationContext().getSharedPreferences(UserPref, 0);
                                final SharedPreferences.Editor editor = prefs.edit();
                                //  try {


                                editor.putBoolean(doorkey, obj.get("door").toString().equals("1"));
                                editor.putBoolean(windowkey, obj.get("window").toString().equals("1"));

                                editor.putBoolean(lightkey, obj.get("light").toString().equals("1"));
                                editor.putBoolean(fankey, obj.get("fan").toString().equals("1"));
                                Boolean a = editor.commit();

//light.setChecked(Boolean.parseBoolean(lig));
//
//                                door.setChecked(Boolean.parseBoolean(doo));
//                                window.setChecked(Boolean.parseBoolean(win));
//                                fan.setChecked(Boolean.parseBoolean(fa));
uichnage();
                            }


                        }


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


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


    void uichnage(){



                                door.setChecked(prefs.getBoolean(doorkey,true));
                                window.setChecked((prefs.getBoolean(windowkey, true)));
                                fan.setChecked((prefs.getBoolean(fankey, true)));
        light.setChecked((prefs.getBoolean(lightkey, true)));



    }
    void changeprefs(){

        prefs = getApplicationContext().getSharedPreferences(UserPref, 0);
        final SharedPreferences.Editor editor = prefs.edit();
        //  try {


        editor.putBoolean(doorkey, doo.equals("1"));
        editor.putBoolean(windowkey,win.equals("!"));

        editor.putBoolean(lightkey,lig.equals("1") );
        editor.putBoolean(fankey,fan.equals("1") );
        Boolean a = editor.commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {


            case R.id.action_log_out:
                prefs = getApplicationContext().getSharedPreferences(UserPref, 0);
                final SharedPreferences.Editor editor = prefs.edit();
                editor.remove(Clusterkey);
                editor.commit();
                Intent Cl = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(Cl);
                finish();
                return true;

            case R.id.action_exit:
                onBackPressed();
                // check for updates action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
