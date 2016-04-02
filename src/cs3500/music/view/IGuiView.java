package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * Interface to an advanced Gui view allowing user input.
 */
public interface IGuiView extends IMusicView{

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
   * @param x the x position of the cursor in pixels
   * @param y the y position of the cursor in pixels
   * @return the pitch of the row
   * @throws IllegalArgumentException if the position is outside of the panel
   */
  Note.Pitch getPitchAt(int x, int y);

  /**
   * Gets the beat of the column that the cursor is resting on.
   * @param x the x position of the cursor in pixels
   * @param y the y position of the cursor in pixels
   * @return the pitch of the row
   * @throws IllegalArgumentException if the position is outside of the panel
   */
  int getBeatAt(int x, int y);

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
