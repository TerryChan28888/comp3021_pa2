package viewmodel.customNodes;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.concurrent.Callable;

// is it...? is the following neccessary?
import javafx.beans.binding.StringBinding;
/**
 * Displays info about the current level being played by the user.
 */
public class GameplayInfoPane extends VBox {
    private final Label levelNameLabel = new Label();
    private final Label timerLabel = new Label();
    private final Label numMovesLabel = new Label();
    private final Label numRestartsLabel = new Label();

    /**
     * @param levelNameProperty   the StringProperty which holds the current level name
     * @param timerProperty       the IntegerProperty which holds the number of seconds the current
     *                            level has been active since the most recent start/restart
     * @param numMovesProperty    the IntegerProperty which holds the number of moves the user's
     *                            Sokoban character has made on the map
     * @param numRestartsProperty the IntegerProperty which tracks how many times the user
     *                            has restarted the current level
     */
    public GameplayInfoPane(StringProperty levelNameProperty, IntegerProperty timerProperty, IntegerProperty numMovesProperty, IntegerProperty numRestartsProperty) {
        bindTo(levelNameProperty, timerProperty, numMovesProperty, numRestartsProperty);
        this.getChildren().addAll(levelNameLabel, timerLabel, numMovesLabel, numRestartsLabel);
    }

    /**
     * @param s Seconds duration
     * @return A string that formats the duration stopwatch style
     */
    private static String format(int s) {
//        Duration d = Duration.of(s, SECONDS);
//
//        int seconds = d.toSecondsPart();
//        int minutes = d.toMinutesPart();
//
//        return String.format("%02d:%02d", minutes, seconds);
        //allow for compiling using jdk8
        return String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }

    /**
     * This function binds the text properties of the labels to the properties passed as parameters.
     * <p>
     * Hint: {@link Label#textProperty()}, {@link Bindings#createStringBinding(Callable, Observable...)} are
     * required. Follow the following format examples inside the square brackets:
     * <p>
     * levelNameLabel: [Level: 1.txt]
     * timerLabel: [Time: 00:01]
     * numMovesLabel: [Moves: 1]
     * numRestartsLabel: [Restarts: 0]
     *
     * @param levelNameProperty   the StringProperty which holds the current level name
     * @param timerProperty       the IntegerProperty which holds the number of seconds the current
     *                            level has been active since the most recent start/restart
     * @param numMovesProperty    the IntegerProperty which holds the number of moves the user's
     *                            Sokoban character has made on the map
     * @param numRestartsProperty the IntegerProperty which tracks how many times the user
     *                            has restarted the current level
     */
    private void bindTo(StringProperty levelNameProperty, IntegerProperty timerProperty, IntegerProperty numMovesProperty, IntegerProperty numRestartsProperty) {
        //TODO
        timerLabel.textProperty().bind(
                new StringBinding() {
                    {bind(timerProperty);}
                    @Override
                    protected String computeValue() {
                        return "Timer : "+format(timerProperty.get());
                    }
                }
        );

        levelNameLabel.textProperty().bind(
                new StringBinding() {
                    {bind(levelNameProperty);}
                    @Override
                    protected String computeValue() {
                        return "Level : "+levelNameProperty.get();
                    }
                }
        );

        numMovesLabel.textProperty().bind(
                new StringBinding() {
                    {bind(numMovesProperty);}
                    @Override
                    protected String computeValue() {
                        return "Moves : "+numMovesProperty.get();
                    }
                }
        );

        numRestartsLabel.textProperty().bind(
                new StringBinding() {
                    {bind(numRestartsProperty);}
                    @Override
                    protected String computeValue() {
                        return "Restarts : "+numRestartsProperty.get();
                    }
                }
        );

    }
}
