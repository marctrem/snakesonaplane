package com.snakesonaplane.ctrl;

import com.google.common.math.IntMath;
import com.snakesonaplane.jeu.Board;
import com.snakesonaplane.jeu.BoardElement;
import com.snakesonaplane.jeu.GameMaster;
import com.snakesonaplane.jeu.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class BoardCtrl implements Initializable {

    private static final float TOKEN_RADIUS = 20;

    @FXML
    private GridPane boardGrid;
    @FXML
    private AnchorPane boardAnchorPane;
    @FXML
    private Button playBtn;
    @FXML
    private Button undoBtn;
    @FXML
    private Button redoBtn;
    @FXML
    private Label statusLbl;

    private List<Circle> tokensOnBoard;

    private List<Rectangle> graphicalCells;
    private GameMaster gameMaster;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        playBtn.setOnAction(actionEvent -> {
            gameMaster.onDiceRolled();
            updateButtonState();

        });

        undoBtn.setOnAction(actionEvent -> {
            gameMaster.onUndoRequested();
            updateButtonState();
        });
        redoBtn.setOnAction(actionEvent -> {
            gameMaster.onRedoRequested();
            updateButtonState();
        });

        tokensOnBoard = new ArrayList<>();
    }

    private void updateButtonState() {
        undoBtn.setDisable(!gameMaster.isUndoAvailable());
        redoBtn.setDisable(!gameMaster.isRedoAvailable());
    }

    public void setPlayBtnClickable(boolean clickable) {
        this.playBtn.setDisable(!clickable);
    }

    public void disableAllButtons() {
        undoBtn.setDisable(true);
        redoBtn.setDisable(true);
        playBtn.setDisable(true);
    }

    public void setupGrid(int boardSize) {
        graphicalCells = new ArrayList<>(boardSize);


        int lowerSquare = IntMath.sqrt(boardSize, RoundingMode.FLOOR);
        int higherSquare = IntMath.sqrt(boardSize, RoundingMode.CEILING);

        int gridWidth = higherSquare;
        int gridHeight = IntMath.divide(boardSize, higherSquare, RoundingMode.CEILING);


        for (int row = 0; row < gridHeight; row++) {
            for (int col = 0; col < gridWidth && boardSize > 0; col++) {
                boardSize--;
                Rectangle cell = new Rectangle();
                graphicalCells.add(cell);
                Color color = (boardSize % 2 == 0) ? Color.WHITE : Color.WHEAT;
                cell.setFill(color);
                Label label = new Label("" + (row * higherSquare + (col + 1)));

                int xPositionInBoard = (row % 2 == 0) ? col : (gridWidth - col - 1);
                int yPositionInBoard = gridHeight - row - 1;

                this.boardGrid.add(cell, xPositionInBoard, yPositionInBoard);
                this.boardGrid.add(label, xPositionInBoard, yPositionInBoard);
                cell.widthProperty().bind(this.boardGrid.widthProperty().divide(higherSquare));
                cell.heightProperty().bind(this.boardGrid.heightProperty().divide(lowerSquare));
            }
        }
    }

    public void setStatusMessage(String message) {
        this.statusLbl.setText(message);
    }

    public void updatePlayersPosition(List<Player> players) {

        tokensOnBoard.forEach(circle1 -> this.boardAnchorPane.getChildren().remove(circle1));

        for (Player player : players) {

            if (player.getPosition() >= 0) {


                Rectangle cell = graphicalCells.get(((int) player.getPosition()));
                Circle circle = new Circle(TOKEN_RADIUS);
                tokensOnBoard.add(circle);

                circle.centerXProperty().bind(cell.layoutXProperty().add(cell.widthProperty().multiply(players.indexOf(player)).divide(players.size() * 2)).add(cell.widthProperty().divide(4)));
                circle.centerYProperty().bind(cell.layoutYProperty().add(cell.heightProperty().multiply(players.indexOf(player)).divide(players.size() * 2)).add(cell.heightProperty().divide(4)));

                if (!this.boardAnchorPane.getChildren().contains(circle)) {
                    circle.setFill((Paint) player.getColor().getRawColorObject());
                    circle.setStroke(Color.BLACK);
                    circle.setOpacity(0.7);
                    this.boardAnchorPane.getChildren().add(circle);
                }
                circle.toFront();
            }
        }
    }

    public void drawLine(Color color, int fromCell, int toCell) {

        Line line = new Line();

        Rectangle rectangleFrom = graphicalCells.get(fromCell);
        Rectangle rectangleTo = graphicalCells.get(toCell);

        line.startXProperty().bind(rectangleFrom.layoutXProperty().add(rectangleFrom.widthProperty().divide(2)));
        line.startYProperty().bind(rectangleFrom.layoutYProperty().add(rectangleFrom.heightProperty().divide(2)));

        line.endXProperty().bind(rectangleTo.layoutXProperty().add(rectangleTo.widthProperty().divide(2)));
        line.endYProperty().bind(rectangleTo.layoutYProperty().add(rectangleTo.heightProperty().divide(2)));

        this.boardAnchorPane.getChildren().add(line);

        line.setFill(color);
        line.setStroke(color);
        line.setStrokeWidth(2.0d);
        line.toFront();
    }

    public void setupBoard(Board board) {

        this.setupGrid(board.getNumberOfCells());

        for (BoardElement boardElement : board.getBoardElements()) {
            if (boardElement.destination > boardElement.origin) {
                this.drawLadder(boardElement.origin, boardElement.destination);
            } else {
                this.drawSnake(boardElement.origin, boardElement.destination);
            }
        }
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

    public interface DiceRollListener {
        void onDiceRolled();
    }

}
