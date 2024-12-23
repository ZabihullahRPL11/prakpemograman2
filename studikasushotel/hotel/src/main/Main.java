package main;

import controller.RoomController;
import view.RoomView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Gunakan SwingUtilities untuk menjalankan GUI di event dispatch thread
        SwingUtilities.invokeLater(() -> {
            // Membuat instance RoomController
            RoomController roomController = new RoomController();

            // Membuat instance RoomView dan menghubungkannya dengan RoomController
            RoomView roomView = new RoomView(roomController);

            // Menampilkan GUI
            roomView.setVisible(true);
        });
    }
}
