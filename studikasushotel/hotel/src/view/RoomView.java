package view;

import controller.RoomController;
import model.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RoomView extends JFrame {
    private final RoomController roomController;
    private DefaultTableModel tableModel;
    private JTable roomTable;

    // Constructor
    public RoomView(RoomController roomController) {
        this.roomController = roomController;
        setTitle("Room Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setupUI();
    }

    // Setup GUI components
    private void setupUI() {
        // Main layout setup
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Room Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = {"Room Number", "Type", "Price", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        roomTable = new JTable(tableModel);
        roomTable.setRowHeight(25);
        roomTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(roomTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton addButton = new JButton("Add Room");
        JButton editButton = new JButton("Edit Room");
        JButton deleteButton = new JButton("Delete Room");
        JButton refreshButton = new JButton("Refresh");

        styleButton(addButton);
        styleButton(editButton);
        styleButton(deleteButton);
        styleButton(refreshButton);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Button actions
        addButton.addActionListener(this::addRoom);
        editButton.addActionListener(this::editRoom);
        deleteButton.addActionListener(this::deleteRoom);
        refreshButton.addActionListener(e -> loadRoomData());

        // Load initial data
        loadRoomData();
    }

    // Style buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(new Color(72, 128, 237));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    // Load room data into the table
    private void loadRoomData() {
        tableModel.setRowCount(0); // Clear existing rows
        try {
            List<Room> rooms = roomController.getAllRooms();
            for (Room room : rooms) {
                tableModel.addRow(new Object[]{
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPrice(),
                    room.isAvailable() ? "Yes" : "No"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add new room
    private void addRoom(ActionEvent e) {
        JTextField roomNumberField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();
        JCheckBox availabilityBox = new JCheckBox("Available");

        Object[] inputs = {
            "Room Number:", roomNumberField,
            "Type:", typeField,
            "Price:", priceField,
            "Available:", availabilityBox
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Add New Room", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                String type = typeField.getText();
                double price = Double.parseDouble(priceField.getText());
                boolean isAvailable = availabilityBox.isSelected();

                Room room = new Room(roomNumber, type, price, isAvailable);
                if (roomController.addRoom(room)) {
                    JOptionPane.showMessageDialog(this, "Room added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadRoomData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add room.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Edit selected room
    private void editRoom(ActionEvent e) {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomNumber = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String type = tableModel.getValueAt(selectedRow, 1).toString();
        double price = Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString());
        boolean isAvailable = tableModel.getValueAt(selectedRow, 3).toString().equals("Yes");

        JTextField typeField = new JTextField(type);
        JTextField priceField = new JTextField(String.valueOf(price));
        JCheckBox availabilityBox = new JCheckBox("Available", isAvailable);

        Object[] inputs = {
            "Type:", typeField,
            "Price:", priceField,
            "Available:", availabilityBox
        };

        int result = JOptionPane.showConfirmDialog(this, inputs, "Edit Room", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String newType = typeField.getText();
                double newPrice = Double.parseDouble(priceField.getText());
                boolean newAvailability = availabilityBox.isSelected();

                Room updatedRoom = new Room(roomNumber, newType, newPrice, newAvailability);
                if (roomController.updateRoom(updatedRoom)) {
                    JOptionPane.showMessageDialog(this, "Room updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadRoomData();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update room.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Delete selected room
    private void deleteRoom(ActionEvent e) {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomNumber = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete room " + roomNumber + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (roomController.deleteRoom(roomNumber)) {
                JOptionPane.showMessageDialog(this, "Room deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadRoomData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete room.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
