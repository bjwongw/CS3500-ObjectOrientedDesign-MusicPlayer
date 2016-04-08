package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Specifies operations for any music view.
 */
public interface View {

  /**
   * Initializes the view and provides it, playing it immediately if it's a static view. If it is a
   * dynamic view, provides it paused awaiting user input.
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

  /**
   * Adds the provided handler to the view, getting run on every occurrence of a beat, if this view
   * supports it.
   *
   * @param r the handler
   * @return true if the handler was added, false if the view does not support beats or ticks
   */
  boolean addTickHandler(Runnable r);
}
