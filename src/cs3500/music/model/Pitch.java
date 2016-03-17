package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a musical pitch, which includes the pitch name (e.g. A#, B, C#) and the octave
 * number.
 */
public class Pitch implements Comparable<Pitch> {

  /**
   * Represents the names of the musical pitches, from C to B. Uses sharps, and no representation
   * of flats.
   */
  public enum PitchSymbol {
    C("C"), C_SHARP("C#"), D("D"), D_SHARP("D#"), E("E"), F("F"), F_SHARP("F#"), G("G"),
    G_SHARP("G#"), A("A"), A_SHARP("A#"), B("B");

    private final String symbol;

    /**
     * Constructs a PitchSymbol
     * @param symbol the symbol to represent this PitchSymbol
     */
    PitchSymbol(String symbol) {
      this.symbol = symbol;
    }

    @Override
    public String toString() {
      return this.symbol;
    }
  }

  private final PitchSymbol pitchSymbol;
  private final int octave;

  /**
   * Constructs a Pitch.
   *
   * @param pitchSymbol the symbol of this Pitch
   * @param octave the octave of this Pitch
   */
  public Pitch(PitchSymbol pitchSymbol, int octave) {
    if (octave < 0) { throw new IllegalArgumentException("Must be non-negative"); }
    this.pitchSymbol = Objects.requireNonNull(pitchSymbol);
    this.octave = octave;
  }

  /**
   * Returns the PitchSymbol of this Pitch.
   *
   * @return the PitchSymbol of this Pitch
   */
  public PitchSymbol getPitchSymbol() {
    return this.pitchSymbol;
  }

  /**
   * Returns the octave of this Pitch.
   *
   * @return the octave of this Pitch
   */
  public int getOctave() {
    return this.octave;
  }

  @Override
  public String toString() { return pitchSymbol.toString() + Integer.toString(octave); }

  @Override
  public int compareTo(Pitch that) {
    if (this.octave < that.octave) { return -1; }
    else if (this.octave > that.octave) { return 1; }
    else {
      return this.pitchSymbol.compareTo(that.pitchSymbol);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof Pitch)) { return false; }
    Pitch that = (Pitch) obj;
    return this.pitchSymbol == that.pitchSymbol && this.octave == that.octave;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pitchSymbol, this.octave);
  }
}
