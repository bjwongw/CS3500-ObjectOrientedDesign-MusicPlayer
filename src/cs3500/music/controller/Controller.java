package cs3500.music.controller;

import java.awt.event.KeyEvent;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.view.IGuiView;

/**
 * {@link IController} implementation.
 */
public class Controller implements IController {

  private final GenericMusicModel model;
  private final IGuiView view;
  private final KeyboardHandler keyboardHandler;
  private final MouseHandler mouseHandler;

  /**
   * Constructs a controller with the given model and view.
   *
   * @param model the model to use
   * @param view the view to control
   */
  public Controller(GenericMusicModel model, IGuiView view) {
    this.model = model;
    this.view = view;
    this.keyboardHandler = new KeyboardHandler();
    this.mouseHandler = new MouseHandler();

    this.initialize();
  }

  /**
   * Binds functions to the handlers and inserts the handlers into the view.
   */
  private void initialize() {
    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, KeyEvent.VK_SPACE, new PausePlay());

    this.view.addKeyListener(this.keyboardHandler);
    this.view.addMouseListener(this.mouseHandler);
  }

  @Override
  public void start() {
    view.initialize(this.model);
  }

  /**
   * Controls pausing/playing of the view. Suggested bound to spacebar.
   */
  private class PausePlay implements Runnable{
    private boolean isPlaying = false;
    public void run() {
      if(isPlaying) {
        view.pause();
      } else {
        view.play();
      }
    }
  }
}
