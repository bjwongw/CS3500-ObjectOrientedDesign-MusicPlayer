package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Note class
 */
public class NoteTest {
  Note c0 = new Note(Note.Pitch.C, 0, 0, 4, 0, 0);
  Note c4 = new Note(Note.Pitch.C, 4, 3, 1, 0, 0);
  Note cSharp3 = new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0);
  Note cSharp7 = new Note(Note.Pitch.C_SHARP, 7, 10, 3, 0, 0);
  Note d3 = new Note(Note.Pitch.D, 3, 13, 4, 0, 0);
  Note d6 = new Note(Note.Pitch.D, 6, 0, 1, 0, 0);
  Note dSharp0 = new Note(Note.Pitch.D_SHARP, 0, 21, 3, 0, 0);
  Note dSharp9 = new Note(Note.Pitch.D_SHARP, 9, 8, 8, 0, 0);
  Note e1 = new Note(Note.Pitch.E, 1, 0, 1, 0, 0);
  Note e2 = new Note(Note.Pitch.E, 2, 2, 1, 0, 0);
  Note f3 = new Note(Note.Pitch.F, 3, 0, 7, 0, 0);
  Note f5 = new Note(Note.Pitch.F, 5, 5, 5, 0, 0);
  Note fSharp4 = new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0);
  Note fSharp9 = new Note(Note.Pitch.F_SHARP, 9, 6, 2, 0, 0);
  Note g0 = new Note(Note.Pitch.G, 0, 3, 1, 0, 0);
  Note g6 = new Note(Note.Pitch.G, 6, 10, 4, 0, 0);
  Note gSharp5 = new Note(Note.Pitch.G_SHARP, 5, 3, 1, 0, 0);
  Note gSharp7 = new Note(Note.Pitch.G_SHARP, 7, 40, 2, 0, 0);
  Note a8 = new Note(Note.Pitch.A, 8, 32, 2, 0, 0);
  Note a10 = new Note(Note.Pitch.A, 10, 32, 2, 0, 0);
  Note aSharp4 = new Note(Note.Pitch.A_SHARP, 4, 14, 10, 0, 0);
  Note aSharp9 = new Note(Note.Pitch.A_SHARP, 9, 22, 9, 0, 0);
  Note b10 = new Note(Note.Pitch.B, 10, 33, 7, 0, 0);

  /**
   * Ensures that a note cannot be constructed with a negative start time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructor_negativeStartNote() {
    new Note(Note.Pitch.A, 0, -1, 3, 0, 0);
  }

  /**
   * Ensures that a note cannot be constructed with a duration less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructor_beatLessThanOne() {
    new Note(Note.Pitch.B, 2, 0, 0, 0, 0);
  }

  /**
   * Tests for the method getPitch
   */
  @Test
  public void testGetPitch() {
    assertEquals(Note.Pitch.F_SHARP, fSharp4.getPitch());
    assertEquals(Note.Pitch.B, b10.getPitch());
    assertEquals(Note.Pitch.A_SHARP, aSharp4.getPitch());
    assertEquals(Note.Pitch.G, g6.getPitch());
    assertEquals(Note.Pitch.C, c4.getPitch());
  }

  /**
   * Tests for the method getOctave
   */
  @Test
  public void testGetOctave() {

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
   * Tests for the method getInstrument
   */
  @Test
  public void testGetInstrument() {
    assertEquals(5, new Note(Note.Pitch.A, 4, 3, 2, 5, 2).getInstrument());
    assertEquals(3, new Note(Note.Pitch.G, 7, 10, 20, 3, 19).getInstrument());
    assertEquals(13, new Note(Note.Pitch.F_SHARP, 9, 11, 1, 13, 7).getInstrument());
    assertEquals(6, new Note(Note.Pitch.D, 3, 4, 5, 6, 7).getInstrument());
  }

  /**
   * Tests for the method getVolume
   */
  @Test
  public void testGetVolume() {
    assertEquals(7, new Note(Note.Pitch.D, 3, 4, 5, 6, 7).getVolume());
    assertEquals(19, new Note(Note.Pitch.G, 7, 10, 20, 3, 19).getVolume());
    assertEquals(7, new Note(Note.Pitch.F_SHARP, 9, 11, 1, 13, 7).getVolume());
    assertEquals(2, new Note(Note.Pitch.B, 4, 3, 2, 5, 2).getVolume());
  }

  /**
   * Tests for the method getMidiPitch
   */
  @Test
  public void testGetMidiPitch() {

  }

  /**
   * Tests for the method playsDuring
   */
  @Test
  public void testPlaysDuring() {
    assertEquals(true, c0.playsDuring(0));
    assertEquals(true, c0.playsDuring(3));
    assertEquals(false, c0.playsDuring(4));
    assertEquals(true, cSharp3.playsDuring(2));
    assertEquals(true, gSharp7.playsDuring(41));
    assertEquals(true, a8.playsDuring(32));
    assertEquals(false, aSharp9.playsDuring(0));
    assertEquals(false, aSharp9.playsDuring(14));
    assertEquals(false, a8.playsDuring(30));
  }

  /**
   * Tests for the method midiToPitch
   */
  @Test
  public void testMidiToPitch() {

  }

  /**
   * Tests for the method midiToOctave
   */
  @Test
  public void testMidiToOctave() {

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
    assertEquals(0, new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0).compareTo(cSharp3));
    assertEquals(0, cSharp3.compareTo(new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0)));
    assertEquals(0, d3.compareTo(d3));
    assertEquals(0, g6.compareTo(new Note(Note.Pitch.G, 6, 10, 4, 0, 0)));
    assertEquals(0, new Note(Note.Pitch.E, 1, 0, 1, 0, 0).compareTo(e1));
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
    assertTrue(0 > c0.compareTo(new Note(Note.Pitch.C, 0, 5, 7, 0, 0)));
    assertTrue(0 > gSharp5.compareTo(new Note(Note.Pitch.G_SHARP, 5, 10, 1, 0, 0)));

    assertTrue(0 < f5.compareTo(new Note(Note.Pitch.F, 5, 3, 2, 0, 0)));
    assertTrue(0 < a10.compareTo(new Note(Note.Pitch.A, 1, 10, 2, 0, 0)));
    assertTrue(0 < aSharp4.compareTo(new Note(Note.Pitch.A_SHARP, 4, 7, 6, 0, 0)));
    assertTrue(0 < dSharp0.compareTo(new Note(Note.Pitch.D_SHARP, 0, 20, 3, 0, 0)));
  }

  /**
   * Tests for the method compareTo when the two notes have the same pitch and start time, but
   * different durations.
   */
  @Test
  public void testCompareTo_samePitchSameStartDifferentDuration() {
    assertTrue(0 > f3.compareTo(new Note(Note.Pitch.F, 3, 32, 4, 0, 0)));
    assertTrue(0 < b10.compareTo(new Note(Note.Pitch.B, 1, 33, 2, 0, 0)));

    assertTrue(0 < c0.compareTo(new Note(Note.Pitch.C, 0, 0, 3, 0, 0)));
    assertTrue(0 < d3.compareTo(new Note(Note.Pitch.D, 3, 13, 2, 0, 0)));
    assertTrue(0 < gSharp7.compareTo(new Note(Note.Pitch.G_SHARP, 7, 40, 1, 0, 0)));
    assertTrue(0 < fSharp4.compareTo(new Note(Note.Pitch.F_SHARP, 4, 6, 1, 0, 0)));
  }

  /**
   * Tests for the method compareTo when the two notes have the same pitch, start time, and
   * duration, but different instruments.
   */
  @Test
  public void testCompareTo_sameUpToInstrument() {
    assertTrue(0 < new Note(Note.Pitch.A, 4, 3, 2, 5, 2).compareTo(
      new Note(Note.Pitch.A, 4, 3, 2, 2, 1)));
    assertTrue(0 < new Note(Note.Pitch.G, 7, 10, 20, 3, 19).compareTo(
      new Note(Note.Pitch.G, 7, 10, 20, 0, 23)));
    assertTrue(0 < new Note(Note.Pitch.F_SHARP, 9, 11, 1, 13, 7).compareTo(
      new Note(Note.Pitch.F_SHARP, 9, 11, 1, 12, 4)));
    assertTrue(0 < new Note(Note.Pitch.D, 3, 4, 5, 6, 7).compareTo(
      new Note(Note.Pitch.D, 3, 4, 5, 3, 7)));

    assertTrue(0 > b10.compareTo(new Note(Note.Pitch.B, 10, 33, 7, 3, 0)));
    assertTrue(0 > e1.compareTo(new Note(Note.Pitch.E, 1, 0, 1, 5, 0)));
    assertTrue(0 > fSharp4.compareTo(new Note(Note.Pitch.F_SHARP, 4, 6, 2, 7, 0)));
    assertTrue(0 > c0.compareTo(new Note(Note.Pitch.C, 0, 0, 4, 1, 0)));
  }

  /**
   * Tests for the method compareTo when the two notes have the same pitch, start time, duration,
   * and instrument, but different volumes.
   */
  @Test
  public void testCompareTo_sameUpToVolume() {
    assertTrue(0 < new Note(Note.Pitch.D, 3, 4, 5, 6, 7).compareTo(
      new Note(Note.Pitch.D, 3, 4, 5, 6, 0)));
    assertTrue(0 < new Note(Note.Pitch.G, 7, 10, 20, 3, 19).compareTo(
      new Note(Note.Pitch.G, 7, 10, 20, 3, 3)));
    assertTrue(0 < new Note(Note.Pitch.F_SHARP, 9, 11, 1, 13, 7).compareTo(
      new Note(Note.Pitch.F_SHARP, 9, 11, 1, 13, 4)));
    assertTrue(0 < new Note(Note.Pitch.B, 4, 3, 2, 5, 2).compareTo(
      new Note(Note.Pitch.B, 4, 3, 2, 5, 1)));

    assertTrue(0 > b10.compareTo(new Note(Note.Pitch.B, 10, 33, 7, 0, 3)));
    assertTrue(0 > fSharp4.compareTo(new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 132)));
    assertTrue(0 > c0.compareTo(new Note(Note.Pitch.C, 0, 0, 4, 0, 43)));
    assertTrue(0 > e1.compareTo(new Note(Note.Pitch.E, 1, 0, 1, 0, 87)));
  }

  /**
   * Tests for the method equals.
   */
  @Test
  public void testEquals() {
    assertEquals(true, c0.equals(c0));
    assertEquals(true, new Note(Note.Pitch.C, 0, 0, 4, 0, 0).equals(c0));
    assertEquals(true, c0.equals(new Note(Note.Pitch.C, 0, 0, 4, 0, 0)));
    assertEquals(true, dSharp0.equals(new Note(Note.Pitch.D_SHARP, 0, 21, 3, 0, 0)));
    assertEquals(true, new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0).equals(fSharp4));
    assertEquals(false, d3.equals(d6));
    assertEquals(false, a10.equals(c0));
    assertEquals(false, e1.equals(new Note(Note.Pitch.E, 1, 0, 2, 0, 0)));
    assertEquals(false, new Note(Note.Pitch.E, 1, 0, 2, 0, 0).equals(new Note(
      Note.Pitch.E, 1, 1, 2, 0, 0)));
  }

  /**
   * Tests for the method hashCode.
   */
  @Test
  public void testHashCode() {
    assertEquals(c0.hashCode(), new Note(Note.Pitch.C, 0, 0, 4, 0, 0).hashCode());
    assertEquals(cSharp3.hashCode(),
      new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0).hashCode());
    assertEquals(d6.hashCode(), new Note(Note.Pitch.D, 6, 0, 1, 0, 0).hashCode());
    assertEquals(f3.hashCode(), new Note(Note.Pitch.F, 3, 0, 7, 0, 0).hashCode());
    assertEquals(fSharp4.hashCode(),
      new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0).hashCode());
  }
}
