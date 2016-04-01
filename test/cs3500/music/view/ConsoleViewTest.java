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
   * Test for the method initialize for a model whose beats go into double digits.
   */
  @Test
  public void testPrintMusic_doubleDigitBeats() {
    Appendable actualOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel model = new GenericMusicModel();

    model.addNote(new Note(Note.Pitch.D, 3, 13, 4, 0, 0));
    model.addNote(new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0));
    model.addNote(new Note(Note.Pitch.E, 2, 2, 1, 0, 0));

    String expectedOutput = "    E2   F2  F#2   G2  G#2   A2  A#2   B2   C3  C#3   D3 \n"
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

    console.initialize(model);
    assertEquals(expectedOutput, actualOutput.toString());
  }


  /**
   * Test for the method initialize when there are no notes in the given model.
   */
  @Test
  public void testInitialize_noNotes() {
    Appendable actualOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel model = new GenericMusicModel();
    console.initialize(model);
    assertEquals("", actualOutput.toString());
  }

  /**
   * Test for the method initialize for a model whose notes span two octaves.
   */
  @Test
  public void testPrintMusic_twoOctaves() {
    Appendable actualOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel model = new GenericMusicModel();
    model.addNote(new Note(Note.Pitch.C, 0, 0, 1, 0, 0));
    model.addNote(new Note(Note.Pitch.C_SHARP, 1, 1, 3, 0, 0));

    String expectedOutput = "   C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1  "
      + "C#1 \n"
      + "0  X                                                                   \n"
      + "1                                                                   X  \n"
      + "2                                                                   |  \n"
      + "3                                                                   |  ";

    console.initialize(model);
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test for the method initialize to check that the output scales the pitches so that the lower
   * bound is the lowest pitch in the model, and the upper bound is the highest pitch in the model.
   */
  @Test
  public void testInitialize_scaledPitches() throws IOException {
    Appendable actualOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel actualModel = new GenericMusicModel();

    actualModel.addNote(new Note(Note.Pitch.F, 3, 0, 7, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.C_SHARP, 3, 2, 2, 0, 0));

    String expectedOutput =
      "  C#3   D3  D#3   E3   F3 \n" +
        "0                      X  \n" +
        "1                      |  \n" +
        "2  X                   |  \n" +
        "3  |                   |  \n" +
        "4                      |  \n" +
        "5                      |  \n" +
        "6                      |  ";

    console.initialize(actualModel);
    assertEquals(expectedOutput, actualOutput.toString());
  }

  /**
   * Test for the method initialize with several notes.
   */
  @Test
  public void testInitialize_severalNotes() throws IOException {
    Appendable actualOutput = new StringBuilder();
    IMusicView console = new ConsoleView(actualOutput);
    IMusicModel actualModel = new GenericMusicModel();

    actualModel.addNote(new Note(Note.Pitch.C, 5, 3, 1, 6, 2));
    actualModel.addNote(new Note(Note.Pitch.D, 3, 1, 5, 6, 2));
    actualModel.addNote(new Note(Note.Pitch.F, 3, 0, 7, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.F_SHARP, 4, 6, 2, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.A_SHARP, 4, 14, 10, 0, 0));
    actualModel.addNote(new Note(Note.Pitch.B, 2, 33, 7, 0, 0));

    String expectedOutput = "    B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   "
      + "C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4   C5 \n"
      + " 0                                X                                                      "
      + "                                           \n"
      + " 1                 X              |                                                      "
      + "                                           \n"
      + " 2                 |              |                                                      "
      + "                                           \n"
      + " 3                 |              |                                                      "
      + "                                        X  \n"
      + " 4                 |              |                                                      "
      + "                                           \n"
      + " 5                 |              |                                                      "
      + "                                           \n"
      + " 6                                |                                                      "
      + "          X                                \n"
      + " 7                                                                                       "
      + "          |                                \n"
      + " 8                                                                                       "
      + "                                           \n"
      + " 9                                                                                       "
      + "                                           \n"
      + "10                                                                                       "
      + "                                           \n"
      + "11                                                                                       "
      + "                                           \n"
      + "12                                                                                       "
      + "                                           \n"
      + "13                                                                                       "
      + "                                           \n"
      + "14                                                                                       "
      + "                              X            \n"
      + "15                                                                                       "
      + "                              |            \n"
      + "16                                                                                       "
      + "                              |            \n"
      + "17                                                                                       "
      + "                              |            \n"
      + "18                                                                                       "
      + "                              |            \n"
      + "19                                                                                       "
      + "                              |            \n"
      + "20                                                                                       "
      + "                              |            \n"
      + "21                                                                                       "
      + "                              |            \n"
      + "22                                                                                       "
      + "                              |            \n"
      + "23                                                                                       "
      + "                              |            \n"
      + "24                                                                                       "
      + "                                           \n"
      + "25                                                                                       "
      + "                                           \n"
      + "26                                                                                       "
      + "                                           \n"
      + "27                                                                                       "
      + "                                           \n"
      + "28                                                                                       "
      + "                                           \n"
      + "29                                                                                       "
      + "                                           \n"
      + "30                                                                                       "
      + "                                           \n"
      + "31                                                                                       "
      + "                                           \n"
      + "32                                                                                       "
      + "                                           \n"
      + "33  X                                                                                    "
      + "                                           \n"
      + "34  |                                                                                    "
      + "                                           \n"
      + "35  |                                                                                    "
      + "                                           \n"
      + "36  |                                                                                    "
      + "                                           \n"
      + "37  |                                                                                    "
      + "                                           \n"
      + "38  |                                                                                    "
      + "                                           \n"
      + "39  |                                                                                    "
      + "                                           ";

    console.initialize(actualModel);
    assertEquals(expectedOutput, actualOutput.toString());
  }
}
