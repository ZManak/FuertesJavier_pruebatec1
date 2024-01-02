
package controlsys;

import controlsys.exceptions.NonexistentEntityException;
import controlsys.exceptions.PreexistingEntityException;
import controlsys.exceptions.InvalidDataException;
import empleados.models.Employee;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
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
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void create(Employee empleado)
            throws InvalidDataException, PreexistingEntityException, NonexistentEntityException {
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
    public void edit(Employee empleado) throws NonexistentEntityException, InvalidDataException {
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
                try {
                    if (findEmployee(id) == null) {
                        throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                    }
                } catch (Exception e) {
                    throw new InvalidDataException();
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
            System.out.println("Employee deleted");
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
     * @throws Exception
     */
    public List<Employee> findEmployeeEntities() throws Exception {
        try {
            return findEmpleadoEntities(true, -1, -1);
        } catch (InvalidDataException e) {
            throw new InvalidDataException();
        } catch (NonexistentEntityException e) {
            throw new NonexistentEntityException("Couldn't find employees. Params: maxResults: -1, firstResult: -1");
        } catch (Exception e) {
            throw new Exception("Couldn't find employees. Params: maxResults: -1, firstResult: -1");
        }
    }

    /**
     * Method to find all the employees, it creates a query and returns the result
     * list with a limit and an offset
     * 
     * @param maxResults
     * @param firstResult
     * @return
     * @throws Exception
     */
    public List<Employee> findEmpleadoEntities(int maxResults, int firstResult)
            throws Exception {
        try {
            return findEmpleadoEntities(false, maxResults, firstResult);
        } catch (InvalidDataException e) {
            throw new InvalidDataException();
        } catch (Exception e) {
            throw new Exception("Couldn't find employees. Params: maxResults: " + maxResults + ", firstResult: "
                    + firstResult + " out of bounds.");
        }
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
    private List<Employee> findEmpleadoEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            cq.select(root);
            TypedQuery<Employee> query = em.createQuery(cq); // Update the type of Query to TypedQuery<Employee>
            if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
            return query.getResultList(); // Cast the result to List<Employee>
        } catch (Exception e) {
            throw new Exception("Couldn't find employees. Params: maxResults: " + maxResults + ", firstResult: "
                    + firstResult + " out of bounds.");
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
    public Employee findEmployee(int id) throws NonexistentEntityException {
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
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> rt = cq.from(Employee.class);
            cq.multiselect(cb.count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
