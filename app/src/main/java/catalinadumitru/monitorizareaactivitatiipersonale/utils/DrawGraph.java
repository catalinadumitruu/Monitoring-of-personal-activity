package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import catalinadumitru.monitorizareaactivitatiipersonale.MainActivity;
import catalinadumitru.monitorizareaactivitatiipersonale.R;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.Grafic;
import catalinadumitru.monitorizareaactivitatiipersonale.activities.Welcome;
import catalinadumitru.monitorizareaactivitatiipersonale.database.App_DML;

public class DrawGraph extends View {

    Paint paint;
    List<Integer> source;
    List<String> label;
    Random random;
    int numberOfActivities;
    int numberCompleted ;
    TextView _activToal_;
    TextView _actovDONE_;

    SharedPreferences pref;
    Context applicationContext = Welcome.getContextOfApplication();

    public DrawGraph(Context context){
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setTextSize(100);
    }


    public DrawGraph(Context context, @NonNull AttributeSet attrs){
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setTextSize(100);

        source = new ArrayList<>();
        random = new Random();
        label = new ArrayList<>();

        _activToal_ = findViewById(R.id.activTot);
        _actovDONE_ = findViewById(R.id.activDone);
//
//        for(int o : source){
//            Log.i("vall", o + "");
//        }

        pref = PreferenceManager.getDefaultSharedPreferences(applicationContext);

//
        numberOfActivities = pref.getInt("total", -1);
        numberCompleted = pref.getInt("done",-2);
//
//        Log.i("total", numberOfActivities + "");
//        Log.i("done", numberCompleted + "");
//

//        if(_actovDONE_ != null && _actovDONE_ != null) {
//            activTotal = Integer.parseInt(_activToal_.getText().toString());
//            activDONE = Integer.parseInt(_actovDONE_.getText().toString());
//        }
            Log.i("total in drawGrapg", numberOfActivities + "");
            Log.i("done in drawGrapg", numberCompleted + "");

        source.add(numberOfActivities);
        source.add(numberCompleted);

        label.add("Activities completed");
        label.add("Activities in total");

        //check if source was populated
        //Log.i("sourceLength",source.size() + "");
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth((float) (getHeight() * 0.01));
        //calculam distanta minima pe o lasam fata de margini
        float paddW = (float) (getWidth() * 0.1);
        float paddH = (float) (getHeight() * 0.1);

        //calculam suprafata efectiva de lucru, pentru desenarea graficului. Scadem toate cele 4 margini.
        float availableWidth = getWidth() - 2 * paddW;
        float availableHeight = getHeight() -  2 * paddH;

        //desenam axele graficului
        canvas.drawLine(paddW, paddH, paddW, paddH + availableHeight, paint);
        canvas.drawLine(paddW, paddH + availableHeight, paddW + availableWidth, paddH + availableHeight, paint);

        //calculam valoarea maxima, o vom folosi ca sa putem calcula inaltimea fiecarei bare
        double maxValue = calculateMaxim() - 15;

        //calculam grosimea unei bare(din dimensiune disponibila, ar trebui sa scadem cate un spatiu ptr fiecare bara, la final impartind la nr total de valori
        float widthOfElement = availableWidth / source.size();

        for (int i = 0; i < source.size(); i++) {

            Log.i("VALUEE", source.get(i) + "  " + i);
            Log.i("VALUEE", label.get(i) + "  " + i);

            //calculam o culoare aleator
            int color = Color.argb(100,
                    1 + random.nextInt(254),
                    1 + random.nextInt(254),
                    1 + random.nextInt(254));
            paint.setColor(color);

            float x1 = paddW + i * widthOfElement;
            float y1 = (float) ((1 - source.get(i)) / maxValue) * availableHeight + paddH;
            float x2 = x1 + widthOfElement;
            float y2 = paddH + availableHeight;

            canvas.drawRect(x1, y1, x2, y2, paint);
            drawLabel(canvas,x1, widthOfElement, paddH, availableHeight, label.get(i));
        }

    }

    private void drawLabel(Canvas canvas, float x1, float widthOfElement, float paddH, float availableHeight, String label) {
        paint.setColor(Color.BLACK);
        paint.setTextSize((float) (0.3* widthOfElement));
        float x = x1 + widthOfElement / 2;
        float y = 1 / 2 * paddH + availableHeight;
        canvas.rotate(270, x, y);
        canvas.drawText(label , x, y, paint);
        canvas.rotate(-270, x, y); // readucem canvasul in pozitia initiala. ce se scrie inainte de rotatire ramane in aceeasi pozitie.
    }

    private double calculateMaxim() {
        double max = 0;
        for (double value : source) {
            if (max < value) {
                max = value;
            }
        }
        return max;
    }
}
