package cs3500.music.model;

import cs3500.music.model.repeats.Repeat;

/**
 * Interface to a model that can handle repeats.
 */
public interface IRepeatModel extends IMusicModel {

  /**
   * Returns the next beat to play given the current beat. Generally, currentBeat + 1 unless there
   * is a repeat at currentBeat that says otherwise.
   *
   * @return the next beat to play
   */
  int nextBeat(int currentBeat);

  /**
   * Resets any repeats in this piece so that it may be replayed.
   */
  void reset();

  /**
   * Adds a repeat at the given beat that sends the player to the start on the first visite, and
   * then does nothing on subsequent visits.
   *
   * @param beat the beat at which to place the repeat
   * @return this, for convenience
   */
  IRepeatModel addGoToStartOnceRepeat(int beat);

  /**
   * Adds a repeat at the given beat that sends the player to the given goTo beat on the first
   * visit, and then does nothing on subsequent visits.
   *
   * @param beat the beat at which to place the repeat
   * @param goTo the beat to go to on first visit
   * @return this, for convenience
   */
  IRepeatModel addGoToBeatOnceRepeat(int beat, int goTo);

  /**
   * Adds an alternative part repeat that sends the player to goBackToBeat when it visits
   * goBackBeat for the first time. Then, when the player visits skipAtBeat, skips to the beat
   * after goBackBeat.
   *
   * @param goBackBeat go back to goBackToBeat on first visit
   * @param goBackToBeat go back to this beat
   * @param skipAtBeat skip to goBackBeat + 1 on second+ visits
   * @return this, for convenience
   */
  IRepeatModel addAlternateRepeat(int goBackBeat, int goBackToBeat, int skipAtBeat);


}
