package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * KeyboardHandler that only allows one runnable to be provided for each key/event type.
 */
public class KeyboardHandler implements KeyListener {

  private final Map<Integer, Runnable> keyTypedMap;
  private final Map<Integer, Runnable> keyPressedMap;
  private final Map<Integer, Runnable> keyReleasedMap;

  public enum EVENT_TYPE{
    TYPED, PRESSED, RELEASED
  }

  /**
   * Constructs a KeyboardHandler.
   */
  public KeyboardHandler() {
    keyTypedMap = new HashMap<>();
    keyPressedMap = new HashMap<>();
    keyReleasedMap = new HashMap<>();
  }

  /**
   * Adds the given runnable to this KeyboardHandler and associates it with the key and event type given.
   * @param t the type of event
   * @param key the key typed/pressed/released
   * @param r the runnable to run under the above conditions
   * @throws IllegalArgumentException if a t/r are null, or key is negative
   */
  public void addHandler(EVENT_TYPE t, int key, Runnable r) {
    if(t == null || key < 0 || r == null) throw new IllegalArgumentException("Invalid input!");

    Map<Integer, Runnable> map;
    switch(t) {
      case TYPED:
        map = keyTypedMap;
        break;
      case PRESSED:
        map = keyPressedMap;
        break;
      case RELEASED:
        map = keyReleasedMap;
        break;
      default:
        throw new IllegalArgumentException("EVENT_TYPE is null.");
    }

    map.put(key, r);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    int k;
    if(keyTypedMap.containsKey(k = e.getKeyCode())) {
      keyTypedMap.get(k).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int k;
    if(keyPressedMap.containsKey(k = e.getKeyCode())) {
      keyPressedMap.get(k).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int k;
    if(keyReleasedMap.containsKey(k = e.getKeyCode())) {
      keyReleasedMap.get(k).run();
    }
  }
}
