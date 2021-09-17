package com.example.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    RightFragment rightFragment;
    boolean island = false;
    ContentResolver resolver;

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            island = true;
            setContentView(R.layout.land_main);
            init();
//            Toast.makeText(this, "land", Toast.LENGTH_SHORT).show();
        }else{
            island = false;
            setContentView(R.layout.activity_main);
            init();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        listView = findViewById(R.id.mylist);
        registerForContextMenu(listView);

        resolver = this.getContentResolver();

        ArrayList<Map<String, String>> items = getAllWords();
//        Log.v("Activity1", items.toString());
        setWordsListAdapter(listView, items);

        loadRightFragment();
        Toast.makeText(this, "横屏点击单词可查看详情", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "长按单词可删除或修改", Toast.LENGTH_SHORT).show();
    }

    public void loadRightFragment(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        rightFragment = new RightFragment();
        transaction.replace(R.id.sample_show, rightFragment).commit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, String> map = getAllWords().get(i);

                rightFragment.getTextView(new RightFragment.CallBack() {
                    @Override
                    public void setSample(TextView textView) {
                        textView.setText(map.get(Words.Word.COLUMN_NAME_SAMPLE));
                    }
                });
            }
        });
    }

    private ArrayList<Map<String, String>> getAllWords(){
//        String sql = "select * from words";
//        SQLiteDatabase db = mWord.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, new String[]{});
        Cursor cursor = resolver.query(Words.Word.uri, null, null, null, null);
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
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        Map<String, String> map = getAllWords().get(index);
        switch (item.getItemId()){
            case R.id.delete:
                DeleteDialog(map.get(Words.Word._ID));
                break;
            case R.id.change:
                String up_id = map.get(Words.Word._ID);
                String up_name = map.get(Words.Word.COLUMN_NAME_WORD);
                String up_mean = map.get(Words.Word.COLUMN_NAME_MEAN);
                String up_sample = map.get(Words.Word.COLUMN_NAME_SAMPLE);
                UpdateDialog(up_id, up_name, up_mean, up_sample);
                break;
        }
        return true;
    }

    private void setWordsListAdapter(ListView listView, ArrayList<Map<String, String>> item){
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, item, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEAN, Words.Word.COLUMN_NAME_SAMPLE},
                new int[]{R.id.id123, R.id.name, R.id.mean, R.id.item_sample});
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
//        String sql = "insert into words(word, meaning, sample) values(?,?,?)";
//        SQLiteDatabase db = mWord.getWritableDatabase();
//        db.execSQL(sql, new String[]{str1, str2, str3});
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, str1);
        values.put(Words.Word.COLUMN_NAME_MEAN, str2);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, str3);
        resolver.insert(Words.Word.uri, values);
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
//        String sql = "delete from words where _id = ?";
//        SQLiteDatabase db = mWord.getWritableDatabase();
//        db.execSQL(sql, new String[]{str});
        String select = "_id=?";
        resolver.delete(Words.Word.uri, select, new String[]{str});
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
//        String sql = "update words set word=?,meaning=?,sample=? where _id=?";
//        SQLiteDatabase db = mWord.getWritableDatabase();
//        db.execSQL(sql, new String[]{str2, str3, str4, str1});
        ContentValues values = new ContentValues();
        values.put(Words.Word.COLUMN_NAME_WORD, str2);
        values.put(Words.Word.COLUMN_NAME_MEAN, str3);
        values.put(Words.Word.COLUMN_NAME_SAMPLE, str4);
        String select = "_id=?";
        resolver.update(Words.Word.uri, values, select, new String[]{str1});
    }

    private void searchWords(){
        final LinearLayout linearLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.search, null);
        new AlertDialog.Builder(this).setTitle("查找单词").setView(linearLayout).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String search_name = ((EditText)linearLayout.findViewById(R.id.search_word)).getText().toString();
//                        Log.v("Activity1", search_name);
                        ArrayList<Map<String, String>> item = select(search_name);
//                        Log.v("Activity1", item.toString());
                        if(item.size() > 0){
                            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                            intent.putExtra("searchedResult", select(search_name));
                            intent.putExtra("layout", island);
                            startActivity(intent);
//                            setWordsListAdapter(listView, select(search_name));
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
//        String sql = "select * from words where word like ? order by word desc";
//        SQLiteDatabase db = mWord.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, new String[]{"%"+str+"%"});
//        return getSearchedWords(cursor);
        String select = "word like ?";
        String order = "word desc";
        Log.v("MainActivity", str);
        Cursor cursor = resolver.query(Words.Word.uri, null, select, new String[]{"%"+str+"%"}, null);
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