package cs3500.music.model;

import java.util.List;
import java.util.Set;

/**
 * Specifies operations for any music model.
 */
public interface IMusicModel {

  /**
   * Adds the given note to the IMusicModel. If the given note is the same as one already in the
   * model (by Note.equals(...)), it will not be added.
   *
   * @param note the given note to add to the model
   */
  void addNote(Note note);

  /**
   * Removes this note from the IMusicModel. If this note is not in the model, an exception will be
   * thrown.
   *
   * @param note the note to remove from the model
   * @throws IllegalArgumentException if the given note is not in the model
   */
  void removeNote(Note note);

  /**
   * Exchanges the first given note with the second note.
   *
   * @param note  the note to change
   * @param newNote the note to replace the original note with
   * @throws IllegalArgumentException if the first note argument is not in the model
   */
  void editNote(Note note, Note newNote);

  /**
   * Returns the tempo of this piece.
   * @return the tempo of this piece
   */
  int getTempo();

  /**
   * Returns all the notes in this music model.
   * @return all the notes in this music model
   */
  Set<Note> getNotes();

  /**
   * Returns the lowest note in the entire model. Lowest note is determined first by octave. If
   * there are ties, they are broken by the lower pitch, then start, duration, instrument, and
   * volume.
   *
   * @return the lowest note in the model
   * @throws IllegalStateException if there are no notes in the model
   */
  Note getLowestNote();

  /**
   * Returns the highest note in the entire model. Highest note is determined first by octave. If
   * there are ties, they are broken by the higher pitch, then start, duration, instrument, and
   * volume.
   *
   * @return the highest note in the model
   * @throws IllegalStateException if there are no notes in the model
   */
  Note getHighestNote();

  /**
   * Determines the last beat to occur in this model.
   * @return the last beat to occur in the this model
   */
  int finalBeat();

  /**
   * Creates a list containing every pitch string between the lowest note and highest note
   * (inclusively). Each string will be a concatenation of the Pitch name an the octave.
   *
   * @return a list of all the Pitches from the lowest note to the highest note in this model.
   */
  List<String> getPitchRange();

  /**
   * Returns all the notes that start on the given beat.
   *
   * @param beat the desired point in time in the model to play associated notes
   * @return all the notes that start on the given beat.
   * @throws IllegalArgumentException if the given beat is negative
   */
  Set<Note> notesToPlay(int beat);

  /**
   * Overlays the given IMusicModel on top of this music model. Note collisions are dealt with in
   * the same way as addNote. Allows these models to be played simultaneously.
   *
   * @param otherMusic the music model to combine with this model
   */
  void combinePieces(IMusicModel otherMusic);

  /**
   * Adds the given music model to the end of this one, so that they can play consecutively. The
   * given model is appended starting at the beat where the last beat of the last note in this
   * music model occurs.
   *
   * @param otherMusic the music model to combine with this model
   */
  void addMusicToTail(IMusicModel otherMusic);
}
