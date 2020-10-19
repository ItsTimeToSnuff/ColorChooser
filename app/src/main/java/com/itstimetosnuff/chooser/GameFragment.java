package com.itstimetosnuff.chooser;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.graphics.Color;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class GameFragment extends Fragment implements View.OnClickListener {

    public static final String COUNTER_KEY = "COUNTER";

    private TextView question;
    private List<com.itstimetosnuff.chooser.Color> colors;
    private List<Button> answers;
    private TextView score;
    private TextView timerText;
    private Random random;
    private AtomicInteger counter;
    private CountDownTimer timer;
    private View view;
    private Bundle bundle;
    private Resources res;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_fragment, container, false);
        question = view.findViewById(R.id.question);
        score = view.findViewById(R.id.score);
        timerText = view.findViewById(R.id.timer);
        answers = initQuestions();
        timer = setTimer();
        colors = loadColors();
        bundle = getArguments();
        res = getResources();
        counter = new AtomicInteger(0);
        random = new Random();

        game();

        return view;
    }

    @Override
    public void onClick(View v) {
        Button receivedAnswer = view.findViewById(v.getId());
        if (receivedAnswer.getText().equals(question.getText())) {
            counter.incrementAndGet();
            timer.cancel();
            game();
        } else {
            timer.cancel();
            endGame();
        }
    }

    private void game() {
        score.setText(String.format(res.getString(R.string.score_text), counter.get() * 10));
        bundle.putInt(COUNTER_KEY, counter.get() * 10);

        int randomIdx = random.nextInt(colors.size());
        com.itstimetosnuff.chooser.Color randomColor = colors.get(randomIdx);
        question.setText(randomColor.getName());
        List<com.itstimetosnuff.chooser.Color> tmpColors = new ArrayList<>(colors);
        tmpColors.remove(randomIdx);
        randomIdx = random.nextInt(tmpColors.size());
        question.setTextColor(tmpColors.get(randomIdx).getValue());
        tmpColors.remove(randomIdx);
        randomIdx = random.nextInt(tmpColors.size());
        question.setBackgroundColor(tmpColors.get(randomIdx).getValue());
        tmpColors.remove(randomIdx);

        int answerIdx = random.nextInt(answers.size());
        Button correctAnswer = answers.get(answerIdx);
        correctAnswer.setText(randomColor.getName());
        tmpColors.remove(randomColor);
        randomIdx = random.nextInt(tmpColors.size());
        correctAnswer.setBackgroundColor(tmpColors.get(randomIdx).getValue());
        tmpColors.remove(randomIdx);
        randomIdx = random.nextInt(tmpColors.size());
        correctAnswer.setTextColor(tmpColors.get(randomIdx).getValue());
        tmpColors.remove(randomIdx);
        List<Button> tmpAnswers = new ArrayList<>(answers);
        tmpAnswers.remove(answerIdx);
        for (Button answer : tmpAnswers) {
            randomIdx = random.nextInt(tmpColors.size());
            answer.setText(tmpColors.get(randomIdx).getName());
            tmpColors.remove(randomIdx);
            randomIdx = random.nextInt(tmpColors.size());
            answer.setTextColor(tmpColors.get(randomIdx).getValue());
            tmpColors.remove(randomIdx);
            randomIdx = random.nextInt(tmpColors.size());
            answer.setBackgroundColor(tmpColors.get(randomIdx).getValue());
            tmpColors.remove(randomIdx);

        }
        timer.start();
    }

    private List<Button> initQuestions() {
        List<Button> answers = new ArrayList<>();
        Button answer1 = view.findViewById(R.id.answer_1);
        answer1.setOnClickListener(this);
        answers.add(answer1);
        Button answer2 = view.findViewById(R.id.answer_2);
        answer2.setOnClickListener(this);
        answers.add(answer2);
        Button answer3 = view.findViewById(R.id.answer_3);
        answer3.setOnClickListener(this);
        answers.add(answer3);
        Button answer4 = view.findViewById(R.id.answer_4);
        answer4.setOnClickListener(this);
        answers.add(answer4);
        return answers;
    }

    private CountDownTimer setTimer() {
        return new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.format(res.getString(R.string.timer_text), millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                endGame();
            }
        };
    }

    private List<com.itstimetosnuff.chooser.Color> loadColors() {
        List<com.itstimetosnuff.chooser.Color> colors = new ArrayList<>();
        try {
            JSONArray colorsJson = new JSONArray(loadJSONFromFile());
            for (int i = 0; i < colorsJson.length(); i++) {
                colors.add(new com.itstimetosnuff.chooser.Color(colorsJson.getJSONObject(i).getString("name"),
                        Color.parseColor(colorsJson.getJSONObject(i).getString("value").replace("0x","#"))));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return colors;
    }

    private String loadJSONFromFile() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("colors.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void endGame() {
        ((MainActivity) getActivity()).loadFragment(new EndGameFragment());
    }
}
