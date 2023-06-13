package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;

public class MainSceneController {

    public Button btnCopy;
    public Button btnDelete;
    public Button btnMove;
    public Button btnReset;
    public Button btnSource;
    public Button btnTarget;
    public Label lblMessage;
    public TextField txtSource;
    public TextField txtTarget;

    private File srcFile;
    private File trgtFile;


    public void btnCopyOnAction(ActionEvent event) {

    }


    public void btnDeleteOnAction(ActionEvent event) {
        if (srcFile.isDirectory()) {
            deleteFile(srcFile);
        }
        srcFile.delete();
        txtSource.clear();
        txtTarget.clear();
        srcFile = null;
        trgtFile = null;
        lblMessage.setText("Complete..!");
        System.out.println("Complete");
    }

    private void deleteFile(File srcFile) {
        File[] files = srcFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFile(file);
            }
            file.delete();
        }
    }


    public void btnMoveOnAction(ActionEvent event) {

    }


    public void btnResetOnAction(ActionEvent event) {
        txtSource.clear();
        txtTarget.clear();
        lblMessage.setText("");
        srcFile = null;
        trgtFile = null;
        txtSource.requestFocus();
    }


    public void btnSourceOnAction(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogTitle("Select a Folder / File");
        int i = chooser.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            srcFile = chooser.getSelectedFile();
            txtSource.setText(srcFile.getPath());
        }

        lblMessage.setText("");
        buttonEnable();
    }


    public void btnTargetOnAction(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogTitle("Select a Folder");
        int i = chooser.showOpenDialog(null);
        if (i == JFileChooser.APPROVE_OPTION) {
            trgtFile = chooser.getSelectedFile();
            txtTarget.setText(trgtFile.getPath());
        }

        buttonEnable();
    }

    private void buttonEnable() {
        btnCopy.setDisable(srcFile == null || trgtFile == null);
        btnDelete.setDisable(srcFile == null);
        btnMove.setDisable(srcFile == null || trgtFile == null || srcFile.getPath().equals(trgtFile.getPath()));
    }

}
