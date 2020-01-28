package teamwork.reflectme.data;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import teamwork.reflectme.R;
import teamwork.reflectme.model.NoteEntity;


public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  NoteAdapterClickListener mListener;
    private Context context;
    private List<NoteEntity> notes=new ArrayList<>();
    private List<TextView> items;
    private int row_index;
    // private List<Data> mDataset = new ArrayList<>();

    public NoteAdapter(List<NoteEntity> notes) {
       // mListener = listener;
        this.notes=notes;
        this.items = new ArrayList<>();
    }
    /*
        public void updateData(List<Data> dataset) {
            mDataset.clear();
            mDataset.addAll(dataset);
            notifyDataSetChanged();
        }
    */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        return new RowViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //holder.name
        if (holder instanceof RowViewHolder) {

            RowViewHolder rowHolder = (RowViewHolder) holder;

            rowHolder.name.setText(notes.get(position).getName());
            rowHolder.date.setText(notes.get(position).getDate());




            rowHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   row_index=position;
                  //  notifyDataSetChanged();
                    // mListener.onClick();
                    mListener.onClick(view, notes.get(position));


                }
            });
            if(position % 5 == 0){
                rowHolder.cardView.setCardBackgroundColor(Color.parseColor("#480674"));

               // rowHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.colorSee), android.graphics.PorterDuff.Mode.MULTIPLY);
            }else if(position % 5 == 1){
                rowHolder.cardView.setCardBackgroundColor(Color.parseColor("#F71414"));
                //rowHolder.image.setImageResource(R.drawable.flower);
             //   rowHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
            }else if(position % 5 == 2){

                rowHolder.cardView.setCardBackgroundColor(Color.parseColor("#F38D24"));
                //rowHolder.image.setImageResource(R.drawable.rosegreen);
             //   rowHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.ColorPinkNice), android.graphics.PorterDuff.Mode.MULTIPLY);
            }else if(position % 5 == 3){
                rowHolder.cardView.setCardBackgroundColor(Color.parseColor("#D81B60"));
               // rowHolder.image.setImageResource(R.drawable.forest);
              //  rowHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.MULTIPLY);
            }else if(position % 5 == 4){
                rowHolder.cardView.setCardBackgroundColor(Color.parseColor("#008577"));
               // rowHolder.image.setImageResource(R.drawable.woman);
              //  rowHolder.image.setColorFilter(ContextCompat.getColor(context, R.color.ColorSplash), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            if(row_index==position){
                //  ((RowViewHolder) holder).cardView.setBackgroundColor(Color.parseColor("#567845"));
                // ((RowViewHolder) holder).cardView.setCardBackgroundColor(Color.parseColor("#567845"));\
            //    rowHolder.name.setBackgroundResource(R.drawable.textview_background);
                rowHolder.name.setTextColor(Color.parseColor("#480674"));

                //   holder.tv1.setTextColor(Color.parseColor("#ffffff"));
            }
            else {
                //((RowViewHolder) holder).cardView.setBackgroundColor(Color.parseColor("#ffffff"));
                //  rowHolder.name.setBackgroundResource(R.drawable.rounded_corner);
             //   rowHolder.name.setBackgroundResource(R.drawable.white_background);
                rowHolder.name.setTextColor(Color.parseColor("#4e4848"));
                //holder.tv1.setTextColor(Color.parseColor("#000000"));
            }

            //set values of data here
        }

    }
    public class RowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  NoteAdapterClickListener mListener;
        private TextView name,date;
        private ImageView image;
        public CardView cardView;


        RowViewHolder(View v,  NoteAdapterClickListener listener) {
            super(v);


            mListener = listener;
            name = (TextView) v.findViewById(R.id.note_name);
           date = (TextView) v.findViewById(R.id.note_date);
            cardView=v.findViewById(R.id.cardView_row);
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

        if (notes != null)
            return notes.size();
        else return 0;
    }

    public interface  NoteAdapterClickListener {

        void onClick(View view, NoteEntity position);
    }

    private void makeAllWhite() {
        for(TextView item : items) {
            // item.setBackgroundColor(Color.parseColor("#ffffff"));
            // item.setBackgroundResource(R.drawable.circle_background_dark);
        }
    }
}