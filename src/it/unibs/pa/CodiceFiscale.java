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
	
	
	private String codiceCompleto;    //codice fiscale
	
	//costrutturi
	
	public CodiceFiscale(Persona p) throws XMLStreamException {      //costruttore crea il codice partendo dai dati di una persona
		this.p = p;          
		this.codiceNome = creaCodiceNome();
		this.codiceCognome = creaCodiceCognome();
		this.codiceDataESesso = creaCodiceData();
		this.codiceComune = creaCodiceComune();
		this.codiceControllo = creaCodiceControllo(this.codiceCognome + this.codiceNome + this.codiceDataESesso + this.codiceComune);
		this.codiceCompleto = codiceCognome + codiceNome + codiceDataESesso + codiceComune + codiceControllo;
	}
	
	public CodiceFiscale(String codiceFiscale) {      //costruttore crea il codice partendo da codice fiscale
		this.codiceCompleto = codiceFiscale;
	}
	
	// metodi
	public String getCodiceCompleto() {       //costruisce il codice partendo da una stringa
		return codiceCompleto;
	}

	
	
	public String creaCodiceNome () {
		
		String nome = p.getNome();
		String codNome ="";
		int numCons = numeroConsonanti(nome);
		
		if (nome.length() < 3)     //controlla il caso in cui il nome abbia meno di 3 caratteri
			codNome = stringaConMenoDiTreCaratteri(nome);
		
		else if (numCons >= 4) {  //caso con 4 o più consonanti
			String str = stringaSenzaVocali(nome);
			codNome = "" + str.charAt(0) + str.charAt(2) + str.charAt(3);
		}
		else         //controlla il caso in cui il nome abbia meno di 3 consonanti
			codNome = stringaConMenoDiTreConsonanti(nome);
		return codNome;
	}
	
	public String creaCodiceCognome() {
		
		String codCogn = "";
		String cognome = p.getCognome();
		
		if(cognome.length() < 3)     //controlla il caso in cui il nome abbia meno di 3 caratteri
			codCogn = stringaConMenoDiTreCaratteri(cognome);
		else     //controlla il caso in cui il nome abbia meno di 3 consonanti
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
	
	public String stringaSenzaVocali(String s ) {     //crea la stringa senza le vocali
		String stringaSenzaVocali = "";		

		for(int i = 0; i< s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U');
			else 
				stringaSenzaVocali += ch;	
		}
	
		return stringaSenzaVocali;
	}
	
	public String stringaSenzaConsonanti(String s) {     //crea la stringa senza le consonanti
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
		if (numeroConsonanti(s) == 2) {     //caso 2 consonanti
			str = s + 'X';
			return str;
		}
		else if(numeroConsonanti(s) == 1 && s.length() - numeroConsonanti(s)  == 1) {    //caso 1 vocale e una consonante
			str = stringaSenzaVocali(s)+ stringaSenzaConsonanti(s).charAt(0) + 'X';
			return str;
		}
		else if (numeroConsonanti(s) - s.length() == 2) {    //caso 2 vocali
			str = s + 'X';
			return str;
		}
		else if (s.length() == 1){     //caso lunghezza 1
			str = s + 'X' + 'X';
			return str;
		}
		else 
			return "Come ci sei finito qui?";     
	}
	
	public String stringaConMenoDiTreConsonanti(String s) {
	
		String str = "";
		
		int numCons = numeroConsonanti(s);
		if (numCons >= 3)   //solo per il cognome (non entrerà mai per il nome)
			str = "" +stringaSenzaVocali(s).charAt(0) + stringaSenzaVocali(s).charAt(1) + stringaSenzaVocali(s).charAt(2);
		
		else if (numCons == 2)   //caso 2 consonanti 
			str = stringaSenzaVocali(s) + stringaSenzaConsonanti(s).charAt(0);
		
		else if (numCons == 1)   //coso 1 consonante
			str = stringaSenzaVocali(s) + stringaSenzaConsonanti(s).charAt(0) + stringaSenzaConsonanti(s).charAt(1);
		
		else {   //caso senza consonanti
			str ="" + stringaSenzaConsonanti(s).charAt(0) + stringaSenzaConsonanti(s).charAt(1) + stringaSenzaConsonanti(s).charAt(2);
		}
		return str;
	}
	
	//////////////
	
	public String creaCodiceData() {
		
		String lettere = "ABCDEHLMPRST";
		
		String data = p.getData();
		String codAnno = data.substring(2, 4); //ultimi due caratteri dell'anno
		int mese =  Integer.parseInt(data.substring(5, 7));  //prende il mese e lo converte in int (la data e' una stringa)
		char codMese = lettere.charAt(mese -1);
		String codGiorno = data.substring(8);  //prende il giorno xd
		
		if (p.getSesso() == 'F') { //se e' femmina aggiungo 40
			int giornoF = Integer.parseInt(codGiorno);
			giornoF += 40;
			codGiorno = String.valueOf(giornoF);
		}
		else if (p.getSesso() != 'M') {
			System.out.println("Ci sono solo due sessi e gli interisti");
			System.exit(1);
		}
		
		return codAnno + codMese + codGiorno;
	}
	
	public String creaCodiceComune() throws XMLStreamException {
		
		boolean trovato = false;
		String codComune = "";
		
		XMLStreamReader xmlr = CreaXML.creaReader("comuni", "comuni.xml");   //crea un lettore xml 
		
		while(trovato != true && xmlr.hasNext()) {   //ciclo finisce quando hai trovato il comune oppure non è presente
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
		if (trovato == false ) {    //nel caso il comune non è stato trovato
			System.out.println("Dove sei nato??");
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
	
		for(int i =0; i< codiceParziale.length(); i++) {   //crea i codici con i caratteri pari e dispari
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
	
	public int conversioneCaratteri (String codice, int [] numeri) {    //richiede in input il codice e la stringa di conversione 
		int somma = 0;
		for (int i = 0; i < codice.length(); i++) {
			if (isLettera(codice.charAt(i))) {   //controlla se è una lettera
				somma += numeri[codice.charAt(i) - 55];    //toglie 55 perchè la A corrisponde al valore 65
			}
			else 
				somma += numeri[codice.charAt(i) - 48];    //toglie 48 perchè lo 0 corrisponde al valore 48
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
		
		if (cf.length() < 16)   //verifica se la lunghezza non sia minore di 16
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
		for(int i = 0; i < cf.length(); i++) {   //controllo che le lettere e numeri siano nelle posizioni corrispondenti
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
	
	public boolean isMeseValido(char mese) {   //controlla che la lettera del mese sia valida
		String lettere = "ABCDEHLMPRST";
		for(int i = 0; i<lettere.length(); i++) {
			if (mese == lettere.charAt(i))
				return true;
		}
		return false;
	}
	public boolean isGiornoValido(int giorno, char mese) {    //controlla che il giorno sia valido
		
		char sesso;
		if (giorno <= 31 && giorno >= 1)  
			sesso = 'M';
		else if (giorno <= 71 && giorno >= 41)
			sesso = 'F';
		else 
			return false;
		
		if(giorno < 1 || (giorno > 31 && giorno < 41) || giorno > 71)
			return false;
		
		if (mese == 'B' && ((giorno > 28 && sesso == 'M') || (giorno > 68 && sesso == 'F')))   //caso febbraio
			return false;
		
		if ((mese == 'S' || mese == 'D' || mese == 'H' || mese == 'P') && (giorno == 31 || giorno == 71))   //controllo dei mesi con 30 giorni 
			return false;
		
		return true;
	}
}

