import java.util.*;
import java.io.*;
import java.math.*;
/**
 * Write a description of class Aeropuerto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Aeropuerto
{
    private String codigo;
    private int vuelos;
    private int numpasajeros;
    private int maxpasajeros;
    private double porcentaje;

    /**
     * Constructor for objects of class Aeropuerto
     */
    public Aeropuerto(String cod)
    {
        codigo = cod;
        vuelos = 0;
        numpasajeros = 0;
        maxpasajeros = 0;
        porcentaje = 0.0;
    }
    
    public String getCodigo(){
        return codigo;
    }
    
    public double getAprovechamiento(){
        return porcentaje;
    }
    
    public int getNumPasajeros(){
        return numpasajeros;
    }
    
    public int getMaxPasajeros(){
        return maxpasajeros;
    }
    
    /**
     * Calcular el porcentaje de aprovechamiento del aeropuerto.
     */
    public void aprovechamiento()
    {
        porcentaje = (double) numpasajeros / (double) maxpasajeros * 100;
    }
    
    public void nuevoVuelo(int gente, int capacidad){
        numpasajeros += gente;
        maxpasajeros += capacidad;
        vuelos++;
    }
    
    public String enPantalla(){
        aprovechamiento();
        String nombre = null;
        boolean encontrado = false;
        try{
            Scanner f = new Scanner(new File(Programa.getAeropuertos()));
            while(f.hasNextLine() && !encontrado){
                String linea = f.nextLine();
                String siglas = linea.substring(0,3);
                if(siglas.equals(codigo)){
                    nombre = linea.substring(4);
                    encontrado = true;
                }
            }
        } catch (IOException ioe) {};
        
        // REDONDEO. Para que el porcentaje salga solo con 2 decimales.
        String val = porcentaje+"";
        BigDecimal big = new BigDecimal(val);
        big = big.setScale(2, RoundingMode.HALF_UP); //AJUSTAR AQUI el número de decimales.
        
        // FIN del redondeo.
        
        if(Programa.tabulado())
            //Tabulado quita los datos para usarlos como cabecera en casilla propia.
            nombre = "\t" + nombre + " (" + codigo + ") \t" + big + "% \t" + numpasajeros + " \t" + maxpasajeros + " \t" + vuelos;
        else
            nombre = " - " + nombre + " (" + codigo + "): " + big + "% de utilidad en " + vuelos + " vuelos. " + numpasajeros + " pasajeros de " + maxpasajeros + " posibles";
        return nombre;
    }
    
}
