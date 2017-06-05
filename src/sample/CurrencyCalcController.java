package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class CurrencyCalcController {

    @FXML
    private ImageView leftFlag;


    @FXML
    private ImageView rightFlag;

    @FXML
    public void initialize() {
        File leftImage = new File("Icons\\bulgariC.gif");
        leftFlag.setImage(new Image(leftImage.toURI().toString()));
        setFlags("Icons\\bulgariC.gif","Icons\\america2.gif");

    }

    private void setFlags(String firstPath,String secPath){
        File leftImage = new File(firstPath);
        leftFlag.setImage(new Image(leftImage.toURI().toString()));
        File rightImage = new File(secPath);
        rightFlag.setImage(new Image(rightImage.toURI().toString()));
    }

}
