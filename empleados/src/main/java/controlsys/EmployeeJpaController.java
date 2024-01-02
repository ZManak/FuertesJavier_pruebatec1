
package controlsys;

import controlsys.exceptions.NonexistentEntityException;
import empleados.models.Employee;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * This class is in charge of the CRUD operations of the employee entity
 * 
 * @author Manak
 */
public class EmployeeJpaController implements Serializable {

    /**
     * Constructor of the class
     * 
     * @param emf
     */
    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Default constructor of the class
     */
    public EmployeeJpaController() {
        emf = Persistence.createEntityManagerFactory("controlSysPersistence");
    }

    /**
     * Entity manager factory, used to create the entity manager and perform the
     * CRUD operations
     */
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Method to create an employee, it receives an employee object and persists it,
     * then tries to commit the transaction
     * 
     * @param empleado
     */
    public void create(Employee empleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method to edit an employee, it receives an employee object and merges it,
     * then tries to commit the transaction
     * 
     * @param empleado
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Employee empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            empleado = em.merge(empleado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = empleado.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employeee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method to destroy an employee, it receives an employee id and tries to find
     * it, then tries to destroy it
     * 
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee empleado;
            try {
                empleado = em.getReference(Employee.class, id);
                empleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Method to find all the employees, it creates a query and returns the result
     * list
     * 
     * @return
     */
    public List<Employee> findEmployeeEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    /**
     * Method to find all the employees, it creates a query and returns the result
     * list with a limit and an offset
     * 
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Employee> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    /**
     * Method to find all the employees, it creates a query and returns the result
     * list with a limit and an offset
     * 
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<Employee> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Method to find an employee, it receives an employee id and tries to find it
     * 
     * @param id
     * @return
     */
    public Employee findEmployee(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Method to find the number of employees, it creates a query and returns the
     * result list
     * 
     * @return
     */
    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
