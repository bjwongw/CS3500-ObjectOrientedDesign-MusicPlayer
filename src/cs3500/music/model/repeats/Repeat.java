package cs3500.music.model.repeats;

/**
 * Interface to a repeat. Inserted at a beat in the model, informs which beat to go to next.
 *
 * Created by Alex on 4/26/2016.
 */
public interface Repeat {

  /**
   * Gets the next beat to be played after the beat this repeat represents. Also tells the repeat
   * that it has been passed over by the player, whatever that may implicate.
   *
   * @return the next beat
   */
  int nextBeat();

  /**
   * Resets this repeat to its initial state. Allows replaying.
   */
  void reset();

  /**
   * Returns the home of this repeat.
   *
   * @return the beat of this repeat
   */
  int getBeat();

  /**
   * Returns true if this is a basic repeat
   * @return above
   */
  boolean isBasic();

}
