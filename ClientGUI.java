import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;



public class ClientGUI extends JFrame {

    private JPanel JPanel;
    private JButton refreshButton;
    private JButton addContactButton;
    private JTextArea chatTextArea;
    private JLabel chattLabel;
    private JScrollPane chattSPane;
    private JLabel contactsLabel;
    private JScrollPane contactsSPane;
    private JButton discButton;
    private JButton fileButton;
    private JScrollPane jScrollPane1;
    private JLabel lblSendTo;
    private JButton messageButton;
    private JTextField messageTextField;
    private JLabel onlineLabel;
    private JScrollPane onlineSPane;
    private JTextPane onlineTextPane;
    private JList<String> onlineUserList;
    private JLabel peopleLabel;
    private JLabel pepLabel;
    private JButton removeContactButton;
    private JList<String> savedKontakterLista;
    private JButton sendToButton;
    private Client client;
    private ArrayList<JLabel> lblUsers = new ArrayList<>();
    private StringBuilder sendTo = new StringBuilder();
    private JSeparator sep;
    private ImageIcon messageImage;
    int count = 0;
    int variable = 1;

    public ClientGUI(Client client) {
        this.client = client;
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JPanel = new JPanel();
        onlineSPane = new JScrollPane();
        onlineTextPane = new JTextPane();
        onlineLabel = new JLabel();
        discButton = new JButton();
        refreshButton = new JButton();
        chattSPane = new JScrollPane();
        chatTextArea = new JTextArea();
        chattLabel = new JLabel();
        messageTextField = new JTextField();
        fileButton = new JButton();
        messageButton = new JButton();
        contactsSPane = new JScrollPane();
        savedKontakterLista = new JList<>();
        contactsLabel = new JLabel();
        addContactButton = new JButton();
        removeContactButton = new JButton();
        pepLabel = new JLabel();
        sendToButton = new JButton();
        peopleLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        onlineUserList = new JList<>();
        lblSendTo = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");

        onlineTextPane.setEditable(false);
        onlineTextPane.setLayout(new BoxLayout(onlineTextPane, BoxLayout.PAGE_AXIS));
        onlineSPane.setViewportView(onlineTextPane);

        onlineLabel.setText("Online Clients:");

        discButton.setText("Disconnect");
        discButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh list");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        chattSPane.setViewportView(chatTextArea);

        chattLabel.setText("Chat:");

        fileButton.setText("Send Image");
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        messageButton.setText("Send Message");
        messageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageButtonActionPerformed(evt);
            }
        });

        savedKontakterLista.setToolTipText("");
        contactsSPane.setViewportView(savedKontakterLista);

        contactsLabel.setText("Contacts:");

        addContactButton.setText("Add to Contact List");
        addContactButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addContactButtonActionPerformed(evt);
            }
        });

        removeContactButton.setText("Remove from Contact List");
        removeContactButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeContactButtonActionPerformed(evt);
            }
        });

        pepLabel.setIcon(new ImageIcon("src/files/beniceman.png"));

        sendToButton.setText("Select User ");
        sendToButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendToButtonActionPerformed(evt);
            }
        });

        peopleLabel.setText("People you can add:");

        jScrollPane1.setViewportView(onlineUserList);

        lblSendTo.setText("Sending To: Everyone");

        GroupLayout JPanelLayout = new GroupLayout(JPanel);
        JPanel.setLayout(JPanelLayout);
        JPanelLayout.setHorizontalGroup(
                JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(JPanelLayout.createSequentialGroup()
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(refreshButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(onlineSPane, GroupLayout.Alignment.LEADING)
                                        .addComponent(onlineLabel, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(discButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGroup(JPanelLayout.createSequentialGroup()
                                                        .addComponent(chattLabel, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                                                        .addComponent(lblSendTo, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                                                        .addGap(72, 72, 72))
                                                .addComponent(messageTextField)
                                                .addComponent(chattSPane))
                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                .addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(messageButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sendToButton, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(contactsSPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                        .addComponent(addContactButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                                                .addComponent(contactsLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                        .addComponent(removeContactButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                                .addGap(6, 6, 6))
                                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                                .addComponent(peopleLabel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(GroupLayout.Alignment.TRAILING, JPanelLayout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(pepLabel, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                                                .addGap(32, 32, 32))))
        );
        JPanelLayout.setVerticalGroup(
                JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, JPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(chattLabel)
                                        .addComponent(onlineLabel)
                                        .addComponent(contactsLabel)
                                        .addComponent(lblSendTo))
                                .addGap(3, 3, 3)
                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(chattSPane, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                                                        .addComponent(onlineSPane))
                                                .addGap(6, 6, 6)
                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(discButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(messageTextField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addGroup(JPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(messageButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(sendToButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(refreshButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(JPanelLayout.createSequentialGroup()
                                                .addComponent(contactsSPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(peopleLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addContactButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(removeContactButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(pepLabel)))
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
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
        addListListener();
        pack();
    }

    public void setOnlineUserList(String[] userName) {
        onlineUserList.setListData(userName);
    }
    public void setSavedContactList(String[] username) {
        savedKontakterLista.setListData(username);
    }
    public int getListIndex() {
        return onlineUserList.getSelectedIndex();
    }
    public int getContactListIndex() {
        return savedKontakterLista.getSelectedIndex();
    }

    public void resetGUI() {
        for(JLabel lbl : lblUsers) {
            onlineTextPane.remove(lbl);
        }
        repaint();
    }

    public JTextArea getTaChatbox() {
        return chatTextArea;
    }

    public JTextField getTfMessage() {
        return messageTextField;
    }

    public ImageIcon getMessageImage() {
        return messageImage;
    }

    public JLabel getLblSendTo() {
        return lblSendTo;
    }

    public void resetLabel() {
        sendTo.setLength(0);
        count = 0;
    }

    private void addListListener() {
        onlineUserList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = onlineUserList.getSelectedIndex();
                if (!e.getValueIsAdjusting() && index>-1) {
                    client.changeIndication(index);
                }
            }
        });
    }

    public void setTextForLabel(String text) {
        if(count == 0) {
            sendTo.append(text);
        } else {
            sendTo.append(", " + text);
        }
        lblSendTo.setText("Sending To: " + sendTo);
        count++;
    }

    public void displayUser(JLabel user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                lblUsers.add(user);
                onlineTextPane.add(user);
                revalidate();
            }
        });
    }




    public void displayImage(String sender, String text, ImageIcon image) {
        Image scaled = ImageGUI.scaleImage(image.getImage(), 400,400);
        ImageIcon scaledImage = new ImageIcon(scaled);
        ImageGUI.displayImage(sender, text, scaledImage);
        revalidate();
    }

    public void removeUser(JLabel user) {
        for(JLabel lbl : lblUsers) {
            if(lbl.getText().equals(user.getText())) {
                onlineTextPane.remove(lbl);
                lblUsers.remove(user);
                repaint();
            }
        }
    }


    private void discButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (client.getRunning()){
            client.button(Buttons.Disconnect);
            addContactButton.setEnabled(false);
            fileButton.setEnabled(false);
            refreshButton.setEnabled(false);
            messageButton.setEnabled(false);
            removeContactButton.setEnabled(false);
            sendToButton.setEnabled(false);
            messageTextField.setEnabled(false);
            discButton.setText("Connect");
            repaint();
        } else{
            client.button(Buttons.Connect);
            addContactButton.setEnabled(true);
            fileButton.setEnabled(true);
            refreshButton.setEnabled(true);
            messageButton.setEnabled(true);
            removeContactButton.setEnabled(true);
            sendToButton.setEnabled(true);
            messageTextField.setEnabled(true);
            discButton.setText("Disconnect");
            client.button(Buttons.Contacts);
            repaint();
        }

    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        client.button(Buttons.Contacts);
    }

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        int response = jfc.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION) {
            String pictureFile = jfc.getSelectedFile().toString();
            if(ImageGUI.isImage(jfc.getSelectedFile().toString()) == false) {
                JOptionPane.showMessageDialog(null, "Fel filformat");
            } else {
                messageImage = new ImageIcon(pictureFile);
            }
        }
    }

    private void messageButtonActionPerformed(java.awt.event.ActionEvent evt) {
        client.button(Buttons.Send);
        messageTextField.setText("");
        messageImage = null;
    }

    private void addContactButtonActionPerformed(java.awt.event.ActionEvent evt) {
        client.button(Buttons.AddContact);
    }

    private void removeContactButtonActionPerformed(java.awt.event.ActionEvent evt) {
        client.button(Buttons.ContactsRemove);
    }

    private void sendToButtonActionPerformed(java.awt.event.ActionEvent evt) {
        client.button(Buttons.SendContacts);
    }


}
