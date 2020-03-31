package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		
		final String sql = "select * from corso where codins = ?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();


			if (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				corso.setCodins(codins);
				corso.setCrediti(numeroCrediti);
				corso.setNome(nome);
				corso.setPd(periodoDidattico);
				

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
			}

			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */

	public void getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT * from studente as s, iscrizione as i where s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> studentiIscrittiAlCorso = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();


			while (rs.next()) {

				//***********HO MESSO INT AL POSTO DI STRING
				int matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				
				Studente s = new Studente(matricola, nome, cognome, cds);
				studentiIscrittiAlCorso.add(s);

				System.out.println(matricola + " " + cognome + " " + nome + " " + cds);
			}

			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}

	//********** METODO CHE RITORNA LISTA AL POSTO DI VOID (FATTO DA ME)
	
	public List<Studente> getStudentiInscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT * from studente as s, iscrizione as i where s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> studentiIscrittiAlCorso = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();


			while (rs.next()) {

				int matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				
				Studente s = new Studente(matricola, cognome, nome, cds);
				studentiIscrittiAlCorso.add(s);

				System.out.println(matricola + " " + cognome + " " + nome + " " + cds);
			}

			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return studentiIscrittiAlCorso;
	}

	
}
