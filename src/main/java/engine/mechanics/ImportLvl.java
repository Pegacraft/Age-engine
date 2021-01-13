package engine.mechanics;

import engine.Entity;
import engine.Scene;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class ImportLvl {
    static String type, className, x, y, width, height, params;
    static Entity entity;
    static Scene scene;
    static EntityList entityList = new EntityList();

    public static EntityList importLvl(String path, Scene scene) {
        ImportLvl.scene = scene;
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
                if (type.matches("BUTTON"))
                    typeButton();

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
        String borderColor, textType, fontSize, maxValue, setText, setMatcher;
        TextBox textBox = new TextBox(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height), scene);

        //get values for customisation
        borderColor = params.split(",")[0];
        textType = params.split(",")[1];
        fontSize = params.split(",")[2];
        maxValue = params.split(",")[3];
        setText = params.split(",")[4];
        setMatcher = params.split(",")[5];
        if (!borderColor.matches("NONE"))
            textBox.setBorderColor(new Color(Integer.parseInt(borderColor.replace("#", ""), 16)));
        if (!textType.matches("NONE"))
            textBox.setTextType(textType);
        if (!fontSize.matches("NONE"))
            textBox.setFontSize(Integer.parseInt(fontSize));
        if (!maxValue.matches("NONE"))
            textBox.setMaxValue(Integer.parseInt(maxValue));
        if (!setText.matches("NONE"))
            textBox.setText(setText);
        if (!setMatcher.matches("NONE"))
            textBox.setMatcher(setMatcher);

        entityList.add(textBox);
    }

    static private void typeButton() {
        String color, hoverColor, text, font, fontSize, textColor;
        Button button = new Button(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(width), Integer.parseInt(height), scene);

        //get values for customisation
        color = params.split(",")[0];
        hoverColor = params.split(",")[1];
        text = params.split(",")[2];
        font = params.split(",")[3];
        fontSize = params.split(",")[4];
        textColor = params.split(",")[5];
        if (!color.matches("NONE"))
            button.setColor(new Color(Integer.parseInt(color.replace("#", ""), 16)));
        if (!hoverColor.matches("NONE"))
            button.setHoverColor(new Color(Integer.parseInt(hoverColor.replace("#", ""), 16)));
        if (!text.matches("NONE"))
            button.setText(text);
        if (!font.matches("NONE"))
            button.setFont(font);
        if (!fontSize.matches("NONE"))
            button.setFontSize(Integer.parseInt(fontSize));
        if (!textColor.matches("NONE"))
            button.setTextColor(new Color(Integer.parseInt(textColor.replace("#", ""), 16)));

        entityList.add(button);
    }
}
