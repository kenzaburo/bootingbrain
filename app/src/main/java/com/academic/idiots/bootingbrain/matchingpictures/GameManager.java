package com.academic.idiots.bootingbrain.matchingpictures;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuyThien on 3/14/2015.
 */
public class GameManager {
    private int currentPlayerPosition = 0;
    List<Player> players = new ArrayList<Player>(2);
    int level = 0;

    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int position) {
        if (position < 0 || players.size() <= position) {
            return null;
        }
        return players.get(position);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerPosition);
    }

    public void switchPlayer() {
        if (players.size() == 0) {
            Log.e("ERROR", "switch no player");
            return;
        }
        currentPlayerPosition++;
        if (currentPlayerPosition == players.size()) {
            currentPlayerPosition = 0;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp() {
        this.level++;
    }

    public LevelInfo getLevelInfo() {
        switch (level) {
            case 0:
            case 1:
            case 2:
                return LevelInfo.LEVEL_INFOS[level];
            default:
                return new LevelInfo(6, 6);
        }
    }

    private EndGameListenner endGameListenner = null;

    public void setEndGameListenner(EndGameListenner endGameListenner) {
        this.endGameListenner = endGameListenner;
    }

    public void checkEndGame() {
        if (getLevelInfo().isFinished()) {
            if (endGameListenner != null) {
                endGameListenner.onEndGame();
            }
        }
    }

    public interface EndGameListenner {
        public void onEndGame();
    }

    public void increaseMatching(){
        getCurrentPlayer().addScore(1);
        getLevelInfo().inscreaseMatChing();
        checkEndGame();
    }
}
