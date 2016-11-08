package Logic;
import java.awt.*;
import java.util.*;

/*Clase que representa al objeto nodo*/
public class Nodo {
    private double costo;
    private double acumuladoCosto;
    private Point coordenada;
    private int energiaRobot;
    private ArrayList<PuntoRuta> ruta;
    private int indice;
    private int indicePadre;
    private int profundidad;
   
    /*Constructor de la clase*/
    public Nodo(double costo, double acumuladoCosto, Point coordenada, int energiaRobot, ArrayList ruta, int indice, int indicePadre, int profundidad){
        this.costo = costo;
        this.acumuladoCosto = acumuladoCosto;
        this.coordenada = coordenada;
        this.energiaRobot = energiaRobot;
        this.ruta = ruta;
        this.indice = indice;
        this.indicePadre = indicePadre;
        this.profundidad = profundidad;
    }

    /*Metodo que retorna el valor del costo del nodo*/
    public double getCosto() {
        return costo;
    }

    /*Metodo que se encarga de establecer el valor del costo del nodo*/
    public void setCosto(double costo) {
        this.costo = costo;
    }
    
    /*Metodo que retorna el valor del acumulado del costo del nodo*/
    public double getAcumuladoCosto() {
        return acumuladoCosto;
    }

    /*Metodo que se encarga de establecer el valor del acumulado del costo del nodo*/
    public void setAcumuladoCosto(double acumuladoCosto) {
        this.acumuladoCosto = acumuladoCosto;
    }

    /*Metodo que retorna la coordenada del nodo*/
    public Point getCoordenada() {
        return coordenada;
    }

    /*Metodo que se encarga de establecer la coordenada del nodo*/
    public void setCoordenada(Point coordenada) {
        this.coordenada = coordenada;
    }

    /*Metodo que retorna el valor la energia del robot en el nodo*/
    public int getEnergiaRobot() {
        return energiaRobot;
    }

    /*Metodo que se encarga de establecer el valor de la energia del robot en el nodo*/
    public void setEnergiaRobot(int energiaRobot) {
        this.energiaRobot = energiaRobot;
    }

    /*Metodo que retorna la ruta tomada para llegar al nodo*/
    public ArrayList<PuntoRuta> getRuta() {
        return ruta;
    }

    /*Metodo que se encarga de establecer la ruta tomada para llegar al nodo*/
    public void setRuta(ArrayList<PuntoRuta> ruta) {
        this.ruta = ruta;
    }
    
    /*Metodo que retorna el valor del indice del nodo*/
     public int getIndice() {
        return indice;
    }

     /*Metodo que se encarga de establecer el valor del indice del nodo*/
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /*Metodo que retorna el valor del indice del padre del nodo*/
    public int getIndicePadre() {
        return indicePadre;
    }

    /*Metodo que se encarga de establecer el valor del indice del padre del nodo*/
    public void setIndicePadre(int indicePadre) {
        this.indicePadre = indicePadre;
    }

    /*Metodo que retorna el valor de la profundidad del nodo*/
    public int getProfundidad() {
        return profundidad;
    }

    /*Metodo que se encarga de establecer el valor de la profundidad del nodo*/
    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }
}
