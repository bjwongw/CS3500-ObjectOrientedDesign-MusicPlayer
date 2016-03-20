package cs3500.music.view;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A view for a range of Note.Pitches
 */
public class GuiViewPanel extends JPanel {

  private final IMusicModel model;
  private static final int BEAT_DIMENSION = 20;

  /**
   * Constructs the GuiViewPanel
   *
   * @param model the music model to represent
   */
  GuiViewPanel(IMusicModel model) {
    this.model = model;
    setLa
  }

  private List<Rectangle> createNoteSquares(Note n) {
    List<Rectangle> noteSquares = new ArrayList<>();
    if (n != null) {
      for (int i = n.getStart(); i < n.getStart() + n.getDuration(); i++) {
        noteSquares.add(new Rectangle(0, 0, BEAT_DIMENSION, BEAT_DIMENSION));
      }
    }
    return noteSquares;
  }

  private List<Graphics2D> createNoteImages() {
    List<Graphics2D> result = new ArrayList<>();
//    int range =
//    Set<Note> notes = model.getNotes();
    return result;
  }

  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);

    List<String> pitchRange = model.getPitchRange();
    for (int i = 0; i < pitchRange.size(); i++) {
      g.drawString(pitchRange.get((pitchRange.size() - 1) - i), 10, i*20);
    }
  }
}
