package it.unibs.pa;

import java.io.FileInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

public class CreaXML {
	
	public static XMLStreamReader creaReader (String nomeFile, String nomeConEstensione) {
		
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader(nomeFile, new FileInputStream(nomeConEstensione));
			} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
			}
		return xmlr;
	}

}
