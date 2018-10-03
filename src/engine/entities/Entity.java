package engine.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Deven on 3/17/2018.
 * Last edited on 4/18/18.
 */

public class Entity {
    private static final List<Entity> entityList = new ArrayList<>();
    private static final HashMap<Class, HashMap<UUID, Component>> componentLibrary = new HashMap<>();
    private UUID ID;

    public Entity() {
        ID = UUID.randomUUID();
        entityList.add(this);
    }

    public void addComponent(Component component) {
        synchronized (componentLibrary) {
            HashMap<UUID, Component> componentSection = componentLibrary.get(component.getClass());
            if (componentSection == null) {
                componentSection = new HashMap<>();
                componentLibrary.put(component.getClass(), componentSection);
            }
            componentSection.put(ID, component);
        }
        System.recalculateRelevantEntitiesForAllSystems();
    }

    public void addComponents(Component... components) {
        for (Component c : components) {
            addComponent(c);
        }
    }

    public void removeComponent(Component component) {
        synchronized (componentLibrary) {
            HashMap<UUID, Component> componentSection = componentLibrary.get(component.getClass());
            if (componentSection == null) {
                return;
            }
            componentSection.remove(ID);
        }
        System.recalculateRelevantEntitiesForAllSystems();
    }

    public void removeComponents(Component... components) {
        for (Component c : components) {
            synchronized (componentLibrary) {
                HashMap<UUID, Component> componentSection = componentLibrary.get(c.getClass());
                if (componentSection == null) {
                    return;
                }
                componentSection.remove(ID);
            }
        }
        System.recalculateRelevantEntitiesForAllSystems();
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        HashMap<UUID, Component> componentSection = componentLibrary.get(componentClass);
        if (componentSection != null) {
            return (T) componentSection.get(ID);
        }
        return null;
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return getComponent(componentClass) != null;
    }

    public void delete() {
        entityList.remove(this);
        synchronized (componentLibrary) {
            for (Class c : componentLibrary.keySet()) {
                componentLibrary.get(c).remove(this.getID());
            }
        }
        System.recalculateRelevantEntitiesForAllSystems();
    }

    public static List<Entity> getAllEntitiesWithComponent(Class<? extends Component> componentClass) {
        List<Entity> entitiesWithComponent = new ArrayList<>();
        for (Entity e : entityList) {
            if (e.hasComponent(componentClass)) {
                entitiesWithComponent.add(e);
            }
        }
        return entitiesWithComponent;
    }

    public static void clearEntities() {
        entityList.clear();
        componentLibrary.clear();
    }

    public UUID getID() {
        return ID;
    }

    protected static List<Entity> getEntityList() {
        return entityList;
    }
}
