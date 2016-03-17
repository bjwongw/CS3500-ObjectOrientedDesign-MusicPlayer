package cs3500.music.model.Alex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link Note}
 *
 * Created by Alex on 3/4/2016.
 */
public class NoteTest {

  /**
   * Testing the constructor for throwing an exception on bad input
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructorTest1() {
    new Note(-1, Note.PITCH.A, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTest2() {
    new Note(0, Note.PITCH.A, -1, 0);
  }

  /**
   * Testing the PITCH enum
   */
  @Test
  public void getPaddedRepresentation() {
    assertEquals("  C0 ", Note.PITCH.C.getPaddedRepresentation(0));
    assertEquals(" D#4 ", Note.PITCH.D_SHARP.getPaddedRepresentation(4));
    assertEquals("  A2 ", Note.PITCH.A.getPaddedRepresentation(2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTest3() {
    new Note(0, Note.PITCH.A, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTest4() {
    new Note(0, Note.PITCH.A, 0, -1);
  }

  @Test
  public void constructorTest5() {
    new Note(0, Note.PITCH.A, 0, 0);
  }

  /**
   * Testing {@link Note#compareTo(Note)}
   */
  @Test
  public void compareToTest() {
    int i = 0;
    for (Note.PITCH p : Note.PITCH.values()) {
      //Tests equality maintained
      Note a = new Note(0, p, 0, 0);
      Note b = new Note(0, p, 0, 0);
      assertTrue(a.compareTo(b) == 0);
      assertTrue(b.compareTo(a) == 0);
      //Tests differing octave
      a = new Note(0, p, 0, 0);
      b = new Note(0, p, 1, 0);
      assertTrue(a.compareTo(b) < 0);
      assertTrue(b.compareTo(a) > 0);
      //Tests differing duration
      a = new Note(0, p, 0, 0);
      b = new Note(0, p, 0, 1);
      assertTrue(a.compareTo(b) < 0);
      assertTrue(b.compareTo(a) > 0);
      //Tests differing pitch
      a = new Note(0, p, 0, 0);
      b = new Note(0, Note.PITCH.values()[i + 1], 0, 0);
      assertTrue(a.compareTo(b) < 0);
      assertTrue(b.compareTo(a) > 0);
      i += 1;
      if (i == 11) break;
    }
  }

  /**
   * Testing {@link Note#playsDuring(int)}
   */
  @Test
  public void playsDuringTest() {
    Note a = new Note(10, Note.PITCH.A, 0, 0);
    Note b = new Note(231, Note.PITCH.A, 0, 10);

    assertFalse(a.playsDuring(-1));
    assertFalse(a.playsDuring(9));
    assertTrue(a.playsDuring(10));
    assertFalse(a.playsDuring(11));

    assertFalse(b.playsDuring(-1));
    assertFalse(b.playsDuring(230));
    assertTrue(b.playsDuring(231));
    assertTrue(b.playsDuring(232));
    assertTrue(b.playsDuring(241));
    assertFalse(b.playsDuring(242));
  }


}
