package it.polito.tdp.lab04.model;

import java.util.LinkedList;
import java.util.List;

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
	
	public List<Studente> studentiIscrittiAlCorso(Corso c){
		return corsoDao.getStudentiIscrittiAlCorso(c);
		
	}
}
