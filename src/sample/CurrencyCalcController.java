package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.CurrencyManager;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


public class CurrencyCalcController {

    @FXML
    private Tab calcTab;

    @FXML
    private ImageView leftFlag;

    @FXML
    private ImageView rightFlag;

    @FXML
    private ComboBox<String> leftCurrencyCB;

    @FXML
    private ComboBox<String> rightCurrencyCB;

    @FXML
    private Button convertBtn;

    @FXML
    private TextField leftValueTF;

    @FXML
    private Label rightValueTF;

    @FXML
    private Tab currencyTableTab;

    @FXML
    private TableView<CurrencyManager.Currency> tableViewContent;

    @FXML
    private TableColumn<CurrencyManager.Currency, String> descTableTab;

    @FXML
    private TableColumn<CurrencyManager.Currency, String> codeTableTab;

    @FXML
    private TableColumn<CurrencyManager.Currency, String> rateTableTab;

    @FXML
    private Tab updateTableTab;

    @FXML
    private TableView<CurrencyManager.Currency> tableViewContent1;

    @FXML
    private TableColumn<CurrencyManager.Currency, String> descTableTab1;

    @FXML
    private TableColumn<CurrencyManager.Currency, String> codeTableTab1;

    @FXML
    private TableColumn<CurrencyManager.Currency, String> rateTableTab1;

    @FXML
    private Button commitBtn;

    @FXML
    private Button DiscardBtn;

    @FXML
    private Button saveAsBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private MenuItem openFileMenuItem;

    @FXML
    private MenuItem defaultTheme;

    @FXML
    private MenuItem darkTheme;

    private CurrencyManager manager = CurrencyManager.getInstance();
    private ArrayList<CurrencyManager.Currency> backUpData;

    private void backUpTableData(){
        backUpData = new ArrayList<>();
        manager.getCurrenciesList().forEach(x -> backUpData.add(CurrencyManager.createCurrency(x.getDescription(),x.getCode(),String.valueOf(x.getRate()),x.getImagePath())));
    }

    @FXML
    public void initialize() {
        leftCurrencyCB.setItems(FXCollections.observableList(manager.getCodeValues()));
        setLeftFlag(manager.getImagePath(manager.getCurrencies().first()));
        rightCurrencyCB.setItems(FXCollections.observableList(manager.getCodeValues()));
        setRightFlag(manager.getImagePath(manager.getCurrencies().first()));
        backUpTableData();
        initializeTables();
    }

    @FXML
    public void convertCurrency(ActionEvent actionEvent) {
        double inputValue = Double.parseDouble(leftValueTF.getText());
        String leftCurrCode = leftCurrencyCB.getValue();
        String rightCurrCode = rightCurrencyCB.getValue();

        double leftValue = Double.parseDouble(manager.getCurrenciesList().get(leftCurrencyCB.getSelectionModel().getSelectedIndex()).getRate()) * inputValue;
        double rightValue = Double.parseDouble(manager.getCurrenciesList().get(rightCurrencyCB.getSelectionModel().getSelectedIndex()).getRate()) * inputValue;
        double convertedValue = (leftValue-rightValue) + inputValue;
        rightValueTF.setText(String.valueOf(convertedValue));
    }
    @FXML
    public void setLeftFlagOnChoice(ActionEvent actionEvent) {
        setLeftFlag(manager.getCurrenciesList().get(leftCurrencyCB.getSelectionModel().getSelectedIndex()).getImagePath());
    }
    @FXML
    public void setRightFlagOnChoice(ActionEvent actionEvent) {
        setRightFlag(manager.getCurrenciesList().get(rightCurrencyCB.getSelectionModel().getSelectedIndex()).getImagePath());
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Currency Calculator");
        alert.setHeaderText("Version 1.0");
        alert.setContentText("Made by Martin Draganov and Dimitar Bakardzhiev");

        alert.showAndWait();
    }

    private void setLeftFlag(String imagePath){
        File leftImage = new File(imagePath);
        leftFlag.setImage(new Image(leftImage.toURI().toString()));
    }

    private void setRightFlag(String imagePath){
        File leftImage = new File(imagePath);
        rightFlag.setImage(new Image(leftImage.toURI().toString()));
    }


    @FXML
    public void commitTableData(TableColumn.CellEditEvent<CurrencyManager.Currency, String> currencyStringCellEditEvent) {
        if (currencyStringCellEditEvent.getSource().equals(descTableTab1)) {
            descTableTab1.getTableView().getItems().get(currencyStringCellEditEvent.getTablePosition().getRow()).descriptionProperty().set(currencyStringCellEditEvent.getNewValue());
        }
        else if (currencyStringCellEditEvent.getSource().equals(codeTableTab1)){
            codeTableTab1.getTableView().getItems().get(currencyStringCellEditEvent.getTablePosition().getRow()).codeProperty().set(currencyStringCellEditEvent.getNewValue());
        }
        else {
            rateTableTab1.getTableView().getItems().get(currencyStringCellEditEvent.getTablePosition().getRow()).rateProperty().set(currencyStringCellEditEvent.getNewValue());
        }
    }


    private void initializeTables(){
        ObservableList<CurrencyManager.Currency> data = FXCollections.observableArrayList(manager.getCurrenciesList());
        descTableTab.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        codeTableTab.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        rateTableTab.setCellValueFactory(cellData -> cellData.getValue().rateProperty());
        tableViewContent.setItems(data);

        descTableTab1.setCellFactory(TextFieldTableCell.forTableColumn());
        descTableTab1.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        descTableTab1.setEditable(true);

        codeTableTab1.setCellFactory(TextFieldTableCell.forTableColumn());
        codeTableTab1.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        codeTableTab1.setEditable(true);

        rateTableTab1.setCellFactory(TextFieldTableCell.forTableColumn());
        rateTableTab1.setCellValueFactory(cellData -> cellData.getValue().rateProperty());
        rateTableTab1.setEditable(true);
        tableViewContent1.setItems(data);
        tableViewContent1.setEditable(true);
        tableViewContent1.refresh();
    }

    @FXML
    public void saveTableChanges(ActionEvent actionEvent) {
        backUpData.clear();
        manager.getCurrenciesList().forEach(x -> backUpData.add(manager.createCurrency(x.getDescription(),x.getCode(),x.getRate(),x.getImagePath())));
        backUpData.forEach(x -> System.out.println(x.getDescription()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Table Information");
        alert.setHeaderText("Table changes applied successfully !");

        alert.showAndWait();
    }

    @FXML
    public void cancelTableData(ActionEvent actionEvent) {
        manager.getCurrenciesList().clear();
        backUpData.forEach(x -> manager.getCurrenciesList().add(manager.createCurrency(x.getDescription(),x.getCode(),x.getRate(),x.getImagePath())));
        tableViewContent.setItems(FXCollections.observableArrayList(manager.getCurrenciesList()));
        tableViewContent1.setItems(FXCollections.observableArrayList(manager.getCurrenciesList()));
        tableViewContent.refresh();
        tableViewContent1.refresh();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Table Information");
        alert.setHeaderText("Table changes discarded !");

        alert.showAndWait();
    }

    @FXML
    public void saveTableToFile(ActionEvent actionEvent) {
        File file = new File("table.csv");
        StringBuilder data = new StringBuilder();
        manager.getCurrenciesList().forEach(x -> data.append(x.getDescription()+","+x.getCode()+","+x.getRate()+","+x.getImagePath()+"\n"));
        String toSave = data.toString();
        DataOutputStream os = null;
        try {
            os = new DataOutputStream(new FileOutputStream(file));
           os.writeUTF(toSave);
           os.flush();
           os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void newTable(ActionEvent actionEvent) {
        manager.getCurrenciesList().forEach(x -> {
            x.descriptionProperty().set("data");
            x.rateProperty().set("0.0");
            x.codeProperty().set("data");
        });
    }

    @FXML
    public void openTableFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selected = fileChooser.showOpenDialog(openFileMenuItem.getParentMenu().getParentPopup());
        if (selected != null){
            manager.getCurrenciesList().clear();
            try {
                Scanner scanner = new Scanner(new FileInputStream(selected));
                while (scanner.hasNext()){
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data[0].contains(")")){
                        data[0] = data[0].substring(2);
                    }
                    //System.out.println(data[0] + " " +data[1] + " " +data[2] + " " +data[3]);

                    manager.getCurrenciesList().add(CurrencyManager.createCurrency(data[0],data[1],data[2],data[3]));
                }
                tableViewContent.setItems(FXCollections.observableArrayList(manager.getCurrenciesList()));
                tableViewContent1.setItems(FXCollections.observableArrayList(manager.getCurrenciesList()));
                tableViewContent.refresh();
                tableViewContent1.refresh();
                CurrencyManager.refreshTreeSetCurrencies(manager.getCurrenciesList());

                leftCurrencyCB.setItems(FXCollections.observableList(manager.getCodeValues()));
                rightCurrencyCB.setItems(FXCollections.observableArrayList(manager.getCodeValues()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Load Information");
                alert.setHeaderText("Table loaded successfully and changes applied !");

                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Load Information");
                alert.setHeaderText("Loading table data error!");

                alert.showAndWait();
                e.printStackTrace();
            }
        }

    }

    public void changeSkin(ActionEvent actionEvent) {
        System.out.println("change");
        Main.scene.getStylesheets().add(Main.class.getResource("DarkTheme.css").toExternalForm());
    }
}
