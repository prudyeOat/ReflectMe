package teamwork.reflectme;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import teamwork.reflectme.model.NoteEntity;
import teamwork.reflectme.model.ResultEntity;
import teamwork.reflectme.roomHelperClasses.NoteDatabase;
import teamwork.reflectme.roomHelperClasses.ScoreDatabase;

public class MainRepository {

    private static MainRepository instance;
    private ArrayList<ResultEntity> dataSet=new ArrayList<>();
    private ArrayList<NoteEntity> noteEntityArrayList=new ArrayList<>();
    private int swipedPosition;
    private int testPosition;
    //private int saveType=0;
    private int savedScore,entryType;
    private  String name,date,content,path;
    private ScoreDatabase Database;
    private teamwork.reflectme.roomHelperClasses.NoteDatabase NoteDatabase;
    private NoteEntity note=new NoteEntity();
    private ResultEntity resultEntity=new ResultEntity();
    private ResultEntity enterEntity;
    MainActivity mainActivity;
    private Context context;
    private    ResultEntity noteResult;
    private MainRepositoryListener mListener;

    public MainRepository() {}
    public MainRepository(Application application) {
        context = application.getApplicationContext();
        Database = Room.databaseBuilder(context,
                ScoreDatabase.class, "score-database").build();

        NoteDatabase = Room.databaseBuilder(context,
                NoteDatabase.class, "note-database").build();
        try {
            mListener =(MainRepositoryListener) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() + " must implement MainRepositorylistener");

        }

    }


/*
    public static MainRepository getInstance(Application application){
        if(instance==null){
            instance=new MainRepository();
           context = application.getApplicationContext();

            try {
                mListener =(MainRepositoryListener) context;
            } catch (ClassCastException e) {

                throw new ClassCastException(context.toString() + " must implement MainRepositorylistener");

            }
          //init();
        }
        return instance;
    }
*/

    public void init() {
        //this.context = application.getApplicationContext();

    }



    public MutableLiveData<List<ResultEntity>> getResults(){


     //  init();
       MutableLiveData<List<ResultEntity>>data=new MutableLiveData<>();
     //   if(!(dataSet==null)) {
            data.setValue(dataSet);
       // }
     return data;
    }

    public MutableLiveData<List<NoteEntity>> getNotes(){
       // init();
        MutableLiveData<List<NoteEntity>>noteData=new MutableLiveData<>();
       // if(!(noteEntityArrayList==null)) {
            noteData.setValue(noteEntityArrayList);
      //  }
        return noteData;
    }



    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//init();
            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some dummy data to the database.



            switch (entryType){

                case 0:
                    //Now access all the methods defined in DaoAccess with sampleDatabase object

                    Database.scoreDao().insertOnlySingleRecord(enterEntity);
                    break;
                case 1:
                    NoteDatabase.noteDao().insertOnlySingleRecord(note);
                    break;
                case 2:
                    Database.scoreDao().insertOnlySingleRecord(resultEntity);
                    NoteDatabase.noteDao().insertOnlySingleRecord(note);
                    break;
            }
/*
            if(entryType == 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
                String currentDateandTime = sdf.format(new Date());
                ResultEntity enterEntity = new ResultEntity();
                enterEntity.setTestResult(savedScore);
                enterEntity.setDate(currentDateandTime);
                enterEntity.setTest(testPosition);


                //Now access all the methods defined in DaoAccess with sampleDatabase object

                Database.scoreDao().insertOnlySingleRecord(enterEntity);
            }if(entryType==1){

                NoteDatabase.noteDao().insertOnlySingleRecord(note);
            }

            if (entryType==2){
                Database.scoreDao().insertOnlySingleRecord(resultEntity);
                NoteDatabase.noteDao().insertOnlySingleRecord(note);

            }
*/


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            //     pastResultAdapter.notifyDataSetChanged();
            new populateView();


        }
    }
    private class populateView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Now access all the methods defined in DaoAccess with sampleDatabase object

            //  if (resultEntityArrayList!=null) {

            //     resultEntityArrayList.clear();
         dataSet = (ArrayList<ResultEntity>) Database.scoreDao().getAll();
            noteEntityArrayList = (ArrayList<NoteEntity>) NoteDatabase.noteDao().getAll();
            //   }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
/*
            layoutManager3 = new LinearLayoutManager(context);
            layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);


            savedRecyclerView.setLayoutManager(layoutManager3);
            pastResultAdapter=new PastResultAdapter(resultEntityArrayList);
            savedRecyclerView.setAdapter(pastResultAdapter);
            pastResultAdapter.notifyDataSetChanged();
*/
            //To after addition operation here.
        }
    }


    class deleteView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Database.scoreDao().delete( dataSet.get(swipedPosition));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);



        dataSet.remove(swipedPosition);
           // pastResultAdapter.notifyDataSetChanged();


        }

    }

    class findResultsAsync extends AsyncTask<String,Void,ResultEntity>{


        @Override
        protected ResultEntity doInBackground(String... strings) {

        noteResult =Database.scoreDao().getResult(strings[0]);

            return null;
        }

    }

    public void deleteResult(int position){

      swipedPosition=position;
        new deleteView().execute();
    }


    public void saveResult(int position,int score,String resultString){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
        String currentDateandTime = sdf.format(new Date());

       enterEntity = new ResultEntity();
        enterEntity.setTestResult(score);
        enterEntity.setDate(currentDateandTime);
        enterEntity.setTest(position);
        enterEntity.setNote(0);
        enterEntity.setAnswers(resultString);

       //testPosition=position;
       // savedScore=score;
        entryType=0;
new DatabaseAsync().execute();

    }

    protected void saveNote(NoteEntity noteEntity){
       // String name,String content,String path
        context=context;
        entryType=1;
note=noteEntity;
       // name=name;
       // content=content;
       // path=path;
        new DatabaseAsync().execute();
    }

    public void saveBoth(NoteEntity noteEntity, ResultEntity results) {

      entryType=2;
        note=noteEntity;
        resultEntity=results;
        new DatabaseAsync().execute();
    }
    public void getNoteResults(String date){
        new findResultsAsync().execute(date);
     mListener.sendResults(noteResult);

    }


    public interface MainRepositoryListener {


        void sendResults(ResultEntity resultEntity);


        void onFragmentInteraction(Uri uri);
    }
}
