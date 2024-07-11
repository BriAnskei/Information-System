package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import main.java.Callback.*;
import main.java.model.*;
import main.java.service.BookService;
import main.java.util.ImageUtil;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class BookList_user extends JFrame implements BookUpdateCallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5387774472338963855L;
	private BookUpdateCallback callback;
	private int bookId = 0;
	
	private JPanel contentPane;
	private JTable table;
	
	// Initialize info card.
	private JLabel imgLabel;
	private JLabel lblTittle;
	private JLabel authorLabel;
	private JLabel publisherLabel;
	private JLabel yearPublishLabel;
	private JLabel isbnLabel;
	private JLabel categoryLabel;
	private JLabel quantityLabel;
	
	


	/**
	 * Launch the application.
	 */
	public BookList_user(BookUpdateCallback callback) {
		this.callback = callback;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 887, 473);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(205, 205, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JPanel navPanel = new JPanel();
		navPanel.setBackground(new Color(132, 132, 0));
		navPanel.setBounds(0, 0, 874, 50);
		contentPane.add(navPanel);
		navPanel.setLayout(null);
		
		JLabel navLabelTitle = new JLabel("BookList");
		navLabelTitle.setBounds(426, 0, 115, 50);
		navPanel.add(navLabelTitle);
		navLabelTitle.setForeground(Color.WHITE);
		navLabelTitle.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
		
		
		ImageIcon image = new ImageIcon("src/main/java/img/booklist.png");
		
		JLabel imgLogo = new JLabel(image);
		imgLogo.setBounds(364, 0, 60, 50);
		navPanel.add(imgLogo);
		
		
		JTextArea searchInput = new JTextArea();
		searchInput.setFont(new Font("Tahoma", Font.PLAIN, 16));
		searchInput.setBounds(159, 60, 291, 30);
		contentPane.add(searchInput);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 displayBookInfo(searchInput.getText().toString());
			}
		});
		btnNewButton.setBounds(460, 61, 119, 30);
		contentPane.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBackground(new Color(192, 192, 192));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 101, 572, 322);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel bookInfoPanel = new JPanel();
		bookInfoPanel.setBounds(589, 61, 272, 362);
		contentPane.add(bookInfoPanel);
		bookInfoPanel.setBackground(new Color(205, 205, 205));
		bookInfoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Book Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bookInfoPanel.setLayout(null);
		
		imgLabel = new JLabel("");
		imgLabel.setBounds(67, 27, 138, 140);
		bookInfoPanel.add(imgLabel);
		
		authorLabel = new JLabel("");
		authorLabel.setBounds(46, 203, 90, 14);
		bookInfoPanel.add(authorLabel);
		
		publisherLabel = new JLabel("");
		publisherLabel.setBounds(46, 228, 90, 14);
		bookInfoPanel.add(publisherLabel);
		
		yearPublishLabel = new JLabel("");
		yearPublishLabel.setBounds(46, 253, 90, 14);
		bookInfoPanel.add(yearPublishLabel);
		
		isbnLabel = new JLabel("");
		isbnLabel.setBounds(46, 278, 90, 14);
		bookInfoPanel.add(isbnLabel);
		
		categoryLabel = new JLabel("");
		categoryLabel.setBounds(46, 303, 90, 14);
		bookInfoPanel.add(categoryLabel);
		
		quantityLabel = new JLabel("");
		quantityLabel.setBounds(46, 328, 90, 14);
		bookInfoPanel.add(quantityLabel);
		
		lblTittle = new JLabel("");
		lblTittle.setBounds(46, 178, 75, 14);
		bookInfoPanel.add(lblTittle);
		
		JLabel lblNewLabel = new JLabel("Search book tittle or id");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 65, 139, 25);
		contentPane.add(lblNewLabel);
		
		// Listener for the selected rows.
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		  @Override
		  public void valueChanged(ListSelectionEvent e) {
			  if (!e.getValueIsAdjusting()) {
				  int selectedRow  = table.getSelectedRow();
                  if (selectedRow != -1) {
                      DefaultTableModel model = (DefaultTableModel) table.getModel();
                      bookId = (Integer) model.getValueAt(selectedRow, 0);
                      displayBookInfo(Integer.toString(bookId)); // Display the select row info.
                  }
              }
          }
		});
		
		loadTable();// Load Table List.
	}
	
	
	BookService service = new BookService();
	
	 @Override
	 public void onBookListUpdated() {
		 loadTable();
		 bookListUpdated();
		 resetInfoCard();
	 }
	
	 public void loadTable() {
		 String[] columnNames = {"ID,", "ISBN", "Tittle", "Author", "Publisher", "Published", "Category", "Available"};
		 DefaultTableModel model = new DefaultTableModel(null, columnNames);
		 try {
	            List<Book> books = service.getBooks();
	            for (Book book : books) {
	            	 Object[] row = {
	            			 book.getBookID(),
	            			 book.getIsbn(),
	            			 book.getTittle(),
	            			 book.getAuthor(),
	            			 book.getPublisher(),
	            			 book.getYearPublish(),
	            			 book.getCategory(),
	            			 book.getCopiesAvailable()
	            	 };
	            	 model.addRow(row);
	            }
	            table.setModel(model);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }
	 
	 
	 private void resetInfoCard() {
		 imgLabel.setIcon(null);
		 lblTittle.setText("");
		 authorLabel.setText("");
		 publisherLabel.setText("");
		 yearPublishLabel.setText("");
		 isbnLabel.setText("");
		 categoryLabel.setText("");
		 quantityLabel.setText("");
	 }
	 
	 // Display from the selected rows from table. Accepts id or title of the book. also used in searching books.
	 private void displayBookInfo(String search) {
		 List<Book> searchedBooks;
		 searchedBooks = service.searchedBook(search);
		 for (Book book: searchedBooks) {
			 try {
				 // Convert Image before initializing.
				 BufferedImage buffedImg = ImageUtil.convertByteArrayToBufferedImage(book.getImageData());
		         ImageIcon icon = new ImageIcon(ImageUtil.resizeImage(buffedImg, imgLabel.getWidth(), imgLabel.getHeight()));
		         
		         lblTittle.setText("Tittle: " + book.getTittle());
		         authorLabel.setText("Author: " + book.getAuthor());
		         publisherLabel.setText("Publisher: " + book.getPublisher());
		         yearPublishLabel.setText("Published: " + Integer.toString(book.getYearPublish()));
		         isbnLabel.setText("ISBN: " + book.getIsbn());
		         categoryLabel.setText("Category: "+ book.getCategory());
		         quantityLabel.setText("Quantity: "+ Integer.toString(book.getCopiesAvailable()));
		         imgLabel.setIcon(icon); 
			 } catch (IOException ex) {
		            ex.printStackTrace();
		    }
		 }
	 }
	 
	 private void bookListUpdated() {
		 if(callback != null) {
			 callback.onBookListUpdated();
		 }
	 }
}
