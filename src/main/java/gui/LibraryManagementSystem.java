package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.border.MatteBorder;

import main.java.Callback.MemberUpdateCallback;
import main.java.dao.*;
import main.java.model.*;
import main.java.util.ImageUtil;
import main.java.Callback.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class LibraryManagementSystem extends JFrame implements MemberUpdateCallback, BookUpdateCallback, IssueUpdateCallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel memberCountLabel;
	private JLabel bookLabel;
	private JLabel issueBookLabel;
	
	private JLabel bookImage1;
	private JLabel bookImage2;
	private JLabel bookImage3;
	private JLabel bookImage4;
	private JLabel bookImage5;

	
	public LibraryManagementSystem() throws SQLException, IOException {
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
		
		bookLabel = new JLabel("0");
		bookLabel.setVerticalAlignment(SwingConstants.TOP);
		bookLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		bookLabel.setBounds(0, 41, 190, 71);
		bookLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bookCard.add(bookLabel);
		
		JPanel NavigationPanel = new JPanel();
		NavigationPanel.setBackground(Color.RED);
		NavigationPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		NavigationPanel.setBounds(0, 0, 884, 70);
		contentPane.add(NavigationPanel);
		NavigationPanel.setLayout(null);
		
		
		JButton btnBook = new JButton("BOOKS");
		btnBook.setForeground(new Color(255, 255, 255));
		btnBook.setBorder(BorderFactory.createEmptyBorder());
		btnBook.setBorderPainted(false);
		btnBook.setFocusPainted(false);
		btnBook.setContentAreaFilled(false);
		btnBook.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnBook.setBackground(Color.red);
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookList bookList = new BookList(LibraryManagementSystem.this);
				bookList.setVisible(true);
			}
		});
		btnBook.setBounds(455, 18, 120, 35);
		NavigationPanel.add(btnBook);
		
		
		JButton btnMember = new JButton("MEMBER");
		btnMember.setForeground(new Color(255, 255, 255));
		btnMember.setBorder(BorderFactory.createEmptyBorder());
		btnMember.setBorderPainted(false);
		btnMember.setFocusPainted(false);
		btnMember.setContentAreaFilled(false);
		btnMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberList memberList = new MemberList(LibraryManagementSystem.this);
				memberList.setVisible(true); // Open Window
			}
		});
		btnMember.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnMember.setBackground(new Color(192, 192, 192));
		btnMember.setBounds(585, 18, 129, 35);
		NavigationPanel.add(btnMember);
		
		
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
		btnCirculate.setForeground(new Color(255, 255, 255));
		btnCirculate.setBorder(BorderFactory.createEmptyBorder());
		btnCirculate.setBorderPainted(false);
		btnCirculate.setFocusPainted(false);
		btnCirculate.setContentAreaFilled(false);
		btnCirculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Circulation circulate = new Circulation(LibraryManagementSystem.this);
				circulate.setVisible(true);
			}
		});
		btnCirculate.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnCirculate.setBackground(new Color(192, 192, 192));
		btnCirculate.setBounds(724, 18, 150, 35);
		NavigationPanel.add(btnCirculate);
		
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
		
	    memberCountLabel = new JLabel("0");
		memberCountLabel.setVerticalAlignment(SwingConstants.TOP);
		memberCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		memberCountLabel.setBounds(0, 41, 190, 71);
		memberCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
		
		JLabel lblLatestBookAdded = new JLabel("Latest Book Added");
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
		btnLogOut.setBounds(800, 243, 84, 15);
		contentPane.add(btnLogOut);
		
		initializeTotalMembers(); 
		initializeTotalBooks();
		initializeTotalIssued();
		initializeLatestBooksImg();
	}
	
	MemberDAO memberDAO = new MemberDAO();
	BookDAO bookDAO = new BookDAO();
	LoanDAO loanDAO = new LoanDAO();
	
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
	
	@Override
	public void onIssueListUpdate() {
		try {
			initializeTotalIssued();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initializeTotalMembers() throws SQLException {
		memberCountLabel.setText(Integer.toString(memberDAO.countTotalMember()));
	}
	
	private void initializeTotalIssued() throws SQLException {
		issueBookLabel.setText(Integer.toString(loanDAO.countTotalLoans()));
	}
	
	private void initializeTotalBooks() throws SQLException {
		bookLabel.setText(Integer.toString(bookDAO.countTotalBooks()));
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
}
