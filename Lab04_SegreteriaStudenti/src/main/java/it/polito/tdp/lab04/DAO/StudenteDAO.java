package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;


public class StudenteDAO {
	
	public Studente getStudente(int matricola) {
		Studente s = new Studente();
		
		final String sql = "SELECT * from studente where matricola = ?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			

			if (rs.next()) {

				
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");
				
				s.setMatricola(matricola);
				s.setCognome(cognome);
				s.setNome(nome);
				s.setCds(cds);

				System.out.println(matricola + " " + cognome + " " + nome + " " + cds);
			}

			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
		return s;
	}
	
	/*
	 * Ottengo la lista di tutti gli studenti
	 * 
	 */
	public Map<Integer, Studente> getTuttiGliStudenti() {

		final String sql = "SELECT * FROM studente";

		//Creo una mappa identificata da matricola con valore lo studente
		Map<Integer, Studente> studenti = new HashMap<Integer, Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("CDS");

				

				// Crea un nuovo JAVA Bean Studente
				// Aggiungi il nuovo oggetto Studente alla mappa studenti
				
				Studente s = new Studente(matricola, cognome, nome, cds);
				studenti.put(matricola, s);
			}

			conn.close();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
		return studenti;
	}
	
	/*
	 * Dato uno studente, ottengo i corsi a cui è iscritto
	 */
	public List<Corso> getCorsiDatoStudente(int matricola) {
		
		final String sql = "SELECT * FROM iscrizione, corso WHERE iscrizione.codins = corso.codins AND iscrizione.matricola=?";
		
		List<Corso> corsiFromStudente = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();


			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsiFromStudente.add(c);
			}
			conn.close();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return corsiFromStudente;
		
	}
}
