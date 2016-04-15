package cs3500.music.other.view;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.other.model.HeadBeat;
import cs3500.music.other.model.IBeat;
import cs3500.music.other.model.IMusic;
import cs3500.music.other.model.Note;
import cs3500.music.other.model.Pitch;
import cs3500.music.other.model.TailBeat;


/**
 * A dummy view that simply draws a string
 */
public class ConcreteGuiViewPanel extends JPanel implements ActionListener {
  private IMusic nt;
  private Timer t;
  private int x = 40, y = 30, vellX = 5;
  private HashMap<Integer, Integer> hm;

  public ConcreteGuiViewPanel() {
    hm = new HashMap<>();
  }

  ;


  public void initialize(IMusic m, int delay) {
    this.nt = m;
    t = new Timer(delay, this);

  }


  @Override
  public void paintComponent(Graphics g) {


    // Look for more documentation about the Graphics class,
    // and methods on it that may be useful
    super.paintComponent(g);

    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();


  //this.scrollRectToVisible(new Rectangle());

    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    int min = lop1.get(0).hashCode();


    // get the pitches that are supposed to be displayed;
    int max = lop1.get(lop1.size() - 1).hashCode();
    for (int l = min; l < max + 1; l++) {
      Pitch p1 = new Pitch(l);
      lop2.add(p1);
    }


    //reverse the list
    Collections.reverse(lop2);

    //draw pitches;
    int i = 40;
    for (Pitch p : lop2) {
      g.drawString(p.toString(), 0, i);
      i = i + 20;
    }


    // draw the lower horizontal line


    // draw the upper horizontal line
    for (i = 0; i < lop2.size() + 1; i++) {
      g.drawLine(40, 30 + i * 20, 20 * nt.getSize() + 40, 30 + i * 20);
    }


    //draw the beat time
    for (i = 0; i < nt.getSize() + 1; i = i + 16) {
      g.drawString(Integer.toString(i), 40 + i * 20, 30);
    }


    // draw the vertical line;
    for (i = 0; i < (Math.floor(nt.getSize() / 16)) * 4 + 1; i++) {
      g.drawLine(40 + i * 80, 30, 40 + i * 80, lop2.size() * 20 + 30);
    }


    //draw the curret line
    g.setColor(Color.RED);
    g.drawLine(x, y, x, lop2.size() * 20 + y);


    // draw the square
    for (int p = 0; p < lop2.size(); p++) {
      for (int ii = 0; ii < nt.getSize(); ii++) {
        TreeMap<Integer, IBeat> tm1 = nt.getCollections().get(lop2.get(p));
        if (tm1.containsKey(ii)) {
          if (tm1.get(ii) instanceof HeadBeat) {
            g.setColor(Color.BLACK);
            g.fillRect(40 + ii * 20, 30 + p * 20, 20, 20);
          } else if (tm1.get(ii) instanceof TailBeat) {
            g.setColor(Color.GREEN);
            g.fillRect(40 + ii * 20, 30 + p * 20, 20, 20);
          }
        }
      }
    }


  }


  public void remove(int x, int y) {
    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();


    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    int min = lop1.get(0).hashCode();


    // get the pitches that are supposed to be displayed;
    int max = lop1.get(lop1.size() - 1).hashCode();
    for (int l = min; l < max + 1; l++) {
      Pitch p1 = new Pitch(l);
      lop2.add(p1);
    }

    //reverse the list
    Collections.reverse(lop2);
    Pitch pp = lop2.get(y);
    int pn = pp.pitchNumber();
    HeadBeat hb;

    if (nt.getCollections().get(pp).get(x) instanceof HeadBeat) {
      hb = (HeadBeat) nt.getCollections().get(pp).get(x);
      int duration = hb.getEnd() - hb.getPosition();
      int instrument = hb.getInstrument();
      int volume = hb.getVolume();
      hm.put(1, duration);
      hm.put(2, instrument);
      hm.put(3, volume);
      nt.remove(new Note(new Pitch(pn), duration), hb.getPosition());
      repaint();
    }

  }








  /**
   * Invoked when an action occurs.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    x += vellX;
    repaint();
  }


  public void stop() {
    t.stop();
  }

  public void resume() {
    t.start();
  }


  public void move2(int x, int y) {
    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();


    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    int min = lop1.get(0).hashCode();


    // get the pitches that are supposed to be displayed;
    int max = lop1.get(lop1.size() - 1).hashCode();
    for (int l = min; l < max + 1; l++) {
      Pitch p1 = new Pitch(l);
      lop2.add(p1);
    }

    //reverse the list
    Collections.reverse(lop2);
    Pitch pp = lop2.get(y);
    int pn = pp.pitchNumber();
    Note n1 = new Note(new Pitch(pn), 2);

    try {
      int duration = hm.get(1);
      int instrument = hm.get(2);
      int volume = hm.get(3);

      n1.Set(x, x + duration, instrument, pn, volume);
      nt.addNote(n1, x);
      repaint();
    } catch (NullPointerException e) {
      System.out.println("please place mouse at right position");
    }
  }


  public void addNote(int x, int y, int duration, int instrument, int volume) {
    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();


    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    int min = lop1.get(0).hashCode();


    // get the pitches that are supposed to be displayed;
    int max = lop1.get(lop1.size() - 1).hashCode();
    for (int l = min; l < max + 1; l++) {
      Pitch p1 = new Pitch(l);
      lop2.add(p1);
    }

    //reverse the list
    Collections.reverse(lop2);
    Pitch pp = lop2.get(y);
    int pn = pp.pitchNumber();


//    this.setFocusable(true);
//    this.requestFocus();
    Note n1 = new Note(new Pitch(pn), duration);
    n1.Set(x, x + duration, instrument, pn, volume);
    nt.addNote(n1, x);
    repaint();

  }

  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }


  public void deteleAll(ArrayList<int[]> lop3) {

    Set<Pitch> lop = nt.getCollections().keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();


    for (int i = 0; i < lop3.size(); i++) {
      int x = lop3.get(i)[0];
      int y = lop3.get(i)[1];


      for (Pitch p : lop) {
        TreeMap<Integer, IBeat> hm1 = nt.getCollections().get(p);
        Collection c1 = hm1.values();
        if (!c1.isEmpty()) {
          lop1.add(p);
        }
      }
      int min = lop1.get(0).hashCode();


      // get the pitches that are supposed to be displayed;
      int max = lop1.get(lop1.size() - 1).hashCode();
      for (int l = min; l < max + 1; l++) {
        Pitch p1 = new Pitch(l);
        lop2.add(p1);
      }

      //reverse the list
      Collections.reverse(lop2);
      Pitch pp = lop2.get(y);
      int pn = pp.pitchNumber();
      HeadBeat hb;

      if (nt.getCollections().get(pp).get(x) instanceof HeadBeat) {
        hb = (HeadBeat) nt.getCollections().get(pp).get(x);
        int duration = hb.getEnd() - hb.getPosition();
        nt.remove(new Note(new Pitch(pn), duration), hb.getPosition());
      }

    }
    repaint();
  }


  public void setModel(IMusic m) {
    nt = m;
  }
}
