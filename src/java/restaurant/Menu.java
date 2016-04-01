package restaurant;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

@Named(value = "menu")
@SessionScoped
public class Menu implements Serializable {

    @EJB
    ItemFacade itemFacade;

    private Item item;
    private Item newItem;
    private String mode;

    @PostConstruct
    public void init() {
        item = itemFacade.firstActive();
        newItem = new Item();
        mode = "";
    }

    public List<Item> getAll() {
        return itemFacade.findAll();
    }

    public Item get(int id) {
        return itemFacade.find(id);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getNewItem() {
        return newItem;
    }

    public void setNewItem(Item newItem) {
        this.newItem = newItem;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void add() {
        itemFacade.create(newItem);
        item = newItem;
        mode = "";
    }

    public void del() {
        itemFacade.remove(item);
        mode = "";
    }

    public void edit(Item item) {
        this.item = item;
        mode = "edit";
    }

    public void save() {
        itemFacade.edit(item);
        mode = "";
    }

    public String view() {
        return "/menu";
    }

    public void clear() {
        newItem = null;
    }

    public List<Item> getAllActive() {
        return itemFacade.findAllActive();
    }
}
