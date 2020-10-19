package com.itstimetosnuff.chooser;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment implements View.OnClickListener {

    private Button startGame;
    private Button exitGame;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources res = getResources();
        view = inflater.inflate(R.layout.main_menu_fragment, null);
        startGame = view.findViewById(R.id.start_btn);
        startGame.setText(res.getString(R.string.start_game_text));
        startGame.setOnClickListener(this);
        exitGame = view.findViewById(R.id.end_btn);
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
        if (receivedAnswer.getText().equals(startGame.getText())){
            ((MainActivity)getActivity()).loadFragment(new GameFragment());
        }

    }
}
