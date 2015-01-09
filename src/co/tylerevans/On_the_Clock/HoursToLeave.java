package co.tylerevans.On_the_Clock;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tyler on 6/30/14.
 */
public class HoursToLeave extends MyActivity{

    TextView textView;
    Spinner inHourSpin, inMinuteSpin, hoursWorkedSpin, minutesWorkedSpin, lunchSpin;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hours_to_leave);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inHourSpin = (Spinner)findViewById(R.id.in_hour_spin);
        inMinuteSpin = (Spinner)findViewById(R.id.in_minute_spin);
        hoursWorkedSpin = (Spinner)findViewById(R.id.hour_worked_spin);
        minutesWorkedSpin = (Spinner)findViewById(R.id.minute_worked_spin);
        lunchSpin = (Spinner)findViewById(R.id.lunch_time_spin);
        textView = (TextView)findViewById(R.id.result);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hoursOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inHourSpin.setAdapter(adapter);
        hoursWorkedSpin.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,R.array.minutesOptions,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inMinuteSpin.setAdapter(adapter);
        minutesWorkedSpin.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.lunchOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lunchSpin.setAdapter(adapter);

        simpleDateFormat = new SimpleDateFormat("kk:mm");

        className = "HoursToLeave";
    }

    public void calculate(View view){
        String inHour, inMinute, hoursWorked, minutesWorked, lunchTime;

        inHour = String.valueOf(inHourSpin.getSelectedItem());
        inMinute = String.valueOf(inMinuteSpin.getSelectedItem());
        hoursWorked = String.valueOf(hoursWorkedSpin.getSelectedItem());
        minutesWorked = String.valueOf(minutesWorkedSpin.getSelectedItem());
        lunchTime = String.valueOf(lunchSpin.getSelectedItem());

        try{
            Date inTime = simpleDateFormat.parse(inHour + ":" + inMinute);
            Date outTime = simpleDateFormat.parse(hoursWorked + ":" + minutesWorked);
            long lunchMinutes = Long.parseLong(lunchTime);

            long sum = outTime.getTime() + inTime.getTime() + (lunchMinutes * 60 * 1000) - (7 * 60 * 60 * 1000);

            Date sumDate = new Date(sum);

            String leaveTime = new SimpleDateFormat("hh:mm aaa").format(sumDate);
            textView.setText("You can leave at " + leaveTime);
            textView.setVisibility(TextView.VISIBLE);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }
}
