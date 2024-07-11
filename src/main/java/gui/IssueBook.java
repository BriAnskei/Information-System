package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import com.toedter.calendar.JDateChooser;

import main.java.Callback.*;
import main.java.model.Member;
import main.java.service.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class IssueBook extends JFrame {

	private static final long serialVersionUID = 1L;
	private IssueUpdateCallback callback;
	private Member member;
	private JPanel frame;
	
	private JTextField bookIdInput;
	private JTextField memberIdLabel;
	private JDateChooser issuedDate;
	private JDateChooser dueDate;



		
	public IssueBook(IssueUpdateCallback callback, Member member) {
		this.member = member;
		this.callback = callback;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 417);
		frame = new JPanel();
		frame.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setBackground(new Color(205, 205, 205));

		setContentPane(frame);
		frame.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(78, 0, 155));
		panel.setBounds(0, 0, 329, 50);
		frame.add(panel);
		panel.setLayout(null);
		
		JLabel lblIssue = new JLabel("Issue Book");
		lblIssue.setBounds(139, 0, 160, 50);
		panel.add(lblIssue);
		lblIssue.setForeground(Color.WHITE);
		lblIssue.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		
		JLabel bookIdLbl = new JLabel("Book ID");
		bookIdLbl.setBounds(27, 73, 66, 14);
		frame.add(bookIdLbl);
		
		JLabel lblNewLabel = new JLabel("Member ID");
		lblNewLabel.setBounds(27, 129, 66, 14);
		frame.add(lblNewLabel);
		
		JButton btnBrowseBook = new JButton("Details");
		btnBrowseBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookId = bookIdInput.getText();
				BookCard bookCard = new BookCard(bookId);
				bookCard.setVisible(true);
			}
		});
		btnBrowseBook.setBackground(Color.LIGHT_GRAY);
		btnBrowseBook.setBounds(208, 93, 94, 30);
		frame.add(btnBrowseBook);
		
		bookIdInput = new JTextField();
		bookIdInput.setColumns(10);
		bookIdInput.setBounds(27, 93, 171, 30);
		frame.add(bookIdInput);
		
		memberIdLabel = new JTextField();
		memberIdLabel.setEditable(false);
		memberIdLabel.setColumns(10);
		memberIdLabel.setBounds(27, 154, 171, 30);
		frame.add(memberIdLabel);
		
		JButton btnBrowseMember = new JButton("Show");
		btnBrowseMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String memberId = memberIdLabel.getText();
				MemberCard memberCard = new MemberCard(memberId);
				memberCard.setVisible(true);
			}
		});
		btnBrowseMember.setBackground(Color.LIGHT_GRAY);
		btnBrowseMember.setBounds(208, 154, 94, 30);
		frame.add(btnBrowseMember);
		
		issuedDate = new JDateChooser();
		issuedDate.setBounds(27, 220, 275, 30);
		frame.add(issuedDate);
		
		JLabel lblNewLabel_1 = new JLabel("Issue Date");
		lblNewLabel_1.setBounds(27, 195, 115, 14);
		frame.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Due Date");
		lblNewLabel_1_1.setBounds(27, 261, 115, 14);
		frame.add(lblNewLabel_1_1);
		
		dueDate = new JDateChooser();
		dueDate.setBounds(27, 286, 275, 30);
		frame.add(dueDate);
		
		JButton btnBorrow = new JButton("BORROW");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				issueBook();
			}
		});
		btnBorrow.setBackground(Color.LIGHT_GRAY);
		btnBorrow.setBounds(10, 327, 106, 30);
		frame.add(btnBorrow);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(213, 327, 106, 30);
		frame.add(btnCancel);
		
		initializeMemberId(member.getMemberId());
	}
	
	LoanService  loanService = new LoanService();
	BookService bookService = new BookService();
	
	private void issueBook() {
	    String bookId = bookIdInput.getText();
	    String memberId = memberIdLabel.getText();
	    java.util.Date issuedDateUtil = issuedDate.getDate();
	    java.util.Date dueDateUtil = dueDate.getDate();
	    
	    if (!bookService.bookValidation(bookId)) {
	        showErrorDialog("Book doesn't exist in Database");
	        return;
	    }
	    
	    if (isFormIncomplete(bookId, memberId, issuedDateUtil, dueDateUtil)) {
	        showErrorDialog("Please complete the form.");
	        return;
	    }
	    
	    if (!bookUnavailable(bookId)) {
	        showErrorDialog("Book Unavailable");
	        return;
	    }

	    try {
	        boolean issued = loanService.issue(bookId, memberId, issuedDateUtil, dueDateUtil);
	        if (issued) {
	            showSuccessDialog("Book issued successfully.");
	            issueUpdate();
	        } else {
	            System.out.println("Issuing book Error");
	        }
	    } catch (SQLException e) {
	        showErrorDialog("Error issuing book: " + e.getMessage());
	        logError(e);
	    }
	}
	
	private void initializeMemberId(int  memberId) {
		memberIdLabel.setText(Integer.toString(memberId));
	}

	
	// Check the  quantity of books
	private boolean bookUnavailable(String bookId) {
		BookService bookCheck = new BookService();
		return bookCheck.checkQuantity(bookId);
	}

	private boolean isFormIncomplete(String bookId, String memberId, java.util.Date issuedDateUtil, java.util.Date dueDateUtil) {
	    return bookId == null || bookId.trim().isEmpty() ||
	           memberId == null || memberId.trim().isEmpty() ||
	           issuedDateUtil == null || dueDateUtil == null;
	}

	private void showErrorDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showSuccessDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}

	private void logError(SQLException e) {
	    e.printStackTrace();
	}

	private void issueUpdate() {
		if(callback != null) {
			callback.onIssueListUpdate();
		}
	}
}
