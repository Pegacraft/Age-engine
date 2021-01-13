package engine;

import engine.mechanics.EntityList;
import engine.rendering.Graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    public int x = 0, y = 0;
    public int width = 0, height = 0;
    public double rotation = 0;
    public Point rotatePos;
    public List<Entity> objectList = new ArrayList<>();

    /**
     * This Method will be called once on creation of the object
     */
    public abstract void init();

    /**
     * The loop for the object logic
     */
    public abstract void logicLoop();

    /**
     * The loop for the object render
     */
    public abstract void renderLoop();

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void anchorToCam(int offsetX, int offsetY){
        x = offsetX + -Graphics.getCamPos().x;
        y = offsetY + -Graphics.getCamPos().y;
    }

    /**
     * @return Returns the Object list of an scene. Its not recommended to use, because there are simpler ways
     * e. g. the <code>scene.addObject</code> method, or the <code>scene.getObject</code> method.
     * @see Scene#addObject(Entity)
     * @see Scene#removeObject(Entity)
     * @see Scene#getObject(int)
     */
    public List<Entity> getObjectList() {
        return objectList;
    }

    /**
     * @param obj The object to be added.
     */
    public void addObject(Entity obj) {
        if (obj == null)
            throw new NullPointerException("The object can not be null");
        obj.init();
        objectList.add(obj);
    }

    /**
     * @param obj The object to be removed
     */
    protected void removeObject(Entity obj) {
        objectList.remove(obj);
    }

    /**
     * @param index The position in the ObjectList
     * @return The wanted object
     */
    protected Entity getObject(int index) {
        return objectList.get(index);
    }

    protected void replaceObject(Entity obj, Entity newObj){
        if (obj == null || newObj == null)
            throw new NullPointerException("The object can not be null");
        newObj.init();
        objectList.set(objectList.indexOf(obj), newObj);
    }

    /**
     * Use this method to make an entity face an point via rotating it around the set rotationPosition.
     *
     * @param p The point you want it to face.
     */
    public void rotateTo(Point p) {
        if (rotatePos == null)
            rotation = -Math.atan2(x - p.x, y - p.y);
        else
            rotation = Math.toRadians(360) - Math.atan2(p.x - rotatePos.x, p.y - rotatePos.y);
    }

    /**
     * Use this method to move the entity to a certain point. (Entity position is the rotate position)
     * @param p The point you want the entity to be moved to.
     * @param speed The speed you want it to be moved. (I recommend using values over 3 because with lower values, the result would be greatly impacted)
     */
    public void moveTo(Point p, int speed) {
        if (rotatePos == null)
            rotatePos = new Point(x, y);
        float len = (float) Math.sqrt(Math.pow(rotatePos.x - p.x, 2) + Math.pow(rotatePos.y - p.y, 2));
        if (speed < len) {
            double angle = Math.atan2(p.y - (double) rotatePos.y, p.x - (double) rotatePos.x);
            x += (Math.cos(angle) * speed);
            y += (Math.sin(angle) * speed);
            rotatePos.x += (Math.cos(angle) * speed);
            rotatePos.y += (Math.sin(angle) * speed);
        }
    }
}
