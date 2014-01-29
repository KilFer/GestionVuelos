import java.io.*;
import java.util.*;

/**
 * Write a description of class Informe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Informe
{
    public static void generaInforme()
    {
        
        Compania.ordenaCompanias();
        for(int i=0;i<Compania.Getncom();i++){
            Compania.GetInforme(i).Ordenaaeropuertos();
        }
        File a = new File(Programa.getInforme());
        try {
            // COMPRUEBA SI EL ARCHIVO EXISTE YA O NO. SI LO EXISTE; LO BORRA ANTES DE CREARLO.
            if (a.exists()){
                a.delete();
                a.createNewFile();
            }
                else
                a.createNewFile();
            PrintWriter f = new PrintWriter(a);
            //Añade linea de información sobre el informe
            if(Compania.Getncom() > 0){
                if(!Programa.tabulado()){
                    if(Compania.Getncom()>1){
                        f.println("Entre el " + Programa.Inicio() + " y el " + Programa.Final() + " han operado " + Compania.Getncom() + " compañía(s)."); 
                        f.println("Listadas en orden de aprovechamiento total de sus aeropuertos:");
                    }
                    else
                        f.println("Entre el " + Programa.Inicio() + " y el " + Programa.Final() + " han operado solo una compañía."); 
                    f.println("===========================================");   
                }
                // Añade información de cada compañía, en el orden ya declarado.
                if(Programa.tabulado())
                    f.println("COMPAÑIA \t AEROPUERTO \t % APROV. \t NUM PASAJEROS \t MAX PASAJEROS \t NUM VUELOS");
                for(int i=0;i<Compania.Getncom();i++){
                    f.println(Compania.GetInforme(i).enPantalla());
                    //Añade información de cada aeropuerto, ya ordenados.
                    for(int j=0;j<Compania.GetInforme(i).numAeropuertos();j++){
                        f.println(Compania.GetInforme(i).GetOpera(j).enPantalla());
                    }
                    if(!Programa.tabulado())
                        f.println(" -=-=-=- ");
                }
            } else {f.println("Ninguna compañía ha operado en ese rango de fechas.");}
                
            f.close();
            System.out.println("Informe creado");
            } catch (IOException ioe) {
                ioe.getMessage(); System.out.println("ERROR - Informe no creado");
            }
    }
}
