package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GenericMusicModel.Builder class
 */
public class GenericMusicModelBuilderTest {
  int tempo = 3215;
  GenericMusicModel.Builder b = new GenericMusicModel.Builder();
  IMusicModel model = new GenericMusicModel(tempo);
  IMusicModel built;

  public void startup() {
    b.setTempo(tempo);
    for (int i = 0; i < 5; i++) {
      for (int j = 6; j < 10; j++) {
        for (int k = 0; k < 5; k++) {
          for (int l = 12; l < 36; l++) {
            for (int m = 0; m < 5; m++) {
              b.addNote(i, j, k, l, m);
              model.addNote(new NoteImpl(Note.midiToPitch(l), Note.midiToOctave(l), i, j - i, k, m));
            }
          }
        }
      }
    }
    built = b.build();
  }

  /**
   * Verifies that the builder provides the same tempo.
   */
  @Test
  public void getTempoTest() {
    startup();
    assertEquals(model.getTempo(), built.getTempo());
  }

  /**
   * Verifies that the builder provides the same tempo.
   */
  @Test
  public void getNotesTest() {
    startup();
    assertEquals(model.getNotes(), built.getNotes());
  }
}
