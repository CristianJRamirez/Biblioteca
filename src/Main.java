import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 45858000w on 19/12/16.
 */
public class Main {

    /* public static ArrayList<ManageLibro> libros = new ArrayList<>();
     public static ArrayList<ManageSocio> socios = new ArrayList<>();
     public static ArrayList<ManagePrestamo> prestamos = new ArrayList<>();
 */
    public static void main(String[] args) {
        System.out.println("BIENVENIDO AL PROGRAMA DE CRISTIAN JAVIER");


//http://serprogramador.es/como-conectar-y-utilizar-java-con-sqlite/

        boolean exit = false;


        do {
            System.out.println("\n<---------------------------MENU----------------------------->");
            System.out.println("Que quieres hacer \n" +
                    "\t\tCrear tablas                                                                   -> Pulse el '1' \n" +
                    "\t\tDonar d'alta, de baixa i modificar llibres.                                    -> Pulse el '2' \n" +
                    "\t\tDonar d'alta, de baixa i modificar socis.                                      -> Pulse el '3' \n" +
                    "\t\tConsultar socis i llibres per diferents criteris: nom, cognom, títol, autor.   -> Pulse el '4' \n" +
                    "\t\tRealitzar préstecs d'un llibre a un soci.                                      -> Pulse el '5' \n" +
                    "\t\tLlistar de llibres prestats.                                                   -> Pulse el '6' \n" +
                    "\t\tLlistar els llibres prestats a un soci determinat.                             -> Pulse el '7' \n" +
                    "\t\tLlistar els llibres que han superat la data de fi de préstec                   -> Pulse el '8' \n" +
                    "\t\tLlistar els socis que tenen llibres que han superat a data de fi de préstec.   -> Pulse el '9' \n" +
                    "\t\tSalir de la Aplicacion                                                         -> Pulse el '0' " +
                    "");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if (opcion <= 9) {
                switch (opcion) {
                    case 0://salir
                        exit = true;
                        break;
                    case 1://Llistar els socis que tenen llibres que han superat a data de fi de préstec
                        crearTablas();
                        break;
                    case 2://Donar d'alta, de baixa i modificar llibres.
                        updateLibro();
                        break;
                    case 3://Donar d'alta, de baixa i modificar socis.
                        updateSocio();
                        break;
                    case 4://Consultar socis i llibres per diferents criteris: nom, cognom, títol, autor.
                        consultar();
                        break;
                    case 5://Realitzar préstecs d'un llibre a un soci.
                        doPrestamo();
                        break;
                    case 6://Llistar de llibres prestats.
                        listaLibros();
                        break;
                    case 7://Llistar els llibres prestats a un soci determinat.
                        listaLibros();
                        break;
                    case 8://Llistar els llibres que han superat la data de fi de préstec
                        listaLibros();
                        break;
                    case 9://Llistar els socis que tenen llibres que han superat a data de fi de préstec
                        listaSocios();//
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Opcion Incorrecta");
            }
        } while (!exit);

        System.out.println("EL PROGRAMA A FINALIZADO");
    }

    private static void crearTablas() {
        try {
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    //region Libro : -> añadir, actualizar y borrar
    private static void updateLibro() {
        boolean exit = false;
        do {
            System.out.println("\n<---------------------------MENU----------------------------->");
            System.out.println("Que quieres hacer sobre un Libro \n" +
                    "\t\tAñadir                         -> Pulse el '1' \n" +
                    "\t\tActualizar                     -> Pulse el '2' \n" +
                    "\t\tEliminar                       -> Pulse el '3' \n" +
                    "\t\tSalir de la Aplicacion         -> Pulse el '0' " +
                    "");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if ((opcion == 0) || (opcion == 1) || (opcion == 2) || (opcion == 3)) {
                switch (opcion) {
                    case 0://salir
                        exit = true;
                        break;
                    case 1:
                        datosLibro(1);
                        break;
                    case 2:
                        datosLibro(2);
                        break;
                    case 3:
                        eliminarLibro();
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Opcion Incorrecta");
            }
        } while (!exit);

    }

    private static void datosLibro(int opcion) {
        int id = 0;

        if (opcion == 2) {
            System.out.println("Dime el Id del Libro");
            Scanner sc2 = new Scanner(System.in);
            id = sc2.nextInt();
        }

        System.out.println("Dime el Titulo del Libro");
        Scanner sc = new Scanner(System.in);
        String titulo = sc.nextLine();

        System.out.println("Dime los numero de ejemplares del Libro");
        sc = new Scanner(System.in);
        int numEjemplares = sc.nextInt();

        System.out.println("Dime la editorial del Libro");
        sc = new Scanner(System.in);
        String editorial = sc.nextLine();

        System.out.println("Dime el numero de paginas del Libro");
        sc = new Scanner(System.in);
        int numPaginas = sc.nextInt();

        System.out.println("Dime el año de la edicion del Libro");
        sc = new Scanner(System.in);
        int anyoEdicion = sc.nextInt();

        ManageLibro ML = new ManageLibro();

        if (opcion == 1) {
            Integer libID1 = ML.addLibro(titulo, numEjemplares, editorial, numPaginas, anyoEdicion);
            //libros.add(libID1,ML);
            System.out.println("Añadido el Libro");
        } else if (opcion == 2) {
            ML.updateLibro(id, titulo, numEjemplares, editorial, numPaginas, anyoEdicion);
            //libros.get(id).updateLibro(id, titulo, numEjemplares, editorial, numPaginas, anyoEdicion);
            System.out.println("Actualizado el Libro");
        }

    }

    private static void eliminarLibro() {
        System.out.println("Dime el Id del Libro");
        Scanner sc = new Scanner(System.in);
        int idLibro = sc.nextInt();

        ManageLibro ML = new ManageLibro();

        ML.deleteLibro(idLibro);
        //libros.get(idLibro).deleteLibro(idLibro);
        System.out.println("Libro Eliminado");

    }
//endregion

    //region Libro : -> añadir, actualizar y borrar
    private static void updateSocio() {
        boolean exit = false;

        do {
            System.out.println("\n<---------------------------MENU----------------------------->");
            System.out.println("Que quieres hacer sobre un Socio \n" +
                    "\t\tAñadir                         -> Pulse el '1' \n" +
                    "\t\tActualizar                     -> Pulse el '2' \n" +
                    "\t\tEliminar                       -> Pulse el '3' \n" +
                    "\t\tSalir de la Aplicacion         -> Pulse el '0' " +
                    "");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if ((opcion == 0) || (opcion == 1) || (opcion == 2) || (opcion == 3) || (opcion == 4) || (opcion == 5) || (opcion == 6) || (opcion == 7)) {
                switch (opcion) {
                    case 0://salir
                        exit = true;
                        break;
                    case 1:
                        datosSocio(1);
                        break;
                    case 2:
                        datosSocio(2);
                        break;
                    case 3:
                        eliminarSocio();
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Opcion Incorrecta");
            }
        } while (!exit);
    }

    private static void datosSocio(int opcion) {
        int id = 0;

        if (opcion == 2) {
            System.out.println("Dime el Id del Socio");
            Scanner sc2 = new Scanner(System.in);
            id = sc2.nextInt();
        }

        System.out.println("Dime el nombre del Socio");
        Scanner sc = new Scanner(System.in);
        String nombre = sc.nextLine();

        System.out.println("Dime la edad del Socio");
        sc = new Scanner(System.in);
        int edad = sc.nextInt();

        System.out.println("Dime la direccion del Socio");
        sc = new Scanner(System.in);
        String direccion = sc.nextLine();

        System.out.println("Dime el telefono del Socio");
        sc = new Scanner(System.in);
        int telefono = sc.nextInt();


        ManageSocio MS = new ManageSocio();

        if (opcion == 1) {
            Integer libID1 = MS.addSocios(nombre, edad, direccion, telefono);
            //socios.add(libID1,MS);
            System.out.println("Añadido el Socio");
        } else if (opcion == 2) {
            MS.updateSocio(id, nombre, edad, direccion, telefono);
            //socios.get(id).updateSocio(id,nombre, edad, direccion, telefono) ;
            System.out.println("Actualizado el Socio");
        }

    }

    private static void eliminarSocio() {
        System.out.println("Dime el Id del Socio");
        Scanner sc = new Scanner(System.in);
        int idSocio = sc.nextInt();

        ManageSocio MS = new ManageSocio();

        MS.deleteSocio(idSocio);
        ///socios.get(idSocio).deleteSocio(idSocio);
        System.out.println("Socio Eliminado");

    }
//endregion


    private static void consultar() {
        //nom, cognom, títol, autor.
        boolean exit = false;
        do {
            System.out.println("\n<---------------------------MENU----------------------------->");
            System.out.println("Que quieres consultar \n" +
                    "\t\tpor Nombre                         -> Pulse el '1' \n" +
                    "\t\tpor Edat                     -> Pulse el '2' \n" +
                    "\t\tpor Titulo                       -> Pulse el '3' \n" +
                    "\t\tpor Autor                       -> Pulse el '4' \n" +
                    "\t\tSalir de la Aplicacion         -> Pulse el '0' " +
                    "");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if (opcion > 5) {
                switch (opcion) {
                    case 0://salir
                        exit = true;
                        break;
                    case 1://
                        socioNombre();
                        break;
                    case 2://
                        socioEdat();
                        break;
                    case 3://
                        libroTitulo();
                        break;
                    case 4://
                        libroAnyo();
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Opcion Incorrecta");
            }
        } while (!exit);
    }

    //region busqueda de libro/socio -> nombre, edat, titulo, anyo
    private static void socioNombre() {
        System.out.println("Dime el Nombre del Socio");
        Scanner sc = new Scanner(System.in);
        String nombre = sc.nextLine();


        ManageSocio MS = new ManageSocio();
        List socios = MS.listSocios();
        for (Iterator iterator =
             socios.iterator(); iterator.hasNext(); ) {
            Socio socio = (Socio) iterator.next();

            if (socio.getNombre().equalsIgnoreCase(nombre)) {
                System.out.print("ID: " + socio.getId());
                System.out.print("\tNombre : " + socio.getNombre());
                System.out.print("\tEdad : " + socio.getEdad());
                System.out.print("\tDireccion : " + socio.getDireccion());
                System.out.println("\tTelefono : " + socio.getTelefono());
            }
        }
    }

    public static void socioEdat() {
        System.out.println("Dime la Edat del Socio");
        Scanner sc = new Scanner(System.in);
        int edat = sc.nextInt();


        ManageSocio MS = new ManageSocio();
        List socios = MS.listSocios();
        for (Iterator iterator =
             socios.iterator(); iterator.hasNext(); ) {
            Socio socio = (Socio) iterator.next();

            if (socio.getEdad() == edat) {
                System.out.print("ID: " + socio.getId());
                System.out.print("\tNombre : " + socio.getNombre());
                System.out.print("\tEdad : " + socio.getEdad());
                System.out.print("\tDireccion : " + socio.getDireccion());
                System.out.println("\tTelefono : " + socio.getTelefono());
            }
        }
    }


    private static void libroTitulo() {
        System.out.println("Dime el Titulo del Libro");
        Scanner sc = new Scanner(System.in);
        String titulo = sc.nextLine();


        ManageLibro ML = new ManageLibro();
        List libros = ML.listLibros();
        for (Iterator iterator =
             libros.iterator(); iterator.hasNext(); ) {
            Libro libro = (Libro) iterator.next();

            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                System.out.print("ID: " + libro.getId());
                System.out.print("\tTitulo: " + libro.getTitulo());
                System.out.print("\tNº Ejemplares: " + libro.getNumEjemplares());
                System.out.print("\tEditorial: " + libro.getEditorial());
                System.out.print("\tNº Paginas: " + libro.getNumPaginas());
                System.out.println("\tAño Edicion: " + libro.getAnyoEdicion());
            }
        }
    }

    private static void libroAnyo() {
        System.out.println("Dime el año de la edicion del Libro");
        Scanner sc = new Scanner(System.in);
        int anyo = sc.nextInt();


        ManageLibro ML = new ManageLibro();
        List libros = ML.listLibros();
        for (Iterator iterator =
             libros.iterator(); iterator.hasNext(); ) {
            Libro libro = (Libro) iterator.next();

            if (libro.getAnyoEdicion() == anyo) {
                System.out.print("ID: " + libro.getId());
                System.out.print("\tTitulo: " + libro.getTitulo());
                System.out.print("\tNº Ejemplares: " + libro.getNumEjemplares());
                System.out.print("\tEditorial: " + libro.getEditorial());
                System.out.print("\tNº Paginas: " + libro.getNumPaginas());
                System.out.println("\tAño Edicion: " + libro.getAnyoEdicion());
            }
        }
    }
//endregion

    private static void doPrestamo() {
        //TODO : ACABAR

    }

    private static void listaLibros() {
        boolean exit = false;
        do {
            System.out.println("\n<---------------------------MENU----------------------------->");
            System.out.println("Que Libros Prestados quieres consultar \n" +
                    "\t\tpor Todos                                      -> Pulse el '1' \n" +
                    "\t\tpor un Socio Determinado                       -> Pulse el '2' \n" +
                    "\t\tque han superat la data de fi de préstec       -> Pulse el '3' \n" +
                    "\t\tSalir de la Aplicacion                         -> Pulse el '0' " +
                    "");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if (opcion > 5) {
                switch (opcion) {
                    case 0://salir
                        exit = true;
                        break;
                    case 1://
                        listaTodosLibros();
                        break;
                    case 2://
                        listaLibrosSocio();
                        break;
                    case 3://
                        listaLibrosFecha();
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Opcion Incorrecta");
            }
        } while (!exit);
    }

    private static void listaTodosLibros()
    {
        ManagePrestamo MP = new ManagePrestamo();
        List prestamos = MP.listPrestamos();
        for (Iterator iterator =
             prestamos.iterator(); iterator.hasNext(); ) {
            Prestamo prestamo = (Prestamo) iterator.next();

            System.out.print("ID : " + prestamo.getId());
            System.out.print("\tID Libro: " + prestamo.getIdLibro());
            System.out.print("\tID Socio: " + prestamo.getIdSocio());
            System.out.println("\tFecha Inicio: " + prestamo.getFechaInicio());
            System.out.println("\tFecha Final: " + prestamo.getFechaFinal());

        }
    }

    private static void listaLibrosSocio()
    {
        System.out.println("Dime el Id del Socio");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();

        ManagePrestamo MP = new ManagePrestamo();
        List prestamos = MP.listPrestamos();
        for (Iterator iterator =
             prestamos.iterator(); iterator.hasNext(); ) {
            Prestamo prestamo = (Prestamo) iterator.next();

            if (prestamo.getIdSocio()==id)
            {
                System.out.print("ID : " + prestamo.getId());
                System.out.print("\tID Libro: " + prestamo.getIdLibro());
                System.out.print("\tID Socio: " + prestamo.getIdSocio());
                System.out.println("\tFecha Inicio: " + prestamo.getFechaInicio());
                System.out.println("\tFecha Final: " + prestamo.getFechaFinal());
            }

        }
    }

    private static void listaLibrosFecha()
    {
        System.out.println("Dime el Id del Socio");
        Scanner sc = new Scanner(System.in);
        String fecha = sc.nextLine();

        ManagePrestamo MP = new ManagePrestamo();
        List prestamos = MP.listPrestamos();
        for (Iterator iterator =
             prestamos.iterator(); iterator.hasNext(); ) {
            Prestamo prestamo = (Prestamo) iterator.next();
//TODO acabar la conversion de fecha para poder comparar
            if (prestamo.getFechaFinal()==fecha)
            {
                System.out.print("ID : " + prestamo.getId());
                System.out.print("\tID Libro: " + prestamo.getIdLibro());
                System.out.print("\tID Socio: " + prestamo.getIdSocio());
                System.out.println("\tFecha Inicio: " + prestamo.getFechaInicio());
                System.out.println("\tFecha Final: " + prestamo.getFechaFinal());
            }

        }
    }

    private static void listaSocios()
    {
        //TODO : acabar de hacer el listado de socios que tenen llibres que han superat a data de fi de préstec
        ManageSocio MS = new ManageSocio();
        List socios = MS.listSocios();
        for (Iterator iterator =
             socios.iterator(); iterator.hasNext();){
            Socio socio = (Socio) iterator.next();

            if (socio.getNombre().equalsIgnoreCase(nombre)) {
                System.out.print("ID: " + socio.getId());
                System.out.print("\tNombre : " + socio.getNombre());
                System.out.print("\tEdad : " + socio.getEdad());
                System.out.print("\tDireccion : " + socio.getDireccion());
                System.out.println("\tTelefono : " + socio.getTelefono());
            }
        }
    }



}
