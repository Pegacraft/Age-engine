package engine.mechanics;

import engine.Entity;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class ImportLvl {

    public static EntityList importLvl(String path) {
        Scanner reader = null;
        EntityList entityList = new EntityList();
        try {
            File f = new File((ImportLvl.class.getResource(path)).getPath());
            reader = new Scanner(f);

            while (reader.hasNextLine()) {
                Entity entity;
                String input = reader.nextLine();
                String className, x, y, width, height, params;
                className = input.split("[~]")[0];
                x = input.split("[~]")[1];
                y = input.split("[~]")[2];
                width = input.split("[~]")[3];
                height = input.split("[~]")[4];
                params = input.split("[~]")[5];

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
        } catch (FileNotFoundException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return entityList;
    }
}
