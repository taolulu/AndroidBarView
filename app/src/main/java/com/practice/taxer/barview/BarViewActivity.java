package com.practice.taxer.barview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.practice.barView.BarView;
import com.practice.barView.OverLayColor;

public class BarViewActivity extends AppCompatActivity {

    TextView percentText;
    SeekBar seekBar;
    BarView barView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_view_acitivity);
        percentText = (TextView) findViewById(R.id.percentText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        barView = (BarView) findViewById(R.id.bar_view);
        barView.resetPercent(40);
        OverLayColor overLayColor = new OverLayColor(Color.rgb(255, 127, 209),
                0, 15, 0, 0);
        barView.addOverlayColor(overLayColor);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentText.setText(Integer.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                barView.updatePercent(seekBar.getProgress());
            }
        });

//        barView.clearOverlayColor();
    }

}
