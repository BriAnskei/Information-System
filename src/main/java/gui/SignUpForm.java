package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.dao.UserDAO;
import main.java.model.User;
import main.java.service.UserService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userNameInput;
	private JTextField passwordInput;
	private JTextField emailInput;

	public SignUpForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 409, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(10, 67, 52, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 393, 50);
		contentPane.add(panel);
		
		ImageIcon image = new ImageIcon("src/main/java/img/um-seal.png"); // Replace with actual path
		
		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(0, 0, 60, 50);
		panel.add(imgLogo);
		
		JLabel lblLogin = new JLabel("Register");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		lblLogin.setBounds(70, 0, 127, 50);
		panel.add(lblLogin);
		
		userNameInput = new JTextField();
		userNameInput.setColumns(10);
		userNameInput.setBounds(10, 92, 369, 30);
		contentPane.add(userNameInput);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 133, 52, 14);
		contentPane.add(lblNewLabel_1);
		
		passwordInput = new JTextField();
		passwordInput.setColumns(10);
		passwordInput.setBounds(10, 157, 369, 30);
		contentPane.add(passwordInput);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(10, 198, 52, 14);
		contentPane.add(lblNewLabel_1_1);
		
		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(10, 223, 369, 30);
		contentPane.add(emailInput);
		
		JButton btnSignup = new JButton("Sign Up");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUpValidation();
			}
		});
		btnSignup.setBounds(10, 279, 89, 23);
		contentPane.add(btnSignup);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInout();
			}
		});
		btnClear.setBounds(294, 279, 89, 23);
		contentPane.add(btnClear);
	}
	
	UserService service = new UserService();
	
	
	private void signUpValidation() {
		String username = userNameInput.getText();
		String password = passwordInput.getText();
		String email = emailInput.getText();
		
		if(isInputFormComplete(username, password, email)) {
			 showErrorMessage("Please complete form");
			 return;
		}
	
		if(!inputVallidation(email, username)) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			
			 if (service.register(user)) {
		            showSuccessMessage("Sign Up successful");
		            dispose();
		     } else {
		            showErrorMessage("Sign Up failed. Please try again.");
		     }
		}
	}
	
	private boolean isInputFormComplete(String user, String pass,  String email) {
		return user.equals("") || pass.equals("") || email.equals("");
	}
	
	
	private boolean inputVallidation(String email, String username) {
		UserDAO userDAO = new UserDAO();
		if(userDAO.checkUsernameExists(username)) {
			showErrorMessage("Username  already exist");
			return true;
		}
		if(userDAO.checkEmailExists(email)) {
			showErrorMessage("Email  already exist");
			return true;
		}
		return false;
	}
	
	private void clearInout() {
		userNameInput.setText("");
		passwordInput.setText("");
		emailInput.setText("");
		;
	}
	
	public static void showErrorMessage(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		}

	private void showSuccessMessage(String message) {
		  JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
		}

}
