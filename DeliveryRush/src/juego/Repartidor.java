package juego;
import java.awt.Image;
import entorno.Entorno;
public class Repartidor {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private int vidas;
	private int ultimaDireccion;// 0: arriba, 1: abajo, 2: izquierda, 3: derecha
	private int ticksInvulnerable = 0;
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
		this.ancho = 20;
		this.alto = 24;
		this.ticksInvulnerable = 150;
	}
	public void moverArriba(Ciudad ciudad) {
	    this.ultimaDireccion = 0;
	    this.estadosRepartidos = this.arriba;
	    double nuevoY = this.y - this.velocidad;
	    if (nuevoY - this.alto / 2.0 > 40) {
	        if (ciudad == null || !ciudad.hayColisiones(this.x, nuevoY, this.ancho, this.alto)) {
	            this.y = nuevoY;
	        }
	    }
	}

	public void moverAbajo(Ciudad ciudad) {
	    this.ultimaDireccion = 1;
	    this.estadosRepartidos = this.abajo;
	    double nuevoY = this.y + this.velocidad;
	    if (nuevoY + this.alto / 2.0 < 595) {
	        if (ciudad == null || !ciudad.hayColisiones(this.x, nuevoY, this.ancho, this.alto)) {
	            this.y = nuevoY;
	        }
	    }
	}

	public void moverIzquierda(Ciudad ciudad) {
	    this.ultimaDireccion = 2;
	    this.estadosRepartidos = this.izquierda;
	    double nuevoX = this.x - this.velocidad;
	    if (nuevoX - this.ancho / 2.0 > 5) {
	        if (ciudad == null || !ciudad.hayColisiones(nuevoX, this.y, this.ancho, this.alto)) {
	            this.x = nuevoX;
	        }
	    }
	}

	public void moverDerecha(Ciudad ciudad) {
	    this.ultimaDireccion = 3;
	    this.estadosRepartidos = this.derecha;
	    double nuevoX = this.x + this.velocidad;
	    if (nuevoX + this.ancho / 2.0 < 795) {
	        if (ciudad == null || !ciudad.hayColisiones(nuevoX, this.y, this.ancho, this.alto)) {
	            this.x = nuevoX;
	        }
	    }
	}

	public void moverArriba() {
	    moverArriba(null);
	}

	public void moverAbajo() {
	    moverAbajo(null);
	}

	public void moverIzquierda() {
	    moverIzquierda(null);
	}

	public void moverDerecha() {
	    moverDerecha(null);
	}
	
	public void perderVidas() {
	    if (this.ticksInvulnerable <= 0) {
	        this.vidas--;
	        this.ticksInvulnerable = 100; //inmunidad
	    }
	}

	public boolean esInvulnerable() {
	    return this.ticksInvulnerable > 0;
	}

	public void actualizar() {
	    if (this.ticksInvulnerable > 0) {
	        this.ticksInvulnerable--;
	    }
	}
	
	public void dibujarse(Entorno entorno) {
	    if (this.ticksInvulnerable > 0 && (this.ticksInvulnerable / 6) % 2 == 0) {
	        return; 
	    }
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
	
    
    public int getVidas() {
        return this.vidas;
    }

    public double getAncho() {
        return this.ancho;
    }

    public double getAlto() {
        return this.alto;
    }

}
