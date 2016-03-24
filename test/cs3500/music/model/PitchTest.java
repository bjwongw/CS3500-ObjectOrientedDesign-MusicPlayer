package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Pitch class
 */
public class PitchTest {

  Note.Pitch cPitch = Note.Pitch.C;
  Note.Pitch cSharpPitch = Note.Pitch.C_SHARP;
  Note.Pitch dPitch = Note.Pitch.D;
  Note.Pitch dSharpPitch = Note.Pitch.D_SHARP;
  Note.Pitch ePitch = Note.Pitch.E;
  Note.Pitch fPitch = Note.Pitch.F;
  Note.Pitch fSharpPitch = Note.Pitch.F_SHARP;
  Note.Pitch gPitch = Note.Pitch.G;
  Note.Pitch gSharpPitch = Note.Pitch.G_SHARP;
  Note.Pitch aPitch = Note.Pitch.A;
  Note.Pitch aSharpPitch = Note.Pitch.A_SHARP;
  Note.Pitch bPitch = Note.Pitch.B;

  /**
   * Tests for the method toString in Note.Pitch.
   */
  @Test
  public void testToString() {
    assertEquals("C", cPitch.toString());
    assertEquals("C#", cSharpPitch.toString());
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
}
