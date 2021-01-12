package edu.cnm.deepdive.controller;

import edu.cnm.deepdive.model.Terrain;
import edu.cnm.deepdive.view.TerrainView;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class MainController {

  private static final int DEFAULT_WORLD_SIZE = 500;
  private static final String STOP_KEY = "stop";
  private static final String START_KEY = "start"; // ??????
  private static final String GENERATION_FORMAT_KEY = "generation_format";
  private static final String POPULATION_FORMAT_KEY = "population_format";

  private Terrain terrain;
  private Random rng;
  private boolean running;
  private DisplayUpdater updater;
  private String generationFormat;
  private String populationFormat;

  @FXML
  private Text generationDisplay;
  @FXML
  private Text populationDisplay;
  @FXML
  private TerrainView terrainView;
  @FXML
  private ToggleButton toggleRun;
  @FXML
  private Slider densitySlider;
  @FXML
  private Button reset;
  @FXML
  private ResourceBundle resources;

  @FXML
  private void initialize() {
    rng = new Random();
    updater = new DisplayUpdater();
    generationFormat = resources.getString(GENERATION_FORMAT_KEY);
    populationFormat = resources.getString(POPULATION_FORMAT_KEY);
    reset(null);
  }

  @FXML
  private void toggleRun(ActionEvent actionEvent) {
    if (toggleRun.isSelected()) {
      start();
    } else {
      stop();
    }
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
    terrain = new Terrain(DEFAULT_WORLD_SIZE, densitySlider.getValue() / 100, rng);
    terrainView.setTerrain(terrain);
    updateDisplay();
  }

  private void start() {
    running = true;
    toggleRun.setText(resources.getString(STOP_KEY));
    reset.setDisable(true);
    new Runner().start();
  }

  public void stop() {
    running = false;
    updater.stop();
    toggleRun.setDisable(true);
  }

  private void updateDisplay() {
    long generation = terrain.getIterationCount();
    long population = terrain.getPopulation();
    terrainView.draw();
    generationDisplay.setText(String.format(generationFormat, generation));
    populationDisplay.setText(String.format(populationFormat, population));
  }

  private void updateControls() {
    toggleRun.setText(resources.getString(START_KEY));
    toggleRun.setSelected(false);
    toggleRun.setDisable(false);
    reset.setDisable(false);

  }

  private class DisplayUpdater extends AnimationTimer {

    @Override
    public void handle(long now) {
      updateDisplay();
    }

  }

  private class Runner extends Thread {

    // TODO Add fields for recognizing a terrain containing only oscillators & still lifes.

    @Override
    public void run() {
      while (running) {
        terrain.iterate();
        // TODO Examine terrain.checksum for stopping condition.
      }
      Platform.runLater(() -> {
        updateDisplay();
        updateControls();
      });
    }
  }

}
