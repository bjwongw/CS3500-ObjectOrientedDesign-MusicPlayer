package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Specifies operations for any music view.
 */
public interface IMusicView {

  /**
   * Initializes this view object with the given music model. Must be run for proper function.
   *
   * Starts the view (for example in the case of MidiView. TODO look at this next assignment
   */
  void initialize(IMusicModel m);

  /**
   * Updates the view to the given beat.
   *
   * <ul>
   *   <li>ConsoleView: Does nothing, as of yet</li>
   *   <li>GuiView: TODO move the current beat marker to the given beat</li>
   *   <li>MidiView: Plays the notes at the given beat to the speakers</li>
   * </ul>
   *
   * @param beat the beat to go to
   */
  void update(int beat);
}
