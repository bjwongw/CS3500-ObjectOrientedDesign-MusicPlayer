package cs3500.music.view;

import cs3500.music.model.IMusicModel;

/**
 * Specifies operations for any music view.
 */
public interface IMusicView {

  /**
   * Initializes this view object.
   *
   * It's suggested that this be run regardless of what implementation is being used for
   * future-proofing.
   *
   * <ul>
   *   <li>ConsoleView: Does nothing</li>
   *   <li>GuiView: *Required for proper function*</li>
   *   <li>MidiView: Does nothing</li>
   * </ul>
   */
  void initialize();

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
