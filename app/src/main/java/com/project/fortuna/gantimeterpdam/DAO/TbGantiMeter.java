package com.project.fortuna.gantimeterpdam.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.fortuna.gantimeterpdam.annotation.Column;
import com.project.fortuna.gantimeterpdam.annotation.PrimaryKey;
import com.project.fortuna.gantimeterpdam.annotation.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Table(name="tablegantimeter")
public class TbGantiMeter extends BaseDAO {

    public TbGantiMeter(Context context) {
        super(context);
    }

    @Column
    @PrimaryKey
    public String KODE_PELANGGAN;

    @Column
    public String KD_WILAYAH;

    @Column
    public String KD_JALAN;

    @Column
    public String NO_SPKP;

    @Column
    public String NO_BAP;

    @Column
    public String NAMA_PELANGGAN;

    @Column
    public String ALAMAT_PELANGGAN;

    @Column
    public String GPS_LAT_PELANGGAN;

    @Column
    public String GPS_LONG_PELANGGAN;

    @Column
    public String KD_STAND_METER_LAMA;

    @Column
    public String KD_STAND_METER_BARU;

    @Column
    public String MERK_METER_LAMA;

    @Column
    public String MERK_METER_BARU;

    @Column
    public String NO_TERA_LAMA;

    @Column
    public String NO_TERA_BARU;

    @Column
    public String ANGKA_METER_LAMA;

    @Column
    public String ANGKA_METER_BARU;

    @Column
    public String GPS_LAT_PASANG;

    @Column
    public String GPS_LONG_PASANG;

    @Column
    public String FOTO_LAMA;

    @Column
    public String FOTO_PASANG;

    @Column
    public String DIAMETER;

    @Column
    public String KETERANGAN;

    @Column
    public String TGL_PASANG;

    @Column
    public String S_PASANG;

    @Column
    public String USER_ID;


    public String getKODE_PELANGGAN() {
        return KODE_PELANGGAN;
    }



    public void setKODE_PELANGGAN(String kODE_PELANGGAN) {
        KODE_PELANGGAN = kODE_PELANGGAN;
    }



    public String getKD_WILAYAH() {
        return KD_WILAYAH;
    }



    public void setKD_WILAYAH(String kD_WILAYAH) {
        KD_WILAYAH = kD_WILAYAH;
    }



    public String getKD_JALAN() {
        return KD_JALAN;
    }



    public void setKD_JALAN(String kD_JALAN) {
        KD_JALAN = kD_JALAN;
    }



    public String getNO_SPKP() {
        return NO_SPKP;
    }



    public void setNO_SPKP(String nO_SPKP) {
        NO_SPKP = nO_SPKP;
    }



    public String getNO_BAP() {
        return NO_BAP;
    }



    public void setNO_BAP(String nO_BAP) {
        NO_BAP = nO_BAP;
    }



    public String getNAMA_PELANGGAN() {
        return NAMA_PELANGGAN;
    }



    public void setNAMA_PELANGGAN(String nAMA_PELANGGAN) {
        NAMA_PELANGGAN = nAMA_PELANGGAN;
    }



    public String getALAMAT_PELANGGAN() {
        return ALAMAT_PELANGGAN;
    }



    public void setALAMAT_PELANGGAN(String aLAMAT_PELANGGAN) {
        ALAMAT_PELANGGAN = aLAMAT_PELANGGAN;
    }



    public String getGPS_LAT_PELANGGAN() {
        return GPS_LAT_PELANGGAN;
    }



    public void setGPS_LAT_PELANGGAN(String gPS_LAT_PELANGGAN) {
        GPS_LAT_PELANGGAN = gPS_LAT_PELANGGAN;
    }



    public String getGPS_LONG_PELANGGAN() {
        return GPS_LONG_PELANGGAN;
    }



    public void setGPS_LONG_PELANGGAN(String gPS_LONG_PELANGGAN) {
        GPS_LONG_PELANGGAN = gPS_LONG_PELANGGAN;
    }



    public String getKD_STAND_METER_LAMA() {
        return KD_STAND_METER_LAMA;
    }



    public void setKD_STAND_METER_LAMA(String kD_STAND_METER_LAMA) {
        KD_STAND_METER_LAMA = kD_STAND_METER_LAMA;
    }



    public String getKD_STAND_METER_BARU() {
        return KD_STAND_METER_BARU;
    }



    public void setKD_STAND_METER_BARU(String kD_STAND_METER_BARU) {
        KD_STAND_METER_BARU = kD_STAND_METER_BARU;
    }



    public String getMERK_METER_LAMA() {
        return MERK_METER_LAMA;
    }



    public void setMERK_METER_LAMA(String mERK_METER_LAMA) {
        MERK_METER_LAMA = mERK_METER_LAMA;
    }



    public String getMERK_METER_BARU() {
        return MERK_METER_BARU;
    }



    public void setMERK_METER_BARU(String mERK_METER_BARU) {
        MERK_METER_BARU = mERK_METER_BARU;
    }



    public String getNO_TERA_LAMA() {
        return NO_TERA_LAMA;
    }



    public void setNO_TERA_LAMA(String nO_TERA_LAMA) {
        NO_TERA_LAMA = nO_TERA_LAMA;
    }



    public String getNO_TERA_BARU() {
        return NO_TERA_BARU;
    }



    public void setNO_TERA_BARU(String nO_TERA_BARU) {
        NO_TERA_BARU = nO_TERA_BARU;
    }



    public String getANGKA_METER_LAMA() {
        return ANGKA_METER_LAMA;
    }



    public void setANGKA_METER_LAMA(String aNGKA_METER_LAMA) {
        ANGKA_METER_LAMA = aNGKA_METER_LAMA;
    }



    public String getANGKA_METER_BARU() {
        return ANGKA_METER_BARU;
    }



    public void setANGKA_METER_BARU(String aNGKA_METER_BARU) {
        ANGKA_METER_BARU = aNGKA_METER_BARU;
    }



    public String getGPS_LAT_PASANG() {
        return GPS_LAT_PASANG;
    }



    public void setGPS_LAT_PASANG(String gPS_LAT_PASANG) {
        GPS_LAT_PASANG = gPS_LAT_PASANG;
    }



    public String getGPS_LONG_PASANG() {
        return GPS_LONG_PASANG;
    }



    public void setGPS_LONG_PASANG(String gPS_LONG_PASANG) {
        GPS_LONG_PASANG = gPS_LONG_PASANG;
    }



    public String getFOTO_LAMA() {
        return FOTO_LAMA;
    }



    public void setFOTO_LAMA(String fOTO_LAMA) {
        FOTO_LAMA = fOTO_LAMA;
    }



    public String getFOTO_PASANG() {
        return FOTO_PASANG;
    }



    public void setFOTO_PASANG(String fOTO_PASANG) {
        FOTO_PASANG = fOTO_PASANG;
    }



    public String getDIAMETER() {
        return DIAMETER;
    }



    public void setDIAMETER(String dIAMETER) {
        DIAMETER = dIAMETER;
    }



    public String getKETERANGAN() {
        return KETERANGAN;
    }



    public void setKETERANGAN(String kETERANGAN) {
        KETERANGAN = kETERANGAN;
    }



    public String getTGL_PASANG() {
        return TGL_PASANG;
    }



    public void setTGL_PASANG(String tGL_PASANG) {
        TGL_PASANG = tGL_PASANG;
    }



    public String getS_PASANG() {
        return S_PASANG;
    }



    public void setS_PASANG(String s_PASANG) {
        S_PASANG = s_PASANG;
    }



    public String getUSER_ID() {
        return USER_ID;
    }



    public void setUSER_ID(String uSER_ID) {
        USER_ID = uSER_ID;
    }



    /* Retrieve All */
    @Override
    public List<TbGantiMeter> retrieveAll() {
        List<TbGantiMeter> w = new ArrayList<TbGantiMeter>();

        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getAllData(this);
        if(cursor.moveToFirst()){
            do{
                TbGantiMeter m = new TbGantiMeter(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(m, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                w.add(m);
            }while(cursor.moveToNext());
        }
        db.close();
        return w;
    }



    /* Retrieve by ID */
    @Override
    public TbGantiMeter retrieveByID() {
        DBHelper db = new DBHelper(context);
        Cursor cursor = db.getDataByPrimaryKey(this);
        if(cursor.getCount() > 0 ){
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
            return this;
        }
        db.close();
        return null;
    }



    /* Retrieve Baca Meter */
    public TbGantiMeter retrieveForMeter(String user, String kd_pel) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from tablegantimeter where USER_ID=? and KODE_PELANGGAN=?",
                new String[]{user, kd_pel}
        );
        if(cursor.getCount() > 0 ){
            if(cursor != null) cursor.moveToFirst();
            String[] columnNames = cursor.getColumnNames();
            for (String string : columnNames) {
                try {
                    System.out.println("test "+string+"="+cursor.getString(cursor.getColumnIndex(string)));
                    Field declaredField = this.getClass().getDeclaredField(string);
                    declaredField.set(this, cursor.getString(cursor.getColumnIndex(string)));
                } catch (NoSuchFieldException e) {
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }

            }
            return this;
        }
        db.close();
        return null;
    }



    /* Review */
    public List<TbGantiMeter> retrieveForReview(String user, String sTglMeter, boolean isBelum, boolean isSukses) {
        List<TbGantiMeter> w = new ArrayList<TbGantiMeter>();

        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        String sql = "select "+DBUtils.getCloumns(this)+" from tablegantimeter where USER_ID=?";
        if(isBelum) sql=sql+" and ( S_PASANG < 0 ) ORDER BY KODE_PELANGGAN ASC";
        else if(isSukses) sql=sql+" and ( TGL_PASANG LIKE '"+sTglMeter+"%' ) and ( S_PASANG between 0 and 4 ) ORDER BY KODE_PELANGGAN ASC";

        Cursor cursor = readableDatabase.rawQuery(sql,
                new String[]{user}
        );
        if(cursor.moveToFirst()){
            do{
                TbGantiMeter m = new TbGantiMeter(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(m, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                w.add(m);
            }while(cursor.moveToNext());
        }
        db.close();
        return w;
    }



    /* Export to Server */
    public List<TbGantiMeter> retrieveForExportServer(String user) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        List<TbGantiMeter> w = new ArrayList<TbGantiMeter>();
        String sql = "select "+DBUtils.getCloumns(this)+" from tablegantimeter where USER_ID=? and ( S_PASANG between 0 and 4 ) ORDER BY KODE_PELANGGAN ASC";

        Cursor cursor = readableDatabase.rawQuery(sql,
                new String[]{user}
        );
        if(cursor.moveToFirst()){
            do{
                TbGantiMeter m = new TbGantiMeter(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(m, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                w.add(m);
            }while(cursor.moveToNext());
        }
        db.close();
        return w;
    }



    /* Retrieve Export Local */
    public List<TbGantiMeter> retrieveForExport(String user, String sTglPasang) {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getReadableDatabase();
        List<TbGantiMeter> w = new ArrayList<TbGantiMeter>();
        String sql = "select "+ DBUtils.getCloumns(this)+" from tablegantimeter where USER_ID=? and ( TGL_PASANG LIKE '"+sTglPasang+"%' ) and ( S_PASANG between 0 and 4 ) ORDER BY KODE_PELANGGAN ASC";

        Cursor cursor = readableDatabase.rawQuery(sql,
                new String[]{user}
        );
        if(cursor.moveToFirst()){
            do{
                TbGantiMeter m = new TbGantiMeter(context);
                String[] columnNames = cursor.getColumnNames();
                for (String string : columnNames) {
                    try {
                        Field declaredField = this.getClass().getDeclaredField(string);
                        declaredField.set(m, cursor.getString(cursor.getColumnIndex(string)));
                    } catch (NoSuchFieldException e) {
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e) {
                    }
                }
                w.add(m);
            }while(cursor.moveToNext());
        }
        db.close();
        return w;
    }



    /* Hapus Data Belum di Baca */
    public List<TbGantiMeter> doClearDatabaseBacaMeterBelumdiBaca() {
        DBHelper db = new DBHelper(context);
        SQLiteDatabase readableDatabase = db.getWritableDatabase();
        List<TbGantiMeter> w = new ArrayList<>();
        String sql = "delete from tablegantimeter where S_PASANG < 0 ";

        /*Cursor cursor = */readableDatabase.execSQL(sql);/*(sql,
					new String[]{}
				);*/
		/*if(cursor.moveToFirst()){
			do{
				TSurvey m = new TSurvey(context);
				String[] columnNames = cursor.getColumnNames();
				for (String string : columnNames) {
					try {
						Field declaredField = this.getClass().getDeclaredField(string);
						declaredField.set(m, cursor.getString(cursor.getColumnIndex(string)));
					} catch (NoSuchFieldException e) {
					} catch (IllegalArgumentException e) {
					} catch (IllegalAccessException e) {
					}
				}
				w.add(m);
			}while(cursor.moveToNext());
		}*/
        db.close();
        return w;
    }
}
