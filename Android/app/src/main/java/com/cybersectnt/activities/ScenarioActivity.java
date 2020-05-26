package com.cybersectnt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cybersectnt.cybersectntdemo1.R;
import com.cybersectnt.data.TypeWriter;
import com.cybersectnt.globalVars;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScenarioActivity extends AppCompatActivity implements TypeWriter.DataChange {
    private String scenarioID;
    List<String> currentConversationArrayList, questionsArrayList, directionArrayList, answersArrayList;
    ScrollView scrollView;
    LinearLayout ConversationLinearLayout;
    String subScenario = "main";
    int counter = 0;

    /**
     * Setup and initiate the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenarios);
        ConversationLinearLayout = findViewById(R.id.LinearLayoutToHoldTheConversation);
        scrollView = findViewById(R.id.ScrollViewMainLayout);
        answersArrayList = new ArrayList<>();
        scenarioID = getIntent().getStringExtra("ScenarioID");
        loadDB();
    }

    /*
    This method is to initialize the first typewrite (a custom text view)
     */
    public TypeWriter incomingTypeWriter(String text) {
        TypeWriter typeWriter = new TypeWriter(this, TypeWriter.INCOMING, text);
        addAnimateAndScroll(typeWriter);
        return typeWriter;
    }
    /*
    This method is to initialize the second typewrite (a custom text view)
     */

    public TypeWriter outgoingTypeWriter(String text) {
        TypeWriter typeWriter = new TypeWriter(this, TypeWriter.OUTGOING, text);
        addAnimateAndScroll(typeWriter);
        return typeWriter;
    }

    /*
    This method is to initialize the type write used to show the question
     */

    public TypeWriter questionTypeWriter(String text) {
        TypeWriter typeWriter = new TypeWriter(this, TypeWriter.QUESTION, text);
        addAnimateAndScroll(typeWriter);
        return typeWriter;
    }

    /*
    This method is to initialize the type writer used to show to answers
     */

    public TypeWriter answerTypeWriter(String text) {
        TypeWriter typeWriter = new TypeWriter(this, TypeWriter.ANSWER, text);
        addAnimateAndScroll(typeWriter);
        return typeWriter;
    }
    /*
    This method is to initialize the type writer used for the results
     */

    private TypeWriter resultTypeWriter(String text) {
        TypeWriter typeWriter = new TypeWriter(this, TypeWriter.RESULT, text);
        addAnimateAndScroll(typeWriter);
        return typeWriter;
    }

    /*
    This method is to aad an animation to the type writer to allow it to scroll if it reached the bottom of the page
     */

    private void addAnimateAndScroll(TypeWriter typeWriter) {
        counter++;
        ConversationLinearLayout.addView(typeWriter);
        typeWriter.animateText();
        scroll();
    }

    public void scroll() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }

    /**
     * This method will check if the email pattern is valid or not
     *
     * @input the id of the scenario
     * @retur populate the information based on the scenario
     */
    private void loadDB() {
        String ID = scenarioID;
        if (!subScenario.equals("main")) {
            ID += "/SubScenarios/" + subScenario;
        }
        globalVars.getScenariosCollection().document(ID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if ((documentSnapshot.get("Conversation") + "").equals("null")) {
                    //Result
                    String paragraph = "";
                    for (int i = 0; i < answersArrayList.size(); i++) {
                        String[] answer = answersArrayList.get(i).split(";");
                        String field = answer[0];
                        int index = Integer.parseInt(answer[1]);
                        List<String> result = (List<String>) documentSnapshot.get(field);
                        paragraph += result.get(index) + " ";
                    }
                    resultTypeWriter(paragraph);
                } else {
                    currentConversationArrayList = (List<String>) documentSnapshot.get("Conversation");
                    questionsArrayList = (List<String>) documentSnapshot.get("Question");
                    directionArrayList = (List<String>) documentSnapshot.get("Direction");
                    finished();
                }
            }
        });
    }


    /**
     * This method is to populate the type writers with the proper information and to show the question and to get the answer
     * @return boolean valid or not valid
     */
    @Override
    public void finished() {
        if (counter < currentConversationArrayList.size()) {
            if (counter % 2 == 0) {
                incomingTypeWriter(currentConversationArrayList.get(counter));
            } else {
                outgoingTypeWriter(currentConversationArrayList.get(counter));
            }
        } else {
            if (counter == currentConversationArrayList.size()) {
                questionTypeWriter(questionsArrayList.get(0));
            } else if (counter < questionsArrayList.size() + currentConversationArrayList.size()) {
                final int index = counter - currentConversationArrayList.size();
                TypeWriter typeWriter = answerTypeWriter(questionsArrayList.get(index));
                typeWriter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setClickable(false);
                        checkAnswer(index - 1);
                    }
                });
            }

        }
    }

    /**
     * This method will check if the user's answer is correct or not
     *
     * @param index of the the answer chosen by the user
     * @return add the answer to a list to show to proper response
     */

    private void checkAnswer(int index) {
        int selectedPosition = ConversationLinearLayout.getChildCount() - questionsArrayList.size() + index + 1;
        int lastIndex = ConversationLinearLayout.getChildCount() - questionsArrayList.size() + 1;
        for (int i = ConversationLinearLayout.getChildCount() - 1; i >= lastIndex; i--) {
            if (i != selectedPosition) {
                ConversationLinearLayout.removeViewAt(i);
            }
        }
        answersArrayList.add(subScenario + ";" + index);
        subScenario = directionArrayList.get(index);
        counter = 0;
        loadDB();
    }
}
