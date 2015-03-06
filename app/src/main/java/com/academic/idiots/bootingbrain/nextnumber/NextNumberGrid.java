package com.academic.idiots.bootingbrain.nextnumber;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.academic.idiots.bootingbrain.HomeActivity;
import com.academic.idiots.bootingbrain.R;

public class NextNumberGrid extends Fragment {

    private State state = State.STOP;
    // 2x2, 3x3, 4x4, 5x5, 6x6, 7x7, 8x8
    private int[] timer = {10,30,40,70,80,90,100}; // 10... seconds
    private ProgressBar progressBar = null;
    private GridView gridView;
    private int mark = 0;
    private Chronometer chronometer = null;
    private TextView markView;
    private Handler handler = new Handler();
    private int level = 1;
    private CountDownTimer countDownTimer = null;

    public NextNumberGrid() {
        this.state = State.RUNNING;
    }

    public enum State{
        STOP(1),
        RUNNING(2);
        private int status;
        State(int value){this.status = value;};
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_next_number, container, false);
        // Set up progress bar
        progressBar = (ProgressBar)rootView.findViewById(R.id.timerBar);
        chronometer = (Chronometer) rootView.findViewById(R.id.chronometer);
        markView = (TextView) rootView.findViewById(R.id.markView);
        startProgressBar();
        chronometer.start();

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        final NumberAdapter numberAdapter = new NumberAdapter(getActivity(), level + 1);
        gridView.setNumColumns(level + 1);
        gridView.setAdapter(numberAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NumberAdapter numberAdapter1 = (NumberAdapter)gridView.getAdapter();
                if(state == State.RUNNING){
                    if(numberAdapter1.removeItem(position)){
                        mark = mark + 5;
                        markView.setText(Integer.toString(mark));
                        numberAdapter1.notifyDataSetChanged();
                    }
                    if(numberAdapter1.isEmpty()){
                        level = level + 1;
                        countDownTimer.cancel();
                        if(level > 6){
                            showDialog();
                        }else{
                            updateGridView(level, gridView);
                        }
                    }
                }
            }
        });
        return rootView;
    }

    public void updateGridView(int level, GridView gView){
        int matrixSize = level + 1;
        NumberAdapter numberAdapter = new NumberAdapter(getActivity(),matrixSize);
        gridView.setNumColumns(matrixSize);
        gridView.setAdapter(numberAdapter);
        startProgressBar();
        chronometer.stop();
        chronometer.start();
    }

    class Counter extends CountDownTimer{
        private long total;

        Counter(long miliInFuture, long interval){
            super(miliInFuture,interval);
            total = miliInFuture;
        }
        @Override
        public void onTick(long millisUntilFinished) {
            progressBar.setProgress( (int)millisUntilFinished*100/ (int)total);
        }

        @Override
        public void onFinish() {
            state = State.STOP;
            progressBar.setProgress(0);
            showDialog();
        }
    }
    public void startProgressBar(){
        progressBar.setProgress(100);
        progressBar.setMax(100);
        //        Start long running operation in a background thread
        countDownTimer = new Counter(timer[level-1]*1000, 10);
        countDownTimer.start();
    }

    public void showDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
//        b.setTitle(R.string.congratulation);
        b.setTitle("Congratulation");
        b.setMessage("Your mark is: " + mark + " your level: " + level);
        b.setPositiveButton("Home", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent returnBtn = new Intent(getActivity(),
                        HomeActivity.class);
                startActivity(returnBtn);
            }
        });
        b.create().show();
    }
}
