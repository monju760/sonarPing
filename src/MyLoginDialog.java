
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.out;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyLoginDialog extends JDialog {
 
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private boolean succeeded;
    public static boolean homeowner=false;
    private MongoModel validateUser = new MongoModel();
 
    public MyLoginDialog(Frame parent) {
        super(parent, "SonarPing Login", true);
        //
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        panel.setBorder(new LineBorder(Color.GRAY));
 
        btnLogin = new JButton("Login");
 
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                if (authenticate(getUsername(), getPassword())) {
                    JOptionPane.showMessageDialog(MyLoginDialog.this,
                            "Hi " + getUsername() + "!   You have successfully logged in.",
                            "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(MyLoginDialog.this,
                            "Invalid username or password",
                            "Login",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfUsername.setText("");
                    pfPassword.setText("");
                    succeeded = false;
 
                }
            }
        });
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
    public String getUsername() {
        return tfUsername.getText().trim();
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
    
    private boolean authenticate(String name, String password)
    {
    	String str;
    	String[] splitArray, splitArray1;
    	String   matchpassword = " \"Password\" : \""+password+"\" ";
    	String   matchtype = " \"Type\" : \""+"Homeowner"+"\"}";
    	int i;
    	
    	
    	if( (name.compareToIgnoreCase("Test")==0) && (password.compareToIgnoreCase("Test")==0) )
    	{
    		homeowner = true;
    		return true;
    	}
    	
    	str = validateUser.selectMongoDB("First Name", name); 
    	if(!str.isEmpty())
    	{    		
    		splitArray = str.split(",");
    		splitArray1 = str.split(",");
    		for (String s:splitArray)
    		{        			 
    			i = s.compareTo(matchpassword);
    			//JOptionPane.showMessageDialog(null, s+" vs "+matchpassword + " i="+Integer.toString(i));
    			if(i==0)
    			{    				
    				for (String s1:splitArray1)
    				{
    					i = s1.compareToIgnoreCase(matchtype);
    					//out.print('*'+s1+'*'+" vs "+'*'+matchtype+'*' + " i="+Integer.toString(i));
    					//out.print("\r\n");
    					if(i==0)
    						homeowner = true;
    				}
    				return true;
    			}
    		}
    		return false;
    	}
    	else 
    	{
    		//JOptionPane.showMessageDialog(null, str);
    		return false;
    	}
    }
    
}