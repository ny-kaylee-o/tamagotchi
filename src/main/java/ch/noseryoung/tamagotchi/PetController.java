package ch.noseryoung.tamagotchi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PetController {

    @FXML
    private Label energyLabel;

    @FXML
    private Label happinessLabel;

    @FXML
    private Label appetiteLabel;

    @FXML
    private ImageView petImageView;

    @FXML
    private Button feedButton;

    @FXML
    private Button sleepButton;

    @FXML
    private Button slotsButton;

    @FXML
    private Pane nightOverlay;

    private final Pet pet = new Pet(100, 100, 100);

    private boolean sleeping = false;

    private static final double TICK_SECONDS = 5;
    private static final int ENERGY_DECAY = 3;
    private static final int APPETITE_DECAY = 2;
    private static final int HAPPINESS_DECAY = 4;
    private static final int SLEEP_ENERGY_GAIN = 15;
    private static final int FEED_APPETITE_GAIN = 20;
    private static final int FEED_HAPPINESS_GAIN = 10;
    private static final double FEED_HAPPINESS_CHANCE = 0.35;

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(TICK_SECONDS), event -> runTasks())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        updateGUI();
    }

    private void runTasks() {
        if (sleeping) {
            pet.increaseEnergy(SLEEP_ENERGY_GAIN);
        } else {
            pet.decreaseEnergy(ENERGY_DECAY);
            pet.decreaseAppetite(APPETITE_DECAY);
            pet.decreaseHappiness(HAPPINESS_DECAY);
        }
        updateGUI();
    }

    @FXML
    private void onFeedButtonClick() {
        if (sleeping) {
            return;
        }

        pet.increaseAppetite(FEED_APPETITE_GAIN);

        boolean luckyBonus = Math.random() < FEED_HAPPINESS_CHANCE;
        if (luckyBonus) {
            pet.increaseHappiness(FEED_HAPPINESS_GAIN);
        }

        updateGUI();
    }

    @FXML
    private void onSleepButtonClick() {
        sleeping = !sleeping;
        sleepButton.setText(sleeping ? "wake up" : "sleep");
        feedButton.setDisable(sleeping);
        slotsButton.setDisable(sleeping);
        nightOverlay.setVisible(sleeping);
        updateGUI();
    }

    @FXML
    private void onSlotsButtonClick() {
        if (sleeping) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("petgame-view.fxml"));
            Parent root = loader.load();

            PetGameController gameController = loader.getController();
            gameController.setPet(pet);

            Stage popup = new Stage();
            popup.setTitle("Slot Machine");
            popup.setScene(new Scene(root));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait(); // waits here until the popup is closed

            updateGUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGUI() {
        energyLabel.setText("energy\n" + pet.getEnergy() + "/100");
        appetiteLabel.setText("appetite\n" + pet.getAppetite() + "/100");
        happinessLabel.setText("happiness\n" + pet.getHappiness() + "/100");
        updateSprite();
    }

    private void updateSprite() {
        String fileName = "durr_neutral.png";

        if (sleeping) {
            fileName = "durr_sleep.png";
        } else if (pet.getAppetite() >= 95) {
            fileName = "durr_fat.png";
        } else if (pet.getEnergy() <= 30) {
            fileName = "durr_tired.png";
        } else if (pet.getAppetite() <= 30) {
            fileName = "durr_hungry.png";
        } else if (pet.getHappiness() <= 30) {
            fileName = "durr_bored.png";
        } else if (pet.getHappiness() >= 70) {
            fileName = "durr_happy.png";
        }

        Image image = new Image(getClass().getResourceAsStream("/sprites/" + fileName));
        petImageView.setImage(image);

        updateNightTint();
    }


    private void updateNightTint() {
        if (!sleeping) {
            petImageView.setEffect(null);
            return;
        }

        ColorAdjust nightTint = new ColorAdjust();
        nightTint.setBrightness(-0.25);
        petImageView.setEffect(nightTint);
    }
}