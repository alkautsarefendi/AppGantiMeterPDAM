package com.project.fortuna.gantimeterpdam.review;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.R;
import com.project.fortuna.gantimeterpdam.Utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LihatDataSudahActivity extends ListActivity implements SearchView.OnQueryTextListener{

    private Toolbar toolbar;

    private String[] menuItems = {""};
    private List<TbGantiMeter> listMeter;
    private List<TbGantiMeter> tempListMeter;
    private TextView txtTotal;
    private ListView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_sudah);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lihat Data Sudah");
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtTotal = (TextView) findViewById(R.id.txtViewTotal);

        initListData();
    }

    private void initListData(){
        Bundle extras = getIntent().getExtras();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);
        String userid = sharedPreferences.getString("userid", "");
        String sTglMeter = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(sTglMeter);

        listMeter = new TbGantiMeter(LihatDataSudahActivity.this).retrieveForReview(userid, sTglMeter, false, extras.getBoolean("isSukses"));
        tempListMeter = listMeter;

        /* CheckBox Sukses */
        if (extras.getBoolean("isSukses"))
            txtTotal.setText("Total Data yang Sukses di Baca = " + listMeter.size());
        {
            if(listMeter.size() > 0){
                LihatDataPelangganAdapter adapter = new LihatDataPelangganAdapter(this, listMeter);
                setListAdapter(adapter);
            }else{
                setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems));
                AlertDialog.Builder builder = new AlertDialog.Builder(LihatDataSudahActivity.this);
                builder.setTitle("Peringatan :")
                        .setMessage("Status " + getString(R.string.msg_no_data))
                        .setCancelable(false)
                        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
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

    /* Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuviewsudah, menu);
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_sukses).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(LihatDataSudahActivity.this);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextSubmit(String newText)
    {
        // this is your adapter that will be filtered
        if (TextUtils.isEmpty(newText)) {

        } else {
            Bundle extras = getIntent().getExtras();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);
            String userid = sharedPreferences.getString("userid", "");
            String sTglMeter = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
            tempListMeter = listMeter;
            List<TbGantiMeter> lm = new TbGantiMeter(LihatDataSudahActivity.this).retrieveForReview(userid, sTglMeter, false, extras.getBoolean("isSukses"));
            lm.clear();
            for (TbGantiMeter tb : listMeter) {
                if (tb.getALAMAT_PELANGGAN().toLowerCase().contains(newText.toLowerCase())) {
                    lm.add(tb);
                }
            }
            LihatDataPelangganAdapter adapter = new LihatDataPelangganAdapter(this, lm);
            tempListMeter = lm;
            reviewList.setAdapter(adapter);
        }

        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.equals("") || newText.isEmpty()) {
            initListData();
        }
        return false;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        try {
            final TbGantiMeter lvm = tempListMeter.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Review Data : ");
            builder.setMessage("Lihat Detail Data Ganti Meter Pelanggan : "+ lvm.getNAMA_PELANGGAN() + " ?").setCancelable(false);
            builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Util.showmsg(LihatDataSudahActivity.this, getString(R.string.action_view), "Kode Pelanggan = "
                            + lvm.getKODE_PELANGGAN().replace("'", "")
                            + "\n" + "No SPKP = " + lvm.getNO_SPKP()
                            + "\n" + "No BAP = " + lvm.getNO_BAP()
                            + "\n" + "Nama = " + lvm.getNAMA_PELANGGAN()
                            + "\n" + "Alamat = " + lvm.getALAMAT_PELANGGAN()
                            + "\n" + "GPS Lat Pel = " + lvm.getGPS_LAT_PASANG()
                            + "\n" + "GPS Long Pel = " + lvm.getGPS_LONG_PASANG()
                            + "\n" + "No Meter Air Lama = " + lvm.getKD_STAND_METER_LAMA()
                            + "\n" + "No Meter Air Baru = " + lvm.getKD_STAND_METER_BARU()
                            + "\n" + "Stand Angkat = " + lvm.getANGKA_METER_BARU()
                            + "\n" + "Tanggal Pasang = " + lvm.getTGL_PASANG());
                }
            });

            builder.setNegativeButton("Tidak", null).show();

        } catch (Exception e) {

            e.printStackTrace();
            Log.e("getData", "Error: " + e.toString());
            Util.showmsg(this, "Peringatan :", e.toString());

        }
    }
}
