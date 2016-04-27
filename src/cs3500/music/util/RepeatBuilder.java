package cs3500.music.util;

/**
 * Interface to an IRepeatModel Builder
 */
public interface RepeatBuilder<T> extends CompositionBuilder<T>  {

  RepeatBuilder<T> addGoToStartOnceRepeat(int beat);

  RepeatBuilder<T> addGoToBeatOnceRepeat(int beat, int goTo);

  RepeatBuilder<T> addSkipSection(int goBackBeat, int goBackToBeat, int skipAtBeat);
}
