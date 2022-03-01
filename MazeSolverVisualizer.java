import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Maze Solver Visualizer, CIS 121
 *
 * @author hanbangw, 20sp
 */
final class MazeSolverVisualizer {
    private final static int MAX_CELL_SIZE = 80;
    private final static Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
    private final static Pattern numbers = Pattern.compile("([01])");
    private static int MAX_SIZE;
    private static JFrame mainFrame;
    private static JPanel mainPanel;
    private static GridBagLayout mainLayout;
    private static JButton runBtn;
    private static int rows, cols;
    private static Coordinate srcCoord, goalCoord;

    public static void main(String[] args) throws Exception {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        MAX_SIZE = (int) (Math.min(screenSize.width, screenSize.height) * 0.75);
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        UIManager.put("Button.disabledText", Color.black);
        javax.swing.SwingUtilities.invokeLater(MazeSolverVisualizer::createAndShowGUI);
    }

    private static void newMaze() {
        JDialog dialog = new JDialog(mainFrame, "New Maze", true);
        JTabbedPane panel = new JTabbedPane();

        JPanel realCard1 = new JPanel(new BorderLayout());
        realCard1.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel card1 = new JPanel(new GridLayout(2, 1, 0, 5));

        final Function<String, JSpinner> addLabeledSpinner = label -> {
            JLabel l = new JLabel(label);
            card1.add(l);
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(5, 1, null, 1));
            l.setLabelFor(spinner);
            card1.add(spinner);
            return spinner;
        };
        JSpinner rows = addLabeledSpinner.apply("# Rows:");
        JSpinner cols = addLabeledSpinner.apply("# Columns:");
        realCard1.add(card1, BorderLayout.NORTH);
        JButton create = new JButton("Create");
        create.addActionListener(e -> {
            createMaze((int) rows.getValue(), (int) cols.getValue());
            dialog.dispose();
        });
        realCard1.add(create, BorderLayout.SOUTH);

        JPanel card2 = new JPanel(new BorderLayout(0, 5));
        card2.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel hint = new JLabel("Type in or paste your maze here:");
        JEditorPane editorPane = new JEditorPane();
        editorPane.setPreferredSize(new Dimension(300, 250));
        card2.add(hint, BorderLayout.NORTH);
        card2.add(new JScrollPane(editorPane), BorderLayout.CENTER);
        JButton imp = new JButton("Import");
        imp.addActionListener(e -> {
            final String err = importMaze(editorPane.getText());
            if (err == null) dialog.dispose();
            else JOptionPane.showMessageDialog(dialog, err);
        });
        card2.add(imp, BorderLayout.SOUTH);

        panel.addTab("Create Empty Maze", realCard1);
        panel.addTab("Import Maze", card2);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);
    }

    private static void onClick(JButton src) {
        if (src.getBackground().equals(Color.black)) return;
        GridBagConstraints constraints = mainLayout.getConstraints(src);
        Coordinate coord = new Coordinate(constraints.gridx, constraints.gridy);
        if (srcCoord == null) {
            src.setText("S");
            srcCoord = coord;
            src.setEnabled(false);
        } else if (goalCoord == null) {
            if (!srcCoord.equals(coord)) {
                src.setText("G");
            } else {
                src.setText("S/G");
            }
            goalCoord = coord;
            src.setEnabled(false);
            runBtn.setEnabled(true);
        } else {
            for (Component c : mainPanel.getComponents()) {
                if (c instanceof JButton) {
                    JButton cc = ((JButton) c);
                    if (!cc.isEnabled()) {
                        cc.setText(null);
                        cc.setEnabled(true);
                        cc.setBackground(Color.white);
                    }
                }
            }
            src.setText("S");
            srcCoord = coord;
            src.setEnabled(false);
            runBtn.setEnabled(false);
            goalCoord = null;
        }
    }

    private static void onRightClick(JButton src) {
        final String text = src.getText();
        if (src.getBackground().equals(Color.black))
            src.setBackground(Color.white);
        else if (text == null || text.isEmpty()) {
            src.setBackground(Color.black);
            src.setEnabled(true);
        }
    }

    private static void createMaze(int ro, int co) {
        rows = ro;
        cols = co;
        mainPanel.removeAll();
        mainLayout = new GridBagLayout();
        mainPanel.setLayout(mainLayout);
        final int size = Math.min(MAX_CELL_SIZE, Math.min(MAX_SIZE / rows, MAX_SIZE / cols));
        final Dimension d = new Dimension(size, size);
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                final JButton btn = new JButton();
                btn.setMargin(ZERO_INSETS);
                btn.setPreferredSize(d);
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) onClick(btn);
                        else onRightClick(btn);
                    }
                });
                btn.setBorder(new LineBorder(Color.black));
                btn.setBackground(Color.white);
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = j;
                c.gridy = i;
                mainPanel.add(btn, c);
            }
        }
        runBtn.setEnabled(false);
        mainFrame.pack();
        mainFrame.repaint();
    }

    private static String importMaze(String text) {
        final String[] splits = text.split("\n");
        ArrayList<ArrayList<Boolean>> ll = new ArrayList<>();
        for (final String s : splits) {
            final Matcher matcher = numbers.matcher(s);
            final ArrayList<Boolean> l = new ArrayList<>();
            while (matcher.find()) {
                l.add(matcher.group(0).charAt(0) == '1');
            }
            if (l.isEmpty()) continue;
            if (ll.isEmpty() || ll.get(0).size() == l.size())
                ll.add(l);
            else return "Unable to parse the input maze.";
        }

        if (ll.isEmpty()) return "Unable to parse the input maze.";

        final int rows = ll.size(), cols = ll.get(0).size();
        createMaze(rows, cols);
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < cols; ++j)
                if (ll.get(i).get(j))
                    onRightClick((JButton) mainPanel.getComponent(i * cols + j));
        return null;
    }

    private static void solveMaze() {
        final int[][] maze = new int[rows][cols];
        final Component[] cps = mainPanel.getComponents();
        assert cps.length == rows * cols;
        for (int i = 0; i < cps.length; ++i) {
            final JButton btn = (JButton) cps[i];
            if (btn.getBackground() == Color.black) {
                maze[i / cols][i % cols] = 1;
            } else {
                maze[i / cols][i % cols] = 0;
                btn.setBackground(Color.white);
            }
        }
        try {
            final int[][] path = MazeSolverImpl.solveMaze(maze, srcCoord, goalCoord);
            if (path == null) {
                JOptionPane.showMessageDialog(mainFrame, "No path found.");
                return;
            }
            for (int i = 0; i < rows; ++i)
                for (int j = 0; j < cols; ++j)
                    if (path[i][j] == 1) {
                        final JButton btn = (JButton) cps[i * cols + j];
                        btn.setEnabled(false);
                        btn.setBackground(Color.green);
                    }
        } catch (Exception err) {
            JOptionPane.showMessageDialog(mainFrame, err);
        }
    }

    private static void createAndShowGUI() {
        mainFrame = new JFrame("Maze Solver Visualizer");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainPanel = new JPanel();
        mainPanel.add(new JLabel("No maze initialized yet."));
        mainPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        JLabel hintText = new JLabel("<html>Left click to select the source cell and the goal cell.<br>" +
                "Right click to block or unblock a cell.</html>");
        hintText.setHorizontalAlignment(JLabel.CENTER);
        hintText.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        JButton mazeBtn = new JButton("New Maze");
        mazeBtn.addActionListener(e -> newMaze());
        runBtn = new JButton("Solve Maze");
        runBtn.addActionListener(e -> solveMaze());
        runBtn.setEnabled(false);
        buttonPanel.add(mazeBtn);
        buttonPanel.add(runBtn);

        mainFrame.add(buttonPanel, BorderLayout.NORTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(hintText, BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setLocationByPlatform(true);
        mainFrame.setVisible(true);

        mazeBtn.doClick();
    }
}