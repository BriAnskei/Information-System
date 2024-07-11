package main.java.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.java.Callback.MemberUpdateCallback;
import main.java.model.Member;
import main.java.service.MemberService;
import main.java.util.ImageUtil;

public class EditMember extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6552364654195081155L;
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
	
	private File oldImageFile;
	private File newImageFile;
	
	private MemberUpdateCallback callback;
	private int memberId;


	public EditMember(int memberId, MemberUpdateCallback callback) throws FileNotFoundException, IOException  {
		this.callback = callback;
		this.memberId = memberId;
		
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
		
		ImageIcon image = new ImageIcon("src/main/java/img/pngegg.png");
		
		imgLogo = new JLabel(image);
		imgLogo.setBounds(124, 0, 60, 50);
		panel.add(imgLogo);
		
		lblNewLabel = new JLabel("Edit Member");
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
		
		JButton RegisterButton = new JButton("Save");
		RegisterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String firstName = userFirstN.getText();
	             String lastName = userLastN.getText();
	             String address = userAddress.getText();
	             String phoneNumber = userNumber.getText();
	             String email = userEmail.getText();
				
					
						try {
							editMember(firstName, lastName, address, phoneNumber, email, memberId);
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
		
		loadMemberDetails();
	}
	
	
	MemberService service = new MemberService();
	ImageUtil imgUtil = new ImageUtil();
	
	private void loadMemberDetails() throws FileNotFoundException, IOException  {
		List<Member> memberDetails;
		 try {
			memberDetails = service.searchMember(Integer.toString(memberId));
			for(Member member: memberDetails) {
				  userFirstN.setText(member.getFirstName());
		            userLastN.setText(member.getLastName());
		            userAddress.setText(member.getAddress());
		            userNumber.setText(member.getPhoneNumber());
		            userEmail.setText(member.getEmail());
		            
		            oldImageFile = imgUtil.convertByteArrayToFile(member.getImageData());
	}
		 } catch (SQLException e) {
	            e.printStackTrace();
	     }
	}
	
	private void editMember(String firstName, String lastName, String address, String phoneNumber, String email, int memberId) throws IOException, SQLException  {
		if(hasMissingInput(firstName, lastName, address, phoneNumber, email)) { 
			showErrorMessage("showErrorMessage"); return;
		}else {
			Member member = new Member();
			  member.setFirstName(firstName);
			  member.setLastName(lastName);
			  member.setAddress(address);
			  member.setPhoneNumber(phoneNumber);
			  member.setEmail(email);
			  if(newImageFile != null) {
				  member.setImageData(imgUtil.convertFileToBytes(newImageFile));
			  }else {
				  member.setImageData(imgUtil.convertFileToBytes(oldImageFile));
			  }
			  
			  service.editMember(member, memberId);
			  if(callback != null) {
					callback.onMemberListUpdated();
			}
			  JOptionPane.showMessageDialog(null, "Member Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
		}
	}
		
	
	
	public  boolean hasMissingInput(String firstName, String lastName, String address, 
            String phoneNumber, String email) {
			// Check if any of the String inputs are null or empty
			return  (firstName == null || firstName.isEmpty()) ||
					(lastName == null || lastName.isEmpty()) ||
					(address == null || address.isEmpty()) ||
					(phoneNumber == null || phoneNumber.isEmpty()) ||
					(email == null || email.isEmpty());
			}
	
	public  void showErrorMessage(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		}


	
	private void imgUploadFromFile() {
		newImageFile = imgUtil.imageUploadFromFile();
		// Checks if the file is not null before setting it in label Image
		if(newImageFile != null) {
			imgNameLabel.setText(newImageFile.getName());
		}
		
	}
	
}
