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
	private final Image derecha = entorno.Herramientas.cargarImagen("juego/repartidor_izquierda.png");//esaba al reves 
	private final Image izquierda = entorno.Herramientas.cargarImagen("juego/repartidor_derecha.png");
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
	    this.ancho = 21.0;
	    this.alto = 21.0;
	    
	}
	public void moverArriba(Ciudad ciudad ) {
	    this.ultimaDireccion = 0;
	    this.estadosRepartidos = this.arriba;
	  
	    Double nuevo_y = this.y - this.velocidad;
	   
	    if(nuevo_y > 16 && !ciudad.hayColisiones(x, nuevo_y, ancho, alto)) {
	    	this.y=nuevo_y;
	    	
	    }
	}

	public void moverAbajo(Ciudad ciudad) {
	    this.ultimaDireccion = 1;
	    this.estadosRepartidos = this.abajo;
	    double nuevo_y = this.y + this.velocidad;
	    if (nuevo_y < 580 && !ciudad.hayColisiones(this.x, nuevo_y, this.ancho, this.alto)) {
	        this.y = nuevo_y;
	    }
	}

	public void moverIzquierda(Ciudad ciudad) {
	    this.ultimaDireccion = 2;
	    this.estadosRepartidos = this.izquierda;
	    double nuevo_x = this.x - this.velocidad;
	    if (nuevo_x > 16 &&!ciudad.hayColisiones(nuevo_x, this.y, this.ancho, this.alto)) {
	        this.x = nuevo_x;
	    }
	}

	public void moverDerecha(Ciudad ciudad) {
	    this.ultimaDireccion = 3;
	    this.estadosRepartidos = this.derecha;
	    double nuevo_x = this.x + this.velocidad;
	    if (nuevo_x < 784  && !ciudad.hayColisiones(nuevo_x, this.y, this.ancho, this.alto)) {
	        this.x = nuevo_x;
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
