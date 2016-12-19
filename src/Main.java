import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by 45858000w on 19/12/16.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("BIENVENIDO AL PROGRAMA DE CRISTIAN JAVIER");

        ArrayList<ManageLibro> libros = new ArrayList<>();
        ArrayList<ManageSocio> socios = new ArrayList<>();
        ArrayList<ManagePrestamo> prestamos = new ArrayList<>();
//http://serprogramador.es/como-conectar-y-utilizar-java-con-sqlite/

        boolean exit=false;


        do {
            System.out.println("\n<---------------------------MENU----------------------------->");
            System.out.println("Que quieres hacer \n" +
                    "\t\tDonar d'alta, de baixa i modificar llibres.                                    -> Pulse el '1' \n" +
                    "\t\tDonar d'alta, de baixa i modificar socis.                                      -> Pulse el '2' \n" +
                    "\t\tConsultar socis i llibres per diferents criteris: nom, cognom, títol, autor.   -> Pulse el '3' \n" +
                    "\t\tRealitzar préstecs d'un llibre a un soci.                                      -> Pulse el '4' \n" +
                    "\t\tLlistar de llibres prestats.                                                   -> Pulse el '5' \n" +
                    "\t\tLlistar els llibres prestats a un soci determinat.                             -> Pulse el '6' \n" +
                    "\t\tLlistar els llibres que han superat la data de fi de préstec                   -> Pulse el '7' \n" +
                    "\t\tLlistar els socis que tenen llibres que han superat a data de fi de préstec.   -> Pulse el '8' \n" +
                    "\t\tSalir de la Aplicacion                                                         -> Pulse el '0' " +
                    "");
            Scanner sc = new Scanner(System.in);
            int opcion = sc.nextInt();
            if ((opcion==0)||(opcion==1)||(opcion==2)||(opcion==3)||(opcion==4)||(opcion==5)||(opcion==6)||(opcion==7))
            {
                switch (opcion) {
                    case 0://salir
                        exit=true;
                        break;
                    case 1://crear tablas
                        crearTablas();
                        break;
                    case 2://Mostrar Peliculas
                        selectSQLite.getDatos(1,0); //1 = select Peliculas
                        break;
                    case 3://Mostrar Actores
                        selectSQLite.getDatos(2,0); //2 = select Actores
                        break;
                    case 4://Mostrar Relacion Peliculas-Actores(Personajes)
                        selectSQLite.getDatos(3,0); //2 = select Actores
                        break;
                    case 5://Insertar datos de la Api
                        pedirDatos(actores,pelis,PA);
                        insertDatos(actores,pelis,PA);
                        break;
                    case 6://Select de una pelicula por ID del actor
                        System.out.println("Dime el Id del Actor");
                        sc= new Scanner(System.in);
                        int idpelicula=sc.nextInt();
                        selectSQLite.getDatos(4,idpelicula);//
                        break;
                    case 7://Select de una actor por ID del pelicula
                        System.out.println("Dime el Id de la Pelicula");
                        sc= new Scanner(System.in);
                        int idactor=sc.nextInt();
                        selectSQLite.getDatos(5,idactor);
                        break;
                    case 8://Select Global
                        selectSQLite.getDatos(0,0);//4 = select global
                        break;
                    default:
                        break;
                }
            }
            else
            {
                System.out.println("Opcion Incorrecta");
            }
        }while (!exit);

        System.out.println("EL PROGRAMA A FINALIZADO");
    }
}
