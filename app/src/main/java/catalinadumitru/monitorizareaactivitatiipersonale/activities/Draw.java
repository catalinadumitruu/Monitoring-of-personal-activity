package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;

public class Draw extends AppCompatActivity {

    //get intent numar de activ din graf
    // find view by id -> viewul facut de mine
    // set numar etc

    TextView _activToal_;
    TextView _actovDONE_;

    App_DML dml_op;
    SharedPreferences pref;

    int numberOfActivities = 0;
    int numberCompleted = 0;
    int userID;

    String currentUsername= "";
    View desen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        _activToal_ = findViewById(R.id.activTot);
        _actovDONE_ = findViewById(R.id.activDone);

        dml_op = new App_DML(getApplication());
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        currentUsername = getIntent().getStringExtra("userUSER");

//        currentUsername = pref.getString("userUSER", "");
        numberOfActivities = pref.getInt("total", -1);
        numberCompleted = pref.getInt("done",-2);

        Log.i("user in draw", currentUsername + "");

        try{
          userID = dml_op.getUserID(currentUsername);
          numberOfActivities = dml_op.getCountActivities(userID);
          numberCompleted = dml_op.getCountDONEActivities(userID, true);
      }catch(Exception e){
          Log.i("Info about exp(grf)", e.getMessage());
      }

        Log.i("total in draw", numberOfActivities + "");
        Log.i("done in draw", numberCompleted + "");

        _activToal_.setText("Numarul total de activitati-> " + numberOfActivities);
        _actovDONE_.setText("Numarul de actiivitati completate-> " + numberCompleted + "");

        desen = findViewById(R.id.grafic);
    }

}
