package cs3500.music.controller;

import org.junit.Test;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link MouseHandler}
 */
public class MouseHandlerTest {

  MouseHandler h = new MouseHandler();

  class Fun1 implements Runnable {
    int counter = 0;

    @Override
    public void run() {
      this.counter += 1;
    }
  }


}
