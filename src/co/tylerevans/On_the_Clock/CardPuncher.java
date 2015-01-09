package co.tylerevans.On_the_Clock;

import android.os.Bundle;;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;

/**
 * Created by tyler on 6/30/14.
 */
public class CardPuncher extends MyActivity {

    private Button workStart, workEnd, lunchEnd, lunchStart;
    private TextView textView;
    private int state;
    private Time inTime, outTime, lunchTime;

    private final int WORKING = 0;
    private final int NOT_WORKING = 1;
    private final int BREAK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_puncher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView = (TextView) findViewById(R.id.result);
        workStart = (Button) findViewById(R.id.work_start);
        workEnd = (Button) findViewById(R.id.work_end);
        lunchEnd = (Button) findViewById(R.id.lunch_end);
        lunchStart = (Button) findViewById(R.id.lunch_start);
        state = NOT_WORKING;
        inTime = new Time(0,0,0);
        outTime = new Time(0,0,0);
        lunchTime = new Time(0,0,0);
        className = "CardPuncher";
    }

    public void work_start(View view){
        state = WORKING;
        Calendar rightNow = Calendar.getInstance();

        //Time temp = new Time(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));

        //Log.d("CardPuncher", "Work Started-- Time: " + temp.getHour() + ":" + temp.getMinute() + ":" + temp.getSecond() + " Calender: " + rightNow.get(Calendar.HOUR) + ":" + rightNow.get(Calendar.MINUTE) + ":" + rightNow.get(Calendar.SECOND));

        inTime.setTime(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));
        workStart.setVisibility(Button.GONE);
        workEnd.setVisibility(Button.VISIBLE);
        lunchStart.setVisibility(Button.VISIBLE);

        textView.setText("Clocked-in: " + inTime.getHour() + ":" + inTime.getMinute() + ":" + inTime.getSecond());
        textView.setVisibility(TextView.VISIBLE);
    }

    public void work_end(View view){
        Calendar rightNow = Calendar.getInstance();
        outTime.setTime(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));

        //Time temp = new Time(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));

        //Log.d("CardPuncher", "Work Ended-- Time: " + temp.getHour() + ":" + temp.getMinute() + ":" + temp.getSecond() + " Calender: " + rightNow.get(Calendar.HOUR) + ":" + rightNow.get(Calendar.MINUTE) + ":" + rightNow.get(Calendar.SECOND));

        if(state == BREAK){
            state = WORKING;
            rightNow = Calendar.getInstance();

            lunchTime = Time.difference(lunchTime, rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));
        }

        Time worked = Time.difference(inTime, outTime);
        worked.subtract(lunchTime);

        textView.setText("You have worked " + worked.getHour() + " hours, " + worked.getMinute() + " minutes, " + worked.getSecond() + " seconds.");
        textView.setVisibility(TextView.VISIBLE);
        workStart.setVisibility(Button.VISIBLE);
        workEnd.setVisibility(Button.GONE);
        lunchStart.setVisibility(Button.GONE);
    }

    public void lunch_start(View view){
        state = BREAK;
        Calendar rightNow = Calendar.getInstance();

        //Time temp = new Time(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));

        //Log.d("CardPuncher", "Lunch Started-- Time: " + temp.getHour() + ":" + temp.getMinute() + ":" + temp.getSecond() + " Calender: " + rightNow.get(Calendar.HOUR) + ":" + rightNow.get(Calendar.MINUTE) + ":" + rightNow.get(Calendar.SECOND));

        lunchTime.setTime(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));
        lunchStart.setVisibility(Button.GONE);
        lunchEnd.setVisibility(Button.VISIBLE);

        textView.setText("Lunch started at " + lunchTime.getHour() + ":" + lunchTime.getMinute() + ":" + lunchTime.getSecond());
    }

    public void lunch_end(View view){
        state = WORKING;
        Calendar rightNow = Calendar.getInstance();

        //Time temp = new Time(rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));

        //Log.d("CardPuncher", "Lunch Ended-- Time: " + temp.getHour() + ":" + temp.getMinute() + ":" + temp.getSecond() + " Calender: " + rightNow.get(Calendar.HOUR) + ":" + rightNow.get(Calendar.MINUTE) + ":" + rightNow.get(Calendar.SECOND));

        lunchTime = Time.difference(lunchTime, rightNow.get(Calendar.HOUR),rightNow.get(Calendar.MINUTE),rightNow.get(Calendar.SECOND));
        lunchEnd.setVisibility(Button.GONE);

        textView.setText("Lunch was " + lunchTime.getHour() + " hours, " + lunchTime.getMinute() + " minutes, " + lunchTime.getSecond() + " seconds.");
    }
}
