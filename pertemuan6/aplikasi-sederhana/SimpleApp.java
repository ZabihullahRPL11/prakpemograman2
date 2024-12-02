import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Calendar;

public class SimpleApp extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public SimpleApp() {
        setTitle("Aplikasi Java Swing");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Setup Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        // Menu Item for Keluar
        JMenuItem exitItem = new JMenuItem("Keluar");
        exitItem.addActionListener(e -> System.exit(0));
        menu.add(exitItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Setup Table
        tableModel = new DefaultTableModel(new Object[]{"Nama", "Alamat", "Jenis Kelamin", "Tanggal Lahir", "Umur", "Hobi", "Kota", "Minat"}, 0);
        table = new JTable(tableModel);

        // Custom renderer for text wrapping in table cells
        table.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Input and Delete Buttons
        JButton inputButton = new JButton("Input");
        JButton deleteButton = new JButton("Delete");

        inputButton.addActionListener(e -> showForm());
        deleteButton.addActionListener(e -> deleteSelectedRow());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(inputButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showForm() {
        JDialog formDialog = new JDialog(this, "Form Input", true);
        formDialog.setSize(500, 600);
        formDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Components
        JTextField nameField = new JTextField(15);
        JTextArea addressArea = new JTextArea(3, 15);
        JRadioButton maleButton = new JRadioButton("Laki-laki");
        JRadioButton femaleButton = new JRadioButton("Perempuan");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        JComboBox<Integer> dayComboBox = new JComboBox<>();
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        JComboBox<Integer> yearComboBox = new JComboBox<>();

        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            yearComboBox.addItem(i);
        }

        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(18, 0, 100, 1));
        JTextField hobbyField = new JTextField(15);
        JCheckBox robotCheckBox = new JCheckBox("Saya bukan robot");
        JComboBox<String> cityCombo = new JComboBox<>(new String[]{"Bandung", "Jakarta", "Surabaya"});
        
        // JSlider for Interest Level
        JSlider interestSlider = new JSlider(0, 10);
        interestSlider.setMajorTickSpacing(1);
        interestSlider.setPaintTicks(true);
        interestSlider.setPaintLabels(true);

        // Adding components to panel
        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(addressArea), gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        panel.add(maleButton, gbc);
        y++;
        gbc.gridx = 1;
        gbc.gridy = y;
        panel.add(femaleButton, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Tanggal Lahir:"), gbc);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.add(dayComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);
        gbc.gridx = 1;
        panel.add(datePanel, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Umur:"), gbc);
        gbc.gridx = 1;
        panel.add(ageSpinner, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Hobi:"), gbc);
        gbc.gridx = 1;
        panel.add(hobbyField, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Kota:"), gbc);
        gbc.gridx = 1;
        panel.add(cityCombo, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel("Minat (0-10):"), gbc);
        gbc.gridx = 1;
        panel.add(interestSlider, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(robotCheckBox, gbc);
        y++;

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = y;
        panel.add(submitButton, gbc);

        formDialog.add(panel);

        submitButton.addActionListener(e -> {
            if (!robotCheckBox.isSelected()) {
                JOptionPane.showMessageDialog(formDialog, "Anda harus mencentang 'Saya bukan robot' untuk melanjutkan.");
                return;
            }

            String name = nameField.getText();
            String address = addressArea.getText();
            String gender = maleButton.isSelected() ? "Laki-laki" : "Perempuan";
            int day = (Integer) dayComboBox.getSelectedItem();
            String month = (String) monthComboBox.getSelectedItem();
            int year = (Integer) yearComboBox.getSelectedItem();
            int age = (Integer) ageSpinner.getValue();
            String hobby = hobbyField.getText();
            String city = (String) cityCombo.getSelectedItem();
            int interestLevel = interestSlider.getValue();
            String birthDate = day + "-" + month + "-" + year;

            tableModel.addRow(new Object[]{name, address, gender, birthDate, age, hobby, city, interestLevel});
            formDialog.dispose();
        });

        formDialog.setVisible(true);
    }

    private void deleteSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleApp::new);
    }

    // Custom TableCellRenderer to wrap text in cells
    private static class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {

        public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
            boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");

            // Set background and foreground colors
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            // Adjust the height of each row to fit content
            int currentRowHeight = table.getRowHeight(row);
            setSize(table.getColumnModel().getColumn(column).getWidth(), Integer.MAX_VALUE);
            int textHeight = getPreferredSize().height;
            
            if (currentRowHeight < textHeight) {
                table.setRowHeight(row, textHeight);
            }

            return this;
        }
    }
}
