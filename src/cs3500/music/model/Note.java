package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a musical note, consisting of a pitch and octave.
 */
public final class Note implements Comparable<Note> {

  //Never null
  private final Pitch pitch;

  //Invariant: [0,100)
  private final int octave;

  //Invariant: [0,..)
  private final int start;

  //Invariant: [1,..)
  private final int duration;

  //MIDI instrument to play the note with
  private final int instrument;

  //Volume to play the note at
  private final int volume;

  /**
   * Represents the names of the musical pitches, from C to B. Uses sharps, and no representation
   * of flats.
   */
  public enum Pitch {
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

  /**
   * Constructs a note with given arguments
   *
   * @param start      the beat where the note starts from [0,..)
   * @param pitch      the pitch of the note on the chromatic scale
   * @param octave     the octave of the note from [0,100)
   * @param duration   the duration of the note beyond its first beat from [0,..)
   * @param volume     the volume to play the Note
   * @param instrument the MIDI instrument to play the note with
   * @throws IllegalArgumentException if any of the ranges are violated
   */
  public Note(Pitch pitch, int octave, int start, int duration, int instrument, int volume) {
    if (start < 0 || octave < 0 || octave >= 100 || duration < 1 || pitch == null) {
      throw new IllegalArgumentException("Impossible arguments!");
    }

    this.pitch = pitch;
    this.octave = octave;
    this.start = start;
    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * Returns this Note's pitch.
   *
   * @return this Note's pitch
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Returns this Note's octave.
   *
   * @return this Note's octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Returns this Note's start time.
   *
   * @return this Note's start time
   */
  public int getStart() {
    return this.start;
  }

  /**
   * Returns this Note's duration in beats. Each beat represents the length of a quarter note.
   *
   * @return this Note's duration
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Returns this Note's MIDI instrument.
   *
   * @return this Note's instrument
   */
  public int getInstrument() {
    return instrument;
  }

  /**
   * Returns this Note's volume.
   *
   * @return this Note's volume
   */
  public int getVolume() {
    return volume;
  }

  /**
   * Returns the midi pitch of this note.
   *
   * @return the midi pitch
   */
  public int getMidiPitch() {
    return this.pitch.ordinal() + 12*(this.octave + 1);
  }

  /**
   * Determines if this note is playing during the given beat
   *
   * @param beat the beat to check
   * @return true if this note starts or continues during this beat, false otherwise
   */
  public boolean playsDuring(int beat) {
    return (this.start <= beat) && (this.start + this.duration > beat);
  }

  /**
   * Given a midi pitch value, return its pitch symbol
   *
   * @param midi midi pitch value
   * @return the pitch symbol
   */
  public static Pitch midiToPitch(int midi) {
    return Pitch.values()[midi % 12];
  }

  /**
   * Given a midi pitch value, return its octave
   *
   * @param midi midi pitch value
   * @return the octave
   */
  public static int midiToOctave(int midi) {
    return midi / 12 - 1; // the -1 is included because midi note 0 is C-1, whereas our note 0
    // is C0
  }

  @Override
  public String toString() {
    return pitch.toString() + Integer.toString(octave);
  }

  /**
   * Compares by octave, then by pitch, then start, duration, instrument, and finally volume.
   */
  @Override
  public int compareTo(Note that) {
    if (this.octave == that.octave) {
      if (this.pitch == that.pitch) {
        if (this.start == that.start) {
          if (this.duration == that.duration) {
            if (this.instrument == that.instrument) {
              return Integer.compare(this.volume, that.volume);
            } else {
              return Integer.compare(this.instrument, that.instrument);
            }
          } else {
            return Integer.compare(this.duration, that.duration);
          }
        } else {
          return Integer.compare(this.start, that.start);
        }
      } else {
        return this.pitch.compareTo(that.pitch);
      }
    } else {
      return Integer.compare(this.octave, that.octave);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Note)) {
      return false;
    }
    Note that = (Note) obj;
    return this.pitch == that.pitch && this.start == that.start && this.octave == that.octave
            && this.duration == that.duration && this.instrument == that.instrument && this
            .volume ==
            that.volume;
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, pitch, octave, duration, instrument, volume);
  }
}
