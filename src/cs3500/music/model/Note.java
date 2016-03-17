package cs3500.music.model;

import java.util.Objects;

/**
 * Represents a musical note, consisting of a pitch and octave.
 */
public class Note implements Comparable<Note> {

  private Pitch pitch;
  private int start;
  private int duration;

  /**
   * Constructs a Note
   *
   * @param pitch the name of the musical pitch
   * @param beginning the start time of the note
   * @param beats the duration of the note
   */
  public Note(Pitch pitch, int beginning, int beats) {
    ensureNoteValues(beginning, beats);
    this.pitch = Objects.requireNonNull(pitch);
    this.start = beginning;
    this.duration = beats;
  }

  /**
   * Ensures that the beginning time is not negative. Also ensures that the beats are not less
   * than 1 (a note with 0 beats is meaningless).
   *
   * @param beginning the start time of the note
   * @param beats the duration of the note
   * @throws IllegalArgumentException if {@code beginning} is negative, or if {@code beats} is less
   * than 1.
   */
  private static void ensureNoteValues(int beginning, int beats) {
    if (beginning < 0 || beats < 1) {
      throw new IllegalArgumentException("Must be non-negative");
    }
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
   * Returns this Note's start time.
   *
   * @return this Note's start time
   */
  public int getStart() {
    return this.start;
  }

  /**
   * Returns this Note's duration (in beats).
   *
   * @return this Note's duration
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Changes the pitch of this note to the given pitch.
   *
   * @param newPitch the new pitch for this note
   */
  public void changePitch(Pitch newPitch) {
    this.pitch = Objects.requireNonNull(newPitch);
  }

  /**
   * Changes the starting time of this Note.
   *
   * @param newStart the new starting time for this Note.
   * @throws IllegalArgumentException if the newStart is negative.
   */
  public void changeStart(int newStart) {
    if (newStart < 0) { throw new IllegalArgumentException("Must be non-negative"); }
    this.start = newStart;
  }

  /**
   * Changes the duration of this Note.
   *
   * @param newDuration the new length of this Note.
   * @throws IllegalArgumentException if the given duration is less than 1.
   */
  public void changeDuration(int newDuration) {
    if (newDuration < 1) { throw new IllegalArgumentException("Must be greater than zero"); }
    this.duration = newDuration;
  }

  @Override
  public String toString() {
    return pitch.toString();
  }

  @Override
  public int compareTo(Note that) {
    int pitchCompared = this.pitch.compareTo(that.getPitch());
    if (pitchCompared != 0) { return pitchCompared; }
    else {
      int startCompared = Integer.compare(this.start, that.getStart());
      if (startCompared != 0) { return startCompared; }
      else {
        return Integer.compare(this.duration, that.getDuration());
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof Note)) { return false; }
    Note that = (Note) obj;
    return this.pitch.equals(that.pitch) && this.start == that.start
      && this.duration == that.duration;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pitch, start, duration);
  }
}
