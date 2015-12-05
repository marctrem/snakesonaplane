package com.snakesonaplane.ctrl;

import com.google.common.math.LongMath;
import com.snakesonaplane.jeu.GameMaster;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;


public class BoardCtrl implements Initializable {

    @FXML
    private GridPane boardGrid;

    private GameMaster gameMaster;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        long boardSize = 26;

        long lowerSquare = LongMath.sqrt(boardSize, RoundingMode.FLOOR);
        long higherSquare = LongMath.sqrt(boardSize, RoundingMode.CEILING);

        for (int row = 0; row < higherSquare; row++) {
            for (int col = 0; col < higherSquare && boardSize > 0; col++) {
                boardSize--;
                Rectangle cell = new Rectangle();
                Color color = ((row + col) % 2 == 0) ? Color.WHITE : Color.WHEAT;
                cell.setFill(color);
                cell.setStroke(Color.GRAY);
                this.boardGrid.add(cell, col, row);
                cell.widthProperty().bind(this.boardGrid.widthProperty().divide(higherSquare));
                cell.heightProperty().bind(this.boardGrid.heightProperty().divide(lowerSquare));
            }
        }


    }

    public void setGameMaster(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }
}
