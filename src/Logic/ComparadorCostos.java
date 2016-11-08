package Logic;
import java.util.*;

/*Clase de tipo comparador de nodos que se usa como criterio de comparacion para
sacar los elementos de la cola de prioridad*/
public class ComparadorCostos implements Comparator<Nodo>{

    /*Metodo que retorna si el costo de un nodo es menor a otro*/
    @Override
    public int compare(Nodo x, Nodo y) {
        if(x.getCosto()<y.getCosto()){
            return -1;
        }
        
        if(x.getCosto()>y.getCosto()){
            return 1;
        }
        if(x.getCosto()==y.getCosto()){
            if(x.getIndice()<y.getIndice())
                return -1;
            else 
                return 1;
        }
        return 0;
    }
}
