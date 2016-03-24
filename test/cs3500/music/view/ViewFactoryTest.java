package cs3500.music.view;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the ViewFactory class.
 */
public class ViewFactoryTest {

  /**
   * Test for the construct method. Ensures that you can't just give it any string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badStringTest() {
    ViewFactory.construct("Not what you want");
  }

  /**
   * Tests for the construct method.
   */
  @Test
  public void correctClassTest() {
    assertTrue(ViewFactory.construct("console") instanceof ConsoleView);
    assertTrue(ViewFactory.construct("midi") instanceof MidiView);
    assertTrue(ViewFactory.construct("gui") instanceof GuiView);
  }
}
