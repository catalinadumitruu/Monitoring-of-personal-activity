package catalinadumitru.monitorizareaactivitatiipersonale.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import catalinadumitru.monitorizareaactivitatiipersonale.R;

public class CustomAdaptor_Person extends BaseAdapter {

    Context context;
    List<Person> person;

    public CustomAdaptor_Person(@NonNull Context context, List<Person> pers) {
        this.context = context;
        this.person = pers;
    }

    public Context getContext() { return context; }

    public void setContext(Context context) { this.context = context; }

    @Override
    public int getCount() { return person.size(); }

    @Nullable
    @Override
    public Person getItem(int position) {
        return person.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        Person person = this.person.get(position);

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.customadapter_person, parent, false);


            viewHolder.nameText = convertView.findViewById(R.id.name_adaptor);
            viewHolder.usernameText =  convertView.findViewById(R.id.username_adaptor);
            viewHolder.passwordText = convertView.findViewById(R.id.password_adaptor);
            viewHolder.birthdayText = convertView.findViewById(R.id.birthday_adaptor);
            viewHolder.emailText = convertView.findViewById(R.id.email_adaptor);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try{
            viewHolder.nameText.setText("Name: " + person.getName());
            viewHolder.usernameText.setText("Username: " + person.getUsername());
            viewHolder.passwordText.setText("Password: " + person.getPassword());
            viewHolder.birthdayText.setText("Birthday: " + person.getBirthday());
            viewHolder.emailText.setText("Email: " + person.getEmail());
        }catch(Exception e){
            e.printStackTrace();
            Log.i("MY ERROR", "This doesn't work");
        }

        return convertView;
    }

    static class ViewHolder{
        TextView nameText;
        TextView usernameText;
        TextView passwordText;
        TextView birthdayText;
        TextView emailText;
    }
}
