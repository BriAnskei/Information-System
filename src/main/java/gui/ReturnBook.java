package main.java.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import main.java.Callback.*;
import main.java.model.Loan;
import main.java.service.LoanService;

public class ReturnBook extends JFrame {

	private static final long serialVersionUID = 1L;
	private  IssueUpdateCallback callback;
	private JPanel frame;
	
	
	private JTextField bookIdInput;
	private JTextField memberIdLabel;
	private JDateChooser issuedDate;
	private JDateChooser dueDate;
	private JDateChooser dateReturned;
	
	private String loanId;

	public ReturnBook(String loanId, IssueUpdateCallback callback) throws SQLException {
		this.loanId = loanId;
		this.callback = callback;
		
		System.out.println(loanId);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 475);
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
		
		JLabel lblIssue = new JLabel("Return Book");
		lblIssue.setBounds(29, 0, 160, 50);
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
		bookIdInput.setEditable(false);
		bookIdInput.setColumns(10);
		bookIdInput.setBounds(27, 93, 171, 30);
		frame.add(bookIdInput);
		
		memberIdLabel = new JTextField();
		memberIdLabel.setEditable(false);
		memberIdLabel.setColumns(10);
		memberIdLabel.setBounds(27, 154, 275, 30);
		frame.add(memberIdLabel);
		
		issuedDate = new JDateChooser();
		issuedDate.getDateEditor().setEnabled(false);
		issuedDate.setBounds(27, 220, 275, 30);
		frame.add(issuedDate);
		
		JLabel lblNewLabel_1 = new JLabel("Issue Date");
		lblNewLabel_1.setBounds(27, 195, 115, 14);
		frame.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Due Date");
		lblNewLabel_1_1.setBounds(27, 261, 115, 14);
		frame.add(lblNewLabel_1_1);
		
		dueDate = new JDateChooser();
		dueDate.getDateEditor().setEnabled(false);
		dueDate.setBounds(27, 286, 275, 30);
		frame.add(dueDate);
		
		JButton btnReturn = new JButton("RETURN");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					returnBook();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReturn.setBackground(Color.LIGHT_GRAY);
		btnReturn.setBounds(10, 395, 106, 30);
		frame.add(btnReturn);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBackground(Color.LIGHT_GRAY);
		btnCancel.setBounds(208, 395, 106, 30);
		frame.add(btnCancel);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Returned Dated");
		lblNewLabel_1_1_1.setBounds(27, 327, 115, 14);
		frame.add(lblNewLabel_1_1_1);
		
		dateReturned = new JDateChooser();
		dateReturned.setBounds(27, 354, 275, 30);
		frame.add(dateReturned);
		
		fillDetails();
	}

	LoanService service = new LoanService();
	
	private void fillDetails() throws SQLException {
		Loan loan = service.searchLoan(loanId);
		if(loan == null) {
			System.out.println("Error fetching loan details");
		}else {
			String bookId = Integer.toString(loan.getBookID());
			String memberId = Integer.toString(loan.getMemberID());
			
			bookIdInput.setText(bookId);
			memberIdLabel.setText(memberId);
			issuedDate.setDate(loan.getIssueDate());
			dueDate.setDate(loan.getDueDate());
		}
	}
	
	private void returnBook() throws SQLException {
		  String bookId = bookIdInput.getText();
		  String memberId = memberIdLabel.getText();
		  java.util.Date issuedDateUtil = issuedDate.getDate();
		  java.util.Date dueDateUtil = dueDate.getDate();
		  java.util.Date returnedDate = dateReturned.getDate();
		  
		  if(returnedDate == null) {
			  showErrorDialog(" Please enter the return date");
		  }
		
		  if(service.Return(loanId, bookId, memberId, issuedDateUtil, dueDateUtil, returnedDate)) { 
			  showSuccessDialog("book successfully returned");
			  issueUpdated();
		  }else {
			  System.out.println("Error returning book");
		  }
	}
	
	private void showErrorDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showSuccessDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void issueUpdated() {
		if(callback != null) {
			callback.onIssueListUpdate();
		}
		
	}
	
	
	
}
 