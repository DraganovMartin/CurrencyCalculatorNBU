package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CurrencyManager;

import java.io.File;


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
    private TableView<?> tableViewContent;

    @FXML
    private TableColumn<?, ?> descTableTab;

    @FXML
    private TableColumn<?, ?> codeTableTab;

    @FXML
    private TableColumn<?, ?> rateTableTab;

    @FXML
    private Tab updateTableTab;

    @FXML
    private TableView<?> tableViewContent1;

    @FXML
    private TableColumn<?, ?> descTableTab1;

    @FXML
    private TableColumn<?, ?> codeTableTab1;

    @FXML
    private TableColumn<?, ?> rateTableTab1;

    @FXML
    private Button commitBtn;

    @FXML
    private Button DiscardBtn;

    @FXML
    private Button saveAsBtn;

    @FXML
    private Button exitBtn;

    private CurrencyManager manager = CurrencyManager.getInstance();

    @FXML
    public void initialize() {
        leftCurrencyCB.setItems(FXCollections.observableList(manager.getCodeValues()));
        setLeftFlag(manager.getImagePath(manager.getCurrencies().first()));
        rightCurrencyCB.setItems(FXCollections.observableList(manager.getCodeValues()));
        setRightFlag(manager.getImagePath(manager.getCurrencies().first()));
    }

    @FXML
    public void convertCurrency(ActionEvent actionEvent) {
        double inputValue = Double.parseDouble(leftValueTF.getText());
        String leftCurrCode = leftCurrencyCB.getValue();
        String rightCurrCode = rightCurrencyCB.getValue();
        double leftValue = manager.getCurrenciesList().get(leftCurrencyCB.getSelectionModel().getSelectedIndex()).getRate() * inputValue;
        double rightValue = manager.getCurrenciesList().get(rightCurrencyCB.getSelectionModel().getSelectedIndex()).getRate() * inputValue;
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

}
