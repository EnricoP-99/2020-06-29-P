/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenze;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<?> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<?> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	txtResult.clear();
    	int mese = cmbMese.getValue();
		int min = Integer.parseInt(txtMinuti.getText());
    	for(Adiacenze a : this.model.getMaxArchi(mese, min))
    	{
    		txtResult.appendText(a.toString());
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	try 
    	{
    		
	    	if(!(cmbMese.getValue()==null))
	    	{
	    		if(!txtMinuti.getText().equals(""))
	    		{
	    			int mese = cmbMese.getValue();
    				int min = Integer.parseInt(txtMinuti.getText());
            		this.model.creaGrafo(mese, min);
            		txtResult.appendText("Grafo creato con successo!\n");
            		txtResult.appendText("#Vertici "+ this.model.getNVertici()+"\n");
            		txtResult.appendText("#Archi "+ this.model.getNArchi()+"\n");
	            		
	    		}
	    		else
	    		{
	    			txtResult.appendText("Il campo di teso è vuto, per favore inserisci un valore minimo");
	    			return ;
	    		}
	    	}
	    	else
			{
				txtResult.appendText("Per favore selezionare un mese");
				return ;
			}
    	} catch (NumberFormatException e) {
			txtResult.appendText("Il valore inserito è errato");
			return;
		}

    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        for(int i=1; i<13;i++)
    	{
    		
    		cmbMese.getItems().add(i);
    	}
    }
    
    public void setModel(Model model) {
    	this.model = model;
//    	Map<Integer,String> gg = new HashMap<>();
//		gg.put(1, "Gennaio");
//		gg.put(2, "Febbraio");
//		gg.put(3, "Marzo");
//		gg.put(4, "Aprile");
//		gg.put(5, "Maggio");
//		gg.put(6, "Giugno");
//		gg.put(7, "Luglio");
//		gg.put(8, "Agosto");
//		gg.put(9, "Settembre");
//		gg.put(10, "Ottobre");
//		gg.put(11, "Novembre");
//		gg.put(12, "Dicembre");
  
    }
    
    
}
