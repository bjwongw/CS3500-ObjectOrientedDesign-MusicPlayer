package cs3500.music.other.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import cs3500.music.util.*;

/**
 * Created by renyuan on 3/3/16.
 */

//represents a collection of notes with order on time
public class MusicModel implements IMusic {

  private int size;                                           // how many beats the collection has
  private TreeMap<Pitch, TreeMap<Integer, IBeat>> collections; // collections of notes
  private int tempo;

  //revised!
  // filed temp is added here to store temp information

  //constructor
  public MusicModel(int size) {
    this.size = size;
    this.collections = new TreeMap<Pitch, TreeMap<Integer, IBeat>>();
    this.tempo = 1;
  }

  /**
   * this function must be called at the beginning when you want to add notes to our collection
   */

  public void prepareAddNote() {
    for (int i = 1; i < 121; i++) {
      collections.put(new Pitch(i), new TreeMap<Integer, IBeat>());
    }
  }


  /**
   * add an note to a collection of notes
   *
   * @param n             the note that we want to add to a collection
   * @param startPosition the start position we want add this note
   *
   *
   *                      // INVARIANT: startPosition must not be less than 0;
   */

  public void addNote(Note n, int startPosition) {

    if (startPosition < 0) {
      throw new IllegalArgumentException("startPosition can't be less than 0");
    }

    Pitch p = n.getPitch();
    int d = n.getDuration();
    int s1 = startPosition + d;
    if (s1 > size) {
      size = s1;
    }
    //if(startPosition<0 || startPosition >size || d+startPosition>size+1){
    //throw new IllegalArgumentException("you cant add this note at this position");
    //}
    HeadBeat hb = new HeadBeat(p, n.getStarttime(), n.getEndTime(), n.getInstrumentt(),
            n.getVolumee());

    TreeMap<Integer, IBeat> hm = collections.get(p);
    hm.put(startPosition, hb);
    if (d > 1) {
      for (int i = 1; i < d; i++) {
        TailBeat tb = new TailBeat(p, startPosition + i);
        hm.put(startPosition + i, tb);

      }
    }
  }


  /**
   * remove a note from collection at a specific position
   *
   * @param n             the note that we want to remove at a collection
   * @param startPosition the start position we want to remove this note
   *
   *
   *                      // INVARIANT: startPosition must not be less than 0;
   */
  public void remove(Note n, int startPosition) {

    if (startPosition < 0 || startPosition > size) {
      throw new IllegalArgumentException("wrong position");
    }
    Pitch p = n.getPitch();
    Integer d = n.getDuration();
    TreeMap<Integer, IBeat> hm = this.collections.get(p);
    hm.remove(startPosition);
    if (d > 1) {
      for (int i = 1; i < d; i++) {
        collections.get(p).remove(startPosition + i);
      }
    }
  }


  /**
   * rise the pitch of a note at the collection
   *
   * @param n             the note you want to rise
   * @param startPosition the start position the note is at
   */

  public void rise(Note n, int startPosition) {
    if (n.getPitch().hashCode() > 119) {
      throw new IllegalArgumentException("my application doest support octave greater than 10");
    }
    remove(n, startPosition);
    n.rise();
    addNote(n, startPosition);
  }


  /**
   * lower the pitch of a note at the collection
   *
   * @param n             the note you want to lower its pitch
   * @param startPosition the start position the note is at
   */
  public void fall(Note n, int startPosition) {
    if (n.getPitch().hashCode() < 2) {
      throw new IllegalArgumentException("you can lower the lowest pitch");
    }
    remove(n, startPosition);
    n.fall();
    addNote(n, startPosition);

  }


  /**
   * Print the collection in a Text.
   *
   * @return the text form of the collection
   */
  public String print() {

    String result = "";
    Set<Pitch> lop = collections.keySet();
    ArrayList<Pitch> lop1 = new ArrayList<Pitch>();
    ArrayList<Pitch> lop2 = new ArrayList<Pitch>();
    for (Pitch p : lop) {
      TreeMap<Integer, IBeat> hm1 = collections.get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        lop1.add(p);
      }
    }
    if (lop1.isEmpty()) {
      return "";
    }

    int min = lop1.get(0).hashCode();
    int max = lop1.get(lop1.size() - 1).hashCode();
    for (int l = min; l < max + 1; l++) {
      lop2.add(new Pitch(l));
    }

    //show pitch name
    for (int i = -1; i < this.size; i++) {
      if (i == -1) {
        result = result + "  ";
        for (int j = 0; j < lop2.size(); j++) {
          Pitch p = lop2.get(j);
          if (p.toString().length() == 2) {
            result = result + "   " + p.toString();
          } else if (p.toString().length() == 3) {
            result = result + "  " + p.toString();
          } else {
            result = result + " " + p.toString();
          }
        }
        result = result + "\n";
      }

      // Show Note
      else {
        if (i > 9) {
          result = result + i;
        } else {
          result = result + " " + i;
        }

        IBeat ib;

        for (Pitch p : lop2) {
          if (lop2.contains(p)) {
            TreeMap<Integer, IBeat> Lov = collections.get(p);
            if (Lov.containsKey(i)) {
              ib = Lov.get(i);
              String sob = ib.getPitch().toString();
              if (sob.length() == 2) {
                result = result + "   " + ib.toString() + " ";
              } else if (sob.length() == 3) {
                result = result + "  " + ib.toString() + "  ";
              } else {
                result = result + " " + ib.toString() + "   ";
              }
            } else {
              // show nothing
              result = result + "     ";
            }
          } else {
            result = result + "     ";
          }
        }
        // Next line
        result = result + "\n";
      }
    }
    return result;

  }

  public void playSimultaneously(IMusic n) {
    Set<Pitch> sop2 = n.getCollections().keySet();

    for (Pitch p : sop2) {
      TreeMap<Integer, IBeat> hm1 = n.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        int size = n.getSize();
        for (int i = 0; i < size; i++) {
          if (n.getCollections().get(p).containsKey(i)) {
            collections.get(p).put(i, n.getCollections().get(p).get(i));
          }
        }
      }
    }
  }


  public void playConsecutively(IMusic n) {
    size = 128;
    Set<Pitch> sop2 = n.getCollections().keySet();

    for (Pitch p : sop2) {
      TreeMap<Integer, IBeat> hm1 = n.getCollections().get(p);
      Collection c1 = hm1.values();
      if (!c1.isEmpty()) {
        for (int i = 64; i < 128; i++) {
          if (n.getCollections().get(p).containsKey(i - 64)) {
            collections.get(p).put(i, n.getCollections().get(p).get(i - 64));
          }
        }
      }
    }
  }


  public TreeMap<Pitch, TreeMap<Integer, IBeat>> getCollections() {
    return this.collections;
  }


  public void setTempo(int tem) {
    this.tempo = tem;
  }

  public int getSize() {
    return size;
  }

  public int getTempo() {
    return tempo;
  }


  public static final class Builder implements CompositionBuilder<IMusic> {

    protected MusicModel nt = new MusicModel(64);

    public Builder() {
      nt.prepareAddNote();
    }


    /**
     * Constructs an actual composition, given the notes that have been added
     *
     * @return The new composition
     */
    @Override
    public IMusic build() {

      return this.nt;
    }

    /**
     * Sets the tempo of the piece
     *
     * @param tempo The speed, in microseconds per beat
     * @return This builder
     */
    @Override
    public CompositionBuilder<IMusic> setTempo(int tempo) {
      this.nt.setTempo(tempo);
      return this;
    }

    /**
     * Adds a new note to the piece
     *
     * @param start      The start time of the note, in beats
     * @param end        The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch      The pitch (in the range [0, 127], where 60 represents C4,
     *                   the middle-C on a
     *                   piano)
     * @param volume     The volume (in the range [0, 127])
     */
    @Override
    public CompositionBuilder<IMusic>
    addNote(int start, int end, int instrument, int pitch, int volume) {
      Note n1 = new Note(new Pitch(pitch - 23), end - start);
      n1.Set(start, end, instrument, pitch - 23, volume);
      nt.addNote(n1, start);
      return this;
    }

  }


}







