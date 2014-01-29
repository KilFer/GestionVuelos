import java.util.*;
import java.io.*;
/**
 * Write a description of class Programa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Programa{
    private static int ANOIN;
    private static int MESIN;
    private static int DIAIN;
    private static int ANOFIN;
    private static int MESFIN;
    private static int DIAFIN;
    private static boolean TABULADO = false;

    /* Nombre del archivo que contiene los vuelos */
    private static String VUELOS="VUELOS.TXT";
    /* Nombre del archivo que contiene los aeropuertos */
    private static String AEROPUERTOS="AEROPUERTOS.TXT";
    /* Nombre del archivo que contiene las companias */
    private static String COMPANIAS="COMPANIAS.TXT";
    /* Nombre del archivo en el que se generara el informe */
    private static String INFORME="INFORME.txt";

    public static String getVuelos(){
    	return VUELOS;
    }
    public static String getAeropuertos(){
    	return AEROPUERTOS;
    }
    public static String getCompanias(){
    	return COMPANIAS;
    }
    public static String getInforme(){
    	return INFORME;
    }

    
    public static String Inicio(){
        return "" + DIAIN + "/" + MESIN + "/" + ANOIN;
    }
    
    public static String Final(){
        return "" + DIAFIN + "/" + MESFIN + "/" + ANOFIN;
    }
    
    public static boolean tabulado(){
        return TABULADO;
    }
    public static boolean fechaValida(String vuelo){ //quedan definidas los dias límites como constantes en la clase. Los dias de inicio y final están incluidos.
        boolean valida = false;
        if(ANOIN < anno(vuelo) && anno(vuelo) < ANOFIN){
            valida = true;
        } else if(ANOIN == anno(vuelo)){
            if(MESIN < mes(vuelo)){
                valida = true;
            } else if(MESIN == mes(vuelo)){
                if(DIAIN <= dia(vuelo)){
                    valida = true;
                }
            }
        } else if(ANOFIN == anno(vuelo)){
            if(MESFIN > mes(vuelo)){
                valida = true;
            } else if(MESIN == mes(vuelo)){
                if(DIAFIN <= dia(vuelo)){
                 valida = true;
                }
            }
        }
        return valida;
    }
    
    public static void analizarVuelo(String vuelo){
        if(fechaValida(vuelo))
            introducirNuevoVuelo(vuelo);
    }
    
    // REVISAR EL METODO skip DE LA CLASE Scanner ANTES DE EJECUTAR TODO ESTE CÓDIGO.
    
    public static int anno(String vuelo){ //Devuelve el año de un String del objeto VUELOS.TXT.
        int anno=0;
        try {
        Scanner input = new Scanner(vuelo);
        input.skip("(\\w+) (\\w+) (\\w+) (\\w+) (\\d+)/(\\d+)/");
        anno=input.nextInt();
        input.close();
        } catch (IllegalStateException ise) {ise.getMessage();} catch (NoSuchElementException nsee) {nsee.getMessage();} catch (Exception e) {e.getMessage();}
        return anno;
    }
    
    public static int mes(String vuelo){ //Devuelve el mes de un String del objeto VUELOS.TXT.
        int mes=0;
        try {
        Scanner input = new Scanner(vuelo);
        input.skip("(\\w+) (\\w+) (\\w+) (\\w+) (\\d+)/");
        mes=input.nextInt();
        input.close();
        } catch (IllegalStateException ise) {ise.getMessage();} catch (NoSuchElementException nsee) {nsee.getMessage();} catch (Exception e) {e.getMessage();}
        return mes;
    }
    
    public static int dia(String vuelo){ //Devuelve el dia de un String del objeto VUELOS.TXT.
        int dia=0;
        try {
        Scanner input = new Scanner(vuelo);
        input.skip("(\\w+) (\\w+) (\\w+) (\\w+) ");
        dia=input.nextInt();
        input.close();
        } catch (IllegalStateException ise) {ise.getMessage();} catch (NoSuchElementException nsee) {nsee.getMessage();} catch (Exception e) {e.getMessage();}
        return dia;
    }
    
    public static void introducirNuevoVuelo(String vuelo){
        if(fechaValida(vuelo)){
            try {
                Scanner input = new Scanner(vuelo);
                String nvuelo = input.next();
                String compania = input.next();
                String aero = input.next();
                String malo = input.next(); //aeropuerto de destino
                malo = input.next(); // fecha
                malo = input.next(); // hora
                int pasajeros = input.nextInt();
                int maximo = input.nextInt();
                input.close();
                int i = Compania.existeCompania(compania);
                if (i != -1){ 
                    int j = Compania.GetInforme(i).existeAeropuerto(aero,compania);
                    if(j != -1){ 
                        Compania.GetInforme(i).GetOpera(j).nuevoVuelo(pasajeros,maximo); 
                    } else {
                        Compania.GetInforme(i).anadeAeropuerto(aero); 
                        j = Compania.GetInforme(i).existeAeropuerto(aero,compania);
                        Compania.GetInforme(i).GetOpera(j).nuevoVuelo(pasajeros,maximo); 
                    }
                } else {
                    Compania.anadeCompania(compania); 
                    i = Compania.existeCompania(compania);
                    Compania.GetInforme(i).anadeAeropuerto(aero); 
                    Compania.GetInforme(i).GetOpera(0).nuevoVuelo(pasajeros,maximo); 
                }
            } catch (IllegalStateException ise) {ise.getMessage();} catch (NoSuchElementException nsee) {nsee.getMessage();} catch (Exception e) {e.getMessage();}
        } 
    }
    
    public static void analizarArchivo(){
        try{
            Scanner f = new Scanner(new File(VUELOS));
            while(f.hasNextLine()){
                String vuelo = f.nextLine();
                analizarVuelo(vuelo);
            }
        } catch (IOException ioe) {ioe.getMessage();}
    }
    
    /* A partir de aquí, código de Patri. */
    
    public static void ayuda(){
        System.out.println("Introduzca el digito correspondiente para que acceder a los apartador o salir al menu:");
        System.out.println("");
        System.out.println("1-Vuelos");
        System.out.println("2-Informe");
        System.out.println("3-Tabulación");
        System.out.println("4-Menu");
        System.out.println("");
        Scanner teclado= new Scanner(System.in);
        int n= teclado.nextInt();
        if(n==1){
            System.out.println("Los vuelos se tratan como cualidades de las compañías y de los aeroputos, no como objetos propiamente dichos. Se introducen en el archivo vuelos.txt");
            System.out.println("");
        }else if(n==2){
            System.out.println("El informe se generará en la misma carpeta y saldrán todos los vuelos entre las fechas seleccionadas ordenadas porcompañías");
            System.out.println("");
            ayuda();
        }else if(n==3){
            System.out.print("El informe puede ser creado tabulado o sin tabular. Esto lo hemos creado debido a que el fichero tabulado ofrece una gran mejora visual en programas");
            System.out.print("como excel o similares. La vista sin tabular es recomendable para ver ficheros de la manera tradicional; aunque sigue siendo mejor la forma tabulada");
            System.out.println("en los programas a los que va dirigida.");
            System.out.println("");
            ayuda();
        }else if(n==4){
            menu();
        }else if(n!=1&&n!=2&&n!=3){
            System.out.println("Error al introducir digito");
            System.out.println("");
            ayuda();
        }
        

    }
    
    public static void menu() {
        System.out.println("Elija el numero correspondiente a la accion a realizar: ");
        System.out.println("1-Informe entre fechas.");    
        System.out.println("2-Ayuda");
        System.out.println("0-Salir"); //opcional
        System.out.println("");
        Scanner leer = new Scanner(System.in);
        try{
            int opcion = leer.nextInt();
            System.out.print('\f');
            switch (opcion) {
                case 1: informeEntreFechas() ; break;
                case 2: ayuda(); break;
                case 0: System.exit(0); break;
                default: System.out.println("La opcion elegida no existe"); break;
            }
        }
        catch(InputMismatchException excepcion) {System.err.print("El caracter introducido no es un numero");}
        menu();
    }
    
    public static void main(String [] args){
        menu();
    }
    
    public static boolean fechaIntroducidaValida(String vuelo){
        boolean correcto = false;
        Scanner f = new Scanner(vuelo).useDelimiter("/");
        int dia = f.nextInt();
        int mes = f.nextInt();
        int ano = f.nextInt();
        if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
            if(dia > 0 && dia <32)
                correcto = true;
        }
        if(mes == 2){
            if(ano%4==0) {
                if(ano%100==0){
                    if(ano%400==0){
                    if (dia > 0 && dia <30)
                        correcto = true; 
                    } else {
                     if (dia > 0 && dia <29)
                        correcto = true;
                    }
                }else{
                    if (dia > 0 && dia <30)
                        correcto = true;
                }
            } else {
            if (dia > 0 && dia <29)
                correcto = true;
            }
        }
        if(mes == 4 || mes == 6 || mes == 9 || mes == 11){
            if(dia > 0 && dia <31)
                correcto = true;
        }
        return correcto;
    }
    
    // Calcula si la fecha inicial es anterior a la final.
    public static boolean fechaCorrecta(){
        boolean menor=true;
        if(ANOFIN<=ANOIN){
            menor=false;
        }else if(ANOFIN==ANOIN&&MESFIN<=MESIN){
            menor=false;        
        }else if(ANOFIN==ANOIN&&MESFIN==MESIN&&DIAFIN<=DIAIN){
            menor=false;
        }
        return menor;
    }
    
    public static void informeEntreFechas(){
        String fecha;
        boolean correcto = false;
        do{
            do{
             Scanner f = new Scanner(System.in);
             System.out.println("Introduzca fecha de inicio. Formato: dd/mm/aaaa (ejemplo: 19/4/1994)");
             fecha = f.next();
             if(!fechaIntroducidaValida(fecha)){
                System.out.println("Fecha introducida no valida. Vuelva a intentarlo.");
                } else {correcto = true;}
            } while (!correcto);
            
            Scanner g = new Scanner(fecha).useDelimiter("/");
            DIAIN = g.nextInt();
            MESIN = g.nextInt();
            ANOIN = g.nextInt();
            correcto=false;
            do{
             Scanner f = new Scanner(System.in);
             System.out.println("Introduzca fecha final. Formato: dd/mm/aaaa (ejemplo: 19/4/1994)");
             fecha = f.next();
             if(!fechaIntroducidaValida(fecha)){
                System.out.println("Fecha introducida no valida. Vuelva a intentarlo.");
                } else {correcto = true;}
            } while (!correcto);
            
            g = new Scanner(fecha).useDelimiter("/");
            DIAFIN = g.nextInt();
            MESFIN = g.nextInt();
            ANOFIN = g.nextInt();
            
            if(!fechaCorrecta()){
                System.out.println("La fecha inicial es posterior a la final.");
            }
            
        } while(!fechaCorrecta());
        System.out.print('\f');
        setTabulacion();
        System.out.print('\f');
        
        //INTRODUCIDAS LAS FECHAS DE INICIO Y FINAL; MEDIMOS EL NUMERO MÁXIMO DE COMPAÑIAS.
        System.out.print("Comprobando numero de aeropuertos y compañías... ");
        Compania.numeroAerolineas();
        //MEDIDO EL NUMERO DE COMPAÑIAS; MEDIMOS EL NUMERO MÁXIMO DE AEROPUERTOS.
        Compania.numeroAeropuertoTotales();
        System.out.println("Hecho.");
        System.out.print("Generando aeropuertos y compañías... ");
        //MEDIDOS AMBOS, GENERAMOS EL ARRAY DE COMPAÑIAS, VACIO.
        Compania.generaInforme();
        System.out.println("Hecho.");
        System.out.print("Analizando archivo vuelos y obteniendo datos... ");
        // VAMOS ANALIZANDO LINEA A LINEA EL ARCHIVO.
        analizarArchivo();
        System.out.println("Hecho.");
        System.out.print("Generando informe... ");
        // ANALIZADOS TODOS LOS VUELOS. PASAMOS DIRECTAMENTE A GENERAR EL INFORME.
        Informe.generaInforme();
    }

    public static void setTabulacion(){
        Scanner f = new Scanner(System.in);
        System.out.println("El fichero INFORME.TXT puede ser Tabulado, para un posterior tratamiento más sencillo.");
        System.out.println("Si tabula el informe, el archivo será más incómodo en lectura, pero muy fácil de tratar en");
        System.out.println("programas tales como Microsoft Excel o OpenOffice Calc.");
        System.out.println("Si no tabula el informe, el archivo será más cómodo en su lectura, pero difícil de tratar.");
        System.out.println("¿Deseas que el fichero esté tabulado? (Y/N)");
        char s = f.next().charAt(0);
        if(s=='y' | s=='Y')
            TABULADO=true;
        else 
            TABULADO=false;
    }

}
