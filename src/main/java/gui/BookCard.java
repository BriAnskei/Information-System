package main.java.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.model.Book;
import main.java.service.*;
import main.java.util.ImageUtil;

import javax.swing.JLabel;

public class BookCard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String bookId;
	
	private  JLabel tittleLabel;
	private JLabel authorLabel;
	private JLabel publisherLabel ;
	private JLabel yearPublishLabel;
	private JLabel isbnLabel;
	private JLabel categoryLabel;
	private JLabel imgLabel;
	


	public BookCard(String bookId) {
		this.bookId = bookId;
		
		System.out.print(bookId);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 362, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		imgLabel = new JLabel("");
		imgLabel.setBounds(10, 11, 180, 289);
		contentPane.add(imgLabel);
		
		tittleLabel = new JLabel("text");
		tittleLabel.setBounds(200, 36, 90, 14);
		contentPane.add(tittleLabel);
		
		JLabel lblTittle = new JLabel("Tittle");
		lblTittle.setBounds(200, 11, 90, 14);
		contentPane.add(lblTittle);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setBounds(200, 61, 90, 14);
		contentPane.add(lblAuthor);
		
		authorLabel = new JLabel("text");
		authorLabel.setBounds(200, 86, 90, 14);
		contentPane.add(authorLabel);
		
		JLabel lblPublisher = new JLabel("publisher");
		lblPublisher.setBounds(200, 111, 90, 14);
		contentPane.add(lblPublisher);
		
		publisherLabel = new JLabel("text");
		publisherLabel.setBounds(200, 136, 90, 14);
		contentPane.add(publisherLabel);
		
		JLabel lblYearPublish = new JLabel("Year Publish");
		lblYearPublish.setBounds(200, 161, 90, 14);
		contentPane.add(lblYearPublish);
		
		yearPublishLabel = new JLabel("text");
		yearPublishLabel.setBounds(200, 186, 90, 14);
		contentPane.add(yearPublishLabel);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(200, 211, 90, 14);
		contentPane.add(lblIsbn);
		
		isbnLabel = new JLabel("text");
		isbnLabel.setBounds(200, 236, 90, 14);
		contentPane.add(isbnLabel);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setBounds(200, 261, 90, 14);
		contentPane.add(lblCategory);
		
		categoryLabel = new JLabel("text");
		categoryLabel.setBounds(200, 286, 90, 14);
		contentPane.add(categoryLabel);
		
		fillBookDetails();
	}

	BookService service = new BookService();
	
	private void fillBookDetails() {
		 List<Book> searchedBooks;
		 searchedBooks = service.searchedBook(bookId);
		 for (Book book: searchedBooks) {
			 try {
				 // Convert Image before initializing.
				 BufferedImage buffedImg = ImageUtil.convertByteArrayToBufferedImage(book.getImageData());
		         ImageIcon icon = new ImageIcon(ImageUtil.resizeImage(buffedImg, imgLabel.getWidth(), imgLabel.getHeight()));
		         
		         tittleLabel.setText(book.getTittle());
		         authorLabel.setText(book.getAuthor());
		         publisherLabel.setText(book.getPublisher());
		         yearPublishLabel.setText(Integer.toString(book.getYearPublish()));
		         isbnLabel.setText(book.getIsbn());
		         categoryLabel.setText(book.getCategory());
		       
		         imgLabel.setIcon(icon); 
			 } catch (IOException ex) {
		            ex.printStackTrace();
		    }
		 }
	}

}
