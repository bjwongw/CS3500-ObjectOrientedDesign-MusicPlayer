package cs3500.music.model;

import cs3500.music.other.model.IMusic;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the IMusicModelToIMusicAdapter class
 */
public class IMusicModelToIMusicAdapterTest {
  IMusicModel model;
  IMusic adapter;

  @Test
  public void testGetCollections() {
    model = new GenericMusicModel(1000);
    adapter = new IMusicModelToIMusicAdapter(model);
    model.addNote(new NoteImpl(Note.Pitch.F, 3, 2, 5, 1, 1));
    model.addNote(new NoteImpl(Note.Pitch.F_SHARP, 3, 0, 2, 1, 1));
    model.addNote(new NoteImpl(Note.Pitch.C, 4, 10, 6, 1, 1));
    model.addNote(new NoteImpl(Note.Pitch.G, 3, 1, 3, 1, 1));
    assertEquals(8, adapter.getCollections().size()); // the pitch range

    model.addNote(new NoteImpl(Note.Pitch.D, 5, 3, 1, 2, 3));
    assertEquals(22, adapter.getCollections().size());

    model.addNote(new NoteImpl(Note.Pitch.E, 1, 4, 3, 1, 1));
    assertEquals(47, adapter.getCollections().size());
  }

  @Test
  public void testGetSize() {
    model = new GenericMusicModel(500);
    model.addNote(new NoteImpl(Note.Pitch.C_SHARP, 10, 3, 5, 1, 1));
    adapter = new IMusicModelToIMusicAdapter(model);
    assertEquals(7, adapter.getSize());

    model.addNote(new NoteImpl(Note.Pitch.A, 3, 7, 10, 1, 1));
    assertEquals(16, adapter.getSize());

    model.addNote(new NoteImpl(Note.Pitch.B, 2, 2, 22, 3, 5));
    assertEquals(23, adapter.getSize());
  }

  /**
   * Tests for the method getTempo
   */
  @Test
  public void testGetTempo() {
    model = new GenericMusicModel(1000);
    adapter = new IMusicModelToIMusicAdapter(model);
    assertEquals(1000, adapter.getTempo());

    model = new GenericMusicModel(1200);
    adapter = new IMusicModelToIMusicAdapter(model);
    assertEquals(1200, adapter.getTempo());

    model = new GenericMusicModel(15);
    adapter = new IMusicModelToIMusicAdapter(model);
    assertEquals(15, adapter.getTempo());
  }

  /**
   * Tests for the method print
   */
  @Test
  public void testPrint() {
    model = new GenericMusicModel(2000);
    adapter = new IMusicModelToIMusicAdapter(model);
    assertEquals("", adapter.print());

    model = new GenericMusicModel(10);
    adapter = new IMusicModelToIMusicAdapter(model);
    assertEquals("", adapter.print());
  }

}
