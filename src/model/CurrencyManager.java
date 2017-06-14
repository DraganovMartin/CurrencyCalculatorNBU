package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by DevM on 6/9/2017.
 */
public class CurrencyManager implements Serializable{
    private static CurrencyManager ourInstance = new CurrencyManager();

    public static CurrencyManager getInstance() {
        return ourInstance;
    }
    private ArrayList<String> codeValues;
    private ArrayList<Currency> currenciesList;
    private static TreeSet<Currency> currencies;

    private CurrencyManager() {
        currencies  = new TreeSet<>((o1, o2) -> o1.code.getValue().compareTo(o2.code.getValue()));
        codeValues = new ArrayList<>();
        loadCurrenciesFromDB();
        currenciesList = new ArrayList<>(currencies);
        populateCodeValues();
    }

    public TreeSet<Currency> getCurrencies() {
        return currencies;
    }

    public ArrayList<String> getCodeValues() {
        return codeValues;
    }

    private void populateCodeValues(){
        currencies.forEach(x -> codeValues.add(x.code.getValue()));
    }

    public static void refreshTreeSetCurrencies(List<Currency> currenciesList) {
        currencies = new TreeSet<>((o1, o2) -> o1.code.getValue().compareTo(o2.code.getValue()));
        currenciesList.forEach(x -> currencies.add(new Currency(x.getDescription(),x.getCode(),x.getRate(),x.getImagePath())));
    }

    private void loadCurrenciesFromDB(){
        String path = new File("DB//CurrencyDB.mdb").getPath();
        String dbURL = "jdbc:ucanaccess://"+path;
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbURL);

            Statement stmt = connection.createStatement();

            ResultSet rSet = stmt.executeQuery("SELECT * FROM CurrencyTable");

            while (rSet.next()) {

                String desc = rSet.getString("Description");
                String code = rSet.getString("Code");
                String rate = rSet.getString("Rate");
                String image = rSet.getString("Image");
                currencies.add(new Currency(desc,code,rate,image));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImagePath(Currency x){
        return x.getImagePath();
    }

    public ArrayList<Currency> getCurrenciesList() {
        return currenciesList;
    }

    public static Currency createCurrency(String description, String code, String rate, String imageName){
        return new Currency(description, code, rate, imageName);
    }

    public static class Currency implements Serializable{
        private SimpleStringProperty description;
        private SimpleStringProperty code;
        private SimpleStringProperty rate;
        private SimpleStringProperty imagePath;

        private Currency(String description, String code, String rate, String imageName){
            this.description = new SimpleStringProperty(description);
            this.code = new SimpleStringProperty(code);
            this.rate = new SimpleStringProperty(rate);
            this.imagePath = new SimpleStringProperty("Icons\\"+imageName);
        }

        public String getDescription() {
            return description.get();
        }

        public SimpleStringProperty descriptionProperty() {
            return description;
        }

        public String getCode() {
            return code.get();
        }

        public SimpleStringProperty codeProperty() {
            return code;
        }

        public String getRate() {
            return rate.get();
        }

        public SimpleStringProperty rateProperty() {
            return rate;
        }

        public String getImagePath() {
            return imagePath.get();
        }

        public SimpleStringProperty imagePathProperty() {
            return imagePath;
        }

    }
}
