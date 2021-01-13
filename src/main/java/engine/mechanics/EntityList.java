package engine.mechanics;

import engine.Entity;

import java.util.ArrayList;

public class EntityList extends Entity {

    ArrayList<Entity> entityList = new ArrayList<>();

    /**
     * This is an entity list. It can be used like a <code>ArrayList</code>. The advantage is, that you can add it to
     * the entity list of a scene.
     */

    @Override
    public void init() {
        entityList.forEach(Entity::init);
    }

    @Override
    public void logicLoop() {
        entityList.forEach(Entity::logicLoop);
    }

    @Override
    public void renderLoop() {
        entityList.forEach(Entity::renderLoop);
    }

    /**
     * Adds an entity to the Entity List.
     *
     * @param entity The entity you want to add.
     */
    public void add(Entity entity) {
        entityList.add(entity);
    }

    public Entity get(int index) {
        return entityList.get(index);
    }

    public Entity get(Entity e) {
        return entityList.get(entityList.indexOf(e));
    }

    /**
     * Removes an entity from the entity list.
     *
     * @param index The index of the entity you want to remove.
     */
    public void remove(int index) {
        entityList.remove(index);
    }

    /**
     * Use this method to talk directly to the <code>ArrayList</code> attached to the entity list.
     *
     * @return The <code>ArrayList</code> attached to the entity list.
     */
    public ArrayList<Entity> getEntityList() {
        return entityList;
    }
}
