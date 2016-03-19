package cs3500.music.model.Alex;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for ChromaticModel
 *
 * Created by Alex on 3/4/2016.
 */
public class ChromaticModelTest {

  IChromaticModel m = ChromaticModelCreator.create(ChromaticModelCreator.TYPE.CHROMATIC);


  @Test
  public void addNoteTest1() {
    m.addNote(0, 0, Note.PITCH.A, 0);
    List<Note> l = new ArrayList<>();
    for (int i = 5; i <= 10; i++) {
      assertTrue(m.getNotesAtBeat(5).isEmpty());
    }
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j <= 10; j++) {
        m.addNote(5, 5, Note.PITCH.values()[i], j);
        l.add(new Note(5, Note.PITCH.values()[i], j, 5));
      }
    }
    Collections.sort(l);
    for (int i = 5; i <= 10; i++) {
      assertTrue(m.getNotesAtBeat(5).equals(l));
    }
  }

  /**
   * Tests for bad input into addNote
   */
  @Test(expected = IllegalArgumentException.class)
  public void addNoteTestBadInput1() {
    m.addNote(-1, 0, Note.PITCH.A, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteTestBadInput2() {
    m.addNote(0, -1, Note.PITCH.A, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteTestBadInput3() {
    m.addNote(0, 0, Note.PITCH.A, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteTestBadInput4() {
    m.addNote(0, 0, Note.PITCH.A, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNoteTestBadInput5() {
    m.addNote(0, 0, null, 0);
  }

  /**
   * Tests for overlapping addNote
   */
  @Test(expected = IllegalStateException.class)
  public void addNoteTestOverlappingNote1() {
    m.addNote(0, 0, Note.PITCH.A, 0);
    m.addNote(0, 0, Note.PITCH.A, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void addNoteTestOverlappingNote2() {
    m.addNote(0, 5, Note.PITCH.A, 0);
    m.addNote(3, 5, Note.PITCH.A, 0);
  }

  /**
   * Tests for {@link ChromaticModel#removeNote(int, Note.PITCH, int)}
   */
  @Test
  public void removeNoteTest1() {
    for (int i = 5; i <= 10; i++) {
      assertTrue(m.getNotesAtBeat(5).isEmpty());
    }
    for (int i = 0; i < 12; i++) {
      for (int j = 0; j <= 10; j++) {
        m.addNote(5, 5, Note.PITCH.values()[i], j);
        m.removeNote(5, Note.PITCH.values()[i], j);
      }
    }
    for (int i = 5; i <= 10; i++) {
      assertTrue(m.getNotesAtBeat(5).isEmpty());
    }
  }

  /**
   * Tests for bad input into removeNote
   */
  @Test(expected = IllegalArgumentException.class)
  public void removeNoteTestBadInput1() {
    m.removeNote(-1, Note.PITCH.A, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteTestBadInput2() {
    m.removeNote(0, Note.PITCH.A, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteTestBadInput3() {
    m.removeNote(0, null, 0);
  }

  /**
   * Tests for no note there for removeNote
   */
  @Test(expected = IllegalArgumentException.class)
  public void removeNoteTestFail1() {
    m.removeNote(0, Note.PITCH.A, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeNoteTestFail2() {
    m.addNote(0, 5, Note.PITCH.A, 0);
    m.removeNote(1, Note.PITCH.A, 0);
  }

  /**
   * Tests for {@link ChromaticModel#getStringRepresentation()}
   */
  @Test
  public void getStringRepresentationTest1() {
    assertEquals("Your grammy is just a few clicks away! (There's nothing here...)",
            m.getStringRepresentation());
  }

  @Test
  public void getStringRepresentationTest2() {

    for (int i = 0; i <= 10; i++) {
      assertTrue(m.getNotesAtBeat(5).isEmpty());
    }
    for (int i = 4; i < 10; i++) {
      for (int j = 8; j <= 10; j++) {
        if (true) {
          m.addNote((i * j) % 50, (i + j) % 8, Note.PITCH.values()[i], j);
        }
      }
    }
    assertEquals("    E8   F8  F#8   G8  G#8   A8  A#8   B8   C9  C#9   D9  D#9   E9   F9  F#9  " +
                    " G9  G#9   A9  A#9   B9  C10  C#10 D10  D#10 E10  F10  F#10 G10  G#10 A10 " +
            "\n" +
                    " 0                                                                         " +
                    "                                                      X                 \n" +
                    " 1                                                                         " +
                    "                                                      |                 \n" +
                    " 2                                                                         " +
                    "                                                      |                 \n" +
                    " 3                                                                         " +
                    "                                                      |                 \n" +
                    " 4                                                                        X" +
                    "                                                      |                 \n" +
                    " 5                                                                        |" +
                    "                                                      |                 \n" +
                    " 6                 X                                                      |" +
                    "                                                      |                 \n" +
                    " 7                 |                                                      |" +
                    "                                                      |                 \n" +
                    " 8                 |                                                      |" +
                    "                                                                        \n" +
                    " 9                 |                                                      |" +
                    "                                                                        \n" +
                    "10                 |                                                      |" +
                    "                                                           X            \n" +
                    "11                 |                                                      |" +
                    "                                                                        \n" +
                    "12                 |                                                       " +
                    "                                                                        \n" +
                    "13                 |                                                       " +
                    "    X                                                                   \n" +
                    "14                      X                                                  " +
                    "                                                                        \n" +
                    "15                                                                         " +
                    "                                                                        \n" +
                    "16                                                                         " +
                    "                                                                        \n" +
                    "17                                                                         " +
                    "                                                                        \n" +
                    "18                                                                         " +
                    "                                                                        \n" +
                    "19                                                                         " +
                    "                                                                        \n" +
                    "20                                                                         " +
                    "                                                                X       \n" +
                    "21                                                                         " +
                    "                                                                |       \n" +
                    "22                           X                                             " +
                    "         X                                                              \n" +
                    "23                           |                                             " +
                    "         |                                                              \n" +
                    "24                                                                         " +
                    "                                                                        \n" +
                    "25                                                                         " +
                    "                                                                        \n" +
                    "26                                                                         " +
                    "                                                                        \n" +
                    "27                                                                         " +
                    "                                                                        \n" +
                    "28                                                                         " +
                    "                                                                        \n" +
                    "29                                                                         " +
                    "                                                                        \n" +
                    "30                                                                         " +
                    "                                                                     X  \n" +
                    "31                                                                         " +
                    "              X                                                      |  \n" +
                    "32  X                                                                      " +
                    "              |                                                      |  \n" +
                    "33  |                                                                      " +
                    "              |                                                         \n" +
                    "34  |                                                                      " +
                    "                                                                        \n" +
                    "35  |                                                                      " +
                    "                                                                        \n" +
                    "36  |                                                           X          " +
                    "                                                                        \n" +
                    "37                                                              |          " +
                    "                                                                        \n" +
                    "38                                                              |          " +
                    "                                                                        \n" +
                    "39                                                              |          " +
                    "                                                                        \n" +
                    "40       X                                                      |          " +
                    "                                                 X                        X" +
            "  \n" +
                    "41       |                                                      |          " +
                    "                                                 |                        |" +
            "  \n" +
                    "42       |                                                                 " +
                    "                                                 |                        |" +
            "  \n",
            m.getStringRepresentation());

    for (int i = 8; i < 10; i++) {
      for (int j = 8; j <= 10; j++) {
        if (true) {
          m.removeNote((i * j) % 50, Note.PITCH.values()[i], j);
        }
      }
    }

    assertEquals("    E8   F8  F#8   G8  G#8   A8  A#8   B8   C9  C#9   D9  D#9   E9   F9  F#9  " +
                    " G9  G#9   A9  A#9   B9  C10  C#10 D10  D#10 E10  F10  F#10 G10  G#10 A10 " +
            "\n" +
                    " 0                                                                         " +
            "        " +
                    "                                              X                 \n" +
                    " 1                                                                         " +
            "        " +
                    "                                              |                 \n" +
                    " 2                                                                         " +
            "        " +
                    "                                              |                 \n" +
                    " 3                                                                         " +
            "        " +
                    "                                              |                 \n" +
                    " 4                                                                        X" +
            "        " +
                    "                                              |                 \n" +
                    " 5                                                                        |" +
            "        " +
                    "                                              |                 \n" +
                    " 6                 X                                                      |" +
            "        " +
                    "                                              |                 \n" +
                    " 7                 |                                                      |" +
            "        " +
                    "                                              |                 \n" +
                    " 8                 |                                                      |" +
            "        " +
                    "                                                                \n" +
                    " 9                 |                                                      |" +
            "        " +
                    "                                                                \n" +
                    "10                 |                                                      |" +
            "        " +
                    "                                                   X            \n" +
                    "11                 |                                                      |" +
            "        " +
                    "                                                                \n" +
                    "12                 |                                                       " +
            "        " +
                    "                                                                \n" +
                    "13                 |                                                       " +
            "    X   " +
                    "                                                                \n" +
                    "14                                                                         " +
            "        " +
                    "                                                                \n" +
                    "15                                                                         " +
            "        " +
                    "                                                                \n" +
                    "16                                                                         " +
            "        " +
                    "                                                                \n" +
                    "17                                                                         " +
            "        " +
                    "                                                                \n" +
                    "18                                                                         " +
            "        " +
                    "                                                                \n" +
                    "19                                                                         " +
            "        " +
                    "                                                                \n" +
                    "20                                                                         " +
            "        " +
                    "                                                        X       \n" +
                    "21                                                                         " +
            "        " +
                    "                                                        |       \n" +
                    "22                                                                         " +
            "        " +
                    "                                                                \n" +
                    "23                           |                                             " +
            "        " +
                    " |                                                              \n" +
                    "24                                                                         " +
            "        " +
                    "                                                                \n" +
                    "25                                                                         " +
            "        " +
                    "                                                                \n" +
                    "26                                                                         " +
            "        " +
                    "                                                                \n" +
                    "27                                                                         " +
            "        " +
                    "                                                                \n" +
                    "28                                                                         " +
            "        " +
                    "                                                                \n" +
                    "29                                                                         " +
            "        " +
                    "                                                                \n" +
                    "30                                                                         " +
            "        " +
                    "                                                                \n" +
                    "31                                                                         " +
            "        " +
                    "      X                                                         \n" +
                    "32  X                                                                      " +
            "        " +
                    "      |                                                      |  \n" +
                    "33  |                                                                      " +
            "        " +
                    "      |                                                         \n" +
                    "34  |                                                                      " +
            "        " +
                    "                                                                \n" +
                    "35  |                                                                      " +
            "        " +
                    "                                                                \n" +
                    "36  |                                                           X          " +
            "        " +
                    "                                                                \n" +
                    "37                                                              |          " +
            "        " +
                    "                                                                \n" +
                    "38                                                              |          " +
            "        " +
                    "                                                                \n" +
                    "39                                                              |          " +
            "        " +
                    "                                                                \n" +
                    "40       X                                                                 " +
            "        " +
                    "                                         X                        X  \n" +
                    "41       |                                                      |          " +
            "        " +
                    "                                         |                        |  \n" +
                    "42       |                                                                 " +
            "        " +
                    "                                         |                        |  \n",
            m.getStringRepresentation());
  }

  /**
   * Tests for {@link IChromaticModel#merge(IChromaticModel)}
   */
  @Test
  public void mergeTest1() {
    IChromaticModel k = ChromaticModelCreator.create(ChromaticModelCreator.TYPE.CHROMATIC);
    m.addNote(0, 5, Note.PITCH.A, 0);
    k.addNote(3, 8, Note.PITCH.A, 0);
    k.addNote(2, 5, Note.PITCH.C_SHARP, 0);
    k.addNote(1, 0, Note.PITCH.C_SHARP, 0);

    m.merge(k);
    assertEquals("  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0 \n" +
            "0                                          X  \n" +
            "1  X                                       |  \n" +
            "2  X                                       |  \n" +
            "3  |                                       |  \n" +
            "4  |                                       |  \n" +
            "5  |                                       |  \n" +
            "6  |                                     \n" +
            "7  |                                     \n", m.getStringRepresentation());
  }

  /**
   * Tests for {@link IChromaticModel#append(IChromaticModel)}
   */
  @Test
  public void appendTest1() {
    IChromaticModel k = ChromaticModelCreator.create(ChromaticModelCreator.TYPE.CHROMATIC);
    m.addNote(0, 5, Note.PITCH.A, 0);
    k.addNote(3, 8, Note.PITCH.A, 0);
    k.addNote(2, 5, Note.PITCH.C_SHARP, 0);

    m.append(k);
    assertEquals("   C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0 \n" +
            " 0                                          X  \n" +
            " 1                                          |  \n" +
            " 2                                          |  \n" +
            " 3                                          |  \n" +
            " 4                                          |  \n" +
            " 5                                          |  \n" +
            " 6                                        \n" +
            " 7  X                                     \n" +
            " 8  |                                     \n" +
            " 9  |                                     \n" +
            "10  |                                     \n" +
            "11  |                                     \n" +
            "12  |                                     \n" +
            "13                                        \n" +
            "14                                          X  \n" +
            "15                                          |  \n" +
            "16                                          |  \n" +
            "17                                          |  \n" +
            "18                                          |  \n" +
            "19                                          |  \n" +
            "20                                          |  \n", m.getStringRepresentation());
  }
}
