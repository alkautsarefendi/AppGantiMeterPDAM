package com.project.fortuna.gantimeterpdam.review;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.fortuna.gantimeterpdam.DAO.TbGantiMeter;
import com.project.fortuna.gantimeterpdam.R;

import java.util.List;

public class LihatDataPelangganAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TbGantiMeter> listPemasangan;

    public static final String PHOTO_PATH_LAMA = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Foto/Foto Lama/";
    public static final String PHOTO_PATH_PASANG = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Foto/Foto Pasang/";
    public static final String ICON_DEFAULT = Environment.getExternalStorageDirectory().toString() + "/GantiMeter/Icon/";


    public LihatDataPelangganAdapter(Activity activity, List<TbGantiMeter> listMeter) {
        this.activity = activity;
        this.listPemasangan = listMeter;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_lihat_data_pelanggan_adapter, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        ImageView thumbail = (ImageView) convertView.findViewById(R.id.thumbnail);
        thumbail.setOnClickListener(new ThumbnailClickHandler());

        TbGantiMeter m = listPemasangan.get(position);
        title.setText(m.getNAMA_PELANGGAN());
        rating.setText("Kode Pel : "+m.getKODE_PELANGGAN().replace("'", ""));
        genre.setText(m.getALAMAT_PELANGGAN());

        if(m.FOTO_LAMA == null && m.FOTO_PASANG == null || m.FOTO_LAMA.trim().equals("") && m.FOTO_PASANG.trim().equals("") ){
            String defIcon = "tidak_ada_foto_meter.png";

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(ICON_DEFAULT+defIcon, options );
            thumbail.setImageBitmap(bitmap);
        } else {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(PHOTO_PATH_LAMA+m.FOTO_LAMA, options );
            thumbail.setImageBitmap(bitmap);
            Bitmap bitmap2 = BitmapFactory.decodeFile(PHOTO_PATH_PASANG+m.FOTO_PASANG, options );
            thumbail.setImageBitmap(bitmap2);
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


    public class ThumbnailClickHandler implements android.view.View.OnClickListener {

        @Override
        public void onClick(View v) {

        }

    }
}
