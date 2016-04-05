package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.MouseEvent;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link MouseHandler}
 */
public class MouseHandlerTest {

  MouseHandler h = new MouseHandler();

  class Fun1 implements Runnable {
    int counter = 0;

    @Override
    public void run() {
      this.counter += 1;
    }
  }

  @Test
  public void mouseClickedTest() {
    Fun1 f = new Fun1();
    h.addHandler(MouseHandler.EVENT_TYPE.CLICKED, f);
    assertEquals(0, f.counter);
    h.mouseClicked(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    assertEquals(1, f.counter);
    h.mousePressed(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseReleased(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseEntered(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseExited(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    assertEquals(1, f.counter);
  }

  @Test
  public void mousePressedTest() {
    Fun1 f = new Fun1();
    h.addHandler(MouseHandler.EVENT_TYPE.PRESSED, f);
    assertEquals(0, f.counter);
    h.mousePressed(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    assertEquals(1, f.counter);
    h.mouseClicked(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseReleased(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseEntered(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseExited(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    assertEquals(1, f.counter);
  }

  @Test
  public void mouseReleasedTest() {
    Fun1 f = new Fun1();
    h.addHandler(MouseHandler.EVENT_TYPE.RELEASED, f);
    assertEquals(0, f.counter);
    h.mouseReleased(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    assertEquals(1, f.counter);
    h.mousePressed(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseClicked(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseEntered(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    h.mouseExited(new MouseEvent(new JPanel(), 3, 3, 3, 3, 3, 4, true, 3));
    assertEquals(1, f.counter);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badInputTest1() {
    Fun1 f = new Fun1();
    h.addHandler(null, f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badInputTest2() {
    Fun1 f = new Fun1();
    h.addHandler(MouseHandler.EVENT_TYPE.CLICKED, null);
  }


}
