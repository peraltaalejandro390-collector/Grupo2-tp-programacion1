package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Negocio {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int numero;
	private Image imagenNegocio;
	 
	
	public Negocio(double x, double y, double ancho, double alto, int numero) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.numero = numero;
		
		this.imagenNegocio = Herramientas.cargarImagen("juego/tienda.png");
	}



	public void dibujarse(Entorno entorno) {
	    if (this.imagenNegocio != null) {
	        double factorEscala = (this.ancho * 0.90) / this.imagenNegocio.getWidth(null);
	        entorno.dibujarImagen(this.imagenNegocio, this.x, this.y, 0, factorEscala);
	    }
	}

	public boolean contienePunto(double px, double py) {
		return (px >= x - ancho / 2 && px <= x + ancho / 2 && 
				py >= y - alto / 2 && py <= y + alto / 2);
	}

	// Getters 
	public double getX() { return x; }
	public double getY() { return y; }
	public double getAncho() { return ancho; }
	public double getAlto() { return alto; }
	public int getNumero() { return numero; }
}

