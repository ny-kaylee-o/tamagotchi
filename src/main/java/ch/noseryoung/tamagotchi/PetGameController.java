package ch.noseryoung.tamagotchi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PetGameController {

    @FXML
    private Label slot1;

    @FXML
    private Label slot2;

    @FXML
    private Label slot3;

    @FXML
    private Label resultLabel;

    @FXML
    private Button gamble;

    private final PetGame game = new PetGame();

    private Pet pet; // injected by PetController before the window is shown

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @FXML
    private void onSpinButtonClick() {
        if (pet.getEnergy() <= 0) {
            resultLabel.setText("Too tired to play anymore!");
            gamble.setDisable(true);
            return;
        }

        int outcome = game.spin();

        slot1.setText(game.getSymbol1());
        slot2.setText(game.getSymbol2());
        slot3.setText(game.getSymbol3());

        pet.decreaseEnergy(10); // every spin costs energy

        if (outcome == PetGame.BIG_WIN) {
            pet.increaseHappiness(75);
            resultLabel.setText("JACKPOT! Big win!");
        } else if (outcome == PetGame.SMALL_WIN) {
            pet.increaseHappiness(25);
            resultLabel.setText("Small win!");
        } else {
            pet.decreaseHappiness(5);
            resultLabel.setText("No match. Try again!");
        }

        if (pet.getEnergy() <= 0) {
            gamble.setDisable(true);
        }
    }
}