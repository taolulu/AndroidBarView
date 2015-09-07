package com.practice.taxer.barview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.practice.barView.BarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarViewWithListViewActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_view_with_list_view);
        listView = (ListView) findViewById(R.id.listView);
        Integer[] percentData = {50, 30,10, 60,40, 90, 50, 30, 60, 90, 50, 30,10, 60,40, 90, 50, 30, 60, 90, 50, 30,10, 60,40, 90, 50, 30, 60, 90};
        MyAdapter myAdapter = new MyAdapter(Arrays.asList(percentData));
        listView.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter {

        List<Integer> percents;
        List<Integer> performed = new ArrayList<>();

        public MyAdapter(List<Integer> percents) {
            this.percents = percents;
        }

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return percents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView)
            {
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(BarViewWithListViewActivity.this);
                convertView = mInflater.inflate(R.layout.list_item, null);

                viewHolder.barView = (BarView) convertView
                        .findViewById(R.id.pb_vote_item);

                viewHolder.percent = (TextView) convertView.findViewById(R.id.percent);

                convertView.setTag(viewHolder);

            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            if(performed.contains(new Integer(position))){
                viewHolder.barView.resetPercent(percents.get(position));
            }else {
                viewHolder.barView.updatePercent(percents.get(position));
                performed.add(new Integer(position));
            }

            viewHolder.percent.setText(Integer.toString(percents.get(position)));
            if (position % 2 == 0) {
                viewHolder.barView.setProgressColor(getResources().getColor(R.color.default_progress_color));
            } else {
                viewHolder.barView.setProgressColor(getResources().getColor(R.color.default_background_color));
            }

            return convertView;
        }
    }

    class ViewHolder {
        BarView barView;
        TextView percent;
    }
}
