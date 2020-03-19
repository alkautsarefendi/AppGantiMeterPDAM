package com.project.fortuna.gantimeterpdam.review;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.input.InputGantiMeterActivity;
import com.project.fortuna.gantimeterpdam.R;
import com.project.fortuna.gantimeterpdam.Utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LihatDataBelumActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private Toolbar toolbar;

    private String[] menuItems = { "" };
    private List<TbGantiMeter> listPemasangan;
    private List<TbGantiMeter> tempListPemasangan;
    private TextView txtTotal;
    private ListView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_belum);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lihat Data Belum");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initComponent();
    }

    @SuppressLint("CutPasteId")
    private void initComponent() {

        txtTotal = (TextView) findViewById(R.id.txtViewTotal);
        reviewList = (ListView) findViewById(R.id.id_review_list_view);
        reviewList.setOnItemClickListener(new ListMeterBelumClickHandler());

        initListData();


    }

    @Override
    public void onResume(){  // After a pause OR at startup
        super.onResume();
        initListData();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        listPemasangan = null;
        menuItems = null;
        tempListPemasangan = null;
        txtTotal = null;
    }

    private void initListData() {

        Bundle extras = getIntent().getExtras();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);
        String userid = sharedPreferences.getString("userid", "");
        String sTglPemasangan = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        listPemasangan = new TbGantiMeter(LihatDataBelumActivity.this).retrieveForReview(userid, sTglPemasangan, extras.getBoolean("isBelum"), false);
        tempListPemasangan = listPemasangan;

        /* CheckBox Belum */
        if (extras.getBoolean("isBelum"))
            txtTotal.setText("Total Data yang Belum di Baca = " + listPemasangan.size());
        {
            if(listPemasangan.size() > 0){
                LihatDataPelangganAdapter adapter = new LihatDataPelangganAdapter(this, listPemasangan);
                reviewList.setAdapter(adapter);
            }else{
                reviewList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems));
                AlertDialog.Builder builder = new AlertDialog.Builder(LihatDataBelumActivity.this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        // this is your adapter that will be filtered
        if (query.isEmpty() || query.equals(""))
        {

        }
        else
        {
            Bundle extras = getIntent().getExtras();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("GantiMeterApp", 0);
            String userid = sharedPreferences.getString("userid", "");
            String sTglPemasangan = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            tempListPemasangan = listPemasangan;
            List<TbGantiMeter> lm = new TbGantiMeter(LihatDataBelumActivity.this).retrieveForReview(userid, sTglPemasangan, extras.getBoolean("isBelum"), false);
            lm.clear();
            for(TbGantiMeter pm:tempListPemasangan){
                if(pm.getALAMAT_PELANGGAN().toLowerCase().contains(query.toLowerCase())){
                    lm.add(pm);
                }
            }
            LihatDataPelangganAdapter adapter = new LihatDataPelangganAdapter(this, lm);
            tempListPemasangan = lm;
            reviewList.setAdapter(adapter);
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.equals("") || newText.isEmpty()) {
            initListData();
        }
        return false;

    }

    private class ListMeterBelumClickHandler implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {

            try {
                final TbGantiMeter lvm = tempListPemasangan.get(position);

                Intent intent = new Intent(LihatDataBelumActivity.this, InputGantiMeterActivity.class);
                intent.putExtra("key", lvm.getKODE_PELANGGAN().replace("'", ""));
                startActivity(intent);

            } catch (Exception e) {

                e.printStackTrace();
                Log.e("getData", "Error: " + e.toString());
                Util.showmsg(LihatDataBelumActivity.this, "Peringatan :", e.toString());

            }

        }
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
}
