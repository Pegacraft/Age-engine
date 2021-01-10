package engine;

import java.awt.*;

public abstract class Entity {
    public int x = 0, y = 0;
    public int width = 0, height = 0;
    public double rotation = 0;
    public Point rotatePos;

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
