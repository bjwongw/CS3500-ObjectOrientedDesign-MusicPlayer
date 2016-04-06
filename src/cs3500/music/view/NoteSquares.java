package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Square that represents a note at a single beat.
 */
public class NoteSquares extends JPanel {
  static final int PREF_W = 20;
  static final int PREF_H = PREF_W;
  private final int numSquares;
  private final List<Color> notes;

  /**
   * Constructs a NoteSquares object
   */
  public NoteSquares(int numSquares) {
    super();
    this.numSquares = numSquares;
    this.notes = new ArrayList<>(numSquares);
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    for (int i = 0; i < numSquares; i++) {
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
      throw new IndexOutOfBoundsException("Must give a valid index");
    } else if (color == null) {
      throw new IllegalArgumentException("Cannot give a null Color");
    } else {
      notes.set(index, color);
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(PREF_W * numSquares, PREF_H);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    for (int i = 0; i < notes.size(); i++) {
      g2.setPaint(notes.get(i));
      g2.fillRect(i * PREF_W, 0, PREF_W, PREF_H);
    }
  }
}
