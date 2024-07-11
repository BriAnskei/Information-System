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
import main.java.model.Book;
import main.java.model.Loan;
import main.java.model.Member;
import main.java.service.BookService;
import main.java.service.LoanService;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Circulation extends JFrame implements BookUpdateCallback{

	private static final long serialVersionUID = 1L;
	private IssueUpdateCallback callback;
	private JPanel contentPane;
	private JTable unretunedBooksTable;
	private JTable retunedBooksTable;
	
	private List<Loan> unreturnedSearh;
	private List<Loan> retunedSearch;
	
	private int loanId = 0;
	
	
	private JTextField unreturnedBookInput;
	private JTextField retunedBookSearch;

	/**
	 * Launch the application.
	 */
	public Circulation(IssueUpdateCallback callback) {
		this.callback = callback;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(205, 205, 205));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(78, 0, 155));
		panel.setBounds(0, 0, 904, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel navLabelTitle = new JLabel("Circulation");
		navLabelTitle.setBounds(444, 0, 169, 50);
		panel.add(navLabelTitle);
		navLabelTitle.setForeground(Color.WHITE);
		navLabelTitle.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		
		ImageIcon image = new ImageIcon("src/main/java/img/Circulation.png"); 
		
		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(385, 0, 60, 50);
		panel.add(imgLogo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Unretuned Book", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 92, 430, 418);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane unreturnedScrollPane = new JScrollPane();
		unreturnedScrollPane.setBounds(20, 27, 386, 369);
		panel_1.add(unreturnedScrollPane);
		
		unretunedBooksTable = new JTable();
		unreturnedScrollPane.setViewportView(unretunedBooksTable);
		
		JLabel lblNewLabel_1 = new JLabel("Search member by Id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 61, 150, 30);
		contentPane.add(lblNewLabel_1);
		
		unreturnedBookInput = new JTextField();
		unreturnedBookInput.setColumns(10);
		unreturnedBookInput.setBounds(161, 61, 192, 30);
		contentPane.add(unreturnedBookInput);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchUnretunedLoans();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setBounds(363, 61, 77, 30);
		contentPane.add(btnNewButton);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Retuned Book", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1_1.setBounds(462, 92, 430, 418);
		contentPane.add(panel_1_1);
		
		JScrollPane returnedScrollPane = new JScrollPane();
		returnedScrollPane.setBounds(20, 27, 386, 369);
		panel_1_1.add(returnedScrollPane);
		
		retunedBooksTable = new JTable();
		returnedScrollPane.setViewportView(retunedBooksTable);
		
		JLabel lblNewLabel_1_1 = new JLabel("Search member by Id");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(453, 61, 150, 30);
		contentPane.add(lblNewLabel_1_1);
		
		retunedBookSearch = new JTextField();
		retunedBookSearch.setColumns(10);
		retunedBookSearch.setBounds(613, 61, 192, 30);
		contentPane.add(retunedBookSearch);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchRetunredBook();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.setBounds(815, 61, 77, 30);
		contentPane.add(btnNewButton_1);
		
		// Listener for the selected rows.
		unretunedBooksTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				  @Override
				  public void valueChanged(ListSelectionEvent e) {
					  if (!e.getValueIsAdjusting()) {
						  int selectedRow  = unretunedBooksTable.getSelectedRow();
		                  if (selectedRow != -1) {
		                      DefaultTableModel model = (DefaultTableModel) unretunedBooksTable.getModel();
		                      loanId = (int) model.getValueAt(selectedRow, 0);

		                  }
		              }
		          }
				});
		
		loadUnnreturnedTable();
		loanReturnedTable();
	}
	
	@Override
	public void onBookListUpdated() {
		loadUnnreturnedTable();
		loanReturnedTable();
	}
	
	LoanService loanService = new LoanService();
	BookService bookService = new BookService();
	
	
	public void loadUnnreturnedTable() {
		 String[] columnNames = {"ID", "Title", "Member ID", "Issued", "Due"};
		 DefaultTableModel model = new DefaultTableModel(null, columnNames);
		 try {
	            List<Loan> loans = unreturnedSearh != null ? unreturnedSearh: loanService.getLoans();
	            
	         // Fetch all book details in one go
	    	    Set<Integer> bookIds = loans.stream().map(Loan::getBookID).collect(Collectors.toSet());
	    	    Map<Integer, Book> bookMap = bookService.getBooksByIds(bookIds);
	            for (Loan loan : loans) {
	            	  Book book = bookMap.get(loan.getBookID());
	            	 Object[] row = {
	            			loan.getLoanID(),
	            			book.getTittle(),
	            			loan.getMemberID(),
	            			loan.getIssueDate(),
	            			loan.getDueDate(),
	            	 };
	            	 model.addRow(row);
	            }
	            unretunedBooksTable.setModel(model);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }
	
	// Initialize table based on the searched member id.
	private void searchUnretunedLoans() {
	    String inputSearch = unreturnedBookInput.getText();
	    if (inputSearch == null || inputSearch.isEmpty()) {
	        return;
	    }

	    try {
	        int memberId = Integer.parseInt(inputSearch);
	        if (memberId == 0) {
	            return;
	        }

	        unreturnedSearh = loanService.getUserLoan(memberId);
	        loadUnnreturnedTable();
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        showError("Invalid member ID");
	    }
	}
	
	private void searchRetunredBook() {
		String inputSearch = retunedBookSearch.getText();
		if(inputSearch == null || inputSearch.isEmpty()) {
			return;
		}
		
		  try {
		        int memberId = Integer.parseInt(inputSearch);
		        if (memberId == 0) {
		            return;
		        }
		        
		        retunedSearch = loanService.getretunedLoans(memberId);
		        loanReturnedTable();
		    } catch (NumberFormatException e) {
		        e.printStackTrace();
		        showError("Invalid member ID");
		    }
	}

	public static void showError(String message) {
		  JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		}
	
	

	
	public void loanReturnedTable() {
		   String[] columnNames = {"ID", "Title", "Member ID", "Due", "Returned"};
	        DefaultTableModel model = new DefaultTableModel(null, columnNames);
	        List<Loan> loans = retunedSearch != null ? retunedSearch : loanService.getAllReturnedBooks();
	        // Fetch all book details in one go
    	    Set<Integer> bookIds = loans.stream().map(Loan::getBookID).collect(Collectors.toSet());
    	    Map<Integer, Book> bookMap = bookService.getBooksByIds(bookIds);
	        for (Loan loan : loans) {
	        	  Book book = bookMap.get(loan.getBookID());
	            Object[] row = {
	                loan.getLoanID(),
	                book.getTittle(),
	                loan.getMemberID(),
	                loan.getDueDate(),
	                loan.getReturnDate()
	            };
	            model.addRow(row);
	        }
	        retunedBooksTable.setModel(model); 
	 }
}
