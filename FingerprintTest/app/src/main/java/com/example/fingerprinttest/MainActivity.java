package com.example.fingerprinttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Map<String, String>> accountList;
    AccountDBHelper accountDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        registerForContextMenu(listView);
        accountDBHelper = new AccountDBHelper(this);
        accountList = getAll();
        setAdapter(listView, accountList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                addAccount();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Map<String, String> map = getAll().get(info.position);
        switch (item.getItemId()){
            case R.id.delete:
                deleteAccount(map.get(Account._ID));
                break;
            case R.id.change:
                String ac_id = map.get(Account._ID);
                String ac_name = map.get(Account.NAME);
                String ac_pass = map.get(Account.PASSWORD);
                String ac_desc = map.get(Account.DESCRIPTION);
                changeAccount(ac_id, ac_desc, ac_name, ac_pass);
                break;
        }
        return true;
    }

    public void setAdapter(ListView listView, ArrayList<Map<String, String>> item){
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, item, R.layout.items,
                new String[]{Account.DESCRIPTION, Account._ID}, new int[]{R.id.list_desc, R.id.list_id});
        listView.setAdapter(simpleAdapter);
    }

    public ArrayList<Map<String, String>> getAll(){
        String sql = "select * from " + Account.TABLE_NAME;
        SQLiteDatabase db = accountDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{});
        ArrayList<Map<String, String>> list = new ArrayList<>();
        while(cursor.moveToNext()){
            Map<String, String> map = new HashMap<>();
            map.put(Account._ID, cursor.getString(0));
            map.put(Account.DESCRIPTION, cursor.getString(1));
            map.put(Account.NAME, cursor.getString(2));
            map.put(Account.PASSWORD, cursor.getString(3));
            list.add(map);
        }
        return list;
    }

    public void addAccount(){
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this).setTitle("新增").setView(linearLayout).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String desc = ((EditText)linearLayout.findViewById(R.id.account_desc)).getText().toString();
                        String name = ((EditText)linearLayout.findViewById(R.id.account_name)).getText().toString();
                        String password = ((EditText)linearLayout.findViewById(R.id.account_password)).getText().toString();
                        insert(desc, name, password);
                        setAdapter(listView, getAll());
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
    }
    public void insert(String arg1, String arg2, String arg3){
        String sql = "insert into "+Account.TABLE_NAME+" ("+Account.DESCRIPTION+","+Account.NAME+","
                +Account.PASSWORD+") values(?,?,?)";
        SQLiteDatabase db = accountDBHelper.getWritableDatabase();
        db.execSQL(sql, new String[]{arg1, arg2, arg3});
    }
    public void deleteAccount(String id){
        new AlertDialog.Builder(this).setTitle("新增").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(id);
                        setAdapter(listView, getAll());
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create().show();
    }
    public void delete(String id){
        String sql = "delete from "+Account.TABLE_NAME+" where _id=?";
        SQLiteDatabase db = accountDBHelper.getWritableDatabase();
        db.execSQL(sql, new String[]{id});
    }
    public void changeAccount(String arg1, String arg2, String arg3, String arg4){
        final LinearLayout linearLayout= (LinearLayout)getLayoutInflater().inflate(R.layout.insert, null);
        EditText edit_desc = (EditText)linearLayout.findViewById(R.id.account_desc);
        EditText edit_name = (EditText)linearLayout.findViewById(R.id.account_name);
        EditText edit_password = (EditText)linearLayout.findViewById(R.id.account_password);
        edit_desc.setText(arg2);
        edit_name.setText(arg3);
        edit_password.setText(arg4);
        new AlertDialog.Builder(this).setTitle("更新数据").setView(linearLayout).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        update(arg1, arg2, arg3, arg4);
                        setAdapter(listView, getAll());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).create().show();
    }
    public void update(String arg1, String arg2, String arg3, String arg4){
        String sql = "update "+Account.TABLE_NAME+" set "+Account.DESCRIPTION+"=?,"
                +Account.NAME+"=?,"+Account.PASSWORD+"=? where "+Account._ID+"=?";
        SQLiteDatabase db = accountDBHelper.getWritableDatabase();
        db.execSQL(sql, new String[]{arg2, arg3, arg4, arg1});
    }
}