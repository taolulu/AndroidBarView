package com.practice.taxer.barview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToListSample(View v){
        Intent intent = new Intent();
        intent.setClass(this, BarViewWithListViewActivity.class);
        startActivity(intent);
    }

    public void goToSample(View v){
        Intent intent = new Intent();
        intent.setClass(this, BarViewActivity.class);
        startActivity(intent);
    }


}
