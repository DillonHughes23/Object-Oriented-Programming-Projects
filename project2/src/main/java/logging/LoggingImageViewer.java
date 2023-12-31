package logging;

import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import static javax.swing.WindowConstants.*;

/**
 * A modification of the image viewer program that logs various events.
 */
public class LoggingImageViewer {

    public static void main(String[] args) {
        if (System.getProperty("java.util.logging.config.class") == null
                && System.getProperty("java.util.logging.config.file") == null) {
            try {
                Logger.getLogger(LoggingImageViewer.class.getName()).setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("%h/Temp/LoggingImageViewer.log.%u.%g.txt", 0, LOG_ROTATION_COUNT);
                handler.setFormatter(new SimpleFormatter());
                Logger.getLogger(LoggingImageViewer.class.getName()).addHandler(handler);
            } catch (IOException e) {
                Logger.getLogger(LoggingImageViewer.class.getName()).log(Level.SEVERE,
                        "Can't create log file handler", e);
            }
        }

        Handler windowHandler = new WindowHandler();
        windowHandler.setLevel(Level.ALL);
        Logger.getLogger(LoggingImageViewer.class.getName()).addHandler(windowHandler);

        JFrame frame = new ImageViewerFrame();
        frame.setTitle("LoggingImageViewer");
        frame.setSize(300, 400);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Logger.getLogger(LoggingImageViewer.class.getName()).fine("Showing frame");
        frame.setVisible(true);
    }
}

/**
 * The frame that shows the image.
 */
class ImageViewerFrame extends JFrame {

    public ImageViewerFrame() {
        logger.entering("ImageViewerFrame", "<init>");
        // set up menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        menu.add(openItem);
        openItem.addActionListener(new FileOpenListener());

        JMenuItem exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                logger.fine("Exiting.");
                System.exit(0);
            }
        });

        // use a label to display the images
        label = new JLabel();
        add(label);
        logger.exiting("ImageViewerFrame", "<init>");
    }

    private class FileOpenListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", event);

            // set up file chooser
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            // accept all files ending with .gif
            chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
                }

                public String getDescription() {
                    return "GIF Images";
                }
            });

            // show file chooser dialog
            int r = chooser.showOpenDialog(ImageViewerFrame.this);

            // if image file accepted, set it as icon of the label
            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                logger.log(Level.FINE, "Reading file {0}", name);
                label.setIcon(new ImageIcon(name));
            } else {
                logger.fine("File open dialog canceled.");
            }
            logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
        }
    }

    private JLabel label;
    private static Logger logger = Logger.getLogger("com.horstmann.corejava");
}

/**
 * A handler for displaying log records in a window.
 */
class WindowHandler extends StreamHandler {

    public WindowHandler() {
        frame = new JFrame();
        final JTextArea output = new JTextArea();
        output.setEditable(false);
        frame.setSize(600, 400);
        frame.add(new JScrollPane(output));
        frame.setFocusableWindowState(false);
        frame.setVisible(true);
        setOutputStream(new OutputStream() {
            public void write(int b) {
            } // not called

            public void write(byte[] b, int off, int len) {
                output.append(new String(b, off, len));
            }
        });
    }

    public void publish(LogRecord record) {
        if (!frame.isVisible()) {
            return;
        }
        super.publish(record);
        flush();
    }

    private JFrame frame;
}
