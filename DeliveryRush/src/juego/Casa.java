package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Casa {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int numero;
	private int tipoCasa;
	   private Image casa; 
	
	   public Casa(double x, double y, double ancho, double alto, int numero, int tipoCasa) {
			
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.numero = numero;
		
		if (tipoCasa == 1) {
			this.casa = Herramientas.cargarImagen("juego/casa1.png"); // Techo en L
		} else {
			this.casa = Herramientas.cargarImagen("juego/casa2.png"); // Techo Recto
		}
	}

	   public void dibujarse(Entorno entorno) {
		    if (this.casa != null) { 
		      double factorEscala = (this.ancho * 0.90) / this.casa.getWidth(null);
		        entorno.dibujarImagen(this.casa, this.x, this.y, 0, factorEscala); 
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
	public int getTipoTecho() { return tipoCasa; }
}


//guarda la posicion (x, y),  ancho, alto, numero identificador, imagen de casa 1 o 2 segun el tipo de techo, dibujo en pantalla adaptando su escala xq sino se deformaba, un rectangulo por si falla la imagen , un texto identificador (ej: C1 ) arriba de las casas, colisiones si algo toca los limites de la casa
