package engine.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deven on 3/20/2018.
 * Last edited on 4/18/18.
 */

public abstract class System {
    private static List<System> systemList = new ArrayList<>();
    private Class<? extends Component>[] componentsRequired;
    private List<Entity> relevantEntities;

    public System(Class<? extends Component>... componentsRequired) {
        systemList.add(this);
        this.componentsRequired = componentsRequired;
        relevantEntities = new ArrayList<>();
        calculateRelevantEntities();
    }

    private void calculateRelevantEntities() {
        relevantEntities.clear();
        for (Entity e : Entity.getEntityList()) {
            boolean failed = false;
            for (Class<? extends Component> c : componentsRequired) {
                if (!e.hasComponent(c)) {
                    failed = true;
                    break;
                }
            }
            if (failed) {
                continue;
            }
            relevantEntities.add(e);
        }
    }

    public void delete() {
        systemList.remove(this);
    }

    protected static void recalculateRelevantEntitiesForAllSystems() {
        for (System system : systemList) {
            system.calculateRelevantEntities();
        }
    }

    public List<Entity> getRelevantEntities() {
        return relevantEntities;
    }
}
