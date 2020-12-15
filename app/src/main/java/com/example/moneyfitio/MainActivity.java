package com.example.moneyfitio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static String X_LABEL = "xLabels", Y_LABEL_INVEST = "yLabel_invest", Y_LABEL_CURRENT = "yLabel_current";

    private List<Model> modelList;
    private ArrayList<String> xLabels;
    private ArrayList<Integer> investAxis, currentAxis;
    private ProgressBar loader;
    private ConstraintLayout loaderLayout;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=#2B2B2B>" + getString(R.string.app_name) + "</font>"));

        modelList = new ArrayList<>();
        xLabels = new ArrayList<>();
        investAxis = new ArrayList<>();
        currentAxis = new ArrayList<>();

        loader = findViewById(R.id.loader);
        Button getCurve = findViewById(R.id.get_curve);
        loaderLayout = findViewById(R.id.loader_layout);
        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        getData();

        getCurve.setOnClickListener(v -> {
            if(xLabels.size() != 0 && investAxis.size() != 0) {
                Intent intent = new Intent(getApplicationContext(), ShowGraphActivity.class);
                intent.putStringArrayListExtra(X_LABEL, xLabels);
                intent.putIntegerArrayListExtra(Y_LABEL_INVEST, investAxis);
                intent.putIntegerArrayListExtra(Y_LABEL_CURRENT, currentAxis);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Data not fetched. Try to refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        loaderLayout.setVisibility(View.VISIBLE);
        loader.setVisibility(View.VISIBLE);
        loader.animate();
        xLabels.clear();
        investAxis.clear();
        modelList.clear();
        currentAxis.clear();

        Call<ModelList> call = RetrofitClient.getInstance().getApi().getData();

        call.enqueue(new Callback<ModelList>() {
            @Override
            public void onResponse(@NonNull Call<ModelList> call, @NonNull Response<ModelList> response) {
                ModelList list = response.body();
                modelList = list.getModelList();
                for (Model model : modelList) {
                    xLabels.add(model.getDate());
                    investAxis.add(model.getInvest_value());
                    currentAxis.add(model.getCurrent_value());
                }
                loaderLayout.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                loader.clearAnimation();
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelList> call,@NonNull Throwable t) {
                loaderLayout.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                loader.clearAnimation();
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }


}

