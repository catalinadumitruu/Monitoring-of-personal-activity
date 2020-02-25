package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.Activity;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.BaseAdaptor;

public class Add extends AppCompatActivity {

    TextView currentDay;
    ListView listOfActivities;
    ArrayList<String> list;
    EditText activity;
    int NumberofActiv;
    ArrayList<Integer> itemIds;
    //BaseAdapter adapter;
    Button submitButton;
    RatingBar ratingBar;
   // Intent intentTOstatistics;
    Intent incoming;
    List<Boolean> state;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private App_DML dml_op;
    String currentUser = "";
    int currentUserID;
    List<String> fromDB;
    int usID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        dml_op = new App_DML(this.getApplication());

        NumberofActiv = 0;
       // intentTOstatistics = new Intent(Add.this, Statistics_firebase.class);

        ratingBar = findViewById(R.id.ratingDayBar);
        submitButton = findViewById(R.id.submitBTN);
        currentDay = findViewById(R.id.currentDay);
        listOfActivities = findViewById(R.id.listOfActivities);
        activity = findViewById(R.id.add_activityTXT);

        list = new ArrayList<>();
        state = new ArrayList<>();
        fromDB = new ArrayList<>();

        itemIds = new ArrayList<Integer>();
      //  adapter = new BaseAdaptor(this, list);

        incoming = getIntent();
        String data = incoming.getStringExtra("currentDate");

        try{
            currentDay.setText(data);
            //Log.i("data mea", data);
        }catch(Exception e){
            Log.i("myError", e.getMessage());
        }


        //Aceasta parte de cod afiseaza lista de activitati deja existenta oentru ziua curenta, insa
        // nu mai functioneaza click-ul pe item => activitate terminata

        //get username of current user
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        currentUser = prefs.getString("current_user", "NaN");
        usID = dml_op.getUserID(currentUser);
//
//        Log.i("Cuuren user", usID + "");
//        Log.i("Current day" , data  +"");
//        try{
//            fromDB = dml_op.getAllActivities(usID, data);
//        }catch(Exception e){
//            Log.i("Error", e.getMessage());
//        }
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, fromDB);
//        listOfActivities.setAdapter(adapter);



        //intentTOstatistics.putExtra("dateFORactivities", currentDay.getText());
        CheckedItem();
    }

    public void CheckedItem (){
        listOfActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state.set(position, !state.get(position));
                // se schimba din done in to be done si tot asa
//                TextView text = (TextView) view;
//                if(state.get(position) == true) {
//                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    itemIds.add(text.getId());
//                }
            }
        });
    }

    public void crossItems(){
//        for(int id : itemIds){
//            TextView tobeCrossed = findViewById(id);
//            tobeCrossed.setPaintFlags(tobeCrossed.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        }
//        TextView text = (TextView) view;
        for(int i = 0 ; i < list.size(); i++){
            if(state.get(i) == true){
//                text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }

    public void addActivity(View view){
        String newActiv = activity.getText().toString();
        if(newActiv.length() > 1){
            list.add(newActiv);
            state.add(false);   // la inceput toate activ sunt false -> necompletate
            NumberofActiv++;
        }
        updateListView(list);
       // intentTOstatistics.putExtra("listOfActivities", list);
        activity.setText("");
    }

    public void updateListView(ArrayList<String> list){
        try{
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, list);
            listOfActivities.setAdapter(adapter);
            crossItems();

            //listOfActivities.setAdapter(adapter);
        }catch(Exception e){
            Log.i("myErrrror", e.getMessage());
        }

        CheckedItem();
    }

    public float submitData(View view){
            // store the value of the rating bar in a value and send it to the Statistics

        float rate = ratingBar.getRating();
        Log.i("RATE THE DAY" ,rate + "");
        //intentTOstatistics.putExtra("rate", rate);

        editor.putFloat("rate_of", rate);
        editor.apply();
        return rate;
    }

    public void writeTo_prefFile(){
        int i = 1;
        editor.putString("date", currentDay.getText().toString());
        editor.putString("numberString", NumberofActiv + "");
        editor.putInt("numberOfAciv", list.size());
        for(String activ : list){
            editor.putString("activ" + i, activ);
            i++;
        }
        editor.putFloat("rating", submitData(ratingBar));
        editor.putString("ratingString", submitData(ratingBar) + "");
        editor.apply();
    }

    //getting the items from listView  --  these are already saved in list


    public void done(View view){

        writeTo_prefFile();

        //de asemenea, cand apasam done vrem sa adaugam activitatile in baza de date
        // date -> currentDay
        // description -> in list
        // status -> N , on click -> change to Y
        // user ID -> select userID from users where username = :username (din shared pref)
//
//        //get username of current user
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        currentUser = prefs.getString("current_user", "NaN");

        //get id of current_user
        currentUserID = dml_op.getUserID(currentUser);

        // pt fiecare activitate adaugam o inregistrare
        for(int i = 0; i < list.size(); i++){
            Log.i("Listofactiv", "" + list.get(i));
            Activity activ = new Activity();
            activ.setDate(currentDay.getText().toString());
            activ.setDescription(list.get(i));
            activ.setStatus(state.get(i));
            activ.setUserID(currentUserID);

            Log.i("Object inserted", "" + activ.toString());
            //insert in database
            dml_op.insertActivity(activ);

            Toast.makeText(this, "Activities were inserted in database", Toast.LENGTH_LONG).show();
        }
    }
}
