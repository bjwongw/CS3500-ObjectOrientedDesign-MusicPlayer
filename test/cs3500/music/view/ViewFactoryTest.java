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
    ViewFactory.constructView("Not what you want");
  }

  /**
   * Tests for the construct method.
   */
  @Test
  public void correctClassTest() {
    assertTrue(ViewFactory.constructView("console") instanceof ConsoleViewImpl);
    assertTrue(ViewFactory.constructView("midi") instanceof MidiView);
    assertTrue(ViewFactory.constructView("gui") instanceof GuiViewImpl);
  }
}
