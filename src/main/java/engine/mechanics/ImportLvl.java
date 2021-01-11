package engine.mechanics;

import engine.Entity;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class ImportLvl {
    static String type, className, x, y, width, height, params;
    static Entity entity;
    static EntityList entityList = new EntityList();

    public static EntityList importLvl(String path) {
        Scanner reader = null;
        try {
            File f = new File((ImportLvl.class.getResource(path)).getPath());
            reader = new Scanner(f);

            while (reader.hasNextLine()) {
                String input = reader.nextLine();
                type = input.split("[~]")[0];
                className = input.split("[~]")[1];
                x = input.split("[~]")[2];
                y = input.split("[~]")[3];
                width = input.split("[~]")[4];
                height = input.split("[~]")[5];
                params = input.split("[~]")[6];

                if (type.matches("CUSTOM"))
                    typeCustom();
                if (type.matches("TEXTBOX"))
                    typeTextBox();

            }
        } catch (FileNotFoundException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    static private void typeCustom() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        if (!params.equals("NONE")) {
            Class cl = Class.forName(className);
            Constructor con = cl.getConstructor(String.class);
            entity = (Entity) con.newInstance(params);
            entity.x = Integer.parseInt(x);
            entity.y = Integer.parseInt(y);
            entity.width = Integer.parseInt(width);
            entity.height = Integer.parseInt(height);

        } else {
            Class cl = Class.forName(className);
            Constructor con = cl.getConstructor();
            entity = (Entity) con.newInstance();
            entity.x = Integer.parseInt(x);
            entity.y = Integer.parseInt(y);
            entity.width = Integer.parseInt(width);
            entity.height = Integer.parseInt(height);
        }

        entityList.add(entity);
    }

    static private void typeTextBox() {

    }
}
