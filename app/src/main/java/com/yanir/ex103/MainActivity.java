package com.yanir.ex103;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Yanir Aton
 * @version 1.0
 * @since 2023-10-14
 * This class is responsible for the Main screen
 */
public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder adb;
    private LinearLayout mydialog;

    int mode = -1;
    String[] modes = {"Arithmetic Series","Geometric series"};
    String[] dOrQText = {"common difference","common ratio"};

    String SfirstIntense,SdifferenceMultiplier;
    Button seriesType;
    EditText firstIntense,differenceMultiplier;
    TextView dOrQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
     * the onclick function of the done button in the dialog box
     * it will go to the results activity with the information from the user input
     */
    public void goToResults(){
        // check the the input fields are not empty
        if (firstIntense.getText().toString().equals("") || differenceMultiplier.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please fill in all the fields",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mode == -1){
            Toast.makeText(getApplicationContext(),"Choose Series Type!!",Toast.LENGTH_SHORT).show();
            return;
        }else{
            // create the intent
            Intent res = new Intent(this,results.class);

            // add the information to the intent
            res.putExtra("mode",mode);
            res.putExtra("firstIntense",Integer.parseInt(String.valueOf(firstIntense.getText())));
            res.putExtra("differenceMultiplier",Integer.parseInt(String.valueOf(differenceMultiplier.getText())));

            resetInfo();
            // start the activity
            startActivity(res);
        }
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
                goToResults();
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
     * This method satrt the credit
     * @param v
     */
    public void goToCredits(View v){
        Intent credits = new Intent(this, credits.class);
        startActivity(credits);
    }


}