package teamwork.reflectme.data;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import teamwork.reflectme.R;
import teamwork.reflectme.model.Result;

public class ScoringAdapter extends  RecyclerView.Adapter<ScoringAdapter.AnswerPostHolder> {

    private ArrayList<Result> answerArraylist;
    private Context context;

    public ScoringAdapter(ArrayList<Result> answerArraylist) {
        this.answerArraylist =  answerArraylist;
    }

    @NonNull
    @Override
    public AnswerPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent, false);
        AnswerPostHolder itemViewHolder = new AnswerPostHolder(view);
        context = parent.getContext();

        itemViewHolder.setOnClickListener(new AnswerPostHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {




            }
        });

        return itemViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull AnswerPostHolder holder, int position) {

        holder.question.setText(answerArraylist.get(position).getQuestion());
        holder.score.setText(String.valueOf(answerArraylist.get(position).getPosition()));
    }

    @Override
    public int getItemCount() {
        return answerArraylist.size();
    }

    public static class AnswerPostHolder extends RecyclerView.ViewHolder{
        private TextView question, score;

        public AnswerPostHolder(View v) {
            super(v);

            question=v.findViewById(R.id.question);
            score=v.findViewById(R.id.position);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.onItemLongClick(v, getAdapterPosition());
                    return true;
                }
            });

        }

        private AnswerPostHolder.ClickListener mClickListener;

        //Interface to send callbacks...
        public interface ClickListener{
            public void onItemClick(View view, int position);
            public void onItemLongClick(View view, int position);
        }

        public void setOnClickListener(AnswerPostHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }
    }
}

