package cs3500.music.controller;

/**
 * Interface to a controller for an {@link cs3500.music.view.IGuiView} and {@link cs3500.music.model.GenericMusicModel}
 * Handles input and edits the model accordingly
 */
public interface IController {

  /**
   * Initializes the view to await input/start playing etc.
   */
  public void start();


}
