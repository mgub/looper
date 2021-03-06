package computer.clay.looper;

import android.graphics.Point;

import java.util.ArrayList;

public class Perspective {

    private Clay clay = null;

    public static float DEFAULT_SCALE_FACTOR = 1.0f;

    private Point position = new Point ();
    private float scaleFactor = DEFAULT_SCALE_FACTOR;

    private ArrayList<LoopConstruct> loopConstructs = new ArrayList<LoopConstruct> ();
    private ArrayList<BehaviorConstruct> behaviorConstructs = new ArrayList<BehaviorConstruct> ();

//    public Point startAnglePoint = null;
//    public Point spanPoint = null;
//    public int startAngle = 0;
//    public int span = 0;

    public Perspective (Clay clay) {
        super();

        this.clay = clay;

        this.position.set (0, 0);
        this.scaleFactor = DEFAULT_SCALE_FACTOR;
    }

    public Clay getClay () {
        return this.clay;
    }

    public void setPosition (int x, int y) {
        position.set (x, y);
    }

    public Point getPosition () {
        return this.position;
    }

    public void moveBy (int xOffset, int yOffset) {
        position.offset (xOffset, yOffset);
    }

    public void setScaleFactor (float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getScaleFactor () {
        return this.scaleFactor;
    }

    /**
     * Checks if the perspective has a construct corresponding to the specified loop.
     *
     * @param loop
     * @return
     */
    public boolean hasLoopConstruct (Loop loop) {
        for (LoopConstruct loopConstruct : this.loopConstructs) {
            if (loopConstruct.getLoop () == loop) {
                return true;
            }

        }
        return false;
    }

    public boolean hasLoopConstructs () {
        return (this.loopConstructs.size () > 0);
    }

    public LoopConstruct createLoopConstruct (Unit unit) {
        LoopConstruct loopConstruct = new LoopConstruct (this, unit);
        this.loopConstructs.add (loopConstruct);
        return loopConstruct;
    }

    public ArrayList<LoopConstruct> getLoopConstructs () {
        return this.loopConstructs;
    }

    public void addBehaviorConstruct (BehaviorConstruct behaviorConstruct) {
        if (!this.behaviorConstructs.contains (behaviorConstruct)) {
            this.behaviorConstructs.add (behaviorConstruct);
        }
    }

    public ArrayList<BehaviorConstruct> getBehaviorConstructs () {
        return this.behaviorConstructs;
    }

    // TODO: createConstruct to hide the creation of constructs.

    // TODO: hasLoopConstruct (Behavior behavior)

    // TODO: hasLoopConstruct (Loop loop, Behavior behavior)
    // TODO: (...) Loop.hasLoopConstruct (Behavior behavior)

    public LoopConstruct getLoopConstruct (Loop loop) {
        for (LoopConstruct loopConstruct : this.loopConstructs) {
            if (loopConstruct.getLoop () == loop) {
                return loopConstruct;
            }

        }
        return null;
    }

    // TODO: getLoopConstruct (Behavior behavior)

    // TODO: getLoopConstruct (Loop loop, Behavior behavior)
    // TODO: (...) Loop.getLoopConstruct (Behavior behavior)

    public LoopConstruct getNearestLoopConstruct (BehaviorConstruct behaviorConstruct) {
        LoopConstruct nearestLoop = null;
        double nearestLoopDistance = Double.POSITIVE_INFINITY;
        for (LoopConstruct loopConstruct : getLoopConstructs ()) {
            if (behaviorConstruct.getDistanceToLoop (loopConstruct) < nearestLoopDistance) {
                nearestLoop = loopConstruct;
            }
        }
        return nearestLoop;
    }

    public BehaviorConstruct createBehaviorConstruct (Point touchPoint) {
        BehaviorConstruct behaviorConstruct = new BehaviorConstruct (this, touchPoint.x, touchPoint.y);
        // <HACK>
        ((AppActivity) getClay ().getPlatformContext()).Hack_PromptForBehaviorSelection(behaviorConstruct);
        // </HACK>
        addBehaviorConstruct (behaviorConstruct);
        return behaviorConstruct;
    }
}
