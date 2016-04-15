package cs3500.music.other.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.music.other.controller.ControllerImp;
import cs3500.music.other.controller.KeyboardHandler;
import cs3500.music.other.model.IMusic;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView, ActionListener {

  private final ConcreteGuiViewPanel displayPanel;
  private JScrollPane jsp;
  private IMusic m;
  private Timer t;
  private int x = 0, vellX = 5;


  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    this.displayPanel = new ConcreteGuiViewPanel();
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
  }

  @Override
  public void initialize(IMusic m) {
    this.m = m;
    t = new Timer(m.getTempo() / 4000, this);
    this.displayPanel.initialize(m, m.getTempo() / 4000);
    this.displayPanel.setPreferredSize(new Dimension(m.getSize() * 20 + 80, 800));
    jsp = new JScrollPane(displayPanel);
    this.getContentPane().add(jsp);
    this.setVisible(true);
    this.resetFocus();


  }


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 600);
  }


 @Override
  public void remove(int x, int y) {

    displayPanel.remove(x, y);
  }


  @Override
  public void stop() {
    displayPanel.stop();
    t.stop();
  }


  @Override
  public void addKeyListener(KeyboardHandler kbd) {
    super.addKeyListener(kbd);
  }

  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  public ConcreteGuiViewPanel getCG() {

    return this.displayPanel;

  }

  @Override
  public void resume() {
    displayPanel.resume();
    t.start();

  }

  public void move1(int x, int y) {
    this.remove(x, y);
  }

  @Override
  public void move2(int x, int y,int duration,int instrument,int volume) {

    this.displayPanel.move2(x, y);
  }

  @Override
  public void addNote(int x, int y,int duration,int instrument,int volume) {
    displayPanel.addNote(x, y, duration, instrument, volume);
  }


  @Override
  public void deleteAll(ArrayList<int[]> lop) {
    displayPanel.deteleAll(lop);
  }


  @Override
  public void scrollright() {
    jsp.getHorizontalScrollBar().setValue(jsp.getHorizontalScrollBar().getValue() + 50);

  }

  @Override
  public void scrollleft() {
    jsp.getHorizontalScrollBar().setValue(jsp.getHorizontalScrollBar().getValue() - 50);
  }

  @Override
  public void scrollup() {
    jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getValue() - 50);
  }


  @Override
  public void scrolldown() {
    jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getValue() + 50);
  }

  @Override
  public void scrollEnd() {
    jsp.getHorizontalScrollBar().setValue(displayPanel.getWidth());
  }

  @Override
  public void scrollBegin() {
    jsp.getHorizontalScrollBar().setValue(jsp.getVerticalScrollBar().getMinimum());
  }


  /**
   * Invoked when an action occurs.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    x += vellX;
    moveScrollBar();
  }

  public void moveScrollBar() {

    if (x % 780 == 0) {

      jsp.getHorizontalScrollBar().setValue(jsp.getHorizontalScrollBar().getValue() + 780);
    }
  }

  @Override
  public void addMouseListener(ControllerImp.MouseHandler mh) {
    this.displayPanel.addMouseListener(mh);
  }

  @Override
  public void setModel(IMusic m) {
    displayPanel.setModel(m);
  }

}

