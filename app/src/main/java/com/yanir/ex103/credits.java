package com.yanir.ex103;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

/**
 * @author Yanir Aton
 * @version 1.0
 * @since 2023-10-05
 * This class is responsible for the credit screen
 */
public class credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    /**
     * this method will be called when the back button is clicked and it will finish the activity
     * @param view the view
     */
    public void finishActivity(View view) {
        finish();
    }
}