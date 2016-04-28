package cs3500.music.controller.repeats;

import java.awt.event.KeyEvent;

import cs3500.music.controller.GuiController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.repeats.IRepeatModel;
import cs3500.music.view.GuiView;

/**
 * Created by Alex on 4/27/2016.
 */
public class RepeatController extends GuiController {

  private IRepeatModel rmodel;

  /**
   * Constructs a controller with the given model and view.
   *
   * @param model the model to use
   * @param view  the view to control
   */
  public RepeatController(IRepeatModel model, GuiView view) {
    super(model, view);
    this.rmodel = model;
  }

  @Override
  protected void initialize() {
    AddBasicRepeat a = new AddBasicRepeat();
    this.keyboardHandler.
            addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, KeyEvent.VK_Q, a);
    this.keyboardHandler.
            addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, KeyEvent.VK_E, new AddAlternateRepeat());
    super.initialize();
  }

  private class AddBasicRepeat implements Runnable {

    private int previousBeat;
    private boolean started = false;

    @Override
    public void run() {
      try {
        if(this.started) {
          if(previousBeat == view.getBeatAtCursor()) {
            rmodel.addGoToStartOnceRepeat(previousBeat);
          } else {
            rmodel.addGoToBeatOnceRepeat(previousBeat, view.getBeatAtCursor());
          }
          started = false;
          view.update();
        } else {
          previousBeat = view.getBeatAtCursor();
          started = true;
        }
      } catch (IllegalStateException e) {
        // do nothing
      }
    }
  }

  private class AddAlternateRepeat implements Runnable {

    private int previousBeat;
    private boolean started = false;

    @Override
    public void run() {

      try {
        if(this.started) {
          if(previousBeat == view.getBeatAtCursor()) {

          } else {
            rmodel.addAlternateRepeat(previousBeat, 0, view.getBeatAtCursor()) ;
          }
          started = false;
          view.update();
        } else {
          previousBeat = view.getBeatAtCursor();
          started = true;
        }
      } catch (IllegalStateException e) {
        // do nothing
      }
    }
  }
}
