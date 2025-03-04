package com.hotel.controller;

import com.hotel.dao.EmployeeDAO;
import com.hotel.dao.FinancialTransactionDAO;
import com.hotel.model.Employee;
import com.hotel.model.FinancialTransaction;
import com.hotel.Main; // Assurez-vous que le chemin est correct
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MainController {
    @FXML private TabPane mainTabPane;
    @FXML private TableView<Employee> employeeTable;
    @FXML private TableView<FinancialTransaction> transactionTable;
    @FXML private Label totalEmployeesLabel;
    @FXML private Label totalSalaryLabel;
    @FXML private Label totalRevenueLabel;
    @FXML private Label totalExpensesLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    
    private EmployeeDAO employeeDAO;
    private FinancialTransactionDAO transactionDAO;
    private NumberFormat currencyFormat;
    private DateTimeFormatter dateFormatter;

    @FXML
    public void initialize() {
        employeeDAO = new EmployeeDAO();
        transactionDAO = new FinancialTransactionDAO();
        currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        setupEmployeeTable();
        setupTransactionTable();
        loadTransactions();
        loadData();
        updateDashboardStats();
    }

    @SuppressWarnings("unchecked")
    private void setupEmployeeTable() {
        // Active le tri pour la table
        employeeTable.setSortPolicy(tv -> true);

        // Désactiver le redimensionnement et le déplacement des colonnes
        employeeTable.setTableMenuButtonVisible(false);

        // ID Column
        TableColumn<Employee, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> 
            new SimpleStringProperty(String.valueOf(data.getValue().getId()))
        );
        idCol.setReorderable(false);
        idCol.setResizable(false);
        
        // Name Column
        TableColumn<Employee, String> nameCol = new TableColumn<>("Nom Complet");
        nameCol.setCellValueFactory(data -> 
            new SimpleStringProperty(
                data.getValue().getFirstName() + " " + data.getValue().getLastName()
            )
        );
        nameCol.setReorderable(false);
        nameCol.setResizable(false);
        
        // Email Column
        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getEmail())
        );
        emailCol.setReorderable(false);
        emailCol.setResizable(false);
        
        // Phone Column
        TableColumn<Employee, String> phoneCol = new TableColumn<>("Téléphone");
        phoneCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getPhone())
        );
        phoneCol.setReorderable(false);
        phoneCol.setResizable(false);
        
        // Position Column
        TableColumn<Employee, String> positionCol = new TableColumn<>("Poste");
        positionCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getPosition())
        );
        positionCol.setReorderable(false);
        positionCol.setResizable(false);
        
        // Salary Column
        TableColumn<Employee, String> salaryCol = new TableColumn<>("Salaire");
        salaryCol.setCellValueFactory(data -> 
            new SimpleStringProperty(currencyFormat.format(data.getValue().getSalary()))
        );
        salaryCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        salaryCol.setComparator((s1, s2) -> {
            double salary1 = parseCurrency(s1);
            double salary2 = parseCurrency(s2);
            return Double.compare(salary1, salary2);
        });
        salaryCol.setReorderable(false);
        salaryCol.setResizable(false);
        
        // Hire Date Column
        TableColumn<Employee, String> hireDateCol = new TableColumn<>("Date d'embauche");
        hireDateCol.setCellValueFactory(data -> 
            new SimpleStringProperty(
                data.getValue().getHireDate().format(dateFormatter)
            )
        );
        hireDateCol.setReorderable(false);
        hireDateCol.setResizable(false);

        // Ajout des colonnes
        employeeTable.getColumns().addAll(
            idCol, nameCol, emailCol, phoneCol, positionCol, salaryCol, hireDateCol
        );
    }

    @SuppressWarnings("unchecked")
    private void setupTransactionTable() {
        // Configuration de la table
        transactionTable.setSortPolicy(tv -> true);

        // Désactiver le redimensionnement et le déplacement des colonnes
        transactionTable.setTableMenuButtonVisible(false);

        // ID Column
        TableColumn<FinancialTransaction, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> 
            new SimpleStringProperty(String.valueOf(data.getValue().getId()))
        );
        idCol.setReorderable(false);
        idCol.setResizable(false);
        
        // Date Column
        TableColumn<FinancialTransaction, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data -> 
            new SimpleStringProperty(
                data.getValue().getTransactionDate().format(dateFormatter)
            )
        );
        dateCol.setReorderable(false);
        dateCol.setResizable(false);
        
        // Type Column
        TableColumn<FinancialTransaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getType())
        );
        typeCol.setReorderable(false);
        typeCol.setResizable(false);
        
        // Amount Column
        TableColumn<FinancialTransaction, String> amountCol = new TableColumn<>("Montant");
        amountCol.setCellValueFactory(data -> 
            new SimpleStringProperty(currencyFormat.format(data.getValue().getAmount()))
        );
        amountCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        amountCol.setComparator((s1, s2) -> {
            double amount1 = parseCurrency(s1);
            double amount2 = parseCurrency(s2);
            return Double.compare(amount1, amount2);
        });
        amountCol.setReorderable(false);
        amountCol.setResizable(false);
        
        // Category Column
        TableColumn<FinancialTransaction, String> categoryCol = new TableColumn<>("Catégorie");
        categoryCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getCategory())
        );
        categoryCol.setReorderable(false);
        categoryCol.setResizable(false);
        
        // Description Column
        TableColumn<FinancialTransaction, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getDescription())
        );
        descriptionCol.setReorderable(false);
        descriptionCol.setResizable(false);

        // Ajout des colonnes
        transactionTable.getColumns().addAll(
            idCol, dateCol, typeCol, amountCol, categoryCol, descriptionCol
        );
    }

    private void loadTransactions() {
        List<FinancialTransaction> transactions = transactionDAO.getAllTransactions();
        transactionTable.setItems(FXCollections.observableArrayList(transactions));
        adjustTableColumnsWidthTransactions(transactionTable); // Adjust column widths
    }

    @FXML
    private void loadData() {
        try {
            List<Employee> employees = employeeDAO.getAllEmployees();
            employeeTable.setItems(FXCollections.observableArrayList(employees));
            adjustTableColumnsWidthEmployees(employeeTable); // Adjust column widths

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
    }

    private void updateDashboardStats() {
        try {
            // Statistiques des employés
            int totalEmployees = employeeDAO.getTotalEmployees();
            double totalSalary = employeeDAO.getTotalSalary();
            totalEmployeesLabel.setText("Total Employés: " + totalEmployees);
            totalSalaryLabel.setText("Masse Salariale: " + currencyFormat.format(totalSalary));

            // Statistiques financières
            double totalIncome = transactionDAO.getTotalIncome();
            double totalExpenses = transactionDAO.getTotalExpenses();
            
            // Format pour l'euro
            NumberFormat euroFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
            
            totalRevenueLabel.setText("Revenue total: " + euroFormat.format(totalIncome));
            totalExpensesLabel.setText("Depenses totales: " + euroFormat.format(totalExpenses));
        } catch (SQLException e) {
            showError("Erreur de chargement", "Impossible de charger les statistiques: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddEmployee() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un employé");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField position = new TextField();
        TextField salary = new TextField();
        TextField email = new TextField();
        TextField phone = new TextField();

        grid.add(new Label("Prénom:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Poste:"), 0, 2);
        grid.add(position, 1, 2);
        grid.add(new Label("Salaire:"), 0, 3);
        grid.add(salary, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(email, 1, 4);
        grid.add(new Label("Téléphone:"), 0, 5);
        grid.add(phone, 1, 5);

        dialog.getDialogPane().setContent(grid);
        
        ButtonType addButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    double salaryValue = Double.parseDouble(salary.getText());
                    Employee employee = new Employee();
                    employee.setFirstName(firstName.getText());
                    employee.setLastName(lastName.getText());
                    employee.setPosition(position.getText());
                    employee.setSalary(salaryValue);
                    employee.setEmail(email.getText());
                    employee.setPhone(phone.getText());
                    return employee;
                } catch (NumberFormatException e) {
                    showError("Erreur", "Le salaire doit être un nombre valide");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(employee -> {
            try {
                employeeDAO.add(employee);
                loadData();
                updateDashboardStats();
            } catch (SQLException e) {
                showError("Erreur d'enregistrement", e.getMessage());
            }
        });
    }

    @FXML
    private void handleAddTransaction() {
        Dialog<FinancialTransaction> dialog = new Dialog<>();
        dialog.setTitle("Ajouter une transaction");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("INCOME", "EXPENSE");
        TextField amount = new TextField();
        TextField category = new TextField();
        TextField description = new TextField();

        grid.add(new Label("Type:"), 0, 0);
        grid.add(type, 1, 0);
        grid.add(new Label("Montant:"), 0, 1);
        grid.add(amount, 1, 1);
        grid.add(new Label("Catégorie:"), 0, 2);
        grid.add(category, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(description, 1, 3);

        dialog.getDialogPane().setContent(grid);
        
        ButtonType addButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    double amountValue = Double.parseDouble(amount.getText());
                    FinancialTransaction transaction = new FinancialTransaction();
                    transaction.setType(type.getValue());
                    transaction.setAmount(amountValue);
                    transaction.setCategory(category.getText());
                    transaction.setDescription(description.getText());
                    return transaction;
                } catch (NumberFormatException e) {
                    showError("Erreur", "Le montant doit être un nombre valide");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(transaction -> {
            try {
                transactionDAO.add(transaction);
                loadTransactions();
                updateDashboardStats();
            } catch (SQLException e) {
                showError("Erreur d'enregistrement", e.getMessage());
            }
        });
    }

    @FXML
    private void handleEditEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            Dialog<Employee> dialog = new Dialog<>();
            dialog.setTitle("Modifier un employé");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

            TextField firstName = new TextField(selectedEmployee.getFirstName());
            TextField lastName = new TextField(selectedEmployee.getLastName());
            TextField position = new TextField(selectedEmployee.getPosition());
            TextField salary = new TextField(String.valueOf(selectedEmployee.getSalary()));
            TextField email = new TextField(selectedEmployee.getEmail());
            TextField phone = new TextField(selectedEmployee.getPhone());

            grid.add(new Label("Prénom:"), 0, 0);
            grid.add(firstName, 1, 0);
            grid.add(new Label("Nom:"), 0, 1);
            grid.add(lastName, 1, 1);
            grid.add(new Label("Poste:"), 0, 2);
            grid.add(position, 1, 2);
            grid.add(new Label("Salaire:"), 0, 3);
            grid.add(salary, 1, 3);
            grid.add(new Label("Email:"), 0, 4);
            grid.add(email, 1, 4);
            grid.add(new Label("Téléphone:"), 0, 5);
            grid.add(phone, 1, 5);

            dialog.getDialogPane().setContent(grid);
            
            ButtonType addButton = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    try {
                        double salaryValue = Double.parseDouble(salary.getText());
                        selectedEmployee.setFirstName(firstName.getText());
                        selectedEmployee.setLastName(lastName.getText());
                        selectedEmployee.setPosition(position.getText());
                        selectedEmployee.setSalary(salaryValue);
                        selectedEmployee.setEmail(email.getText());
                        selectedEmployee.setPhone(phone.getText());
                        return selectedEmployee;
                    } catch (NumberFormatException e) {
                        showError("Erreur", "Le salaire doit être un nombre valide");
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(employee -> {
                try {
                    employeeDAO.update(employee);
                    loadData();
                    updateDashboardStats();
                } catch (SQLException e) {
                    showError("Erreur de modification", e.getMessage());
                }
            });
        } else {
            // Afficher un message indiquant qu'aucun employé n'est sélectionné
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                employeeDAO.deleteById(selectedEmployee.getId()); // Utiliser deleteById
                employeeTable.getItems().remove(selectedEmployee);
                updateDashboardStats(); // Mettre à jour les statistiques si nécessaire
            } catch (SQLException e) {
                e.printStackTrace();
                // Afficher un message d'erreur à l'utilisateur
            }
        } else {
            // Afficher un message indiquant qu'aucun employé n'est sélectionné
        }
    }

    @FXML
    private void handleDeleteTransaction() {
        FinancialTransaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            try {
                transactionDAO.deleteTransactionById(selectedTransaction.getId());
                transactionTable.getItems().remove(selectedTransaction);
                updateDashboardStats(); // Mettre à jour les statistiques si nécessaire
            } catch (SQLException e) {
                e.printStackTrace();
                // Afficher un message d'erreur à l'utilisateur
            }
        } else {
            // Afficher un message indiquant qu'aucune transaction n'est sélectionnée
        }
    }

    @FXML
    private void handleEditTransaction() {
        FinancialTransaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            Dialog<FinancialTransaction> dialog = new Dialog<>();
            dialog.setTitle("Modifier une Transaction");
            
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

            ComboBox<String> typeComboBox = new ComboBox<>();
            typeComboBox.getItems().addAll("INCOME", "EXPENSE");
            typeComboBox.setValue(selectedTransaction.getType());
            TextField amount = new TextField(String.valueOf(selectedTransaction.getAmount()));
            TextField category = new TextField(selectedTransaction.getCategory());
            TextField description = new TextField(selectedTransaction.getDescription());

            grid.add(new Label("Type:"), 0, 0);
            grid.add(typeComboBox, 1, 0);
            grid.add(new Label("Montant:"), 0, 1);
            grid.add(amount, 1, 1);
            grid.add(new Label("Catégorie:"), 0, 2);
            grid.add(category, 1, 2);
            grid.add(new Label("Description:"), 0, 3);
            grid.add(description, 1, 3);

            dialog.getDialogPane().setContent(grid);
            
            ButtonType addButton = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == addButton) {
                    try {
                        double amountValue = Double.parseDouble(amount.getText());
                        selectedTransaction.setType(typeComboBox.getValue());
                        selectedTransaction.setAmount(amountValue);
                        selectedTransaction.setCategory(category.getText());
                        selectedTransaction.setDescription(description.getText());
                        return selectedTransaction;
                    } catch (NumberFormatException e) {
                        showError("Erreur", "Le montant doit être un nombre valide");
                        return null;
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(transaction -> {
                try {
                    transactionDAO.update(transaction);
                    loadTransactions();
                    updateDashboardStats();
                } catch (SQLException e) {
                    showError("Erreur de modification", e.getMessage());
                }
            });
        } else {
            // Afficher un message indiquant qu'aucune transaction n'est sélectionnée
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Main.login(username, password); // Appel à la méthode login
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode utilitaire pour parser les montants avec devise
    private double parseCurrency(String amount) {
        try {
            // Supprime tous les caractères non numériques sauf le point et la virgule
            String cleanAmount = amount.replaceAll("[^0-9,.]", "");
            // Remplace la virgule par un point pour la conversion
            cleanAmount = cleanAmount.replace(",", ".");
            return Double.parseDouble(cleanAmount);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Ajuste la largeur des colonnes en fonction du contenu
     */
    private void adjustTableColumnsWidthEmployees(TableView<?> table) {
        // Largeur fixe pour toutes les colonnes
        final int FIXED_WIDTH = 150;
    
        // Appliquer la largeur fixe à chaque colonne
        table.getColumns().forEach(column -> {
            column.setPrefWidth(FIXED_WIDTH);
        });
    
        // Forcer la mise à jour de la table
        table.requestLayout();
    }
    private void adjustTableColumnsWidthTransactions(TableView<?> table) {
        // Largeur fixe pour toutes les colonnes
        final int FIXED_WIDTH = 175;
    
        // Appliquer la largeur fixe à chaque colonne
        table.getColumns().forEach(column -> {
            column.setPrefWidth(FIXED_WIDTH);
        });
    
        // Forcer la mise à jour de la table
        table.requestLayout();
    }
}
