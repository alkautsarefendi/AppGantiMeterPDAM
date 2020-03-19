package com.project.fortuna.gantimeterpdam.config;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.fortuna.gantimeterpdam.Utils.ConnectivityWS;
import com.project.fortuna.gantimeterpdam.DAO.DBUtils;
import com.project.fortuna.gantimeterpdam.DAO.TbBlok;
import com.project.fortuna.gantimeterpdam.DAO.TbKonfigurasi;
import com.project.fortuna.gantimeterpdam.DAO.TbUser;
import com.project.fortuna.gantimeterpdam.DAO.TbWilayah;
import com.project.fortuna.gantimeterpdam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class KonfigurasiActivity extends AppCompatActivity {

    private TextView inputIP;
    private EditText txtInputIP;
    private Button btnSyncUser, btnSyncWilayah, btnSyncBlock, btnSimpanSync, btnCancelSync;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    Snackbar snackbar;
    private static final String TAG = KonfigurasiActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfigurasi);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Konfigurasi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        isInternetOn();
        initComponent();
        initData();
    }

    private void initData() {
        TbKonfigurasi mk = new TbKonfigurasi(KonfigurasiActivity.this);
        //mk.kdKonfigurasi = "1";
        mk = mk.retrieveByID();
        if (mk != null) {

            txtInputIP.setText(mk.userURL);

        } else {

            txtInputIP.setText("");
            Toast.makeText(getApplicationContext(), "Belum dilakukan Konfigurasi", Toast.LENGTH_SHORT).show();

        }
    }

    private void initComponent() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayoutKonfig);
        inputIP = (TextView) findViewById(R.id.text_dummy_hint_inputIP);
        txtInputIP = (EditText) findViewById(R.id.txtInputIP);
        txtInputIP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            inputIP.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (txtInputIP.getText().length() > 0)
                        inputIP.setVisibility(View.VISIBLE);
                    else
                        inputIP.setVisibility(View.INVISIBLE);
                }
            }
        });


        btnSyncUser = (Button) findViewById(R.id.btnSyncUser);
        btnSyncUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipPort = txtInputIP.getText().toString();
                System.out.println("tes = " + txtInputIP.getText().toString());
                if (ipPort.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "IP dan Port tidak boleh kosong", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });

                } else {
                    SyncUser syncUser = new SyncUser();
                    syncUser.execute();
                }
            }
        });

        btnSyncWilayah = (Button) findViewById(R.id.btnSyncWilayah);
        btnSyncWilayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipPort = txtInputIP.getText().toString();
                System.out.println("tes = " + txtInputIP.getText().toString());
                if (ipPort.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "IP dan Port tidak boleh kosong", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });

                } else {
                    SyncWilayah syncWilayah = new SyncWilayah();
                    syncWilayah.execute();
                }
            }
        });

        btnSyncBlock = (Button) findViewById(R.id.btnSyncBlok);
        btnSyncBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipPort = txtInputIP.getText().toString();
                System.out.println("tes = " + txtInputIP.getText().toString());
                if (ipPort.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "IP dan Port tidak boleh kosong", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });

                } else {
                    SyncBlock syncBlock = new SyncBlock();
                    syncBlock.execute();
                }
            }
        });

        btnSimpanSync = (Button)findViewById(R.id.btnSimpanConfig);
        btnSimpanSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipPort = txtInputIP.getText().toString();
                System.out.println("tes = " + txtInputIP.getText().toString());
                if (ipPort.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "Mohon melakukan konfigurasi dulu", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });

                } else {
                    inputDataConfig();
                    clearText();
                }
            }
        });

        btnCancelSync = (Button)findViewById(R.id.btnBackConfig);
        btnCancelSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class SyncUser extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(KonfigurasiActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String userURL = String.valueOf(txtInputIP.getText());
            String url = "http://" + userURL + "/pdamws/pdam/corev2?USERID=123&WEBCMD=SyncUser&PDAMID=PDAMCoreV2";
            TbUser domain = new TbUser(getApplicationContext());
            domain.setImei(DBUtils.getDeviceID(KonfigurasiActivity.this));
            JSONObject object = ConnectivityWS.postToServer(domain, url);
            System.out.println("object = " + object);
            if (object != null && object.has("DATA") || (object.has("CODE"))) {
                domain.delete();
                try {
                    JSONArray jsonArray = object.getJSONArray("DATA");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TbUser muser = domain;
                        JSONObject juser = jsonArray.getJSONObject(i);
                        Iterator keys = juser.keys();
                        while (keys.hasNext()) {
                            try {
                                String key = ((String) keys.next()).trim();
                                if (key.equals("USER_ID")) {
                                    muser.setUserid(String.valueOf(juser.get(key)));
                                } else {
                                    muser.getClass().getDeclaredField(DBUtils.formatField(key)).set(muser, String.valueOf(juser.get(key)));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        muser.save();
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi gagal", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
                snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi Berhasil", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class SyncWilayah extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(KonfigurasiActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String userURL = String.valueOf(txtInputIP.getText());
            String url = "http://" + userURL + "/pdamws/pdam/corev2?USERID=123&WEBCMD=SyncWilayah&PDAMID=PDAMCoreV2";
            TbWilayah domain = new TbWilayah(getApplicationContext());
            JSONObject object = ConnectivityWS.postToServer(domain, url);
            System.out.println("object = " + object);
            if (object != null && object.has("DATA") || (object.has("CODE"))) {
                new TbWilayah(getApplicationContext()).delete();
                try {
                    JSONArray jsonArray = object.getJSONArray("DATA");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TbWilayah mwilayah = domain;
                        JSONObject juser = jsonArray.getJSONObject(i);
                        Iterator keys = juser.keys();
                        while (keys.hasNext()) {
                            try {
                                String key = ((String) keys.next()).trim();
                                if (key.equals("KD_WILAYAH")) {
                                    mwilayah.setKdWilayah(String.valueOf(juser.get(key)));
                                } else {
                                    mwilayah.getClass().getDeclaredField(DBUtils.formatField(key)).set(mwilayah, String.valueOf(juser.get(key)));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        mwilayah.save();
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi gagal", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
                snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi Berhasil", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    public class SyncBlock extends AsyncTask<String, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(KonfigurasiActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String userURL = String.valueOf(txtInputIP.getText());
            String url = "http://" + userURL + "/pdamws/pdam/corev2?USERID=123&WEBCMD=SyncJalan&PDAMID=PDAMCoreV2";
            TbBlok domain = new TbBlok(getApplicationContext());
            JSONObject object = ConnectivityWS.postToServer(domain, url);
            System.out.println("object = " + object);
            if (object != null && object.has("DATA") || (object.has("CODE"))) {
                domain.delete();
                try {
                    JSONArray jsonArray = object.getJSONArray("DATA");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TbBlok muser = domain;
                        JSONObject juser = jsonArray.getJSONObject(i);
                        Iterator keys = juser.keys();
                        while (keys.hasNext()) {
                            try {
                                String key = ((String) keys.next()).trim();
                                if (key.equals("KD_JALAN")) {
                                    muser.setKdJalan(String.valueOf(juser.get(key)));
                                } else {
                                    muser.getClass().getDeclaredField(DBUtils.formatField(key)).set(muser, String.valueOf(juser.get(key)));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        muser.save();

                    }

                } catch (final JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi gagal", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi gagal", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
                snackbar = Snackbar.make(coordinatorLayout, "Konfigurasi Berhasil", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }
    }

    private void inputDataConfig() {
        try{
            TbKonfigurasi mk = new TbKonfigurasi(KonfigurasiActivity.this);

            String userURL = String.valueOf(txtInputIP.getText());
            String urlUser = "http://"+userURL+"/pdamws/pdam/corev2?USERID=123&WEBCMD=SyncUser&PDAMID=PDAMCoreV2";
            String wilayahURL = String.valueOf(txtInputIP.getText());
            String urlWIlayah = "http://"+wilayahURL+"/pdamws/pdam/corev2?USERID=123&WEBCMD=SyncWilayah&PDAMID=PDAMCoreV2";
            String blockURL = String.valueOf(txtInputIP.getText());
            String urlBlock = "http://"+blockURL+"/pdamws/pdam/corev2?USERID=123&WEBCMD=SyncJalan&PDAMID=PDAMCoreV2";
            String downloadURL = String.valueOf(txtInputIP.getText());
            String urlDownload = "http://"+downloadURL+"/pdamws/pdam/corev2?WEBCMD=DownloadDsml&PDAMID=PDAMCoreV2";
            String uploadURL = String.valueOf(txtInputIP.getText());
            String urlUpload = "http://"+uploadURL+"/pdamws/pdam/corev2?WEBCMD=MeterReading&PDAMID=PDAMCoreV2";

            mk.kdKonfigurasi = "1";
            mk.wilayahURL = urlWIlayah;
            mk.userURL = urlUser;
            mk.jalanURL = urlBlock;
            mk.downloadURL = urlDownload;
            mk.uploadURL = urlUpload;

            mk.update();

            if (mk.retrieveByID() != null) {
                mk.update();
                System.out.println("dataupdate = ");
            } else {
                mk.save();
                System.out.println("datasave = ");
            }

//			if(mk.retrieveByID() != null){
//				mk.update();
//			} else if (mk.userURL.equals("") || mk.userURL == null) {
//				mk.userURL = txtUser.getText().toString();
//			} else {
//				mk.save();
//			}


            new AlertDialog.Builder(this)
                    .setTitle("Simpan Data")
                    .setMessage("Data konfigurasi telah disimpan")
                    //.setIcon(android.R.drawable.ic_dialog_info)
                    .setNeutralButton("Ok ", null)
                    .show();
        }catch(Exception e){
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Simpan Data")
                    .setMessage("Simpan data gagal karena : "+e.getMessage())
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setNeutralButton("Ok ", null)
                    .show();

        }
    }

    private void clearText() {

        txtInputIP.setText("");

    }

    public final boolean isInternetOn() {

        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            Log.e(TAG, "Connected");
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(KonfigurasiActivity.this);

            alertDialog.setMessage("No Internet Connection")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            }).setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            KonfigurasiActivity.super.onBackPressed();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(new Intent(KonfigurasiActivity.this, LoginActivity.class));
                        }
                    });
            AlertDialog alert = alertDialog.create();
            alert.show();
            return false;
        }
        return false;
    }

}
