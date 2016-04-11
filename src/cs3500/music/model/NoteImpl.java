package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a musical note, consisting of a pitch and octave.
 */
public final class NoteImpl implements Note {

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
   * Constructs a note with given arguments
   *
   * @param start      the beat where the note starts from [0,..)
   * @param pitch      the pitch of the note on the chromatic scale
   * @param octave     the octave of the note from [0,100)
   * @param duration   the duration of the note beyond its first beat from [0,..)
   * @param volume     the volume to play the NoteImpl
   * @param instrument the MIDI instrument to play the note with
   * @throws IllegalArgumentException if any of the ranges are violated
   */
  public NoteImpl(Pitch pitch, int octave, int start, int duration, int instrument, int volume) {
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

  @Override
  public Pitch getPitch() {
    return this.pitch;
  }

  @Override
  public int getOctave() {
    return this.octave;
  }

  @Override
  public int getStart() {
    return this.start;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }


  @Override
  public int getInstrument() {
    return instrument;
  }

  @Override
  public int getVolume() {
    return volume;
  }

  @Override
  public int getMidiPitch() {
    return this.pitch.ordinal() + 12*(this.octave + 1);
  }

  @Override
  public boolean playsDuring(int beat) {
    return (this.start <= beat) && (this.start + this.duration > beat);
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
    if (this.octave == that.getOctave()) {
      if (this.pitch == that.getPitch()) {
        if (this.start == that.getStart()) {
          if (this.duration == that.getDuration()) {
            if (this.instrument == that.getInstrument()) {
              return Integer.compare(this.volume, that.getVolume());
            } else {
              return Integer.compare(this.instrument, that.getInstrument());
            }
          } else {
            return Integer.compare(this.duration, that.getDuration());
          }
        } else {
          return Integer.compare(this.start, that.getStart());
        }
      } else {
        return this.pitch.compareTo(that.getPitch());
      }
    } else {
      return Integer.compare(this.octave, that.getOctave());
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Note)) {
      return false;
    }
    NoteImpl that = (NoteImpl) obj;
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
