package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adaptor_for_Statistics extends ArrayAdapter<Statistics> {

    Context context;
    List<Statistics> list = new ArrayList<Statistics>();

    public Adaptor_for_Statistics(Context context, ArrayList<Statistics> list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }
}
