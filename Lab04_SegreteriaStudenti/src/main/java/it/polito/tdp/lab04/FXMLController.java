package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
	Map<Integer, Studente> studenti;

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
		txtResult.clear();

		try {
			String input = txtMatricola.getText().trim();
			if(input==null || input.length()==0) {
				txtResult.setText("Inserire una matricola!");
				return;
			}

			int matricola = Integer.parseInt(input);

			Studente s = model.getStudente(matricola);
			if(s==null ) {
				txtResult.setText("Lo studente non esiste");
				return;
			}
			List<Corso> corsiFromStudente = model.getCorsiDatoStudente(matricola);

			StringBuilder sb = new StringBuilder();
			
			for(Corso corso : corsiFromStudente) {
				if(corsiFromStudente.isEmpty()) {
					txtResult.setText("Lo studente non è iscritto ad alcun corso");
				}
				sb.append(String.format("%-8s ", corso.getCodins()));
				sb.append(String.format("%-4s ", corso.getCrediti()));
				sb.append(String.format("%-45s ", corso.getNome()));
				sb.append(String.format("%-4s ", corso.getPd()));
				sb.append("\n");	
			}
			txtResult.setText(sb.toString());
		} catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
	}

	@FXML
	void cercaIscrittiCorso(ActionEvent event) {
		txtResult.clear();
		txtNome.clear();
		txtCognome.clear();

		try {	
			Corso corso = choiceBox.getValue();

			if(corso == null) {
				txtResult.appendText("Scegli un corso!");
				return;
			}

			List<Studente> studenti = model.studentiInscrittiAlCorso(corso);

			StringBuilder sb = new StringBuilder();

			for (Studente s : studenti) {
//				txtResult.appendText(s.toString() +"\n");
				
     			sb.append(String.format("%-10s ", s.getMatricola()));
    			sb.append(String.format("%-20s", s.getNome()));
    			sb.append(String.format("%-20s ", s.getCognome()));
				sb.append(String.format("%-10s ", s.getCds()));
    			sb.append("\n");
				
			}
			txtResult.appendText(sb.toString());
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
	}


	/*
	 * Metodo che entra in gioco quando premiamo sulla spunta
	 */
	@FXML
	void doCheck(ActionEvent event) {

		//Quando il flag diventa 'true' abbiamo trovato una corrispondenza
		// Altrimenti se alla fine è 'false' non c'è corrispondenza e riportiamo msg di errore
		boolean flag = false;

		String input = txtMatricola.getText().trim();
		if(input == null || input.length()==0 ) {
			txtResult.setText("Devi inserire una matricola!");
			checkBox.setSelected(false);
			return;
		}

		try {
			int matricola = Integer.parseInt(input);
			Map<Integer, Studente> studenti = model.getTuttiGliStudenti();

			//itero la mappa sulle chiavi (matricola)
			for (Integer m : studenti.keySet()) {
				//se la mappa contiene la matricola inserita
				if(m == matricola) {
					txtResult.clear();
					flag = true;
					//creo oggetto studente, corrispondente a quella matricola (valore della mappa)
					Studente s = studenti.get(matricola);
					txtNome.setText(s.getNome());
					txtCognome.setText(s.getCognome());
				}
			}
			if(flag == false) {
				checkBox.setSelected(false);
				txtResult.setText("Non è stata trovata alcuna corrispondenza con la matricola inserita!");
			}
		}catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}

	}

	@FXML
	void doIscrivi(ActionEvent event) {

		txtResult.clear();

		try {

			if (txtMatricola.getText().isEmpty()) {
				txtResult.setText("Inserire una matricola.");
				return;
			}

			if (choiceBox.getValue() == null) {
				txtResult.setText("Selezionare un corso.");
				return;
			}

			// Prendo la matricola in input
			int matricola = Integer.parseInt(txtMatricola.getText().trim());

			// (opzionale)
			// Inserisco il Nome e Cognome dello studente nell'interfaccia
			Studente studente = model.getStudente(matricola);
			if (studente == null) {
				txtResult.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());

			// Ottengo il nome del corso
			Corso corso = choiceBox.getValue();

			// Controllo se lo studente è già iscritto al corso
			if (model.isIscrittoACorso(studente, corso)) {
				txtResult.appendText("Studente già iscritto a questo corso");
				return;
			}

			// Iscrivo lo studente al corso.
			// Controllo che l'inserimento vada a buon fine
			if (!model.inscriviStudenteACorso(studente, corso)) {
				txtResult.appendText("Errore durante l'iscrizione al corso");
				return;
			} else {
				txtResult.appendText("Studente iscritto al corso!");
			}

		} catch (NumberFormatException e) {
			txtResult.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtResult.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
	}

	@FXML
	void doReset(ActionEvent event) {
		txtCognome.clear();
		txtNome.clear();
		txtMatricola.clear();
		txtResult.clear();

		//resetta anche la selezione del menu 
		choiceBox.getSelectionModel().clearSelection();

		//Elimina il check alla checkbox
		checkBox.setSelected(false);
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
		
		// Utilizzare questo font per incolonnare correttamente i dati
		txtResult.setStyle("-fx-font-family: monospace");

	}

	public void setModel(Model model) {
		this.model=model;

		txtResult.setEditable(false);

		//Richiamo metodo dal model che recupera i corsi, salvandoli in una lista
		corsi= model.listaTuttiCorsi();
		
		//Ordino la lista alfabeticamente
		Collections.sort(corsi);

		//popolo la choiseBox con la lista appena ottenuta
		choiceBox.getItems().addAll(corsi);
	}
}
