package viewmodel.panes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import model.Exceptions.InvalidMapException;
import model.LevelManager;
import model.Map.Cell;
import viewmodel.MapRenderer;
import viewmodel.SceneManager;

// is it needed?
import javafx.stage.Stage;


import java.io.File;


/**
 * Represents the main menu in the game
 */
public class LevelSelectPane extends BorderPane {
    private VBox leftContainer;
    private Button returnButton;
    private Button playButton;
    private Button chooseMapDirButton;
    private ListView<String> levelsListView;
    private VBox centerContainer;
    private Canvas levelPreview;

    /**
     * Instantiate the member components and connect and style them. Also set the callbacks.
     * Use 20 for VBox spacing
     */
    public LevelSelectPane() {
        //TODO
        leftContainer = new VBox(20);
        returnButton = new Button("Return");
        playButton = new Button("Play");
        chooseMapDirButton = new Button("Choose map directory");
        levelsListView = new ListView<>();
        centerContainer = new VBox(20);
        levelPreview = new Canvas();



        connectComponents();styleComponents();
        setCallbacks();

    }

    /**
     * Connects the components together (think adding them into another, setting their positions, etc). Reference
     * the other classes in the {@link javafx.scene.layout.Pane} package.
     */
    private void connectComponents() {
        //TODO
        leftContainer.getChildren().addAll(
            returnButton,chooseMapDirButton,levelsListView,playButton
        );
        centerContainer.getChildren().add(levelPreview);
        this.setCenter(centerContainer);
        this.setLeft(leftContainer);
    }

    /**
     * Apply CSS styling to components. Also sets the {@link LevelSelectPane#playButton} to be disabled.
     */
    private void styleComponents() {
        //TODO
        levelsListView.setPrefHeight(300);
        leftContainer.getStyleClass().add("side-menu");
        returnButton.getStyleClass().add("big-button");
        playButton.getStyleClass().add("big-button");
        chooseMapDirButton.getStyleClass().add("big-button");
        levelsListView.getStyleClass().add("list-cell");

        centerContainer.getStyleClass().add("big-vbox");
        playButton.setDisable(true);
    }

    /**
     * Set the event handlers for the 3 buttons and listview.
     * <p>
     * Hints:
     * The return button should show the main menu scene
     * The chooseMapDir button should prompt the user to choose the map directory, and load the levels
     * The play button should set the current level based on the current level name (see LevelManager), show
     * the gameplay scene, and start the level timer.
     * The listview, based on which item was clicked, should set the current level (see LevelManager), render the
     * preview (see {@link MapRenderer#render(Canvas, Cell[][])}}, and set the play button to enabled.
     */
    private void setCallbacks() {
        //TODO
//        try{
            returnButton.setOnMouseClicked(e->{SceneManager.getInstance().showMainMenuScene();});

            // is it...?
            // how to initialize gamelevel(), this is a very big problem
        // is it...?
        // if the helper function(getMapDirectory) is not allowed, must redo the whole loadLevelNamesFromDisk in levelmanager
            playButton.setOnMouseClicked(
                    e->{ //after lunch, handle the exception
                        // not sure if it is ok...?
//                        try{LevelManager.getInstance().setLevel(
////                               "C:\\Users\\Chen\\Desktop\\comp3021(2018_fall)\\PA2\\PA2\\PA2\\src\\assets\\maps\\"+ levelsListView.getSelectionModel().getSelectedItem()
////                                "PA2\\PA2\\PA2\\src\\assets\\maps\\"+ levelsListView.getSelectionModel().getSelectedItem()
////                                LevelManager.getInstance().getMapDirectory()+"\\"  +
//                                        levelsListView.getSelectionModel().getSelectedItem()
//                        );}catch(InvalidMapException ex){ex.printStackTrace();}
                        LevelManager.getInstance().currentLevelNameProperty().setValue(levelsListView.getSelectionModel().getSelectedItem());
//                        System.out.println(levelsListView.getSelectionModel().getSelectedItem()) ;
                        SceneManager.getInstance().showGamePlayScene();
                        LevelManager.getInstance().startLevelTimer();
                    }
            );

//        }catch(InvalidMapException e){System.out.println("what can i do?");}

            chooseMapDirButton.setOnMouseClicked(e->{promptUserForMapDirectory();});

            levelsListView.getSelectionModel().selectedItemProperty().addListener(
                            (ObservableValue)->{
//                                File f = new File(LevelManager.getInstance().getMapDirectory()+"\\"  + levelsListView.getSelectionModel().getSelectedItem() );

                                // is it...?
                                // cannot import wall, it means should not depend on Map method!!! ( and perhaps GameLevel method?)

//                                try (Scanner reader = new Scanner(f)) {
//                                    int numRows = reader.nextInt();
//                                    int numCols = reader.nextInt();
//                                    reader.nextLine();
//
//                                    char[][] rep = new char[numRows][numCols];
//                                    for (int r = 0; r < numRows; r++) {
//                                        String row = reader.nextLine();
//                                        for (int c = 0; c < numCols; c++) {
//                                            rep[r][c] = row.charAt(c);
//                                        }
//                                    }
//                                }
//
//                                    Cell[][] preview_map = new Cell[numRows][numCols];
//
//                                    for (int i = 0; i <  numRows; i++) {
//                                        for (int j = 0; j < numCols; j++) {
//
//                                            char c = rep[i][j];
//
//                                            if (c == '#') {
//                                                preview_map[i][j] = new Wall();
//
//                                            } else if (c == '@') {
//                                                preview_map[i][j] = new Tile();
//
//                                            } else if (c == '.') {
//                                                preview_map[i][j] = new Tile();
//
//                                            } else if (c=='C') {
//
//                                                preview_map[i][j] = new DestTile();
//
//
//                                            } else if (c=='c') {
//
//                                                preview_map[i][j] = new Tile();
//
//
//                                            }
//
//                                        }
//                                    }
//
//
//                                    MapRenderer.render(levelPreview, preview_map);
//
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                } catch (InvalidMapException e) {
//                                    e.printStackTrace();
//                                    throw e;
//                                }
//                                finally {
//
//                                }
//                                try{
//                               "C:\\Users\\Chen\\Desktop\\comp3021(2018_fall)\\PA2\\PA2\\PA2\\src\\assets\\maps\\"+ levelsListView.getSelectionModel().getSelectedItem()
//                                "PA2\\PA2\\PA2\\src\\assets\\maps\\"+ levelsListView.getSelectionModel().getSelectedItem()
//                                File f = new File(LevelManager.getInstance().getMapDirectory()+"\\"  + levelsListView.getSelectionModel().getSelectedItem() );
//
//                                    Scanner reader = new Scanner(f);
//                                        int numRows = reader.nextInt();
//                                        int numCols = reader.nextInt();
//                                        reader.nextLine();
//
//                                        char[][] rep = new char[numRows][numCols];
//                                        for (int r = 0; r < numRows; r++) {
//                                            String row = reader.nextLine();
//                                            for (int c = 0; c < numCols; c++) {
//                                                rep[r][c] = row.charAt(c);
//                                            }
//                                        }
//                                }
//                                catch (FileNotFoundException e) {e.printStackTrace();}

//                                levelPreview=null;
//                                levelPreview = new Canvas();
//                                centerContainer.getChildren().add(levelPreview);
                                levelPreview.getGraphicsContext2D().clearRect(0,0,levelPreview.getWidth(),levelPreview.getHeight());
                                try{
                                    LevelManager.getInstance().setLevel(
                                        levelsListView.getSelectionModel().getSelectedItem()
                                );
//                                    System.out.println("This is unexpected!!!! : "+levelsListView.getSelectionModel().getSelectedItem());
//                                    System.out.println(levelsListView.getSelectionModel().getSelectedItem());
                                MapRenderer.render(levelPreview, LevelManager.getInstance().getGameLevel().getMap().getCells());
//                                    LevelManager.getInstance().getGameLevel().getMap().player;
                                }
                                catch(InvalidMapException ex){ex.printStackTrace();}
                                // is it...?
                                // it is possible to handle invalid input file, try it later
                                catch(Exception ex){ex.printStackTrace();}
                                playButton.setDisable(false);

                            }
                    );
    }

    /**
     * Popup a DirectoryChooser window to ask the user where the map folder is stored.
     * Update the LevelManager's map directory afterwards, and potentially
     * load the levels from disk using LevelManager (if the user didn't cancel out the window)
     */
    private void promptUserForMapDirectory() {
        //TODO
        DirectoryChooser dir_c = new DirectoryChooser();

        Stage secondaryStage = new Stage();
        File dir = dir_c.showDialog(secondaryStage);



        if(dir!=null){
            LevelManager.getInstance().setMapDirectory(dir.getAbsolutePath());
            LevelManager.getInstance().loadLevelNamesFromDisk();
            ObservableList<String> items = LevelManager.getInstance().getLevelNames();
//System.out.print(items);

            levelsListView.setItems(items);
//            playButton.setDisable(false);
        }
        else{        }
    }
}
