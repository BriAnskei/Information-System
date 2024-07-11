package main.java.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.model.Member;
import main.java.service.MemberService;
import main.java.util.ImageUtil;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// This card is used for issuing book.
public class MemberCard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String memberId;
	
	private JLabel memberName;
	private JLabel addressLabel;
	private JLabel phoneNumberLabel;
	private JLabel emailLabel;
	private JLabel imgLabel;


	public MemberCard(String memberId) {
		this.memberId = memberId;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		imgLabel = new JLabel("Image will appear here");
		imgLabel.setBounds(10, 11, 150, 134);
		contentPane.add(imgLabel);
		
		JLabel lbl = new JLabel("Name");
		lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl.setBounds(184, 11, 46, 14);
		contentPane.add(lbl);
		
		memberName = new JLabel("New label");
		memberName.setBounds(184, 36, 255, 14);
		contentPane.add(memberName);
		
		JLabel lblNewLabel = new JLabel("Address");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(184, 61, 46, 14);
		contentPane.add(lblNewLabel);
		
		addressLabel = new JLabel("New label");
		addressLabel.setBounds(184, 86, 218, 14);
		contentPane.add(addressLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Phone Number");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(184, 111, 96, 14);
		contentPane.add(lblNewLabel_1);
		
		phoneNumberLabel = new JLabel("New label");
		phoneNumberLabel.setBounds(184, 136, 150, 14);
		contentPane.add(phoneNumberLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(184, 161, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		emailLabel = new JLabel("New label");
		emailLabel.setBounds(184, 186, 150, 14);
		contentPane.add(emailLabel);
		
		fillDetails(memberId);
	}
	
	MemberService service = new MemberService();
	
	private void fillDetails(String memberId) {
		 List<Member> searchedMembers;
	        try {
	            searchedMembers = service.searchMember(memberId);
	            for (Member member : searchedMembers) {
	                try {
	                	 // Convert ByteArray into buffedImg and store in IageIcon with the current size of the ImageLabel
	                    BufferedImage buffedImg = ImageUtil.convertByteArrayToBufferedImage(member.getImageData());
	                    ImageIcon icon = new ImageIcon(ImageUtil.resizeImage(buffedImg, imgLabel.getWidth(), imgLabel.getHeight()));

	                    memberName.setText(member.getFirstName() + " " +member.getLastName());
	                    addressLabel.setText(member.getAddress());
	                    phoneNumberLabel.setText(member.getPhoneNumber());
	                    emailLabel.setText(member.getEmail());
	                    imgLabel.setIcon(icon);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
}
