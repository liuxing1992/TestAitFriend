package com.example.admin.testaitfriend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.testaitfriend.ait.AitMember;

import java.util.ArrayList;
import java.util.List;

public class SelectPersonActiivty extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private List<AitMember> names;
    private myAdapter mAdapter;
    public static final int REQUEST_CODE = 0x10;
    public static final String RESULT_DATA = "data";
    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SelectPersonActiivty.class);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person_actiivty);
        mListView = findViewById(R.id.list);
        names = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            names.add(new AitMember(i+"" , "小明" + i));
        }
        mAdapter = new myAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent();

        intent.putExtra(RESULT_DATA, names.get(position) );

        setResult(RESULT_OK, intent);
        finish();
    }

    private class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return names.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(SelectPersonActiivty.this);
            textView.setTextSize(18);
            textView.setPadding(0, 20, 0, 20);
            textView.setText(names.get(position).getName());
            return textView;
        }
    }
}
