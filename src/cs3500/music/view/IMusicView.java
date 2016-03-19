package cs3500.music.view;

/**
 * Specifies operations for any music view.
 */
public interface IMusicView {

  /**
   * Initializes this view object.
   */
  void initialize();

  /**
   * Updates the view to the given time. In the case of a midi view, plays all notes that havent
   * been played up to the beat at or right before the given time.
   *
   * @param time the time
   */
  void update(int time);
}
