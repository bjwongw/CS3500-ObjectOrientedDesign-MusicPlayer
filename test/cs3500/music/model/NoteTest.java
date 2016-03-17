package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Note class
 */
public class NoteTest {
  Note c0;
  Note c4;
  Note cSharp3;
  Note cSharp7;
  Note d3;
  Note d6;
  Note dSharp0;
  Note dSharp9;
  Note e1;
  Note e2;
  Note f3;
  Note f5;
  Note fSharp4;
  Note fSharp9;
  Note g0;
  Note g6;
  Note gSharp5;
  Note gSharp7;
  Note a8;
  Note a10;
  Note aSharp4;
  Note aSharp9;
  Note b10;

  /**
   * Initial data, prevents the originals from being mutated.
   */
  public void initData() {
    c0 = new Note(new Pitch(Pitch.PitchSymbol.C, 0), 0, 4);
    c4 = new Note(new Pitch(Pitch.PitchSymbol.C, 4), 3, 1);
    cSharp3 = new Note(new Pitch(Pitch.PitchSymbol.C_SHARP, 3), 2, 2);
    cSharp7 = new Note(new Pitch(Pitch.PitchSymbol.C_SHARP, 7), 10, 3);
    d3 = new Note(new Pitch(Pitch.PitchSymbol.D, 3), 13, 4);
    d6 = new Note(new Pitch(Pitch.PitchSymbol.D, 6), 0, 1);
    dSharp0 = new Note(new Pitch(Pitch.PitchSymbol.D_SHARP, 0), 21, 3);
    dSharp9 = new Note(new Pitch(Pitch.PitchSymbol.D_SHARP, 9), 8, 8);
    e1 = new Note(new Pitch(Pitch.PitchSymbol.E, 1), 0, 1);
    e2 = new Note(new Pitch(Pitch.PitchSymbol.E, 2), 2, 1);
    f3 = new Note(new Pitch(Pitch.PitchSymbol.F, 3), 0, 7);
    f5 = new Note(new Pitch(Pitch.PitchSymbol.F, 5), 5, 5);
    fSharp4 = new Note(new Pitch(Pitch.PitchSymbol.F_SHARP, 4), 6, 2);
    fSharp9 = new Note(new Pitch(Pitch.PitchSymbol.F_SHARP, 9), 6, 2);
    g0 = new Note(new Pitch(Pitch.PitchSymbol.G, 0), 3, 1);
    g6 = new Note(new Pitch(Pitch.PitchSymbol.G, 6), 10, 4);
    gSharp5 = new Note(new Pitch(Pitch.PitchSymbol.G_SHARP, 5), 3, 1);
    gSharp7 = new Note(new Pitch(Pitch.PitchSymbol.G_SHARP, 7), 40, 2);
    a8 = new Note(new Pitch(Pitch.PitchSymbol.A, 8), 32, 2);
    a10 = new Note(new Pitch(Pitch.PitchSymbol.A, 10), 32, 2);
    aSharp4 = new Note(new Pitch(Pitch.PitchSymbol.A_SHARP, 4), 14, 10);
    aSharp9 = new Note(new Pitch(Pitch.PitchSymbol.A_SHARP, 9), 22, 9);
    b10 = new Note(new Pitch(Pitch.PitchSymbol.B, 10), 33, 7);
  }

  /**
   * Ensures that a note cannot be constructed with a negative start time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructor_negativeStartNote() {
    new Note(new Pitch(Pitch.PitchSymbol.A, 0), -1, 3);
  }

  /**
   * Ensures that a note cannot be constructed with a duration less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void constructor_beatLessThanOne() {
    new Note(new Pitch(Pitch.PitchSymbol.B, 2), 0, 0);
  }

  /**
   * Tests for the method getPitch
   */
  @Test
  public void testGetPitch() {
    initData();
    assertEquals(new Pitch(Pitch.PitchSymbol.C, 0), c0.getPitch());
    assertEquals(new Pitch(Pitch.PitchSymbol.C_SHARP, 3), cSharp3.getPitch());
    assertEquals(new Pitch(Pitch.PitchSymbol.D, 6), d6.getPitch());
    assertEquals(new Pitch(Pitch.PitchSymbol.E, 2), e2.getPitch());
    assertEquals(new Pitch(Pitch.PitchSymbol.F_SHARP, 4), fSharp4.getPitch());
    assertEquals(new Pitch(Pitch.PitchSymbol.B, 10), b10.getPitch());
  }

  /**
   * Tests for the method getStart
   */
  @Test
  public void testGetStart() {
    initData();
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
    initData();
    assertEquals(4, c0.getDuration());
    assertEquals(3, cSharp7.getDuration());
    assertEquals(1, d6.getDuration());
    assertEquals(8, dSharp9.getDuration());
    assertEquals(1, e1.getDuration());
    assertEquals(7, f3.getDuration());
  }

  /**
   * Tests for the method changePitch
   */
  @Test
  public void testChangePitch() {
    initData();
    assertEquals(new Pitch(Pitch.PitchSymbol.C, 0), c0.getPitch()); // unchanged pitch
    c0.changePitch(new Pitch(Pitch.PitchSymbol.D, 3));
    assertEquals(new Pitch(Pitch.PitchSymbol.D, 3), c0.getPitch()); // changed pitch

    assertEquals(new Pitch(Pitch.PitchSymbol.D, 6), d6.getPitch()); // unchanged pitch
    d6.changePitch(new Pitch(Pitch.PitchSymbol.A_SHARP, 7));
    assertEquals(new Pitch(Pitch.PitchSymbol.A_SHARP, 7), d6.getPitch()); // changed pitch

    assertEquals(new Pitch(Pitch.PitchSymbol.F_SHARP, 4), fSharp4.getPitch()); // unchanged pitch
    fSharp4.changePitch(new Pitch(Pitch.PitchSymbol.E, 9));
    assertEquals(new Pitch(Pitch.PitchSymbol.E, 9), fSharp4.getPitch()); // changed pitch

    assertEquals(new Pitch(Pitch.PitchSymbol.B, 10), b10.getPitch()); // unchanged pitch
    b10.changePitch(new Pitch(Pitch.PitchSymbol.D_SHARP, 5));
    assertEquals(new Pitch(Pitch.PitchSymbol.D_SHARP, 5), b10.getPitch()); // changed pitch
  }

  /**
   * Ensures that you cannot change the start of a Note to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void changeStart_negativeStart() {
    Note note = new Note(new Pitch(Pitch.PitchSymbol.C, 0), 0, 0);
    note.changeStart(-2);
  }

  /**
   * Tests for the method changeStart
   */
  @Test
  public void testChangeStart() {
    initData();
    assertEquals(0, c0.getStart()); // unchanged start
    c0.changeStart(3);
    assertEquals(3, c0.getStart()); // changed start value

    assertEquals(6, fSharp4.getStart()); // unchanged start
    fSharp4.changeStart(32);
    assertEquals(32, fSharp4.getStart()); // changed start value

    assertEquals(22, aSharp9.getStart()); // unchanged start
    aSharp9.changeStart(5);
    assertEquals(5, aSharp9.getStart()); // changed start value

    assertEquals(33, b10.getStart()); // unchanged start
    b10.changeStart(0);
    assertEquals(0, b10.getStart()); // changed start
  }

  /**
   * Ensures that you cannot change the duration of a Note to be less than 1.
   */
  @Test(expected = IllegalArgumentException.class)
  public void changeDuration_invalidDuration() {
    Note note = new Note(new Pitch(Pitch.PitchSymbol.C_SHARP, 2), 2, 2);
    note.changeDuration(-1);
  }

  /**
   * Tests for the method changeDuration
   */
  @Test
  public void testChangeDuration() {
    initData();
    assertEquals(1, c4.getDuration()); // unchanged duration
    c4.changeDuration(3);
    assertEquals(3, c4.getDuration()); // changed duration

    assertEquals(4, d3.getDuration()); // unchanged duration
    d3.changeDuration(12);
    assertEquals(12, d3.getDuration()); // changed duration

    assertEquals(2, gSharp7.getDuration()); // unchanged duration
    gSharp7.changeDuration(3);
    assertEquals(3, gSharp7.getDuration()); // changed duration

    assertEquals(2, a10.getDuration()); // unchanged duration
    a10.changeDuration(30);
    assertEquals(30, a10.getDuration()); // changed duration
  }

  /**
   * Tests for the method toString
   */
  @Test
  public void testToString() {
    initData();
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
    initData();
    assertEquals(0, cSharp3.compareTo(cSharp3));
    assertEquals(0, new Note(new Pitch(Pitch.PitchSymbol.C_SHARP, 3), 2, 2).compareTo(cSharp3));
    assertEquals(0, cSharp3.compareTo(new Note(new Pitch(Pitch.PitchSymbol.C_SHARP, 3), 2, 2)));
    assertEquals(0, d3.compareTo(d3));
    assertEquals(0, g6.compareTo(new Note(new Pitch(Pitch.PitchSymbol.G, 6), 10, 4)));
    assertEquals(0, new Note(new Pitch(Pitch.PitchSymbol.E, 1), 0, 1).compareTo(e1));
  }

  /**
   * Tests for the method compareTo when the two notes have different pitches.
   */
  @Test
  public void testCompareTo_differentPitch() {
    initData();
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
    initData();
    assertTrue(0 > c0.compareTo(new Note(new Pitch(Pitch.PitchSymbol.C, 0), 2, 4)));
    assertTrue(0 > d3.compareTo(new Note(new Pitch(Pitch.PitchSymbol.D, 3), 14, 1)));
    assertTrue(0 > e2.compareTo(new Note(new Pitch(Pitch.PitchSymbol.E, 2), 5, 7)));
    assertTrue(0 > gSharp5.compareTo(new Note(new Pitch(Pitch.PitchSymbol.G_SHARP, 5), 10, 1)));

    assertTrue(0 < f5.compareTo(new Note(new Pitch(Pitch.PitchSymbol.F, 5), 3, 2)));
    assertTrue(0 < a10.compareTo(new Note(new Pitch(Pitch.PitchSymbol.A, 10), 10, 2)));
    assertTrue(0 < aSharp4.compareTo(new Note(new Pitch(Pitch.PitchSymbol.A_SHARP, 4), 7, 6)));
    assertTrue(0 < dSharp0.compareTo(new Note(new Pitch(Pitch.PitchSymbol.D_SHARP, 0), 20, 3)));
  }

  /**
   * Tests for the method compareTo when the two notes have the same pitch and start time, but
   * different durations.
   */
  @Test
  public void testCompareTo_samePitchSameStartDifferentDuration() {
    initData();
    assertTrue(0 > f3.compareTo(new Note(new Pitch(Pitch.PitchSymbol.F, 3), 0, 8)));
    assertTrue(0 > g6.compareTo(new Note(new Pitch(Pitch.PitchSymbol.G, 6), 10, 6)));
    assertTrue(0 > a8.compareTo(new Note(new Pitch(Pitch.PitchSymbol.A, 8), 32, 4)));
    assertTrue(0 > b10.compareTo(new Note(new Pitch(Pitch.PitchSymbol.B, 10), 33, 10)));

    assertTrue(0 < c0.compareTo(new Note(new Pitch(Pitch.PitchSymbol.C, 0), 0, 3)));
    assertTrue(0 < d3.compareTo(new Note(new Pitch(Pitch.PitchSymbol.D, 3), 13, 2)));
    assertTrue(0 < gSharp7.compareTo(new Note(new Pitch(Pitch.PitchSymbol.G_SHARP, 7), 40, 1)));
    assertTrue(0 < fSharp4.compareTo(new Note(new Pitch(Pitch.PitchSymbol.F_SHARP, 4), 6, 1)));
  }

  /**
   * Tests for the method equals.
   */
  @Test
  public void testEquals() {
    initData();
    assertEquals(true, c0.equals(c0));
    assertEquals(true, new Note(new Pitch(Pitch.PitchSymbol.C, 0), 0, 4).equals(c0));
    assertEquals(true, c0.equals(new Note(new Pitch(Pitch.PitchSymbol.C, 0), 0, 4)));
    assertEquals(true, dSharp0.equals(new Note(new Pitch(Pitch.PitchSymbol.D_SHARP, 0), 21, 3)));
    assertEquals(true, new Note(new Pitch(Pitch.PitchSymbol.F_SHARP, 4), 6, 2).equals(fSharp4));
    assertEquals(false, d3.equals(d6));
    assertEquals(false, a10.equals(c0));
    assertEquals(false, e1.equals(new Note(new Pitch(Pitch.PitchSymbol.E, 1), 0, 2)));
    assertEquals(false, new Note(new Pitch(Pitch.PitchSymbol.E, 1), 0, 2).equals(new Note(
      new Pitch(Pitch.PitchSymbol.E, 1), 1, 2)));
  }

  /**
   * Tests for the method hashCode.
   */
  @Test
  public void testHashCode() {
    initData();
    assertEquals(c0.hashCode(), new Note(new Pitch(Pitch.PitchSymbol.C, 0), 0, 4).hashCode());
    assertEquals(cSharp3.hashCode(),
      new Note(new Pitch(Pitch.PitchSymbol.C_SHARP, 3), 2, 2).hashCode());
    assertEquals(d6.hashCode(), new Note(new Pitch(Pitch.PitchSymbol.D, 6), 0, 1).hashCode());
    assertEquals(f3.hashCode(), new Note(new Pitch(Pitch.PitchSymbol.F, 3), 0, 7).hashCode());
    assertEquals(fSharp4.hashCode(),
      new Note(new Pitch(Pitch.PitchSymbol.F_SHARP, 4), 6, 2).hashCode());
  }
}
