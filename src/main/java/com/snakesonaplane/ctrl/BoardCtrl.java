package com.snakesonaplane.ctrl;

import com.google.common.math.LongMath;
import com.snakesonaplane.jeu.GameMaster;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class BoardCtrl implements Initializable {

    @FXML
    private GridPane boardGrid;

    @FXML
    private AnchorPane boardAnchorPane;

    @FXML
    private Button playBtn;

    private List<Rectangle> graphicalCells;

    private GameMaster gameMaster;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        int boardSize = 26;
        graphicalCells = new ArrayList<Rectangle>(boardSize);

        long lowerSquare = LongMath.sqrt(boardSize, RoundingMode.FLOOR);
        long higherSquare = LongMath.sqrt(boardSize, RoundingMode.CEILING);

        for (int row = 0; row < higherSquare; row++) {
            for (int col = 0; col < higherSquare && boardSize > 0; col++) {
                boardSize--;
                Rectangle cell = new Rectangle();
                graphicalCells.add(cell);
                Color color = ((row + col) % 2 == 0) ? Color.WHITE : Color.WHEAT;
                cell.setFill(color);
                this.boardGrid.add(cell, col, row);
                cell.widthProperty().bind(this.boardGrid.widthProperty().divide(higherSquare));
                cell.heightProperty().bind(this.boardGrid.heightProperty().divide(lowerSquare));
            }
        }


        playBtn.setOnAction(actionEvent -> {
            System.out.println("PLAY!");
        });

        drawSnake(4, 13);
        drawLadder(2, 20);
    }

    public void drawLine(Color color, int fromCell, int toCell) {

        Line line = new Line();

        line.startXProperty().bind(graphicalCells.get(fromCell).layoutXProperty());
        line.startYProperty().bind(graphicalCells.get(fromCell).layoutYProperty());

        line.endXProperty().bind(graphicalCells.get(toCell).layoutXProperty());
        line.endYProperty().bind(graphicalCells.get(toCell).layoutYProperty());

        this.boardAnchorPane.getChildren().add(line);

        line.setFill(color);
        line.setStroke(color);
        line.setStrokeWidth(2.0d);
        line.toFront();
    }


    public void drawLadder(int fromCell, int toCell) {
        drawLine(Color.GREEN, fromCell, toCell);
    }

    public void drawSnake(int fromCell, int toCell) {
        drawLine(Color.RED, fromCell, toCell);
    }

    public void setGameMaster(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }
}
