/**
 * Created by 45858000w on 12/12/16.
 */
import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ManageLibro {

    private static SessionFactory factory;

   /* public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageLibro ML = new ManageLibro();

      // Add few employee records in database
        Integer libID1 = ML.addLibro("Zara", "Ali", 1000);
        Integer libID2 = ML.addLibro("Daisy", "Das", 5000);
        Integer libID3 = ML.addLibro("John", "Paul", 10000);

      // List down all the employees
        ML.listLibros();
*/
      /* Update employee's records */
        //  ML.updateLibro(empID1, 5000);

      /* Delete an employee from the database */
        //   ML.deleteLibro(empID2);

      /* List down new list of the employees */
        // ML.listLibros();
   // }
    /* Method to CREATE an employee in the database */
    public Integer addLibro(String titulo, int numEjemplares, String editorial, int numPaginas, int anyoEdicion) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer libroID = null;
        try{
            tx = session.beginTransaction();
            Libro libro = new Libro(titulo, numEjemplares, editorial, numPaginas, anyoEdicion);
            libroID = (Integer) session.save(libro);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return libroID;
    }

    /* Method to  READ all the employees */
    public List listLibros( ){
        Session session = factory.openSession();
        Transaction tx = null;
        List libros=null;
        try{
            tx = session.beginTransaction();
            libros = session.createQuery("FROM Libro").list();
            for (Iterator iterator =
                 libros.iterator(); iterator.hasNext();){
                Libro libro = (Libro) iterator.next();
                System.out.print("ID: " + libro.getId());
                System.out.print("\tTitulo: " + libro.getTitulo());
                System.out.print("\tNº Ejemplares: " + libro.getNumEjemplares());
                System.out.print("\tEditorial: " + libro.getEditorial());
                System.out.print("\tNº Paginas: " + libro.getNumPaginas());
                System.out.println("\tAño Edicion: " + libro.getAnyoEdicion());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return libros;
    }
    /* Method to UPDATE salary for an employee */
    public void updateLibro(Integer LibroID, String titulo, int numEjemplares, String editorial, int numPaginas, int anyoEdicion) {
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Libro libro =
                    (Libro)session.get(Libro.class, LibroID);
            libro.setTitulo( titulo );
            libro.setNumEjemplares( numEjemplares );
            libro.setEditorial( editorial );
            libro.setNumPaginas( numPaginas );
            libro.setAnyoEdicion( anyoEdicion );
            session.update(libro);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deleteLibro(Integer LibroID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Libro libro =
                    (Libro)session.get(Libro.class, LibroID);
            session.delete(libro);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}