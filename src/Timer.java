import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {

    private  int time;
    private  int seconds;
    private int minutes;
    public Timer(Label updated, int mil){

        Timeline timeline = new Timeline();
        timeline .setCycleCount(Timeline.INDEFINITE);
        time = mil;
        seconds = (time /1000);
        minutes = (seconds / 60) ;
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override

                            public void handle(ActionEvent event) {


                                updated.setText(minutes + " min " + seconds + " sec");

                                seconds--;

                            }
                        })
        );
        timeline.playFromStart();
    }
}
