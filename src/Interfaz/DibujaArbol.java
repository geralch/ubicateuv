package Interfaz;
import Logic.Nodo;
import java.awt.*;
import java.util.*;
 
/*Clase que se encarga de analizar el arbol de nodos y dibujar el arbol generado*/
public class DibujaArbol 
{
    private int separacionX = 45;
    private int separacionY = 100;
    private int margen = 50;
    private int anchoMaximo = 0;
    private int profundidadMaxima = 0;
    private int nodosProfundidad[];
    int altoCanvas = 0;
    int anchoCanvas = 0;
    private ArrayList<Nodo> arbol;
    private ArrayList<Point>[] aristas;
    private ArrayList<Point>  posicionNodos;
    private Nodo ultimoNodo;
    private int[]  recorrido;
    
    /*Constructor de la clase, le entra el arbol generado y el ultimo nodo de la ruta*/
    public DibujaArbol(ArrayList<Nodo> arbol, Nodo ultimoNodo){
        this.arbol = arbol;  
        this.ultimoNodo =  ultimoNodo;
        ordenarArbol();
        calcularAnchoMaximo();
        calcularPosicionNodos();
        calcularAristas();
        calcularRecorrido();
    }
    
    /*Metodo que calcula el recorrido que se tomara segun la ruta del ultimo nodo*/
    private void calcularRecorrido(){
        int tamaño = ultimoNodo.getRuta().size();
        recorrido = new int[tamaño];
        Nodo nodo = ultimoNodo;
        int indice = nodo.getIndice();  
                
        for(int i=0; i<tamaño;i++){
           recorrido[tamaño-i-1] = posicionIndice(indice);
           indice = nodo.getIndicePadre();
           nodo = arbol.get(posicionIndice(indice));
        }
    }
    
    /*Metodo que calcula el ancho maximo del arbol segun el numero de nodos expandidos y la profundidad*/
    private void calcularAnchoMaximo(){
        
        anchoMaximo = 0;
        calcularProfundidad();
        nodosProfundidad = new int[profundidadMaxima];
        
        for(int i=0; i<profundidadMaxima;i++){
            int cantidad = 0;
            for(int j=0; j<arbol.size();j++){
                Nodo nodo = arbol.get(j);  
                if(nodo.getProfundidad() == i){
                    cantidad++;
                }
            }
            if(cantidad>anchoMaximo) anchoMaximo = cantidad;
            nodosProfundidad[i] = cantidad; 
        }
    }
    
    /*Metodo que calcula la profundidad del arbol*/
    private void calcularProfundidad(){
        profundidadMaxima = 0;
        for(int i=0; i<arbol.size();i++){
            Nodo nodo = arbol.get(i);  
            if(nodo.getProfundidad() > profundidadMaxima){
                profundidadMaxima = nodo.getProfundidad();
            }
        }
        profundidadMaxima++;
    }
     
    /*Metodo que retorna un arreglo de puntos con la posicion de los nodos*/
    private  ArrayList<Point>  calcularPosicionNodos(){
        posicionNodos = new ArrayList();
                
        int cantidadIndexada[] = new int[profundidadMaxima];
        
        anchoCanvas = anchoMaximo*separacionX;
        altoCanvas = profundidadMaxima*separacionY+(margen);
        
        for(int i=0; i<arbol.size();i++){
            Nodo nodo = arbol.get(i);  
            int profundidad = nodo.getProfundidad();
            
            int x = (cantidadIndexada[profundidad]+1)*(anchoCanvas/(nodosProfundidad[profundidad]+1));
            int y = profundidad*(altoCanvas/(profundidadMaxima+1));
            
            Point punto = new Point(x,y);  
            posicionNodos.add(punto);
            cantidadIndexada[profundidad]++;
        }
        
        return posicionNodos;      
    }
    
    /*Metodo que ordena el arbol para que expanda en orden segun sus padres*/
    private void ordenarArbol(){
        ArrayList<Nodo> nuevoArbol = new ArrayList();
        
        nuevoArbol.add(arbol.get(0));
        
        for(int i=0; i<arbol.size();i++){
            Nodo nodo = nuevoArbol.get(i);
            int indice = nodo.getIndice();
            ArrayList<Nodo> hijos = obtenerNodosHijos(indice);
            for(int j=0; j<hijos.size();j++){
                Nodo hijo = hijos.get(j);
                nuevoArbol.add(hijo);
            }
            
        }
        arbol =  nuevoArbol;
    }
    
    /*Metodo que retorna un arreglo de nodos con los nodos hijos de un nodo padres segun su indice*/
    private ArrayList<Nodo> obtenerNodosHijos(int indice){
        ArrayList<Nodo> hijos = new ArrayList();
        for(int i=0; i<arbol.size();i++){
            Nodo nodo = arbol.get(i);
            if(nodo.getIndicePadre() == indice){
                hijos.add(nodo);
            }
        }
        return hijos;
    }
          
    /*Metoso que retorna un arreglo de arreglo de puntos con las aristas que conectan los nodos*/
    private  ArrayList<Point>[] calcularAristas(){
                
        aristas = new ArrayList[2];
        aristas[0] = new ArrayList();
        aristas[1] = new ArrayList();
        
         for(int i=1; i<arbol.size();i++){
            Nodo nodo = arbol.get(i);             
            int nodoPadreIndice = nodo.getIndicePadre();
           
            Point puntoPadre = posicionNodos.get(posicionIndice(nodoPadreIndice));
            Point puntoNodo = posicionNodos.get(posicionIndice(nodo.getIndice()));
                     
            aristas[0].add(puntoNodo);
            aristas[1].add(puntoPadre);
        }
         
        return aristas;      
    }
    
    /*Metodo que retorna la posicion de un nodo segun su indice*/
    private  int posicionIndice(int indice){
        int posicion = 0;
        for(int i=0; i<arbol.size();i++){
            Nodo nodo = arbol.get(i);
            if(nodo.getIndice() == indice){
                posicion = i;
                break;
            } 
        }
        return posicion;      
    }

    /*Metodo que retorna las aristas calculadas*/
    public ArrayList<Point>[] getAristas() {
        return aristas;
    }

    /*Metodo que retorna la posicion de los nodos*/
    public ArrayList<Point> getPosicionNodos() {
        return posicionNodos;
    }
    
    /*Metodo que retorna el arbol ordenado*/
    public ArrayList<Nodo> getArbolOrdenado() {
        return arbol;
    }
    
    /*Metodo que retorna un arreglo con los indices del recorrido segun la ruta elegida*/
    public int[] getRecorrido() {
        return recorrido;
    }
}


