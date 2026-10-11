package juego;
import java.awt.Image;
import entorno.Entorno;
public class Repartidor {
	// Atributos privados
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private int vidas;
	private int ultimaDireccion;// 0: arriba, 1: abajo, 2: izquierda, 3: derecha
    //imagen inicial
	private Image estadosRepartidos;
	//imagenes de estado
	private final Image arriba = entorno.Herramientas.cargarImagen("juego/repartidor_arriba.png");
	private final Image abajo = entorno.Herramientas.cargarImagen("juego/repartidor_abajo.png");
	private final Image derecha = entorno.Herramientas.cargarImagen("juego/repartidor_derecha.png");
	private final Image izquierda = entorno.Herramientas.cargarImagen("juego/repartidor_izquierda.png");
	//tamaño del repartidor
	private double escala;
	
	// Constructor
	public Repartidor(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.065; // o 0.07
		this.velocidad=2.5;
		this.vidas=3;
		this.estadosRepartidos=this.derecha;
	    this.ultimaDireccion = 3;  
		
	}
	public void moverArriba() {
	    this.ultimaDireccion = 0;
	    this.estadosRepartidos = this.arriba;
	    if (this.y - this.velocidad > 20) {
	        this.y -= this.velocidad;
	    }
	}

	public void moverAbajo() {
	    this.ultimaDireccion = 1;
	    this.estadosRepartidos = this.abajo;
	    if (this.y + this.velocidad < 580) {
	        this.y += this.velocidad;
	    }
	}

	public void moverIzquierda() {
	    this.ultimaDireccion = 2;
	    this.estadosRepartidos = this.izquierda;
	    if (this.x - this.velocidad > 20) {
	        this.x -= this.velocidad;
	    }
	}

	public void moverDerecha() {
	    this.ultimaDireccion = 3;
	    this.estadosRepartidos = this.derecha;
	    if (this.x + this.velocidad < 780) {
	        this.x += this.velocidad;
	    }
	}
	public void dibujarse(Entorno entorno) {
	    entorno.dibujarImagen(this.estadosRepartidos, this.x, this.y, 0, this.escala);
	}
	
	public double getX() {
	    return this.x;
	}

	public double getY() {
	    return this.y;
	}

	public int getUltimaDireccion() {
	    return this.ultimaDireccion;
	}

}
