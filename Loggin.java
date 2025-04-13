import javax.swing.*;
/*
Denna klass är ett Swing-GUI för en inloggningsskärm.
Den ger textfält för användaren att ange ett användarnamn och ip adress,
samt en inloggningsknapp för att skicka in dessa. När inloggningsknappen
trycks skickas det angivna användarnamnet och ip adressen till Server-klassen.
 */
public class Loggin extends JFrame {

    private JPanel JPanel;
    private JLabel ipLabel;
    private JButton loggin;
    private JLabel mauLabel;
    private JButton pfp;
    private JTextField port;
    private JLabel portLabel;
    private JTextField serverip;
    private JTextField username;
    private JLabel usernameLabel;
    private String picfile;
    private ImageIcon profilePic;
    private boolean ok;
    private String usernamn;
    private String server;
    private String portt;
    private Client client;

    /*
    Detta är konstruktorn för klassen.
    Den anropar initComponents-metoden, som initierar och konfigurerar GUI-komponenter
    (som textfält, etiketter, knappar etc.).
     */
    public Loggin(Client client) {
        this.client = client;
        initComponents();
        setVisible(true);
    }

    public boolean connect(){
        return ok;
    }

    /*
    Denna metod hämtar användarnamnet, server-IP-adressen och porten som angetts av
     användaren och loggar dem.
    Den returnerar användarnamnet, server-IP-adressen och porten.
     */
    public String loggin(){
        usernamn = this.username.getText();
        server = this.serverip.getText();
        portt = this.port.getText();
        return usernamn + server + portt;
    }

    public String getUsername(){
        return usernamn;
    }

    public String getServerip(){
        return server;
    }

    public int getPort(){
        return Integer.parseInt(portt);
    }

    public ImageIcon getPfp(){
        return profilePic;
    }

    private void initComponents() {

        JPanel = new JPanel();
        mauLabel = new JLabel();
        usernameLabel = new JLabel();
        ipLabel = new JLabel();
        portLabel = new JLabel();
        username = new JTextField();
        serverip = new JTextField();
        port = new JTextField();
        pfp = new JButton();
        loggin = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Loggin");

        JPanel.setToolTipText("");
        JPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        mauLabel.setIcon(new ImageIcon("src/files/mau2.png")); // NOI18N
        mauLabel.setText("jLabel1");

        usernameLabel.setText("Username");

        ipLabel.setText("IP address");

        portLabel.setText("Port");

        serverip.setPreferredSize(new java.awt.Dimension(64, 22));

        pfp.setText("Choose Profile Pic");
        pfp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pfpActionPerformed(evt);
            }
        });

        loggin.setText("Join Chatroom");
        loggin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logginActionPerformed(evt);
            }
        });

        GroupLayout JPanelLayout = new GroupLayout(JPanel);
        JPanel.setLayout(JPanelLayout);
        JPanelLayout.setHorizontalGroup(
                JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(JPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mauLabel, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(usernameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(ipLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(portLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(username)
                                        .addComponent(serverip, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(port)
                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                .addComponent(pfp, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(loggin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 10, Short.MAX_VALUE))
        );
        JPanelLayout.setVerticalGroup(
                JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, JPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                .addComponent(usernameLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(username, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(ipLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(serverip, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(portLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(port, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(pfp)
                                                        .addComponent(loggin)))
                                        .addComponent(mauLabel))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }
    /*
    Metoden tillåter användaren att välja och ändra sin profilbild genom att välja
     en bildfil från filsystemet. Om ingen bild väljs används en standardbild.
    Metoden kontrollerar också om den valda filen är en giltig bildfil
    och ger feedback till användaren vid behov.
     */
    private void pfpActionPerformed(java.awt.event.ActionEvent evt) {
        if(picfile == null) {
            picfile = "src/files/mau2.png";
            profilePic = ImageGUI.scaleImage(picfile, 50, 50);
        }
        JFileChooser jfc = new JFileChooser();
        int response = jfc.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION) {
            picfile = jfc.getSelectedFile().toString();
            if(ImageGUI.isImage(jfc.getSelectedFile().toString()) == false) {
                JOptionPane.showMessageDialog(null, "Fel filformat");
            } else {
                profilePic = ImageGUI.scaleImage(picfile, 50, 50);
            }
        }
    }
    private void logginActionPerformed(java.awt.event.ActionEvent evt) {
        this.ok = true;
        loggin();
        dispose();
    }

}
