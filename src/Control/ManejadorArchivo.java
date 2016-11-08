package Control;
import java.io.*;
import java.util.*;

/*Clase que gestiona la lectura del archivo con los datos del ambiente*/
public class ManejadorArchivo {
    
    /*Metodo que retorna un arreglo de arreglos de enteros con los datos del ambiente*/
    public ArrayList<ArrayList<Integer>> leerArchivo(String ruta){
        
        ArrayList<ArrayList<Integer>> ambiente = new ArrayList(); 
        
        try{
            FileInputStream fstream = new FileInputStream(ruta);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));            
            
            int  tamano = Integer.parseInt(br.readLine());            
                
            for(int i=0; i<tamano; i++){
                StringTokenizer token = new StringTokenizer(br.readLine()); 
                ArrayList<Integer> fila = new ArrayList();
                for(int j=0; j<tamano; j++){
                    int numero = Integer.parseInt(token.nextToken());
                    fila.add(numero);
                }
                ambiente.add(fila);
            }    
        }        
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ambiente;
    }
       
    /*Metodo que se encarga de escribir datos en un archivo*/
    public void escribirArchivo(ArrayList<ArrayList<Integer>> ternas, String ruta)
    {
        try{
            FileWriter fichero = new FileWriter(ruta);
            PrintWriter pw = new PrintWriter(fichero);

            pw.println(ternas.size());

            for (int i = 0; i < ternas.size(); i++)
            {
                    pw.println(ternas.get(i));
            }
            fichero.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
