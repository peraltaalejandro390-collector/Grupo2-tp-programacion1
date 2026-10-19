package juego;

public class Bomba {
	private double x;
	private double y;
	private int ticksParaExplotar;
	private int ticksDuracionExplosion;
	private boolean exploto;
	private double anchoArea;
	private double altoArea;

	// Constructor
	public Bomba(double x, double y) {
		this.x = x;
		this.y = y;
	}
}
