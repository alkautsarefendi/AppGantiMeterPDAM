package com.project.fortuna.gantimeterpdam.spkp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.R;

import java.util.List;

public class ImportSPKPAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TbGantiMeter> listPemasangan;
    public static final String PHOTO_PATH_LAMA = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Foto/Foto Lama/";
    public static final String PHOTO_PATH_PASANG = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Foto/Foto Pasang/";
    public static final String ICON_DEFAULT = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Icon/";

    public ImportSPKPAdapter(Activity activity, List<TbGantiMeter> listPemasangan) {
        this.activity = activity;
        this.listPemasangan = listPemasangan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v("Test", "data ke "+position);

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_import_spkp, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
        thumbnail.setOnClickListener(new ThumbnailExportClickHandler());

        // getting movie data for the row
        TbGantiMeter b = listPemasangan.get(position);
        title.setText(b.getNAMA_PELANGGAN());
        rating.setText("Kode Pel : "+b.getKODE_PELANGGAN().replace("'", ""));
        genre.setText(b.getALAMAT_PELANGGAN());

        if(b.FOTO_LAMA == null && b.FOTO_PASANG == null|| b.FOTO_LAMA.trim().equals("") && b.FOTO_PASANG.trim().equals("") ){
            String defIcon = "tidak_ada_foto_meter.png";

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(ICON_DEFAULT+defIcon, options );
            thumbnail.setImageBitmap(bitmap);
            Bitmap bitmap2 = BitmapFactory.decodeFile(ICON_DEFAULT+defIcon, options );
            thumbnail.setImageBitmap(bitmap2);
        } else {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(PHOTO_PATH_LAMA+b.FOTO_LAMA, options );
            thumbnail.setImageBitmap(bitmap);
            Bitmap bitmap2 = BitmapFactory.decodeFile(PHOTO_PATH_PASANG+b.FOTO_PASANG, options );
            thumbnail.setImageBitmap(bitmap2);
        }

        return convertView;
    }


    @Override
    public int getCount() {
        return listPemasangan.size();
    }


    @Override
    public Object getItem(int position) {
        return listPemasangan.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ThumbnailExportClickHandler implements android.view.View.OnClickListener {

        @Override
        public void onClick(View v) {

        }

    }
}
