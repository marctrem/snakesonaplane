package com.snakesonaplane.ctrl;

import com.snakesonaplane.jeu.GameMaster;
import com.snakesonaplane.jeu.Player;
import com.snakesonaplane.wrappers.ui.javafx.ColorWrapper;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.WeakHashMap;


public class GameSetupCtrl implements Initializable {

    private GameMaster gameMaster;
    @FXML
    private Button playBtn;
    @FXML
    private Button addPlayerBtn;
    @FXML
    private Button removePlayerBtn;
    @FXML
    private ListView<Player> playerListView;
    @FXML
    private ChoiceBox<String> algoSelectionList;
    @FXML
    private ChoiceBox<Integer> numberOfFacesList;
    private ObservableList<Player> playerList = FXCollections.observableArrayList(
            new Player("Player 1", false),
            new Player("Player 2", true)
    );
    private Map<Player, PlayerCellCtrl> itemCtrlMapping = new WeakHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.algoSelectionList.setItems(new ImmutableObservableList<>("Algo 1", "Algo 2", "Algo 3"));
        this.algoSelectionList.setValue(algoSelectionList.getItems().get(0));

        this.numberOfFacesList.setItems(new ImmutableObservableList<>(6, 8, 20));
        this.numberOfFacesList.setValue(numberOfFacesList.getItems().get(0));

        playerListView.setItems(playerList);

        playerListView.setCellFactory(list -> new PlayerCell());

        playBtn.setOnAction(actionEvent -> this.gameMaster.onGameSetupCompleted(this.playerList, (this.algoSelectionList.getValue()), this.numberOfFacesList.getValue()));

        addPlayerBtn.setOnMouseClicked(actionEvent -> {
            playerList.add(new Player("", false));
            System.out.println(playerList);
        });


        removePlayerBtn.setOnAction(actionEvent -> Platform.runLater(() -> {

            int itemCount = playerList.size();

            if (itemCount > 2) {
                playerList.remove(playerListView.getSelectionModel().getSelectedItem());
            }
            System.out.println(playerList);
        }));
    }

    public GameSetupCtrl setGameMaster(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
        return this;
    }

    public interface GameSetupReceiver {
        void onGameSetupCompleted(List<Player> playerSetupInfoList, String algoName, Integer numberOfFacesOnDice);
    }

    public static class PlayerCellCtrl implements Initializable {


        @FXML
        public Parent root;
        @FXML
        public TextField playerNameTxt;
        @FXML
        public CheckBox isAiChk;
        @FXML
        public ColorPicker color;
        private Player playerSetupInfo;

        public PlayerCellCtrl setPlayerSetupInfo(Player playerSetupInfo) {
            this.playerSetupInfo = playerSetupInfo;

            this.playerNameTxt.setText(playerSetupInfo.getName());
            this.isAiChk.setSelected(playerSetupInfo.isAi());
            this.color.setValue((Color) playerSetupInfo.getColor().getRawColorObject());
            return this;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            playerNameTxt.setOnKeyReleased(keyEvent -> playerSetupInfo.setName(playerNameTxt.getText()));
            isAiChk.setOnMouseClicked(actionEvent -> playerSetupInfo.setAi(isAiChk.isSelected()));
            color.setOnAction(actionEvent -> playerSetupInfo.setColor(new ColorWrapper(color.getValue())));
        }
    }

    class PlayerCell extends ListCell<Player> {

        private final URL CELL_RESOURCE_URL = PlayerCell.class.getClassLoader().getResource("player_cell.fxml");

        @Override
        public void updateItem(Player item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {

                PlayerCellCtrl ctrl = itemCtrlMapping.getOrDefault(item, null);

                if (null == ctrl) {
                    try {
                        FXMLLoader loader = new FXMLLoader(CELL_RESOURCE_URL);
                        loader.load();
                        ctrl = loader.getController();
                        itemCtrlMapping.put(item, ctrl);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                ctrl.setPlayerSetupInfo(item);
                setGraphic(ctrl.root);
            }
        }
    }
}
