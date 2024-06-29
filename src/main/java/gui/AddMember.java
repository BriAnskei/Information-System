package main.java.gui;

import main.java.service.MemberService;
import main.java.util.ImageUtil;
import main.java.Callback.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;

import java.awt.Font;


public class AddMember extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userFirstN;
	private JTextField userLastN;
	private JTextField userAddress;
	private JLabel firstName_3;
	private JTextField userNumber;
	private JLabel email;
	private JTextField userEmail;
	private JLabel imgNameLabel;
	
	private JPanel panel;
	private JLabel imgLogo;
	private JLabel lblNewLabel;
	
	private File selectedFile;
	
	private MemberUpdateCallback callback;

 

	public AddMember(MemberUpdateCallback callback) {
		this.callback = callback;
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 433, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userFirstN = new JTextField();
		userFirstN.setBounds(22, 87, 369, 30);
		contentPane.add(userFirstN);
		userFirstN.setColumns(10);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 166, 83));
		panel.setBounds(0, 0, 425, 51);
		contentPane.add(panel);
		
		ImageIcon image = new ImageIcon("src/main/java/img/MemberLogo.png");
		
		imgLogo = new JLabel(image);
		imgLogo.setBounds(113, 0, 60, 50);
		panel.add(imgLogo);
		
		lblNewLabel = new JLabel("Add Member");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		lblNewLabel.setBounds(183, 0, 171, 50);
		panel.add(lblNewLabel);
		
		JLabel firstName = new JLabel("First Name");
		firstName.setBounds(22, 62, 73, 20);
		contentPane.add(firstName);
		
		JLabel lastName = new JLabel("Last Name");
		lastName.setBounds(22, 128, 73, 14);
		contentPane.add(lastName);
		
		userLastN = new JTextField();
		userLastN.setColumns(10);
		userLastN.setBounds(22, 153, 369, 30);
		contentPane.add(userLastN);
		
		JLabel address = new JLabel("Address");
		address.setBounds(22, 194, 59, 14);
		contentPane.add(address);
		
		userAddress = new JTextField();
		userAddress.setColumns(10);
		userAddress.setBounds(22, 219, 369, 30);
		contentPane.add(userAddress);
		
		firstName_3 = new JLabel("Phone Number");
		firstName_3.setBounds(22, 265, 95, 14);
		contentPane.add(firstName_3);
		
		userNumber = new JTextField();
		userNumber.setColumns(10);
		userNumber.setBounds(22, 291, 369, 30);
		contentPane.add(userNumber);
		
		email = new JLabel("Email");
		email.setBounds(22, 332, 59, 14);
		contentPane.add(email);
		
		userEmail = new JTextField();
		userEmail.setColumns(10);
		userEmail.setBounds(22, 357, 369, 30);
		contentPane.add(userEmail);
		
		JButton RegisterButton = new JButton("Register");
		RegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String firstName = userFirstN.getText();
	             String lastName = userLastN.getText();
	             String address = userAddress.getText();
	             String phoneNumber = userNumber.getText();
	             String email = userEmail.getText();
	             if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
	            	  JOptionPane.showMessageDialog(null, "Complete input", "Error", JOptionPane.ERROR_MESSAGE);
	            	  return;
	             }
	             try {
	            	 addNewMember(firstName, lastName, address, phoneNumber, email);
				} catch (IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		RegisterButton.setBounds(22, 397, 89, 23);
		contentPane.add(RegisterButton);
		
		JButton imgUpload = new JButton("Browse");
		imgUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imgUploadFromFile();
			}
		});
		imgUpload.setBounds(302, 398, 89, 23);
		contentPane.add(imgUpload);
		
		imgNameLabel = new JLabel("Upload image.....");
		imgNameLabel.setBounds(210, 399, 89, 19);
		contentPane.add(imgNameLabel);
		
		
	}
	
	
	MemberService service = new MemberService();
	ImageUtil imgUtil = new ImageUtil();
	
	private void addNewMember(String firstName, String lastName, String address, String phoneNumber, String email) throws FileNotFoundException, IOException, SQLException {
		service.addMember(firstName, lastName, address, phoneNumber, email, selectedFile);
		// notify other parts of the application that the member list has been updated.
		if(callback != null) {
			callback.onMemberListUpdated();
		}
		 JOptionPane.showMessageDialog(null, "Member Added", "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void imgUploadFromFile() {
		selectedFile = imgUtil.imageUploadFromFile();
		// Checks if the file is not null before setting it in label Image
		if(selectedFile != null) {
			imgNameLabel.setText(selectedFile.getName());
		}
		
	}
}




