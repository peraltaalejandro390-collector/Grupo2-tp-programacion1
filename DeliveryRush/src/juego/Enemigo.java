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
    private final Image arriba = entorno.Herramientas.cargarImagen("juego/enemigo_arriba.png");
    private final Image abajo = entorno.Herramientas.cargarImagen("juego/enemigo_abajo.png");
    private final Image derecha = entorno.Herramientas.cargarImagen("juego/enemigo_derecha.png");
    private final Image izquierda = entorno.Herramientas.cargarImagen("juego/enemigo_izquierda.png");

    private Image imagenActual;

    
    // Constructor aleatorio: se posiciona solo en una calle al azar y elige su direccion
   public Enemigo() {
        this.esHorizontal = Math.random() < 0.5; //50% de aparicion
        this.puedeDejarBombas = Math.random() < 0.5; //50% de aparicion
        this.ticksParaBomba = 100;
        this.velocidad = 2.0;
        this.escala = 0.065;

        if (this.esHorizontal) {
            int fila = (int)(Math.random() * Ciudad.CALLES_Y.length);
            this.y = Ciudad.CALLES_Y[fila];
            
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
            int col = (int)(Math.random() * Ciudad.CALLES_X.length);
            this.x = Ciudad.CALLES_X[col];

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
