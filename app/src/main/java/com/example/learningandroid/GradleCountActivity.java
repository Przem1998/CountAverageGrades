package com.example.learningandroid;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.media.MediaCodec.MetricsConstants.HEIGHT;


class GradeModel implements Parcelable {
    private String name;
    private int grade;


    protected GradeModel(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    protected GradeModel(Parcel in) {
        grade = in.readInt();
        name = in.readString();
    }


    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(grade);
        dest.writeString(name);
    }


    public static final Parcelable.Creator<GradeModel> CREATOR
            = new Parcelable.Creator<GradeModel>() {
        public GradeModel createFromParcel(Parcel in) {
            return new GradeModel(in);
        }


        public GradeModel[] newArray(int size) {
            return new GradeModel[size];
        }
    };
}


public class GradleCountActivity extends AppCompatActivity {

    private static final String STORE_GRADES="STORE_GRADES";
    private static final String DELIVER_AVG_GRADE = "AVG_GRADE";

    private static final int PICK_UP_NUMBER_GRADE_KEY=2;
    private static final String PICK_UP_NUMBER_GRADE="GRADE";

    private ArrayList<GradeModel> mGradeList;
    private int numberGrades;
    private String[] subjects;

    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private InteractiveArrayAdapter interactiveArrayAdapter;
    private Button mCountAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradle_count);
        Bundle bundle = getIntent().getExtras();
        mCountAvg = (Button) findViewById(R.id.countAVG);
        numberGrades = bundle.getInt(PICK_UP_NUMBER_GRADE, 0);
        subjects = getResources().getStringArray(R.array.subjects);
        mGradeList = new ArrayList<GradeModel>();


        if (savedInstanceState == null) {
            initListGrades(numberGrades);

        } else {
            mGradeList = savedInstanceState.getParcelableArrayList(STORE_GRADES);
        }
        recyclerView = findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(this);

        interactiveArrayAdapter = new InteractiveArrayAdapter(this, mGradeList);
        recyclerView.setAdapter(interactiveArrayAdapter);
        recyclerView.setLayoutManager(manager);


        mCountAvg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double avg = 0;
                for (int i = 0; i < numberGrades; i++) {
                    avg += mGradeList.get(i).getGrade();
                }
                avg /= numberGrades;
                Bundle bundle1 = new Bundle();
                bundle1.putDouble(DELIVER_AVG_GRADE, avg);
                Intent intent = new Intent();
                intent.putExtras(bundle1);
                setResult(PICK_UP_NUMBER_GRADE_KEY, intent);
                finish();
            }
        });
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(STORE_GRADES, mGradeList);

    }


    protected void initListGrades(int numberGrades) {
        mGradeList.clear();
        for (int i = 0; i < numberGrades; i++) {
            mGradeList.add(new GradeModel(subjects[i], 2));
        }
    }
}

