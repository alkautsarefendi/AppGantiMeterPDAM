package com.project.fortuna.gantimeterpdam.DAO;

import android.content.Context;
import android.database.Cursor;

import com.project.fortuna.gantimeterpdam.annotation.Column;
import com.project.fortuna.gantimeterpdam.annotation.PrimaryKey;
import com.project.fortuna.gantimeterpdam.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tabelkonfigurasi")
public class TbKonfigurasi extends BaseDAO{

    public TbKonfigurasi(Context context) {
        super(context);
    }


    @Column
    public String kdKonfigurasi;

    @Column
    public String userFTP;

    @Column
    @PrimaryKey
    public String userURL;

    @Column
    public String wilayahURL;

    @Column
    public String jalanURL;

    @Column
    public String downloadURL;

    @Column
    public String uploadURL;

    public String getKdKonfigurasi() {
        return kdKonfigurasi;
    }

    public void setKdKonfigurasi(String kdKonfigurasi) {
        this.kdKonfigurasi = kdKonfigurasi;
    }

    public String getUserFTP() {
        return userFTP;
    }

    public void setUserFTP(String userFTP) {
        this.userFTP = userFTP;
    }

    public String getUserURL() {
        return userURL;
    }

    public void setUserURL(String userURL) {
        this.userURL = userURL;
    }

    public String getWilayahURL() {
        return wilayahURL;
    }

    public void setWilayahURL(String wilayahURL) {
        this.wilayahURL = wilayahURL;
    }

    public String getJalanURL() {
        return jalanURL;
    }

    public void setJalanURL(String jalanURL) {
        this.jalanURL = jalanURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }


    public String getUploadURL() {
        return uploadURL;
    }

    public void setUploadURL(String uploadURL) {
        this.uploadURL = uploadURL;
    }

    @Override
    public List<TbKonfigurasi> retrieveAll() {
        List<TbKonfigurasi> k = new ArrayList<TbKonfigurasi>();
        DBHelper db = new DBHelper(context);

        try {
            Cursor cursor = db.getAllData(this);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    TbKonfigurasi mk = new TbKonfigurasi(context);
                    String[] columnNames = cursor.getColumnNames();
                    for (String string : columnNames) {
                        try {
                            Field declaredField = this.getClass().getDeclaredField(string);
                            declaredField.set(mk, cursor.getString(cursor.getColumnIndex(string)));
                        } catch (NoSuchFieldException e) {
                        } catch (IllegalArgumentException e) {
                        } catch (IllegalAccessException e) {
                        }
                    }
                    k.add(mk);
                } while (cursor.moveToNext());
            }
            return k;
        } finally {
            db.close();
        }
    }

    @Override
    public TbKonfigurasi retrieveByID() {
        DBHelper db = new DBHelper(context);

        try {
            Cursor cursor = db.getDataByPrimaryKey(this);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor != null) cursor.moveToFirst();
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
                return this;
            }
            return null;
        } finally {
            db.close();
        }
    }
}
