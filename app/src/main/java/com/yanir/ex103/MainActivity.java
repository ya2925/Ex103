package com.yanir.ex103;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Yanir Aton
 * @version 1.0
 * @since 2023-11-23
 * This class is used to get the data and display the results of the calculation
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private AlertDialog.Builder adb;
    private LinearLayout mydialog;

    int mode = -1;
    String[] modes = {"Arithmetic Series","Geometric series"};
    String[] dOrQText = {"common difference","common ratio"};

    String SfirstIntense,SdifferenceMultiplier;
    Button seriesType;
    EditText firstIntense,differenceMultiplier;
    TextView dOrQ;
    ListView seriesListView;

    double resultA1;
    double resultDQ;
    int currntN;
    double sumToN;
    TextView X1,textDifferenceMultiplier,whereN,sumN;

    Double[] seriesNs = new Double[20];
    Intent si;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect the views to the xml widgets
        X1 = findViewById(R.id.x1);
        textDifferenceMultiplier = findViewById(R.id.textDifferenceMultiplier);
        whereN = findViewById(R.id.whereN);
        sumN = findViewById(R.id.sumN);

        // reset the variables and set the layout weight to 0
        sumToN = 0;
    }
    public void showResults() {

        // check the the input fields are not empty
        if (firstIntense.getText().toString().equals("") || differenceMultiplier.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please fill in all the fields",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isDoubleNum(firstIntense.getText().toString()) || !isDoubleNum(differenceMultiplier.getText().toString())){
            Toast.makeText(getApplicationContext(),"Please make sure the numbers you entered are a valid numbers",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mode == -1){
            Toast.makeText(getApplicationContext(),"Choose Series Type!!",Toast.LENGTH_SHORT).show();
            return;
        }else{
            resultA1 = Double.parseDouble(String.valueOf(firstIntense.getText()));
            resultDQ = Double.parseDouble(String.valueOf(differenceMultiplier.getText()));
            // setup the ListView
            seriesListView = (ListView) findViewById(R.id.Lv_series);
            seriesListView.setOnItemClickListener(this);
            seriesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            fillSeriesNs(seriesNs,mode); // fill the seriesNs array with values to display in the ListView
            ArrayAdapter<Double> adp = new ArrayAdapter<Double>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,seriesNs);
            seriesListView.setAdapter(adp);
        }
    }


    public void start(View view) {
        mydialog=(LinearLayout) getLayoutInflater().inflate(R.layout.my_diamlog,null);

        // connect the views to the xml widgets
        seriesType = mydialog.findViewById(R.id.seriesType);
        firstIntense = mydialog.findViewById(R.id.firstIntense);
        differenceMultiplier = mydialog.findViewById(R.id.differenceMultiplier);
        dOrQ = mydialog.findViewById(R.id.dOrQ);

        adb=new AlertDialog.Builder(this);

        adb.setView(mydialog);
        adb.setTitle("Identification");
        adb.setMessage("Please enter your name & password:");
        adb.setPositiveButton("Done",myclick);
        adb.setNegativeButton("Cancel",myclick);
        adb.setNeutralButton("reset",myclick);
        lodeInfo();
        adb.show();
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
        X1.setText("X1 = " + resultA1);
        if(mode == 0){
            textDifferenceMultiplier.setText("common difference = " + resultDQ);
        }
        else if (mode == 1){
            textDifferenceMultiplier.setText("common ratio = " + resultDQ);
        }
        else{
            textDifferenceMultiplier.setText(mode + "error");
        }
        whereN.setText("n = " + (position+1));

        sumToN = 0;
        for (int i = 0; i < position+1; i++) {
            sumToN += seriesNs[i];
        }
        sumN.setText("Sn = " + sumToN);
    }


    /**
     * the onclick function of the change mode - change from Arithmetic Series to Invoice series or Invoice series to Arithmetic Series
     *
     * @param v the button that was clicked
     */
    public void changeMode(View v){
        saveInfo();
        if (mode == -1 || mode == 1){
            mode = 0;
        }
        else{
            mode = 1;
        }
        lodeInfo();
    }

    /**
     * this function lode information to the dialog box
     */
    public void lodeInfo(){
        firstIntense.setText(SfirstIntense);
        differenceMultiplier.setText(SdifferenceMultiplier);
        if (mode == -1){return;};
        dOrQ.setText(dOrQText[mode]);
        seriesType.setText(modes[mode]);
    }

    /**
     * this function save the numbers that the user has entered to be loaded when needed
     */
    public void saveInfo(){
        SfirstIntense = String.valueOf(firstIntense.getText());
        SdifferenceMultiplier = String.valueOf(differenceMultiplier.getText());
    }

    /**
     * this function resets the alert dialog variables and used with the restart button
     */
    public void resetInfo(){
        mode = -1;
        SfirstIntense = "";
        SdifferenceMultiplier = "";
    }

    /**
     * onClick method of the dialog
     *
     * @param dialog the DialogInterface object that triggered the method
     * @param which the identifier that clicked
     */
    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                showResults();
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                saveInfo();
                dialog.cancel();
            }
            if (which == DialogInterface.BUTTON_NEUTRAL){
                resetInfo();
                dialog.cancel();
            }
        }
    };

    /**
     * this method chack if a given string is a double number
     * @param str the string to check
     * @return true if the string is a double number, otherwise false
     */
    public static boolean isDoubleNum(String str){
        if (str == null || str.equals("") || str.isEmpty()){
            return false;
        }
        boolean hasDecimalPoint = false;
        boolean hasDigit = false;
        for(int i = 0; i < str.length(); i++){
            char currentChar = str.charAt(i);
            if(i==0 && (currentChar == '-' || currentChar == '+')){
                continue;
            } else if (currentChar == '.') {
                if (hasDecimalPoint) {
                    return false;
                } else {
                    hasDecimalPoint = true;
                }
            } else if (Character.isDigit(currentChar)) {
                hasDigit = true;
            } else {
                return false;
            }
        }
        return hasDigit;
    }


    /**
     * This function presents the options menu for moving between activities.
     * @param menu The options menu in which you place your items.
     * @return true in order to show the menu, otherwise false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.manu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        System.out.println(item.getTitle().toString());
        if (item.getTitle().toString().equals("credit")){
            si = new Intent(this, credits.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }
}