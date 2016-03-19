package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a musical note, consisting of a pitch and octave.
 */
public class Note implements Comparable<Note> {

  //Never null
  private final Pitch pitch;

  //Invariant: [0,100)
  private final int octave;

  //Invariant: [0,..)
  private final int start;

  //Invariant: [0,..)
  private final int duration;

  //Volume to play the note at
  private final int volume;

  //MIDI instrument to play the note with
  private final int instrument;


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

    /**
     * Returns the padded representation of this pitch given an octave, roughly centered, with the
     * octave, in a string of length 5
     *
     * @param octave the octave to be appended
     * @return the string
     */
    public String getPaddedRepresentation(int octave) {
      if (octave < 10) {
        if (symbol.length() == 1) {
          return "  " + symbol + octave + " ";
        } else {
          return " " + symbol + octave + " ";
        }
      } else {
        if (symbol.length() == 1) {
          return " " + symbol + octave + " ";
        } else {
          return " " + symbol + octave;
        }
      }
    }

    public String getStringWithOctave(int o) {
      return this.symbol + Integer.toString(o);
    }

    @Override
    public String toString() {
      return this.symbol;
    }
  }

  /**
   * Constructs a note with given arguments
   *
   * @param start    the beat where the note starts from [0,..)
   * @param pitch    the pitch of the note on the chromatic scale
   * @param octave   the octave of the note from [0,100)
   * @param duration the duration of the note beyond its first beat from [0,..)
   * @throws IllegalArgumentException if any of the ranges are violated
   */
  public Note(Pitch pitch, int octave, int start, int duration, int volume, int instrument) {
    if (start < 0 || octave < 0 || octave >= 100 || duration < 0 || pitch == null) {
      throw new IllegalArgumentException("Impossible arguments!");
    }

    this.pitch = pitch;
    this.octave = octave;
    this.start = start;
    this.duration = duration;
    this.volume = volume;
    this.instrument = instrument;
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
   * Returns this Note's duration (in beats).
   *
   * @return this Note's duration
   */
  public int getDuration() {
    return this.duration;
  }

  public int getVolume() {
    return volume;
  }

  public int getInstrument() {
    return instrument;
  }

  @Override
  public String toString() {
    return pitch.getStringWithOctave(this.octave);
  }

  /**
   * Compares by octave (lower is lower), then by pitch (ordered least to greatest int this.PITCH),
   * then by duration (lower is lower)
   */
  @Override
  public int compareTo(Note that) {
    if (this.octave == that.octave) {
      if (this.pitch == that.pitch) {
        return Integer.compare(this.duration, that.duration);
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
            && this.duration == that.duration;
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, pitch, octave, duration);
  }

  /**
   * Determines if this note is playing during the given beat
   *
   * @param beat the beat to check
   * @return true if this note starts or continues during this beat, false otherwise
   */
  public boolean playsDuring(int beat) {
    return (this.start <= beat) && (this.start + this.duration >= beat);
  }
}
