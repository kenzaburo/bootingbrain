package com.academic.idiots.bootingbrain.matchingpictures;

/**
 * Created by HuyThien on 3/14/2015.
 */
public class Player {
    int score;

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int additionScores){
        score+= additionScores;
    }
}
