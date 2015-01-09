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
public class HoursWorked extends MyActivity{

    TextView textView;
    Spinner inHourSpin, inMinuteSpin, outHourSpin, outMinuteSpin, lunchSpin;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hours_worked);
    }

    @Override
    protected void onResume() {
        super.onResume();

        inHourSpin = (Spinner)findViewById(R.id.in_hour_spin);
        inMinuteSpin = (Spinner)findViewById(R.id.in_minute_spin);
        outHourSpin = (Spinner)findViewById(R.id.out_hour_spin);
        outMinuteSpin = (Spinner)findViewById(R.id.out_minute_spin);
        lunchSpin = (Spinner)findViewById(R.id.lunch_time_spin);
        textView = (TextView)findViewById(R.id.result);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hoursOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inHourSpin.setAdapter(adapter);
        outHourSpin.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,R.array.minutesOptions,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inMinuteSpin.setAdapter(adapter);
        outMinuteSpin.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this, R.array.lunchOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lunchSpin.setAdapter(adapter);

        simpleDateFormat = new SimpleDateFormat("kk:mm");

        className = "HoursWorked";
    }

    public void calculate(View view){
        String inHour, inMinute, outHour, outMinute, lunchTime;

        inHour = String.valueOf(inHourSpin.getSelectedItem());
        inMinute = String.valueOf(inMinuteSpin.getSelectedItem());
        outHour = String.valueOf(outHourSpin.getSelectedItem());
        outMinute = String.valueOf(outMinuteSpin.getSelectedItem());
        lunchTime = String.valueOf(lunchSpin.getSelectedItem());

        try{
            Date inTime = simpleDateFormat.parse(inHour + ":" + inMinute);
            Date outTime = simpleDateFormat.parse(outHour + ":" + outMinute);
            long lunchMinutes = Long.parseLong(lunchTime);

            if(outTime.getTime() > inTime.getTime()) {//outTime is after inTime
                long diff = outTime.getTime() - inTime.getTime();

                if(diff > (lunchMinutes * 60 * 1000)){
                    diff = diff - (lunchMinutes * 60 * 1000);
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    String result = diffHours + " hours";
                    if(diffMinutes > 0){
                        result += "\n" + diffMinutes + " minutes";
                    }

                    textView.setText(result);
                }
                else {
                    textView.setText("Your lunch was greater than the time you worked.");
                }
            }
            else {//inTime is after outTime
                textView.setText("In time must be before out.");
            }
            textView.setVisibility(textView.VISIBLE);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }
}
