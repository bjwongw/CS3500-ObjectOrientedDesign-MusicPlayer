package cs3500.music.model.Alex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a model for a song in the chromatic scale (more information in {@link
 * IChromaticModel}.
 *
 * <p>Class invariant: Notes may not overlap</p>
 *
 * Created by Alex on 3/4/2016.
 */
public class ChromaticModel implements IChromaticModel {

  //stores each note in this composition by its beat, never null, though its values may be
  protected final Map<Integer, List<Note>> notes;

  //length of this composition in beats
  protected int length;

  /**
   * Constructs a default ChromaticModel
   */
  ChromaticModel() {
    notes = new HashMap<>();
    length = 0;
  }

  /**
   * {@inheritDoc}
   *
   * @param beat     [0,..) beat where the note should start
   * @param duration [0,..) number of beats the note should be played past the initial beat
   * @param pitch    pitch of the note in the chromatic scale
   * @param octave   [0,100) octave of the note
   * @return this, for convenience
   * @throws IllegalArgumentException on bad input based on above
   * @throws IllegalStateException    if there is already a note of the same pitch and octave
   *                                  playing during that beat
   */
  @Override
  public IChromaticModel addNote(int beat, int duration, Note.PITCH pitch, int octave) throws
          IllegalArgumentException, IllegalStateException {
    checkBeatDurationPitchOctave(beat, duration, pitch, octave);

    if (!checkNoNote(beat, pitch, octave)) {
      throw new IllegalStateException("There is already a note being played there!");
    }

    for (int i = 0; i <= duration; i++) {
      if (notes.get(beat + i) == null) {
        notes.put(beat + i, new ArrayList<Note>());
      }
      notes.get(beat + i).add(new Note(beat, pitch, octave, duration));
      if ((beat + i) > this.length) {
        this.length += 1;
      }
    }

    return this;
  }

  /**
   * Checks whether there is no note playing of given pitch and octave at the given beat.
   *
   * @param beat   the beat to check
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   */
  private boolean checkNoNote(int beat, Note.PITCH pitch, int octave) {
    List<Note> l = this.getNotesAtBeat(beat);
    for (Note n : l) {
      if (pitch == n.pitch && octave == n.octave) {
        return false;
      }
    }
    return true;
  }

  @Override
  public IChromaticModel removeNote(int beat, Note.PITCH pitch, int octave) throws
          IllegalArgumentException {

    checkBeatDurationPitchOctave(beat, 0, pitch, octave);

    List<Note> l = this.notes.get(beat);
    if (l == null) {
      throw new IllegalArgumentException("Note to remove not found");
    }

    int i = 0;
    for (Note n : l) {
      if (beat == n.beat && pitch == n.pitch && octave == n.octave) {
        l.remove(i);
        return this;
      }
    }

    throw new IllegalArgumentException("Note to remove not found");
  }

  /**
   * Verifies that the given arguments are valid, throwing an illegal argument exception otherwise
   *
   * @param beat     must be [0,..)
   * @param duration must be [0,..)
   * @param pitch    must not be NULL
   * @param octave   must be [0,100)
   * @throws IllegalArgumentException if any of the above is false
   */
  private void checkBeatDurationPitchOctave(int beat, int duration, Note.PITCH pitch, int
          octave) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Referenced a negative beat!");
    }
    if (duration < 0) {
      throw new IllegalArgumentException("Input a negative duration!");
    }
    if (octave < 0 || octave >= 100) {
      throw new IllegalArgumentException("Octave is negative, or above 99!");
    }
    if (pitch == null) {
      throw new IllegalArgumentException("Input a null pitch!");
    }
  }

  @Override
  public List<Note> getNotesAtBeat(int beat) {
    if (this.notes.get(beat) == null) {
      return new ArrayList<>();
    } else {
      List<Note> l = new ArrayList<>(this.notes.get(beat));
      Collections.sort(l);
      return l;
    }
  }

  @Override
  public Map<Integer, List<Note>> getNotes() {
    return new HashMap<>(this.notes);
  }

  @Override
  public String getStringRepresentation() {

    if (this.notes.isEmpty()) {
      return "Your grammy is just a few clicks away! (There's nothing here...)";
    }

    StringBuilder s = new StringBuilder();

    s.append(this.leftColumn(-1));
    s.append(this.pitchRow()).append("\n");

    stringRepresentationNoteFiller(s);

    return s.toString();
  }

  /**
   * Fills the rest of the {@link cs3500.hw05.ChromaticModel#getStringRepresentation()} by producing each row
   * with its beat number prefix, and X's / |'s where each beat is starting/playing under its
   * pitch/octave.
   *
   * @param s the string builder to add output onto
   */
  private void stringRepresentationNoteFiller(StringBuilder s) {

    Note lowest = this.getLowestNote();

    for (int i = 0; i <= this.length; i++) {
      s.append(this.leftColumn(i));

      int doneSpaces = 0;
      for (Note n : this.getNotesAtBeat(i)) {
        int spaces = (((n.pitch.ordinal() - lowest.pitch.ordinal()) % 12) + 12 * (n.octave -
                lowest.octave)) - doneSpaces;
        while (spaces > 0) {
          s.append("     ");
          spaces -= 1;
          doneSpaces += 1;
        }
        if (n.beat == i) {
          s.append("  X  ");
        } else {
          s.append("  |  ");
        }
        doneSpaces += 1;
      }
      while (doneSpaces * 5 < (this.pitchRow().length() - 5)) {
        s.append("     ");
        doneSpaces += 1;
      }

      s.append("\n");
    }
  }

  /**
   * Provides the first row of the string representation as described in {@link
   * ChromaticModel#getStringRepresentation()} without the initial spaces
   *
   * @return the first row
   */
  private String pitchRow() {

    int pitch = this.getLowestNote().pitch.ordinal();
    int octave = this.getLowestNote().octave;
    Note.PITCH targetPitch = this.getHighestNote().pitch;
    int targetOctave = this.getHighestNote().octave;

    String s = "";
    while (true) {
      s = s + Note.PITCH.values()[pitch].getPaddedRepresentation(octave);

      if (Note.PITCH.values()[pitch] == targetPitch && octave == targetOctave) {
        break;
      }

      pitch += 1;
      if (pitch == 12) {
        octave += 1;
        pitch = 0;
      }
    }
    return s;
  }

  /**
   * Provides the left prefix of a given row in {@link cs3500.hw05.ChromaticModel#getStringRepresentation()}
   *
   * @param beat the row number, if negative, provides the prefix with just spaces
   * @return the left prefix consisting of the row number with enough white space on the left to
   * allow any beat in this song to fit
   */
  private String leftColumn(int beat) {
    int width = Integer.toString(this.length).length();
    String s;
    if (beat >= 0) {
      s = Integer.toString(beat);
    } else {
      s = "";
    }
    while (s.length() < width) {
      s = " " + s;
    }

    return s;
  }

  /**
   * Returns the lowest note in the entire piece (the first one, if there are multiple)
   *
   * @return the lowest note
   */
  private Note getLowestNote() {
    Note lowest = new Note(0, Note.PITCH.B, 99, 0);
    for (Map.Entry<Integer, List<Note>> e : this.notes.entrySet()) {
      for (Note n : e.getValue()) {
        if ((n.octave < lowest.octave) || ((n.octave == lowest.octave) && (n.pitch.compareTo
                (lowest.pitch) < 0))) {
          lowest = n;
        }
      }
    }
    return lowest;
  }

  /**
   * Returns the highest note in the entire piece (the first one, if there are multiple)
   *
   * @return the highest note
   */
  private Note getHighestNote() {
    Note highest = new Note(0, Note.PITCH.C, 0, 0);
    for (Map.Entry<Integer, List<Note>> e : this.notes.entrySet()) {
      for (Note n : e.getValue()) {
        if ((n.octave > highest.octave) || ((n.octave == highest.octave) && (n.pitch.compareTo
                (highest.pitch) > 0))) {
          highest = n;
        }
      }
    }
    return highest;
  }

  @Override
  public int getLength() {
    return this.length;
  }

  /**
   * {@inheritDoc}
   *
   * <p>Confilicting notes will not be added</p>
   *
   * @param o the other ChromaticModel
   */
  @Override
  public IChromaticModel merge(IChromaticModel o) {

    for (Map.Entry<Integer, List<Note>> e : o.getNotes().entrySet()) {
      for (Note n : e.getValue()) {
        if (n.beat == e.getKey()) {
          try {
            this.addNote(n.beat, n.duration, n.pitch, n.octave);
          } catch (IllegalStateException exp) {

          }
        }
      }
    }

    return this;
  }

  @Override
  public IChromaticModel append(IChromaticModel o) {

    for (Map.Entry<Integer, List<Note>> e : o.getNotes().entrySet()) {
      for (Note n : e.getValue()) {
        if (n.beat == e.getKey()) {
          this.addNote(n.beat + this.length, n.duration, n.pitch, n.octave);
        }
      }
    }
    return this;
  }
}
