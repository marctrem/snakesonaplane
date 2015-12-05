package com.snakesonaplane.ctrl;

import com.snakesonaplane.jeu.GameMaster;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuCtrl implements Initializable {

    @FXML
    private Button playBtn;
    private GameMaster gameMaster;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBtn.setOnAction(actionEvent -> {
            this.gameMaster = new GameMaster();
            this.gameMaster.startGame();
        });
    }
}
