package com.project.fortuna.gantimeterpdam.DAO;

import android.content.Context;
import android.database.Cursor;

import com.project.fortuna.gantimeterpdam.annotation.Column;
import com.project.fortuna.gantimeterpdam.annotation.PrimaryKey;
import com.project.fortuna.gantimeterpdam.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tabelblok")
public class TbBlok extends BaseDAO{

    public TbBlok(Context context) {
        super(context);
    }


    @PrimaryKey
    @Column
    public String kdWilayah;

    @Column
    public String kdJalan;

    @Column
    public String nmJalan;


    public String getKdWilayah() {
        return kdWilayah;
    }

    public void setKdWilayah(String kdWilayah) {
        this.kdWilayah = kdWilayah;
    }

    public String getKdJalan() {
        return kdJalan;
    }

    public void setKdJalan(String kdJalan) {
        this.kdJalan = kdJalan;
    }

    public String getNmJalan() {
        return nmJalan;
    }

    public void setNmJalan(String nmJalan) {
        this.nmJalan = nmJalan;
    }



    @Override
    public List<TbBlok> retrieveAll() {
        List<TbBlok> j = new ArrayList<TbBlok>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getAllData(this);
        if(cursor.moveToFirst()){
            do{
                TbBlok mjal = new TbBlok(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(mjal, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                j.add(mjal);
            }while(cursor.moveToNext());
        }
        db.close();
        return j;
    }

    public List<TbBlok> retrieveByWilayah(String kdWilayah) {
        List<TbBlok> j = new ArrayList<TbBlok>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getReadDB().rawQuery("SELECT * FROM tabelblok where kdWilayah=?", new String[]{kdWilayah});
        if(cursor.moveToFirst()){
            do{
                TbBlok mjal = new TbBlok(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(mjal, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                j.add(mjal);
            }while(cursor.moveToNext());
        }
        db.close();
        return j;
    }

    @Override
    public TbBlok retrieveByID() {
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getDataByPrimaryKey(this);
        if(cursor != null) cursor.moveToFirst();
        String[] columnNames = cursor.getColumnNames();
        for (String string : columnNames) {
            try {
                Field declaredField = this.getClass().getDeclaredField(string);
                declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
            } catch (NoSuchFieldException e) {
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            }

        }
        db.close();
        return this;
    }
}
