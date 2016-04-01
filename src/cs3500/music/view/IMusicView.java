package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Specifies operations for any music view.
 */
public interface IMusicView {

  /**
   * Initializes the view and provides it, playing it immediately if its a static view. If it is a
   * dynamic view, provides it paused awaitingg user input.
   *
   * @param m the model to base the view on
   */
  void initialize(IMusicModel m);

  /**
   * Starts or resumes playback of the view.
   */
  void play();

  /**
   * Pauses the view, awaiting more input.
   */
  void pause();

  /**
   * Resets the view to paused on beat 0, awaiting input.
   */
  void reset();
}
