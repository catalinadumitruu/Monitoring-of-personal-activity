package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Paint;
import android.os.CpuUsageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import catalinadumitru.monitorizareaactivitatiipersonale.R;


// ARRAY ADAPTOR
public class BaseAdaptor extends BaseAdapter {     // TO BE USED when reading a person from DataBase, to set the textViews from Settings

    Context context;
    List<String> list;
    ArrayList<String> selectedItems;

    public BaseAdaptor(Context context, List<String> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();

            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_checked, parent, false);
            holder.text = (TextView) convertView;

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(getItem(position).toString());
        if(selectedItems.contains(getItem(position))){
            holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.text.setPaintFlags(0);
        }
        return convertView;

    }

    public ArrayList<String> getSelectedStrings() {
        return selectedItems;
    }

    public class ViewHolder{
        TextView text;
    }

}
