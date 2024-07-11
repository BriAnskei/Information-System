package main.java.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import main.java.Callback.*;
import main.java.dao.BookDAO;
import main.java.dao.LoanDAO;
import main.java.dao.MemberDAO;
import main.java.model.*;
import main.java.service.MemberService;
import main.java.util.ImageUtil;

public class LibraryManagementSystem_user extends JFrame implements IssueUpdateCallback, BookUpdateCallback, MemberUpdateCallback {

	private static final long serialVersionUID = 1L;
	private User loggedUser;
	private JPanel contentPane;
	private JLabel issueBookLabel;
	
	private JLabel bookImage1;
	private JLabel bookImage2;
	private JLabel bookImage3;
	private JLabel bookImage4;
	private JLabel bookImage5;

	
	public LibraryManagementSystem_user(User loggedUser) throws SQLException, IOException {
		this.loggedUser = loggedUser;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 485);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(205, 205, 205));
		contentPane.setForeground(new Color(226, 35, 40));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel bookCard = new JPanel();
		bookCard.setBackground(new Color(255, 255, 111));
		bookCard.setBounds(51, 81, 190, 150);
		contentPane.add(bookCard);
		bookCard.setLayout(null);
		
		JPanel BookNav = new JPanel();
		BookNav.setBackground(new Color(132, 132, 0));
		BookNav.setBounds(0, 0, 190, 30);
		bookCard.add(BookNav);
		BookNav.setLayout(null);
		
		JLabel bookNavLabel = new JLabel("Books");
		bookNavLabel.setForeground(new Color(255, 255, 255));
		bookNavLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		bookNavLabel.setBounds(10, 0, 80, 30);
		BookNav.add(bookNavLabel);
		
		JLabel bookLabel = new JLabel("10");
		bookLabel.setVerticalAlignment(SwingConstants.TOP);
		bookLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bookLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		bookLabel.setBounds(0, 46, 190, 71);
		bookCard.add(bookLabel);
		
		JPanel NavigationPanel = new JPanel();
		NavigationPanel.setBackground(Color.RED);
		NavigationPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		NavigationPanel.setBounds(0, 0, 884, 70);
		contentPane.add(NavigationPanel);
		NavigationPanel.setLayout(null);
		
		
		ImageIcon image = new ImageIcon("src/main/java/img/um-seal.png"); // Replace with actual path

		
		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(10, 11, 60, 50);
		NavigationPanel.add(imgLogo);
		
		JLabel logoNavLabel = new JLabel("Library");
		logoNavLabel.setForeground(new Color(255, 255, 255));
		logoNavLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 45));
		logoNavLabel.setBounds(68, 0, 162, 70);
		NavigationPanel.add(logoNavLabel);
		
		JButton btnCirculate = new JButton("CIRCULATE");
		btnCirculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				circulation();
			}
		});
		btnCirculate.setForeground(Color.WHITE);
		btnCirculate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnCirculate.setFocusPainted(false);
		btnCirculate.setContentAreaFilled(false);
		btnCirculate.setBorderPainted(false);
		btnCirculate.setBorder(BorderFactory.createEmptyBorder());
		btnCirculate.setBackground(Color.LIGHT_GRAY);
		btnCirculate.setBounds(724, 18, 150, 35);
		NavigationPanel.add(btnCirculate);
		
		JButton btnMember = new JButton("MEMBERSHIP");
		btnMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					memberProfile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnMember.setForeground(Color.WHITE);
		btnMember.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnMember.setFocusPainted(false);
		btnMember.setContentAreaFilled(false);
		btnMember.setBorderPainted(false);
		btnMember.setBorder(BorderFactory.createEmptyBorder());
		btnMember.setBackground(Color.LIGHT_GRAY);
		btnMember.setBounds(597, 18, 129, 35);
		NavigationPanel.add(btnMember);
		
		JButton btnBook = new JButton("BOOKS");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookList_user bookList = new BookList_user(LibraryManagementSystem_user.this);
				bookList.setVisible(true);
			}
		});
		btnBook.setForeground(Color.WHITE);
		btnBook.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnBook.setFocusPainted(false);
		btnBook.setContentAreaFilled(false);
		btnBook.setBorderPainted(false);
		btnBook.setBorder(BorderFactory.createEmptyBorder());
		btnBook.setBackground(Color.RED);
		btnBook.setBounds(467, 18, 120, 35);
		NavigationPanel.add(btnBook);
		
		JPanel memberCard = new JPanel();
		memberCard.setLayout(null);
		memberCard.setBackground(Color.ORANGE);
		memberCard.setBounds(339, 81, 190, 150);
		contentPane.add(memberCard);
		
		JPanel memberNav = new JPanel();
		memberNav.setLayout(null);
		memberNav.setBackground(new Color(234, 117, 0));
		memberNav.setBounds(0, 0, 190, 30);
		memberCard.add(memberNav);
		
		JLabel memberNavLabel = new JLabel("Members");
		memberNavLabel.setForeground(Color.WHITE);
		memberNavLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		memberNavLabel.setBounds(10, 0, 80, 30);
		memberNav.add(memberNavLabel);
		
		JLabel memberCountLabel = new JLabel("2");
		memberCountLabel.setVerticalAlignment(SwingConstants.TOP);
		memberCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		memberCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		memberCountLabel.setBounds(0, 41, 190, 71);
		memberCard.add(memberCountLabel);
		
		JPanel bookCard_2 = new JPanel();
		bookCard_2.setLayout(null);
		bookCard_2.setBackground(new Color(151, 47, 255));
		bookCard_2.setBounds(636, 81, 190, 150);
		contentPane.add(bookCard_2);
		
		JPanel BookCardLabel_2 = new JPanel();
		BookCardLabel_2.setLayout(null);
		BookCardLabel_2.setBackground(new Color(78, 0, 155));
		BookCardLabel_2.setBounds(0, 0, 190, 30);
		bookCard_2.add(BookCardLabel_2);
		
		JLabel issuesNavLabel = new JLabel("Issued");
		issuesNavLabel.setForeground(Color.WHITE);
		issuesNavLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		issuesNavLabel.setBounds(10, 0, 80, 30);
		BookCardLabel_2.add(issuesNavLabel);
		
		issueBookLabel = new JLabel("0");
		issueBookLabel.setBackground(new Color(172, 89, 255));
		issueBookLabel.setVerticalAlignment(SwingConstants.TOP);
		issueBookLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		issueBookLabel.setBounds(0, 41, 190, 71);
		issueBookLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bookCard_2.add(issueBookLabel);
		
		JPanel bookList = new JPanel();
		bookList.setBackground(new Color(47, 47, 255));
		bookList.setBounds(10, 259, 864, 177);
		contentPane.add(bookList);
		bookList.setLayout(null);
		
		JPanel bookListNav = new JPanel();
		bookListNav.setBackground(new Color(0, 0, 160));
		bookListNav.setBounds(0, 0, 864, 46);
		bookList.add(bookListNav);
		bookListNav.setLayout(null);
		
		JLabel lblLatestBookAdded = new JLabel("Latest Books");
		lblLatestBookAdded.setBounds(10, 0, 221, 46);
		bookListNav.add(lblLatestBookAdded);
		lblLatestBookAdded.setForeground(Color.WHITE);
		lblLatestBookAdded.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		bookImage1 = new JLabel("New label");
		bookImage1.setBounds(141, 57, 107, 109);
		bookList.add(bookImage1);
		
		bookImage2 = new JLabel("New label");
		bookImage2.setBounds(299, 57, 107, 109);
		bookList.add(bookImage2);
		
		bookImage3 = new JLabel("New label");
		bookImage3.setBounds(439, 57, 107, 109);
		bookList.add(bookImage3);
		
		bookImage4 = new JLabel("New label");
		bookImage4.setBounds(584, 57, 107, 109);
		bookList.add(bookImage4);
		
		bookImage5 = new JLabel("New label");
		bookImage5.setBounds(735, 57, 107, 109);
		bookList.add(bookImage5);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginForm login = new LoginForm();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogOut.setForeground(Color.BLUE);
		btnLogOut.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnLogOut.setFocusPainted(false);
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setBorderPainted(false);
		btnLogOut.setBorder(BorderFactory.createEmptyBorder());
		btnLogOut.setBackground(Color.LIGHT_GRAY);
		btnLogOut.setBounds(800, 242, 84, 15);
		contentPane.add(btnLogOut);
		
		initializeTotalMembers(); 
		initializeTotalBooks();
		initializeTotalIssued();
		initializeLatestBooksImg();
	}
	
	MemberDAO memberDAO = new MemberDAO();
	BookDAO bookDAO = new BookDAO();
	LoanDAO loanDAO = new LoanDAO();
	
	
	


	 
	 private void initializeTotalMembers() throws SQLException {
		}
	 
	 @Override
	 public void onIssueListUpdate() {
	     System.out.println("Main window updated");
	     initializeTotalIssued();
	 }

	 private void initializeTotalIssued() {
		 if(!isUserRegistered(loggedUser.getUserId())) return;
	     try {
	         Member member = memberDAO.getMemberByUserId(loggedUser.getUserId());
	         if (member != null) {
	             System.out.println("Member ID: " + member.getMemberId());
	             int totalIssued = loanDAO.countIssuedBooksByMemberId(member.getMemberId());
	             updateTotalIssuedDisplay(totalIssued);
	         } else {
	             System.err.println("Error retrieving member details: Member not found for user ID " + loggedUser.getUserId());
	         }
	     } catch (Exception e) {
	         System.err.println("Exception while initializing total issued books: " + e.getMessage());
	         e.printStackTrace();
	     }
	 }

	 private void updateTotalIssuedDisplay(int totalIssued) {
	     issueBookLabel.setText(Integer.toString(totalIssued));
	     System.out.println("Total Issued Books: " + totalIssued);  // For debugging
	 }

		
	private void initializeTotalBooks() throws SQLException {
		}
		
	private void initializeLatestBooksImg() throws SQLException, IOException {
			List<Book> latestBooks = bookDAO.getLatesBooks();
			JLabel[] arrImageLabels = {bookImage1, bookImage2, bookImage3, bookImage4, bookImage5};
			for(int i = 0; i < latestBooks.size() && i < 5; i++) {
				Book book = latestBooks.get(i);
				
				BufferedImage buffedImg = ImageUtil.convertByteArrayToBufferedImage(book.getImageData());
				
				ImageIcon icon = new ImageIcon(ImageUtil.resizeImage(buffedImg, arrImageLabels[i].getWidth(), arrImageLabels[i].getHeight()));
				arrImageLabels[i].setIcon(icon);
				
			}
		}
	
	private void circulation() {
		if(!isUserRegistered(loggedUser.getUserId())) {
			showErrorMessage("It appears you are not currently registered for a membership.\nTo access this feature, please register for a membership first.");
		}else {
			Circulation_user circulation = new Circulation_user(LibraryManagementSystem_user.this, loggedUser);
			circulation.setVisible(true);
		}
	}
	  
	 private void memberProfile() throws IOException {
		 // Checks if  the user is member if not it will show some registration option.
		 if(!isUserRegistered(loggedUser.getUserId())) {
			 membershipRegistration();
		 }else {
			 Membership membership = new Membership(LibraryManagementSystem_user.this, loggedUser);
			 membership.setVisible(true);
		 }
	 }
	 
	 private boolean isUserRegistered(int userId) {
		 MemberService service = new MemberService();
		 return service.checkMembership(userId);
	 }
	 
	 private void membershipRegistration() {
		    if (confirmationMessage("You are not currently a member. Would you like to register?")) {
		        MembershipForm membership = new MembershipForm(LibraryManagementSystem_user.this, loggedUser);
		        membership.setVisible(true);
		    }
		}

	 private boolean confirmationMessage(String message) {
		 int result = JOptionPane.showConfirmDialog(null, message, "Membership Registration", JOptionPane.YES_NO_OPTION);
		 return result == JOptionPane.YES_OPTION;
	 }
	 
	 private void showErrorMessage(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		}

	 
	 private void succesfullMessage(String message) {
		 JOptionPane.showMessageDialog(null, "Success!", message, JOptionPane.INFORMATION_MESSAGE);

	 }
	 
		@Override
	    public void onMemberListUpdated() {
			try {
				initializeTotalMembers();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		
		@Override
		public void onBookListUpdated() {
			try {
				initializeLatestBooksImg();// Update the latest book image display.
				initializeTotalBooks();
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
