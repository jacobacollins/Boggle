import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Timer {

    public int getTime() {
        return time;
    }

    private  int time;

    public Timer(Label updated, int mil){

        Timeline timeline = new Timeline();
        timeline .setCycleCount(Timeline.INDEFINITE);
        time = mil;

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {
                            @Override

                            public void handle(ActionEvent event) {


                                    updated.setAlignment(Pos.TOP_CENTER);
                                updated.setAlignment(Pos.CENTER);

                                updated.setText(time / 1000 / 60 + " min " + ((time / 1000) % 60) + " sec");

                                    updated.setAlignment(Pos.CENTER);
                                    if(time > 0) {
                                        time -= 1000;
                                    }
                            }
                        })
        );
        timeline.playFromStart();
    }
}
