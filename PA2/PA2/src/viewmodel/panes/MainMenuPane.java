package viewmodel.panes;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import viewmodel.Config;
import viewmodel.SceneManager;

/**
 * Represents the main menu in the game
 */
public class MainMenuPane extends BorderPane {

    private VBox container;
    private Label title;
    private Button playButton;
    private Button levelEditorButton;
    private Button settingsButton;
    private Button quitButton;

    /**
     * Instantiate the member components and connect and style them. Also set the callbacks.
     */
    public MainMenuPane() {
        //TODO
        container = new VBox();
        title = new Label("Sokoban");
        playButton = new Button("Play");
        levelEditorButton = new Button("Level Editor");
        settingsButton = new Button("About / Settings");
        quitButton = new Button("Quit");

        connectComponents();

        styleComponents();
        setCallbacks();
    }

    /**
     * Connects the components together (think adding them into another, setting their positions, etc).
     * Use this for reference.
     */
    private void connectComponents() {
//        title.setStyle(Config.CSS_STYLES);
//        playButton.setStyle(Config.CSS_STYLES);
//        levelEditorButton.setStyle(Config.CSS_STYLES);
//        settingsButton.setStyle(Config.CSS_STYLES);
//        quitButton.setStyle(Config.CSS_STYLES);
        container.getChildren().addAll(
                title,
                playButton,
                levelEditorButton,
                settingsButton,
                quitButton
        );
        this.setCenter(container);
//        container.getStylesheets().add(Config.CSS_STYLES);
    }

    /**
     * Apply CSS styling to components.
     */
    private void styleComponents() {
        //TODO
//        SceneManager.getInstance().mainMenuScene.getStylesheets.add();

        container.getStyleClass().add("big-vbox");
        title.setStyle("-fx-font-size: 50;");
        playButton.getStyleClass().add("big-button");

        levelEditorButton.getStyleClass().add("big-button");
        settingsButton.getStyleClass().add("big-button");
        quitButton.getStyleClass().add("big-button");

    }

    /**
     * Set the event handlers for the 4 buttons, 3 of which switch to different scene, and 1 of which exits the program.
     */
    private void setCallbacks() {
        //TODO
        quitButton.setOnMouseClicked(e->Platform.exit());
        playButton.setOnMouseClicked(e->{SceneManager.getInstance().showLevelSelectMenuScene();});
        levelEditorButton.setOnMouseClicked(e->{SceneManager.getInstance().showLevelEditorScene();});
        settingsButton.setOnMouseClicked(e->{SceneManager.getInstance().showSettingsMenuScene();});

    }
}
