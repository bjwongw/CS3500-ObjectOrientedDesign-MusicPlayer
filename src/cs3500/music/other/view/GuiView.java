package cs3500.music.other.view;

import java.util.ArrayList;

import cs3500.music.other.controller.ControllerImp;
import cs3500.music.other.controller.KeyboardHandler;
import cs3500.music.other.model.IMusic;

/**
 * Created by renyuan on 3/26/16.
 */
public interface GuiView extends IMusicView {


  // GUI-specific methods that make no sense for the other view types,
  // such as methods for dealing with keyboard and mouse.

  // add keylistener to the view

  /**
   *
   * @param kbd
   */
  void addKeyListener(KeyboardHandler kbd);


  //set the focus back to the view

  void resetFocus();



  //a method will resume the music.
  void resume();



  //a method will move some note based on the key and mouse event.
  /**
   *
   * @param x
   * @param y
   */
  void move1(int x, int y);


  //a method will move some note based on the key and mouse event.(Second phase)

  /**
   *
   * @param x
   * @param y
   * @param duration
   * @param instrument
   * @param volume
   */
  void move2(int x, int y, int duration, int instrument, int volume);


  //a method will move move the scroller bar to right;

  void scrollright();


  //a method will move move the scroller bar to left;
  void scrollleft();


  //a method will move move the scroller bar up;
  void scrollup();


  //a method will move move the scroller bar down;
  void scrolldown();


  //a method will move move the scroller bar to the end of the composition;
  void scrollEnd();


  //a method will move move the scroller bar to the beginning of the composition;
  void scrollBegin();


  //a method will add some note to the composition based on the input information;

  /**
   *
   * @param x
   * @param y
   * @param duration
   * @param instrument
   * @param volume
   */
  void addNote(int x, int y, int duration, int instrument, int volume);


  //a method will remove some note to the composition based on the input information;

  /**
   *
   * @param x
   * @param y
   */
  void remove(int x, int y);


  //a method will stop the music
  void stop();



  // a method will delete a few notes at one time based on the input;

  /**
   *
   * @param lop
   */
  void deleteAll(ArrayList<int[]> lop);


  //add mouse listener to the view

  /**
   *
   * @param mh
   */



  // set the model for the view.
  void addMouseListener(ControllerImp.MouseHandler mh);
  /**
   *
   * @param m
   */
    void setModel(IMusic m);

}
