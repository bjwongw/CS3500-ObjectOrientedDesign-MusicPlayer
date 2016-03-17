package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Note class
 */
public class NoteTest {
  Note c0 = new Note(Note.Pitch.C, 0, 0, 4);
  Note c4 = new Note(Note.Pitch.C, 4, 3, 1);
  Note cSharp3 = new Note(Note.Pitch.C_SHARP, 3, 2, 2);
  Note cSharp7 = new Note(Note.Pitch.C_SHARP, 7, 10, 3);
  Note d3 = new Note(Note.Pitch.D, 3, 13, 4);
  Note d6 = new Note(Note.Pitch.D, 6, 0, 1);
  Note dSharp0 = new Note(Note.Pitch.D_SHARP, 0, 21, 3);
  Note dSharp9 = new Note(Note.Pitch.D_SHARP, 9, 8, 8);
  Note e1 = new Note(Note.Pitch.E, 1, 0, 1);
  Note e2 = new Note(Note.Pitch.E, 2, 2, 1);
  Note f3 = new Note(Note.Pitch.F, 3, 0, 7);
  Note f5 = new Note(Note.Pitch.F, 5, 5, 5);
  Note fSharp4 = new Note(Note.Pitch.F_SHARP, 4, 6, 2);
  Note fSharp9 = new Note(Note.Pitch.F_SHARP, 9, 6, 2);
  Note g0 = new Note(Note.Pitch.G, 0, 3, 1);
  Note g6 = new Note(Note.Pitch.G, 6, 10, 4);
  Note gSharp5 = new Note(Note.Pitch.G_SHARP, 5, 3, 1);
  Note gSharp7 = new Note(Note.Pitch.G_SHARP, 7, 40, 2);
  Note a8 = new Note(Note.Pitch.A, 8, 32, 2);
  Note a10 = new Note(Note.Pitch.A, 1, 32, 2);
  Note aSharp4 = new Note(Note.Pitch.A_SHARP, 4, 14, 10);
  Note aSharp9 = new Note(Note.Pitch.A_SHARP, 9, 22, 9);
  Note b10 = new Note(Note.Pitch.B, 1, 33, 7);

  /**
   * Ensures that a note cannot be constructed with a negative start time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructor_negativeStartNote() {
    new Note(Note.Pitch.A, 0, -1, 3);
  }

  /**
   * Ensures that a note cannot be constructed with a duration less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructor_beatLessThanOne() {
    new Note(Note.Pitch.B, 2, 0, 0);
  }

  /**
   * Tests for the method getPitch
   */
  @Test
  public void testGetPitch() {
    assertEquals(Note.Pitch.F_SHARP, fSharp4.getPitch());
    assertEquals(Note.Pitch.B, b10.getPitch());
  }

  /**
   * Tests for the method getStart
   */
  @Test
  public void testGetStart() {
    assertEquals(3, c4.getStart());
    assertEquals(2, cSharp3.getStart());
    assertEquals(21, dSharp0.getStart());
    assertEquals(5, f5.getStart());
    assertEquals(6, fSharp4.getStart());
    assertEquals(10, g6.getStart());
    assertEquals(40, gSharp7.getStart());
    assertEquals(33, b10.getStart());
  }

  /**
   * Tests for the method getDuration
   */
  @Test
  public void testGetDuration() {
    assertEquals(4, c0.getDuration());
    assertEquals(3, cSharp7.getDuration());
    assertEquals(1, d6.getDuration());
    assertEquals(8, dSharp9.getDuration());
    assertEquals(1, e1.getDuration());
    assertEquals(7, f3.getDuration());
  }

  /**
   * Tests for the method toString
   */
  @Test
  public void testToString() {
    assertEquals("C0", c0.toString());
    assertEquals("C4", c4.toString());
    assertEquals("C#3", cSharp3.toString());
    assertEquals("C#7", cSharp7.toString());
    assertEquals("D3", d3.toString());
    assertEquals("D6", d6.toString());
    assertEquals("D#0", dSharp0.toString());
    assertEquals("D#9", dSharp9.toString());
    assertEquals("E1", e1.toString());
    assertEquals("E2", e2.toString());
    assertEquals("F3", f3.toString());
    assertEquals("F5", f5.toString());
    assertEquals("F#4", fSharp4.toString());
    assertEquals("F#9", fSharp9.toString());
    assertEquals("G0", g0.toString());
    assertEquals("G6", g6.toString());
    assertEquals("G#5", gSharp5.toString());
    assertEquals("G#7", gSharp7.toString());
    assertEquals("A8", a8.toString());
    assertEquals("A10", a10.toString());
    assertEquals("A#4", aSharp4.toString());
    assertEquals("A#9", aSharp9.toString());
  }

  /**
   * Tests for the method compareTo for equals notes.
   */
  @Test
  public void testCompareTo_equalNotes() {
    assertEquals(0, cSharp3.compareTo(cSharp3));
    assertEquals(0, new Note(Note.Pitch.C_SHARP, 3, 2, 2).compareTo(cSharp3));
    assertEquals(0, cSharp3.compareTo(new Note(Note.Pitch.C_SHARP, 3, 2, 2)));
    assertEquals(0, d3.compareTo(d3));
    assertEquals(0, g6.compareTo(new Note(Note.Pitch.G, 6, 10, 4)));
    assertEquals(0, new Note(Note.Pitch.E, 1, 0, 1).compareTo(e1));
  }

  /**
   * Tests for the method compareTo when the two notes have different pitches.
   */
  @Test
  public void testCompareTo_differentPitch() {
    assertTrue(0 > cSharp3.compareTo(d3));
    assertTrue(0 > dSharp0.compareTo(f3));
    assertTrue(0 > e1.compareTo(e2));
    assertTrue(0 > g0.compareTo(gSharp5));

    assertTrue(0 < b10.compareTo(a8));
    assertTrue(0 < gSharp7.compareTo(cSharp7));
    assertTrue(0 < f3.compareTo(e2));
    assertTrue(0 < fSharp4.compareTo(dSharp0));
  }

  /**
   * Tests for the method compareTo when the two notes have the same pitch, but different start
   * times.
   */
  @Test
  public void testCompareTo_samePitchDifferentStart() {
    assertTrue(0 > c0.compareTo(new Note(Note.Pitch.C, 0, 5, 7)));
    assertTrue(0 > gSharp5.compareTo(new Note(Note.Pitch.G_SHARP, 5, 10, 1)));

    assertTrue(0 < f5.compareTo(new Note(Note.Pitch.F, 5, 3, 2)));
    assertTrue(0 < a10.compareTo(new Note(Note.Pitch.A, 1, 10, 2)));
    assertTrue(0 < aSharp4.compareTo(new Note(Note.Pitch.A_SHARP, 4, 7, 6)));
    assertTrue(0 < dSharp0.compareTo(new Note(Note.Pitch.D_SHARP, 0, 20, 3)));
  }

  /**
   * Tests for the method compareTo when the two notes have the same pitch and start time, but
   * different durations.
   */
  @Test
  public void testCompareTo_samePitchSameStartDifferentDuration() {
    assertTrue(0 > f3.compareTo(new Note(Note.Pitch.F, 3, 32, 4)));
    assertTrue(0 > b10.compareTo(new Note(Note.Pitch.B, 1, 33, 10)));

    assertTrue(0 < c0.compareTo(new Note(Note.Pitch.C, 0, 0, 3)));
    assertTrue(0 < d3.compareTo(new Note(Note.Pitch.D, 3, 13, 2)));
    assertTrue(0 < gSharp7.compareTo(new Note(Note.Pitch.G_SHARP, 7, 40, 1)));
    assertTrue(0 < fSharp4.compareTo(new Note(Note.Pitch.F_SHARP, 4, 6, 1)));
  }

  /**
   * Tests for the method equals.
   */
  @Test
  public void testEquals() {
    assertEquals(true, c0.equals(c0));
    assertEquals(true, new Note(Note.Pitch.C, 0, 0, 4).equals(c0));
    assertEquals(true, c0.equals(new Note(Note.Pitch.C, 0, 0, 4)));
    assertEquals(true, dSharp0.equals(new Note(Note.Pitch.D_SHARP, 0, 21, 3)));
    assertEquals(true, new Note(Note.Pitch.F_SHARP, 4, 6, 2).equals(fSharp4));
    assertEquals(false, d3.equals(d6));
    assertEquals(false, a10.equals(c0));
    assertEquals(false, e1.equals(new Note(Note.Pitch.E, 1, 0, 2)));
    assertEquals(false, new Note(Note.Pitch.E, 1, 0, 2).equals(new Note(
      Note.Pitch.E, 1, 1, 2)));
  }

  /**
   * Tests for the method hashCode.
   */
  @Test
  public void testHashCode() {
    assertEquals(c0.hashCode(), new Note(Note.Pitch.C, 0, 0, 4).hashCode());
    assertEquals(cSharp3.hashCode(),
      new Note(Note.Pitch.C_SHARP, 3, 2, 2).hashCode());
    assertEquals(d6.hashCode(), new Note(Note.Pitch.D, 6, 0, 1).hashCode());
    assertEquals(f3.hashCode(), new Note(Note.Pitch.F, 3, 0, 7).hashCode());
    assertEquals(fSharp4.hashCode(),
      new Note(Note.Pitch.F_SHARP, 4, 6, 2).hashCode());
  }
}
