package main.java.gui;


import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.java.model.User;
import main.java.service.UserService;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userInput;
	private JPasswordField passInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 409, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 80, 68, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(10, 163, 68, 20);
		contentPane.add(lblPassword);
		
		JButton RegisterButton = new JButton("LOGIN");
		RegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loginUser();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		RegisterButton.setBounds(10, 257, 89, 23);
		contentPane.add(RegisterButton);
		
		userInput = new JTextField();
		userInput.setColumns(10);
		userInput.setBounds(10, 107, 369, 30);
		contentPane.add(userInput);
		
		 passInput = new JPasswordField();
        passInput.setColumns(10);
        passInput.setBounds(10, 194, 369, 30);
        contentPane.add(passInput);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 393, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		ImageIcon image = new ImageIcon("src/main/java/img/um-seal.png"); // Replace with actual path

		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(0, 0, 60, 50);
		panel.add(imgLogo);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		lblLogin.setBounds(70, 0, 79, 50);
		panel.add(lblLogin);
		
		JButton btnDontHaveAn = new JButton("Don't have an account? Sign up here....");
		btnDontHaveAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userSignUp();
			}
		});
		btnDontHaveAn.setFocusPainted(false);
		btnDontHaveAn.setContentAreaFilled(false);
		btnDontHaveAn.setBorderPainted(false);
		btnDontHaveAn.setBorder(BorderFactory.createEmptyBorder());
		btnDontHaveAn.setForeground(new Color(0, 0, 255));
		btnDontHaveAn.setBounds(155, 257, 238, 23);
		contentPane.add(btnDontHaveAn);;	
	}
	
	UserService service = new UserService();
		
	private void loginUser() throws SQLException, IOException {
		String user = userInput.getText();
		String pass = passInput.getText();
	// checks if user name exist and validate password input
		if (service.usernameValidation(user)) {
	        if (!service.loginValidate(user, pass)) {
	            showErrorMessage("Wrong password!");
	        } else {
	        	openMain_user(user, pass);
	            dispose();
	        }
	    } else {
	    	if(isUserAdmin(user, pass)) {
	    		openMain_admin();
	    		dispose();
			}else 
	            showErrorMessage("Login failed. Please ensure your username and password are correct.");
	        }
	    }
	
	private void openMain_user(String username, String password) throws SQLException, IOException {
		LibraryManagementSystem_user main = new LibraryManagementSystem_user(service.getUserInfo(username, password));
		main.setVisible(true);
	}
	
	private void openMain_admin() throws SQLException, IOException {
		LibraryManagementSystem main = new LibraryManagementSystem();
		main.setVisible(true);
	}
	
	
	private boolean isUserAdmin(String username, String password) {
		return username.equals("admin") && password.equals("admin");
	}
	
	private void userSignUp() {
		SignUpForm signUp = new SignUpForm();
		signUp.setVisible(true);
	}
	
	public static void showErrorMessage(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
