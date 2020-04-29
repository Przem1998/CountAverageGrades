package com.example.learningandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;



public class InteractiveArrayAdapter extends RecyclerView.Adapter<InteractiveArrayAdapter.GradeViewHolder> {
    private List<GradeModel> mGradeList;
    private LayoutInflater mPump;
    Context context;


    public InteractiveArrayAdapter(Activity context, List<GradeModel> gradeList) {
        mPump = context.getLayoutInflater();
        this.mGradeList = gradeList;
        this.context=context;
    }


    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = mPump.inflate(R.layout.gradle_elements, null);
        return new GradeViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        String mNameSubject = mGradeList.get(position).getName();
        holder.mNazwa_tv.setText(mNameSubject);
        int ocena=mGradeList.get(position).getGrade();
        switch(ocena){
            case 2:
                holder.mRB_2.setChecked(true);
                break;
            case 3:
                holder.mRB_3.setChecked(true);
                break;
            case 4:
                holder.mRB_4.setChecked(true);
                break;
            case 5:
                holder.mRB_5.setChecked(true);
                break;
        }
        holder.radioGroup.setTag(position);
    }
    @Override
    public int getItemCount() {
        return mGradeList.size();
    }

    public class GradeViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        RadioGroup radioGroup;
        RadioButton mRB_2;
        RadioButton mRB_3;
        RadioButton mRB_4;
        RadioButton mRB_5;
        TextView mNazwa_tv;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            mNazwa_tv = itemView.findViewById(R.id.tvNameGrade);
            mRB_2 = itemView.findViewById(R.id.radio2);
            mRB_3 = itemView.findViewById(R.id.radio3);
            mRB_4 = itemView.findViewById(R.id.radio4);
            mRB_5 = itemView.findViewById(R.id.radio5);
            radioGroup = itemView.findViewById(R.id.gradeGroup);
            radioGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group.getTag() != null) {
                int numer = (int) group.getTag();
                switch (checkedId) {
                    case R.id.radio2:
                        mGradeList.get(numer).setGrade(2);
                        break;
                    case R.id.radio3:
                        mGradeList.get(numer).setGrade(3);
                        break;
                    case R.id.radio4:
                        mGradeList.get(numer).setGrade(4);
                        break;
                    case R.id.radio5:
                        mGradeList.get(numer).setGrade(5);
                        break;
                }
            }
        }
    }

}
