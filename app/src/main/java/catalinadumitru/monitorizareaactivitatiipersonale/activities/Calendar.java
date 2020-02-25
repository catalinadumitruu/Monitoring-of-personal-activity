package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import java.util.Date;

import catalinadumitru.monitorizareaactivitatiipersonale.R;

public class Calendar extends AppCompatActivity {

    CalendarView calendar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendar = findViewById(R.id.calendar);

        calendar.setDate(new Date().getTime(), false, true);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

//                String mon = "";
//                switch (month + 1){
//                    case 1 :
//                        mon = "January";
//                          break;
//                    case 2 :
//                        mon = "February";
//                        break;
//                    case 3 :
//                        mon = "March";
//                        break;
//                    case 4 :
//                        mon = "April";
//                        break;
//                    case 5 :
//                        mon = "May";
//                        break;
//                    case 6 :
//                        mon = "June";
//                        break;
//                    case 7 :
//                        mon = "July";
//                        break;
//                    case 8 :
//                        mon = "August";
//                        break;
//                    case 9 :
//                        mon = "September";
//                        break;
//                    case 10 :
//                        mon = "October";
//                        break;
//                    case 11 :
//                        mon = "November";
//                        break;
//                    case 12 :
//                        mon = "December";
//                        break;
//                }

                String day = dayOfMonth + "", monthof = month + "";
                if(dayOfMonth <= 9){day = "0" + dayOfMonth;}
                if(month < 9){monthof= "" + month;}
                String date = day + "/" + (monthof + 1) + "/" + year;
                Log.i("data", date);
                Intent intent = new Intent(Calendar.this, Add.class);
                intent.putExtra("currentDate", date);
                startActivity(intent);
            }
        });
    }
}
