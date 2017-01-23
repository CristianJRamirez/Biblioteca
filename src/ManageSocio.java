/**
 * Created by 45858000w on 12/12/16.
 */
import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ManageSocio {

    private static SessionFactory factory;

   /* public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageSocio MS = new ManageSocio();

      // Add few employee records in database
        Integer socID1 = MS.addSocios("Zara", "Ali", 1000);
        Integer socID2 = MS.addSocios("Daisy", "Das", 5000);
        Integer socID3 = MS.addSocios("John", "Paul", 10000);

      // List down all the employees
        MS.listSocios();
*/
      /* Update employee's records */
        //  MS.updateSocios(empID1, 5000);

      /* Delete an employee from the database */
        //   MS.deleteSocios(empID2);

      /* List down new list of the employees */
        // MS.listSocios();
    //}
    /* Method to CREATE an employee in the database */
    public Integer addSocios(String nombre, int edad, String direccion, int telefono){
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer socioID = null;
        try{
            tx = session.beginTransaction();
            Socio socio = new Socio(nombre, edad, direccion, telefono);
            socioID = (Integer) session.save(socio);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return socioID;
    }
    /* Method to  READ all the employees */
    public List listSocios( ){
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List socios=null;
        try{
            tx = session.beginTransaction();
            socios = session.createQuery("FROM Socio").list();
            for (Iterator iterator =
                 socios.iterator(); iterator.hasNext();){
                Socio socio = (Socio) iterator.next();
                /*System.out.print("ID: " + socio.getId());
                System.out.print("\tNombre : " + socio.getNombre());
                System.out.print("\tEdad : " + socio.getEdad());
                System.out.print("\tDireccion : " + socio.getDireccion());
                System.out.println("\tTelefono : " + socio.getTelefono());*/
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return socios;
    }
    /* Method to UPDATE salary for an employee */
    public void updateSocio(Integer SocioID,String nombre, int edad, String direccion, int telefono){
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Socio socio =
                    (Socio)session.get(Socio.class, SocioID);
            socio.setNombre( nombre );
            socio.setEdad( edad );
            socio.setDireccion( direccion );
            socio.setTelefono( telefono );
            session.update(socio);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deleteSocio(Integer SocioID){
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Socio socio =
                    (Socio)session.get(Socio.class, SocioID);
            session.delete(socio);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}