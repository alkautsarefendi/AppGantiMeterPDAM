package com.project.fortuna.gantimeterpdam.export;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.DAO.TbKonfigurasi;
import com.project.fortuna.gantimeterpdam.R;
import com.project.fortuna.gantimeterpdam.Utils.ConnectivityWS;
import com.project.fortuna.gantimeterpdam.Utils.Util;

import org.json.JSONObject;

import java.util.List;

public class ExportDariJaringanActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private List<TbGantiMeter> listMeter;
    private TbGantiMeter selectedMeter;
    private Button btnKirimSemua;
    private ListView listExport;

    private int flag;

    protected String userid;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_dari_jaringan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Export File Network");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pref = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);
        userid = pref.getString("userid", "");

        initComponent();
    }

    private void initComponent() {

        listExport = (ListView) findViewById(R.id.id_export_list_view);

        listMeter = new TbGantiMeter(ExportDariJaringanActivity.this).retrieveForExportServer(userid);
        if (listMeter.size() > 0) {
            ExportFileGMAdapter adapter = new ExportFileGMAdapter(this, listMeter);
            listExport.setAdapter(adapter);
        } else {
            System.out.print(listMeter.size());
            AlertDialog.Builder builder = new AlertDialog.Builder(ExportDariJaringanActivity.this);
            builder.setTitle("Peringatan :")
                    .setMessage("Status " + getString(R.string.msg_no_data))
                    .setCancelable(false)
                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setCancelable(false);
            alert.show();
        }


        /* Button Kirim */
        btnKirimSemua = (Button) findViewById(R.id.btnSendChecked);
        btnKirimSemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String allnoreg = "";
                    for (int i = 0; i < listExport.getCount(); i++) {
                        View vg = listExport.getChildAt(i);
                        CheckBox cb = (CheckBox)findViewById(R.id.cb);
                        if (cb.isChecked()) {
                            TbGantiMeter m = listMeter.get(i);
                            allnoreg += m.getKODE_PELANGGAN();
                        }
                    }
                    if (allnoreg != "") {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ExportDariJaringanActivity.this);
                        builder.setTitle("Peringatan : ");
                        builder.setMessage("Apakah Anda Ingin Mengirim Data Dengan Kode Pel : " + allnoreg.replace("'", "") + " ke Server ?").setCancelable(false);
                        builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                TbKonfigurasi km = new TbKonfigurasi(getApplicationContext());
                                km.kdKonfigurasi = "1";
                                km = km.retrieveByID();
                                flag = 0;
                                if (km != null && km.uploadURL != null && !km.uploadURL.trim().equals("")) {
                                    for (int i = 0; i < listExport.getCount(); i++) {
                                        View vg = listExport.getChildAt(i);
                                        CheckBox cb = (CheckBox) vg.findViewById(R.id.cb);
                                        if (cb.isChecked()) {
                                            selectedMeter = listMeter.get(i);
                                            selectedMeter.KODE_PELANGGAN = selectedMeter.KODE_PELANGGAN.replace("'", "");
                                            try {
                                                JSONObject response = ConnectivityWS.postPemasangan(selectedMeter, km.uploadURL);
                                                if (response.getString("CODE").equals("00")) {
                                                    selectedMeter.S_PASANG = "1";
                                                    selectedMeter.KODE_PELANGGAN = "'" + selectedMeter.KODE_PELANGGAN;
                                                    selectedMeter.update();
                                                    flag++;
                                                } else throw new Exception("Gagal terkirim");
                                            } catch (Exception e) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(ExportDariJaringanActivity.this);
                                                builder.setTitle("Export To Network")
                                                        .setMessage("Gagal terkirim")
                                                        .setCancelable(false)
                                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {

                                                            }
                                                        });
                                                AlertDialog alert = builder.create();
                                                alert.setCancelable(false);
                                                alert.show();
                                            }
                                        }
                                    }
                                } else {
                                    Util.showmsg(ExportDariJaringanActivity.this, "Export ke server", "Konfigurasi url belum tersedia.");
                                }
                                if (flag > 0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ExportDariJaringanActivity.this);
                                    builder.setTitle("Export To Network")
                                            .setMessage(flag + " Data Sukses dikirim")
                                            .setCancelable(false)
                                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    finish();
                                                    startActivity(getIntent());
                                                }
                                            });
                                    AlertDialog alert = builder.create();
                                    alert.setCancelable(false);
                                    alert.show();
                                }
                            }
                        });

                        builder.setNegativeButton("Batal", null).show();
                    } else {
                        throw new Exception("Tidak ada data yang dipilih");
                    }
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExportDariJaringanActivity.this);
                    builder.setTitle("Peringatan : ");
                    builder.setMessage(e.getMessage()).setCancelable(false);
                    builder.setNegativeButton("Batal", null).show();
                }
            }
        });
    }
}
