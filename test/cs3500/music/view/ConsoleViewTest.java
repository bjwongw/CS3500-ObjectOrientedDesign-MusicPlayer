package cs3500.music.view;

import org.junit.Test;

import java.io.IOException;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ConsoleView class
 */
public class ConsoleViewTest {

  /**
   * Test for the method initialize when there are no notes in the given model.
   */
  @Test
  public void testInitialize_noNotes() {
    Appendable actualOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel model = new GenericMusicModel();
    console.initialize(model);
    Appendable expectedOutput = new StringBuilder();
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  /**
   * Test for the method initialize when there is only one note in the given model.
   */
  @Test
  public void testInitialize_oneNote() throws IOException {
    Appendable actualOutput = new StringBuilder();
    Appendable expectedOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel testModel = new GenericMusicModel();
    IMusicModel actualModel = new GenericMusicModel();

    testModel.addNote(new Note(Note.Pitch.C, 5, 3, 1, 6, 2));
    actualModel.addNote(new Note(Note.Pitch.C, 5, 3, 1, 6, 2));

    console.initialize(actualModel);
    expectedOutput.append(testModel.printMusic());
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  /**
   * Test for the method initialize with several notes.
   */
  @Test
  public void testInitialize_severalNotes() throws IOException {
    Appendable actualOutput = new StringBuilder();
    Appendable expectedOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel testModel = new GenericMusicModel();
    IMusicModel actualModel = new GenericMusicModel();

    testModel.addNote(new Note(Note.Pitch.C, 5, 3, 1, 6, 2));
    actualModel.addNote(new Note(Note.Pitch.C, 5, 3, 1, 6, 2));

    testModel.addNote(new Note(Note.Pitch.D, 3, 1, 5, 6, 2));
    actualModel.addNote(new Note(Note.Pitch.D, 3, 1, 5, 6, 2));

    testModel.addNote(new Note(Note.Pitch.F, 3, 0, 7, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.F, 3, 0, 7, 0, 0));

    testModel.addNote(new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0));

    testModel.addNote(new Note(Note.Pitch.A_SHARP, 4, 14, 10, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.A_SHARP, 4, 14, 10, 0, 0));

    testModel.addNote(new Note(Note.Pitch.B, 10, 33, 7, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.B, 10, 33, 7, 0, 0));

    console.initialize(actualModel);
    expectedOutput.append(testModel.printMusic());
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  /**
   * Test for the method initialize with a full composition.
   */
  @Test
  public void testInitialize_fullComposition() throws IOException {
    Appendable actualOutput = new StringBuilder();
    Appendable expectedOutput = new StringBuilder();
    IMusicModel testModel = new GenericMusicModel();
    IMusicModel actualModel = new GenericMusicModel();
    for (int p = 0; p < 12; p++) {
      for (int o = 0; o < 3; o++) {
        for (int d = 1; d < 3; d++) {
          for (int i = 0; i < 2; i++) {
            for (int v = 0; v < 2; v++) {
              testModel.addNote(new Note(Note.Pitch.values()[p], o, p + o + d + i + v, d, i, v));
              actualModel.addNote(new Note(Note.Pitch.values()[p], o, p + o + d + i + v, d, i, v));
            }
          }
        }
      }
    }
    IMusicView console = new ConsoleView(actualOutput);
    console.initialize(actualModel);
    expectedOutput.append(testModel.printMusic());
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }
}
