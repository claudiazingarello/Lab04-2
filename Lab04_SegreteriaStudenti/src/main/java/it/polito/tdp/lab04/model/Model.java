package it.polito.tdp.lab04.model;

import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		//Si crea una istanza del dao
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}

	/**
	 * Il metodo ci serve per popolare la choiceBox
	 * @return una lista contenente tutti i corsi
	 */
	public List<Corso> listaTuttiCorsi(){
		return corsoDao.getTuttiICorsi();
	}
	
	public void studentiIscrittiAlCorso(Corso c){
		corsoDao.getStudentiIscrittiAlCorso(c);	
	}
	
	//****************************
	public List<Studente> studentiInscrittiAlCorso(Corso c){
		return corsoDao.getStudentiInscrittiAlCorso(c);	
	}
	//*****************

	public Studente getStudente(int matricola) {
		return studenteDao.getStudente(matricola);
	}
	
	public Map<Integer, Studente> getTuttiGliStudenti() {
		return studenteDao.getTuttiGliStudenti();
	}
	
	public List<Corso> getCorsiDatoStudente(int matricola) {
		return studenteDao.getCorsiDatoStudente(matricola);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return corsoDao.inscriviStudenteACorso(studente, corso);
	}
}
