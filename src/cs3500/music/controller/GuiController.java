package cs3500.music.controller;

import java.awt.event.KeyEvent;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.GuiView;

/**
 * {@link IController} implementation. <ul> <li>Pause/Play: Spacebar</li> <li>Reset: R</li>
 * <li>Move*: arrow keys</li> <li>DeleteNote: D</li> <li>AddNote: Numbers for note length in beats,
 * then left click</li> <li>MoveNote: Right click and hold, release where you want</li>
 * <li>JumpToStart: Home</li> <li>JumpToEnd: End</li> </ul>
 */
public class GuiController implements IController {

  private final IMusicModel model;
  private final GuiView view;
  private final KeyboardHandler keyboardHandler;
  private final MouseHandler mouseHandler;

  /**
   * Constructs a controller with the given model and view.
   *
   * @param model the model to use
   * @param view  the view to control
   */
  public GuiController(IMusicModel model, GuiView view) {
    this.model = model;
    this.view = view;
    this.keyboardHandler = new KeyboardHandler();
    this.mouseHandler = new MouseHandler();
  }

  /**
   * Binds functions to the handlers and inserts the handlers into the view.
   */
  private void initialize() {
    this.keyboardHandler.
            addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_SPACE, new PausePlay());
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_R, new Reset());

    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_HOME, new GoToStart());
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_END, new GoToEnd());

    Move c = new Move();
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_UP, c.new Up());
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_DOWN, c.new Down());
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_LEFT, c.new Left());
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.PRESSED, KeyEvent.VK_RIGHT, c.new Right());

    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, KeyEvent.VK_D, new Delete());

    AddNote a = new AddNote();
    this.keyboardHandler
            .addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, KeyEvent.VK_A, a.new Add());

    for (int i = 0; i < 10; i++) {
      this.keyboardHandler
              .addHandler(KeyboardHandler.EVENT_TYPE.RELEASED, 48 + i, a.new Input(i));
    }

    MoveNote b = new MoveNote();
    this.mouseHandler.addHandler(MouseHandler.EVENT_TYPE.PRESSED, b.new PickUp());
    this.mouseHandler.addHandler(MouseHandler.EVENT_TYPE.RELEASED, b.new PutDown());

    this.view.addKeyListener(this.keyboardHandler);
    this.view.addMouseListener(this.mouseHandler);
    this.view.addTickHandler(new OnTick());
  }

  @Override
  public void start() {
    this.initialize();
    view.initialize(this.model);
  }

  /**
   * Runnable that determines what to do on a clock tick
   */
  private class OnTick implements Runnable {
    @Override
    public void run() {
      view.moveBeatIndicator();
    }
  }

  /**
   * Controls pausing/playing of the view. Suggested bound to spacebar.
   */
  private class PausePlay implements Runnable {
    private boolean isPlaying = false;

    @Override
    public void run() {
      if (isPlaying) {
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

    @Override
    public void run() {
      view.reset();
    }
  }

  /**
   * Contains runnables for moving a guiview up, down, left, right.
   */
  private class Move {
    private class Up implements Runnable {
      @Override
      public void run() {
        view.scrollUp();
      }
    }

    private class Down implements Runnable {
      @Override
      public void run() {
        view.scrollDown();
      }
    }

    private class Left implements Runnable {
      @Override
      public void run() {
        view.scrollLeft();
      }
    }

    private class Right implements Runnable {
      @Override
      public void run() {
        view.scrollRight();
      }
    }
  }

  /**
   * Scrolls the view to the start.
   */
  private class GoToStart implements Runnable {
    @Override
    public void run() {
      view.goToStart();
    }
  }

  /**
   * Scrolls the view to the end.
   */
  private class GoToEnd implements Runnable {
    @Override
    public void run() {
      view.goToEnd();
    }
  }

  /**
   * Deletes the note under the cursor.
   */
  private class Delete implements Runnable {
    @Override
    public void run() {
      int beat = view.getBeatAtCursor();
      int p = view.getPitchAtCursor();
      for (Note n : model.notesToPlay(beat)) {
        if (n.getMidiPitch() == p) {
          model.removeNote(n);
          view.update();
          break;
        }
      }
    }
  }

  /**
   * Adds notes of defined length.
   */
  private class AddNote {

    private int duration = 1;
    private boolean resetOnNextInput = true;


    /**
     * Adds a Note to the IMusicModel
     */
    private class Add implements Runnable {
      @Override
      public void run() {
        try {
          model.addNote(new Note(Note.midiToPitch(view.getPitchAtCursor()),
                  Note.midiToOctave(view.getPitchAtCursor()),
                  view.getBeatAtCursor(), duration, 1, 60));
          resetOnNextInput = true;
          view.update();
        } catch (IllegalStateException e) {
          // do nothing
        }
      }
    }


    /**
     * Handles duration inputs
     */
    private class Input implements Runnable {
      private int input;

      /**
       * Constructs an Input
       * @param input the numeric input (representing note duration)
       */
      public Input(int input) {
        this.input = input;
      }

      @Override
      public void run() {
        if (resetOnNextInput) {
          duration = input;
          resetOnNextInput = false;
        } else {
          duration = 10 * duration + input;
        }
      }
    }
  }

  /**
   * Move a note from one pitch and beat to another.
   */
  private class MoveNote {
    private Note note;
    private boolean isMoving = false;

    /**
     * Grabs the note at the cursor's position
     */
    private class PickUp implements Runnable {
      @Override
      public void run() {
        int beat = view.getBeatAtCursor();
        int p = view.getPitchAtCursor();
        for (Note n : model.notesToPlay(beat)) {
          if (n.getMidiPitch() == p && n.getStart() == beat) {
            note = n;
            break;
          }
        }
        isMoving = true;
      }
    }

    /**
     * Places the held note at the cursor's position
     */
    private class PutDown implements Runnable {
      @Override
      public void run() {
        if (note != null) {
          if (isMoving) {
            int beat = view.getBeatAtCursor();
            int p = view.getPitchAtCursor();
            model.editNote(note, new Note(Note.midiToPitch(p), Note.midiToOctave(p),
                    beat, note.getDuration(), note.getInstrument(), note.getVolume()));
          }
          isMoving = false;
          note = null;
          view.update();
        }
      }
    }
  }
}
