package it.unibs.pa;

import java.io.FileInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class CodiceFiscale {
	
	//attributi
	
	private Persona p;
	
	private String codiceNome;
	private String codiceCognome;
	private String codiceDataESesso;
	private String codiceComune;
	private char codiceControllo;
	
	
	private String codiceCompleto;
	
	//costrutturi
	
	public CodiceFiscale(Persona p) throws XMLStreamException {
		this.p = p;
		this.codiceNome = creaCodiceNome();
		this.codiceCognome = creaCodiceCognome();
		this.codiceDataESesso = creaCodiceData();
		this.codiceComune = creaCodiceComune();
		this.codiceControllo = creaCodiceControllo(this.codiceCognome + this.codiceNome + this.codiceDataESesso + this.codiceComune);
		this.codiceCompleto = codiceCognome + codiceNome + codiceDataESesso + codiceComune + codiceControllo;
	}
	
	public CodiceFiscale(String codiceFiscale) {
		this.codiceCompleto = codiceFiscale;
	}
	
	// metodi
	public String getCodiceCompleto() {
		return codiceCompleto;
	}

	
	
	public String creaCodiceNome () {
		
		String nome = p.getNome();
		String codNome ="";
		int numCons = numeroConsonanti(nome);
		
		if (nome.length() < 3)
			codNome = stringaConMenoDiTreCaratteri(nome);
		
		else if (numCons >= 4) {
			String str = stringaSenzaVocali(nome);
			codNome = "" + str.charAt(0) + str.charAt(2) + str.charAt(3);
		}
		else 
			codNome = stringaConMenoDiTreConsonanti(nome);
		return codNome;
	}
	
	public String creaCodiceCognome() {
		
		String codCogn = "";
		String cognome = p.getCognome();
		
		if(cognome.length() < 3)
			codCogn = stringaConMenoDiTreCaratteri(cognome);
		else
			codCogn = stringaConMenoDiTreConsonanti(cognome);
		return codCogn;
	}
	
	
	public int numeroConsonanti(String s) {
		int numeroVocali = 0;
		for(int i = 0; i< s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U')
				numeroVocali++;
		}
		return s.length() - numeroVocali;
	}
	
	public String stringaSenzaVocali(String s ) {
		String stringaSenzaVocali = "";		

		for(int i = 0; i< s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U');
			else 
				stringaSenzaVocali += ch;	
		}
	
		return stringaSenzaVocali;
	}
	
	public String stringaSenzaConsonanti(String s) {
		String stringaSenzaConsonanti = "";
		for(int i = 0; i< s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U')
				stringaSenzaConsonanti += ch;	
		}
		return stringaSenzaConsonanti;
	}
	
	public String stringaConMenoDiTreCaratteri(String s) {
		String str = "";
		if (numeroConsonanti(s) == 2) {
			str = s + 'X';
			return str;
		}
		else if(numeroConsonanti(s) == 1 && s.length() - numeroConsonanti(s)  == 1) {
			str = stringaSenzaVocali(s)+ stringaSenzaConsonanti(s).charAt(0) + 'X';
			return str;
		}
		else if (numeroConsonanti(s) - s.length() == 2) {
			str = s + 'X';
			return str;
		}
		else if (s.length() == 1){
			str = s + 'X' + 'X';
			return str;
		}
		else 
			return "Come ci sei finito qui?";
	}
	
	public String stringaConMenoDiTreConsonanti(String s) {
	
		String str = "";
		
		int numCons = numeroConsonanti(s);
		if (numCons >= 3)
			str = "" +stringaSenzaVocali(s).charAt(0) + stringaSenzaVocali(s).charAt(1) + stringaSenzaVocali(s).charAt(2);
		
		else if (numCons == 2)
			str = stringaSenzaVocali(s) + stringaSenzaConsonanti(s).charAt(0);
		
		else if (numCons == 1)
			str = stringaSenzaVocali(s) + stringaSenzaConsonanti(s).charAt(0) + stringaSenzaConsonanti(s).charAt(1);
		
		else {
			str ="" + stringaSenzaConsonanti(s).charAt(0) + stringaSenzaConsonanti(s).charAt(1) + stringaSenzaConsonanti(s).charAt(2);
		}
		return str;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String creaCodiceData() {
		
		String lettere = "ABCDEHLMPRST";
		
		String data = p.getData();
		String codAnno = data.substring(2, 4); //ultimi due caratteri dell'anno
		int mese =  Integer.parseInt(data.substring(5, 7));  //prende il mese e lo converte in int (la data è una stringa)
		char codMese = lettere.charAt(mese -1);
		String codGiorno = data.substring(8);
		
		if (p.getSesso() == 'F') { //se è femmina aggiungo 40
			int giornoF = Integer.parseInt(codGiorno);
			giornoF += 40;
			codGiorno = String.valueOf(giornoF);
		}
		else if (p.getSesso() != 'M') {
			System.out.println("Ci sono solo due sessi");
			System.exit(1);
		}
		
		return codAnno + codMese + codGiorno;
	}
	
	public String creaCodiceComune() throws XMLStreamException {
		
		boolean trovato = false;
		String codComune = "";
		
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		try {
		xmlif = XMLInputFactory.newInstance();
		xmlr = xmlif.createXMLStreamReader("comuni", new FileInputStream("comuni.xml"));
		} catch (Exception e) {
		System.out.println("Errore nell'inizializzazione del reader:");
		System.out.println(e.getMessage());
		}
		
		while(trovato != true && xmlr.hasNext()) {
			switch(xmlr.getEventType()) {
			case XMLStreamConstants.CHARACTERS:
				if (xmlr.getText().equals(p.getComuneDiNascita())){
					trovato = true;
					xmlr.next();
					xmlr.next();
					xmlr.next();
					xmlr.next();
					codComune = xmlr.getText();					
				}
				break;
			}
			xmlr.next();
		}
		if (trovato == false ) {
			System.out.println("Dove cazzo sei nato??");
			System.exit(1);
		}
		
		return codComune;
	}
	
	public char creaCodiceControllo(String codiceParziale) {
		
		  
		String codicePari = "", codiceDispari = "";
		char carattereControllo;
		
		int [] caratteriDispari = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3,
									6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
		
		int [] caratteriPari = {0, 1, 2, 3, 4 , 5, 6, 7 , 8, 9, 0, 1 ,2, 3, 4, 5, 6 , 7, 8 , 9 , 10, 11, 12 ,13, 14, 15, 16, 17,
								18, 19, 20, 21, 22, 23, 24, 25};
		
		String tabellaResto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
		for(int i =0; i< codiceParziale.length(); i++) {
			if ((i +1) % 2 == 0) {
				codicePari += codiceParziale.charAt(i);
				}
			else 
				codiceDispari += codiceParziale.charAt(i);
		}
		
		int sommaPari = conversioneCaratteri(codicePari, caratteriPari);
		int sommaDispari = conversioneCaratteri(codiceDispari, caratteriDispari);
		int resto = (sommaPari + sommaDispari)%26;
		
		carattereControllo = tabellaResto.charAt(resto);
		
		return carattereControllo;
		
	}
	
	public int conversioneCaratteri (String codice, int [] numeri) {
		int somma = 0;
		for (int i = 0; i < codice.length(); i++) {
			if (isLettera(codice.charAt(i))) {
				somma += numeri[codice.charAt(i) - 55];
			}
			else 
				somma += numeri[codice.charAt(i) - 48];		
		}
		return somma;
	}
	
	public boolean isLettera(char ch) {
		if (ch >= 'A' && ch <= 'Z')
			return true;
		else 
			return false;
	}
	
	public boolean isNumero(char ch) {
		if (ch >= '0' && ch <= '9')
			return true;
		else 
			return false;
	}
	
	public boolean isValido() {	
		String cf = this.codiceCompleto;
		cf.toUpperCase();
		
		if (cf.length() < 16)
			return false;
		
		
		if(!isPosizioneValida(cf))
			return false;
		
		char mese = cf.charAt(8);
		int giorno = Integer.parseInt(cf.substring(9, 11));	
		
		if(!isMeseValido(mese))
			return false;
		
		if(!isGiornoValido(giorno, mese))
			return false;
		
		if (!(cf.charAt(15) == creaCodiceControllo(cf.substring(0, 15))))
			return false;
		
		return true;	
	}
	
	public boolean isPosizioneValida(String cf) {
		for(int i = 0; i < cf.length(); i++) {
			if(i == 12)
				i = 15;
			if (i == 9)
				i = 11;	
			if (i == 6)
				i = 8;
			if (!isLettera(cf.charAt(i)))
				return false;
			
		}
		if (!isNumero(cf.charAt(6)) || !isNumero(cf.charAt(7)) || !isNumero(cf.charAt(9)) || !isNumero(cf.charAt(10)) ||
				!isNumero(cf.charAt(12)) || !isNumero(cf.charAt(13)) || !isNumero(cf.charAt(14)))
			return false;
		
		return true;
	}
	
	public boolean isMeseValido(char mese) {
		String lettere = "ABCDEHLMPRST";
		for(int i = 0; i<lettere.length(); i++) {
			if (mese == lettere.charAt(i))
				return true;
		}
		return false;
	}
	public boolean isGiornoValido(int giorno, char mese) {
		
		if(giorno < 1 || (giorno > 31 && giorno < 41) || giorno > 71)
			return false;
		
		if (mese == 'B' && (giorno > 28 || giorno > 68))
			return false;
		
		if ((mese == 'S' || mese == 'D' || mese == 'H' || mese == 'P') && (giorno == 31 || giorno == 71))
			return false;
		
		return true;
	}
}

