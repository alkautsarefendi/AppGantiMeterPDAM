package com.project.fortuna.gantimeterpdam.DAO;

import android.content.Context;

import java.util.List;

public class DefaultDTO extends BaseDAO{

    public String USERID;
    public String KD_WILAYAH;
    public String PERIODE;

    public DefaultDTO(Context context) {
        super(context);
    }

    @Override
    public List<?> retrieveAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object retrieveByID() {
        // TODO Auto-generated method stub
        return null;
    }
}
