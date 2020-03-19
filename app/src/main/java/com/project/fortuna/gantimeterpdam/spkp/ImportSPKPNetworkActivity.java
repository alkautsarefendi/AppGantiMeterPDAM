package com.project.fortuna.gantimeterpdam.spkp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.project.fortuna.gantimeterpdam.DAO.DefaultDTO;
import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.DAO.TbKonfigurasi;
import com.project.fortuna.gantimeterpdam.R;
import com.project.fortuna.gantimeterpdam.Utils.ConnectivityWS;
import com.project.fortuna.gantimeterpdam.Utils.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportSPKPNetworkActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/CSV/";

    private ListView listView;
    private ListAdapter adapter;
    private List<TbGantiMeter> listPemasangan = new ArrayList<>();
    private Button btnImportFile;
    ProgressDialog barProgressDialog;
    Handler updateBarHandler;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_spkpnetwork);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Import File Network");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        updateBarHandler = new Handler();

        initComponent();
    }

    private void initComponent() {

        /* Button Import */
        /* Button Import */
        btnImportFile = (Button) findViewById(R.id.btnImportFile);
        btnImportFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listPemasangan.isEmpty()) {
                    boolean flag = false;
                    try {
                        for (TbGantiMeter tm : listPemasangan) {
                            tm.KODE_PELANGGAN = "'" + tm.KODE_PELANGGAN;
                            if (tm.retrieveByID() != null) {
                                tm.update();
                                flag = true;
                            } else {
                                tm.save();
                            }
                        }
                    } catch (Exception e) {
                        Util.showmsg(getApplicationContext(), "Peringatan :", "Data Gagal di Import");
                    }
                    btnImportFile.setEnabled(false);
                    if (!flag) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ImportSPKPNetworkActivity.this);
                        builder.setTitle("Import From Network")
                                .setMessage("Import Data Berhasil")
                                .setCancelable(false)
                                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setCancelable(false);
                        alert.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ImportSPKPNetworkActivity.this);
                        builder.setTitle("Import From Network")
                                .setMessage("Data Berhasil diperbaharui")
                                .setCancelable(false)
                                .setNegativeButton("Close", new
                                        DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.setCancelable(false);
                        alert.show();
                    }
                }
            }
        });

        /* Button Cancel */
        Button btnCancelDownload = (Button) findViewById(R.id.btnCancelDownload);
        btnCancelDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // init
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {

                Log.v("ISA", "ERROR: Creation of directory " + DATA_PATH
                        + " on sdcard failed");
                return;
            } else {

                Log.v("ISA", "Created directory " + DATA_PATH + " on sdcard");

            }
        }

        TbKonfigurasi km = new TbKonfigurasi(getApplicationContext());
        km.kdKonfigurasi = "1";
        km = km.retrieveByID();
        if(km != null && !km.getDownloadURL().trim().equals("")){
            listView = (ListView) findViewById(R.id.id_import_list_view_server);
            adapter = new ImportSPKPAdapter(this, listPemasangan) ;
            listView.setAdapter(adapter);

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("GantiMeterApp", 0); // 0 - for private mode

            DefaultDTO dto = new DefaultDTO(getApplicationContext());
            dto.USERID = pref.getString("userid", "");
            dto.KD_WILAYAH = pref.getString("kdWilayah", "");
            dto.PERIODE = new SimpleDateFormat("yyyyMM").format(new Date());
            JSONObject response = ConnectivityWS.postToServer( dto, km.getDownloadURL());
            System.out.println(response);
            try {
                if(response != null && response.has("CODE") && response.get("CODE").equals("00")){
                    JSONArray jsonArray = response.getJSONArray("DATA");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TbGantiMeter tPemasangan = new TbGantiMeter(getApplicationContext());
                        tPemasangan.KODE_PELANGGAN = jsonArray.getJSONObject(i).getString("KODE_PELANGGAN");
                        tPemasangan.KD_WILAYAH = jsonArray.getJSONObject(i).getString("KD_WILAYAH");
                        tPemasangan.KD_JALAN = jsonArray.getJSONObject(i).getString("KD_JALAN");
                        tPemasangan.NO_SPKP = jsonArray.getJSONObject(i).getString("NO_SPKP");
                        tPemasangan.NO_BAP = jsonArray.getJSONObject(i).getString("NO_BAP");
                        tPemasangan.NAMA_PELANGGAN = jsonArray.getJSONObject(i).getString("NAMA_PELANGGAN");
                        tPemasangan.ALAMAT_PELANGGAN = jsonArray.getJSONObject(i).getString("ALAMAT_PELANGGAN");
                        tPemasangan.GPS_LAT_PELANGGAN = jsonArray.getJSONObject(i).getString("GPS_LAT_PELANGGAN");
                        tPemasangan.GPS_LONG_PELANGGAN = jsonArray.getJSONObject(i).getString("GPS_LONG_PELANGGAN");
                        tPemasangan.NO_TERA_BARU = jsonArray.getJSONObject(i).getString("NO_TERA_BARU");
                        listPemasangan.add(tPemasangan);
                    }
                    hidePDialog();
                }else{
                    hidePDialog();
                    if(response == null)
                        Util.showmsg(ImportSPKPNetworkActivity.this, "Peringatan :", "Permintaan gagal");
                    else
                        Util.showmsg(ImportSPKPNetworkActivity.this, "Peringatan :", "Permintaan gagal, response is "+response.getString("CODE"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Util.showmsg(ImportSPKPNetworkActivity.this, "Peringatan :", "Permintaan gagal, error is "+e.getMessage());
            }
        }else{
            Util.showmsg(ImportSPKPNetworkActivity.this, "Peringatan :", "Silahkan konfigurasi url download terlebih dahulu");
        }
    }

    @SuppressWarnings({ "static-access", "unused" })
    private void launchBarDialog() {

        barProgressDialog = new ProgressDialog(this);

        barProgressDialog.setTitle("Downloading Image ...");
        barProgressDialog.setMessage("Download in progress ...");
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
        barProgressDialog.setProgress(0);
        barProgressDialog.setMax(20);
        barProgressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Here you should write your time consuming task...
                    while (barProgressDialog.getProgress() <= barProgressDialog.getMax()) {

                        Thread.sleep(2000);

                        updateBarHandler.post(new Runnable() {

                            public void run() {

                                barProgressDialog.incrementProgressBy(2);

                            }

                        });


                        if (barProgressDialog.getProgress() == barProgressDialog.getMax()) {

                            barProgressDialog.dismiss();

                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
