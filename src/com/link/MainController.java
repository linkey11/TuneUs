package com.link;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import sun.plugin2.ipc.Event;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    String user = String.format("<%s> ", LoginController.nickname);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_box.setText(Main.session.session_id);
        queue.setItems(Main.queue.listview_data);
        // Setup chat bar event handler
        chat_bar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    if (!chat_bar.getText().trim().isEmpty()) {
                        chat_history.appendText(user + chat_bar.getText() + "\n");
                        String message = chat_bar.getText();
                        chat_bar.clear();
                        //Do stuff with the sent chat message
                        String data = String.format("?id=%s&message=%s", Main.session.session_id, message);
                        Main.session.asyncReadPage(Main.session.getUrl("SEND_MESSAGE") + data);
                    }
                }
            }
        });
        volume_slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                double new_volume = volume_slider.getValue();
                Main.player.setVolume(new_volume);
            }
        });
        // initialize chat and queue listeners
        Chat main_chat = new Chat();
    }

    @FXML
    private TextArea id_box;

    @FXML
    private TextField chat_bar;

    @FXML
    private TextArea chat_history;

    @FXML
    private Slider volume_slider;

    @FXML
    private ListView queue;

    @FXML
    public ProgressIndicator progressBar;

    @FXML
    public void setVolume(int level){
        volume_slider.setValue(0);
    }

    @FXML
    private void addSong(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/link/resources/gui/add_song.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Add Song");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {e.printStackTrace();}
    }

    public void setProgress(int degree){
        progressBar.setProgress(degree);
    }
}
