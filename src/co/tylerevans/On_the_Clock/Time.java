package co.tylerevans.On_the_Clock;

/**
 * Created by tyler on 7/2/14.
 */
public class Time {

    private int hour, minute, second;

    public Time(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public void setTime(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public void militaryToStandard(){
        minute = (int)(minute / Decoder.STAN_TO_MIL);
        second = (int)(second / Decoder.STAN_TO_MIL);
    }

    public void standardToMilitary(){
        minute = (int)(minute * Decoder.STAN_TO_MIL);
        second = (int)(second * Decoder.STAN_TO_MIL);
    }

    public void subtract(Time time){
        Time temp = difference(time, this);
        hour = temp.getHour();
        minute = temp.getMinute();
        second = temp.getSecond();
    }

    public static Time difference(Time startTime, Time endTime){
        return difference(startTime, endTime.getHour(), endTime.getMinute(),endTime.getSecond());
    }

    public static Time difference(Time startTime, int hour, int minute, int second){
        int resultHour = hour - startTime.getHour();
        int resultMinute = (int)((minute * Decoder.STAN_TO_MIL) - (startTime.getMinute() * Decoder.STAN_TO_MIL));
        int resultSecond = (int)((second * Decoder.STAN_TO_MIL) - (startTime.getSecond() * Decoder.STAN_TO_MIL));

        Time temp = positiveTime(resultHour, resultMinute, resultSecond);

        temp.militaryToStandard();

        return  temp;
    }

    public static Time positiveTime(int hourIn, int minuteIn, int secondIn){
        int hour = hourIn;
        int minute = minuteIn;
        int second = secondIn;

        while(second >= 100){
            second -= 100;
            minute++;
        }

        while(second < 0){
            second += 100;
            minute--;
        }

        while(minute >= 100){
            minute -= 100;
            hour++;
        }

        while(minute < 0){
            minute += 100;
            hour--;
        }

        return new Time(hour,minute,second);
    }

    //region Getters
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
    //endregion
}
