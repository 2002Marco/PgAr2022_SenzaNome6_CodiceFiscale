package it.unibs.pa;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class CreaXML {
	
	private static final int utf = 0;

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
	
	public static XMLStreamWriter creaWriter (String nomeFile) {
		
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try {
		xmlof = XMLOutputFactory.newInstance();
		xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(nomeFile), “utf-8”);
		xmlw.writeStartDocument(“utf-8”, “1.0”);
		} catch (Exception e) {
		System.out.println("Errore nell'inizializzazione del writer:");
		System.out.println(e.getMessage());
		}
		
	}

}
