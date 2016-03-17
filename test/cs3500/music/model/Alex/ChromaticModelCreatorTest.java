package cs3500.music.model.Alex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link ChromaticModelCreator}.
 *
 * Created by Alex on 3/4/2016.
 */
public class ChromaticModelCreatorTest {

  @Test
  public void chromaticTest() {
    IChromaticModel a;
    a = ChromaticModelCreator.create(ChromaticModelCreator.TYPE.CHROMATIC);
    assertTrue(a instanceof ChromaticModel);
    assertEquals("Your grammy is just a few clicks away! (There's nothing here...)", a
            .getStringRepresentation());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullInputTest() {
    ChromaticModelCreator.create(null);
  }

}
