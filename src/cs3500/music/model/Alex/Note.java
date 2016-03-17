package cs3500.music.model.Alex;


/**
 * A note in the chromatic scale.
 *
 * Created by Alex on 3/4/2016.
 */
public class Note implements Comparable<Note> {

  //Invariant: [0,..)
  final int beat;

  //Never null
  final PITCH pitch;

  //Invariant: [0,100)
  final int octave;

  //Invariant: [0,..)
  final int duration;

  /**
   * An enum representing each pitch in an octave of the chromatic scale.
   */
  enum PITCH {
    C("C"), C_SHARP("C#"), D("D"), D_SHARP("D#"), E("E"), F("F"), F_SHARP("F#"), G("G"), G_SHARP
            ("G#"), A("A"), A_SHARP("A#"), B("B");

    private final String representation;

    public String getRepresentation() {
      return representation;
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
        if (representation.length() == 1) {
          return "  " + representation + octave + " ";
        } else {
          return " " + representation + octave + " ";
        }
      } else {
        if (representation.length() == 1) {
          return " " + representation + octave + " ";
        } else {
          return " " + representation + octave;
        }
      }
    }

    PITCH(String representation) {
      this.representation = representation;
    }
  }

  /**
   * Constructs a note with given arguments
   *
   * @param beat     the beat where the note starts from [0,..)
   * @param pitch    the pitch of the note on the chromatic scale
   * @param octave   the octave of the note from [0,100)
   * @param duration the duration of the note beyond its first beat from [0,..)
   * @throws IllegalArgumentException if any of the ranges are violated
   */
  public Note(int beat, PITCH pitch, int octave, int duration) throws IllegalArgumentException {
    if (beat < 0 || octave < 0 || octave >= 100 || duration < 0) {
      throw new IllegalArgumentException("Impossible arguments!");
    }

    this.beat = beat;
    this.pitch = pitch;
    this.octave = octave;
    this.duration = duration;
  }

  /**
   * Compares by octave (lower is lower), then by pitch (ordered least to greatest int this.PITCH),
   * then by duration (lower is lower)
   */
  @Override
  public int compareTo(Note o) {
    if (this.octave == o.octave) {
      if (this.pitch == o.pitch) {
        return Integer.compare(this.duration, o.duration);
      } else {
        return this.pitch.compareTo(o.pitch);
      }
    } else {
      return Integer.compare(this.octave, o.octave);
    }
  }

  @Override
  public boolean equals(Object o) {
    Note that;
    return (o instanceof Note) &&
            (this.beat == (that = (Note) o).beat) &&
            (this.pitch == that.pitch) &&
            (this.octave == that.octave) &&
            (this.duration == that.duration);
  }

  @Override
  public int hashCode() {
    int result = beat;
    result = 31 * result + pitch.hashCode();
    result = 31 * result + octave;
    result = 31 * result + duration;
    return result;
  }

  /**
   * Determines if this note is playing during the given beat
   *
   * @param beat the beat to check
   * @return true if this note starts or continues during this beat, false otherwise
   */
  public boolean playsDuring(int beat) {
    return (this.beat <= beat) && (this.beat + this.duration >= beat);
  }
}
