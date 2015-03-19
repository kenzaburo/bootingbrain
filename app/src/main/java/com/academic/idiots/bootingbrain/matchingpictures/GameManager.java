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

    public Player getCurrentPlayer(){
        return players.get(currentPlayerPosition);
    }

    public void switchPlayer() {
        if (players.size() == 0) {
            Log.e("ERROR", "switch no player");
            return;
        }
        currentPlayerPosition++;
        if (currentPlayerPosition == players.size()){
            currentPlayerPosition=0;
        }
    }
}
