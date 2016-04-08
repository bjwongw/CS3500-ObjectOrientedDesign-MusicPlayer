package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse input
 */
public class MouseHandler implements MouseListener {
  private Runnable clicked;
  private Runnable pressed;
  private Runnable released;

  /**
   * The types of events that a mouse could have
   */
  public enum EVENT_TYPE{
    CLICKED, PRESSED, RELEASED
  }

  /**
   * Adds a handler to this MouseHandler, determining what to do when the given EVENT_TYPE is
   * called.
   *
   * @param t the MouseHandler event type to map
   * @param r the Runnable to call when the given event is conducted
   */
  public void addHandler(MouseHandler.EVENT_TYPE t, Runnable r) {
    if(t == null || r == null) throw new IllegalArgumentException("Illegal arguments!");

    switch(t) {
      case CLICKED:
        this.clicked = r;
        break;
      case PRESSED:
        this.pressed = r;
        break;
      case RELEASED:
        this.released = r;
        break;
      default:
        throw new RuntimeException("Now how'd ya do this?");
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if(!(clicked == null)) {
      clicked.run();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if(!(pressed == null)) {
      pressed.run();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if(!(released == null)) {
      released.run();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
