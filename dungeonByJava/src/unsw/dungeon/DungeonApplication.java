package unsw.dungeon;

import java.awt.TextField;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");
        
        final Popup popup = new Popup();
        
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("advanced.json");

        DungeonController controller = dungeonLoader.loadController(popup,primaryStage,this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
