package it.unibs.pa;

import javax.xml.stream.XMLStreamException;

public class Persona {
	
	private String nome;
	private String cognome;
	private char sesso;
	private String data;
	private String comuneDiNascita;
	private boolean presente;
	
	private CodiceFiscale codiceFiscale;
	


	public Persona(String nome, String cognome, char sesso, String data, String comuneDiNascita) throws XMLStreamException {
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.data = data;
		this.comuneDiNascita = comuneDiNascita;
		this.codiceFiscale = creaCodiceFiscale();
		this.presente = false;
	}
	
	
	
	public boolean isPresente() {
		return presente;
	}



	public void setPresente(boolean presente) {
		this.presente = presente;
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
	
	
	
	public CodiceFiscale getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(CodiceFiscale codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public CodiceFiscale creaCodiceFiscale() throws XMLStreamException {
		
		return new CodiceFiscale(this);
		
	}
	
	

}
