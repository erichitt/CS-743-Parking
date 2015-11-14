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
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/****************************  Class Definitions  *****************************/

/**
 * Create a new monitor parking lot screen activity class
 */
public class MonitorParkingLotActivity extends AppCompatActivity
{
    /*************************  Class Static Variables  ***********************/

    public static final int PARKING_LOT_OPACITY = 100;  ///< Opacity for overlays

    /// Polling timer interval (milliseconds)
    public static final int POLL_TIMER_INTERVAL_MSEC = 1000;

    /*************************  Class Member Variables  ***********************/

    private TextView spaceRemainingLabel_;      ///< Space remaining label
    private TextView spaceRemainingCount_;      ///< Displays number of spots
    private ImageView monitorParkingMap_;       ///< Displays map of parking lot
    private View unavailableOverlay_;           ///< Unavailable parking spot overlay
    private View availableOverlay_;             ///< Available parking spot overlay
    private Button parkingSpotStatusButton_;    ///< Click button to see ind. spot status

    private SelectedParkingLot selectedLot_;    ///< Selected parking lot
    private int totalSpots_;                    ///< Total number of parking spots available
    private int spotsRemaining_;                ///< Number of parking spots remaining

    private Timer pollTimer_;                   ///< Poll timer

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

        // Retrieve screen inputs
        spaceRemainingLabel_ = (TextView)findViewById(R.id.spaceRemainingLabel);
        spaceRemainingCount_ = (TextView)findViewById(R.id.spaceRemainingCount);
        monitorParkingMap_ = (ImageView)findViewById(R.id.monitorParkingMap);
        unavailableOverlay_ = findViewById(R.id.unavailableOverlay);
        availableOverlay_ = findViewById(R.id.availableOverlay);
        parkingSpotStatusButton_ = (Button)findViewById(R.id.viewIndividualParkingButton);

        // Retrieve intent
        Intent intent = getIntent();
        selectedLot_ =
                (SelectedParkingLot)intent.getSerializableExtra(RecommendParkingActivity.PREFERENCES_INTENT_DATA);

        // Initialize other member variables (default values)
        totalSpots_ = 0;
        spotsRemaining_ = 0;

        // Using SelectedParkingLot data, determine the number of spaces available and in total
        getParkingSpotCount();
        getParkingSpotStatus();

        // Determine which parking lot image should be shown on this screen and display
        findAndDisplayParkingLotImage();

        // Remainder of screen updates will be handled by onWindowFocusChanged()
    }

    /**
     * Called when the window needs to be updated.
     *
     * @param hasFocus True if have focus, false otherwise
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        // Get parking spot status
        getParkingSpotStatus();

        // Refresh screen
        refreshScreen();
    }

    /**
     * Called when user leaves activity.
     */
    @Override
    public void onPause()
    {
        super.onPause();

        // Stop timer
        pollTimer_.cancel();

        System.out.println("Timer canceled");
    }

    /**
     * Called when user re-enters activity
     */
    @Override
    public void onResume()
    {
        super.onResume();

        // Configure timer
        pollTimer_ = new Timer();
        pollTimer_.schedule(new MonitorLotPollTask(), 0, POLL_TIMER_INTERVAL_MSEC);
    }

    /**
     * Called when the user presses the view parking lot status button
     *
     * @param view Current view
     */
    public void viewParkingSpotStatusButton(View view)
    {
        // Open the monitor parking spot activity, passing in parking preference
        Intent intent = new Intent(this, MonitorParkingSpotStatusActivity.class);
        intent.putExtra(RecommendParkingActivity.PREFERENCES_INTENT_DATA, selectedLot_);
        startActivity(intent);
    }

    /************************  Class Private Interface  ***********************/

    /**
     * Makes a call to the back-end server to obtain the total number of parking
     * spots in the lot, and the number of parking spots available.
     *
     * Updates member variable totalSpots_
     */
    private void getParkingSpotCount()
    {
        // TODO:  IMPLEMENT FUNCTION
        totalSpots_ = 100;
    }

    /**
     * Makes a call to the back-end server to obtain the number of parking spots
     * available in the lot.
     *
     * Updates member variable spotsRemaining_
     */
    private void getParkingSpotStatus()
    {
        // TODO:  IMPLEMENT FUNCTION
        spotsRemaining_ = (spotsRemaining_ + 1) % totalSpots_;
    }

    /**
     * Determine what parking lot image should be shown on the screen.
     *
     * Configures activity to display that image once found
     */
    private void findAndDisplayParkingLotImage()
    {
        // TODO:  IMPLEMENT FUNCTION
        monitorParkingMap_.setImageResource(R.drawable.lot_59020);
    }

    /**
     * Update screen.  This includes both the progress bar and spots
     * remaining count.
     */
    private void refreshScreen()
    {
        //
        // Step 1:  Update spot remaining count
        //
        spaceRemainingCount_.setText(Integer.toString(spotsRemaining_));

        //
        // Step 2:  Update parking lot progress bar images
        //

        // Note:  Overlay with transparency inspired by a stack overflow question:
        // http://stackoverflow.com/questions/5211912/android-overlay-a-picture-jpg-with-transparency
        // That example showed how to overlay a transparent image on another.  This design extends
        // that idea by adding an additional overlay, and dynamically change the height of these
        // overlays based on external information (i.e., parking lot availability).

        // Compute required heights for available and unavailable overlays
        // Unavailable overlay height based on percentage of parking spots remaining
        int unavailLayoutHeight =
                (int)(monitorParkingMap_.getMeasuredHeight() * ((float)(totalSpots_ - spotsRemaining_) / (float)totalSpots_));
        int availLayoutHeight = (monitorParkingMap_.getMeasuredHeight() - unavailLayoutHeight);

        // Configure unavailable parking spot overlay
        unavailableOverlay_.setBackgroundColor((PARKING_LOT_OPACITY * 0x10000000) | 0x00FF0000);
        FrameLayout.LayoutParams unavailParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, // Width
                                             unavailLayoutHeight);                  // Height
        unavailParams.gravity = Gravity.BOTTOM;
        unavailableOverlay_.setLayoutParams(unavailParams);


        /** TEST DEBUG CODE */
        /*
        System.out.println("Image Height:  " + monitorParkingMap_.getMeasuredHeight());
        System.out.println("Image Width:  " + monitorParkingMap_.getMeasuredWidth());
        System.out.println("Image Top:  " + monitorParkingMap_.getTop());
        System.out.println("Image Bottom:  " + monitorParkingMap_.getBottom());

        System.out.println("Unavailable Overlay Height:  " + unavailableOverlay_.getMeasuredHeight());
        System.out.println("Unavailable Overlay Width:  " + unavailableOverlay_.getMeasuredWidth());
        System.out.println("Unavailable Overlay Top:  " + unavailableOverlay_.getTop());
        System.out.println("Unavailable Overlay Bottom:  " + unavailableOverlay_.getBottom());
        */
        /** END TEST DEBUG CODE */

        // Configure available parking spot overlay
        availableOverlay_.setBackgroundColor((PARKING_LOT_OPACITY * 0x10000000) | 0x0000FF00);
        FrameLayout.LayoutParams availParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, // Width
                                             availLayoutHeight);                    // Height
        availParams.gravity = Gravity.TOP;
        availableOverlay_.setLayoutParams(availParams);

        // Update views
        unavailableOverlay_.invalidate();
        availableOverlay_.invalidate();
    }

    /**
     * Processes poll timer event.
     *
     * Called by the poll timer thread.
     */
    private void processPollTimerEvent()
    {
        // TODO:  Should only perform timer processing if moved sufficient distance?
        // Screen changes cannot be done on the timer thread.  Need to be done on main thread.  See
        // http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // Get updated parking spot status
                getParkingSpotStatus();

                // Update screen
                refreshScreen();
            }
        });
    }

    /**
     * Poll timer task class
     */
    class MonitorLotPollTask extends TimerTask
    {
        /**
         * Poll timer timeout handler
         */
        @Override
        public void run()
        {
            processPollTimerEvent();
        }
    }
}
