package cs3500.music.view;

import org.junit.Test;

import javax.print.attribute.standard.MediaSize;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the ViewFactory class.
 */
public class ViewFactoryTest {

  /**
   * Test for the constructView method. Ensures that you can't just give it any string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badStringTest() {
    ViewFactory.constructView("Not what you want");
  }

  /**
   * Tests for the constructView method
   */
  @Test
  public void testConstructView() {
    assertTrue(ViewFactory.constructView("console") instanceof ConsoleViewImpl);
    assertTrue(ViewFactory.constructView("midi") instanceof MidiView);
    assertTrue(ViewFactory.constructView("gui") instanceof GuiViewImpl);
    assertTrue(ViewFactory.constructGui("gui") instanceof GuiViewImpl);
    assertTrue(ViewFactory.constructGui("composite") instanceof CompositeView);
  }

  /**
   * Test for the constructGui method. Ensures that you can't just give it any string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructGui_badInput() {
    ViewFactory.constructGui("This isn't valid");
  }

  /**
   * Tests for the constructGui method
   */
  @Test
  public void testConstructGui() {
    assertTrue(ViewFactory.constructGui("gui") instanceof GuiViewImpl);
    assertTrue(ViewFactory.constructGui("composite") instanceof CompositeView);
  }

  /**
   * Tests for the constructOther method
   */
  @Test
  public void testConstructOther() {
    assertTrue(ViewFactory.constructOther("composite") instanceof OtherCompositeViewAdapter);
  }
}
