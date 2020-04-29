package com.example.learningandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static  final  int KEY_NUMBER_GRADES=2;
    private static final String PICK_UP_AVG="AVG_GRADE";
    private static final String PUT_NUMBER_GRADES="GRADE";
    private final String TEXT_GRADE="SHOW_GRADE";

    private EditText editName;
    private EditText editSurname;
    private EditText editGrades;
    private Button sentResults;
    private TextView displayAvg;


   private boolean acceptRangeAss=true;
   private double getAvg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.editName);

        displayAvg=(TextView)findViewById(R.id.displayAvg);
        editName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editName.getText().toString().isEmpty()) {
                        editName.setError("Pole nie może być puste");
                    }
                }
            }
        });
        editSurname=(EditText) findViewById(R.id.editSurname);
        editSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editSurname.getText().toString().isEmpty()) {
                        editSurname.setError("Pole nie może być puste");
                    }
                }
            }
        });
        editGrades = (EditText) findViewById(R.id.editOceny);
        editGrades.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    int editAssInput=-1;
                    try {
                        editAssInput = Integer.parseInt(editGrades.getText().toString());
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Wprowadzono nieprawidłowe dane", Toast.LENGTH_SHORT).show();
                        editGrades.setError("Wprowadzono nieprawidłowe");
                    }
                    if(editGrades.getText().toString().isEmpty()) editGrades.setError("Pole nie może być puste");
                    else if(editAssInput<5 || editAssInput>15){
                        Toast.makeText(MainActivity.this, "Zakres ilosci ocen to <5,15>", Toast.LENGTH_SHORT).show();
                        editGrades.setError("Liczba nie miesci sie w zakresie");
                    }
                }
            }
        });
        sentResults = (Button) findViewById(R.id.sentResult);
        sentResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int transferedNumber=Integer.parseInt(editGrades.getText().toString());
                Intent intent=new Intent(MainActivity.this, GradleCountActivity.class);
                intent.putExtra(PUT_NUMBER_GRADES,transferedNumber);
                startActivityForResult(intent,KEY_NUMBER_GRADES);
            }
        });
        editName.addTextChangedListener(fillForm);
        editSurname.addTextChangedListener(fillForm);
        editGrades.addTextChangedListener(fillForm);
    }
    public void onActivityResult(int requestCode,int endCode,Intent result){
        super.onActivityResult(requestCode, endCode, result);
        if(requestCode==2) {
            if (result != null) {
                Bundle bundle = result.getExtras();
                getAvg = bundle.getDouble(PICK_UP_AVG);
                displayAvg.setVisibility(View.VISIBLE);
                displayAvg.setText("Twoja średnia wynosi:"+getAvg);

                if(getAvg>3) {
                    sentResults.setText("Super");
                    sentResults.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            Toast.makeText(MainActivity.this, "Gratulacje, otrzymujesz zaliczenie", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else {sentResults.setText("Tym razem mi nie poszło");

                    sentResults.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            Toast.makeText(MainActivity.this, "Składam podanie o warunkowe zaliczenie", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
}
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        displayAvg=findViewById(R.id.displayAvg);
        outState.putString(TEXT_GRADE, displayAvg.getText().toString());
        outState.putDouble("key",getAvg);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        displayAvg = findViewById(R.id.displayAvg);
        displayAvg.setText(savedInstanceState.getString(TEXT_GRADE));
        if (displayAvg.getText().length() > 0) displayAvg.setVisibility(View.VISIBLE);
        getAvg=savedInstanceState.getDouble("key");
                if(getAvg!=0) {
                    if (getAvg > 3) {
                        sentResults.setText("Super");
                        sentResults.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                Toast.makeText(MainActivity.this, "Gratulacje, otrzymujesz zaliczenie", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        sentResults.setText("Tym razem mi nie poszło");
                        sentResults.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                Toast.makeText(MainActivity.this, "Składam podanie o warunkowe zaliczenie", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
    }


    private TextWatcher fillForm= new TextWatcher() {
        String editNameInput,editSurnameInput;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            editNameInput = editName.getText().toString();
            editSurnameInput = editSurname.getText().toString();
        }
        @Override
        public void afterTextChanged(Editable s) {
            int editAssInput=0;
            try {
                editAssInput = Integer.parseInt(editGrades.getText().toString());
            } catch (NumberFormatException e) {}
            if(editAssInput>4 && editAssInput<16) acceptRangeAss=true;
            else acceptRangeAss=false;

            if(editNameInput.length()>0 && editSurnameInput.length()>0 && acceptRangeAss) sentResults.setVisibility(View.VISIBLE);
            else sentResults.setVisibility(View.INVISIBLE);

        }
    };

}