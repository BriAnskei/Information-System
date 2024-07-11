package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.java.Callback.*;
import main.java.model.*;
import main.java.service.*;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Circulation_user extends JFrame implements IssueUpdateCallback {

	private static final long serialVersionUID = 1L;
	private IssueUpdateCallback callback;
	private User user;
	
	private JPanel contentPane;
	private JTable table;
	
	private int loanId = 0;

	/**
	 * Launch the application.
	 */
	public Circulation_user(IssueUpdateCallback callback, User user) {
		this.user = user;
		this.callback = callback;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 591, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(78, 0, 155));
		panel.setBounds(0, 0, 575, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel navLabelTitle = new JLabel("Circulation");
		navLabelTitle.setBounds(280, 0, 169, 50);
		panel.add(navLabelTitle);
		navLabelTitle.setForeground(Color.WHITE);
		navLabelTitle.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		
		ImageIcon image = new ImageIcon("src/main/java/img/Circulation.png"); 
		
		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(220, 0, 60, 50);
		panel.add(imgLogo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 61, 555, 260);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton issueBtn = new JButton("ISSUE");
		issueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				issueBook();
			}
		});
		issueBtn.setBackground(Color.LIGHT_GRAY);
		issueBtn.setBounds(10, 343, 140, 30);
		contentPane.add(issueBtn);
		
		JButton returnBtn = new JButton("RETURN");
		returnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					returnSelectedBook();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		returnBtn.setBackground(Color.LIGHT_GRAY);
		returnBtn.setBounds(425, 343, 140, 30);
		contentPane.add(returnBtn);
		
		// Listener for the selected rows.
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				  @Override
				  public void valueChanged(ListSelectionEvent e) {
					  if (!e.getValueIsAdjusting()) {
						  int selectedRow  = table.getSelectedRow();
		                  if (selectedRow != -1) {
		                      DefaultTableModel model = (DefaultTableModel) table.getModel();
		                      loanId = (int) model.getValueAt(selectedRow, 0);
//		                      displayMemberInfo(Integer.toString(loanId));
		                  }
		              }
		          }
				});
		
		loadTable();
	}
	

	
	
	
	public void loadTable() {
	    LoanService loanService = new LoanService();
	    BookService bookService = new BookService();
	    String[] columnNames = {"ID", "Title", "Issued", "Due"};
	    DefaultTableModel model = new DefaultTableModel(null, columnNames);

	    // Get all the member details from the Database, after that get all the user loans using the memberId.
	    Member member = fetchMemberDetails(user.getUserId());
	    List<Loan> loans = loanService.getUserLoan(member.getMemberId());

	    // Fetch all book details in one go
	    Set<Integer> bookIds = loans.stream().map(Loan::getBookID).collect(Collectors.toSet());
	    Map<Integer, Book> bookMap = bookService.getBooksByIds(bookIds);

	    for (Loan loan : loans) {
	        Book book = bookMap.get(loan.getBookID());
	        Object[] row = {
	            loan.getLoanID(),
	            book.getTittle(),
	            loan.getIssueDate(),
	            loan.getDueDate(),
	        };
	        model.addRow(row);
	    }
	    table.setModel(model);
	}

	
	
	private void issueBook() {
		Member member = fetchMemberDetails(user.getUserId());
		IssueBook issuebook = new IssueBook(Circulation_user.this, member);
		issuebook.setVisible(true);
	}
	
	private void returnSelectedBook() throws SQLException {
		if(loanId == 0) {
			showErrorMessage("Select a Book to  return");
			return;
		}
		String loan = Integer.toString(loanId);
		ReturnBook returnBook = new ReturnBook(loan, Circulation_user.this);
		returnBook.setVisible(true);
	}
	
	// Get memberDetails by user Id from the Database.
	private Member fetchMemberDetails(int userId) {
		MemberService memberService = new MemberService();
		return memberService.getMemberdetails(user.getUserId());
	}
	
	private  void showErrorMessage(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public void onIssueListUpdate() {
		System.out.println("Circulation updated");
		loadTable();
		issueBookUodated();
	}
	 private void issueBookUodated() {
		 if(callback != null) {
			 callback.onIssueListUpdate();
		 }
	 }	 
}
