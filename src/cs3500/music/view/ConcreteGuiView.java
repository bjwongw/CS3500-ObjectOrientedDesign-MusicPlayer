package cs3500.music.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A type of JPanel that is used to display Notes in an IMusicModel.
 */
public class ConcreteGuiView extends JPanel {

  // the lowest beat displayed
  private int columnStart;

  // the offset from the highest pitch in the model
  // a value of 0 indicates that the highest pitch is the first pitch displayed
  // a value of 1 indicates that the second highest pitch is the first pitch displayed, etc.
  private int rowStart;

  // the highest pitch to be displayed
  private int rowStartMidi;

  // the max range of beats to display
  private final int displayedBeats = 48;

  // the number of beats per cell
  private final int beatsPerCell = 4;

  // the number of columns to show at a time
  private final int numColumns = displayedBeats / beatsPerCell;

  // the number of rows to show at a time
  private final int numRows = 32;

  // the square dimensions of each beat representation
  private final int beatSquareDim = 20;

  // the height of each cell containing notes (pixels)
  private final int cellHeight = beatSquareDim;

  // the width of each cell containing notes (pixels)
  private final int cellWidth = beatSquareDim * beatsPerCell;

  // the width of the space to the left of the note grid (pixels)
  private final int horizontalBuffer = 40;

  // the height of the space above the note (pixels)
  private final int verticalBuffer = 20;

  private final IMusicModel model;

  private int currentTime;

  /**
   * Constructs the ConcreteGuiView.
   *
   * @param model the music model to represent
   */
  ConcreteGuiView(IMusicModel model) {
    super();
    this.model = model;
    this.rowStart = 0;
    this.rowStartMidi = model.getHighestNote().getMidiPitch() - rowStart;
    this.columnStart = 0;
    this.currentTime = 0;
    this.updatePanel();
    this.setDoubleBuffered(true);
  }

  /**
   * Updates this panel by removing the previous components (if any), and creating new
   * panels of the pitches, beats, and notes.
   */
  public void updatePanel() {
    this.removeAll();
    JPanel pitchPanel = createPitchPanel();
    JPanel beatPanel = createBeatPanel();
    JPanel notesPanel = createNotesPanel();

    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    setLayout(gridBag);

    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTH;
    this.add(pitchPanel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.anchor = GridBagConstraints.SOUTHWEST;
    this.add(beatPanel, constraints);

    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.anchor = GridBagConstraints.NORTHWEST;
    this.add(notesPanel, constraints);
  }

  /**
   * Creates a JPanel to represent the range of Pitches in this ConcreteGuiView's model. They
   * are stacked vertically. The maximum number of pitches displayed are equal to this view's
   * numRows field. The first pitch displayed is the pitch associated with the rowStart field.
   *
   * @return a JPanel representing the range of Pitches vertically
   */
  private JPanel createPitchPanel() {
    int dim = NoteSquares.PREF_H;
    JPanel pitchP = new JPanel();
    pitchP.setLayout(new BoxLayout(pitchP, BoxLayout.PAGE_AXIS));
    pitchP.setPreferredSize(new Dimension(horizontalBuffer, cellHeight * numRows));
    List<String> pitchRange = model.getPitchRange();
    int rangeSize = pitchRange.size();
    for (int i = 0; i < rangeSize && i < numRows; i++) {
      JLabel pitchLabel = new JLabel(pitchRange.get((rangeSize - (rowStart + 1)) - i));
      pitchLabel.setBorder(BorderFactory.createEmptyBorder(dim / 5, 0, 0, dim / 5));
      pitchP.add(pitchLabel);
    }
    return pitchP;
  }

  /**
   * Creates a JPanel to represent the range of beats in this ConcreteGuiView's model. The
   * beat labels are measured in increments of 16, starting from 0. Each beat label spans the width
   * of four NoteSquares panels.
   *
   * @return a JPanel representing the beats in this ConcreteGuiView's model, starting from 0
   * and increasing in increments of 16 horizontally
   */
  /* TODO deal with final beat (if there's only one note at beat 1 for duration 1, too many
    columns are created. */
  private JPanel createBeatPanel() {
    int dim = NoteSquares.PREF_W;
    GridBagLayout gridBag = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    JPanel beatP = new JPanel(gridBag);
    beatP.setPreferredSize(new Dimension(displayedBeats * dim, verticalBuffer));
    if (columnStart % 16 == 0) {
      for (int i = 0; i < displayedBeats / 16; i++) {
        JLabel beatLabel = new JLabel(Integer.toString(columnStart + (i * 16)));
        beatLabel.setPreferredSize(new Dimension(dim * 16, dim));
        constraints.gridx = i;
        constraints.gridy = 0;
        beatP.add(beatLabel, constraints);
      }
    } else {
      for (int i = 0; i <= displayedBeats / 16; i++) {
        JLabel beatLabel;
        if (i == 0) {
          beatLabel = new JLabel();
          int labelWidth = 16 - (columnStart % 16);
          beatLabel.setPreferredSize(new Dimension(dim * labelWidth, dim));
        } else if (i < displayedBeats / 16) {
          int beat = ((columnStart / 16) + i) * 16;
          beatLabel = new JLabel(Integer.toString(beat));
          beatLabel.setPreferredSize(new Dimension(dim * 16, dim));
        } else {
          int beat = ((columnStart / 16) + i) * 16;
          beatLabel = new JLabel(Integer.toString(beat));
          int labelWidth = columnStart % 16;
          beatLabel.setPreferredSize(new Dimension(dim * labelWidth, dim));
        }
        constraints.gridx = i;
        constraints.gridy = 0;
        beatP.add(beatLabel, constraints);
      }
    }
    return beatP;
  }

  /**
   * Creates a JPanel to represent all the notes contained in this ConcreteGuiView's model.
   * The layout of this panel is a GridLayout, each cell containing four beats (four quarter
   * notes).
   *
   * @return a JPanel representing all the notes in this ConcreteGuiView's model.
   */
  // TODO ensure the proper size of the notesPanel
  private JPanel createNotesPanel() {
    int pitchRows = model.getPitchRange().size();
    int rows = Integer.min(pitchRows, this.numRows);
    JPanel notesP = new JPanel(new GridLayout(rows, this.numColumns));
    notesP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    List<List<NoteSquares>> panelHolder = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      panelHolder.add(new ArrayList<>());
      for (int j = 0; j < this.numColumns; j++) {
        NoteSquares squares = new NoteSquares(beatsPerCell);
        squares.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelHolder.get(i).add(squares);
        notesP.add(panelHolder.get(i).get(j));
      }
    }
    initializeNotesPanel(panelHolder);
    return notesP;
  }

  /**
   * Given a {@code List<List<NoteSquares>>}, this method adjusts all the NoteSquares to represent
   * notes from this ConcreteGuiView's model. Each start beat of a Note is associated with a
   * black square in the NoteSquare, and each sustained beat for that same Note is represented as a
   * green square (with color value of RGB(42, 255, 55)).
   *
   * NOTE: may need to control for invariants, such as the notesP parameter being the wrong size in
   * comparison to the model field in this ConcreteGuiView.
   *
   * @param notesP the grid of NoteSquares to be altered to represent the Notes in this model.
   */
  private void initializeNotesPanel(List<List<NoteSquares>> notesP) {
    for (int i = columnStart; i < columnStart + displayedBeats; i++) {
      List<Note> sustainedNotes = new ArrayList<>(model.sustainedNotes(i));
      Collections.sort(sustainedNotes);
      for (Note n : sustainedNotes) {
        int pitchIndex = this.rowStartMidi - n.getMidiPitch();
        if (pitchIndex >= 0 && pitchIndex < numRows) {
          List<NoteSquares> pitchList = notesP.get(pitchIndex);
          int start = i - columnStart;
          pitchList.get(start / beatsPerCell).setNoteColor(start % beatsPerCell,
            new Color(42, 255, 55));
        }
      }
      List<Note> noteList = new ArrayList<>(model.notesToPlay(i));
      Collections.sort(noteList);
      for (Note n : noteList) {
        int pitchIndex = this.rowStartMidi - n.getMidiPitch();
        if (pitchIndex >= 0 && pitchIndex < numRows) {
          List<NoteSquares> pitchList = notesP.get(pitchIndex);
          int start = n.getStart() - columnStart;
          pitchList.get(start / beatsPerCell).setNoteColor(start % beatsPerCell, Color.BLACK);
        }
      }
    }
  }

  // TODO red line disappears when screen shifts
  private Line2D getTimeLine() {
    int x = horizontalBuffer + (currentTime * beatSquareDim) + 10; // 10 is externalBuffer/2
    int yTop = verticalBuffer + 10; // 10 is externalBuffer/2
    int yBottom = verticalBuffer + (numRows * beatSquareDim) - 10;
    if (currentTime == columnStart + displayedBeats) {
      this.columnStart += displayedBeats; // SIDE EFFECT!
      this.updatePanel();
    }
    double xDouble = (double) x;
    double yTopDouble = (double) yTop;
    double yBottomDouble = (double) yBottom;
    System.out.println("X: " + x + ", yTop: " + yTop + ", yBottom: " + yBottom);
    return new Line2D.Double(xDouble, yTopDouble, xDouble, yBottomDouble);
  }

  void setCurrentTime(int time) {
    this.currentTime = time;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.RED);
    g2.setStroke(new BasicStroke(2));
    g2.draw(getTimeLine());
  }

  /**
   * Shifts the view of this panel. The valid options are the following (in the exact case):
   * "left", "right", "up", or "down". Giving the argument "left" will decrease the beat interval,
   * and giving the argument "right" will do the opposite. The argument "up" will increase the
   * highest pitch displayed, and "down" will decrease the pitch range.
   *
   * @param direction the direction to shift the view
   * @throws IllegalArgumentException if the given direction is not one of: left, right, up, or
   * down
   */
  // TODO allow you to shift outside the bounds of the song
  public void shift(String direction) {
    switch(direction) {
      case "left" :
        if (this.columnStart - beatsPerCell >= 0) {
          this.columnStart -= beatsPerCell;
        }
        break;
      case "right" : // make it stop at the end of the piece
        this.columnStart += beatsPerCell;
        break;
      case "up" :
        if (this.rowStart - 1 >= 0) {
          this.rowStart -= 1;
          this.rowStartMidi += 1;
        }
        break;
      case "down" :
        this.rowStart += 1;
        this.rowStartMidi -= 1;
        break;
      default :
        throw new IllegalArgumentException("Invalid direction");
    }
    this.updatePanel();
  }

  /**
   * Resets this view panel, setting the starting beat at 0, the highest pitch as the highest
   * pitch in the model, and the rowStart field at 0. The panel is then updated to reflect these
   * changes.
   */
  public void reset() {
    this.rowStart = 0;
    this.rowStartMidi = model.getHighestNote().getMidiPitch() - rowStart;
    this.columnStart = 0;
    this.updatePanel();
  }

  /**
   * Returns the pitch (in MIDI format) at the cursor's Y location.
   *
   * @param externalVertBuffer the buffer from any external panels or frames holding this panel
   * @return the pitch (in MIDI format) at the cursor's Y location
   * @throws IllegalStateException if the mouse's Y coordinate is outside of the pitch display range
   */
  public int getPitchAtCursor(int externalVertBuffer) {
    Point mousePos = this.getMousePosition();
    int mouseY = (int) mousePos.getY();
    mouseY = mouseY - externalVertBuffer - this.verticalBuffer + beatsPerCell;
    if (mouseY >= 0) {
      mouseY /= cellHeight;
      return this.rowStartMidi - mouseY;
    }
    else {
      throw new IllegalStateException("Mouse Y coordinate is outside the pitch display range");
    }
  }

  /**
   * Returns the beat at the cursor's X location.
   *
   * @param externalHorizBuffer the buffer from any external panels or frames holding this panel
   * @return the beat at the cursor's X location
   * @throws IllegalStateException if the mouse's X coordinate is outside of the beat display range
   */
  public int getBeatAtCursor(int externalHorizBuffer) {
    Point mousePos = this.getMousePosition();
    int mouseX = (int) mousePos.getX();
    mouseX = mouseX - externalHorizBuffer - this.horizontalBuffer + beatsPerCell;
    if (mouseX >= 0) {
      mouseX /= (cellWidth / beatsPerCell);
      return mouseX;
    }
    else {
      throw new IllegalStateException("Mouse X coordinate is outside the beat display range");
    }
  }

  /**
   * Displays the end of the model. The last beat played will be in the farthest right cell
   * displayed. The columnStart field is set to enable this view, and the panel is updated.
   */
  public void goToEnd() {
    int finalBeat = this.model.finalBeat();
    int displayedSections = this.displayedBeats / beatsPerCell;
    int sectionsToFinalBeat = finalBeat / beatsPerCell;
    int offset = sectionsToFinalBeat - displayedSections + 1; // +1 because beats are displayed
                                                              // starting at 0
    if (offset <= 0) {
      this.columnStart = 0;
    } else {
      this.columnStart = offset * beatsPerCell;
    }
    this.updatePanel();
  }
}
