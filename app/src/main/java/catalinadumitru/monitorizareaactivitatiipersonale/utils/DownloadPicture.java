package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPicture extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url;
        HttpURLConnection connection = null;

        try{
            url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();

            InputStream input = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(input);

            return  image;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
