package co.tylerevans.On_the_Clock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    protected String className;
//    private String[] drawerClassNames;
//    private DrawerLayout drawerLayout;
//    private ListView drawerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        drawerClassNames = getResources().getStringArray(R.array.initialOptions);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerListView = (ListView) findViewById(R.id.left_drawer);
//
//        drawerListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_view, drawerClassNames));
//        drawerListView.setOnItemClickListener(new DrawerItemClickListener(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class clazz;
        Intent intent;
        try {
            switch (item.getItemId()) {
                case R.id.hours_to_leave_mb:
                    clazz = Class.forName("co.tylerevans.On_the_Clock.HoursToLeave");
                    intent = new Intent(this, clazz);
                    startActivity(intent);
                    return true;
                case R.id.hours_worked_mb:
                    clazz = Class.forName("co.tylerevans.On_the_Clock.HoursWorked");
                    intent = new Intent(this, clazz);
                    startActivity(intent);
                    return true;
                case R.id.card_puncher_mb:
                    clazz = Class.forName("co.tylerevans.On_the_Clock.CardPuncher");
                    intent = new Intent(this, clazz);
                    startActivity(intent);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        catch(ClassNotFoundException e){
            Log.d(className, "Unable to find class");
            return super.onOptionsItemSelected(item);
        }
    }

    class DrawerItemClickListener implements ListView.OnItemClickListener{

        Activity activity;

        DrawerItemClickListener (Activity activity){
            this.activity = activity;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Class clazz;
            Intent intent;
            try {
                switch (position) {
                    case 1:
                        clazz = Class.forName("co.tylerevans.On_the_Clock.HoursToLeave");
                        intent = new Intent(activity, clazz);
                        startActivity(intent);
                    case 2:
                        clazz = Class.forName("co.tylerevans.On_the_Clock.HoursWorked");
                        intent = new Intent(activity, clazz);
                        startActivity(intent);
                    case 3:
                        clazz = Class.forName("co.tylerevans.On_the_Clock.CardPuncher");
                        intent = new Intent(activity, clazz);
                        startActivity(intent);
                }
            }
            catch(ClassNotFoundException e){
                Log.d(className, "Unable to find class");
            }
        }
    }
}
