import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JTableSortExample {
    public static void main(String[] args) {
        // Membuat JFrame
        JFrame frame = new JFrame("JTable Sorting Example");

        // Header kolom
        String[] columnNames = { "ID", "Name", "Age" };

        // Data awal
        Object[][] data = {
            { 1, "John", 25 },
            { 3, "Mike", 35 },
            { 2, "Anna", 30 }
        };

        // Membuat DefaultTableModel dengan data dan header
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Membuat JTable dengan model
        JTable table = new JTable(model);

        // Mengaktifkan pengurutan otomatis
        table.setAutoCreateRowSorter(true);

        // Membuat JScrollPane untuk JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Menambahkan JScrollPane ke JFrame
        frame.add(scrollPane);

        // Konfigurasi frame
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
