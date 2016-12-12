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

    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManagePrestamo MP = new ManagePrestamo();

      /* Add few employee records in database */
        Integer empID1 = MP.addPrestamos("Zara", "Ali", 1000);
        Integer empID2 = MP.addPrestamos("Daisy", "Das", 5000);
        Integer empID3 = MP.addPrestamos("John", "Paul", 10000);

      /* List down all the employees */
        MP.listPrestamos();

      /* Update employee's records */
        //  MP.updatePrestamos(empID1, 5000);

      /* Delete an employee from the database */
        //   MP.deletePrestamos(empID2);

      /* List down new list of the employees */
        // MP.listPrestamos();
    }
    /* Method to CREATE an employee in the database */
    public Integer addPrestamos(String fname, String lname, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            Prestamo prestamo = new Prestamo(fname, lname, salary);
            employeeID = (Integer) session.save(prestamo);
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
    public void listPrestamos( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Prestamo").list();
            for (Iterator iterator =
                 employees.iterator(); iterator.hasNext();){
                Prestamo employee = (Prestamo) iterator.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
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
    public void updatePrestamos(Integer EmployeeID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Prestamo employee =
                    (Prestamo)session.get(Prestamo.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deletePrestamos(Integer EmployeeID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Prestamo employee =
                    (Prestamo)session.get(Prestamo.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}