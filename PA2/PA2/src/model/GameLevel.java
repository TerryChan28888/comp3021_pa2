package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.Exceptions.InvalidMapException;
import model.Map.Map;
import model.Map.Occupant.Crate;
import model.Map.Occupant.Occupant;
import model.Map.Occupiable.DestTile;
import model.Map.Occupiable.Occupiable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that loads, stores, modifies, and keeps track of the game map win/deadlock condition. Also keeps tracks
 * of information about this current level, e.g. how many moves the player has made.
 */
public class GameLevel {

    private final IntegerProperty numPushes = new SimpleIntegerProperty(0);
    private Map map;

    public IntegerProperty numPushesProperty() {
        return numPushes;
    }

    public Map getMap() {
        return map;
    }

    /**
     * Loads and reads the map line by line, instantiates and initializes map
     *
     * @param filename the map text filename
     * @throws InvalidMapException when the map is invalid
     */
    public void loadMap(String filename) throws InvalidMapException {
//                    System.out.print(filename);
        File f = new File(filename);
        try (Scanner reader = new Scanner(f)) {
            int numRows = reader.nextInt();
            int numCols = reader.nextInt();
            reader.nextLine();

            char[][] rep = new char[numRows][numCols];
            for (int r = 0; r < numRows; r++) {
                String row = reader.nextLine();
                for (int c = 0; c < numCols; c++) {
                    rep[r][c] = row.charAt(c);
                }
            }
            // the follw is for debug
//System.out.println(rep[0]);
            map = new Map();
            map.initialize(numRows, numCols, rep);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidMapException e) {
            e.printStackTrace();
            throw e;
        }
        finally {

        }
    }

    /**
     * @return Whether or not the win condition has been satisfied
     */
    public boolean isWin() {
        return map.getDestTiles().stream().allMatch(DestTile::isCompleted);
    }

    /**
     * When no crates can be moved but the game is not won, then deadlock has occurred
     *
     * @return Whether deadlock has occurred
     */
    public boolean isDeadlocked() {
        for (Crate c : map.getCrates()) {
            boolean canMoveLR = map.isOccupiableAndNotOccupiedWithCrate(c.getR(), c.getC() - 1)
                    && map.isOccupiableAndNotOccupiedWithCrate(c.getR(), c.getC() + 1);
            boolean canMoveUD = map.isOccupiableAndNotOccupiedWithCrate(c.getR() - 1, c.getC()) &&
                    map.isOccupiableAndNotOccupiedWithCrate(c.getR() + 1, c.getC());
            if (canMoveLR || canMoveUD)
                return false;
        }
        return true;



//boolean[] array_movable = new boolean[map.getCrates().size()];
//for(int i =0; i<map.getCrates().size();i++){
////        for (Crate c : map.getCrates()) {
//            boolean canMoveLR = map.isOccupiableAndNotOccupiedWithCrate(map.getCrates().get(i).getR(), map.getCrates().get(i).getC() - 1)
//                    && map.isOccupiableAndNotOccupiedWithCrate(map.getCrates().get(i).getR(), map.getCrates().get(i).getC() + 1);
//            boolean canMoveUD = map.isOccupiableAndNotOccupiedWithCrate(map.getCrates().get(i).getR() - 1, map.getCrates().get(i).getC()) &&
//                    map.isOccupiableAndNotOccupiedWithCrate(map.getCrates().get(i).getR() + 1, map.getCrates().get(i).getC());
//            if (canMoveLR || canMoveUD)
//            {array_movable[i]=true;}
//            else{array_movable[i]=false;}
////                return false;
//        }
////        boolean[] array_success = new boolean[map.getDestTiles().size()];
////
////        for(int i =0; i<map.getDestTiles().size();i++){
////                if(map.getDestTiles().get(i).isCompleted()){array_success[i]=true;}else{array_success[i]=false;}
////            }
//
//            ArrayList<Integer> array_of_row = new ArrayList<>();
//        ArrayList<Integer> array_of_col = new ArrayList<>();
//
//        for(int x=0; x<map.getCells().length;x++){
//            for(int y=0; y<map.getCells()[x].length;y++){
//                if(map.getCells()[x][y] instanceof DestTile){array_of_row.add(x);array_of_col.add(y);}
//            }
//        }
//            for(Integer x: array_of_row){
//                for(Integer y: array_of_col){
//                    for(int i =0;i<array_movable.length;i++){
//                        if(!array_movable[i] && map.getCrates().get(i).getR()!= x && map.getCrates().get(i).getC()!= y){return true;}
//                    }
//                }
//            }


//        for(int j=0; j<array_movable.length;j++){if(!array_movable[j] && map.getCells()[map.getCrates().get(j).getR()][map.getCrates().get(j).getC()]  ){return true;}}
//        return false;
    }

    /**
     * @param c The char corresponding to a move from the user
     *          w: up
     *          a: left
     *          s: down
     *          d: right
     * @return Whether or not the move was successful
     */
    public boolean makeMove(char c) {
        boolean madeMove = false;
        switch (c) {
            case 'w':
                madeMove = map.movePlayer(Map.Direction.UP);
                if(!madeMove)
                { if(map.getCells()[map.player.getR()][map.player.getC()] instanceof Occupiable){



                    }
                }
                break;
            case 'a':
                madeMove = map.movePlayer(Map.Direction.LEFT);
                break;
            case 's':
                madeMove = map.movePlayer(Map.Direction.DOWN);
                break;
            case 'd':
                madeMove = map.movePlayer(Map.Direction.RIGHT);
                break;
        }
        if (madeMove) {
            numPushes.setValue(numPushes.getValue() + 1);
        }
        return madeMove;
    }
}
