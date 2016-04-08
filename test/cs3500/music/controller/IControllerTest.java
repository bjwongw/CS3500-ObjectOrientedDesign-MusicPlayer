package cs3500.music.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.awt.event.KeyEvent;

import cs3500.music.model.Note;

/**
 * Tests for the controller
 */
public class IControllerTest {
  StringBuilder log = new StringBuilder();
  MockModel model = new MockModel(log);
  MockIGuiView view = new MockIGuiView(log);
  IController controller = new GuiController(model, view);

  @Test
  public void scrollViewTest() {
    controller.start();
    view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_UP, 0, 0);
    view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_DOWN, 0, 0);
    view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_LEFT, 0, 0);
    view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_RIGHT, 0, 0);
    view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_HOME, 0, 0);
    view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_END, 0, 0);

    assertEquals("Sending key PRESSED: 38\n" +
            "Scrolling up\n" +
            "Sending key PRESSED: 40\n" +
            "Scrolling down\n" +
            "Sending key PRESSED: 37\n" +
            "Scrolling left\n" +
            "Sending key PRESSED: 39\n" +
            "Scrolling right\n" +
            "Sending key PRESSED: 36\n" +
            "Scrolling to start\n" +
            "Sending key PRESSED: 35\n" +
            "Scrolling to end\n", log.toString());
  }

  @Test
  public void PausePlayTest() {
    controller.start();
    for(int i = 0; i < 5; i ++) {
      view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_SPACE, 0, 0);
    }

    assertEquals("Sending key PRESSED: 32\n" +
            "Playing\n" +
            "Sending key PRESSED: 32\n" +
            "Pausing\n" +
            "Sending key PRESSED: 32\n" +
            "Playing\n" +
            "Sending key PRESSED: 32\n" +
            "Pausing\n" +
            "Sending key PRESSED: 32\n" +
            "Playing\n", log.toString());
  }

  @Test
  public void ResetTest() {
    controller.start();
    for(int i = 0; i < 5; i ++) {
      view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.PRESSED, KeyEvent.VK_R, 0, 0);
    }

    assertEquals("Sending key PRESSED: 82\n" +
            "Resetting\n" +
            "Sending key PRESSED: 82\n" +
            "Resetting\n" +
            "Sending key PRESSED: 82\n" +
            "Resetting\n" +
            "Sending key PRESSED: 82\n" +
            "Resetting\n" +
            "Sending key PRESSED: 82\n" +
            "Resetting\n", log.toString());
  }

  @Test
  public void DeleteTest() {
    controller.start();
    StringBuilder exp = new StringBuilder();
    for(int i = 20; i < 30; i ++) {
      for(int j = 0; j < 10; j ++) {
        model.addNote(new Note(Note.midiToPitch(i), Note.midiToOctave(i), j, 3, 0, 0));
        view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.RELEASED, KeyEvent.VK_D, i, j);
        exp.append(String.format(
                "AddNote: Pitch: %d, Beat: %d, Length: 3\n" +
                "Sending key RELEASED: 68\n" +
                "notesToPlay Called\n" +
                "RemoveNote: Pitch: %d, Beat: %d\n" +
                "Updating View\n", i, j, i, j));
      }
    }

    assertEquals(exp.toString(), log.toString());
  }

  @Test
  public void AddNoteTest() {
    controller.start();
    StringBuilder exp = new StringBuilder();
    for(int n = 1; n < 10; n ++){
      for(int i = 20; i < 30; i ++) {
        for(int j = 0; j < 10; j ++) {
          view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.RELEASED, 48 + n, i, j);
          view.sendKeyBoardInput(MockIGuiView.INPUT_TYPE.RELEASED, KeyEvent.VK_A, i, j);
          exp.append(String.format(
                  "Sending key RELEASED: %d\n" +
                          "Sending key RELEASED: 65\n" +
                          "AddNote: Pitch: %d, Beat: %d, Length: %d\n" +
                          "Updating View\n",48 + n, i, j, n));
        }
      }
    }
    assertEquals(exp.toString(), log.toString());
  }

  @Test
  public void MoveNoteTest() {
    controller.start();
    StringBuilder exp = new StringBuilder();
    for(int i = 20; i < 30; i ++) {
      for (int j = 0; j < 10; j ++) {
        view.sendMouseInput(MockIGuiView.INPUT_TYPE.PRESSED, i, j);
        view.sendMouseInput(MockIGuiView.INPUT_TYPE.RELEASED, i + 10, j + 10);
        exp.append(String.format("Sending mouse PRESSED\n" +
                "notesToPlay Called\n" +
                "Sending mouse RELEASED\n"));
      }
    }

    for(int i = 20; i < 30; i ++) {
      for (int j = 0; j < 10; j ++) {
        model.addNote(new Note(Note.midiToPitch(i), Note.midiToOctave(i), j, 3, 0, 0));
        view.sendMouseInput(MockIGuiView.INPUT_TYPE.PRESSED, i, j);
        view.sendMouseInput(MockIGuiView.INPUT_TYPE.RELEASED, i + 10, j + 10);
        exp.append(String.format("AddNote: Pitch: %d, Beat: %d, Length: 3\n" +
                "Sending mouse PRESSED\n" +
                "notesToPlay Called\n" +
                "Sending mouse RELEASED\n" +
                "MoveNote: Pitch1: %d, Beat1: %d || Pitch2: %d, Beat2: %d\n" +
                "Updating View\n", i, j, i, j, i+10, j+10));
      }
    }

    assertEquals(exp.toString(), log.toString());
  }

}
