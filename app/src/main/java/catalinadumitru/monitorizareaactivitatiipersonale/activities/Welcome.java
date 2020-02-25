package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.database.AppDAO;
import catalinadumitru.monitorizareaactivitatiipersonale.database.AppDatabase;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.DownloadAPI;

public class Welcome extends AppCompatActivity {

    TextView quote;
    String in, quoteText, author;

    public static Context contextOfApplication;

    private AppDatabase appDB;
    private AppDAO appDAO;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        contextOfApplication = getApplicationContext();

        appDB = AppDatabase.getInstance(this);
        appDAO = appDB.appDAO();

        quote = findViewById(R.id.motivationalQuote);

       DownloadAPI task = new DownloadAPI();

       String toBe = generateQuote(task);

//       if(toBe.length() >= 200){
//           toBe = generateQuote(task);
//       }

       quote.setText(toBe);
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    public String generateQuote(DownloadAPI task) {

        String texttt = null;

        try{
            in = task.execute("https://opinionated-quotes-api.gigalixirapp.com/v1/quotes").get();
        }catch(Exception e){
            e.printStackTrace();
        }

        try{
            JSONObject jsonObject = new JSONObject(in);

            String text = jsonObject.getString("quotes");

            JSONArray array = new JSONArray(text);

            for(int i = 0 ; i < array.length(); i++){
                JSONObject object = array.getJSONObject(i);

                quoteText = object.getString("quote");
                author = object.getString("author");

                Log.i("Author", author);
                Log.i("Quote", quoteText);

              texttt  = "\"" + quoteText + "\" - " + author;

                quote.setText(texttt);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return texttt;
    }

    public void setQuote(){

        DownloadAPI task = new DownloadAPI();


    }

    public void openLogin(View view){

        startActivity(new Intent(this, LogIn.class));
    }

    public void openRegister(View view){

        startActivity(new Intent(this, Register.class));
    }
}
