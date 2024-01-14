package com.politecnicomalaga.NasdaqOilPrices.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.politecnicomalaga.NasdaqOilPrices.Control.JornadaAdapter;
import com.politecnicomalaga.NasdaqOilPrices.Control.MainController;
import com.politecnicomalaga.NasdaqOilPrices.Model.Price;
import com.politecnicomalaga.NasdaqOilPrices.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private List<Price> mList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private JornadaAdapter mAdapter;

    private static MainActivity myActiveActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.rv_prices);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new JornadaAdapter(this, MainController.getSingleton().getList());
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button graph = (Button) findViewById(R.id.b_getData);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Double> precios = MainController.getSingleton().getPreciosRes();
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                intent.putExtra("precio", precios);
                startActivity(intent);
            }
        });
        MainController.getSingleton().requestDataFromNasdaq();
        MainActivity.myActiveActivity = this;
        MainController.setActivity(this);
    }

    public void accessData() {


        mAdapter.notifyDataSetChanged();
        TextView tv = (TextView) findViewById(R.id.tv_oilDesc);
        tv.setText("Nasdaq Oil Prices yesterday");
    }

    public void errorData(String error) {
        TextView tv = (TextView) findViewById(R.id.tv_oilDesc);
        tv.setText(error);

    }


}
