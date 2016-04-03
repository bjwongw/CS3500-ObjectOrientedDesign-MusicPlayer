package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link KeyboardHandler}
 */
public class KeyboardHandlerTest {

  KeyboardHandler h = new KeyboardHandler();

  class Fun1 implements Runnable {
    int counter = 0;

    @Override
    public void run() {
      this.counter += 1;
    }
  }

  @Test
  public void keyTypedTest() {
    Fun1 f = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, 31, f);
    assertEquals(0, f.counter);
    h.keyTyped(new KeyEvent(new JPanel(), 3, 3, 3, 31, 'a', 3));
    assertEquals(1, f.counter);
    h.keyTyped(new KeyEvent(new JPanel(), 3, 3, 3, 32, 'a', 3));
    assertEquals(1, f.counter);

    Fun1 f2 = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, 31, f2);
    assertEquals(1, f.counter);
    assertEquals(0, f2.counter);
    h.keyTyped(new KeyEvent(new JPanel(), 3, 3, 3, 31, 'a', 3));
    assertEquals(1, f.counter);
    assertEquals(1, f2.counter);

  }

  @Test
  public void keyPressedTest() {
    Fun1 f = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, 31, f);
    assertEquals(0, f.counter);
    h.keyPressed(new KeyEvent(new JPanel(), 3, 3, 3, 31, 'a', 3));
    assertEquals(1, f.counter);
    h.keyPressed(new KeyEvent(new JPanel(), 3, 3, 3, 32, 'a', 3));
    assertEquals(1, f.counter);

    Fun1 f2 = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, 31, f2);
    assertEquals(1, f.counter);
    assertEquals(0, f2.counter);
    h.keyPressed(new KeyEvent(new JPanel(), 3, 3, 3, 31, 'a', 3));
    assertEquals(1, f.counter);
    assertEquals(1, f2.counter);
  }

  @Test
  public void keyReleasedTest() {
    Fun1 f = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, 31, f);
    assertEquals(0, f.counter);
    h.keyReleased(new KeyEvent(new JPanel(), 3, 3, 3, 31, 'a', 3));
    assertEquals(1, f.counter);
    h.keyReleased(new KeyEvent(new JPanel(), 3, 3, 3, 32, 'a', 3));
    assertEquals(1, f.counter);

    Fun1 f2 = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, 31, f2);
    assertEquals(1, f.counter);
    assertEquals(0, f2.counter);
    h.keyReleased(new KeyEvent(new JPanel(), 3, 3, 3, 31, 'a', 3));
    assertEquals(1, f.counter);
    assertEquals(1, f2.counter);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badInputTest1() {
    Fun1 f = new Fun1();
    h.addHandler(null, 31, f);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badInputTest2() {
    Fun1 f = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, 31, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badInputTest3() {
    Fun1 f = new Fun1();
    h.addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, -1, f);
  }


}
