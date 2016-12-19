import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * Created by 45858000w on 12/12/16.
 */

public class ManagePrestamo {

    private static SessionFactory factory;

   /* public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManagePrestamo MP = new ManagePrestamo();

      // Add few employee records in database
        Integer presID1 = MP.addPrestamos("Zara", "Ali", 1000);
        Integer presID2 = MP.addPrestamos("Daisy", "Das", 5000);
        Integer presID3 = MP.addPrestamos("John", "Paul", 10000);

      // List down all the employees
        MP.listPrestamos();
*/
      /* Update employee's records */
        //  MP.updatePrestamos(empID1, 5000);

      /* Delete an employee from the database */
        //   MP.deletePrestamos(empID2);

      /* List down new list of the employees */
        // MP.listPrestamos();
   // }
    /* Method to CREATE an employee in the database */
    public Integer addPrestamos(int idLibro, int idSocio, Date fechaInicio, Date fechaFinal){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer prestamoID = null;
        try{
            tx = session.beginTransaction();
            Prestamo prestamo = new Prestamo(idLibro, idSocio, fechaInicio, fechaFinal);
            prestamoID = (Integer) session.save(prestamo);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return prestamoID;
    }
    /* Method to  READ all the employees */
    public void listPrestamos( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List prestamos = session.createQuery("FROM Prestamo").list();
            for (Iterator iterator =
                 prestamos.iterator(); iterator.hasNext();){
                Prestamo employee = (Prestamo) iterator.next();
                System.out.print("ID : " + employee.getId());
                System.out.print("\tID Libro: " + employee.getIdLibro());
                System.out.print("\tID Socio: " + employee.getIdSocio());
                System.out.println("\tFecha Inicio: " + employee.getFechaInicio());
                System.out.println("\tFecha Final: " + employee.getFechaFinal());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to UPDATE salary for an employee */
    public void updatePrestamo(Integer PrestamoID,int idLibro, int idSocio, Date fechaInicio, Date fechaFinal){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Prestamo prestamo =
                    (Prestamo)session.get(Prestamo.class, PrestamoID);
            prestamo.setIdLibro( idLibro );
            prestamo.setIdSocio( idSocio );
            prestamo.setFechaInicio( fechaInicio );
            prestamo.setFechaFinal( fechaFinal );
            session.update(prestamo);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deletePrestamo(Integer PrestamoID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Prestamo prestamo =
                    (Prestamo)session.get(Prestamo.class, PrestamoID);
            session.delete(prestamo);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}