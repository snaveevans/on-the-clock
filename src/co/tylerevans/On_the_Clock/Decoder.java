package co.tylerevans.On_the_Clock;

/**
 * Created by tyler on 7/2/14.
 */
public class Decoder {

    public static final int HOURS_TO_LEAVE = 0;
    public static final int HOURS_WORKED = 1;
    public static final int CARD_PUNCHER = 2;

    public static final double STAN_TO_MIL = 1.66666666666666666;

    public static Time hourColonMinute(String string){
        int hour, minute, div;
        if(string.contains(":")){//colon was used
            div = string.indexOf(":");
            hour = Integer.parseInt(string.substring(0,div));
            minute = Integer.parseInt(string.substring(div+1,string.length()));
        }
        else{//colon was not used
            if(string.length() > 3) {//eg 1130 (11:30)
                hour = Integer.parseInt(string.substring(0,2));
                minute = Integer.parseInt(string.substring(2,4));
            }
            else{//eg 930 (9:30)
                hour = Integer.parseInt(string.substring(0,1));
                minute = Integer.parseInt(string.substring(1,3));
            }
        }
        return new Time(hour,minute,0);
    }
    
    public static Time hourDotMinute(String string){
        int hour, minute, div;
        if(string.contains(".")){//
            div = string.indexOf(".");
            //Log.d("HoursToLeave","Div " + div);
            if(div == 0 && string.length() == 1)//the string only contains a dot
                return null;

            if(div != 0)
                hour = Integer.parseInt(string.substring(0,div));
            else
                hour = 0;
            minute = Integer.parseInt(string.substring(div+1, string.length()));
        }
        else {
            hour = Short.parseShort(string);
            minute = 0;
        }
        return new Time(hour,minute,0);
    }

    public static String format(Time inTime, Time outAndWantWork, Time lunchTime, int type){
        String result = "";
        int resultHour = 0;
        int resultMinute = 0;
        int resultSecond = 0;

        switch (type){
            case (HOURS_TO_LEAVE):
                resultMinute = (int)(inTime.getMinute() * STAN_TO_MIL) + lunchTime.getMinute() + outAndWantWork.getMinute();
                resultHour = inTime.getHour() + lunchTime.getHour() + outAndWantWork.getHour();
                break;
            case (HOURS_WORKED):
                resultHour = outAndWantWork.getHour() - inTime.getHour() - lunchTime.getHour();
                resultMinute = (int)((outAndWantWork.getMinute()* STAN_TO_MIL) - (inTime.getMinute()* STAN_TO_MIL));
                resultMinute -= lunchTime.getMinute();
                break;
            case (CARD_PUNCHER):

                break;
        }


        Time tempTime = Time.positiveTime(resultHour,resultMinute,resultSecond);
        resultHour = tempTime.getHour();
        resultMinute = tempTime.getMinute();
        resultSecond = tempTime.getSecond();

        if(resultHour < 0)
            resultHour += 12;

        if(resultHour > 12)
            resultHour -= 12;
        resultMinute = (int)(resultMinute/ STAN_TO_MIL);

        result = Integer.toString(resultHour);

        if(type == HOURS_WORKED)
            result += " hours ";
        else
            result += ":";

        if(resultMinute < 10){
            result += "0" + resultMinute;
        }
        else{
            result += resultMinute;
        }

        if(type == HOURS_WORKED){
            result += " minutes";
        }

        return result;
    }

}
