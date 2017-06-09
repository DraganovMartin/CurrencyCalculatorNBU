package model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by DevM on 6/9/2017.
 */
public class CurrencyManager {
    private static CurrencyManager ourInstance = new CurrencyManager();

    public static CurrencyManager getInstance() {
        return ourInstance;
    }
    private ArrayList<String> codeValues;
    private ArrayList<Currency> currenciesList;
    private TreeSet<Currency> currencies;

    private CurrencyManager() {
        currencies  = new TreeSet<>((o1, o2) -> o1.code.compareTo(o2.code));
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
        currencies.forEach(x -> codeValues.add(x.code));
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
                double rate = rSet.getDouble("Rate");
                String image = rSet.getString("Image");
                currencies.add(new Currency(desc,code,rate,image));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImagePath(Currency x){
        return x.imagePath;
    }

    public ArrayList<Currency> getCurrenciesList() {
        return currenciesList;
    }

    public class Currency{
        String description;
        String code;
        double rate;
        String imagePath;

        private Currency(String description, String code, double rate, String imageName){
            this.description = description;
            this.code = code;
            this. rate = rate;
            this.imagePath = "Icons\\"+imageName;
        }

        public String getImagePath() {
            return imagePath;
        }

        public double getRate() {
            return rate;
        }
    }
}
