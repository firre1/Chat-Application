import javax.swing.*;

public class DataLoggerGUI extends JFrame {

    // Variables declaration - do not modify
    private JLabel JLabel1;
    private JScrollPane JScrollPane;
    private JPanel LoggerPanel;
    private JLabel fromLabel;
    private JTextField fromTextField;
    private JList<String> logList;
    private JLabel mauLabel;
    private JButton refreshButton;
    private JLabel toLabel;
    private JTextField toTextField;
    private DataLogger dataLoggerRef;
    // End of variables declaration

    public DataLoggerGUI(DataLogger dataLoggerRef) {
        this.dataLoggerRef = dataLoggerRef;
        initComponents();
        setVisible(true);
        UpdateList();
    }

    private void initComponents() {

        LoggerPanel = new JPanel();
        JScrollPane = new JScrollPane();
        logList = new JList<>();
        JLabel1 = new JLabel();
        refreshButton = new JButton();
        mauLabel = new JLabel();
        fromLabel = new JLabel();
        fromTextField = new JTextField(dataLoggerRef.DateTransaction().toString());
        toLabel = new JLabel();
        toTextField = new JTextField(dataLoggerRef.fileLastContent().toString());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logger");

        JScrollPane.setViewportView(logList);

        JLabel1.setText("Logged data:");

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        mauLabel.setIcon(new ImageIcon("src/files/mau.png")); // NOI18N
        mauLabel.setToolTipText("");

        fromLabel.setText("From:");

        toLabel.setText("To:");
        toLabel.setToolTipText("");

        GroupLayout LoggerPanelLayout = new GroupLayout(LoggerPanel);
        LoggerPanel.setLayout(LoggerPanelLayout);
        LoggerPanelLayout.setHorizontalGroup(
                LoggerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(JScrollPane)
                        .addGroup(LoggerPanelLayout.createSequentialGroup()
                                .addComponent(JLabel1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mauLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fromLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fromTextField, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toTextField, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        LoggerPanelLayout.setVerticalGroup(
                LoggerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, LoggerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(LoggerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(JLabel1)
                                        .addComponent(refreshButton)
                                        .addComponent(mauLabel)
                                        .addComponent(fromLabel)
                                        .addComponent(fromTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(toLabel)
                                        .addComponent(toTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JScrollPane, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LoggerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(LoggerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    public void UpdateList()
    {
        logList.setListData( dataLoggerRef.Content(fromTextField.getText(), toTextField.getText()).toArray( new String[0] ) );
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        UpdateList();
    }

}
