package it.polito.tdp.imdb.model;

public class Adiacenza {
Actor attore1;
Actor attore2;
int peso;
public Adiacenza(Actor attore1, Actor attore2, int peso) {
	super();
	this.attore1 = attore1;
	this.attore2 = attore2;
	this.peso = peso;
}
public Actor getAttore1() {
	return attore1;
}
public void setAttore1(Actor attore1) {
	this.attore1 = attore1;
}
public Actor getAttore2() {
	return attore2;
}
public void setAttore2(Actor attore2) {
	this.attore2 = attore2;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}



}
