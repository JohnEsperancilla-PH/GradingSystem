import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GradingSystem extends JFrame implements ActionListener {
    private JLabel nameLabel, courseLabel, idLabel, midtermLabel, endtermLabel, finalLabel;
    private JTextField nameField, courseField, idField, midtermField, endtermField, finalField;
    private JButton addButton, viewButton, exitButton;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private ArrayList<String[]> data = new ArrayList<String[]>();
    private String[] columnNames = {"Name", "Course", "ID", "Midterm Grade", "Endterm Grade", "Final Grade"};

    public GradingSystem() {
        super("Grade Calculation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new FlowLayout());

        nameLabel = new JLabel("Name:");
        add(nameLabel);
        nameField = new JTextField(20);
        add(nameField);

        courseLabel = new JLabel("Course:");
        add(courseLabel);
        courseField = new JTextField(20);
        add(courseField);

        idLabel = new JLabel("ID:");
        add(idLabel);
        idField = new JTextField(20);
        add(idField);

        midtermLabel = new JLabel("Midterm Grade:");
        add(midtermLabel);
        midtermField = new JTextField(20);
        add(midtermField);

        endtermLabel = new JLabel("Endterm Grade:");
        add(endtermLabel);
        endtermField = new JTextField(20);
        add(endtermField);

        finalLabel = new JLabel("Final Grade:");
        add(finalLabel);
        finalField = new JTextField(20);
        finalField.setEditable(false);
        add(finalField);

        addButton = new JButton("Add Grade");
        addButton.addActionListener(this);
        add(addButton);

        viewButton = new JButton("View Grades");
        viewButton.addActionListener(this);
        add(viewButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        add(exitButton);

        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(700, 400));
        add(scrollPane);

        loadDatabase();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText();
            String course = courseField.getText();
            String id = idField.getText();
            String midterm = midtermField.getText();
            String endterm = endtermField.getText();
            String finalGrade = calculateFinalGrade(midterm, endterm);

            String[] row = {name, course, id, midterm, endterm, finalGrade};
            data.add(row);
            model.addRow(row);

            saveDatabase();
        } else if (e.getSource() == viewButton) {
            model.setRowCount(0);
            for (String[] row : data) {
                model.addRow(row);
            }
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private String calculateFinalGrade(String midterm, String endterm) {
        double midtermGrade = Double.parseDouble(midterm);
        double endtermGrade = Double.parseDouble(endterm);
        double finalGrade = (midtermGrade + endtermGrade) / 2;
        return String.format("%.2f", finalGrade);
    }

    private void loadDatabase() {
        try {
            File file = new File("database.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] row = line.split(",");
                data.add(row);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveDatabase() {
        try {
            FileWriter writer = new FileWriter("database.txt");
            for (String[] row : data) {
                String line = String.join(",", row);
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GradingSystem();
    }
}
