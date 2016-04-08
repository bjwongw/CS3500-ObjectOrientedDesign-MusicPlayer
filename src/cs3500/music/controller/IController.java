package cs3500.music.controller;

import cs3500.music.view.GuiView;

/**
 * Interface to a controller for an {@link GuiView} and {@link cs3500.music.model.GenericMusicModel}
 * Handles input and edits the model accordingly
 */
public interface IController {

  /**
   * Initializes the view to await input/start playing etc.
   */
  void start();
}
