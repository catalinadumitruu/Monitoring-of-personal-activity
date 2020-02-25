package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;

public class Grafic extends AppCompatActivity {

    Button read;
    TextView state_user;
    TextView activ_total;
    TextView activ_compl;
    TextView text_rating;

    String id_firebase = "/";

    DatabaseReference databaseStatistics;
    SharedPreferences pref;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    App_DML dml_op = new App_DML(getApplication());
    String currentUsername= "";

    int userID=0;
    int _totalActiv = 0;
    int _complActiv = 0;
    List<Integer> SOURCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic);

        read = findViewById(R.id.readFromDB);
        state_user = findViewById(R.id.text_state_user);
        activ_total = findViewById(R.id.text_activ_total);
        activ_compl = findViewById(R.id.text_activ_compl);
        text_rating = findViewById(R.id.text_rating);
//        canvaas = findViewById(R.id.canvas);

       // dml_op = new App_DML(getApplication());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

//        pref = PreferenceManager.getDefaultSharedPreferences(this);
//        currentUsername = pref.getString("current_user", "NaN");
        currentUsername = getIntent().getStringExtra("currentUsername_ofuser");
        Log.i("_username", currentUsername + "");

        try {
            userID = dml_op.getUserID(currentUsername);
        }  catch(Exception e){
            Log.i("Info about exp(grf)", e.getMessage());
        }
        Log.i("_userid", userID + "");

        try{
            _totalActiv = dml_op.getCountActivities(userID);
            _complActiv = dml_op.getCountDONEActivities(userID, true);
        }catch(Exception er){
            Log.i("Graf interior", er.getMessage());
        }


        SOURCE = new ArrayList<>();
        Log.i("total", _totalActiv + "");
        Log.i("done", _complActiv + "");

        SOURCE.add(_totalActiv);
        SOURCE.add(_complActiv);

        databaseStatistics = FirebaseDatabase.getInstance().getReference("statistics");
    }

    public List<Integer> getValues(){
        return SOURCE;
    }

    public void readData(View view){

        id_firebase = getIntent().getStringExtra("id_firebase");
        Log.i("ID Firebase", id_firebase + "");

        final Integer[] userID = new Integer[1];
        final Integer[] total_Activ = new Integer[1];
        final Integer[] compl_activ = new Integer[1];
        final Float[] rating = new Float[1];

        databaseStatistics.child(id_firebase).child("userID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    if(dataSnapshot.getValue() != null){
                        try{
                            userID[0] = ((Long) dataSnapshot.getValue()).intValue();
                            Log.i("userID stat read", userID[0] + "");

                            state_user.setText("Showing statistics for user with id : " + userID[0]);
                        }catch(Exception e){
                            Log.i("Another ex on get data1", "" + e.getMessage());
                        }
                    }

                }catch(Exception e){
                    Log.i("Get data from firebase", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });

        databaseStatistics.child(id_firebase).child("total_activities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    if(dataSnapshot.getValue() != null){
                        try{
                            total_Activ[0] = ((Long) dataSnapshot.getValue()).intValue();
                            Log.i("total activ stat read", total_Activ[0] + "");

                            activ_total.setText("Total number of activities is " + total_Activ[0]);
                        }catch(Exception e){
                            Log.i("Another ex on get data2", "" + e.getMessage());
                        }
                    }

                }catch(Exception e){
                    Log.i("Get data from firebase", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });

        databaseStatistics.child(id_firebase).child("done_activities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    if(dataSnapshot.getValue() != null){
                        try{
                            compl_activ[0] = ((Long) dataSnapshot.getValue()).intValue();
                            Log.i("compl_activ stat read", compl_activ[0] + "");

                            activ_compl.setText("Out of which " + compl_activ[0] + " are completed");
                        }catch(Exception e){
                            Log.i("Another ex on get data3", "" + e.getMessage());
                        }
                    }

                }catch(Exception e){
                    Log.i("Get data from firebase", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });

        databaseStatistics.child(id_firebase).child("rating").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    if(dataSnapshot.getValue() != null){
                        try{
                            rating[0] = ((Long) dataSnapshot.getValue()).floatValue();
                            Log.i("rating stat read", rating[0] + "");

                            text_rating.setText("The overall rating is " + rating[0]);
                        }catch(Exception e){
                            Log.i("Another ex on get data4", "" + e.getMessage());
                        }
                    }

                }catch(Exception e){
                    Log.i("Get data from firebase", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });
    }

    public void drawGraph(View view){

        Intent intent = new Intent(Grafic.this, Draw.class);
//        int userID = -1;
//        int totalActiv = -1;
//        int complActiv = -2;
//
//        try{
//          userID = dml_op.getUserID(currentUsername);
//          totalActiv = dml_op.getCountActivities(userID);
//          complActiv = dml_op.getCountDONEActivities(userID, true);
//      }catch(Exception e){
//          Log.i("Info about exp(grf)", e.getMessage());
//      }
//
        editor.putInt("total", _totalActiv);
        editor.putInt("done", _complActiv);
        editor.apply();

        Log.i("__total", _totalActiv + "");
        Log.i("__done", _complActiv + "");

        intent.putExtra("total", _totalActiv);
        intent.putExtra("done", _complActiv);
        intent.putExtra("userUSER", currentUsername);

        startActivity(intent);
    }
}
