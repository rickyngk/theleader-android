package com.theleader.app.activity.main.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.ElasticEase;
import com.theleader.app.R;

import java.text.DecimalFormat;

import R.helper.BaseFragment;
import R.helper.Common;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by duynk on 2/25/16.
 */
public class DashboardFragment extends BaseFragment {
    @Bind(R.id.linechart) com.db.chart.view.LineChartView chartView;
    @Bind(R.id.btn_more_detail) Button btnMoreDetail;

    private final String[] mLabels= {"JAN", "FEB", "MAR", "APR", "MAY", "JUN"};
    private final float[] mValues = {2.4f, 2.0f, 3.3f, 4.2f, 4.0f, 3.7f};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, rootView);

        LineSet dataSet = new LineSet(mLabels, mValues);
        dataSet.setColor(Color.parseColor("#004f7f"))
                .setThickness(Tools.fromDpToPx(1))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setSmooth(true);
        chartView.addData(dataSet);


        Paint thresPaint = new Paint();
        thresPaint.setColor(Color.parseColor("#0079ae"));
        thresPaint.setStyle(Paint.Style.STROKE);
        thresPaint.setAntiAlias(true);
        thresPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
        thresPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#33cccccc"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        chartView.setBorderSpacing(Tools.fromDpToPx(20))
                .setXLabels(AxisController.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#304a00"))
                .setYLabels(AxisController.LabelPosition.OUTSIDE)
                .setAxisThickness(Tools.fromDpToPx(0.75f))
                .setXAxis(true)
                .setYAxis(false)
                .setAxisBorderValues(0, 5, 1)
                .setGrid(ChartView.GridType.VERTICAL, 1, 6, gridPaint)
                .setValueThreshold(2.5f, 2.5f, thresPaint)
                .setAxisBorderValues(0, 5);


        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mValues.length; i++) {
                    Tooltip tip = new Tooltip(getContext(), R.layout.linechart_three_tooltip, R.id.value);
                    Rect r = chartView.getEntriesArea(0).get(i);
                    r.bottom -= r.height()/2;
                    tip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP)
                            .setHorizontalAlignment(Tooltip.Alignment.CENTER)
                            .setValueFormat(new DecimalFormat("0.0"))
                            .prepare(r, mValues[i]);
                    chartView.showTooltip(tip, true);
                }
            }
        };

        Animation anim = new Animation().setEasing(new ElasticEase()).setEndAction(chartAction);

        chartView.show(anim);


        //Button more detail
        btnMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.theleaderio_weburl)));
                startActivity(browserIntent);
            }
        });
        Common.setButtonColorRes(getContext(), btnMoreDetail, R.color.colorAccent, R.color.textColorLight);

        return rootView;
    }

}
