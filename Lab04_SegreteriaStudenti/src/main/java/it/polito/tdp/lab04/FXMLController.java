package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FXMLController {
	
	Model model;
	
	List<Corso> corsi;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Corso> choiceBox;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private CheckBox checkBox;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void cercaCorsi(ActionEvent event) {

    }

    @FXML
    void cercaIscrittiCorso(ActionEvent event) {
    	txtResult.clear();
		txtNome.clear();
		txtCognome.clear();
		
    	Corso corso = choiceBox.getValue();
    	
    	if(corso == null) {
    		txtResult.appendText("Scegli un corso!");
    		return;
    	}
    	
    	List<Studente> studenti = model.studentiIscrittiAlCorso(corso);
    	
//    	StringBuilder sb = new StringBuilder();
    	
    	for (Studente s : studenti) {
    		txtResult.appendText(s.toString() +"\n");
    		/*
     			sb.append(String.format("%-10s ", s.getMatricola()));
    			sb.append(String.format("%-20s", s.getNome()));
    			sb.append(String.format("%-20s ", s.getCognome()));
				sb.append(String.format("%-10s ", s.getCds()));
    			sb.append("\n");
    		*/
    	}
    
//    	txtResult.appendText(sb.toString());
    }

    @FXML
    void doCheck(ActionEvent event) {

    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {
    	txtCognome.clear();
    	txtNome.clear();
    	txtMatricola.clear();
    	txtResult.clear();
    	
    	//resetta anche la selezione del menu 
    	choiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    void scegliCorso(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert choiceBox != null : "fx:id=\"choiceBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert checkBox != null : "fx:id=\"checkBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		
		//Richiamo metodo dal model che recupera i corsi, salvandoli in una lista
		corsi= model.listaTuttiCorsi();
		
		//Ordino la lista alfabeticamente
		Collections.sort(corsi);
		
		//popolo la choiseBox con la lista appena ottenuta
		choiceBox.getItems().addAll(corsi);
	}
}
