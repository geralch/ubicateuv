package Control;
import Logic.*;
import java.util.ArrayList;

/* Clase que gestiona el paso de datos entre la Logica y la Interfaz*/
public class ControladorBusquedas {
    
    private ArrayList<ArrayList<Integer>> ambiente;
    private ArrayList<Nodo> arbol;
    private Nodo ultimoNodo;
    private int profundidadMaxima;
    private int nodosExpandidos;
    private int nodosCreados;
    private double costoTotal;
    private double factorRamific;
    private int tipoHeuristica;
    private boolean boolParar = true;
    
    /* Constructor de la clase*/
    public ControladorBusquedas(ArrayList<ArrayList<Integer>> ambiente, int tipoHeuristica, boolean boolParar){
          this.ambiente = ambiente;
          this.tipoHeuristica = tipoHeuristica;
          this.boolParar = boolParar;
    }
    
    /*Metodo que se encarga de utilizar la busqueda no informada*/
    public void busquedaNoInformada(){
        
        BusquedaNoInformada busquedaNoInformada = new BusquedaNoInformada(ambiente,boolParar);
        ultimoNodo = busquedaNoInformada.busqueda();
        nodosCreados = busquedaNoInformada.getNodosCreados();
        nodosExpandidos = busquedaNoInformada.getNodosExpandidos();
        costoTotal = ultimoNodo.getCosto();
        arbol = busquedaNoInformada.arbolExpandido();
        calcularProfundidad(arbol);
        calcularFactor();
    }
    
    /*Metodo que se encarga de utilizar la busqueda informada*/
    public void busquedaInformada(){
        BusquedaInformada busquedaInformada = new BusquedaInformada(ambiente,tipoHeuristica,boolParar);
        ultimoNodo = busquedaInformada.busqueda();
        nodosCreados = busquedaInformada.getNodosCreados();
        nodosExpandidos = busquedaInformada.getNodosExpandidos();
        costoTotal = ultimoNodo.getCosto();
        arbol = busquedaInformada.arbolExpandido();
        calcularProfundidad(arbol);
        calcularFactor();
    }
    
    /*Metodo que retorna el arbol de nodos expandido*/
    public ArrayList<Nodo> getArbol() {
        return arbol;
    }
    
    /*Metodo que retorna el ultimo nodo de la ruta hallada segun la busqueda */
    public Nodo getUltimoNodo() {
        return ultimoNodo;
    }

    /*Metodo que retorna los nodos expandindos del arbol segun la busqueda*/
    public int getNodosExpandidos() {
        return nodosExpandidos;
    }

    /*Metodo que retorna los nodos creados del arbol segun la busqueda*/
    public int getNodosCreados() {
        return nodosCreados;
    }

    /*Metodo que retorna el costo total de la busqueda*/
    public double getCostoTotal() {
        return costoTotal;
    }

    /*Metodo que retorna el factor de ramificacion del arbol segun la busqueda*/
    public double getFactorRamific() {
        return factorRamific;
    }
    
    /*Metodo que se encarga de calcular la profundida maxima del arbol*/
    private void calcularProfundidad(ArrayList<Nodo> arbol){
        profundidadMaxima = 0;
        for(int i=0; i<arbol.size();i++){
            Nodo nodo = arbol.get(i);  
            if(nodo.getProfundidad() > profundidadMaxima){
                profundidadMaxima = nodo.getProfundidad();
            }
        }
        profundidadMaxima++;
    }
    
    /*Metodo que se encarga de calcular el factor de ramificacion del arbol*/
    private void calcularFactor(){
        double nodosCre = (double)nodosCreados;
        double profundidad = (double)profundidadMaxima;
        factorRamific = Math.pow((nodosCre+1), (1/(profundidad+1)));
    }
}
