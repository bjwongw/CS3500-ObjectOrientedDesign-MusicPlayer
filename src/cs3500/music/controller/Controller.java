package cs3500.music.controller;

import java.awt.event.KeyEvent;

import cs3500.music.model.GenericMusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.GuiView;

/**
 * {@link IController} implementation.
 * <ul>
 *   <li>Pause/Play: Spacebar</li>
 *   <li>Reset: R</li>
 *   <li>Move*: arrow keys</li>
 *   <li>DeleteNote: D</li>
 *   <li>AddNote: Numbers for note length in beats, then left click</li>
 *   <li>MoveNote: Right click and hold, release where you want</li>
 *   <li>JumpToStart: Home</li>
 *   <li>JumpToEnd: End</li>
 * </ul>
 */
public class Controller implements IController {

  private final GenericMusicModel model;
  private final GuiView view;
  private final KeyboardHandler keyboardHandler;
  private final MouseHandler mouseHandler;

  /**
   * Constructs a controller with the given model and view.
   *
   * @param model the model to use
   * @param view the view to control
   */
  public Controller(GenericMusicModel model, GuiView view) {
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
    // TODO jump to beginning or end using HOME and END keys
    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_SPACE, new PausePlay());
    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_R, new Reset());

    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, KeyEvent.VK_UP, new Move().new Up());
    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, KeyEvent.VK_DOWN, new Move().new Down());
    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, KeyEvent.VK_LEFT, new Move().new Left());
    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.TYPED, KeyEvent.VK_RIGHT, new Move().new Right());

    this.keyboardHandler.addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, KeyEvent.VK_D, new Delete());


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
  private class PausePlay implements Runnable {
    private boolean isPlaying = false;
    public void run() {
      if(isPlaying) {
        view.pause();
        isPlaying = false;
      } else {
        view.play();
        isPlaying = true;
      }
    }
  }

  /**
   * Resets the view. Suggested bound to R.
   */
  private class Reset implements Runnable {
    public void run() {
      view.reset();
    }
  }

  /**
   * Contains runnables for moving a guiview up, down, left, right.
   */
  private class Move {
    private class Up implements Runnable {
      public void run() {
        view.scrollUp();
      }
    }
    private class Down implements Runnable {
      public void run() {
        view.scrollDown();
      }
    }
    private class Left implements Runnable {
      public void run() {
        view.scrollLeft();
      }
    }
    private class Right implements Runnable {
      public void run() {
        view.scrollRight();
      }
    }
  }

  /**
   * Deletes the note under the cursor.
   */
  private class Delete implements Runnable {
    public void run() {
      int beat = view.getBeatAtCursor();
      int p = view.getPitchAtCursor();
      for(Note n : model.notesToPlay(beat)) {
        if(n.getMidiPitch() == p) {
          model.removeNote(n);
          break;
        }
      }
    }
  }

  private class AddNote {
    private class Add implements Runnable {
      public void run() {

      }
    }
  }
}
