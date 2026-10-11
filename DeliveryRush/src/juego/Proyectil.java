package juego;

import java.awt.Image;
import entorno.Entorno;
public class Proyectil {
    private double x;
    private double y;
    private double velocidad;
    private int direccion; // 0: arriba, 1: abajo, 2: izquierda, 3: derecha
    private double escala;
    
    // Imágenes
    private final Image arriba = entorno.Herramientas.cargarImagen("juego/proyectil_arriba.png");
    private final Image abajo = entorno.Herramientas.cargarImagen("juego/proyectil_abajo.png");
    private final Image derecha = entorno.Herramientas.cargarImagen("juego/proyectil_derecha.png");
    private final Image izquierda = entorno.Herramientas.cargarImagen("juego/proyectil_izquierda.png");
    
    private Image imagenActual;

	// Constructor
    public Proyectil(double x, double y, int direccion) {
        this.x = x;
        this.y = y;
        this.direccion = direccion;
        this.velocidad = 5.5; // Más rápido que el repartidor
        this.escala = 0.08;   // Tamaño para que mida unos ~15 px

        // Seleccionamos la imagen fija según la dirección de disparo:
        if (this.direccion == 0) {
            this.imagenActual = this.arriba;
        } else if (this.direccion == 1) {
            this.imagenActual = this.abajo;
        } else if (this.direccion == 2) {
            this.imagenActual = this.izquierda;
        } else {
            this.imagenActual = this.derecha;
        }
    }
    public void avanzar() {
        if (this.direccion == 0) {
            this.y -= this.velocidad; // Arriba
        } else if (this.direccion == 1) {
            this.y += this.velocidad; // Abajo
        } else if (this.direccion == 2) {
            this.x -= this.velocidad; // Izquierda
        } else if (this.direccion == 3) {
            this.x += this.velocidad; // Derecha
        }
    }
    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(this.imagenActual, this.x, this.y, 0, this.escala);
    }
    public boolean fueraDeLimites(Entorno entorno) {
        return (this.x < 0 || this.x > entorno.ancho() || this.y < 0 || this.y > entorno.alto());
    }
}
