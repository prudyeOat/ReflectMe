package teamwork.reflectme.data;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import teamwork.reflectme.R;
import teamwork.reflectme.model.ResultEntity;

public class PastResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerViewClickListener mListener;
    private List<ResultEntity> entities;
    private List<TextView> items;
    private int backgroundResource;
    private String diagnosisString;
    private RowViewHolder rowHolder;


    public PastResultAdapter(List<ResultEntity> entities1) {

        this.entities=entities1;
        this.items = new ArrayList<>();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.past_result, parent, false);
        return new RowViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //holder.name
        if (holder instanceof RowViewHolder) {

          rowHolder = (RowViewHolder) holder;

            if (entities!= null) {


                rowHolder.date.setText(entities.get(position).getDate());
                //Get arraylist of answers and compute a score
                rowHolder.score.setText(String.valueOf(entities.get(position).getTestResult()));

                if (entities.get(position).getTest() == 0) {
                    rowHolder.diagnosis.setText(getDepressionDiagnosis(entities.get(position).getTestResult()));

                }
                if (entities.get(position).getTest() == 1) {

                    rowHolder.diagnosis.setText(getAnxietyDiagnosis(entities.get(position).getTestResult()));
                }

                ((RowViewHolder) holder).relativeLayout.setBackgroundResource(backgroundResource);
                ;

            }

            rowHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });



            //set values of data here
        }

    }
    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private TextView diagnosis,score,date;
        private ImageView image;
        public LinearLayout relativeLayout;


        RowViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);


            mListener = listener;
            diagnosis = (TextView) v.findViewById(R.id.diagnosis_text);
           score = (TextView) v.findViewById(R.id.result_text);
            date= (TextView) v.findViewById(R.id.date_text);
            relativeLayout=v.findViewById(R.id.rel);
            v.setOnClickListener(this);
/*
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeAllWhite();
                   // items.add(rowHolder.name);
                   // name.setBackgroundColor(Color.parseColor("#d5d5d5"));



                }

            });
*/
        }

        @Override
        public void onClick(View view) {
            //  mListener.onClick(view, getAdapterPosition(),getOldPosition());
            // view.setBackgroundResource(R.drawable.circle_background_dark);
            //  name.setTextColor(Color.parseColor("#ffffff"));


        }


    }

    @Override
    public int getItemCount() {

        if (entities != null)
            return entities.size();
        else return 0;

    }
    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    private void makeAllWhite() {
        for(TextView item : items) {
            // item.setBackgroundColor(Color.parseColor("#ffffff"));
           // item.setBackgroundResource(R.drawable.circle_background_dark);
        }
    }
    private int computeTest(ArrayList<Integer> positions) {
        int score = 0;

        //switch (testPosition) {

        //    case 0:
                for (Integer item : positions)
                    score += item;
              //  break;
         //   case 1:
              //  for (Integer item : positions)
                 //   score += item;
            //    break;

       // }
        return score;
    }

    private String getDepressionDiagnosis(int score){



        if   (score < 10){
            diagnosisString = ("No Depression Likely");

            backgroundResource=R.drawable.green_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));

        }
        if (score > 9 & score < 18){
            diagnosisString = ("Possibly Mildly Depressed");

            backgroundResource=R.drawable.green_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
        }
        if (score > 17 & score < 22){
            diagnosisString = ("Borderline Depression");


            backgroundResource=R.drawable.brown_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
             }
        if (score > 21 & score < 36){
           diagnosisString = ("Mild-Moderate Depression");


            backgroundResource=R.drawable.pop_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
             }
        if (score > 35 & score < 54){

            diagnosisString = ("Moderate-Severe Depression");

            backgroundResource=R.drawable.orange_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
        }
        if (score > 53){

            diagnosisString = (" Severely Depressed");

            //set a public background value
            backgroundResource=R.drawable.red_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));

        }

        return diagnosisString;

    }
    private String getAnxietyDiagnosis(int score){


        if (score < 5){
            diagnosisString = ("minimal anxiety");
            backgroundResource=R.drawable.green_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
            //set a public background value
           // backgroundResource=R.drawable.gradient_purple;
        }
        if (score > 4 & score < 10){
            diagnosisString = (" mild anxiety");

            backgroundResource=R.drawable.brown_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
            //set a public background value
           // backgroundResource=R.drawable.circle_background_dark;
        }
        if (score > 9 & score < 15){
            diagnosisString = ("moderate anxiety");

            backgroundResource=R.drawable.orange_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
            //set a public background value
           // backgroundResource=R.drawable.circle_background_dark;
        }

        if (score > 14){

            diagnosisString = (" severe anxiety");

            backgroundResource=R.drawable.red_background;
            rowHolder.score.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.date.setTextColor(Color.parseColor("#ffffff"));
            rowHolder.diagnosis.setTextColor(Color.parseColor("#ffffff"));
            //set a public background value
            //backgroundResource=R.drawable.circle_background_dark;
        }

return diagnosisString;

    }

}