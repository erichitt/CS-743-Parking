/*******************************************************************************
 * File Name:  MonitorParkingSpotStatusActivity.java
 *
 * Description:
 * Handles the monitor parking spot status screen activity.
 *
 * Revision  Date        Author             Summary of Changes Made
 * --------  ----------- ------------------ ------------------------------------
 * 1         13-Nov-2015 Eric Hitt          Original
 ******************************************************************************/
package com.cs743.uwmparking;

/****************************    Include Files    *****************************/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

/****************************  Class Definitions  *****************************/

/**
 * Create a new monitor parking spot status screen activity class
 */
public class MonitorParkingSpotStatusActivity extends AppCompatActivity
{
    /*************************  Class Static Variables  ***********************/

    /*************************  Class Member Variables  ***********************/

    private ListView parkingSpotStatusList_;        ///< List of parking spots

    private SelectedParkingLot selectedLot_;        ///< Selected parking lot

    /*************************  Class Public Interface  ***********************/

    /**
     * Creates the monitor parking spot status page.
     *
     * @param savedInstanceState Saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_parking_spot_status);

        // Retrieve screen inputs
        parkingSpotStatusList_ = (ListView)findViewById(R.id.parkingSpotStatusList);

        // Retrieve intent
        Intent intent = getIntent();
        selectedLot_ =
                (SelectedParkingLot)intent.getSerializableExtra(RecommendParkingActivity.PREFERENCES_INTENT_DATA);

        // TODO:  Populate list view, get parking lot status (poll timer?)
    }

    /************************  Class Private Interface  ***********************/
}
