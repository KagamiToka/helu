package utils;

import javax.swing.*;

public class ErrorDialog {
   public static void showError(String errorMessage) {
        String fileName = new Throwable().getStackTrace()[1].getFileName();
        JOptionPane.showMessageDialog(null, errorMessage, "ERROR: " + fileName, JOptionPane.ERROR_MESSAGE);
    }
}