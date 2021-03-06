package cs3500.music.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.repeats.Repeat;

/**te
 * A type of JPanel that is used to display Notes in an IMusicModel.
 */
public class ConcreteGuiView extends JPanel {

  // the max range of beats to display
  protected int displayedBeats = 48;

  // the number of beats per cell
  protected final int beatsPerCell = 4;

  // the number of columns to show at a time
  protected int numColumns = displayedBeats / beatsPerCell;

  // the number of rows to show at a time
  protected int numRows = 32;

  // the square dimensions of each beat representation
  protected int beatSquareDim = 20;

  // the height of each cell containing notes (pixels)
  protected int cellHeight = beatSquareDim;

  // the width of each cell containing notes (pixels)
  protected final int cellWidth = beatSquareDim * beatsPerCell;

  // the width of the space to the left of the note grid (pixels)
  protected int horizontalBuffer = 40;

  // the height of the space above the note (pixels)
  protected int verticalBuffer = 20;

  // C4 in MIDI, the default starting point for this view
  protected int defaultStartMidi = 60;

  // the lowest beat displayed
  protected int columnStart;

  // the highest pitch to be displayed
  protected int rowStartMidi;

  protected final IMusicModel model;
  protected int externalHorizontalBuffer;
  protected int externalVerticalBuffer;
  protected int currentTime;

  protected ConcreteGuiView(IMusicModel m) {
    super();
    this.model = m;
  }


  /**
   * Constructs a ConcreteGuiView
   *
   * @param model the music model to represent
   * @param externalHorizBuffer the horizontal buffer surrounding this panel from components
   *                            holding this panel
   * @param externalVertBuffer the vertical buffer surrounding this panel from components holding
   *                           this panel
   */
  ConcreteGuiView(IMusicModel model, int externalHorizBuffer, int externalVertBuffer) {
    super();
    this.model = model;
    this.externalHorizontalBuffer = externalHorizBuffer;
    this.externalVerticalBuffer = externalVertBuffer;
    try {
      this.rowStartMidi = model.getHighestNote().getMidiPitch();
    } catch (IllegalStateException e) {
      this.rowStartMidi = defaultStartMidi;
    }
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
   * Creates a JPanel to represent the range of Pitches starting from the rowStartMidi field. They
   * are stacked vertically. The number of pitches displayed are equal to this view's
   * numRows field.
   *
   * @return a JPanel representing the range of pitches vertically
   */
  private JPanel createPitchPanel() {
    int dim = NoteSquares.PREF_H;
    JPanel pitchP = new JPanel();
    pitchP.setLayout(new BoxLayout(pitchP, BoxLayout.PAGE_AXIS));
    pitchP.setPreferredSize(new Dimension(horizontalBuffer, cellHeight * numRows));
    for (int i = 0; i < numRows; i++) {
      int midiPitch = this.rowStartMidi - i;
      String pitch = Note.midiToPitch(midiPitch).toString() +
        Integer.toString(Note.midiToOctave(midiPitch));
      JLabel pitchLabel = new JLabel(pitch);
      pitchLabel.setBorder(BorderFactory.createEmptyBorder(dim / 5, 0, 0, dim / 5));
      pitchP.add(pitchLabel);
    }
    return pitchP;
  }

  /**
   * Creates a JPanel to represent the range of beats in starting from the columnStart field. The
   * beat labels are measured in increments of 16, starting from 0. Each beat label spans the width
   * of four NoteSquares panels.
   *
   * @return a JPanel representing beats, starting from this ConcreteGuiView's columnStart position
   * and increasing in increments of 16 horizontally
   */
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
   * Creates a JPanel to represent the notes contained in this ConcreteGuiView's model.
   * The layout of this panel is a GridLayout, each cell containing four beats (four quarter
   * notes).
   *
   * @return a JPanel representing the notes in this ConcreteGuiView's model (constrained to the
   * size of this view)
   */
  private JPanel createNotesPanel() {
    JPanel notesP = new JPanel(new GridLayout(this.numRows, this.numColumns));
    notesP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    List<List<NoteSquares>> panelHolder = new ArrayList<>();
    for (int i = 0; i < this.numRows; i++) {
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
   * @param notesP the grid of NoteSquares to be altered to represent the Notes in this model.
   */
  protected void initializeNotesPanel(List<List<NoteSquares>> notesP) {
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

  /**
   * Sets the externalHorizontalBuffer to the given buffer size
   * @param buffer the external buffer size
   */
  void setExternalHorizontalBuffer(int buffer) {
    this.externalHorizontalBuffer = buffer;
  }

  /**
   * Sets the externalVerticalBuffer to the given buffer size
   * @param buffer the external buffer size
   */
  void setExternalVerticalBuffer(int buffer) {
    this.externalVerticalBuffer = buffer;
  }

  /**
   * Resets this view panel, setting the starting beat at 0, the highest pitch as the highest
   * pitch in the model (or C4 if there are no notes in the model). The panel is then updated to
   * reflect these changes.
   */
  public void reset() {
    try {
      this.rowStartMidi = model.getHighestNote().getMidiPitch();
    } catch (IllegalStateException e) {
      this.rowStartMidi = defaultStartMidi;
    }
    this.columnStart = 0;
    this.currentTime = 0;
    this.updatePanel();
  }

  /**
   * Returns the pitch (in MIDI format) at the cursor's Y location.
   *
   * @return the pitch (in MIDI format) at the cursor's Y location
   * @throws IllegalStateException if the mouse's Y coordinate is outside of the pitch display range
   * or if the input is null
   */
  public int getPitchAtCursor() {
    Point mousePos = this.getMousePosition();
    if (mousePos == null) {
      throw new IllegalStateException("Mouse is outside the frame");
    }
    int mouseY = (int) mousePos.getY();
    mouseY = mouseY - externalVerticalBuffer - this.verticalBuffer + beatsPerCell;
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
   * @return the beat at the cursor's X location
   * @throws IllegalStateException if the mouse's X coordinate is outside of the beat display range
   * or if the input is null
   */
  public int getBeatAtCursor() {
    Point mousePos = this.getMousePosition();
    if (mousePos == null) {
      throw new IllegalStateException("Mouse is outside the frame");
    }
    int mouseX = (int) mousePos.getX();
    mouseX = mouseX - externalHorizontalBuffer - this.horizontalBuffer + beatsPerCell;
    if (mouseX >= 0) {
      mouseX /= beatSquareDim;
      return this.columnStart + mouseX;
    }
    else {
      throw new IllegalStateException("Mouse X coordinate is outside the beat display range");
    }
  }

  /**
   * Creates a vertical line representing the current position in time that fits within the
   * notesPanel.
   *
   * @return a vertical line representing the current position in time
   * @throws IllegalStateException if the view is out of sync with the current time
   */
  private Line2D getTimeLine() {
    if (this.currentTime >= columnStart && this.currentTime < columnStart + displayedBeats) {
      int xViewPos = currentTime - columnStart;
      int x = horizontalBuffer + (xViewPos * beatSquareDim) + (externalHorizontalBuffer / 2);
      int yTop = verticalBuffer + (externalVerticalBuffer / 2);
      int yBottom = verticalBuffer + (numRows * beatSquareDim) + (externalVerticalBuffer / 2);
      double xDouble = (double) x;
      double yTopDouble = (double) yTop;
      double yBottomDouble = (double) yBottom;
      return new Line2D.Double(xDouble, yTopDouble, xDouble, yBottomDouble);
    } else {
      throw new IllegalStateException("View is out of sync with current time");
    }
  }

  /**
   * Sets the current time to the given beat and repaints this panel, effectively setting the
   * beat bar at the given beat. If the view does not contain the given beat, it is shifted so
   * that the beat is displayed in the view.
   *
   * @param beat the beat to set the beat bar at
   * @throws IllegalArgumentException if the given beat is negative
   */
  void setBeatBar(int beat) {
    if (beat < 0) {
      throw new IllegalArgumentException("Cannot have a negative beat");
    }
    this.currentTime = beat;
    if (currentTime >= columnStart + displayedBeats || currentTime < columnStart) {
      this.columnStart = beat - (beat % beatsPerCell); // SIDE EFFECT!
      this.updatePanel();
    }
    this.revalidate();
    this.repaint();
  }

  /**
   * Shifts the view of this panel. The valid options are the following (in the exact case):
   * "left", "right", "up", or "down". Giving the argument "left" will decrease the beat interval,
   * and giving the argument "right" will do the opposite. The argument "up" will increase the
   * highest pitch displayed, and "down" will decrease the pitch range.
   *
   * <p>You cannot move left past beat 0, and you cannot move down past C0.</p>
   *
   * @param direction the direction to shift the view
   * @throws IllegalArgumentException if the given direction is not one of: left, right, up, or
   * down
   */
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
          this.rowStartMidi += 1;
        break;
      case "down" :
        if (Note.midiToOctave(this.rowStartMidi - numRows) >= 0) {
          this.rowStartMidi -= 1;
        }
        break;
      default :
        throw new IllegalArgumentException("Invalid direction");
    }
    this.updatePanel();
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

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    try {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.RED);
      g2.setStroke(new BasicStroke(2));
      g2.draw(getTimeLine());
    } catch (IllegalArgumentException | IllegalStateException e) {
      // do nothing
    }
  }
}
