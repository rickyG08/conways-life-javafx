/*
 *  Copyright 2021 CNM Ingenuity, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package edu.cnm.deepdive;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main lifecycle class for Game of Life JavaFX application. This class not only implements {@link
 * #main(String[])} (to provide the standard Java application entry point) but also extends {@link
 * Application} and overrides {@link #start(Stage)} and {@link #stop()} to perform the JavaFX setup
 * and teardown tasks.
 */
public class Life extends Application {

  private static final String RESOURCE_BUNDLE =
      Life.class.getPackageName().replace('.', '/') + "/strings";
  private static final String LAYOUT_RESOURCE = "main.fxml";
  private static final String ICON_RESOURCE = "life.png";
  private static final String WINDOW_TITLE_KEY = "window_title";

  /**
   * Launches this JavaFX application by invoking the {@link #launch(String...)} method, passing
   * along any received command-line arguments.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
    FXMLLoader fxmlLoader = new FXMLLoader(Life.class.getResource(LAYOUT_RESOURCE), bundle);
    Parent root = fxmlLoader.load();
    // TODO Get reference to controller instance.
    Scene scene = new Scene(root);
    stage.setTitle(bundle.getString(WINDOW_TITLE_KEY));
    stage.getIcons().add(new Image(Life.class.getResourceAsStream(ICON_RESOURCE)));
    stage.setResizable(false); // TODO Make resizable.
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
    // TODO Set stage size.
  }

  @Override
  public void stop() throws Exception {
    // TODO Stop any background processing.
    super.stop();
  }

}
