package com.politecnicomalaga.NasdaqOilPrices.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.politecnicomalaga.NasdaqOilPrices.R;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private ArrayList<Double> precios = new ArrayList<>();
    private ArrayList<Double> preciosOrdenados = new ArrayList<>();
    private DataPoint[] puntos = new DataPoint[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Button mainActivity = (Button) findViewById(R.id.button);
        this.precios = (ArrayList<Double>) getIntent().getSerializableExtra("precio");
        this.ordenarPrecios();
        GraphView graph = (GraphView) findViewById(R.id.graph);
        for (int i = 0; i < puntos.length; i++) {
            this.puntos[i] = new DataPoint(i, preciosOrdenados.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(puntos);
        graph.addSeries(series);
        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GraphActivity.this, MainActivity.class));
            }
        });
    }
    private void ordenarPrecios() {
        for (int i = 0; i < precios.size(); i++) {
            double max = precios.get(i);
            int pos = i;
            for (int j = 0; j < precios.size(); j++) {
                if (max <= precios.get(j)) {
                    max = precios.get(j);
                    pos = j;
                }
            }
            preciosOrdenados.add(max);
            precios.remove(pos);
        }
    }
}
