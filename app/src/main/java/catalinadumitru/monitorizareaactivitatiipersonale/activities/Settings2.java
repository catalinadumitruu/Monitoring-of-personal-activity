package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;
import catalinadumitru.monitorizareaactivitatiipersonale.database.User;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.CustomAdaptor_Person;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.Person;

public class Settings2 extends AppCompatActivity {

    List<String> countries;
    Spinner spinner;
    Person person;
    CustomAdaptor_Person custom_adapter;
    ListView list;
    List<Person> people_list;
    ImageView profilePic;
    Uri profilePic_uri;


    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        person = new Person();

        try {

            person = readFromFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

        people_list = new ArrayList<Person>();
        people_list.add(person);

        //Log.i("PersonGenerated2" , person.toString());

        profilePic = findViewById(R.id.profilepic_settings2);
        spinner = findViewById(R.id.spinner_settings2);
        list = findViewById(R.id.Attributes_settings2);

        countries = new ArrayList<>();
        countries.add(getResources().getString(R.string.Romania));
        countries.add(getResources().getString(R.string.Moldova));
        countries.add(getResources().getString(R.string.Germania));
        countries.add(getResources().getString(R.string.Anglia));
        countries.add(getResources().getString(R.string.Elevetia));
        countries.add(getResources().getString(R.string.Olanda));
        countries.add(getResources().getString(R.string.Suedia));
        countries.add(getResources().getString(R.string.Norvegia));
        countries.add(getResources().getString(R.string.Irlanda));
        countries.add(getResources().getString(R.string.Italia));
        countries.add(getResources().getString(R.string.Polonia));
        countries.add(getResources().getString(R.string.USA));

        ArrayAdapter adapter = new ArrayAdapter(Settings2.this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        custom_adapter = new CustomAdaptor_Person(this, people_list);
        list.setAdapter(custom_adapter);


    }

    public Person readFromFile() {
        Person person = null;
        ObjectInputStream input;
        String filename = "testFilemost.srl";

        try {
            input = new ObjectInputStream(new FileInputStream(new File(new File(getFilesDir(),"")+File.separator+filename)));
            person = (Person) input.readObject();
            // Log.v("serialization","Person a="+myPersonObject.getA());
            input.close();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return person;
    }

    public void changePicture(View view){

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            profilePic_uri = data.getData();
            profilePic.setImageURI(profilePic_uri);
        }
    }
}
