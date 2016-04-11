package cs3500.music.model;

/**
 * Specifies operations for any musical note.
 */
public interface Note extends Comparable<Note> {

  /**
   * Returns this note's pitch.
   *
   * @return this note's pitch
   */
  Pitch getPitch();

  /**
   * Returns this note's octave.
   *
   * @return this note's octave
   */
  int getOctave();

  /**
   * Returns this note's start time.
   *
   * @return this note's start time
   */
  int getStart();

  /**
   * Returns this note's duration in beats. Each beat represents the length of a quarter note.
   *
   * @return this note's duration
   */
  int getDuration();

  /**
   * Returns this note's MIDI instrument.
   *
   * @return this note's instrument
   */
  int getInstrument();

  /**
   * Returns this note's volume.
   *
   * @return this note's volume
   */
  int getVolume();

  /**
   * Returns the midi pitch of this note.
   *
   * @return the midi pitch
   */
  int getMidiPitch();

  /**
   * Determines if this note is playing during the given beat
   *
   * @param beat the beat to check
   * @return true if this note starts or continues during this beat, false otherwise
   */
  boolean playsDuring(int beat);

  /**
   * Given a midi pitch value, return its pitch symbol
   *
   * @param midi midi pitch value
   * @return the pitch symbol
   */
  static Pitch midiToPitch(int midi) {
    return Pitch.values()[Math.abs(midi % 12)];
  }

  /**
   * Given a midi pitch value, return its octave
   *
   * @param midi midi pitch value
   * @return the octave
   */
  static int midiToOctave(int midi) {
    return midi / 12 - 1; // the -1 is included because midi note 0 is C-1, whereas our note 0
    // is C0
  }

  /**
   * Represents the names of the musical pitches, from C to B. Uses sharps, and no representation
   * of flats.
   */
  enum Pitch {
    C("C"), C_SHARP("C#"), D("D"), D_SHARP("D#"), E("E"), F("F"), F_SHARP("F#"), G("G"),
    G_SHARP("G#"), A("A"), A_SHARP("A#"), B("B");

    private final String symbol;

    /**
     * Constructs a Pitch
     *
     * @param representation the symbol to represent this Pitch
     */
    Pitch(String representation) {
      this.symbol = representation;
    }

    @Override
    public String toString() {
      return this.symbol;
    }
  }
}
