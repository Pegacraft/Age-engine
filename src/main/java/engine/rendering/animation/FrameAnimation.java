package engine.rendering.animation;

import engine.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import static engine.rendering.Graphics.g;

/**
 * Use this class to create frame animations
 */
public class FrameAnimation extends Entity {
    private final int frameTime;
    private ArrayList<BufferedImage> frames = new ArrayList<>();
    private final ArrayList<FrameAnimationInstance> animationInstances = new ArrayList<>();
    private final ArrayList<FrameAnimationInstance> loopInstances = new ArrayList<>();


    /**
     * @param frameTime the time in ticks that a single image will be shown
     * @param frames the frames the animation consists of
     */
    public FrameAnimation(int frameTime, BufferedImage... frames) {
        this.frameTime = frameTime;
        this.frames.addAll(Arrays.asList(frames));
    }

    /**
     * @param frameTime the time in ticks that a single image will be shown
     * @param frames the frames the animation consists of
     */
    public FrameAnimation(int frameTime, ArrayList<BufferedImage> frames) {
        this.frameTime = frameTime;
        this.frames = frames;
    }


    @Override
    public void init() {
    }

    @Override
    public void logicLoop() {
    }


    @Override
    public void renderLoop() {
        for (FrameAnimationInstance animationInstance : animationInstances) {
            if (animationInstance.isFinished())
                removeObject(animationInstance);
        }
        animationInstances.removeIf(FrameAnimationInstance::isFinished);
    }

    /**
     * Plays the animation once.
     * @param x the x position
     * @param y the y position
     * @param width the width of the animation
     * @param height the height of the animation
     */
    public void play(int x, int y, int width, int height) {
        FrameAnimationInstance anim = new FrameAnimationInstance(frameTime, frames, x, y, width, height);
        animationInstances.add(anim);
        addObject(anim);
    }

    /**
     * Loops an animation.
     * @param x the x position
     * @param y the y position
     * @param width the width of the animation
     * @param height the height of the animation
     */
    public void loop(int x, int y, int width, int height) {
        FrameAnimationInstance anim = new FrameAnimationInstance(frameTime, frames, x, y, width, height);
        loopInstances.add(anim);
        addObject(anim);
    }

    /**
     * Stops all currently playing animations
     */
    public void stopAll() {
        for (FrameAnimationInstance animationInstance : animationInstances) {
            removeObject(animationInstance);
        }
        for (FrameAnimationInstance animationInstance : loopInstances) {
            removeObject(animationInstance);
        }
        animationInstances.clear();
        loopInstances.clear();
    }

    /**
     * Stops a single looping animation
     * @param index the index of the animation
     * @return returns if deleting was a success
     */
    public boolean stopLoop(int index) {
        try {
            removeObject(loopInstances.get(index));
            loopInstances.remove(loopInstances.get(index));
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * Stops all looping animations
     */
    public void stopLoop() {
        for (FrameAnimationInstance animationInstance : loopInstances) {
            removeObject(animationInstance);
        }
        loopInstances.clear();
    }
}

class FrameAnimationInstance extends Entity {
    private final int frameTime;
    private final ArrayList<BufferedImage> frames;

    public FrameAnimationInstance(int frameTime, ArrayList<BufferedImage> frames, int x, int y, int width, int height) {
        this.frameTime = frameTime;
        this.frames = frames;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    int timer = 0;
    int currentFrame = 0;
    boolean isFinished = false;

    @Override
    public void renderLoop() {
        g.drawImage(frames.get(currentFrame), x, y, width, height, null);

        if (timer > frameTime) {
            if (currentFrame == frames.size() - 1) {
                currentFrame = 0;
                isFinished = true;
            } else
                currentFrame++;
            timer = 0;
        }
        timer++;
    }

    boolean isFinished() {
        return isFinished;
    }
}
