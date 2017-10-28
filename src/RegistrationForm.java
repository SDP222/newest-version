// Registration Form
 
import database.JDBC;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegistrationForm extends JFrame implements ActionListener {
    
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JTextField userTextField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton; 
    private ImagePanel panel;
    
    /**
     * Setup GUI
     */
    public RegistrationForm()
    {
        this.setTitle("Player Registration");
        this.setSize(new Dimension(512, 512));
        this.setPreferredSize(new Dimension(512, 512));
        this.setLocationByPlatform(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.BLACK);

        this.panel = new ImagePanel("background.jpg");
        this.panel.setPreferredSize(new Dimension(512, 512));
        this.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.add(this.panel);

        JLabel label = new JLabel("");
        label.setPreferredSize(new Dimension(500, 150));
        panel.add(label);

        
        //add labels and inputs
        this.userLabel = new JLabel("User Name: ");
        this.userLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.userLabel.setForeground(Color.white);
        this.userLabel.setBackground(Color.black);
        this.userLabel.setPreferredSize(new Dimension(150, 30));
        panel.add(userLabel);
        
        this.userTextField = new JTextField();
        this.userTextField.setPreferredSize(new Dimension(200, 30));
        this.panel.add(this.userTextField);
        
        this.passwordLabel = new JLabel("Password: ");
        this.passwordLabel.setForeground(Color.white);
        this.passwordLabel.setBackground(Color.black);
        this.passwordLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.passwordLabel.setPreferredSize(new Dimension(150, 30));
        panel.add(passwordLabel);
        
        this.passwordField = new JPasswordField();
        this.passwordField.setPreferredSize(new Dimension(200, 30));
        this.panel.add(this.passwordField);
        
        this.firstNameLabel = new JLabel("First Name: ");
        this.firstNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.firstNameLabel.setForeground(Color.white);
        this.firstNameLabel.setBackground(Color.black);
        this.firstNameLabel.setPreferredSize(new Dimension(150, 30));
        panel.add(firstNameLabel);
        
        this.firstNameField = new JTextField();
        this.firstNameField.setPreferredSize(new Dimension(200, 30));
        this.panel.add(this.firstNameField);
        
        this.lastNameLabel = new JLabel("Last Name: ");
        this.lastNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.lastNameLabel.setForeground(Color.white);
        this.lastNameLabel.setBackground(Color.black);
        this.lastNameLabel.setPreferredSize(new Dimension(150, 30));
        panel.add(lastNameLabel);
        
        this.lastNameField = new JTextField();
        this.lastNameField.setPreferredSize(new Dimension(200, 30));
        this.panel.add(this.lastNameField);

        this.registerButton = new JButton("Register");
        this.registerButton.setPreferredSize(new Dimension(150, 30));
        this.registerButton.setBackground(Color.black);
        this.registerButton.setForeground(Color.white);
        this.registerButton.addActionListener(this);
        panel.add(this.registerButton);
       
        //Add buttons
        this.loginButton = new JButton("Back to Login");
        this.loginButton.setPreferredSize(new Dimension(150, 30));
        this.loginButton.setBackground(Color.black);
        this.loginButton.setForeground(Color.white);
        this.loginButton.addActionListener(this);
        panel.add(this.loginButton);
        
        this.pack();
    }
    
    /**
     * Check user login
     * 
     * @return 
     */
    public void register()
    {
        String user = this.userTextField.getText();
        String pass = new String(this.passwordField.getPassword());
        String fn = this.firstNameField.getText();
        String ln = this.lastNameField.getText();
        
        JDBC.insertUser(user, pass, fn, ln);
        JDBC.insertNewPlayer(user);
    }
    
    /**
     * Event handler to manage the event triggered
     * 
     * @param event 
     */
    public void actionPerformed(ActionEvent event) {
        
        Object src = event.getSource();
        
        if(src.equals(registerButton))
        {
            register();
            JOptionPane.showMessageDialog(this, "You are registered now! click Login button");
        }
        else if(src.equals(loginButton))
        {
            new PlayForm();
            this.dispose();
        }
    }
    
}
