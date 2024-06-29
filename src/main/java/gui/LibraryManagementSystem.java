package main.java.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import javax.swing.border.MatteBorder;

import main.java.Callback.MemberUpdateCallback;
import main.java.dao.MemberDAO;
import main.java.Callback.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class LibraryManagementSystem extends JFrame implements MemberUpdateCallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel memberCountLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryManagementSystem frame = new LibraryManagementSystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public LibraryManagementSystem() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
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
		
		JLabel lblNewLabel_1 = new JLabel("0");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblNewLabel_1.setBounds(73, 41, 46, 71);
		bookCard.add(lblNewLabel_1);
		
		JPanel NavigationPanel = new JPanel();
		NavigationPanel.setBackground(Color.RED);
		NavigationPanel.setForeground(Color.RED);
		NavigationPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		NavigationPanel.setBounds(0, 0, 884, 70);
		contentPane.add(NavigationPanel);
		NavigationPanel.setLayout(null);
		
		JButton btnBook = new JButton("BOOKS");
		btnBook.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnBook.setBackground(new Color(192, 192, 192));
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBook.setBounds(558, 18, 95, 35);
		NavigationPanel.add(btnBook);
		
		
		JButton btnMember = new JButton("MEMBER");
		btnMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberList memberList = new MemberList(LibraryManagementSystem.this);
				memberList.setVisible(true); // Open Window
			}
		});
		btnMember.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnMember.setBackground(new Color(192, 192, 192));
		btnMember.setBounds(663, 18, 95, 35);
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
		btnCirculate.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCirculate.setBackground(Color.LIGHT_GRAY);
		btnCirculate.setBounds(767, 18, 95, 35);
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
		memberCountLabel.setBounds(73, 41, 46, 71);
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
		
		JLabel authorNavLabel = new JLabel("Authors");
		authorNavLabel.setForeground(Color.WHITE);
		authorNavLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		authorNavLabel.setBounds(10, 0, 80, 30);
		BookCardLabel_2.add(authorNavLabel);
		
		JLabel lblNewLabel_1_2 = new JLabel("0");
		lblNewLabel_1_2.setBackground(new Color(172, 89, 255));
		lblNewLabel_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblNewLabel_1_2.setBounds(73, 41, 46, 71);
		bookCard_2.add(lblNewLabel_1_2);
		
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
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(141, 57, 107, 109);
		bookList.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(299, 57, 107, 109);
		bookList.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(439, 57, 107, 109);
		bookList.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(584, 57, 107, 109);
		bookList.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(735, 57, 107, 109);
		bookList.add(lblNewLabel_5);
		
		initializeTotalMembers(); 
	}
	
	MemberDAO memberDAO = new MemberDAO();
	@Override
    public void onMemberListUpdated() {
		try {
			initializeTotalMembers();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private void initializeTotalMembers() throws SQLException {
		memberCountLabel.setText(Integer.toString(memberDAO.countTotalMember()));
	}
}
