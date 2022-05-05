package it.unibs.pa;

import javax.xml.stream.XMLStreamException;

public class TestMAin {

	public static void main(String[] args) throws XMLStreamException {
		
		Persona p = new Persona("CARMELA", "BO", 'F', "1960-02-20",  "SAN GIACOMO DELLE SEGNATE");
		
		System.out.println("Codice fiascale: " + p.creaCodiceFiscale());
		
		CodiceFiscale cf = new CodiceFiscale("BNDMNG65S45B898Z");
		
		System.out.println(cf.isValido());
	}

}
