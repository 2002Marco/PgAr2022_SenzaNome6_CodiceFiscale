package it.unibs.pa;


import java.util.ArrayList;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Main {
	
	public static Persona persone[] = new Persona[1000];
	public static ArrayList <CodiceFiscale> codiciValidi = new ArrayList <CodiceFiscale>();
	public static ArrayList <CodiceFiscale> codiciInvalidi = new ArrayList <CodiceFiscale>();
	public static ArrayList <CodiceFiscale> codiciSpaiati = new ArrayList <CodiceFiscale>();
	
	public static void main(String[] args) throws XMLStreamException {
		
		letturaPersona(CreaXML.creaReader("inputPersone", "inputPersone.xml"));
		letturaCodici(CreaXML.creaReader("codiciFiscali", "codiciFiscali.xml"));
		controlloPresenzaCodici();
		creaXML(CreaXML.creaWriter("codiciPersone.xml"));
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
					persone[i].setPresente(true);
					trovato = true;
				}
				if (k == codiciValidi.size() -1 && !trovato) 
					codiciSpaiati.add(persone[i].getCodiceFiscale());
				
			}
			
		}
	}
	
	private static void creaXML(XMLStreamWriter xmlw) throws XMLStreamException {
		
		
		xmlw.writeStartElement("output");
		xmlw.writeStartElement("persone");
		xmlw.writeAttribute("numero", Integer.toString(persone.length));
		for(int i = 0; i < persone.length; i++) {
			
			xmlw.writeStartElement("persona");		
			xmlw.writeAttribute("id", Integer.toString(i));
			
			xmlw.writeStartElement("nome");		//nome
			xmlw.writeCharacters(persone[i].getNome());
			xmlw.writeEndElement();				
				
			xmlw.writeStartElement("cognome");	//cognome
			xmlw.writeCharacters(persone[i].getCognome());
			xmlw.writeEndElement();
		
			xmlw.writeStartElement("sesso");	//sesso
			xmlw.writeCharacters("" + persone[i].getSesso());
			xmlw.writeEndElement();
		
			xmlw.writeStartElement("comune_nascita");	//comune di nascita
			xmlw.writeCharacters(persone[i].getComuneDiNascita());
			xmlw.writeEndElement();
			
			xmlw.writeStartElement("data_nascita");	//data di nascita
			xmlw.writeCharacters(persone[i].getData());
			xmlw.writeEndElement();
	
			xmlw.writeStartElement("codice_fiscale");//codice fiscale
			if (persone[i].isPresente())
				xmlw.writeCharacters(persone[i].getCodiceFiscale().getCodiceCompleto());
			else 
				xmlw.writeCharacters("ASSENTE");
				
			xmlw.writeEndElement();
			
			xmlw.writeEndElement();
		}
		
		xmlw.writeEndElement(); // chiude <persone>
		
		xmlw.writeStartElement("codici");
		xmlw.writeStartElement("invalidi");
		xmlw.writeAttribute("numero", Integer.toString(codiciInvalidi.size()));
		
		for (int i = 0; i < codiciInvalidi.size(); i++) {
			xmlw.writeStartElement("codice");
			xmlw.writeCharacters(codiciInvalidi.get(i).getCodiceCompleto());
			xmlw.writeEndElement();
		}
		xmlw.writeEndElement();//chiude invalidi
		
		xmlw.writeStartElement("spaiati");
		xmlw.writeAttribute("numero", Integer.toString(codiciSpaiati.size()));
		for (int i = 0; i < codiciSpaiati.size(); i++) {
			xmlw.writeStartElement("codice");
			xmlw.writeCharacters(codiciSpaiati.get(i).getCodiceCompleto());
			xmlw.writeEndElement();
		}
		xmlw.writeEndElement();//chiude spaiati
		
		xmlw.writeEndDocument(); //chiude output
		
		xmlw.writeEndDocument();
		xmlw.flush();
		xmlw.close();
		
		
	}

}
