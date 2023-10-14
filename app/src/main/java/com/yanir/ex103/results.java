package com.yanir.ex103;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Yanir Aton
 * @version 1.0
 * @since 2023-09-10
 * This class is used to display the results of the calculation
 */
public class results extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView seriesListView;

    double resultA1;
    double resultDQ;
    int currntN;
    double sumToN;
    int mode;

    LinearLayout informationLayout;

    TextView X1,textDifferenceMultiplier,whereN,sumN;

    Double[] seriesNs = new Double[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // connect the views to the xml widgets
        informationLayout = findViewById(R.id.informationLayout);
        X1 = findViewById(R.id.x1);
        textDifferenceMultiplier = findViewById(R.id.textDifferenceMultiplier);
        whereN = findViewById(R.id.whereN);
        sumN = findViewById(R.id.sumN);

        // reset the variables and set the layout weight to 0
        sumToN = 0;
        changeLayoutWeight(informationLayout,0);

        // get the intent and the data from the intent
        Intent gi = getIntent();
        mode = gi.getIntExtra("mode", -1);
        resultA1 = gi.getIntExtra("firstIntense",0);
        resultDQ = gi.getIntExtra("differenceMultiplier",0);

        // setup the ListView
        seriesListView = (ListView) findViewById(R.id.Lv_series);
        seriesListView.setOnItemClickListener(this);
        seriesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        fillSeriesNs(seriesNs,mode); // fill the seriesNs array with values to display in the ListView
        ArrayAdapter<Double> adp = new ArrayAdapter<Double>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,seriesNs);
        seriesListView.setAdapter(adp);
    }


    /**
     * calculate the n's value of an arithmetic series
     * @param a1 the first value of the series
     * @param d the common difference of the series
     * @param n the n's value to calculate
     * @return the n's value of an arithmetic series'
     */
    public static double ArithmeticSeriesGetNthTerm(double a1, double d, int n) {
        return a1 + (n - 1) * d;
    }

    /**
     * calculate the n's value of a geometric series
     * @param a1 the first value of the series
     * @param q the common ratio of the series
     * @param n the n's value to calculate
     * @return the n's value of a geometric series'
     */
    public static double GeometricSeriesGetNthTerm(double a1, double q, int n) {
        return a1 * Math.pow(q, n - 1);
    }

    /**
     * fill the seriesNs array with values to display in the ListView
     * @param fillList the seriesNs array to fill
     * @param mode the type of series to calculate(0 = arithmetic series, 1 = geometric series)
     */
    public void fillSeriesNs(Double[] fillList,int mode){
        if (mode == 0) {
            for (int i = 0; i < fillList.length; i++) {
                fillList[i] = ArithmeticSeriesGetNthTerm(resultA1,resultDQ,i+1);
            }
        }
        else if(mode == 1){
            for (int i = 0; i < fillList.length; i++) {
                fillList[i] = GeometricSeriesGetNthTerm(resultA1,resultDQ,i+1);
            }
        }
        else{
            for (int i = 0; i < fillList.length; i++) {
                fillList[i] = 0.0;
            }
        }
    }

    /**
     * the onClick method for the ListView, it will show information about the result of the calculation
     * @param parent The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     *            will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        changeLayoutWeight(informationLayout,5);
        X1.setText("X1 = " + resultA1);
        if(mode == 0){
            textDifferenceMultiplier.setText("common difference = " + resultDQ);
        }
        else if (mode == 1){
            textDifferenceMultiplier.setText("common ratio = " + resultDQ);
        }
        else{
            textDifferenceMultiplier.setText("error");
        }
        whereN.setText("n = " + (position+1));

        sumToN = 0;
        for (int i = 0; i < position+1; i++) {
            sumToN += seriesNs[i];
        }
        sumN.setText("Sn = " + sumToN);

    }

    /**
     * Change the weight of a given LinearLayout to a given weight
     * @param layout the LinearLayout to change
     * @param weight the weight to change the LinearLayout to
     */
    public void changeLayoutWeight(LinearLayout layout, int weight){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        params.weight = weight;
        layout.setLayoutParams(params);
    }
}