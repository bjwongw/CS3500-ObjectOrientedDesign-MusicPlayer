package cs3500.music.model.Alex;

import java.util.List;
import java.util.Map;

/**
 * Interface to a model of a song in the chromatic scale. A song is a collection of notes (each
 * with a pitch in the chromatic scale, and an octave counting from 0) positioned on a linear axis
 * of beats.
 *
 * <p>The range of octaves is limited to [0,100), for practicality. This extends well beyond human
 * hearing.</p>
 * <p>There may not be negative beats, durations, nor octaves.</p>
 *
 *
 *
 * Created by Alex on 3/4/2016.
 */
public interface IChromaticModel extends Composition {


  /**
   * Adds a note of given pitch and octave to the model at the given beat. The note will last for
   * the given duration.
   *
   * @param beat     [0,..) beat where the note should start
   * @param duration [0,..) number of beats the note should be played past the initial beat
   * @param pitch    pitch of the note in the chromatic scale
   * @param octave   [0,100) octave of the note
   * @return this, for convenience
   * @throws IllegalArgumentException if beat, duration, or octave are out of their ranges; if
   *                                  pitch is null
   * @throws IllegalStateException    if the note cannot be added to the model due to some
   *                                  implementation specific limitation
   */
  IChromaticModel addNote(int beat, int duration, Note.PITCH pitch, int octave) throws
          IllegalArgumentException,
          IllegalStateException;

  /**
   * Removes the note (of given pitch and octave) that starts at the specified beat from the model
   *
   * @param beat   the beat to remove the note from
   * @param pitch  the pitch of the note
   * @param octave the octave of the note
   * @return this, for convenience
   * @throws IllegalArgumentException if no note exists at the indicated position
   */
  IChromaticModel removeNote(int beat, Note.PITCH pitch, int octave) throws
          IllegalArgumentException;

  /**
   * Returns a copy of a list of notes which are being played at the given beat (either starting,
   * or continuing).
   *
   * @param beat the beat to get notes from
   * @return the list of notes, sorted in ascending order by {@link Note#compareTo(Note)}, each
   * with a starting beat <= beat and a starting beat + duration >= beat
   */
  List<Note> getNotesAtBeat(int beat);

  /**
   * Returns a map between each beat in the song and the list of beats that are being played at
   * that beat (in the same manner as described in {@link IChromaticModel#getNotesAtBeat(int)}
   *
   * @return the map
   */
  Map<Integer, List<Note>> getNotes();

  /**
   * Merges o into this, adding all notes in o to this.
   * @param o the other ChromaticModel
   * @return this, for convenience
   */
  IChromaticModel merge(IChromaticModel o);

  /**
   * Appends o into this, adding all notes in o onto the end of this.
   * @param o the other ChromaticModel
   * @return this, for convenience
   */
  IChromaticModel append(IChromaticModel o);
}
