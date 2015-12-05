package com.snakesonaplane.ctrl;

import com.snakesonaplane.jeu.GameMaster;
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
import java.util.*;


public class GameSetupCtrl implements Initializable {

    private GameMaster gameMaster;
    @FXML
    private Button playBtn;
    @FXML
    private Button addPlayerBtn;
    @FXML
    private Button removePlayerBtn;
    @FXML
    private ListView<PlayerSetupInfo> playerListView;
    private ObservableList<PlayerSetupInfo> playerList = FXCollections.observableArrayList(
            new PlayerSetupInfo(false, ""),
            new PlayerSetupInfo(true, "")
    );
    private Map<PlayerSetupInfo, PlayerCellCtrl> itemCtrlMapping = new WeakHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        playerListView.setItems(playerList);

        playerListView.setCellFactory(list -> new PlayerCell());

        playBtn.setOnAction(actionEvent -> this.gameMaster.onGameSetupCompleted(this.playerList));

        addPlayerBtn.setOnMouseClicked(actionEvent -> {
            playerList.add(new PlayerSetupInfo(false, ""));
            System.out.println(playerList);
        });

        removePlayerBtn.setOnAction(actionEvent -> Platform.runLater(() -> {

            int itemCount = playerList.size();

            if (itemCount > 0) {
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
        void onGameSetupCompleted(List<PlayerSetupInfo> playerSetupInfoList);
    }

    public static class PlayerSetupInfo {

        // PRNG for initial color
        private static Random random = new Random();


        public boolean isAi;
        public String name;
        public Color color;

        public PlayerSetupInfo(boolean isAi, String name) {
            this.isAi = isAi;
            this.name = name;
            this.color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0d);


        }

        @Override
        public String toString() {
            return "PlayerSetupInfo{" +
                    "isAi=" + isAi +
                    ", name='" + name + '\'' +
                    ", color=" + color +
                    '}';
        }
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
        private PlayerSetupInfo playerSetupInfo;

        public PlayerCellCtrl setPlayerSetupInfo(PlayerSetupInfo playerSetupInfo) {
            this.playerSetupInfo = playerSetupInfo;

            this.playerNameTxt.setText(playerSetupInfo.name);
            this.isAiChk.setSelected(playerSetupInfo.isAi);
            this.color.setValue(playerSetupInfo.color);
            return this;
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            playerNameTxt.setOnKeyReleased(keyEvent -> playerSetupInfo.name = playerNameTxt.getText());
            isAiChk.setOnMouseClicked(actionEvent -> playerSetupInfo.isAi = isAiChk.isSelected());
            color.setOnAction(actionEvent -> playerSetupInfo.color = color.getValue());
        }
    }

    class PlayerCell extends ListCell<PlayerSetupInfo> {

        private final URL CELL_RESOURCE_URL = PlayerCell.class.getClassLoader().getResource("player_cell.fxml");

        @Override
        public void updateItem(PlayerSetupInfo item, boolean empty) {
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
