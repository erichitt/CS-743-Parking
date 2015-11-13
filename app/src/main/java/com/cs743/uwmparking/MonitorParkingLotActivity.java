/*******************************************************************************
 * File Name:  MonitorParkingActivity.java
 *
 * Description:
 * Handles the monitor parking lot screen activity.
 *
 * Revision  Date        Author             Summary of Changes Made
 * --------  ----------- ------------------ ------------------------------------
 * 1         13-Nov-2015 Eric Hitt          Original
 ******************************************************************************/
package com.cs743.uwmparking;

/****************************    Include Files    *****************************/
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/****************************  Class Definitions  *****************************/

/**
 * Create a new monitor parking lot screen activity class
 */
public class MonitorParkingLotActivity extends AppCompatActivity
{
    /*************************  Class Static Variables  ***********************/

    /*************************  Class Member Variables  ***********************/

    /*************************  Class Public Interface  ***********************/

    /**
     * Creates the monitor parking lot page.
     *
     * @param savedInstanceState Saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_parking_lot);
    }

    /************************  Class Private Interface  ***********************/
}
