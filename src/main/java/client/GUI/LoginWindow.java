package client.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import client.Client;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow {

	public JFrame frame;
	private JTextField userNameTextField;
	private JTextField passwordTextField;
	private JButton btnCreateAccount;
	private JLabel warningLbl;

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(156, 57, 117, 26);
		frame.getContentPane().add(userNameTextField);
		userNameTextField.setColumns(10);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(156, 145, 117, 26);
		frame.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(187, 24, 79, 16);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(187, 117, 79, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.instance.logInRequest(userNameTextField.getText().toString(), passwordTextField.getText().toString());
			}
		});
		btnLogIn.setBounds(156, 208, 117, 29);
		frame.getContentPane().add(btnLogIn);
		
		
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(0, 19, 117, 29);
		frame.getContentPane().add(btnCreateAccount);
		
		warningLbl = new JLabel("");
		warningLbl.setBounds(93, 180, 331, 16);
		frame.getContentPane().add(warningLbl);
	}
	
	public void setWarningLable(String message) {
		warningLbl.setText(message);
	}
}
