package teamwork.reflectme;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import teamwork.reflectme.data.ScoringAdapter;
import teamwork.reflectme.model.NoteEntity;
import teamwork.reflectme.model.Result;
import teamwork.reflectme.model.ResultEntity;


public class NoteBottomSheet extends BottomSheetDialogFragment {
    private NoteBottomSheetListener mListener;
    private ImageView noteImage;
    private    TextView noteNameTv,noteDateTv,noteContentTv,resultsTv,approxDiagnosisTv,scoreTv,disclaimerTv,scoringTv;
    private String date;
    private  File finalFile;
    private RecyclerView scoringRecyclerView;
    private LinearLayoutManager layoutManager;
    private ScoringAdapter scoringAdapter;
    private NoteEntity noteEntity;
    private int backgroundResource;
    private String diagnosisString;
    private ArrayList<String>anxietyQuestions;
    private ArrayList<String>depressionQuestions;
    private static NoteBottomSheet instance;

    public static synchronized NoteBottomSheet getInstance()
    {
        return instance;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //bottom sheet round corners can be obtained but the while background appears to remove that we need to add this.
        setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.note_bottom_sheet, container, false);
        instance=this;

       noteImage= view.findViewById(R.id.note_image);

       noteNameTv= view.findViewById(R.id.note_name);

       noteDateTv= view.findViewById(R.id.note_date);

       noteContentTv= view.findViewById(R.id.note_content);
      resultsTv= view.findViewById(R.id.see_results);
        scoringRecyclerView =  view.findViewById(R.id.scoring_recycler);

        approxDiagnosisTv= view.findViewById(R.id.approx_diagnosis);

        scoreTv= view.findViewById(R.id.final_score);
       disclaimerTv= view.findViewById(R.id.disclaimer);
        scoringTv= view.findViewById(R.id.scoring);
      resultsTv.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mListener.getNoteResults(date);


          }
      });

        return view;
    }
public void showResults(ResultEntity resultEntity){
    scoreTv.setVisibility(View.VISIBLE);
    approxDiagnosisTv.setVisibility(View.VISIBLE);
    disclaimerTv.setVisibility(View.VISIBLE);
 scoringTv.setVisibility(View.VISIBLE);
    scoreTv.setText(resultEntity.getTestResult());

    if (resultEntity.getTest()==0)
    {
    approxDiagnosisTv.setText(getDepressionDiagnosis(resultEntity.getTestResult()));
    }else  if(resultEntity.getTest()==1){
        approxDiagnosisTv.setText(getAnxietyDiagnosis(resultEntity.getTestResult()));
    }

    ArrayList<Result> resultStrings=new ArrayList<>();
    Result result=new Result();
    if(resultEntity.getTest()==0) {
        String answerString = resultEntity.getAnswers();
        for(int i=0; i>18; i++){

            String answer= String.valueOf(answerString.charAt(i));
            int score=Integer.valueOf(answer);
            result.setQuestion(getDepressionTest().get(i));
            result.setPosition(score);

            resultStrings.add(result);



        }
    }else if(resultEntity.getTest()==1){
        String answerString = resultEntity.getAnswers();
        for(int i=0; i>7; i++){
            String answer= String.valueOf(answerString.charAt(i));
            int score=Integer.valueOf(answer);

            result.setQuestion(getDepressionTest().get(i));
            result.setPosition(score);

            resultStrings.add(result);


        }
    }




    layoutManager = new LinearLayoutManager(getActivity());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    scoringRecyclerView.setLayoutManager(layoutManager);
    scoringAdapter=new ScoringAdapter(resultStrings);
    scoringRecyclerView .setAdapter( scoringAdapter);

}
   public void  setNoteViews(NoteEntity note){

         //noteImage.setImageBitmap(imagePath);
       noteNameTv.setText(note.getName());
       noteDateTv.setText(note.getDate());
       noteContentTv.setText(note.getContent());

       finalFile = new File(note.getImagePath());

       date=note.getDate();

     //  noteImage.setImageBitmap(finalFile);

/*
       GlideApp
               .with(getActivity())
               .load(finalFile)
               .into(noteImage);
*/
   }

    public interface NoteBottomSheetListener {

        void onNoteChanged(int position);
        void getNoteResults(String date);
        //   void inflateReportFragment();

        void onFragmentInteraction(Uri uri);
    }


    private String getDepressionDiagnosis(int score){



        if   (score < 10){
            diagnosisString = ("No Depression Likely");




        }
        if (score > 9 & score < 18){
            diagnosisString = ("Possibly Mildly Depressed");


        }
        if (score > 17 & score < 22){
            diagnosisString = ("Borderline Depression");


        }
        if (score > 21 & score < 36){
            diagnosisString = ("Mild-Moderate Depression");

        }
        if (score > 35 & score < 54){

            diagnosisString = ("Moderate-Severe Depression");

        }
        if (score > 53){

            diagnosisString = (" Severely Depressed");


        }

        return diagnosisString;

    }
    private String getAnxietyDiagnosis(int score){


        if (score < 5){
            diagnosisString = ("minimal anxiety");

        }
        if (score > 4 & score < 10){
            diagnosisString = (" mild anxiety");


        }
        if (score > 9 & score < 15){
            diagnosisString = ("moderate anxiety");


        }

        if (score > 14){

            diagnosisString = (" severe anxiety");

        }

        return diagnosisString;

    }
    public ArrayList<String> getAnxietyTest() {
        anxietyQuestions=new ArrayList<>();
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

        depressionQuestions=new ArrayList<>();

        depressionQuestions.add("1. I do things slowly.");
        depressionQuestions.add("2. My future seems hopeless.");
        depressionQuestions.add("3. It is hard for me to concentrate on reading.");
        depressionQuestions.add("4. The pleasure and joy has gone out of my life.");
        depressionQuestions.add("5. I have difficulty making decisions.");
        depressionQuestions.add("6. I have lost interest in aspects of my life that used to be important to me.");
        depressionQuestions.add("7. I feel sad, blue, and unhappy.");
        depressionQuestions.add( "8. I am agitated and keep moving around.");
        depressionQuestions.add( "9. I feel fatigued.");
        depressionQuestions.add( "10. It takes great effort for me to do simple things.");
        depressionQuestions.add("11. I feel that I am a guilty person who deserves to be punished.");
        depressionQuestions.add("12. I feel like a failure.");
        depressionQuestions.add("13. I feel lifeless more dead than alive.");
        depressionQuestions.add("14. I’m getting too much, too little or not enough restful sleep.");
        depressionQuestions.add( "15. I spend time thinking about HOW I might kill myself.");
        depressionQuestions.add( " 16. I feel trapped or caught.");
        depressionQuestions.add( "17. I feel depressed even when good things happen to me.");
        depressionQuestions.add( "18. Without trying to diet, I have lost or gained weight.");
        return depressionQuestions;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (NoteBottomSheetListener) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() + " must implement NoteBottomSheetlistener");

        }
    }
}
