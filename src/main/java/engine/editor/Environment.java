package engine.editor;

import engine.Entity;
import engine.Game;
import engine.editor.menu.Sprite;
import engine.listeners.MouseButtons;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

public class Environment implements Entity {
    private final Map<Integer, Sprite> entityList = new ConcurrentHashMap<>();
    private String path;

    /**
     * save the environment to a given path
     *
     * @param path the path to save to
     */
    public void saveEnv(String path) {
        this.path = path;
        try (FileWriter fw = new FileWriter(path)) {
            for (Sprite e : entityList.values()) {
                fw.write(format("[%d]%n", e.hashCode()));
                fw.write(format("Pos=%d,%d%n", e.getPos().x, e.getPos().y));
                fw.write(format("Image=%s%n", e.getSpritePath()));
                fw.write(format("Scale=%s%n", e.getScale()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * add a sprite to the environment, as an id, it's hashCode will be uesd
     *
     * @param sprite the sprite to be added
     */
    public void add(Sprite sprite) {
        entityList.put(sprite.hashCode(), sprite);
    }

    /**
     * load an environment.ini file using it's path
     *
     * @param path the path pointing to said file
     */
    public void loadEnv(String path) {
        this.path = path;
        entityList.clear();
        String[] content = new String[0];
        try (FileReader fr = new FileReader(path)) {
            int c;
            StringBuilder temp = new StringBuilder();
            while ((c = fr.read()) != -1) {
                temp.append((char) c);
            }
            content = temp.toString().replaceAll("\r", "").split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (content.length == 0) return;

        int id = 0;
        int x = 0;
        int y = 0;
        double scale = 0;
        String spritePath = "";
        for (String line : content) {
            if (line.startsWith("[")) {
                if (id != 0)
                    entityList.put(id, new Sprite(x, y, scale, spritePath, Game.getScene("EditScene")));
                id = Integer.parseInt(line.replaceAll("[\\[\\]]", " ").trim());
            }

            if (!line.contains("=")) continue;
            String key = line.split("=")[0].trim();
            String value = line.split("=")[1].trim();
            switch (key) {
                case "Pos":
                    x = Integer.parseInt(value.split(",")[0]);
                    y = Integer.parseInt(value.split(",")[1]);
                    break;
                case "Image":
                    spritePath = value;
                    break;
                case "Scale":
                    scale = Double.parseDouble(value);
                    break;
                default:
                    System.out.println("invalid value in line " + (Arrays.asList(content).indexOf(line) + 1));
            }
        }
    }

    @Override
    public void init() {
        entityList.clear();
    }

    @Override
    public void logicLoop() {
        entityList.values().forEach(e -> {
            if (e.delete) Game.getScene("EditScene")
                    .mouseListener.deleteEvent(MouseButtons.RIGHT_DOWN, e.onClickEvent);
        });
        entityList.values().removeIf(sprite -> sprite.delete);
        entityList.values().forEach(Sprite::logicLoop);
    }

    @Override
    public void renderLoop() {
        entityList.values().forEach(Sprite::renderLoop);
    }

    /**
     * use this to get direct interaction to a sprite if you need to manipulate it via code
     *
     * @param id the id of said object (see the <i>env</i>.ini file)
     * @return the wanted object
     */
    public Sprite getSprite(int id) {
        return entityList.get(id);
    }

    public void reload() {
        if (path != null)
            loadEnv(path);
    }
}
