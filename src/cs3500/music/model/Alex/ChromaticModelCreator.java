package cs3500.music.model.Alex;

/**
 * Allows the production of all available implementations of {@link IChromaticModel}.
 *
 * Created by Alex on 3/4/2016.
 */
public class ChromaticModelCreator {

  public enum TYPE {
    CHROMATIC
  }

  public static IChromaticModel create(TYPE t) {
    if (t == null) {
      throw new IllegalArgumentException("t is null!");
    }
    return new ChromaticModel();
  }

}
