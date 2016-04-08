package cs3500.music.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * Mock model for testing controller.
 */
public class MockModel implements IMusicModel {
  StringBuilder log;
  HashSet<Note> notes = new HashSet<>();


  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addNote(Note note) {
    this.notes.add(note);
    log.append(String.format("AddNote: Pitch: %d, Beat: %d, Length: %d\n", note.getMidiPitch(),
            note.getStart(), note.getDuration()));
  }

  @Override
  public void removeNote(Note note) {
    this.notes.remove(note);
    log.append(String.format("RemoveNote: Pitch: %d, Beat: %d\n", note.getMidiPitch(), note
            .getStart()));
  }

  @Override
  public void editNote(Note note, Note newNote) {
    log.append(String.format("MoveNote: Pitch1: %d, Beat1: %d || Pitch2: %d, Beat2: %d\n", note
            .getMidiPitch(), note.getStart(), newNote.getMidiPitch(), newNote.getStart()));
  }

  @Override
  public int getTempo() {
    log.append("getTempo Called\n");
    return 0;
  }

  @Override
  public Set<Note> getNotes() {
    log.append("getNotes Called\n");
    return this.notes;
  }

  @Override
  public Note getLowestNote() {
    log.append("getLowestNote Called\n");
    return null;
  }

  @Override
  public Note getHighestNote() {
    log.append("getHighestNote Called\n");
    return null;
  }

  @Override
  public int finalBeat() {
    log.append("finalBeat Called\n");
    return 0;
  }

  @Override
  public List<String> getPitchRange() {
    log.append("getPitchRange Called\n");
    return null;
  }

  @Override
  public Set<Note> notesToPlay(int beat) {
    log.append("notesToPlay Called\n");
    return this.notes;
  }

  @Override
  public Set<Note> sustainedNotes(int beat) {
    log.append("sustainedNotes Called");
    return null;
  }

  @Override
  public void combinePieces(IMusicModel otherMusic) {
    log.append("combinePieces Called\n");
  }

  @Override
  public void addMusicToTail(IMusicModel otherMusic) {
    log.append("addMusicToTail Called\n");
  }
}
