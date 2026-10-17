package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

import javax.swing.ImageIcon;

public class Casa {
	
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int numero;
	private int tipoTecho;
	   private Image imagenTecho; 
	
	   public Casa(double x, double y, double ancho, double alto, int numero, int tipoTecho) {
			
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.numero = numero;
		this.tipoTecho = tipoTecho;
		
		if (tipoTecho == 1) {
			this.imagenTecho = Herramientas.cargarImagen("Juego/casa1.png"); // Techo en L
		} else {
			this.imagenTecho = Herramientas.cargarImagen("Juego/casa2.png"); // Techo Recto
		}
	}

	
	// - dibujarse(Entorno entorno)
	   public void dibujarse(Entorno entorno) {
		    if (this.imagenTecho != null) {//redimensiona la imagen proporcionalmente para que no se deforme 
		      double factorEscala = (this.ancho * 0.55) / this.imagenTecho.getWidth(null);
		       
		        
		        entorno.dibujarImagen(this.imagenTecho, this.x, this.y, 0,factorEscala); 
		    } else {//en caso de que no carge imagen 
		        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, new Color(70, 130, 180));
		    }
		    
		    // Texto identificador de la casa posicionado de forma estable
		    entorno.cambiarFont("Arial", 10, Color.WHITE, entorno.NEGRITA);
		    entorno.escribirTexto("Casa" + numero, this.x - this.ancho * 0.4, this.y - this.alto * 0.1);
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
}



//guarda la posicion (x, y),  ancho, alto, numero identificador, imagen de casa 1 o 2 segun el tipo de techo, dibujo en pantalla adaptando su escala xq sino se deformaba, un rectangulo por si falla la imagen , un texto identificador (ej: C1 ) arriba de las casas, colisiones si algo toca los limites de la casa
