package teamwork.reflectme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import teamwork.reflectme.data.LandingAdapter;
import teamwork.reflectme.data.NoteAdapter;
import teamwork.reflectme.data.PastResultAdapter;
import teamwork.reflectme.data.RecyclerViewAdapter;
import teamwork.reflectme.data.ScoringAdapter;
import teamwork.reflectme.model.NoteEntity;
import teamwork.reflectme.model.Result;
import teamwork.reflectme.model.ResultEntity;
import teamwork.reflectme.roomHelperClasses.ScoreDatabase;

public class MainActivity extends AppCompatActivity implements NoteBottomSheet.NoteBottomSheetListener, WriteNoteBottomSheet.WriteNoteBottomSheetListener, MainRepository.MainRepositoryListener
{

    private String mParam1;
    private String mParam2;
    private LandingAdapter stringAdapter;
    private LinearLayoutManager layoutManager,layoutManager1,layoutManager2,layoutManager3,noteLayoutManager;
    private RecyclerView mRecyclerView,answerRecyclerView,scoringRecyclerView,savedRecyclerView,noteRecyclerView;
    private ArrayList<String> situations;
    private ArrayList<Integer> finishedArraylist;
    private   ArrayList<ResultEntity>   resultEntityArrayList;
    private ArrayList<String> strings;
    private String testType;
    private int testPosition,testScore;
    private String diagnosisString;
    private View bottomSheet;
    private ScoringAdapter scoringAdapter;
    private ArrayList<Result> resultArrayList;
    RecyclerViewAdapter.RecyclerViewClickListener recyclerViewClicklistener;
    NoteAdapter.NoteAdapterClickListener noteAdapterClickListener;
    private int row_index,lastPosition,nextPosition,score;
    private ArrayList<String> strings1,depressionQuestions,depAnswers,anxietyQuestions,anxietyAnswers;
    private BottomSheetBehavior bottomSheetBehavior;
    private Context context;
    private ImageView userImage;
    private ScoreDatabase Database;
    private ResultEntity resultEntity;
    private ResultEntity enterEntity;
    private TextView userDisplayname,depressionScoreTv,anxietyScoreTv;
    private static int RESULT_LOAD_IMG = 100;
    private Uri returnUri;
    private ArrayList<Integer> scoreKeeperArrayList;
    private TextView questionTv,approxDiagnosis,results,nextTv,disclaimerTv,seeMoreTv,scoringTv,finalScoreTv,addNoteTv;
    private RecyclerViewAdapter  recyclerAdapter;
    private PastResultAdapter pastResultAdapter;
    private NoteAdapter noteAdapter;
    private int savedPosition,swipedPosition,saveScore;
    String resultString;
    private MainActivityViewModel mainActivityViewModel;



    public void setViews( int position,ArrayList<Integer> scoreKeeperArrayList,int score){
        scoringRecyclerView.setVisibility(View.VISIBLE);
        scoringTv.setVisibility(View.VISIBLE);

        approxDiagnosis.setVisibility(View.VISIBLE);
        results.setVisibility(View.VISIBLE);
        finalScoreTv.setVisibility(View.VISIBLE);

        disclaimerTv.setVisibility(View.GONE);
        seeMoreTv.setVisibility(View.GONE);



        switch (position) {

            case 0:


                for (int i = 0; i < mainActivityViewModel.getDepressionTest().size(); i++)
                {


                    Result result= new Result();
                    result.setPosition(scoreKeeperArrayList.get(i));
                    result.setQuestion(mainActivityViewModel.getDepressionTest().get(i));

                    resultArrayList.add(result);

                }
                scoringTv.setText("Scoring: " +
                        '\n' +
                        " Not at all : 0 points " + '\n' +
                        " Just a little : 1 point " + '\n' +
                        " Somewhat : 2 points " + '\n' +
                        " Moderately : 3 points " + '\n' +
                        " Quite a lot : 4 points " + '\n' +
                        " Very much : 5 points " +
                        '\n' +
                        " "+
                        '\n'+
                        "Screening test scoring ranges: " +
                        '\n' +
                        " "+
                        '\n' +
                        "No Depression Likely : 0-9  " + '\n' +
                        " Possibly Mildly Depressed : 10-17 " + '\n' +
                        " Borderline Depression : 18-21 " + '\n' +
                        " Mild-Moderate Depression : 22-35 " + '\n' +
                        " Moderate-Severe Depression : 36-53 " + '\n' +
                        " Severely Depressed : 54 and up");
                scoringRecyclerView.setLayoutManager(layoutManager2);

                scoringAdapter=new ScoringAdapter(resultArrayList);

                scoringRecyclerView.setAdapter(scoringAdapter);
                finalScoreTv.setText(String.valueOf(score));

                break;
            case 1:
                for (int i = 0; i < mainActivityViewModel.getAnxietyTest().size(); i++)
                {


                    Result result= new Result();
                    result.setPosition(scoreKeeperArrayList.get(i));
                    result.setQuestion(mainActivityViewModel.getAnxietyTest().get(i));

                    resultArrayList.add(result);

                }


                scoringRecyclerView.setLayoutManager(layoutManager2);

                scoringAdapter=new ScoringAdapter(resultArrayList);

                scoringRecyclerView.setAdapter(scoringAdapter);

                scoringTv.setText("        Scoring: " +
                        '\n' +
                        " Not at all : 0 points" +  '\n'+
                        " Several days : 1 point " + '\n'+
                        " More than half the days : 2 points " + '\n'+
                        "Nearly everyday : 3 points  " +
                        '\n' + " "+
                        '\n'+
                        "Screening test scoring ranges: " +
                        '\n' + " " + '\n'+
                        " Minimal anxiety : 0-4 " +'\n'+
                        " Mild anxiety : 5-9" + '\n'+
                        "Moderate anxiety : 10-14 " + '\n'+
                        " Severe anxiety : 14 and up");

                finalScoreTv.setText(String.valueOf(score));
                break;

        }
        //set questions and annswers here
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context=MainActivity.this;
       // mRepo=new MainRepository(getApplication());
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();


        mainActivityViewModel.getNotes().observe(this, new Observer<List<NoteEntity>>() {

            @Override
            public void onChanged(List<NoteEntity> noteEntityList) {
                noteAdapter.notifyDataSetChanged();
            }
        });

        mainActivityViewModel.getResults().observe(this, new Observer<List<ResultEntity>>() {
            @Override
            public void onChanged(List<ResultEntity> resultEntityList) {
                pastResultAdapter.notifyDataSetChanged();
            }
        });

        resultArrayList=new ArrayList<>();
        resultEntityArrayList=new ArrayList<>();

   /*

        addStoryImage.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMG);
            }
        });
*/
        mRecyclerView =  findViewById(R.id.test_recycler);
        scoringRecyclerView =  findViewById(R.id.scoring_recycler);
        savedRecyclerView = findViewById(R.id.result_recycler);
        answerRecyclerView =  findViewById(R.id.answer_recycler);
        noteRecyclerView =  findViewById(R.id.note_recycler);
        // mRecyclerView.setHasFixedSize(true);



        bottomSheet=findViewById(R.id.bottom_sheet);
        bottomSheetBehavior=BottomSheetBehavior.from(bottomSheet);
        // bottomSheetBehavior.setState(BottomSheetBehavior.);





        results=findViewById(R.id.result);
        approxDiagnosis=findViewById(R.id.approx_diagnosis);
        questionTv=findViewById(R.id.question);
        nextTv=findViewById(R.id.next);
        disclaimerTv=findViewById(R.id.disclaimer);
        seeMoreTv=findViewById(R.id.see_more);
        scoringTv=findViewById(R.id.scoring);
        finalScoreTv=findViewById(R.id.final_score);
       addNoteTv=findViewById(R.id.add_note);



addNoteTv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


      WriteNoteBottomSheet bottomSheetDialog=new WriteNoteBottomSheet();
        bottomSheetDialog.testNote(testPosition,saveScore,getResultString());
        bottomSheetDialog.show(getSupportFragmentManager(),"bottomsheet");

        scoreKeeperArrayList.clear();
    }
});
       // new populateView().execute();
        seeMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setViews(testPosition,scoreKeeperArrayList,testScore);
            }
        });
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scoreKeeperArrayList.add(row_index);
                setQuestion();
                //computeTest(row_index);
            }
        });
        //titleTv=findViewById(R.id.story_title);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

        layoutManager2 = new LinearLayoutManager(context);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        noteLayoutManager=new LinearLayoutManager(context);
        noteLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //    layoutManager3 = new LinearLayoutManager(getActivity());
        //   layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);


        //     savedRecyclerView.setLayoutManager(layoutManager3);

        mRecyclerView.setLayoutManager(layoutManager);
        noteRecyclerView.setLayoutManager(noteLayoutManager);
     //   handpickedRecyclerView.setLayoutManager(layoutManager1);

        //  answerRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));


        LandingAdapter.RecyclerViewClickListener listener = (View v, int position ) -> {


            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);

            testPosition=position;
            testType=mainActivityViewModel.getTestType().get(position);

            scoreKeeperArrayList= new ArrayList();
            //  bottomSheet.setVisibility(View.VISIBLE);

            lastPosition=0;



            startTest(position);


        };
        recyclerViewClicklistener = (View v, int position ) -> {
            // Toast.makeText(getContext(), "Getting " +  getStrings().get(position) + " videos", Toast.LENGTH_SHORT).show();
            row_index=position;



        };

       noteAdapterClickListener = (View v, NoteEntity note) -> {
            // Toast.makeText(getContext(), "Getting " +  getStrings().get(position) + " videos", Toast.LENGTH_SHORT).show();

           NoteBottomSheet bottomSheetDialog=new NoteBottomSheet();
           bottomSheetDialog.setNoteViews(note);
           bottomSheetDialog.show(getSupportFragmentManager(),"bottomsheet");




        };

        // recyclerAdapter=new RecyclerViewAdapter(recyclerViewClicklistener,getMultipleChoice());
        stringAdapter=new LandingAdapter(listener,mainActivityViewModel.getTestType());
        mRecyclerView.setAdapter(stringAdapter);
        noteAdapter=new NoteAdapter(mainActivityViewModel.getNotes().getValue());
        noteRecyclerView.setAdapter(noteAdapter);
        pastResultAdapter=new PastResultAdapter(mainActivityViewModel.getResults().getValue());
        //    pastResultAdapter=new PastResultAdapter(resultEntityArrayList);
           savedRecyclerView.setAdapter(pastResultAdapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(savedRecyclerView);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if(!scoreKeeperArrayList.isEmpty()) {
                          //  mRepo.saveResult(testPosition, saveScore, getResultString());
                            mainActivityViewModel.saveResult(testPosition,saveScore,getResultString());
                        }
                        scoreKeeperArrayList.clear();
                        resultArrayList.clear();


                        break;

                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        // setViews();
                        startTest(testPosition);
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                WriteNoteBottomSheet bottomSheetDialog=new WriteNoteBottomSheet();
                bottomSheetDialog.show(getSupportFragmentManager(),"bottomsheet");
            }
        });

        initAdapter();
    }
private String getResultString(){

    for(int item:scoreKeeperArrayList){

        String character= String.valueOf(item);
        resultString+=character;
    }
    return resultString;
}
    private void initAdapter(){


        layoutManager3 = new LinearLayoutManager(context);
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);


        savedRecyclerView.setLayoutManager(layoutManager3);
        pastResultAdapter=new PastResultAdapter(mainActivityViewModel.getResults().getValue());
        savedRecyclerView.setAdapter(pastResultAdapter);
        pastResultAdapter.notifyDataSetChanged();
    }

    private void startTest( int position){

        approxDiagnosis.setVisibility(View.GONE);
        results.setVisibility(View.GONE);
        disclaimerTv.setVisibility(View.GONE);
        seeMoreTv.setVisibility(View.GONE);
        finalScoreTv.setVisibility(View.GONE);
        scoringRecyclerView.setVisibility(View.GONE);
        scoringTv.setVisibility(View.GONE);


        questionTv.setVisibility(View.VISIBLE);
        answerRecyclerView.setVisibility(View.VISIBLE);
        nextTv.setVisibility(View.VISIBLE);

        switch (position) {

            case 0:

                //score=score;
                answerRecyclerView.setLayoutManager(new GridLayoutManager(context,3));
                recyclerAdapter=new RecyclerViewAdapter(recyclerViewClicklistener,mainActivityViewModel.getMultipleChoice());
                questionTv.setText(mainActivityViewModel.getDepressionTest().get(0));
                answerRecyclerView.setAdapter(recyclerAdapter);
                break;
            case 1:
                answerRecyclerView.setLayoutManager(new GridLayoutManager(context,2));
                recyclerAdapter=new RecyclerViewAdapter(recyclerViewClicklistener,mainActivityViewModel.getAnxietChoices());
                questionTv.setText(mainActivityViewModel.getAnxietyTest().get(0));
                answerRecyclerView.setAdapter(recyclerAdapter);
                break;

        }

    }






    private  void setQuestion(){
        lastPosition=lastPosition+1;
        switch (testPosition) {

            case 0:
                if (lastPosition < mainActivityViewModel.getDepressionTest().size())
                    questionTv.setText(mainActivityViewModel.getDepressionTest().get(lastPosition));
                else
                    getDepressionDiagnosis(mainActivityViewModel.computeTest(scoreKeeperArrayList,testPosition));

                break;
            case 1:
                if (lastPosition < mainActivityViewModel.getAnxietyTest().size())
                    questionTv.setText(mainActivityViewModel.getAnxietyTest().get(lastPosition));
                else
                    getAnxietyDiagnosis(mainActivityViewModel.computeTest(scoreKeeperArrayList,testPosition));
                break;

        }



    }

    private void getDepressionDiagnosis(int score){



        if (score < 10){
            diagnosisString = ("No Depression Likely");
            setlastViews(score,diagnosisString);}
        if (score > 9 & score < 18){
            diagnosisString = ("Possibly Mildly Depressed");
            setlastViews(score,diagnosisString);}
        if (score > 17 & score < 22){
            diagnosisString = ("Borderline Depression");
            setlastViews(score,diagnosisString);}
        if (score > 21 & score < 36){
            diagnosisString = ("Mild-Moderate Depression");
            setlastViews(score,diagnosisString);}
        if (score > 35 & score < 54){
            setlastViews(score,diagnosisString);
            diagnosisString = ("Moderate-Severe Depression");
            setlastViews(score,diagnosisString);}
        if (score > 53){
            System.out.println(" Severely Depressed");
            diagnosisString = (" Severely Depressed");
            setlastViews(score,diagnosisString);}


        saveScore=score;
        //add note to test and call mrepo to save the object

        //new DatabaseAsync().execute();

    }
    private void getAnxietyDiagnosis(int score){

        if (score < 5){
            diagnosisString = ("minimal anxiety");
            setlastViews(score,diagnosisString);}
        if (score > 4 & score < 10){
            diagnosisString = (" mild anxiety");
            setlastViews(score,diagnosisString);}
        if (score > 9 & score < 15){
            diagnosisString = ("moderate anxiety");
            setlastViews(score,diagnosisString);}

        if (score > 14){

            diagnosisString = (" severe anxiety");
            setlastViews(score,diagnosisString);}

        saveScore=score;
        //   new DatabaseAsync().execute();




    }
    private void setlastViews(int score,String diagnosis){
        approxDiagnosis.setVisibility(View.VISIBLE);
        results.setVisibility(View.VISIBLE);
        disclaimerTv.setVisibility(View.VISIBLE);
        seeMoreTv.setVisibility(View.VISIBLE);

        questionTv.setVisibility(View.GONE);
        //questionTv.setText(getString(R.string.disclaimer_text));
        answerRecyclerView.setVisibility(View.GONE);

        results.setText( String.valueOf(score)
        );

        approxDiagnosis.setText(diagnosis);

        nextTv.setVisibility(View.GONE);

        testScore=score;
        // approxDiagnosis.setText();

        saveScore=score;
       // new DatabaseAsync().execute();

        //call respository with save score and testposition
        //mRepo.saveResult(testPosition,saveScore);

    }

    private ItemTouchHelper.Callback createHelperCallback() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.START | ItemTouchHelper.END,
                ItemTouchHelper.START | ItemTouchHelper.END) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {

                // swipedPosition = viewHolder.getAdapterPosition();


                return true;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                swipedPosition = viewHolder.getAdapterPosition();


              //  new deleteView().execute();
               // mRepo.deleteResult(swipedPosition);

                mainActivityViewModel.deleteResult(swipedPosition);



            }
        };
        return simpleItemTouchCallback;
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onNoteChanged(int position) {

    }

    @Override
    public void getNoteResults(String date) {
       // mRepo.getNoteResults(date);
        mainActivityViewModel.getNoteResults(date);
    }



    @Override
    public void passNoteData(NoteEntity noteEntity) {
    //mRepo.saveNote(noteEntity);
    mainActivityViewModel.saveNote(noteEntity);
    }

    @Override
    public void passCombinedData(NoteEntity noteEntity, ResultEntity resultEntity) {
       // mRepo.saveBoth(noteEntity,resultEntity);
        mainActivityViewModel.saveBoth(noteEntity,resultEntity);
    }

    @Override
    public void sendResults(ResultEntity resultEntity) {
        NoteBottomSheet.getInstance().showResults(resultEntity);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Override this method in the activity that hosts the Fragment and call super
        // in order to receive the result inside onActivityResult from the fragment.
        super.onActivityResult(requestCode, resultCode, data);
    }
}
