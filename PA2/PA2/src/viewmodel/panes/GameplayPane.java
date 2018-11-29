package viewmodel.panes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Exceptions.InvalidMapException;
import model.LevelManager;

//is this needed?
import model.GameLevel;

import model.Map.Map;
import viewmodel.AudioManager;
import viewmodel.Config;
import viewmodel.MapRenderer;
import viewmodel.SceneManager;
import viewmodel.customNodes.GameplayInfoPane;

import java.util.Optional;

/**
 * Represents the gameplay pane in the game
 */
public class GameplayPane extends BorderPane {

    private final GameplayInfoPane info;
    private VBox canvasContainer;
    private Canvas gamePlayCanvas;
    private HBox buttonBar;
    private Button restartButton;
    private Button quitToMenuButton;

    /**
     * Instantiate the member components and connect and style them. Also set the callbacks.
     * Use 20 for the VBox spacing
     */
    public GameplayPane() {
        //TODO
        // err is for debugging
//        IntegerProperty err = new SimpleIntegerProperty(3);
        info = new GameplayInfoPane(LevelManager.getInstance().currentLevelNameProperty(), LevelManager.getInstance().curGameLevelExistedDurationProperty(),
                LevelManager.getInstance().getGameLevel().numPushesProperty()
//                err
                , LevelManager.getInstance().curGameLevelNumRestartsProperty());
        canvasContainer = new VBox(20);
        gamePlayCanvas = new Canvas();
        buttonBar = new HBox();
        restartButton = new Button("Restart");
        quitToMenuButton = new Button("Quit to menu");


        connectComponents();

        styleComponents();
        setCallbacks();
    }

    /**
     * Connects the components together (think adding them into another, setting their positions, etc).
     */
    private void connectComponents() {
        //TODO
        buttonBar.getChildren().addAll(info,restartButton, quitToMenuButton);
        canvasContainer.getChildren().add(

                gamePlayCanvas
                );
        this.setCenter(canvasContainer);
        this.setBottom(buttonBar);
    }

    /**
     * Apply CSS styling to components.
     */
    private void styleComponents() {
        //TODO
        restartButton.getStyleClass().add("big-button");
        quitToMenuButton.getStyleClass().add("big-button");
        buttonBar.getStyleClass().add("big-vbox");
        info.getStyleClass().add("    -fx-padding: 20 20 20 20;\n" +
                "    -fx-background-color: #ddd;");
//        gamePlayCanvas.setStyle(Config.CSS_STYLES);
        renderCanvas();
        canvasContainer.getStyleClass().add("side-menu");
        canvasContainer.setAlignment( Pos.BOTTOM_CENTER );
    }

    /**
     * Set the event handlers for the 2 buttons.
     * <p>
     * Also listens for key presses (w, a, s, d), which move the character.
     * <p>
     * Hint: {@link GameplayPane#setOnKeyPressed(EventHandler)}  is needed.
     * You will need to make the move, rerender the canvas, play the sound (if the move was made), and detect
     * for win and deadlock conditions. If win, play the win sound, and do the appropriate action regarding the timers
     * and generating the popups. If deadlock, play the deadlock sound, and do the appropriate action regarding the timers
     * and generating the popups.
     */
    // is it...?
    // A VERY LARGE PROBLEM: how to reactivate the timer after "next level" is clicked?????
    private void setCallbacks() {
        //TODO
this.setOnKeyPressed(
        e->{
            switch(e.getCode()){
                case W :
//                    LevelManager.getInstance().getGameLevel().getMap().movePlayer(Map.Direction.UP);System.out.println('W');
                if( LevelManager.getInstance().getGameLevel().makeMove('w') ){
                    renderCanvas();
//                    LevelManager.getInstance().incrementNumRestarts();
                    if( LevelManager.getInstance().getGameLevel().isWin() ){
                        LevelManager.getInstance().resetLevelTimer();
                        createLevelClearPopup();

//                        LevelManager.getInstance().resetNumRestarts();
                    }
                    else if( LevelManager.getInstance().getGameLevel().isDeadlocked() ){createDeadlockedPopup();}
                }
                break;
                case S :
//                    LevelManager.getInstance().getGameLevel().getMap().movePlayer(Map.Direction.DOWN);System.out.println('S');
                    if( LevelManager.getInstance().getGameLevel().makeMove('s') ){
                        renderCanvas();
//                        LevelManager.getInstance().incrementNumRestarts();
                        if( LevelManager.getInstance().getGameLevel().isWin() ){
                            LevelManager.getInstance().resetLevelTimer();
                            createLevelClearPopup();

//                            LevelManager.getInstance().resetNumRestarts();
                        }
                        else if( LevelManager.getInstance().getGameLevel().isDeadlocked() ){createDeadlockedPopup();}
                    }
                break;
                case D :
//                    LevelManager.getInstance().getGameLevel().getMap().movePlayer(Map.Direction.RIGHT);System.out.println('D');
                    if( LevelManager.getInstance().getGameLevel().makeMove('d') ){
                        renderCanvas();
//                        LevelManager.getInstance().incrementNumRestarts();
                        if( LevelManager.getInstance().getGameLevel().isWin() ){
                            LevelManager.getInstance().resetLevelTimer();
                            createLevelClearPopup();

//                            LevelManager.getInstance().resetNumRestarts();
                        }
                        else if( LevelManager.getInstance().getGameLevel().isDeadlocked() ){createDeadlockedPopup();}
                    }break;
                case A :
//                    System.out.println( LevelManager.getInstance().getGameLevel().getMap().player.getC()  );
//                    System.out.println( LevelManager.getInstance().getGameLevel().getMap().player.getR()  );
//
//                    LevelManager.getInstance().getGameLevel().getMap().movePlayer(Map.Direction.LEFT);
//
//                System.out.println( LevelManager.getInstance().getGameLevel().getMap().player.getC()  );
//                    System.out.println( LevelManager.getInstance().getGameLevel().getMap().player.getR()  );

                    if( LevelManager.getInstance().getGameLevel().makeMove('a') ){
                        renderCanvas();
//                        LevelManager.getInstance().incrementNumRestarts();
                        if( LevelManager.getInstance().getGameLevel().isWin() ){
                            LevelManager.getInstance().resetLevelTimer();
                            createLevelClearPopup();

//                            LevelManager.getInstance().resetNumRestarts();
                        }
                        else if( LevelManager.getInstance().getGameLevel().isDeadlocked() ){createDeadlockedPopup();}
                    }break;
            }
        }
);

        restartButton.setOnMouseClicked(e->doRestartAction());
        quitToMenuButton.setOnMouseClicked(e->doQuitToMenuAction());

    }

    /**
     * Called when the tries to quit to menu. Show a popup (see the documentation). If confirmed,
     * do the appropriate action regarding the level timer, level number of restarts, and go to the
     * main menu scene.
     */
    private void doQuitToMenuAction() {
        //TODO
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Game progress will be lost.", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Confirm");
        alert.setHeaderText("Return to menu?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                LevelManager.getInstance().getGameLevel().numPushesProperty().set(0);
                gamePlayCanvas.getGraphicsContext2D().clearRect(0,0,gamePlayCanvas.getWidth(),gamePlayCanvas.getHeight());
                LevelManager.getInstance().resetNumRestarts();
                SceneManager.getInstance().showMainMenuScene();
                LevelManager.getInstance().resetLevelTimer();
            }
//            else if(response == ButtonType.CANCEL){
//
//            }

        });
    }

    /**
     * Called when the user encounters deadlock. Show a popup (see the documentation).
     * If the user chooses to restart the level, call {@link GameplayPane#doRestartAction()}. Otherwise if they
     * quit to menu, switch to the level select scene, and do the appropriate action regarding
     * the number of restarts.
     */
    private void createDeadlockedPopup() {
        //TODO
        ButtonType restart_button = new ButtonType("Restart");
        ButtonType return_button = new ButtonType("Return");

//        next_level.setOnAction()

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", restart_button, return_button);
        alert.setTitle("Confirm");
        alert.setHeaderText("Level deadlocked!");
        alert.showAndWait().ifPresent(response -> {
            if (response == restart_button) {
                doRestartAction();
            }
            else if (response == return_button){
                System.out.print("return!!!!!!!!!waghhhhhhhhhhhhh!!!!!!!!!");
                LevelManager.getInstance().getGameLevel().numPushesProperty().set(0);
                gamePlayCanvas.getGraphicsContext2D().clearRect(0,0,gamePlayCanvas.getWidth(),gamePlayCanvas.getHeight());
                LevelManager.getInstance().resetNumRestarts();
                SceneManager.getInstance().showLevelSelectMenuScene();
                LevelManager.getInstance().resetLevelTimer();
            }
        });

    }

    /**
     * Called when the user clears the level successfully. Show a popup (see the documentation).
     * If the user chooses to go to the next level, set the new level, rerender, and do the appropriate action
     * regarding the timers and num restarts. If they choose to return, show the level select menu, and do
     * the appropriate action regarding the number of level restarts.
     * <p>
     * Hint:
     * Take care of the edge case for when the user clears the last level. In this case, there shouldn't
     * be an option to go to the next level.
     */

    //is it...?
    // edge  case and return button have not been finished!!!
    private void createLevelClearPopup() {
        //TODO
        ButtonType next_level = new ButtonType("Next level");
        ButtonType return_button = new ButtonType("Return");

//        next_level.setOnAction()

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", next_level, return_button);
        alert.setTitle("Confirm");
        alert.setHeaderText("Level cleared!");
        alert.showAndWait().ifPresent(response -> {
            if (response == next_level) {

                try{
                    LevelManager.getInstance().getGameLevel().numPushesProperty().set(0);
                    LevelManager.getInstance().setLevel(
                            LevelManager.getInstance().getNextLevelName()

                    );
                    renderCanvas();

                }
                catch(InvalidMapException ex){ex.printStackTrace();}

                catch(Exception ex){ex.printStackTrace();}
                LevelManager.getInstance().resetNumRestarts();
//                LevelManager.getInstance().resetLevelTimer();
                LevelManager.getInstance().startLevelTimer();
//                System.out.println("did the timer started????");
//                System.out.println("it's nest level!!!!!!!!huh!!!!!!");
            }
            else if (response == return_button){
                System.out.print("return!!!!!!!!!waghhhhhhhhhhhhh!!!!!!!!!");
                LevelManager.getInstance().getGameLevel().numPushesProperty().set(0);
                gamePlayCanvas.getGraphicsContext2D().clearRect(0,0,gamePlayCanvas.getWidth(),gamePlayCanvas.getHeight());
                LevelManager.getInstance().resetNumRestarts();
                SceneManager.getInstance().showLevelSelectMenuScene();
            }
        });
    }

    /**
     * Set the current level to the current level name, rerender the canvas, reset and start the timer, and
     * increment the number of restarts
     */
    private void doRestartAction() {
        //TODO
//        levelsListView.getSelectionModel().clearSelection();
        LevelManager.getInstance().getGameLevel().numPushesProperty().set(0);

        try{

            LevelManager.getInstance().setLevel(
                    LevelManager.getInstance().currentLevelNameProperty().get()
            );
            renderCanvas();

        }
        catch(InvalidMapException ex){ex.printStackTrace();}
        catch(Exception ex){ex.printStackTrace();}
//        LevelManager.getInstance().resetLevelTimer();
        LevelManager.getInstance().resetLevelTimer();
        LevelManager.getInstance().startLevelTimer();
        LevelManager.getInstance().incrementNumRestarts();
    }

    /**
     * Render the canvas with updated data
     * <p>
     * Hint: {@link MapRenderer}
     */
    private void renderCanvas() {
        //TODO
        // is it...?
        // perhaps should consider Invalid file error?
        gamePlayCanvas.getGraphicsContext2D().clearRect(0,0,gamePlayCanvas.getWidth(),gamePlayCanvas.getHeight());
        MapRenderer.render(gamePlayCanvas, LevelManager.getInstance().getGameLevel().getMap().getCells());

    }
}
