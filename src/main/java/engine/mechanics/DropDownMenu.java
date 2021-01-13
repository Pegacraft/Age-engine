package engine.mechanics;

import engine.Entity;
import engine.Scene;
import engine.listeners.MouseButtons;
import engine.rendering.Graphics;

public class DropDownMenu extends Entity {
    private Button menuButton;
    private EntityList dropDown = new EntityList();
    public boolean isOpen = false;
    private Scene scene;

    public DropDownMenu(Button menuButton) {
        this.x = menuButton.x;
        this.y = menuButton.y;
        this.menuButton = menuButton;
        this.scene = menuButton.scene;

    }

    @Override
    public void init() {
        menuButton.addEvent(MouseButtons.LEFT_DOWN, e -> {
            isOpen = !isOpen;

            if (isOpen) {
                for (int i = 0; i < size(); i++) {
                    ((Button)dropDown.get(i)).isEventEnabled = true;
                }
                scene.addObject(dropDown);
            }
            else {
                for (int i = 0; i < size(); i++) {
                    ((Button)dropDown.get(i)).isEventEnabled = false;
                }
                scene.getObjectList().remove(dropDown);
            }
        });

        //add objects
        addObject(menuButton);
    }

    @Override
    public void logicLoop() {
        menuButton.x = x;
        menuButton.y = y;
        if (dropDown.getEntityList().size() <= 0)
            return;
        int totalSize = y + menuButton.height + Graphics.getCamPos().y;
        for (int i = 0; i < dropDown.getEntityList().size(); i++) {
            totalSize += dropDown.getEntityList().get(i).height;
        }

        if (totalSize < Graphics.stdHeight) {
            //dropDown Button alignment
            dropDown.getEntityList().get(0).x = x;
            dropDown.getEntityList().get(0).y = y + menuButton.height;
            int offset = y + menuButton.height;

            for (int i = 1; i < dropDown.getEntityList().size(); i++) {
                offset += dropDown.getEntityList().get(i - 1).height;

                dropDown.getEntityList().get(i).x = x;
                dropDown.getEntityList().get(i).y = offset;
            }
        } else {
            dropDown.getEntityList().get(0).x = x;
            dropDown.getEntityList().get(0).y = y - dropDown.getEntityList().get(0).height;
            int offset = y - menuButton.height;

            for (int i = 1; i < dropDown.getEntityList().size(); i++) {
                offset -= dropDown.getEntityList().get(i - 1).height;

                dropDown.getEntityList().get(i).x = x;
                dropDown.getEntityList().get(i).y = offset;
            }
        }
    }

    @Override
    public void renderLoop() {

    }

    public DropDownMenu addDropDownButton(Button btn) {
        dropDown.add(btn);
        return this;
    }

    public DropDownMenu removeDropDownButton(Button btn) {
        dropDown.getEntityList().remove(btn);
        return this;
    }

    public DropDownMenu removeDropDownButton(int index) {
        dropDown.remove(index);
        return this;
    }

    public void deleteEvent(int index, MouseButtons event) {
        ((Button) dropDown.get(index)).deleteEvent(event);
    }

    public void deleteEvent(Button btn, MouseButtons event) {
        ((Button) dropDown.get(btn)).deleteEvent(event);
    }

    public int size() {
        return dropDown.getEntityList().size();
    }

    public void closeMenu() {
        if (isOpen) {
            for (int i = 0; i < size(); i++) {
                ((Button)dropDown.get(i)).isEventEnabled = false;
            }
            scene.getObjectList().remove(dropDown);
            isOpen = false;
        }
    }

    public void openMenu() {
        if (!isOpen) {
            for (int i = 0; i < size(); i++) {
                ((Button)dropDown.get(i)).isEventEnabled = true;
            }
            scene.addObject(dropDown);
            isOpen = true;
        }
    }
}
