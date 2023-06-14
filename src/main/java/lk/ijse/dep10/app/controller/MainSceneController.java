package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
        if (srcFile.isDirectory()) {
            File mkFile = new File(trgtFile, srcFile.getName());
            if (srcFile.getParentFile().getPath().equals(trgtFile.getPath())) {
                mkFile = new File(trgtFile, "(copy)" + srcFile.getName());
            }
            mkFile.mkdir();
            directoryCopy(srcFile, mkFile);
        } else if (srcFile.isFile()) {
            fileCopy(srcFile, trgtFile);
        }

        txtSource.clear();
        txtTarget.clear();
        srcFile = null;
        trgtFile = null;
        lblMessage.setText("Complete..!");
        System.out.println("Complete");
    }

    private void directoryCopy(File srcFile, File trFile) {
        File[] files = srcFile.listFiles();
        File mkFile;
        for (File file : files) {
            if (file.isDirectory()) {
                mkFile = new File(trFile, file.getName());
                if (!mkFile.exists()) {
                    mkFile.mkdir();
                }
                directoryCopy(file, mkFile);
                continue;
            }
            fileCopy(file, trFile);
        }
    }

    private void fileCopy(File file, File trFile) {

        try {

            File tempFile = File.createTempFile("CopY", "IteM", new File("/home/kasun/Documents/dep-10/External files/TempFiles/"));
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(tempFile);

            double write = 0.0;

            while (true) {
                byte[] buffer = new byte[1024];
                int read = fis.read(buffer);
                write += read;
                if (read == -1) break;
                fos.write(buffer, 0, read);
            }

            fis.close();
            fos.close();
            File target = new File(trFile, file.getName());
            if (file.getParentFile().getPath().equals(trFile.getPath())) {
                target = new File(trFile, "(copy)" + file.getName());
            }
            tempFile.renameTo(target);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to copy the file, try again").show();
        }
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
        trgtFile = new File(trgtFile, srcFile.getName());
        srcFile.renameTo(trgtFile);
        txtSource.clear();
        txtTarget.clear();
        srcFile = null;
        trgtFile = null;
        lblMessage.setText("Complete..!");
        System.out.println("Complete");

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
