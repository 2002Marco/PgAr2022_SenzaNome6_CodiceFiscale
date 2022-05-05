package it.unibs.pa;


import java.util.ArrayList;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Main {
	
	public static Persona persone[] = new Persona[1000];
	public static ArrayList <CodiceFiscale> codiciValidi = new ArrayList <CodiceFiscale>();
	public static ArrayList <Persona> personeValide = new ArrayList <Persona>();
	public static ArrayList <CodiceFiscale> codiciInvalidi = new ArrayList <CodiceFiscale>();
	public static ArrayList <CodiceFiscale> codiciSpaiati = new ArrayList <CodiceFiscale>();
	
	public static void main(String[] args) throws XMLStreamException {
		
		letturaPersona(CreaXML.creaReader("inputPersone", "inputPersone.xml"));
		letturaCodici(CreaXML.creaReader("codiciFiscali", "codiciFiscali.xml"));
		controlloPresenzaCodici();
	}
	
	public static void letturaPersona(XMLStreamReader xmlr) throws XMLStreamException {
		
		int i = 0;
		String nome = "", cognome = "", comuneDiNascita = "", data = "";
		char sesso = ' ';
			while(i < 1000) {
				
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					if (xmlr.getLocalName().equals("nome")) {
						xmlr.next();
						
						nome = xmlr.getText();
					}
					else if (xmlr.getLocalName().equals("cognome")) {
						xmlr.next();
						cognome = xmlr.getText();
					}
					else if (xmlr.getLocalName().equals("sesso")) {
						xmlr.next();
						sesso = xmlr.getText().charAt(0);
					}
					else if (xmlr.getLocalName().equals("comune_nascita")) {
						xmlr.next();
						comuneDiNascita = xmlr.getText();
					}
					else if (xmlr.getLocalName().equals("data_nascita")) {
						xmlr.next();
						data = xmlr.getText();
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (xmlr.getLocalName().equals("persona")) {
						persone[i] = new Persona(nome, cognome, sesso, data, comuneDiNascita);
						i++;
					}
					break;
				}
				xmlr.next();	
			}
	}
	
	public static void letturaCodici(XMLStreamReader xmlr) throws XMLStreamException {
		
		while(xmlr.hasNext()) {
			switch(xmlr.getEventType()) {
			case XMLStreamConstants.CHARACTERS:
				CodiceFiscale tmp = new CodiceFiscale (xmlr.getText());
				if (xmlr.getText().trim().length() > 0) {
				if (tmp.isValido()) {
					codiciValidi.add(tmp);
				}	
				else
					codiciInvalidi.add(tmp);
			}
				break;
			}
			
			xmlr.next();
		}
	}
	
	public static void controlloPresenzaCodici() {
		
		
		
		for(int i = 0; i < 1000; i++) {
			boolean trovato = false;
			
			for(int k = 0; !trovato && k< codiciValidi.size() ; k++) {
				if (persone[i].getCodiceFiscale().getCodiceCompleto().equals(codiciValidi.get(k).getCodiceCompleto())) {
					personeValide.add(persone[i]);
					trovato = true;
				}
				if (k == codiciValidi.size() -1 && !trovato) 
					codiciSpaiati.add(persone[i].getCodiceFiscale());
				
			}
			
		}
		System.out.println(personeValide.size());
		System.out.println(codiciSpaiati.size());
	}

}
