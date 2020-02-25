package catalinadumitru.monitorizareaactivitatiipersonale.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.utils.DownloadPicture;

public class More extends AppCompatActivity {

    ImageView pic;
    Intent intent;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        intent = new Intent(More.this, Settings.class);
        pic = findViewById(R.id.pic);
        send = findViewById(R.id.send);
    }

    public void downloadImage(View view){

        DownloadPicture task = new DownloadPicture();
        final Bitmap myImage;

        try{
            myImage = task.execute("https://i7.pngguru.com/preview/723/680/969/pepe-the-frog-internet-meme-clip-art-frog.jpg").get();
            pic.setImageBitmap(myImage);

//            send.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    intent.putExtra("image", myImage);
//                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                    myImage.compress(Bitmap.CompressFormat.PNG, 50, bs);
//                    intent.putExtra("bitmapbytes", bs.toByteArray());
//                }
//            });

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaStore.Images.Media.insertImage(getContentResolver(), myImage, "Pic" , "Downloaded pic");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void openMaps(View view){
        Intent intent = new Intent(More.this, MapsActivity.class);
        startActivity(intent);
    }
}
