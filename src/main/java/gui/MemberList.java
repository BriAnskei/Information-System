package main.java.gui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.java.dao.MemberDAO;
import main.java.model.Member;
import main.java.Callback.*;
import main.java.service.*;
import main.java.util.ImageUtil;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;

public class MemberList extends JFrame implements MemberUpdateCallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField searchInput;
	private JTable table;
	
	//Info Labels
	JLabel firstNameLabel;
	JLabel lastNameLabel;
	JLabel addressLabel;
	JLabel contactLabel;
	JLabel emailLabel;
	JLabel idLabel;
	JLabel imgLabel;
	
	private int memberId = 0;// Get row ID on table
	private MemberUpdateCallback callback;

	
	public MemberList(MemberUpdateCallback callback) {
		this.callback = callback; 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 781, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(362, 10, -175, 10);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 166, 83));
		panel_1.setBounds(0, 0, 765, 51);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		
		
		ImageIcon image = new ImageIcon("src/main/java/img/MemberLogo.png"); // Replace with actual path

		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(309, 0, 60, 50);
		panel_1.add(imgLogo);
		
		JLabel lblNewLabel = new JLabel("Members List");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		lblNewLabel.setBounds(379, 0, 171, 50);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Search name or id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 60, 127, 30);
		contentPane.add(lblNewLabel_1);
		
		searchInput = new JTextField();
		searchInput.setBounds(135, 62, 183, 30);
		contentPane.add(searchInput);
		searchInput.setColumns(10);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMemberInfo(searchInput.getText().toString());
			}
		});
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(326, 62, 139, 30);
		contentPane.add(btnNewButton);
		
		
				
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddMember addMember = new AddMember(MemberList.this);
				addMember.setVisible(true);
				
			}
		});
		addButton.setBackground(new Color(192, 192, 192));
		addButton.setBounds(490, 62, 85, 30);
		contentPane.add(addButton);
		
		JButton editbutton = new JButton("EDIT");
		editbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditMember editMember;
				try {
					editMember = new EditMember(memberId, MemberList.this);
					editMember.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			;
			}
		});
		editbutton.setBackground(new Color(192, 192, 192));
		editbutton.setBounds(581, 62, 85, 30);
		contentPane.add(editbutton);
		
		JButton deleteButton = new JButton("DELETE");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMember(memberId);
			}
		});
		deleteButton.setBackground(new Color(192, 192, 192));
		deleteButton.setBounds(672, 62, 85, 30);
		contentPane.add(deleteButton);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(new Color(205, 205, 205));
		infoPanel.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Member info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		infoPanel.setBounds(483, 113, 270, 256);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);
		
		imgLabel = new JLabel("");
		imgLabel.setBounds(79, 27, 120, 90);
		infoPanel.add(imgLabel);
		
		firstNameLabel = new JLabel("");
		firstNameLabel.setBounds(39, 128, 90, 14);
		infoPanel.add(firstNameLabel);
		
	    lastNameLabel = new JLabel("");
		lastNameLabel.setBounds(139, 128, 105, 14);
		infoPanel.add(lastNameLabel);
		
		contactLabel = new JLabel("");
		contactLabel.setBounds(39, 178, 190, 14);
		infoPanel.add(contactLabel);
		
	    addressLabel = new JLabel("");
		addressLabel.setBounds(39, 153, 190, 14);
		infoPanel.add(addressLabel);
		
	    emailLabel = new JLabel("");
		emailLabel.setBounds(39, 203, 190, 14);
		infoPanel.add(emailLabel);
		
		idLabel = new JLabel("");
		idLabel.setBounds(39, 228, 190, 14);
		infoPanel.add(idLabel);
		
		JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(10, 121, 455, 248);
	    contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		// Inside the MemberList class

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		  @Override
		  public void valueChanged(ListSelectionEvent e) {
			  if (!e.getValueIsAdjusting()) {
				  int selectedRow  = table.getSelectedRow();
                  if (selectedRow != -1) {
                      DefaultTableModel model = (DefaultTableModel) table.getModel();
                      memberId = (int) model.getValueAt(selectedRow, 0);
                      displayMemberInfo(Integer.toString(memberId));
                  }
              }
          }
		});

		//Load table of member list
		loadTable();
		
	}
	
	
		MemberDAO memberDAO = new MemberDAO();
		MemberService service = new MemberService();

	  @Override
	    public void onMemberListUpdated() {
	        loadTable();
	        resetInfoCard();
	        memberListUpdated();
	    }
	  
	  private void resetInfoCard() {
		  idLabel.setText(null);
          firstNameLabel.setText(null);
          lastNameLabel.setText(null);
          addressLabel.setText(null);
          contactLabel.setText(null);
          emailLabel.setText(null);
          imgLabel.setIcon(null);
	  }
	
	 public void loadTable() {
		 String[] columnNames = {"ID", "FirstName", "LastName", "Address", "Contact", "Email"};
		 DefaultTableModel model = new DefaultTableModel(null, columnNames);
		 try {
	            List<Member> members = service.getAllMembers();
	            for (Member member : members) {
	            	 Object[] row = {
	            			 member.getMemberId(),
	            			 member.getFirstName(),
	            			 member.getLastName(),
	            			 member.getAddress(),
	            			 member.getPhoneNumber(),
	            			 member.getEmail(),
	            	 };
	            	 model.addRow(row);
	            }
	            table.setModel(model);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }
	 
	 
	 private void displayMemberInfo(String search) {
		 List<Member> searchedMembers;
	        try {
	            searchedMembers = service.searchMember(search);
	            for (Member member : searchedMembers) {
	                try {
	                	
	                    BufferedImage buffedImg = ImageUtil.convertByteArrayToBufferedImage(member.getImageData());
	                    ImageIcon icon = new ImageIcon(ImageUtil.resizeImage(buffedImg, imgLabel.getWidth(), imgLabel.getHeight()));

	                    idLabel.setText(Integer.toString(member.getMemberId()));
	                    firstNameLabel.setText(member.getFirstName());
	                    lastNameLabel.setText(member.getLastName());
	                    addressLabel.setText(member.getAddress());
	                    contactLabel.setText(member.getPhoneNumber());
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
	 
	 private void deleteMember(int memberId)  {
		if(memberId == 0) return;
		
			 int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION);
			 
			 if(response == JOptionPane.YES_OPTION) {
				 try {
					service.Delete(memberId);
					// Refresh the table and reset card info
					resetInfoCard();
					 loadTable();
					 
					memberListUpdated(); // Call to notify other applications
					
					 JOptionPane.showMessageDialog(null, "Member Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			
		
	 }
	 
	 // notify other parts of the application that the member list has been updated.
	 private void memberListUpdated() {
		 if(callback != null) {
			 callback.onMemberListUpdated();
		 }
	 }
	 
	 
}
