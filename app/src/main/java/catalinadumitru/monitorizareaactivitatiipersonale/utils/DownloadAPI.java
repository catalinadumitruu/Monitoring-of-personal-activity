package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URL;

public class DownloadAPI extends AsyncTask<String, Void, String> {

    URL url;
    HttpURLConnection connection = null;
    String result = "";
    String quote, author;

    @Override
    protected String doInBackground(String... strings) {

        try{
            url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            InputStream input = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);

            int data = reader.read();

            while(data != -1){

                char content = (char)data;
                result += content;

                data= reader.read();
            }

            return result;

        }catch(Exception e){
            e.printStackTrace();
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //extracting the quote and the author

    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }
}
