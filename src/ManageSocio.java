
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


public class ManageSocio {

    private static SessionFactory factory;

    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageSocio MS = new ManageSocio();

      /* Add few employee records in database */
        Integer empID1 = MS.addEmployee("Zara", "Ali", 1000);
        Integer empID2 = MS.addEmployee("Daisy", "Das", 5000);
        Integer empID3 = MS.addEmployee("John", "Paul", 10000);

      /* List down all the employees */
        MS.listEmployees();

      /* Update employee's records */
        //  MS.updateEmployee(empID1, 5000);

      /* Delete an employee from the database */
        //   MS.deleteEmployee(empID2);

      /* List down new list of the employees */
        // MS.listEmployees();
    }
    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            Socio socio = new Socio(fname, lname, salary);
            employeeID = (Integer) session.save(socio);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return employeeID;
    }
    /* Method to  READ all the employees */
    public void listEmployees( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Socio").list();
            for (Iterator iterator =
                 employees.iterator(); iterator.hasNext();){
                Socio socio = (Socio) iterator.next();
                System.out.print("First Name: " + socio.getFirstName());
                System.out.print("  Last Name: " + socio.getLastName());
                System.out.println("  Salary: " + socio.getSalary());
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
    public void updateEmployee(Integer SocioID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Socio socio =
                    (Socio)session.get(Socio.class, SocioID);
            socio.setSalary( salary );
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
    public void deleteEmployee(Integer SocioID){
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