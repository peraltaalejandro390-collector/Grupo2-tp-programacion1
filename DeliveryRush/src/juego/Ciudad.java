package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import entorno.Entorno;

public class Ciudad {
    private static int FILAS_CUADRAS = 4;
    private static int COLUMNAS_CUADRAS = 4;
    // Coordenadas centrales de las calles
    public static double[] CALLES_X = {25.0, 208.0, 400.0, 592.0, 775.0};
    public static double[] CALLES_Y = {64.0, 194.0, 324.0, 454.0, 584.0};
 
    // Ancho y alto de las calles 
    public static double ANCHO_CALLE = 46.0;
    public static double ALTO_CALLE = 42.0;

    // Dimensiones de cada casa o negocio
    private static double ANCHO_PROPIEDAD = 50.0;
    private static double ALTO_PROPIEDAD = 34.0;

    private Casa[] casas;
    private Negocio[] negocios;
    private Random random;
    private Image fondoCuadra;
    private Image imagenCalle;

    public Ciudad() {
        this.random = new Random();
        this.casas = new Casa[48];
        this.negocios = new Negocio[16];
        this.fondoCuadra = entorno.Herramientas.cargarImagen("juego/fondo_casas.png");
        this.imagenCalle = entorno.Herramientas.cargarImagen("juego/calle.png");
        generarCiudad();
    }

    private void generarCiudad() {
        int c = 0;
        int n = 0;
        int contadorPosicion = 1;

        // Separacion para agrupar las 4 casas/negocio 
        double dx = 27.0;
        double dy = 18.0;

        for (int fila = 0; fila < FILAS_CUADRAS; fila++) {
            for (int col = 0; col < COLUMNAS_CUADRAS; col++) {
                // Centro de la manzana entre las calles 
                double centroCuadraX = (CALLES_X[col] + CALLES_X[col + 1]) / 2.0;
                double centroCuadraY = (CALLES_Y[fila] + CALLES_Y[fila + 1]) / 2.0;

                int subNegocio = random.nextInt(4); // 0 a 3
                int subCasillero = 0;

  for (int subFila = 0; subFila < 2; subFila++) {
                    for (int subCol = 0; subCol < 2; subCol++) {
                        // Posicion concentrada en el centro de la manzana
                    	double posX;
                    	double posY;

                     	if (subCol == 0) {
                    	        posX = centroCuadraX - dx;
                    	} else {
                    	   	    posX = centroCuadraX + dx;
                    	}

                    	if (subFila == 0) {
                    	    posY = centroCuadraY - dy;
                    	} else {
                    	     posY = centroCuadraY + dy;
                    	}
                        if (subCasillero == subNegocio) {
                            this.negocios[n] = new Negocio(posX, posY, ANCHO_PROPIEDAD, ALTO_PROPIEDAD, contadorPosicion);
                            n++;
                        } else {
                          int tipo = random.nextInt(2) + 1;//solo dos opciones: 0 o 1

                            this.casas[c] = new Casa(posX, posY, ANCHO_PROPIEDAD, ALTO_PROPIEDAD, contadorPosicion, tipo);
                            c++;
                        }

                        contadorPosicion++;
                        subCasillero++;
                    }
                }
            }
        }
    }

    public void dibujarse(Entorno entorno) {
        //pasto de fondo
        if (this.fondoCuadra != null) {
            double escalaCesped = 820.0 / (double) this.fondoCuadra.getWidth(null);
            entorno.dibujarImagen(this.fondoCuadra, 400, 320, 0, escalaCesped);
        }

        //Calles
        if (this.imagenCalle != null) {
            entorno.dibujarImagen(this.imagenCalle, 400, 300, 0, 1.0);
        } else {
            entorno.dibujarRectangulo(400, 320, 800, 560, 0, new Color(55, 55, 55));
        }

        //Casas y negocios agrupados sobre el pasto de cada manzana
        for (int i = 0; i < casas.length; i++) {
            if (casas[i] != null) {
                casas[i].dibujarse(entorno);
            }
        }
        for (int i = 0; i < negocios.length; i++) {
            if (negocios[i] != null) {
                negocios[i].dibujarse(entorno);
            }
        }
    }
    public boolean hayColisiones(double x, double y, double ancho, double alto) {
        //Colision con Casas
        for (int i = 0; i < casas.length; i++) {
            Casa c = casas[i];
            if (c != null) {
                if (Math.abs(x - c.getX()) < (ancho + c.getAncho()) / 2.0 &&
                    Math.abs(y - c.getY()) < (alto + c.getAlto()) / 2.0) {
                    return true;
                }
            }
        }
        
        //colision con Negocios
        for (int i = 0; i < negocios.length; i++) {
            Negocio n = negocios[i];
            if (n != null) {
                if (Math.abs(x - n.getX()) < (ancho + n.getAncho()) / 2.0 &&
                    Math.abs(y - n.getY()) < (alto + n.getAlto()) / 2.0) {
                    return true; 
                }
            }
        }
        return false;
    }

    public Casa obtenerCasaAleatoria() {
        if (this.casas == null || this.casas.length == 0) {
            return null;
        }
        int posicion = this.random.nextInt(this.casas.length);
        return this.casas[posicion];
    }

    public Negocio obtenerNegocioAleatorio() {
        if (this.negocios == null || this.negocios.length == 0) {
            return null;
        }
        int posicion = this.random.nextInt(this.negocios.length);
        return this.negocios[posicion];
    }

    public Casa[] getCasas() { return casas; }
    public Negocio[] getNegocios() { return negocios; }
}

