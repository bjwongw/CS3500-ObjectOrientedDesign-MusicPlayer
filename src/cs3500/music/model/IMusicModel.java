package cs3500.music.model;

import java.util.List;
import java.util.Set;

/**
 * Specifies operations for any music model.
 */
public interface IMusicModel {

  /**
   * Returns all the Notes in this music model.
   *
   * @return all the Notes in this music model
   */
  Set<Note> getNotes();

  /**
   * Returns all the Notes that start on the given beat.
   *
   * @param beat the desired point in time in the model to play associated notes
   * @return all the Notes that start on the given beat.
   * @throws IllegalArgumentException if the given note is not in the model
   */
  Set<Note> notesToPlay(int beat);

  /**
   * Adds the given Note to the IMusicModel. If the given note is the same as one already in the
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
   * Edits the given note to have the given pitch, octave, and start time.
   *
   * @param note  the given note to edit
   * @param pitch the new pitch
   * @param start the new start time
   * @throws IllegalArgumentException if the given start value is negative
   */
  void editNote(Note note, Note.Pitch pitch, int octave, int start, int duration, int volume, int instrument);

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

  /**
   * Returns a String that represents all the Notes in this model. The result is also printed into
   * the console.
   *
   * <p>The return String is viewed as a table. The leftmost column shows all the beats in the
   * composition, from 0 up to the last beat played by the last note in the model. The next column
   * to the right is the lowest pitch in this model, with every ascending pitch in the columns to
   * the right. The rightmost pitch represented is the highest pitch in this model.</p>
   *
   * <p>The start of each note is marked by an 'X', which is placed on its respective start beat
   * and pitch. If the note is more than one beat long, each subsequent beat that the note is
   * sustained will be represented by a '|'. Rests in the piece are represented as whitespace.
   * </p>
   *
   * @return a String that represents all the Notes in this model
   */
  String printMusic();
}
