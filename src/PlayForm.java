//Provides Login Functionality
 
import database.Database;
import database.JDBC;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayForm extends JFrame implements ActionListener
{
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton; 
    private ImagePanel panel;
    
    /**
     * Setup GUI
     */
    public PlayForm()
    {
        this.setTitle(" Player Login");
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
        
        //Add buttons
        this.loginButton = new JButton("Login");
        this.loginButton.setPreferredSize(new Dimension(150, 30));
        this.loginButton.setBackground(Color.black);
        this.loginButton.setForeground(Color.white);
        this.loginButton.addActionListener(this);
        panel.add(this.loginButton);

        this.registerButton = new JButton("Register");
        this.registerButton.setPreferredSize(new Dimension(150, 30));
        this.registerButton.setBackground(Color.black);
        this.registerButton.setForeground(Color.white);
        this.registerButton.addActionListener(this);
        panel.add(this.registerButton);
    }
    
    /**
     * Check user login
     * 
     * @return 
     */
    public boolean login()
    {
        String user = this.userTextField.getText();
        String pass = this.passwordField.getText();
        
        return JDBC.checkLogin(user, pass);
        
    }
    
    /**
     * Event handler to manage the event triggered
     * 
     * @param event 
     */
    public void actionPerformed(ActionEvent event) {
        
        Object src = event.getSource();
        
        if(src.equals(loginButton))
        {
            if(login())
            {
                Database.setPlayer(this.userTextField.getText());
                GameGUI.createAndShowGui();
                GameGUI.setUserID(this.userTextField.getText());
                this.dispose();
            }
            else 
            {
                JOptionPane.showMessageDialog(this, "Login Failed - try again");
            }
        }
        else if(src.equals(registerButton))
        {
            new RegistrationForm();
            this.dispose();
        }
    }
}
