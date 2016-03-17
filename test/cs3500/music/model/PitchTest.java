package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Pitch class
 */
public class PitchTest {

  Pitch.PitchSymbol cPitch = Pitch.PitchSymbol.C;
  Pitch.PitchSymbol cSharpPitch = Pitch.PitchSymbol.C_SHARP;
  Pitch.PitchSymbol dPitch = Pitch.PitchSymbol.D;
  Pitch.PitchSymbol dSharpPitch = Pitch.PitchSymbol.D_SHARP;
  Pitch.PitchSymbol ePitch = Pitch.PitchSymbol.E;
  Pitch.PitchSymbol fPitch = Pitch.PitchSymbol.F;
  Pitch.PitchSymbol fSharpPitch = Pitch.PitchSymbol.F_SHARP;
  Pitch.PitchSymbol gPitch = Pitch.PitchSymbol.G;
  Pitch.PitchSymbol gSharpPitch = Pitch.PitchSymbol.G_SHARP;
  Pitch.PitchSymbol aPitch = Pitch.PitchSymbol.A;
  Pitch.PitchSymbol aSharpPitch = Pitch.PitchSymbol.A_SHARP;
  Pitch.PitchSymbol bPitch = Pitch.PitchSymbol.B;

  Pitch c0 = new Pitch(cPitch, 0);
  Pitch c2 = new Pitch(cPitch, 2);
  Pitch cSharp3 = new Pitch(cSharpPitch, 3);
  Pitch d7 = new Pitch(dPitch, 7);
  Pitch d8 = new Pitch(dPitch, 8);
  Pitch dSharp2 = new Pitch(dSharpPitch, 2);
  Pitch e9 = new Pitch(ePitch, 9);
  Pitch f5 = new Pitch(fPitch, 5);
  Pitch fSharp6 = new Pitch(fSharpPitch, 6);
  Pitch g2 = new Pitch(gPitch, 2);
  Pitch gSharp7 = new Pitch(gSharpPitch, 7);
  Pitch a4 = new Pitch(aPitch, 4);
  Pitch aSharp0 = new Pitch(aSharpPitch, 0);
  Pitch b6 = new Pitch(bPitch, 6);

  /**
   * Tests for the method toString in Pitch.PitchSymbol.
   */
  @Test
  public void testToString_pitchSymbol() {
    assertEquals("C", cPitch.toString());
    assertEquals("C#",cSharpPitch.toString());
    assertEquals("D", dPitch.toString());
    assertEquals("D#", dSharpPitch.toString());
    assertEquals("E", ePitch.toString());
    assertEquals("F", fPitch.toString());
    assertEquals("F#", fSharpPitch.toString());
    assertEquals("G", gPitch.toString());
    assertEquals("G#", gSharpPitch.toString());
    assertEquals("A", aPitch.toString());
    assertEquals("A#", aSharpPitch.toString());
    assertEquals("B", bPitch.toString());
  }

  /**
   * Ensures that constructing a Pitch with a negative octave will throw an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void negativeOctave() { new Pitch(cPitch, -1); }

  /**
   * Tests for the method getPitchSymbol.
   */
  @Test
  public void testGetPitchSymbol() {
    assertEquals(cPitch, c0.getPitchSymbol());
    assertEquals(cSharpPitch, cSharp3.getPitchSymbol());
    assertEquals(dPitch, d7.getPitchSymbol());
    assertEquals(dSharpPitch, dSharp2.getPitchSymbol());
    assertEquals(ePitch, e9.getPitchSymbol());
    assertEquals(fPitch, f5.getPitchSymbol());
    assertEquals(fSharpPitch, fSharp6.getPitchSymbol());
    assertEquals(gPitch, g2.getPitchSymbol());
    assertEquals(gSharpPitch, gSharp7.getPitchSymbol());
    assertEquals(aPitch, a4.getPitchSymbol());
    assertEquals(aSharpPitch, aSharp0.getPitchSymbol());
    assertEquals(bPitch, b6.getPitchSymbol());
  }

  /**
   * Tests for the method getOctave.
   */
  @Test
  public void testGetOctave() {
    assertEquals(0, c0.getOctave());
    assertEquals(2, c2.getOctave());
    assertEquals(3, cSharp3.getOctave());
    assertEquals(4, a4.getOctave());
    assertEquals(5, f5.getOctave());
    assertEquals(6, b6.getOctave());
    assertEquals(7, gSharp7.getOctave());
    assertEquals(8, d8.getOctave());
    assertEquals(9, e9.getOctave());
    assertEquals(2, g2.getOctave());
  }

  /**
   * Tests for the method toString in Pitch.
   */
  @Test
  public void testToString_pitch() {
    assertEquals("C0", c0.toString());
    assertEquals("C2", c2.toString());
    assertEquals("C#3", cSharp3.toString());
    assertEquals("D7", d7.toString());
    assertEquals("D#2", dSharp2.toString());
    assertEquals("E9", e9.toString());
    assertEquals("F5", f5.toString());
    assertEquals("F#6", fSharp6.toString());
    assertEquals("G2", g2.toString());
    assertEquals("G#7", gSharp7.toString());
    assertEquals("A4", a4.toString());
    assertEquals("A#0", aSharp0.toString());
    assertEquals("B6", b6.toString());
  }

  /**
   * Tests for the method compareTo
   */
  @Test
  public void testCompareTo() {
    assertEquals(0, c0.compareTo(new Pitch(cPitch, 0)));
    assertEquals(0, aSharp0.compareTo(new Pitch(aSharpPitch, 0)));
    assertEquals(0, e9.compareTo(new Pitch(ePitch, 9)));
    assertEquals(0, gSharp7.compareTo(new Pitch(gSharpPitch, 7)));
    assertTrue(0 < c2.compareTo(c0));
    assertTrue(0 < d8.compareTo(d7));
    assertTrue(0 < g2.compareTo(c2));
    assertTrue(0 < b6.compareTo(fSharp6));
    assertTrue(0 > c0.compareTo(c2));
    assertTrue(0 > cSharp3.compareTo(fSharp6));
    assertTrue(0 > aSharp0.compareTo(e9));
    assertTrue(0 > g2.compareTo(gSharp7));
  }

  /**
   * Tests for the method equals
   */
  @Test
  public void testEquals() {
    assertEquals(true, cSharp3.equals(cSharp3));
    assertEquals(true, new Pitch(cSharpPitch, 3).equals(cSharp3));
    assertEquals(true, cSharp3.equals(new Pitch(cSharpPitch, 3)));
    assertEquals(true, new Pitch(cSharpPitch, 3).equals(new Pitch(cSharpPitch, 3)));
    assertEquals(true, g2.equals(new Pitch(gPitch, 2)));
    assertEquals(true, new Pitch(dPitch, 7).equals(d7));
    assertEquals(false, aSharp0.equals(a4));
    assertEquals(false, d7.equals(d8));
    assertEquals(false, e9.equals(a4));
    assertEquals(false, gSharp7.equals(fSharp6));

  }

  /**
   * Tests for the method hashCode
   */
  @Test
  public void testHashCode() {
    assertEquals(cSharp3.hashCode(), new Pitch(cSharpPitch, 3).hashCode());
    assertEquals(aSharp0.hashCode(), new Pitch(aSharpPitch, 0).hashCode());
    assertEquals(b6.hashCode(), new Pitch(bPitch, 6).hashCode());
    assertEquals(c2.hashCode(), new Pitch(cPitch, 2).hashCode());
    assertEquals(e9.hashCode(), new Pitch(ePitch, 9).hashCode());
    assertEquals(dSharp2.hashCode(), new Pitch(dSharpPitch, 2).hashCode());
  }
}
