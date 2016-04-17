package cs3500.music.other.model;

/**
 * Created by renyuan on 3/3/16.
 */

// represents a beat with tone and start position
public interface IBeat {


  int getPosition();

  void setPosition(int position);


  /**
   * Determine if it's an first beat or not
   *
   * @return if it's first.
   */
  boolean isOnset();

  Pitch getPitch();


  /**
   * The String form of the note.
   *
   * @return The String form of the note.
   */

  String toString();
}
