/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private Simulatore sim;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
     	this.txtResult.clear();
    	Actor a = this.boxAttore.getValue();
    	if(model.getGrafo()==null) {
    		txtResult.appendText("Devi prima creare il grafo!");
    	}
    	if(a==null) {
    		txtResult.appendText("Devi prima selezionare un attore!");
    	}
    	
    	List <Actor> attori= model.attoriSimili(a);
    	txtResult.appendText("ATTORI SIMILI A: " + a.toString()+"\n");
    	for(Actor aa: attori) {
    		
    		txtResult.appendText(aa.toString()+"\n");
    	
    		
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String genere = this.boxGenere.getValue();
    	if(genere == null) {
    		txtResult.appendText("Seleziona un genere!");
    		return;
    	}else {
    		model.creaGrafo(genere);
    		this.boxAttore.getItems().addAll(model.getVertici());
    		
    		txtResult.appendText("GRAFO CREATO!\n");
        	txtResult.appendText("#VERTICI: "+this.model.nVertici() + "\n");
        	txtResult.appendText("#ARCHI: "+this.model.nArchi() + "\n");
    	}
    	
    	
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	this.txtResult.clear();
    	String g = this.txtGiorni.getText();
    	if(g.equals("")) {
    		txtResult.appendText("Inserire un numero nel campo '#Giorni (n)'!");
    	return;
    	}
    	if(model.getGrafo()==null) {
    		txtResult.appendText("Devi prima creare il grafo!");
    		return;
    	}
    	
    	int giorni=0;
    	try{
    		 giorni = Integer.parseInt(g);
    	}catch(NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtResult.appendText("Inserire un numero intero!");
    	}
    	
    	 sim = new Simulatore(giorni, model.getGrafo());
    	 sim.init();
    	 sim.run();
    	 txtResult.appendText("Numero pause: "+sim.numPause()+ "\n");
    	  txtResult.appendText("Attori intervistati:\n");
    	 for(Actor a : sim.intervistati()) {
    		 txtResult.appendText(a.toString() + "\n");
    	 }
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGenere.getItems().addAll(model.getGeneri());
    
    }
}
