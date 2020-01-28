package teamwork.reflectme;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import teamwork.reflectme.model.NoteEntity;
import teamwork.reflectme.model.ResultEntity;


public class MainActivityViewModel extends ViewModel implements  MainRepository.MainRepositoryListener{


    private MutableLiveData<List<ResultEntity>> mResults = new MutableLiveData<>();
    private MutableLiveData<List<NoteEntity>> mNotes = new MutableLiveData<>();;
    private ArrayList<String> anxietyAnswers;
    private ArrayList<String> depAnswers;
    private ArrayList<String> anxietyQuestions;
    private ArrayList<String> depressionQuestions;
    private ArrayList<String> situations;
    private MainRepository mRepo;


    public MainActivityViewModel() {}

    public MainActivityViewModel(Application application) {
        //super();
        super();

        mRepo = new MainRepository(application);
        mResults = mRepo.getResults();
        mNotes = mRepo.getNotes();
        // context=application.getApplicationContext();
        // mText.setValue("This is notifications fragment");
    }
    public void init() {
        if (mResults != null) {
            return;

        }

//mRepo=MainRepository.getInstance((Application) context);


    }
    public LiveData<List<ResultEntity>> getResults() {
        return mResults;
    }

    public LiveData<List<NoteEntity>> getNotes() {
        return mNotes;
    }


    public int computeTest(ArrayList<Integer> positions, int testPosition) {
        int score = 0;
        //finishedArraylist=new ArrayList<>();
//finishedArraylist.addAll(positions);

        switch (testPosition) {

            case 0:
                for (Integer item : positions)
                    score += item;
                break;
            case 1:
                for (Integer item : positions)
                    score += item;
                break;

        }


        return score;
    }

    public ArrayList<String> getAnxietChoices() {
        anxietyAnswers = new ArrayList<>();
        anxietyAnswers.add("Not at all");
        anxietyAnswers.add("Several days");
        anxietyAnswers.add("More than half the days");
        anxietyAnswers.add("Nearly everyday");
        return anxietyAnswers;
    }

    public ArrayList<String> getMultipleChoice() {
        depAnswers = new ArrayList<>();
        depAnswers.add("Not at all");
        depAnswers.add("Just a little");
        depAnswers.add("Somewhat");
        depAnswers.add("Moderately");
        depAnswers.add("Quite a lot");
        depAnswers.add("Very much");
        return depAnswers;
    }

    public ArrayList<String> getAnxietyTest() {
        anxietyQuestions = new ArrayList<>();
        anxietyQuestions.add("1. Feeling nervous, anxious, or on edge");
        anxietyQuestions.add("2. Not being able to stop or control worrying");
        anxietyQuestions.add("3. Worrying too much about different things");
        anxietyQuestions.add("4. Trouble relaxing");
        anxietyQuestions.add("5. Being so restless that it is hard to sit still");
        anxietyQuestions.add("6. Becoming easily annoyed or irritable");
        anxietyQuestions.add("7. Feeling afraid, as if something awful might happen");


        return anxietyQuestions;
    }

    public ArrayList<String> getDepressionTest() {

        depressionQuestions = new ArrayList<>();

        depressionQuestions.add("1. I do things slowly.");
        depressionQuestions.add("2. My future seems hopeless.");
        depressionQuestions.add("3. It is hard for me to concentrate on reading.");
        depressionQuestions.add("4. The pleasure and joy has gone out of my life.");
        depressionQuestions.add("5. I have difficulty making decisions.");
        depressionQuestions.add("6. I have lost interest in aspects of my life that used to be important to me.");
        depressionQuestions.add("7. I feel sad, blue, and unhappy.");
        depressionQuestions.add("8. I am agitated and keep moving around.");
        depressionQuestions.add("9. I feel fatigued.");
        depressionQuestions.add("10. It takes great effort for me to do simple things.");
        depressionQuestions.add("11. I feel that I am a guilty person who deserves to be punished.");
        depressionQuestions.add("12. I feel like a failure.");
        depressionQuestions.add("13. I feel lifeless more dead than alive.");
        depressionQuestions.add("14. I’m getting too much, too little or not enough restful sleep.");
        depressionQuestions.add("15. I spend time thinking about HOW I might kill myself.");
        depressionQuestions.add(" 16. I feel trapped or caught.");
        depressionQuestions.add("17. I feel depressed even when good things happen to me.");
        depressionQuestions.add("18. Without trying to diet, I have lost or gained weight.");
        return depressionQuestions;
    }


    public ArrayList<String> getTestType() {

        situations = new ArrayList<String>();
        situations.add("Depression test");
        situations.add("Anxiety Test");
        //situations.add( "Eating disorder test");
        // situations.add( "Work health survey");
        // situations.add( "Addiction test");

        return situations;
    }

    public void deleteResult(int position) {

       // swipedPosition = position;

       mRepo.deleteResult(position);
       // new deleteView().execute();
    }


    public void saveResult(int position, int score, String resultString) {
/*
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
        entryType = 0;
        new DatabaseAsync().execute();
        */

        mRepo.saveResult(position,score,resultString);

    }

    protected void saveNote(NoteEntity noteEntity) {
        // String name,String content,String path
        /*
        context = context;
        entryType = 1;
        note = noteEntity;
        // name=name;
        // content=content;
        // path=path;
        new DatabaseAsync().execute();
        */
        mRepo.saveNote(noteEntity);
    }

    public void saveBoth(NoteEntity noteEntity, ResultEntity results) {
/*
        entryType = 2;
        note = noteEntity;
        resultEntity = results;
        new DatabaseAsync().execute();
        */
     //   ((MainActivity)getActivity()).startChronometer();
mRepo.saveBoth(noteEntity,results);
    }

    public void getNoteResults(String date) {
       // new findResultsAsync().execute(date);
      //  mListener.sendResults(noteResult);

        mRepo.getNoteResults(date);

    }

    @Override
    public void sendResults(ResultEntity resultEntity) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}