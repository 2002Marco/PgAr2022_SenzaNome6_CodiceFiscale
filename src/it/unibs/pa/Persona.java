package it.unibs.pa;

import javax.xml.stream.XMLStreamException;

public class Persona {
	
	private String nome;
	private String cognome;
	private char sesso;
	private String data;
	private String comuneDiNascita;
	
	private String codiceFiscale;
	


	public Persona(String nome, String cognome, char sesso, String data, String comuneDiNascita) throws XMLStreamException {
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.data = data;
		this.comuneDiNascita = comuneDiNascita;
		this.codiceFiscale = creaCodiceFiscale();
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public char getSesso() {
		return sesso;
	}

	public String getData() {
		return data;
	}

	public String getComuneDiNascita() {
		return comuneDiNascita;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setComuneDiNascita(String comuneDiNascita) {
		this.comuneDiNascita = comuneDiNascita;
	}
	
	
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String creaCodiceFiscale() throws XMLStreamException {
		CodiceFiscale cd = new CodiceFiscale(this);
		return cd.getCodiceCompleto();
		
	}
	
	

}
