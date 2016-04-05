package cs3500.music.view;

import java.awt.*;
import java.awt.event.KeyListener;
import javax.swing.*;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * The visual view for a composition
 */
public class GuiViewImpl extends JFrame implements IGuiView {

  private IMusicModel model;
  private CompositionViewPanel displayPanel;
  private int time;

  // the lowest beat displayed
  private int columnStart;

  // the highest pitch displayed, in MIDI format
  private int rowStart;

  // the total range of beats to display
  private final int displayedBeats = 48;

  // the number of columns to show at a time
  private final int numColumns = displayedBeats / 4;

  // the number of rows to show at a time
  private final int numRows = 32;

  // the width of each cell containing notes (pixels)
  private final int width = 80;

  // the height of each cell containing notes (pixels)
  private final int height = width / 4;

  // the width of the space to the left of the note grid (pixels)
  private final int horizontalBuffer = 40;

  // the height of the space above the note (pixels)
  private final int verticalBuffer = 20;

  /**
   * Creates a new GuiViewImpl
   */
  public GuiViewImpl() {
    super("Music Player"); // sets the title of the frame
    this.time = 0;
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public void initialize(IMusicModel m) {
    this.model = m;
    this.displayPanel = new CompositionViewPanel(model);
    JScrollPane scroll = new JScrollPane(displayPanel);
    getContentPane().add(scroll);
    pack();
    this.setPreferredSize(this.getPreferredSize());
    this.setVisible(true);
  }

  @Override
  public void play() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void reset() {

  }

  @Override
  public Note.Pitch getPitchAt(int x, int y) {
    return null;
  }

  @Override
  public int getBeatAt(int x, int y) {
    return 0;
  }

  @Override
  public void update() {

  }

  @Override
  public void scrollRight() {

  }

  @Override
  public void scrollLeft() {

  }

  @Override
  public void scrollUp() {

  }

  @Override
  public void scrollDown() {

  }

  @Override
  public void goToStart() {

  }

  @Override
  public void goToEnd() {

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 750);
  }
}
