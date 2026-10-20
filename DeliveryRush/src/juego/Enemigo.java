package juego;

import java.awt.Image;
import entorno.Entorno;

public class Enemigo {
    private double x;
    private double y;
    private double velocidad;
    private boolean esHorizontal;
    private int sentido; // 1: adelante, -1: atras
    private double escala;

    // Control de bombas
    private boolean puedeDejarBombas;
    private int ticksParaBomba; // Cuenta regresiva para soltar la siguiente

    // Imagenes
    private Image arriba = entorno.Herramientas.cargarImagen("juego/enemigo_arriba.png");
    private Image abajo = entorno.Herramientas.cargarImagen("juego/enemigo_abajo.png");
    private Image derecha = entorno.Herramientas.cargarImagen("juego/enemigo_derecha.png");
    private Image izquierda = entorno.Herramientas.cargarImagen("juego/enemigo_izquierda.png");

    private Image imagenActual;

    
    // Constructor aleatorio: se posiciona solo en una calle al azar y elige su direccion
    public Enemigo() {
        double[] callesY = {64, 194, 324, 454, 584}; // Calles horizontales
        double[] callesX = {25, 208, 400, 592, 775}; // Calles verticales

        this.esHorizontal = Math.random() < 0.5;
        this.puedeDejarBombas = Math.random() < 0.5;
        this.ticksParaBomba = 250;
        this.velocidad = 2.0;
        this.escala = 0.065;

        if (this.esHorizontal) {
            int fila = (int)(Math.random() * callesY.length);
            this.y = callesY[fila];
            
            // Aparece en una de las dos puntas de la calle
            if (Math.random() < 0.5) {
                this.x = 30;
                this.sentido = 1;
                this.imagenActual = this.derecha;
            } else {
                this.x = 770;
                this.sentido = -1;
                this.imagenActual = this.izquierda;
            }
        } else {
            int col = (int)(Math.random() * callesX.length);
            this.x = callesX[col];

            // Aparece en una de las dos puntas de la calle
            if (Math.random() < 0.5) {
                this.y = 75;
                this.sentido = 1;
                this.imagenActual = this.abajo;
            } else {
                this.y = 570;
                this.sentido = -1;
                this.imagenActual = this.arriba;
            }
        }
    }

    public void moverse() {
        if (this.esHorizontal) {
            this.x += this.velocidad * this.sentido;
            if (this.x <= 25) {
                this.sentido = 1;
                this.imagenActual = this.derecha;
            } else if (this.x >= 775) {
                this.sentido = -1;
                this.imagenActual = this.izquierda;
            }
        } else {
            this.y += this.velocidad * this.sentido;
            if (this.y <= 65) {
                this.sentido = 1;
                this.imagenActual = this.abajo;
            } else if (this.y >= 575) {
                this.sentido = -1;
                this.imagenActual = this.arriba;
            }
        }
    }

    // Si tiene la habilidad y paso el tiempo, devuelve una bomba nueva en sus coordenadas
    public Bomba intentarSoltarBomba() {
        if (!this.puedeDejarBombas) {
            return null;
        }

        this.ticksParaBomba--;
        if (this.ticksParaBomba <= 0) {
            this.ticksParaBomba = 300; // Reinicia el tiempo para la proxima bomba (6seg)
            return new Bomba(this.x, this.y);
        }
        return null;
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(this.imagenActual, this.x, this.y, 0, this.escala);
    }

    public boolean colisionaCon(double otroX, double otroY) {
        double difX = Math.abs(this.x - otroX);
        double difY = Math.abs(this.y - otroY);
        return difX < 25 && difY < 25;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
