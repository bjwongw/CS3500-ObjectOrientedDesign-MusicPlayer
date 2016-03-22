package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Tests for the GenericMusicModel class
 */
public class GenericMusicModelTest {
  IMusicModel musicModel1;
  IMusicModel musicModel2;
  Note c0;
  Note c4;
  Note cSharp3;
  Note cSharp7;
  Note d3;
  Note d6;
  Note dSharp0;
  Note dSharp9;
  Note e1;
  Note e2;
  Note f3;
  Note f5;
  Note fSharp4;
  Note fSharp9;
  Note g0;
  Note g6;
  Note gSharp5;
  Note gSharp7;
  Note a8;
  Note a10;
  Note aSharp4;
  Note aSharp9;
  Note b10;

  /**
   * Initial data, prevents the originals from being mutated.
   */
  public void initData() {
    musicModel1 = new GenericMusicModel();
    musicModel2 = new GenericMusicModel();
    c0 = new Note(Note.Pitch.C, 0, 0, 4, 0, 0);
    c4 = new Note(Note.Pitch.C, 4, 3, 1, 0, 0);
    cSharp3 = new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0);
    cSharp7 = new Note(Note.Pitch.C_SHARP, 7, 10, 3, 0, 0);
    d3 = new Note(Note.Pitch.D, 3, 13, 4, 0, 0);
    d6 = new Note(Note.Pitch.D, 6, 0, 1, 0, 0);
    dSharp0 = new Note(Note.Pitch.D_SHARP, 0, 21, 3, 0, 0);
    dSharp9 = new Note(Note.Pitch.D_SHARP, 9, 8, 8, 0, 0);
    e1 = new Note(Note.Pitch.E, 1, 0, 1, 0, 0);
    e2 = new Note(Note.Pitch.E, 2, 2, 1, 0, 0);
    f3 = new Note(Note.Pitch.F, 3, 0, 7, 0, 0);
    f5 = new Note(Note.Pitch.F, 5, 5, 5, 0, 0);
    fSharp4 = new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0);
    fSharp9 = new Note(Note.Pitch.F_SHARP, 9, 6, 2, 0, 0);
    g0 = new Note(Note.Pitch.G, 0, 3, 1, 0, 0);
    g6 = new Note(Note.Pitch.G, 6, 10, 4, 0, 0);
    gSharp5 = new Note(Note.Pitch.G_SHARP, 5, 3, 1, 0, 0);
    gSharp7 = new Note(Note.Pitch.G_SHARP, 7, 40, 2, 0, 0);
    a8 = new Note(Note.Pitch.A, 8, 32, 2, 0, 0);
    a10 = new Note(Note.Pitch.A, 10, 32, 2, 0, 0);
    aSharp4 = new Note(Note.Pitch.A_SHARP, 4, 14, 10, 0, 0);
    aSharp9 = new Note(Note.Pitch.A_SHARP, 9, 22, 9, 0, 0);
    b10 = new Note(Note.Pitch.B, 10, 33, 7, 0, 0);
  }

  /**
   * Tests the method addNote in the case that there is note set for the given note's associated
   * pitch.
   */
  @Test
  public void testAddNote_emptyModel() {
    initData();
    musicModel1.addNote(c0);
    Set<Note> noteSet = musicModel1.getNotes();
    assertTrue(noteSet.contains(c0));
  }

  /**
   * Tests the method addNote when the given note to add is a duplicate of a note already in the
   * model.
   */
  @Test
  public void testAddNote_duplicateNotes() {
    initData();
    musicModel1.addNote(d3);
    Set<Note> noteSet = musicModel1.getNotes();
    assertEquals(1, noteSet.size());

    musicModel1.addNote(d3); // duplicate should be ignored
    noteSet = musicModel1.getNotes();
    assertEquals(1, noteSet.size());
  }

  /**
   * Tests the method addNote when the given note starts playing while another note of the same
   * pitch would still be playing.
   */
  @Test
  public void testAddNote_addDuringNote() {
    initData();
    Note c0_1_6 = new Note(Note.Pitch.C, 0, 1, 6, 0, 0);
    musicModel1.addNote(c0_1_6);
    assertEquals(6, c0_1_6.getDuration());
    // the following note starts while c0_1_6 is playing
    Note c0_3_4 = new Note(Note.Pitch.C, 0, 3, 4, 0, 0);
    musicModel1.addNote(c0_3_4);
    assertEquals(6, c0_1_6.getDuration());

    Set<Note> noteSet = musicModel1.getNotes();
    // both notes are in the model
    assertTrue(noteSet.contains(c0_1_6));
    assertTrue(noteSet.contains(c0_3_4));
  }

  /**
   * Tests the method addNote when the given note overlaps onto a following note of the same pitch
   * in the model.
   */
  @Test
  public void testAddNote_givenNotePlaysDuringOtherNote() {
    initData();
    Note d3_5_2 = new Note(Note.Pitch.D, 3, 5, 2, 0, 0);
    musicModel1.addNote(d3_5_2);
    // the following note is playing through the start of d3_5_2
    Note d3_3_5 = new Note(Note.Pitch.D, 3, 3, 5, 0, 0);
    assertEquals(5, d3_3_5.getDuration());
    musicModel1.addNote(d3_3_5);
    // d3_3_5 is kept the same because it is not equivalent to the new note
    assertEquals(5, d3_3_5.getDuration());

    Set<Note> noteSet = musicModel1.getNotes();
    // both notes are in the model
    assertTrue(noteSet.contains(d3_5_2));
    assertTrue(noteSet.contains(d3_3_5));
  }

  /**
   * Tests the method addNote in the event that the given note has the same pitch and start time as
   * another note in the model, but the given note has a shorter duration.
   */
  @Test
  public void testAddNote_notesHaveSameStart_longerNoteInModel() {
    initData();
    Note aSharp4_0_7 = new Note(Note.Pitch.A_SHARP, 4, 0, 7, 0, 0);
    Note aSharp4_0_3 = new Note(Note.Pitch.A_SHARP, 4, 0, 3, 0, 0);
    musicModel1.addNote(aSharp4_0_7);
    assertTrue(musicModel1.getNotes().contains(aSharp4_0_7));
    musicModel1.addNote(aSharp4_0_3);
    // aSharp4_0_3 is different from aSharp4_0_7 so it is maintained
    assertTrue(musicModel1.getNotes().contains(aSharp4_0_3));
  }

  /**
   * Tests the method addNote in the event that the given note has the same pitch and start time as
   * another note in the model, but the given note has a longer duration.
   */
  @Test
  public void testAddNote_notesHaveSameStart_shorterNoteInModel() {
    initData();
    Note d10_4_2 = new Note(Note.Pitch.D, 10, 4, 2, 0, 0);
    Note d10_4_3 = new Note(Note.Pitch.D, 10, 4, 3, 0, 0);
    musicModel1.addNote(d10_4_2);
    assertTrue(musicModel1.getNotes().contains(d10_4_2));
    musicModel1.addNote(d10_4_3);
    // d10_4_3 should be in the model because it had a longer duration than d10_4_2
    assertTrue(musicModel1.getNotes().contains(d10_4_3));

    assertTrue(musicModel1.getNotes().contains(d10_4_2));
  }

  /**
   * Test for addNote, ensuring that all notes that have been added to the model have in fact
   * been added.
   */
  @Test
  public void testAddNote() {
    initData();
    Set<Note> set = new HashSet<>();

    for (int i = 1; i < 10; i++) {
      for (int j = 1; j < 10; j++) {
        for (int k = 1; k < 10; k++) {
          for (int t = 1; t < 10; t++) {
            for (int d = 1; d < 10; d++) {
              musicModel1.addNote(new Note(Note.Pitch.A, i, j, k, d, t));
              set.add(new Note(Note.Pitch.A, i, j, k, d, t));
            }
          }
        }
      }
    }

    for(Note n : musicModel1.getNotes()) {
      assertTrue(set.contains(n));
    }
  }

  /**
   * Test for the method removeNote. Ensures that you cannot remove a note if there are no notes in
   * the model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidNoteRemoval_noNotes() {
    initData();
    musicModel1.removeNote(c0);
  }

  /**
   * Test for the method removeNote. Ensures that you cannot remove a note that is not in the
   * model.
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidNoteRemoval_noteNotInModel() {
    initData();
    musicModel1.addNote(c0);
    musicModel1.addNote(d3);
    musicModel1.removeNote(d6);
  }

  /**
   * Tests for the method removeNote.
   */
  @Test
  public void testRemoveNote() {
    initData();
    musicModel1.addNote(aSharp4);
    musicModel1.addNote(d3);
    musicModel1.addNote(b10);
    Set<Note> noteSet = musicModel1.getNotes();

    // all the inserted notes are in fact in the model
    assertTrue(noteSet.contains(aSharp4));
    assertTrue(noteSet.contains(d3));
    assertTrue(noteSet.contains(b10));

    musicModel1.removeNote(d3);
    noteSet = musicModel1.getNotes();
    assertFalse(noteSet.contains(d3)); // d3 has been removed
    assertTrue(noteSet.contains(aSharp4)); // this is still in the model
    assertTrue(noteSet.contains(b10)); // this is still in the model

    musicModel1.removeNote(aSharp4);
    noteSet = musicModel1.getNotes();
    assertFalse(noteSet.contains(aSharp4)); // aSharp4 has been removed
    assertTrue(noteSet.contains(b10)); // this is still in the model
  }

  /**
   * Tests for the method editNote.
   */
  @Test
  public void testEditNote() {
    initData();
    musicModel1.addNote(cSharp7);
    assertEquals(Note.Pitch.C_SHARP, cSharp7.getPitch());
    assertEquals(10, cSharp7.getStart());
    assertEquals(3, cSharp7.getDuration());
    musicModel1.editNote(cSharp7, aSharp4);
    assertEquals(Note.Pitch.C_SHARP, cSharp7.getPitch());
    assertEquals(10, cSharp7.getStart());
    assertEquals(3, cSharp7.getDuration());
  }

  /**
   * Tests for the method getTempo
   */
  @Test
  public void testGetTempo() {

  }

  /**
   * Tests the method getNotes when the model is empty
   */
  @Test
  public void testGetNotes_emptyModel() {
    IMusicModel model = new GenericMusicModel();
    Set<Note> noteSet = new HashSet<>();
    assertEquals(noteSet, model.getNotes());
  }

  /**
   * Test for the method getNotes
   */
  @Test
  public void testGetNotes() {
    initData();
    List<Note> noteList = new ArrayList<>();
    List<Note> result;
    musicModel1.addNote(c0);
    noteList.add(c0);
    musicModel1.addNote(c4);
    noteList.add(c4);
    musicModel1.addNote(e1);
    noteList.add(e1);
    musicModel1.addNote(g6);
    noteList.add(g6);
    result = new ArrayList<>(musicModel1.getNotes());
    assertNotEquals(noteList, result);
    Collections.sort(noteList); // getNotes returns a sorted list because the notes are stored in
    // a treeMap. Therefore, noteList needs to be sorted to be equal
    Collections.sort(result);
    assertEquals(noteList, result);
  }

  /**
   * Tests for the method getLowestNote
   */
  @Test
  public void testGetLowestNote() {

  }

  /**
   * Tests for the method getHighestNote
   */
  @Test
  public void testGetHighestNote() {

  }

  /**
   * Tests for the method finalBeat
   */
  @Test
  public void testFinalBeat() {

  }

  /**
   * Tests for the method getPitchRange.
   */
  @Test
  public void testGetPitchRange() {

  }

  /**
   * Test for the method notesToPlay. Ensures that you cannot give a negative integer as the input
   * beat.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNotesToPlay_negativeBeat() {
    initData();
    musicModel1.addNote(dSharp9);
    musicModel1.notesToPlay(-1);
  }

  /**
   * Test for the method notesToPlay when there are not matching notes/no notes in the model.
   */
  @Test
  public void testNotesToPlay_noNotes() {
    initData();
    Set<Note> noteList = musicModel1.notesToPlay(3);
    assertEquals(0, noteList.size());
  }

  /**
   * Tests for the method notesToPlay, fetching the notes at beat 0.
   */
  @Test
  public void testNotesToPlay_beat0() {
    initData();
    musicModel1.addNote(c0);
    musicModel1.addNote(aSharp4);
    musicModel1.addNote(e1);
    Set<Note> notesBeat0 = musicModel1.notesToPlay(0);
    assertEquals(2, notesBeat0.size());
    assertTrue(notesBeat0.contains(c0));
    assertTrue(notesBeat0.contains(e1));
  }

  /**
   * Tests for the method notesToPlay, fetching the notes at beat 10.
   */
  @Test
  public void testNotesToPlay_beat10() {
    initData();
    Note e5_10_3 = new Note(Note.Pitch.E, 5, 10, 3, 0, 0);
    Note aSharp6_10_2 = new Note(Note.Pitch.A_SHARP, 6, 10, 2, 0, 0);
    Note dSharp0_10_1 = new Note(Note.Pitch.D_SHARP, 0, 10, 1, 0, 0);
    musicModel1.addNote(e5_10_3);
    musicModel1.addNote(cSharp3);
    musicModel1.addNote(f3);
    musicModel1.addNote(aSharp6_10_2);
    musicModel1.addNote(f5);
    musicModel1.addNote(dSharp0_10_1);
    Set<Note> notesBeat10 = musicModel1.notesToPlay(10);
    assertEquals(3, notesBeat10.size());
    assertTrue(notesBeat10.contains(e5_10_3));
    assertTrue(notesBeat10.contains(aSharp6_10_2));
    assertTrue(notesBeat10.contains(dSharp0_10_1));
  }

  /**
   * Tests for the method combinePieces when one or both of the models are empty.
   */
  @Test
  public void testCombinePieces_emptyModels() {
    initData();
    assertEquals(0, musicModel1.getNotes().size());
    musicModel1.combinePieces(musicModel2);
    assertEquals(0, musicModel1.getNotes().size());

    musicModel2.addNote(c0);
    assertEquals(0, musicModel1.getNotes().size());
    assertEquals(1, musicModel2.getNotes().size());

    musicModel1.combinePieces(musicModel2);
    assertEquals(1, musicModel1.getNotes().size());
  }

  /**
   * Test for the method combinePieces when the two models share the same note.
   */
  @Test
  public void testCombinePieces_duplicateNotes() {
    initData();
    musicModel1.addNote(c0);
    Set<Note> noteSet = musicModel1.getNotes();
    assertEquals(1, noteSet.size());

    musicModel2.addNote(c0);
    musicModel1.combinePieces(musicModel2);
    noteSet = musicModel1.getNotes();
    assertEquals(1, noteSet.size()); // no change because the only notes in each model were c0
  }

  /**
   * Tests for the method combinePieces.
   */
  @Test
  public void testCombinePieces() {
    initData();
    assertEquals(0, musicModel1.getNotes().size());
    musicModel1.addNote(d6);
    musicModel1.addNote(aSharp4);
    musicModel1.addNote(e1);
    assertEquals(3, musicModel1.getNotes().size());

    assertEquals(0, musicModel2.getNotes().size());
    musicModel2.addNote(c0);
    musicModel2.addNote(d6);
    musicModel2.addNote(b10);
    assertEquals(3, musicModel2.getNotes().size());

    musicModel1.combinePieces(musicModel2);
    assertEquals(5, musicModel1.getNotes().size()); // 5, not 6, because d6 was in both models
    assertTrue(musicModel1.getNotes().contains(d6));
    assertTrue(musicModel1.getNotes().contains(aSharp4));
    assertTrue(musicModel1.getNotes().contains(e1));
    assertTrue(musicModel1.getNotes().contains(c0));
    assertTrue(musicModel1.getNotes().contains(b10));
  }

  /**
   * Test for the method addMusicToTail when both models are empty.
   */
  @Test
  public void testAddMusicToTail_emptyModels() {
    initData();
    assertEquals(0, musicModel1.getNotes().size());
    assertEquals(0, musicModel2.getNotes().size());
    musicModel1.addMusicToTail(musicModel2);
    assertEquals(0, musicModel1.getNotes().size());
  }

  /**
   * Tests for the method addMusicToTail when one of the models are empty.
   */
  @Test
  public void testAddMusicToTail_oneEmptyModel() {
    initData();
    musicModel1.addNote(d3);
    musicModel1.addNote(d6);
    assertEquals(2, musicModel1.getNotes().size());
    assertEquals(0, musicModel2.getNotes().size());
    musicModel1.addMusicToTail(musicModel2);
    assertEquals(2, musicModel1.getNotes().size());

    initData();
    assertEquals(0, musicModel1.getNotes().size());
    musicModel2.addNote(aSharp4);
    musicModel2.addNote(c4);
    musicModel2.addNote(gSharp5);
    assertEquals(3, musicModel2.getNotes().size());
    musicModel1.addMusicToTail(musicModel2);
    assertEquals(3, musicModel1.getNotes().size());
  }

  /**
   * Tests for the method addMusicToTail.
   */
  @Test
  public void testAddMusicToTail() {
    initData();
    musicModel1.addNote(b10);
    assertEquals(33, b10.getStart());
    assertEquals(7, b10.getDuration()); // end time = (33 + 7) - 1 = 39
    musicModel1.addNote(g0);
    assertEquals(3, g0.getStart());
    assertEquals(1, g0.getDuration()); // end time = (3 + 1) - 1 = 3
    // 39 is the last beat to be played in this model

    musicModel2.addNote(aSharp4);
    assertEquals(14, aSharp4.getStart()); // aSharp4's original start time
    musicModel2.addNote(a10);
    assertEquals(32, a10.getStart()); // aSharp4's original start time
    assertFalse(musicModel1.getNotes().contains(aSharp4)); // aSharp4 is not in musicModel1
    assertFalse(musicModel1.getNotes().contains(a10)); // a10 is not in musicModel1

    musicModel1.addMusicToTail(musicModel2);
  }

  /**
   * Test for the method printMusic when the model is empty.
   */
  @Test
  public void testPrintMusic_emptyModel() {
    initData();
    assertEquals("", musicModel1.printMusic());
  }

  /**
   * Test for the method printMusic for a model whose notes span two octaves.
   */
  @Test
  public void testPrintMusic_twoOctaves() {
    initData();
    musicModel1.addNote(new Note(Note.Pitch.C, 0, 0, 1, 0, 0));
    musicModel1.addNote(new Note(Note.Pitch.C_SHARP, 1, 1, 3, 0, 0));
    String output = "   C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1  C#1 \n"
            + "0  X                                                                   \n"
            + "1                                                                   X  \n"
            + "2                                                                   |  \n"
            + "3                                                                   |  ";
    assertEquals(output, musicModel1.printMusic());
  }

  /**
   * Test for the method printMusic for a model whose beats go into double digits.
   */
  @Test
  public void testPrintMusic_doubleDigitBeats() {
    initData();
    musicModel1.addNote(d3);
    musicModel1.addNote(cSharp3);
    musicModel1.addNote(e2);
    String output = "    E2   F2  F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3 \n"
            + " 0                                                       \n"
            + " 1                                                       \n"
            + " 2  X                                            X       \n"
            + " 3                                               |       \n"
            + " 4                                                       \n"
            + " 5                                                       \n"
            + " 6                                                       \n"
            + " 7                                                       \n"
            + " 8                                                       \n"
            + " 9                                                       \n"
            + "10                                                       \n"
            + "11                                                       \n"
            + "12                                                       \n"
            + "13                                                    X  \n"
            + "14                                                    |  \n"
            + "15                                                    |  \n"
            + "16                                                    |  ";
    assertEquals(output, musicModel1.printMusic());
  }

  /**
   * Test for the method printMusic to check that the output scales the pitches so that the lower
   * bound is the lowest pitch in the model, and the upper bound is the highest pitch in the model.
   */
  @Test
  public void testPrintMusic_scaledPitches() {
    initData();
    musicModel1.addNote(cSharp3);
    musicModel1.addNote(f3);
    musicModel1.addNote(new Note(Note.Pitch.E, 3, 0, 6, 0, 0));
    musicModel1.addNote(new Note(Note.Pitch.E, 3, 2, 3, 0, 0));
    String output = "  C#3   D3  D#3   E3   F3 \n" +
            "0                 X    X  \n" +
            "1                 |    |  \n" +
            "2  X              X    |  \n" +
            "3  |              |    |  \n" +
            "4                 |    |  \n" +
            "5                 |    |  \n" +
            "6                 |    |  ";
    assertEquals(output, musicModel1.printMusic());
  }
}
