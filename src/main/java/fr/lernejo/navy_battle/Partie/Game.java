package fr.lernejo.navy_battle.Partie;

public class Game {
    public void InitialisationPlateau(){
        int[][] plateau = new int[10][10];

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++) {
                plateau[i][j] = 0;
            }
        }
    }

    public void InitialisationPartie(){
        InitialisationPlateau();
    }
}
