package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Interface to an advanced Gui view allowing user input.
 */
public interface GuiView extends View {

  /**
   * Adds the given listener to the panel
   * @param k is the KeyListener
   */
  void addKeyListener(KeyListener k);

  /**
   * Adds the given listener to the panel
   * @param m is the MouseListener
   */
  void addMouseListener(MouseListener m);

  /**
   * Gets the pitch of the row that the cursor is resting on.
   * @return the MIDI pitch of the row
   * @throws IllegalArgumentException if the position is outside of the panel
   */
  int getPitchAtCursor();

  /**
   * Gets the beat of the column that the cursor is resting on.
   * @return the beat of the column
   * @throws IllegalArgumentException if the position is outside of the panel
   */
  int getBeatAtCursor();

  /**
   * Updates the view to the current state of the model.
   */
  void update();

  /**
   * Scrolls the view right a certain amount.
   */
  void scrollRight();

  /**
   * Scrolls the view left a certain amount.
   */
  void scrollLeft();

  /**
   * Scrolls the view up a certain amount.
   */
  void scrollUp();

  /**
   * Scrolls the view down a certain amount.
   */
  void scrollDown();

  /**
   * Scrolls the view to the start of the piece.
   */
  void goToStart();

  /**
   * Scrolls the view to the end of the piece.
   */
  void goToEnd();
}
