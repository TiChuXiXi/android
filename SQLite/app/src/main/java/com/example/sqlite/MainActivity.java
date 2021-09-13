package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    WordsDBHelper mWord;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mylist);
        registerForContextMenu(listView);

        mWord = new WordsDBHelper(this);

        ArrayList<Map<String, String>> items = getAllWords();
        Log.v("Activity1", items.toString());
        setWordsListAdapter(listView, items);
    }

    private ArrayList<Map<String, String>> getAllWords(){
        String sql = "select * from words";
        SQLiteDatabase db = mWord.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{});
        return getSearchedWords(cursor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                addWords();
                return true;
            case R.id.search:
                searchWords();
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
        TextView text_id, text_name, text_mean, text_sample;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View view = info.targetView;
        switch (item.getItemId()){
            case R.id.delete:
                text_id = view.findViewById(R.id.id123);
                if(text_id != null)
                    DeleteDialog(text_id.getText().toString());
                break;
            case R.id.change:
                text_id = view.findViewById(R.id.id123);
                text_name = view.findViewById(R.id.name);
                text_mean = view.findViewById(R.id.mean);
                text_sample = view.findViewById(R.id.sample);
                if(text_id != null && text_name != null && text_mean != null && text_sample != null)
                    UpdateDialog(text_id.getText().toString(), text_name.getText().toString(),
                            text_mean.getText().toString(), text_sample.getText().toString());
                break;
        }
        return true;
    }

    private void setWordsListAdapter(ListView listView, ArrayList<Map<String, String>> item){
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, item, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEAN, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.id123, R.id.name, R.id.mean, R.id.sample});
        listView.setAdapter(adapter);
    }

    private void addWords(){
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.insert, null);
        new AlertDialog.Builder(this).setTitle("增加单词").setView(linearLayout).
                setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String wordName = ((EditText)linearLayout.findViewById(R.id.word_name)).getText().toString();
                        String wordMean = ((EditText)linearLayout.findViewById(R.id.word_mean)).getText().toString();
                        String wordSample = ((EditText)linearLayout.findViewById(R.id.word_sample)).getText().toString();
                        insert(wordName, wordMean, wordSample);
                        setWordsListAdapter(listView, getAllWords());
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).create().show();
    }
    private void insert(String str1, String str2, String str3){
        String sql = "insert into words(word, meaning, sample) values(?,?,?)";
        SQLiteDatabase db = mWord.getWritableDatabase();
        db.execSQL(sql, new String[]{str1, str2, str3});
    }

    private void DeleteDialog(String wordId){
        new AlertDialog.Builder(this).setTitle("删除单词").setMessage("是否要删除单词？").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(wordId);
                        setWordsListAdapter(listView, getAllWords());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).create().show();
    }
    private void delete(String str){
        String sql = "delete from words where _id = ?";
        SQLiteDatabase db = mWord.getWritableDatabase();
        db.execSQL(sql, new String[]{str});
    }

    private void UpdateDialog(String wordId, String wordName, String wordMean, String wordSample){
        final LinearLayout linearLayout= (LinearLayout)getLayoutInflater().inflate(R.layout.insert, null);
        EditText edit_name = (EditText)linearLayout.findViewById(R.id.word_name);
        EditText edit_mean = (EditText)linearLayout.findViewById(R.id.word_mean);
        EditText edit_sample = (EditText)linearLayout.findViewById(R.id.word_sample);
        edit_name.setText(wordName);
        edit_mean.setText(wordMean);
        edit_sample.setText(wordSample);
        new AlertDialog.Builder(this).setTitle("更新数据").setView(linearLayout).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        update(wordId, edit_name.getText().toString(), edit_mean.getText().toString(), edit_sample.getText().toString());
                        setWordsListAdapter(listView, getAllWords());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).create().show();
    }
    private void update(String str1, String str2, String str3, String str4){
        String sql = "update words set word=?,meaning=?,sample=? where _id=?";
        SQLiteDatabase db = mWord.getWritableDatabase();
        db.execSQL(sql, new String[]{str2, str3, str4, str1});
    }

    private void searchWords(){
        final LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.search, null);
        new AlertDialog.Builder(this).setTitle("查找单词").setView(linearLayout).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String search_name = ((EditText)linearLayout.findViewById(R.id.search_word)).getText().toString();
                        Log.v("Activity1", search_name);
                        ArrayList<Map<String, String>> item = select(search_name);
                        Log.v("Activity1", item.toString());
                        if(item.size() > 0){
                            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                            intent.putExtra("searchedResult", select(search_name));
                            startActivity(intent);
                        }else
                            Toast.makeText(MainActivity.this, "没有找到符合条件的单词", Toast.LENGTH_LONG).show();
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).create().show();
    }
    private ArrayList<Map<String, String>> select(String str){
        String sql = "select * from words where word like ? order by word desc";
        SQLiteDatabase db = mWord.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+str+"%"});
        return getSearchedWords(cursor);
    }
    private ArrayList<Map<String, String>> getSearchedWords(Cursor cursor){
        ArrayList<Map<String, String>> list = new ArrayList<>();
        while(cursor.moveToNext()){
            Map<String, String> map = new HashMap<>();
            map.put(Words.Word._ID, cursor.getString(0));
            map.put(Words.Word.COLUMN_NAME_WORD, cursor.getString(1));
            map.put(Words.Word.COLUMN_NAME_MEAN, cursor.getString(2));
            map.put(Words.Word.COLUMN_NAME_SAMPLE, cursor.getString(3));
            list.add(map);
        }
        return list;
    }
}