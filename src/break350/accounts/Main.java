package break350.accounts;

import java.io.File;

import break350.accounts.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();

			File fileMain = new File(Configs.getProperties().getProperty(
					Configs.pathToMainFXML));
			loader.setLocation(fileMain.toURI().toURL());
			Parent content = (Parent) loader.load();
			Scene scene = new Scene(content);

			File fileCss = new File(Configs.getProperties().getProperty(
					Configs.pathToMainCSS));
			scene.getStylesheets()
					.add(fileCss.toURI().toURL().toExternalForm());

			primaryStage.setScene(scene);

			MainController mainController = loader.getController();
			mainController.setStage(primaryStage);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
