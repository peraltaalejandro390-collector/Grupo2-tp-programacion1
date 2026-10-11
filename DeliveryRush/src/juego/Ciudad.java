package juego;


import java.util.Random;
import entorno.Entorno;


public class Ciudad {
    private static final int FILAS_CUADRAS = 4;
    private static final int COLUMNAS_CUADRAS = 4;
    
    private static final double ANCHO_CALLE = 32.0;
    private static final double ALTO_CALLE = 32.0;
    
    private static final double ANCHO_CUADRA = 160.0;
    private static final double ALTO_CUADRA = 98.0;

    // dimensiones exactas de cada propiedad individual (casa o negocio)
    private static final double ANCHO_ELEMENTO = ANCHO_CUADRA / 2.0; // 80.0
    private static final double ALTO_ELEMENTO = ALTO_CUADRA / 2.0;  // 49.0
    
    private static final double Y_INICIO_MAPA = 32.0; 

    private Casa[] casas;
    private Negocio[] negocios;
   
	private Random random;
	
	
    public Ciudad() {
    	this.random = new Random();
        this.casas = new Casa[48];
        this.negocios = new Negocio[16];
       
        generarCiudad();
    }

    private void generarCiudad() {
    	String[] nombres = {"Pizzeria", "Hamburguesas", "Heladeria", "Rotiseria", "kiosco", "empanadas", "tortas"};

    	int c = 0;
    	int n = 0;
    	int contadorPosicion = 1;

    	// divide el mapa en cuadras grandes, y luego divide cada cuadra en una minicuadrícula de 2x2 (4 espacios en total)
    	for (int fila = 0; fila < FILAS_CUADRAS; fila++) {
    	    for (int col = 0; col < COLUMNAS_CUADRAS; col++) {
    	        
    	        double inicioX = ANCHO_CALLE + (col * (ANCHO_CUADRA + ANCHO_CALLE));
    	        double inicioY = Y_INICIO_MAPA + (fila * (ALTO_CUADRA + ALTO_CALLE));
    	        
    	        int subNegocio = random.nextInt(4); // genera un número del 0 al 3 
    	        int subCasillero = 0;

    	        for (int subFila = 0; subFila < 2; subFila++) {
    	            for (int subCol = 0; subCol < 2; subCol++) {
    	                double posX = inicioX + (subCol * ANCHO_ELEMENTO) + (ANCHO_ELEMENTO / 2.0);
    	                double posY = inicioY + (subFila * ALTO_ELEMENTO) + (ALTO_ELEMENTO / 2.0);

    	                if (subCasillero == subNegocio) {
    	                    // Elegimos el nombre comercial que le toca de la lista (va rotando de 0 a 6)
    	                    String nombreComercial = nombres[n % 7];

    	                    // Creamos el Negocio con su tamaño y posición en la lista actual 'n'
    	                    this.negocios[n] = new Negocio(posX, posY, ANCHO_ELEMENTO, ALTO_ELEMENTO, contadorPosicion, nombreComercial);

    	                    // Sumamos 1 para que el próximo negocio se guarde en el siguiente casillero
    	                    n = n + 1;

    	                } else {
    	                    int tipo;
    	                    int numeroAleatorio = random.nextInt(2); // Genera 0 o 1

    	                    if (numeroAleatorio == 0) {
    	                        tipo = 1;
    	                    } else {
    	                        tipo = 2;
    	                    }
    	                    
    	                    double altoFinal;
    	                    double posYFinal;

    	                    if (tipo == 1) {
    	                        altoFinal = ALTO_ELEMENTO * 0.8; // Más baja (80% del alto)
    	                        posYFinal = posY - 5;            // La subimos un poquito para que no flote
    	                    } else {
    	                        altoFinal = ALTO_ELEMENTO;       // Alto normal
    	                        posYFinal = posY;                // Posición normal
    	                    }

    	                    // 3. Creamos la casa con los datos finales y avanzamos el contador
    	                    this.casas[c] = new Casa(posX, posYFinal, ANCHO_ELEMENTO, altoFinal, contadorPosicion, tipo);
    	                    c = c + 1;
    	                    } 

    	                // Esto tiene que ejecutarse SIEMPRE (tanto para casas como para negocios)
    	                contadorPosicion++;
    	                subCasillero++;
    	            }   
    	        }
    	        }
    	}
    	}
    public void dibujarse(Entorno entorno ) {
    	
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
    public boolean hayColision(double x, double y, double ancho, double alto) {
    	 // Colisión con Casas (Revisa los bordes del personaje vs bordes de las casas)
        for (int i = 0; i < casas.length; i++) {
            Casa c = casas[i];
            if (c != null) {
                if (Math.abs(x - c.getX()) < (ancho + c.getAncho()) / 2.0 && //• límite exacto donde ambos cuerpos se empiezan a tocar.
                    Math.abs(y - c.getY()) < (alto + c.getAlto()) / 2.0) {
                    return true;
                }
            }
        }
        
     // Colisión con Negocios (Revisa los bordes del personaje vs bordes de los negocios)
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


    

    public Casa[] getCasas() { return casas; }
    public Negocio[] getNegocios() { return negocios; }


	

}

	// - obtenerCasaAleatoria()
	// - obtenerNegocioAleatorio()
	// - getters y setters
}
