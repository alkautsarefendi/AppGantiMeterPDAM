package com.project.fortuna.gantimeterpdam;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.Utils.Util;

import java.io.File;

public class CleanDatabaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnDataBelum, btnDataSudah, btnFolderFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_database);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Clean Database");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initComponent();
    }

    private void initComponent() {

        btnDataBelum = (Button) findViewById(R.id.btnDataBelum);
        btnDataBelum.setOnClickListener(new ButtonDataSudahOnClickHandler());
        btnDataSudah = (Button) findViewById(R.id.btnDataSudah);
        btnDataSudah.setOnClickListener(new ButtonSemuaOnClickHandler());
        btnFolderFoto = (Button) findViewById(R.id.btnFolderFoto);
        btnFolderFoto.setOnClickListener(new ButtonFolderFotoOnClickHandler());

    }

    private class ButtonDataSudahOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            doClearDatabaseBacaMeterBelumdiBaca();

        }
    }

    private class ButtonSemuaOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            doClearDatabaseBacaMeterAll();

        }
    }

    private class ButtonFolderFotoOnClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            doClearPhotoFolder();

        }
    }

    private void doClearDatabaseBacaMeterBelumdiBaca(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Peringatan : ");
        builder.setMessage("Semua Data Baca Meter Pelanggan yang Belum di Baca Akan di Hapus, Apakah Anda Yakin ? ").setCancelable(false);
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                new TbGantiMeter(CleanDatabaseActivity.this).doClearDatabaseBacaMeterBelumdiBaca();
                Util.showmsg(CleanDatabaseActivity.this, getString(R.string.action_settings), "Data DSML belum di baca berhasil di hapus");
            }

        });

        builder.setNegativeButton("Tidak", null).show();
    }


    private void doClearDatabaseBacaMeterAll(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Peringatan : ");
        builder.setMessage("Semua Data Baca Meter Pelanggan Akan di Hapus, Apakah Anda Yakin ? ").setCancelable(false);
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                new TbGantiMeter(CleanDatabaseActivity.this).delete();
                Util.showmsg(CleanDatabaseActivity.this, getString(R.string.action_settings), "Semua Data DSML berhasil di hapus");
            }
        });

        builder.setNegativeButton("Tidak", null).show();
    }


    private void doClearPhotoFolder(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Peringatan : ");
        builder.setMessage("Apakah Anda Yakin Menghapus Folder Foto ? ").setCancelable(false);
        builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                File dir = new File(Environment.getExternalStorageDirectory() + "/GantiMeter/Foto/");
                if (dir.isDirectory())
                {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(dir, children[i]).delete();
                    }
                }

                Util.showmsg(CleanDatabaseActivity.this, getString(R.string.action_settings), "Semua File Foto berhasil di hapus");

            }
        });

        builder.setNegativeButton("Tidak", null).show();
    }
}
