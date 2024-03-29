package model;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.Exceptions.InvalidMapException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

//is it needed...? are there any other way besides Collectors in loadLevelNamesFromDisk()?
import java.util.stream.Collectors;

/**
 * Keeps track of the current GameLevel and level name. Also tracks information
 * that's related to this game level but not specific to the map of the game
 * level, i.e. how long the player has been playing the level, how many restarts, etc.
 */
public class LevelManager {
    private static final LevelManager ourInstance = new LevelManager();
    private final ObservableList<String> levelNames = FXCollections.observableArrayList();
    private final StringProperty curLevelNameProperty = new SimpleStringProperty();
    private final IntegerProperty curGameLevelExistedDuration = new SimpleIntegerProperty();
    private final IntegerProperty curGameLevelNumRestarts = new SimpleIntegerProperty();
    private final GameLevel gameLevel = new GameLevel();
    private Timer t = new Timer(true); //declare as daemon, so application exits when Platform.exit is called
    private String mapDirectory = "";

    private LevelManager() {
    }

    public static LevelManager getInstance() {
        return ourInstance;
    }

    public void setMapDirectory(String mapDirectory) {
        this.mapDirectory = mapDirectory;
    }

    public String getMapDirectory() {
        return this.mapDirectory ;
    }

    public GameLevel getGameLevel() {
        return gameLevel;
    }

    /**
     * Clears and loads the the level names into {@link #levelNames}. Can be done succinctly using
     * Streams, Predicates, and Consumers. Load the files by alphabetical sorted order.
     * <p>
     * Hints: Files.walk(Paths.get(mapDirectory), 1) returns a Stream of files 1 folder deep
     */

    // leave this later ......
    public void loadLevelNamesFromDisk() {
        //TODO
        levelNames.clear();
        try{
            // sample doesn't show folder in ListView
//            System.out.println(mapDirectory);
            List<Path> new_levelNames=
//            levelNames.addAll(
                    Files.walk(Paths.get(mapDirectory),1).filter(Files::isRegularFile).map(Path::getFileName)
//                    .forEach(System.out::println)
//                            .forEach(levelNames::add)
//                            .forEach(levelNames::)
//                            .toString()
//.toArray()
//                    .collect(ListView::new, ObservableList::add,ObservableList::addAll)
            .collect(Collectors.toList())
//                            .collect(Collectors.toCollection(FXCollections::observableArrayList))
//                            .collect(Collectors.toCollection(ObservableList::new))
//                            .collect(Collectors.toList(FXCollections::observableArrayList))
//                            .collect(ListView::new, ObservableList::add, ObservableList::addAll)
//                            .collect(ObservableList::new, ObservableList::add, ObservableList::addAll)
//                            .collect(Collectors.collectingAndThen(toList(), l -> FXCollections.observableArrayList(l)))
//            .collect(Collectors.toCollection(ObservableArrayList::new))

                    ;
//System.out.println(new_levelNames);
//for(l : new_levelNames ){levelNames.add(l);}
for(Object l : new_levelNames){levelNames.add(l.toString());}
//            levelNames.addAll(new_levelNames);
//                    .collect(Collectors.toList() );
//            levelNames.getItems().
//            levelNames = Files.walk(Paths.get(mapDirectory),1).sorted()
//                    Files.walk(Paths.get(mapDirectory),1).sorted().collect(ListView::new, ObservableList::add,ObservableList::addAll);
//                            .filter(p -> p.toString().endsWith(".txt"))
            ;
        }
        catch(Exception e){}

    }

    public ObservableList<String> getLevelNames() {
        return levelNames;
    }

    public StringProperty currentLevelNameProperty() {
        return curLevelNameProperty;
    }

    /**
     * Sets the current level based on the level name (i.e. the map filename). Although the level existed duration
     * should be reset, the timer should not be started yet.
     * <p>
     * Hints: don't forget to update the level name and existed duration properties, and load the map for
     * the GameLevel object.
     *
     * @param levelName The level name to set
     * @throws InvalidMapException if the map was invalid
     */
    public void setLevel(String levelName) throws InvalidMapException {
        //TODO
        if(levelName==null){}
        else {
            curGameLevelExistedDuration.setValue(0);
            curLevelNameProperty.setValue(levelName);

//        GameLevel new_game = new GameLevel();
//        try{
//            if(levelName==null){throw new InvalidMapException();}else{}


            gameLevel.loadMap(mapDirectory + "\\" + levelName);
//        }catch(InvalidMapException e){System.out.println("what can i do, now?");e.printStackTrace();throw e;}
//        finally {}
        }
    }

    /**
     * Starts the timer, which updates {@link #curGameLevelExistedDuration} every second.
     * <p>
     * Hint: {@link java.util.Timer#scheduleAtFixedRate(TimerTask, long, long)} and
     * {@link javafx.application.Platform#runLater(Runnable)} are required
     */
    public void startLevelTimer() {
//        t = new Timer(true); // this line makes the timer works after "next level", perhaps because cancel() in resettimer() is executed after run() in this method

        //TODO
//        System.out.println("time machine success start!!!!!!!!");
        t.scheduleAtFixedRate(

                new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(  ()->{curGameLevelExistedDuration.setValue(curGameLevelExistedDuration.getValue()+1);}  );

            }
        }, 1000,1000);
    }

    /**
     * Cancels the existing timer and assigns it to a new instance
     */
    public void resetLevelTimer() {
        t.cancel();


        t = new Timer(true);

    }

    /**
     * Increment the number of restarts the user has performed on the current GameLevel
     */
    public void incrementNumRestarts() {
        curGameLevelNumRestarts.set(curGameLevelNumRestarts.get() + 1);
    }

    /**
     * Reset the number of restarts the user has performed on the current GameLevel
     */
    public void resetNumRestarts() {
        curGameLevelNumRestarts.set(0);
    }

    /**
     * @return The name of the level which appears immediately after the current level name inside {@link #levelNames}.
     * If the current level is the last level, this function returns null. You may assume that the current level
     * name is always valid.
     */
    public String getNextLevelName() {
        //TODO
//        if( == ){}
        String lastElement = null;
        if (!levelNames.isEmpty()) {

            lastElement = levelNames.get(levelNames.size() - 1);

        }

//        int i =0;
//        for(; i<levelNames.size();i++){
//            if(curLevelNameProperty.get()==levelNames.get(i)){break;}
//        }

        int level_index = levelNames.indexOf(curLevelNameProperty.get());

        if(curLevelNameProperty.get() == lastElement){return null;}//NOTE: You may also change this line
        else{return levelNames.get(level_index+1);}
    }

    public IntegerProperty curGameLevelExistedDurationProperty() {
        return curGameLevelExistedDuration;
    }

    public IntegerProperty curGameLevelNumRestartsProperty() {
        return curGameLevelNumRestarts;
    }
}

