package catalinadumitru.monitorizareaactivitatiipersonale.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class App_DML extends AndroidViewModel {

    private AppDatabase appDB;
    private AppDAO appDAO;
    private User _user;

    public App_DML(@NonNull Application application) {
        super(application);

        appDB = AppDatabase.getInstance(application);
        appDAO = appDB.appDAO();
        _user = new User();
    }

    public void insertUser(User user){
        new InsertAsyncTask(appDAO).execute(user);
    }
    public User getUser(String username) throws ExecutionException, InterruptedException {
        User us = new User();
        GetUserAsyncTask ap = new GetUserAsyncTask(appDAO);
        us = (ap.execute(username)).get();
        return us;
    }

    public void updateUser(String username,String name, String password, String email, String birthday, String country){
        new UpdateAsyncTask(appDAO).execute(username,name,password,email,birthday,country);
    }

    public void deleteUser(String username){
        new DeleteAsyncTask(appDAO).execute(username);
    }

    public int getUserID(String username){
        int userID = 0;
        GetIDAsyncTask ap = new GetIDAsyncTask(appDAO);
        try {
            userID = (ap.execute(username)).get();
        } catch (Exception e) {
            Log.i("Get user ID exception", "" + e.getMessage());
        }
        return userID;
    }

    public void insertActivity(Activity activity){
        new InsertActivityAsyncTask(appDAO).execute(activity);
    }

    public List<String> getAllActivities(int userID, String date){
        List<String> listOfActivities = new ArrayList<String>();
        GetAllActivAsyncTask ap = new GetAllActivAsyncTask(appDAO);

        Get g =  new Get(userID, date);
        try {
            listOfActivities = (ap.execute(g)).get();
        } catch (Exception e) {
            Log.i("Get all activ exception", "" + e.getMessage());
        }

        Log.i("return", "" + listOfActivities.size());
        return listOfActivities;
    }

    public int getCountActivities(int userID){
        int number = 0;
        GetCountActivitiesAsyncTask app = new GetCountActivitiesAsyncTask(appDAO);

        try{
            number = (app.execute(userID)).get();
        }catch(Exception e){
            Log.i("Get count activ", e.getMessage());
        }

        return number;
    }

    public int getCountDONEActivities(int userID, boolean bool){
        int number = 0;
        GetCountDONEActivitiesAsyncTask app = new GetCountDONEActivitiesAsyncTask(appDAO);

        NewClass obj = new NewClass(userID, bool);

        try{
            number = (app.execute(obj)).get();
        }catch(Exception e){
            Log.i("Get count activ", e.getMessage());
        }

        return number;
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void>{

        AppDAO _appDAO;

        public InsertAsyncTask(AppDAO appdao) {
            _appDAO = appdao;
        }

        @Override
        protected Void doInBackground(User... users) {
            _appDAO.insertUser(users[0]);
            return null;
        }
    }

    private static class GetUserAsyncTask extends AsyncTask<String, Void, User>{

        AppDAO _appDAO;

        public GetUserAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected User doInBackground(String... strings) {
            User user = new User();
            user = _appDAO.getUser(strings[0]);

            return user;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<String, Void, Void>{
        AppDAO _appDAO;

        public UpdateAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected Void doInBackground(String... strings) {
            _appDAO.updateUser(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String, Void, Void>{
        AppDAO _appDAO;

        public DeleteAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected Void doInBackground(String... strings) {
            _appDAO.deleteUser(strings[0]);
            return null;
        }
    }

    private static class GetIDAsyncTask extends AsyncTask<String, Void, Integer>{

        AppDAO _appDAO;

        public GetIDAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected Integer doInBackground(String... strings) {
            return _appDAO.getUserID(strings[0]);
        }
    }

    private static class InsertActivityAsyncTask extends AsyncTask<Activity, Void, Void>{

        AppDAO _appDAO;

        public InsertActivityAsyncTask(AppDAO appdao) {
            _appDAO = appdao;
        }

        @Override
        protected Void doInBackground(Activity... activities) {
            _appDAO.insertActivity(activities[0]);
            return null;
        }
    }

    private static class GetAllActivAsyncTask extends AsyncTask<Get, Void, List<String>>{

        AppDAO _appDAO;

        public GetAllActivAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected List<String> doInBackground(Get... gets) {
            List<String> allActiv = new ArrayList<String>();
            allActiv = _appDAO.getAllActivities(gets[0].getUserID(), gets[0].getDate());
            return allActiv;
        }
    }

    public class Get{
        int userID;
        String date;

        public Get(int u, String d){
            this.userID = u;
            this.date = d;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    private static class GetCountActivitiesAsyncTask extends AsyncTask<Integer, Void, Integer>{

        AppDAO _appDAO;

        public GetCountActivitiesAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return _appDAO.getCountActivities(integers[0]);
        }
    }

    private static class GetCountDONEActivitiesAsyncTask extends AsyncTask<NewClass, Void, Integer>{

        AppDAO _appDAO;

        public GetCountDONEActivitiesAsyncTask(AppDAO ap){ _appDAO = ap; }

        @Override
        protected Integer doInBackground(NewClass... newClasses) {
          return _appDAO.getCountDONEActivities(newClasses[0].getUserID(), newClasses[0].isState());
        }
    }

    public class NewClass {
        int userID;
        boolean state;

        public NewClass(int u, boolean b){
            this.userID = u;
            this.state = b;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }
}
