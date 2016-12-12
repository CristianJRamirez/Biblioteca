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

    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageLibro ML = new ManageLibro();

      /* Add few employee records in database */
        Integer empID1 = ML.addEmployee("Zara", "Ali", 1000);
        Integer empID2 = ML.addEmployee("Daisy", "Das", 5000);
        Integer empID3 = ML.addEmployee("John", "Paul", 10000);

      /* List down all the employees */
        ML.listEmployees();

      /* Update employee's records */
        //  ME.updateEmployee(empID1, 5000);

      /* Delete an employee from the database */
        //   ME.deleteEmployee(empID2);

      /* List down new list of the employees */
        // ME.listEmployees();
    }
    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try{
            tx = session.beginTransaction();
            Libro employee = new Libro(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
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
            List employees = session.createQuery("FROM Libro").list();
            for (Iterator iterator =
                 employees.iterator(); iterator.hasNext();){
                Libro libro = (Libro) iterator.next();
                System.out.print("First Name: " + libro.getFirstName());
                System.out.print("  Last Name: " + libro.getLastName());
                System.out.println("  Salary: " + libro.getSalary());
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
    public void updateEmployee(Integer LibroID, int salary ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Libro libro =
                    (Libro)session.get(Libro.class, LibroID);
            libro.setSalary( salary );
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
    public void deleteEmployee(Integer LibroID){
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