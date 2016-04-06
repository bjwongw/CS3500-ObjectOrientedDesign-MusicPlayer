package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse input.
 */
public class MouseHandler implements MouseListener {
  private Runnable clicked;
  private Runnable pressed;
  private Runnable released;

  public enum EVENT_TYPE{
    CLICKED, PRESSED, RELEASED
  }

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
