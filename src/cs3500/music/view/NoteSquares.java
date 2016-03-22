package cs3500.music.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Square that represents a note at a single beat.
 */
public class NoteSquares extends JPanel {
  static final int PREF_W = 20;
  static final int PREF_H = PREF_W;

  //guaranteed to be only four Rectangles
  //NOTE: may want to change this invariant in the future so it can be of any size
  private final List<Color> notes = new ArrayList<>(4);

  /**
   * Constructs a NoteSquares object
   */
  public NoteSquares() {
    super();
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    for (int i = 0; i < 4; i++) {
      this.notes.add(getBackground());
    }
  }

  /**
   * Sets the Color at the given index to be the given Color.
   *
   * @param index the index of the Color to change
   * @param color the new Color
   */
  public void setNoteColor(int index, Color color) {
    if (index < 0 || index > notes.size()) {
      throw new IndexOutOfBoundsException("Must give an int between 0 and 3");
    } else if (color == null) {
      throw new IllegalArgumentException("Cannot give a null Color");
    } else {
        notes.set(index, color);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(PREF_W*4, PREF_H);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    for (int i = 0; i < notes.size(); i++) {
      g2.setPaint(notes.get(i));
      g2.fillRect(i*PREF_W, 0, PREF_W, PREF_H);
    }
  }
}
