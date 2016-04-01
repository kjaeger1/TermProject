package restaurant;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ItemFacade extends AbstractFacade<Item> {

    @PersistenceContext(unitName = "TermProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemFacade() {
        super(Item.class);
    }
    
    @Override
    public List<Item> findAll() {
        return em.createNamedQuery("Item.findAll").getResultList();
    }
    
    public List<Item> findAllActive() {
        return em.createNamedQuery("Item.findByActive").setParameter("active", true).getResultList();
    }
    
    public Item firstActive() {
        return findAllActive().get(0);
    }
}
