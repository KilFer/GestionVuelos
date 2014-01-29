
import java.util.*;
import java.io.*;
import java.math.*;

public class Compania {

    /**
     * Array donde se van a encontrar los objetos compañia
     */
    private static Compania[] informe; //Array de compañias.
    private static int ncomp; //numero de compañias en el array
    private static int maxcomp; //numero de compañías en el archivo
    private static int maxaero; //numero de aeropuertos en el archivo
    private int naero; //Número de aeropuertos de la compañía.
    private String nombre; //Siglas de la compañia
    private double aprov; //Porcentaje de aprovechamiento de la compañía
    private Aeropuerto[] opera; //Array de aeropuertos de cada compañia.

    public static Compania GetInforme(int i) {
        return informe[i];
    }

    public Aeropuerto GetOpera(int i) {
        return opera[i];
    }

    public boolean operaLLeno() {
        return naero == maxaero;
    }

    /**
     * Comprueba que el vector de companias no está lleno
     */
    public static boolean informeLleno() {
        return maxcomp == ncomp;
    }

    public static int Getncom() {
        return ncomp;
    }

    /**
     * Objeto compañía y sus métodos básicos
     */
    public Compania(String nom) {
        aprov = 0.0;
        naero = 0;
        nombre = nom;
        opera = new Aeropuerto[maxaero];
    }

    /// Genera el array Informe con el tamaño definido. 
    public static void generaInforme() {
        informe = new Compania[maxcomp];
    }

    public double aprovechamiento() {
        return aprov;
    }

    public int numAeropuertos() {
        return naero;
    }

    public String nombreCompania() {
        return nombre;
    }

    public int numeroAeropuertos() {
        return opera.length;
    }

    /**
     * Ordena compañías del array.
     */
    public static void ordenaCompanias() {
        double h;
        int j;
        Compania apoyo;
        for (int i = 0; i < ncomp; i++) {
            apoyo = informe[i];
            h = informe[i].aprovechamiento();
            j = i;
            while (j > 0 && informe[j - 1].aprovechamiento() != h) {
                informe[j] = informe[j - 1];
                j = j - 1;
            }
            informe[j] = apoyo;
        }
    }

    /**
     * Ordena los aeropuertos según el procentaje de aprovechamiento de una
     * compañía.
     *
     */
    public void Ordenaaeropuertos() {
        int i, j;
        Aeropuerto apoyo;
        double h;
        for (i = 0; i < naero; i++) {
            opera[i].aprovechamiento();
        }
        for (i = 1; i < naero; i++) {
            apoyo = opera[i];
            h = opera[i].getAprovechamiento();
            j = i;
            while (j > 0 && opera[j - 1].getAprovechamiento() < h) { //METODO DE PATRI!
                opera[j] = opera[j - 1];
                j--;
            }
            opera[j] = apoyo;
        }
    }

    /**
     * Determina la existencia o no de un aeropuerto dentro de una compañía, si
     * la posición vendrá determinada desde 0-tamaño array, el -1 indicará no
     * existe.
     */
    public static int existeAeropuerto(String siglas, String siglascompania) {
        int h = -1;
        for (int j = 0; j < ncomp && h == -1; j++) {
            if (siglascompania.equals(informe[j].nombreCompania())) {
                for (int i = 0; i < informe[j].numAeropuertos(); i++) {
                    if (siglas.equals(informe[j].opera[i].getCodigo())) {
                        h = i;
                    }
                }
            }
        }
        return h;
    }

    /**
     * Repetimos igual que en el método anterior, el -1 significará que no
     * existe.
     */
    static int existeCompania(String siglas) {
        int h = -1;
        for (int j = 0; j < ncomp; j++) {
            if (siglas.equals(informe[j].nombreCompania())) {
                h = j;
            }
        }
        return h;
    }

    /**
     * Añade compañías al array, pero teniendo en cuenta que esas compañías no
     * generan vuelos en entre esas fechas.
     */
    public static void anadeCompania(String siglas) {
        if (!informeLleno()) {
            int i = ncomp;
            informe[i] = new Compania(siglas);
            ncomp += 1;
        }
    }

    /**
     * Añade nuevo aeropuerto en el array
     */
    public void anadeAeropuerto(String siglas) {
        if (!operaLLeno()) {
            int i = naero;
            opera[i] = new Aeropuerto(siglas);
            naero += 1;
        }

    }

    /**
     * Calcula el número de aeropuertos en el fichero aeropuertos.txt
     */
    public static void numeroAerolineas() {
        try {
            Scanner f = new Scanner(new File(Programa.getCompanias())); // Programa.getCompanias == ruta del fichero
            String linea;
            int totalcompanias = 0;
            while (f.hasNextLine()) {
                totalcompanias++;
                linea = f.nextLine();
            }
            f.close();
            maxcomp = totalcompanias;
        } catch (FileNotFoundException fnfe) {
            System.out.println("Fichero no encontrado o no se tiene los permisos");
        }
    }

    /**
     * Numero de aeropuertos en el fichero aeropuetos.txt
     */
    public static void numeroAeropuertoTotales() {
        Scanner f = null;
        String linea;
        int totalaeropuertos = 0;
        try {
            f = new Scanner(new File(Programa.getAeropuertos()));
        } catch (FileNotFoundException fnfe) {
            System.out.println("Fichero no encontrado o no se tiene los permisos");
        }
        if (f != null) {
            while (f.hasNextLine()) {
                totalaeropuertos++;
                linea = f.nextLine();
            }
            f.close();
        }
        maxaero = totalaeropuertos;
    }

    public double porcentajeAprovechamientoTotal() {
        int num = 0, max = 0;
        for (int i = 0; i < naero; i++) {
            num += opera[i].getNumPasajeros();
            max += opera[i].getMaxPasajeros();
        }
        double total = ((double) num / (double) max) * 100;
        aprov = total;
        return total;
    }

    // Metodo hecho por Fer. Para agilizar el tratamiento en el Informe.
    public String enPantalla() {
        String aero = null;
        boolean encontrado = false;
        try {
            Scanner f = new Scanner(new File(Programa.getCompanias()));
            while (f.hasNextLine() && !encontrado) {
                String linea = f.nextLine();
                String siglas = linea.substring(0, 2);
                if (siglas.equals(nombre)) {
                    aero = linea.substring(5);
                    encontrado = true;
                }
            }
        } catch (IOException ioe) {
        };

        int numpasajeros = 0, maxpasajeros = 0;
        for (int i = 0; i < naero; i++) {
            numpasajeros += opera[i].getNumPasajeros();
            maxpasajeros += opera[i].getMaxPasajeros();
        }

        // Actualizar el valor de Aprovechamiento de la compañía.
        this.porcentajeAprovechamientoTotal();

        // REDONDEO. Para que el porcentaje salga solo con 2 decimales.
        String val = aprov + "";
        BigDecimal big = new BigDecimal(val);
        big = big.setScale(2, RoundingMode.HALF_UP); //AJUSTAR AQUI el número de decimales.

        // FIN del redondeo.
        // Se han tabulado las lineas. El tabulador es "\t. Se pueden cambiar a espacios.
        if (Programa.tabulado()) {
            nombre = aero + " (" + nombre + ") \t \t" + big + "% \t" + numpasajeros + " \t" + maxpasajeros + " \t";
        } else {
            nombre = aero + " (" + nombre + ") -" + big + "% de utilidad en " + naero + " aeropuertos. " + numpasajeros + " pasajeros de " + maxpasajeros + " posibles.";
        }
        return nombre;
    }
}
