package com.project.fortuna.gantimeterpdam.DAO;

import android.content.Context;
import android.database.SQLException;

import java.util.List;

public abstract class BaseDAO {
    protected Context context;
    protected DBHelper db;

    public BaseDAO(Context context) {
        this.context = context;
    }

    public void save(){
        db = new DBHelper(context);
        db.save(this);
    }

    public void update(){
        db = new DBHelper(context);
        db.update(this);
    }

    public void delete(){
        db = new DBHelper(context);
        db.delete(this);
    }

    public void open() throws SQLException {
        db = new DBHelper(context);
    }

    public void close() {
        db.close();
    }


    public abstract List<?> retrieveAll();

    public abstract Object retrieveByID();
}
