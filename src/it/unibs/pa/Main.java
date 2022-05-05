package it.unibs.pa;


import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Main {
	
	public static Persona persone[] = new Persona[1000];
	
	public static void main(String[] args) throws XMLStreamException {
		
		letturaPersona(CreaXML.creaReader("inputPersone", "inputPersone.xml"));
		letturaCodici(CreaXML.creaReader("codiciFiscali", "codiciFiscali.xml"));
		
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
	
	public static void letturaCodici(XMLStreamReader xmlr) {
		
	}

}
