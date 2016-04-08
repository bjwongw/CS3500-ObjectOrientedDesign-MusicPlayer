package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.view.GuiView;

/**
 * MockView for testing controllers.
 */
public class MockIGuiView implements GuiView {
  KeyListener keyListener;
  MouseListener mouseListener;
  int currentCursorPitch = 120;
  int currentCursorBeat = 0;
  StringBuilder log;

  public enum INPUT_TYPE {
    TYPED, PRESSED, RELEASED
  }

  MockIGuiView(StringBuilder s) {
    this.log = s;
  }

  /**
   * Sends mock keyboard input to the keylistener associated with this mock view. Logs the event in
   * this.log.
   *
   * @param type        the type of the event
   * @param keyCode     the key pressed etc.
   * @param cursorPitch where the mock cursor is at the time of press
   * @param cursorBeat  where the mock cursor is at the time of press
   */
  public void sendKeyBoardInput(INPUT_TYPE type, int keyCode, int cursorPitch, int cursorBeat) {
    this.currentCursorBeat = cursorBeat;
    this.currentCursorPitch = cursorPitch;
    log.append(String.format("Sending key %s: %d\n", type.name(), keyCode));
    switch (type) {
      case TYPED:
        keyListener.keyTyped(new KeyEvent(new JPanel(), keyCode, keyCode, keyCode, keyCode));
        break;
      case PRESSED:
        keyListener.keyPressed(new KeyEvent(new JPanel(), keyCode, keyCode, keyCode, keyCode));
        break;
      case RELEASED:
        keyListener.keyReleased(new KeyEvent(new JPanel(), keyCode, keyCode, keyCode, keyCode));
        break;
    }


  }

  /**
   * Sends mock mouse input to the mouselistener associated with this mock view. Logs the event in
   * this.log.
   *
   * @param type        the type of the event
   * @param cursorPitch where the mock cursor is at the time of press
   * @param cursorBeat  where the mock cursor is at the time of press
   */
  public void sendMouseInput(INPUT_TYPE type, int cursorPitch, int cursorBeat) {
    this.currentCursorBeat = cursorBeat;
    this.currentCursorPitch = cursorPitch;
    log.append(String.format("Sending mouse %s\n", type.name()));
    switch (type) {
      case TYPED:
        mouseListener.mouseClicked(new MouseEvent(new JPanel(), 0, 0, 0, 0, 0, 0, true, 0));
        break;
      case PRESSED:
        mouseListener.mousePressed(new MouseEvent(new JPanel(), 0, 0, 0, 0, 0, 0, true, 0));
        break;
      case RELEASED:
        mouseListener.mouseReleased(new MouseEvent(new JPanel(), 0, 0, 0, 0, 0, 0, true, 0));
        break;
    }


  }

  @Override
  public void addKeyListener(KeyListener k) {
    this.keyListener = k;
  }

  @Override
  public void addMouseListener(MouseListener m) {
    this.mouseListener = m;
  }

  @Override
  public int getPitchAtCursor() {
    return this.currentCursorPitch;
  }

  @Override
  public int getBeatAtCursor() {
    return this.currentCursorBeat;
  }

  @Override
  public void update() {
    this.log.append("Updating View\n");
  }

  @Override
  public void scrollRight() {
    this.log.append("Scrolling right\n");
  }

  @Override
  public void scrollLeft() {
    this.log.append("Scrolling left\n");
  }

  @Override
  public void scrollUp() {
    this.log.append("Scrolling up\n");
  }

  @Override
  public void scrollDown() {
    this.log.append("Scrolling down\n");
  }

  @Override
  public void goToStart() {
    this.log.append("Scrolling to start\n");
  }

  @Override
  public void goToEnd() {
    this.log.append("Scrolling to end\n");
  }

  @Override
  public void moveBeatIndicator() {
    this.log.append("Shifting beat indicator\n");
  }

  @Override
  public void initialize(IMusicModel m) {
    this.log.append("Initializing\n");
  }

  @Override
  public void play() {
    this.log.append("Playing\n");
  }

  @Override
  public void pause() {
    this.log.append("Pausing\n");
  }

  @Override
  public void reset() {
    this.log.append("Resetting\n");
  }

  @Override
  public boolean addTickHandler(Runnable r) {
    return false;
  }
}
