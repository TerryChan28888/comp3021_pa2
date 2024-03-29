package viewmodel;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

import static viewmodel.Config.LEVEL_EDITOR_TILE_SIZE;

/**
 * Extends the Canvas class to provide functionality for generating maps and saving them.
 */
public class LevelEditorCanvas extends Canvas {
    private int rows;
    private int cols;

    private Brush[][] map;

    //Stores the last location the player was standing at
    private int oldPlayerRow = -1;
    private int oldPlayerCol = -1;

    /**
     * Call the super constructor. Also resets the map to all {@link Brush#TILE}.
     * Hint: each square cell in the grid has size {@link Config#LEVEL_EDITOR_TILE_SIZE}
     *
     * @param rows The number of rows in the map
     * @param cols The number of tiles in the map
     */
    public LevelEditorCanvas(int rows, int cols) {
        //TODO
        super(Config.LEVEL_EDITOR_TILE_SIZE*rows, Config.LEVEL_EDITOR_TILE_SIZE*cols);
        this.rows = rows;
        this.cols = cols;
        map=new Brush[cols][rows];
        for(int i =0;i<rows;i++){
            for(int j =0;j< cols;j++){
//                System.out.print(i);System.out.print(j);System.out.println("");
                map[j][i]=Brush.TILE;
            }
        }
        renderCanvas();
    }

    /**
     * Setter function. Also resets the map
     *
     * @param rows The number of rows in the map
     * @param cols The numbers of cols in the map
     */
    public void changeSize(int rows, int cols) {
//        System.out.println("problems in here?????");System.out.print(cols);
        this.rows = rows;
        this.cols = cols;
        resetMap(rows, cols);
    }

    /**
     * Assigns {@link LevelEditorCanvas#map} to a new instance, sets all the values to {@link Brush#TILE}, and
     * renders the canvas with the updated map.
     *
     * @param rows The number of rows in the map
     * @param cols The numbers of cols in the map
     */
    private void resetMap(int rows, int cols) {
        //TODO
        oldPlayerRow = -1;
        oldPlayerCol = -1;
        this.getGraphicsContext2D().clearRect(0,0,this.getWidth(),this.getHeight());
//        this.setWidth(rows*Config.LEVEL_EDITOR_TILE_SIZE);
//        this.setHeight(cols*Config.LEVEL_EDITOR_TILE_SIZE);

        this.map = null;
        this.map = new Brush[rows][cols];

        for(int i =0;i<rows;i++){
            for(int j =0;j< cols;j++){
//                System.out.print(i);System.out.print(j);System.out.println("");
                map[i][j]=Brush.TILE;
            }
        }
        renderCanvas();
    }

    /**
     * Render the map using {@link MapRenderer}
     */
    private void renderCanvas() {
        //TODO
        MapRenderer.render(this,map);
    }

    /**
     * Sets the applicable {@link LevelEditorCanvas#map} cell to the brush the user currently has selected.
     * In other words, when the user clicks somewhere on the canvas, we translate that into updating one of the
     * tiles in our map.
     * <p>
     * There can only be 1 player on the map. As such, if the user clicks a new position using the player brush,
     * the old location must have the player removed, leaving behind either a tile or a destination underneath,
     * whichever the player was originally standing on.
     * <p>
     * Hint:
     * Don't forget to update the player ({@link Brush#PLAYER_ON_DEST} or {@link Brush#PLAYER_ON_TILE})'s last
     * known position.
     * <p>
     * Finally, render the canvas.
     *
     * @param brush The currently selected brush
     * @param x     Mouse click coordinate x
     * @param y     Mouse click coordinate y
     */
    public void setTile(Brush brush, double x, double y) {
        //TODO
        int selected_row = (int)x/Config.LEVEL_EDITOR_TILE_SIZE;
        int selected_col = (int)y/Config.LEVEL_EDITOR_TILE_SIZE;

//        int num_player =0;

//        for(int i =0; x< rows; x++){
//            for(int j = 0; y < cols; y++){
//                if(){}
//
//            }
//        }

        if(oldPlayerRow==-1 && (brush==Brush.PLAYER_ON_DEST||brush==Brush.PLAYER_ON_TILE) ){
//            System.out.print(oldPlayerCol);
//            num_player++;
            map[selected_col][selected_row]=brush;
            oldPlayerCol=selected_col;
            oldPlayerRow=selected_row;

        }



        else if(oldPlayerRow!=-1 && brush==Brush.PLAYER_ON_TILE ){
            if(map[oldPlayerCol][oldPlayerRow]==Brush.PLAYER_ON_DEST){
                map[oldPlayerCol][oldPlayerRow]=Brush.DEST;
                map[selected_col][selected_row]=brush;
                oldPlayerCol=selected_col;
                oldPlayerRow=selected_row;
            }else if(map[oldPlayerCol][oldPlayerRow]==Brush.PLAYER_ON_TILE){
                map[oldPlayerCol][oldPlayerRow]=Brush.TILE;
                map[selected_col][selected_row]=brush;
                oldPlayerCol=selected_col;
                oldPlayerRow=selected_row;
            }
        }
        else if(oldPlayerRow!=-1 && brush==Brush.PLAYER_ON_DEST ){
            if(map[oldPlayerCol][oldPlayerRow]==Brush.PLAYER_ON_DEST){
                map[oldPlayerCol][oldPlayerRow]=Brush.DEST;
                map[selected_col][selected_row]=brush;
                oldPlayerCol=selected_col;
                oldPlayerRow=selected_row;
            }else if(map[oldPlayerCol][oldPlayerRow]==Brush.PLAYER_ON_TILE){
                map[oldPlayerCol][oldPlayerRow]=Brush.TILE;
                map[selected_col][selected_row]=brush;
                oldPlayerCol=selected_col;
                oldPlayerRow=selected_row;
            }
        }
        else if( selected_col==oldPlayerCol && selected_row==oldPlayerRow && (map[selected_col][selected_row]!=Brush.PLAYER_ON_TILE||map[selected_col][selected_row]!=Brush.PLAYER_ON_DEST) ){
            oldPlayerCol=-1;
            oldPlayerRow=-1;
            map[selected_col][selected_row]=brush;
        }
        else{map[selected_col][selected_row]=brush;}


        renderCanvas();

    }

    /**
     * Saves the current map to file. Should prompt the user for the save directory before saving the file to
     * the selected location.
     */
    public void saveToFile() {
        //TODO
        try{
            if(isInvalidMap()){
                File file = getTargetSaveDirectory();
                if(file!=null ){

                    PrintWriter writer = new PrintWriter(file);
                    writer.print(map.length);writer.println();
                    writer.print(map[0].length);writer.println();
                    for(int x =0; x< map.length; x++){
                        for(int y = 0; y < map[0].length; y++){
                            writer.print(map[x][y].rep);
                        }
                        writer.println();
                    }
                    writer.close();
                }
            }

        }catch(FileNotFoundException e){}
    }

    /**
     * Hint: {@link FileChooser} is needed. Also add an extension filter with the following information:
     * description: "Normal text file"
     * extension: "*.txt"
     *
     * @return The directory the user chose to save the map in.
     */
    private File getTargetSaveDirectory() {
        //TODO
//        return null;//NOTE: You may also need to modify this line
        FileChooser filechooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Normal text file", "*.txt");
        filechooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();
        return filechooser.showSaveDialog(stage);
    }

    /**
     * Check if the map is valid for saving.
     * Conditions to check:
     * 1. Map must at least have 3 rows and 3 cols
     * 2. Must have a player
     * 3. Balanced number of crates and destinations
     * 4. At least 1 crate and destination
     * <p>
     * Show an Alert if there's an error.
     *
     * @return If the map is invalid
     */
    private boolean isInvalidMap() {
        //TODO
//        return true;//NOTE: You may also need to modify this line
//        System.out.println('a');
        int num_crate=0;int num_dest=0;
        int num_player=0;
        for(int x =0; x< map.length; x++){
            for(int y = 0; y < map[0].length; y++){
                switch(map[x][y]){
                    case DEST : num_dest++;break;
                    case PLAYER_ON_DEST: num_dest++;num_player++;break;
                    case CRATE_ON_TILE: num_crate++;break;
                    case CRATE_ON_DEST: num_crate++;num_dest++;break;
                    case PLAYER_ON_TILE: num_player++;break;
                }
            }
        }

        if(!(map[0].length>=3&&map.length>=3)) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Minimum size is 3 rows and 3 cols.");
            alert.setTitle("Error");
            alert.setHeaderText("Could not save map!");
            alert.showAndWait();
        }
        else if(num_player<1) {
            System.out.println(num_player);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please add a player.");
            alert.setTitle("Error");
            alert.setHeaderText("Could not save map!");
            alert.showAndWait();
        }
        else if(num_crate<1 ||num_dest<1) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please create at least 1 crate and destination.");
            alert.setTitle("Error");
            alert.setHeaderText("Could not save map!");
            alert.showAndWait();
        }

        else if(num_crate!=num_dest) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Imbalanced number of crates and destinations.");
            alert.setTitle("Error");
            alert.setHeaderText("Could not save map!");
            alert.showAndWait();
        }


        return (map[0].length>=3&&map.length>=3) && (oldPlayerRow!=-1&&oldPlayerCol!=-1)
                && (num_crate==num_dest) && (num_crate>0) &&(num_dest>0) ;
    }

    /**
     * Represents the currently selected brush when the user is making a new map
     */
    public enum Brush {
        TILE("Tile", '.'),
        PLAYER_ON_TILE("Player on Tile", '@'),
        PLAYER_ON_DEST("Player on Destination", '&'),
        CRATE_ON_TILE("Crate on Tile", 'c'),
        CRATE_ON_DEST("Crate on Destination", '$'),
        WALL("Wall", '#'),
        DEST("Destination", 'C');

        private final String text;
        private final char rep;

        Brush(String text, char rep) {
            this.text = text;
            this.rep = rep;
        }

        public static Brush fromChar(char c) {
            for (Brush b : Brush.values()) {
                if (b.getRep() == c) {
                    return b;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return text;
        }

        char getRep() {
            return rep;
        }
    }


}

