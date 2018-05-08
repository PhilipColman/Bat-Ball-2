/**
 * BatBall2
 * Created by Philip on 08/09/2016 at 21:59.
 */
package gameEngine.io.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

    private static boolean RMB = false;
    private static boolean LMB = false;
    private static boolean MMB = false;
    private static boolean MOUSE_OVER_WINDOW = false;
    private static int x, y;


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                LMB = true;
                break;
            case MouseEvent.BUTTON2:
                MMB = true;
                break;
            case MouseEvent.BUTTON3:
                RMB = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                LMB = false;
                break;
            case MouseEvent.BUTTON2:
                MMB = false;
                break;
            case MouseEvent.BUTTON3:
                RMB = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        MOUSE_OVER_WINDOW = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        MOUSE_OVER_WINDOW = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public static boolean RMBIsDown() {
        return RMB;
    }

    public static boolean LMBIsDown() {
        return LMB;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static boolean MMBIsDown() {
        return MMB;
    }

    public static void setLMB(boolean value){
        LMB = value;
    }
}
