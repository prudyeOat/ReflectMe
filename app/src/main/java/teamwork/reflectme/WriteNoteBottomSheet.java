package teamwork.reflectme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import teamwork.reflectme.model.NoteEntity;
import teamwork.reflectme.model.ResultEntity;

import static android.app.Activity.RESULT_OK;

public class WriteNoteBottomSheet extends BottomSheetDialogFragment {
    private WriteNoteBottomSheetListener mListener;
 private TextView addImageTV;
 private ImageView noteImage;
 private EditText noteNameEdt,noteContentEdt;
 private Button button;
 private String title,content;
 private NoteEntity noteEntity;
 private int noteType=0;
    private static int RESULT_LOAD_IMG = 100;
    private Uri returnUri;
 private int saveScore,testPosition;
 private String resultString;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.write_note_bottom_sheet, container, false);

       addImageTV=view.findViewById(R.id.add_image);
       noteImage=view.findViewById(R.id.image);
       noteNameEdt=view.findViewById(R.id.edt_title);
       noteContentEdt=view.findViewById(R.id.edt_content);

       button=view.findViewById(R.id.save_note);

addImageTV.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMG);
    }
});
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               title=noteNameEdt.getText().toString();
               content=noteContentEdt.getText().toString();
               //typestring=rb.getText().toString();
               if (content.trim().equals("")) {
                   Toast.makeText(getActivity(), "Please write note body ", Toast.LENGTH_LONG).show();
                   return;}
               if (title.trim().equals("")) {
                   Toast.makeText(getActivity(), "Your note has no title", Toast.LENGTH_LONG).show();
                   return;
               }
               if(!content.isEmpty() && !title.isEmpty()){


                   button.setEnabled(false);

                 saveNote();


               }
           }
       });


        return view;
    }

   private void saveNote (){

        if(noteType==0){
       SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
       String currentDateandTime = sdf.format(new Date());
       noteEntity=new NoteEntity();
       noteEntity.setName(title);
       noteEntity.setContent(content);
       noteEntity.setDate(currentDateandTime);
       noteEntity.setTest(0);

       mListener.passNoteData(noteEntity);

        }else  if (noteType==1){

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
            String currentDateandTime = sdf.format(new Date());
            noteEntity=new NoteEntity();
            noteEntity.setName(title);
            noteEntity.setContent(content);
            noteEntity.setDate(currentDateandTime);
            noteEntity.setTest(0);

            ResultEntity resultEntity=new ResultEntity();
            resultEntity.setTest(testPosition);
            resultEntity.setTestResult(saveScore);
            resultEntity.setDate(currentDateandTime);
            resultEntity.setAnswers(resultString);
            resultEntity.setNote(1);

            mListener.passCombinedData(noteEntity,resultEntity);
        }

   }

    public void testNote(int testPosition, int saveScore, String resultString) {
        noteType=1;
        testPosition=testPosition;
        saveScore=saveScore;
        resultString=resultString;


    }

    public interface WriteNoteBottomSheetListener {

        void passNoteData(NoteEntity noteEntity);
        //   void inflateReportFragment();
        void passCombinedData(NoteEntity noteEntity, ResultEntity resultEntity);
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                // Uri uri = data.getData();
                returnUri = data.getData();
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                //get path to image on phone
                returnUri.getPath();
               noteImage.setVisibility(View.VISIBLE);
             noteImage.setImageBitmap(bitmapImage);

            }
        }



        catch(
                Exception e)

        {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (WriteNoteBottomSheetListener) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString() + " must implement  WriteNoteBottomSheetlistener");

        }
    }
}
