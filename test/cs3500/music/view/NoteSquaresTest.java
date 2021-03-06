package cs3500.music.view;

import org.junit.Test;

import java.awt.*;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the NoteSquares class
 */
public class NoteSquaresTest {

  /**
   * Test for the method setNoteColor when given a negative index
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetNoteColor_negativeIndex() {
    NoteSquares noteSquare = new NoteSquares(4);
    noteSquare.setNoteColor(-1, Color.BLACK);
  }

  /**
   * Test for the method setNoteColor when given an index that is too high
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testSetNoteColor_indexTooHigh() {
    NoteSquares noteSquare = new NoteSquares(4);
    noteSquare.setNoteColor(4, Color.BLACK);
  }

  /**
   * Test for the method setNoteColor when given a null Color
   */
  @Test(expected = IllegalArgumentException.class)
  public void testSetNoteColor_nullInput() {
    NoteSquares noteSquare = new NoteSquares(4);
    noteSquare.setNoteColor(0, null);
  }

  /**
   * Test for the method getPreferred size
   */
  @Test
  public void testGetPreferredSize() throws Exception {
    JPanel noteSquare = new NoteSquares(4);
    assertEquals(new Dimension(NoteSquares.PREF_W * 4, NoteSquares.PREF_H),
            noteSquare.getPreferredSize());
  }
}
