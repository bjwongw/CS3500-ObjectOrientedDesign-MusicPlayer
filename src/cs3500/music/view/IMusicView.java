package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Specifies operations for any music view.
 */
public interface IMusicView {

  /**
   * Initializes this view object.
   */
  void initialize();

  /**
   * Updates the view to the given beat.
   *
   * @param beat the beat to go to
   */
  void update(int beat);
}
