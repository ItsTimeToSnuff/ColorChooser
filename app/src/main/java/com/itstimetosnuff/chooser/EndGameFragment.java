package com.itstimetosnuff.chooser;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.app.Fragment;

public class EndGameFragment extends Fragment implements View.OnClickListener {

    private Button exitGame;
    private Button retryGame;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getResources();
        view = inflater.inflate(R.layout.end_game_fragment, null);
        TextView endGameText = view.findViewById(R.id.endGameText);
        String score = getArguments().get(GameFragment.COUNTER_KEY).toString();
        endGameText.setText(String.format(res.getString(R.string.end_game_text), score));
        retryGame = view.findViewById(R.id.retry);
        retryGame.setText(res.getString(R.string.retry_btn));
        retryGame.setOnClickListener(this);
        exitGame = view.findViewById(R.id.exit);
        exitGame.setText(res.getString(R.string.exit_btn));
        exitGame.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Button receivedAnswer = view.findViewById(v.getId());
        if (receivedAnswer.getText().equals(exitGame.getText())){
            ((MainActivity)getActivity()).finish();
            System.exit(0);
        }
        if (receivedAnswer.getText().equals(retryGame.getText())){
            ((MainActivity)getActivity()).loadFragment(new GameFragment());
        }
    }
}
