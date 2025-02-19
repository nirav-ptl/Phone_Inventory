
/**
 Starter program
 The CellPhoneInventory class build the CellPhone Inventory GUI
*/

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;



public class CellPhoneInventory extends JFrame {
    private JLabel labelModel, labelManufacturer, labelRetailPrice, labelBanner;
    private JTextField jTextFieldModel, jTextFieldManufacturer, jTextFieldRetailPrice;
    private JButton jButtonAdd, jButtonSave, jButtonNext, jButtonShow;
    private JPanel newPanel, buttonPanel;
    private ArrayList<CellPhone> phoneArrayList = new ArrayList<>();

    public CellPhoneInventory() {
        setTitle("Cellphone Inventory");
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        labelBanner = new JLabel("Cellphone Inventory Management", JLabel.CENTER);
        labelBanner.setFont(new Font("Serif", Font.BOLD, 20));
        labelBanner.setForeground(Color.BLUE);

        labelModel = new JLabel("Model:");
        labelManufacturer = new JLabel("Manufacturer:");
        labelRetailPrice = new JLabel("Retail Price:");

        jTextFieldModel = new JTextField(15);
        jTextFieldManufacturer = new JTextField(15);
        jTextFieldRetailPrice = new JTextField(15);

        jButtonAdd = new JButton("Add");
        jButtonSave = new JButton("Save");
        jButtonNext = new JButton("Next");
        jButtonShow = new JButton("Show Inventory");

        newPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        newPanel.add(labelModel);
        newPanel.add(jTextFieldModel);
        newPanel.add(labelManufacturer);
        newPanel.add(jTextFieldManufacturer);
        newPanel.add(labelRetailPrice);
        newPanel.add(jTextFieldRetailPrice);

        buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.add(jButtonAdd);
        buttonPanel.add(jButtonNext);
        buttonPanel.add(jButtonSave);
        buttonPanel.add(jButtonShow);

        add(labelBanner, BorderLayout.NORTH);
        add(newPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setupEventListeners();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupEventListeners() {
        jButtonAdd.addActionListener(event -> addCellPhone());
        jButtonNext.addActionListener(event -> clearInputFields());
        jButtonSave.addActionListener(event -> saveToFile());
        jButtonShow.addActionListener(event -> showInventory());
    }

    private void addCellPhone() {
        try {
            String model = jTextFieldModel.getText().trim();
            String manufacturer = jTextFieldManufacturer.getText().trim();
            String priceText = jTextFieldRetailPrice.getText().trim();

            if (model.isEmpty()) throw new InvalidModelException();
            if (manufacturer.isEmpty()) throw new InvalidManufacturerException();
            if (priceText.isEmpty()) throw new InvalidRetailPriceException();

            double price = Double.parseDouble(priceText);
            if (price <= 0 || price >= 1500) throw new InvalidRetailPriceException();

            CellPhone phone = new CellPhone(model, manufacturer, price);
            phoneArrayList.add(phone);

            JOptionPane.showMessageDialog(this, "Added:\n" + phone, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidModelException | InvalidManufacturerException | InvalidRetailPriceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInputFields() {
        jTextFieldModel.setText("");
        jTextFieldManufacturer.setText("");
        jTextFieldRetailPrice.setText("");
    }

    private void saveToFile() {
        try (Formatter outCellPhoneList = new Formatter("cellPhones.txt")) {
            for (CellPhone phone : phoneArrayList) {
                outCellPhoneList.format("%s %s %.2f%n", phone.getModel(), phone.getManufacturer(), phone.getRetailPrice());
            }
            JOptionPane.showMessageDialog(this, "Cellphone list saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showInventory() {
        StringBuilder inventory = new StringBuilder(String.format("%-20s%-20s%10s%n", "Model", "Manufacturer", "Price"));

        try (Scanner input = new Scanner(Paths.get("cellPhones.txt"))) {
            while (input.hasNext()) {
                inventory.append(String.format("%-20s%-20s%10.2f%n", input.next(), input.next(), input.nextDouble()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, inventory.toString(), "Cellphone Inventory", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new CellPhoneInventory();
    }
}
