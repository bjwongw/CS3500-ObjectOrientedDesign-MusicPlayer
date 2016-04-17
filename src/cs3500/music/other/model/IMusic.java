package cs3500.music.other.model;

import java.util.TreeMap;

/**
 * Created by renyuan on 3/4/16.
 */


//represent  a kind of music in some music system, it could be western music system or others.

public interface IMusic {


  /**
   * play two music simultaneously
   *
   * @param m the music you want to play simultaneously
   */
  void playSimultaneously(IMusic m);  //revised! changed parameter to IMusic


  /**
   * play two music Consecutively
   *
   * @param m the music you want to play Consecutively
   */
  void playConsecutively(IMusic m);   //revised! changed parameter to IMusic


  /**
   * add note to the music at the giving position
   *
   * @param n             the note you want to add
   * @param startPosition the position you want to add
   */
  void addNote(Note n, int startPosition);


  /**
   * remove a note from collection at a specific position
   *
   * @param n             the note that we want to remove at a collection
   * @param startPosition the start position we want to remove this note
   *
   *
   *                      // INVARIANT: startPosition must not be less than 0;
   */
  void remove(Note n, int startPosition);


  /**
   * rise the pitch of a note at the collection
   *
   * @param n             the note you want to rise
   * @param startPosition the start position the note is at
   */
  void rise(Note n, int startPosition);


  /**
   * lower the pitch of a note at the collection
   *
   * @param n             the note you want to lower its pitch
   * @param startPosition the start position the note is at
   */
  void fall(Note n, int startPosition);


  /**
   * get method
   *
   * @return the composition of the music
   */

  TreeMap<Pitch, TreeMap<Integer, IBeat>> getCollections();

  /**
   * a get method
   *
   * @return the size of the music
   */
  int getSize();


  /**
   * the method that for initialize a composition
   */
  void prepareAddNote();


  /**
   * get the tempo
   */
  int getTempo();


  /**
   * @return the text view of the music
   */
  String print();

}


