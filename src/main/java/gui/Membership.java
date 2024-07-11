package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.Callback.MemberUpdateCallback;
import main.java.model.Member;
import main.java.model.User;
import main.java.service.*;
import main.java.util.ImageUtil;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;

public class Membership extends JFrame implements MemberUpdateCallback{

	private static final long serialVersionUID = 1L;
	private MemberUpdateCallback callback;
	private User user;
	private JPanel contentPane;
	
	private JLabel imgLabel;
	private JLabel memberIDLabel;
	private JLabel nameLabel;
	private JLabel emailLabel;
	private JLabel addressLabel;
	private JLabel phoneNumber;

	
	public Membership(MemberUpdateCallback callback, User user) throws IOException {
		this.callback = callback;
		this.user = user;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 433, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(234, 117, 0));
		panel.setBounds(0, 0, 417, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel imgLogo = new JLabel((Icon) null);
		imgLogo.setBounds(72, 0, 60, 50);
		panel.add(imgLogo);
		
		JLabel lblMembership = new JLabel("Membership");
		lblMembership.setForeground(Color.WHITE);
		lblMembership.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 45));
		lblMembership.setBounds(142, 0, 224, 50);
		panel.add(lblMembership);
		
		imgLabel = new JLabel("img will appear here");
		imgLabel.setBounds(10, 61, 185, 185);
		contentPane.add(imgLabel);
		
		JLabel lblNewLabel = new JLabel("Member ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(205, 72, 69, 14);
		contentPane.add(lblNewLabel);
		
		memberIDLabel = new JLabel("New label");
		memberIDLabel.setBounds(297, 72, 85, 14);
		contentPane.add(memberIDLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(205, 97, 101, 14);
		contentPane.add(lblNewLabel_1_1);
			
		nameLabel = new JLabel("New label");
		nameLabel.setBounds(205, 122, 193, 14);
		contentPane.add(nameLabel);
		
		JLabel lblNewLabel_1_3 = new JLabel("Email:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_3.setBounds(205, 146, 101, 14);
		contentPane.add(lblNewLabel_1_3);
		
		emailLabel = new JLabel("New label");
		emailLabel.setBounds(205, 171, 193, 14);
		contentPane.add(emailLabel);
		
		JLabel lblNewLabel_1_5 = new JLabel("Address:");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_5.setBounds(205, 196, 101, 14);
		contentPane.add(lblNewLabel_1_5);
		
		addressLabel = new JLabel("New label");
		addressLabel.setBounds(205, 221, 193, 14);
		contentPane.add(addressLabel);
		
		phoneNumber = new JLabel("New label");
		phoneNumber.setBounds(205, 271, 203, 14);
		contentPane.add(phoneNumber);
		
		JButton Edit = new JButton("Edit");
		Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					editMembership();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Edit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		Edit.setBackground(Color.LIGHT_GRAY);
		Edit.setBounds(10, 306, 110, 30);
		contentPane.add(Edit);
		
		JButton deletebtn = new JButton("Close Account ");
		deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMember();
			}
		});
		deletebtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		deletebtn.setBackground(Color.LIGHT_GRAY);
		deletebtn.setBounds(269, 306, 138, 30);
		contentPane.add(deletebtn);
		
		JLabel lblNewLabel_1_5_1 = new JLabel("Phone Number:");
		lblNewLabel_1_5_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_5_1.setBounds(205, 246, 101, 14);
		contentPane.add(lblNewLabel_1_5_1);
		
		diplayMemberDetails();
	}

	private void diplayMemberDetails() throws IOException  {
		MemberService service = new MemberService();	
		Member member = service.getMemberdetails(user.getUserId());
		
		BufferedImage buffImg = ImageUtil.convertByteArrayToBufferedImage(member.getImageData());
		ImageIcon imgIcon = new ImageIcon(ImageUtil.resizeImage(buffImg, imgLabel.getWidth(), imgLabel.getHeight()));
		
		memberIDLabel.setText(Integer.toString(member.getMemberId()));
		nameLabel.setText(member.getFirstName() + " " + member.getLastName());
		emailLabel.setText(member.getEmail());
		addressLabel.setText(member.getAddress());
		phoneNumber.setText(member.getPhoneNumber());
		imgLabel.setIcon(imgIcon);
	}
	
	MemberService memberService = new MemberService();
	LoanService loanService = new LoanService();
	
	private void deleteMember() {
	    String memberIdText = memberIDLabel.getText();
	    int memberId = Integer.parseInt(memberIdText);

	    if (loanService.memberIssueCheck(memberId)) {
	        showError("<html><b>Action Blocked:</b> Outstanding Loans</html>");
	        return;
	    }
	    
	    if (showConfirmationDialog("Delete Membership?")) {
	        try {
	            memberService.Delete(memberId);
	            JOptionPane.showMessageDialog(
	                null, 
	                "Member Deleted", 
	                "Success", 
	                JOptionPane.INFORMATION_MESSAGE
	            );
	            memberUpdate();
	            dispose();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	private  boolean showConfirmationDialog(String message) {
		  int option = JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.YES_NO_OPTION);
		  return option == JOptionPane.YES_OPTION;
		}

	
	private void editMembership() throws FileNotFoundException, IOException {
		 String memberIdText = memberIDLabel.getText();
		 int memberId = Integer.parseInt(memberIdText);
		  if (loanService.memberIssueCheck(memberId)) {
			   showError("<html><b>Action Blocked:</b> Outstanding Loans</html>");
		        return;
		  }else {
			  EditMember editmember = new EditMember(memberId, Membership.this);
			  editmember.setVisible(true);
		  }
	}

	
	public static void showError(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		}

	
	private void memberUpdate() {
		if(callback != null) {
			callback.onMemberListUpdated();
		}
	}

	@Override
	public void onMemberListUpdated() {
		// TODO Auto-generated method stub
		try {
			diplayMemberDetails();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
