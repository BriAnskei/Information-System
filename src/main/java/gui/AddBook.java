package main.java.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import main.java.Callback.BookUpdateCallback;
import main.java.service.BookService;
import main.java.util.ImageUtil;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddBook extends JFrame {

    private static final long serialVersionUID = 1L;
	private BookUpdateCallback callback;
    
    private JPanel contentPane;
    private JTextField bookTittle;
    private JTextField bookISBN;
    private JTextField bookPublisher;
    private JTextField bookYearPublish;
    private JTextField bookCategory;
    private JTextField bookAuthor;
    private JTextField bookQuantity;
    private JLabel imgLabel;
    private JLabel imgPhotoLabel;
    
    private File selectedFile;

   

    /**
     * Create the frame.
     */
    public AddBook(BookUpdateCallback callback) {
    	this.callback = callback;
    	
    	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 850, 478);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(205, 205, 205));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblName = new JLabel("Tittle");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblName.setBounds(26, 58, 72, 14);
        contentPane.add(lblName);

        bookTittle = new JTextField();
        bookTittle.setBounds(26, 78, 374, 30);
        contentPane.add(bookTittle);
        bookTittle.setColumns(10);

        JLabel lblGenre = new JLabel("Author");
        lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblGenre.setBounds(26, 119, 56, 14);
        contentPane.add(lblGenre);

        JLabel lblQuantity = new JLabel("Quantity");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblQuantity.setBounds(26, 251, 56, 14);
        contentPane.add(lblQuantity);

        JLabel lblPublisher = new JLabel("Publisher");
        lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPublisher.setBounds(26, 185, 50, 14);
        contentPane.add(lblPublisher);

        bookPublisher = new JTextField();
        bookPublisher.setBounds(26, 210, 376, 30);
        contentPane.add(bookPublisher);
        bookPublisher.setColumns(10);

        JLabel lblPrice = new JLabel("Published In");
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblPrice.setBounds(26, 317, 85, 14);
        contentPane.add(lblPrice);

        JLabel lblCategory = new JLabel("Category");
        lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblCategory.setBounds(438, 317, 56, 14);
        contentPane.add(lblCategory);

        bookCategory = new JTextField();
        bookCategory.setBounds(438, 342, 372, 30);
        contentPane.add(bookCategory);
        bookCategory.setColumns(10);

        JLabel lblISBN = new JLabel("ISBN");
        lblISBN.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblISBN.setBounds(438, 251, 72, 14);
        contentPane.add(lblISBN);

        bookISBN = new JTextField();
        bookISBN.setBounds(438, 276, 375, 30);
        contentPane.add(bookISBN);
        bookISBN.setColumns(10);

        JButton btnAddBook = new JButton("Add Book");
        btnAddBook.setBackground(new Color(192, 192, 192));
        btnAddBook.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String tittle = bookTittle.getText();
        		String author = bookAuthor.getText();
        		String  publisher = bookPublisher.getText();
        		String ISBN = bookISBN.getText();
        		String category = bookCategory.getText();
        		// Exception handling for number inputs(year, quantity).
        	    try {
        	    	int yearPublish = Integer.parseInt(bookYearPublish.getText());
        	    	int quantity = Integer.parseInt(bookQuantity.getText());
        	    	 addBook(tittle, author, publisher, yearPublish, ISBN, category, quantity);
                } catch (NumberFormatException | IOException | SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid year format. Please enter a four-digit number (e.g., 2023).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        	}
        });
        btnAddBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAddBook.setBounds(26, 396, 94, 30);
        contentPane.add(btnAddBook);

        JButton btnCancel = new JButton("Browse");
        btnCancel.setBackground(new Color(192, 192, 192));
        btnCancel.setForeground(new Color(0, 0, 0));
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					imgUploadFromFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnCancel.setBounds(713, 398, 100, 30);
        contentPane.add(btnCancel);
        
        bookAuthor = new JTextField();
        bookAuthor.setColumns(10);
        bookAuthor.setBounds(26, 144, 372, 30);
        contentPane.add(bookAuthor);
        
        bookQuantity = new JTextField();
        bookQuantity.setColumns(10);
        bookQuantity.setBounds(24, 276, 376, 30);
        contentPane.add(bookQuantity);
        
        imgLabel = new JLabel("Upload image......");
        imgLabel.setForeground(new Color(0, 0, 255));
        imgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        imgLabel.setBounds(575, 396, 137, 30);
        contentPane.add(imgLabel);
        
        bookYearPublish = new JTextField();
        bookYearPublish.setColumns(10);
        bookYearPublish.setBounds(24, 342, 369, 30);
        contentPane.add(bookYearPublish);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(132, 132, 0));
        panel.setBounds(0, 0, 835, 50);
        contentPane.add(panel);
                panel.setLayout(null);
        
        JLabel navLabel = new JLabel("Add Book");
        navLabel.setBounds(435, 0, 166, 48);
        panel.add(navLabel);
        navLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 30));
        
        imgPhotoLabel = new JLabel("");
        imgPhotoLabel.setBounds(563, 86, 130, 134);
        contentPane.add(imgPhotoLabel);
    
    }
    
    
    BookService service = new BookService();
	ImageUtil imgUtil = new ImageUtil();

	
	
	private void imgUploadFromFile() throws IOException {
		selectedFile = imgUtil.imageUploadFromFile();
	   
		// Covert the selected file first to resize it and set to icon label.
		if(selectedFile != null) {
			byte[] byteImage = imgUtil.convertFileToBytes(selectedFile);
			 BufferedImage buffedImg = ImageUtil.convertByteArrayToBufferedImage(byteImage);
			 ImageIcon imageIcon = new ImageIcon(ImageUtil.resizeImage(buffedImg, imgPhotoLabel.getWidth(), imgPhotoLabel.getHeight()));
			  
			 System.out.println(imageIcon);
			 imgPhotoLabel.setIcon(imageIcon);			
			 imgLabel.setText(selectedFile.getName());
		}
		
	}
	
	
	private void addBook(String tittle, String author, String publisher, int yearPublish, String isbn, String category, int quanity) throws FileNotFoundException, IOException, SQLException {
		service.add(tittle, author, publisher, yearPublish, isbn, category, quanity, selectedFile);
		// notify other parts of the application that the member list has been updated.
		if(callback != null) {
			callback.onBookListUpdated();
		}
		 JOptionPane.showMessageDialog(null, "Book Added", "Success", JOptionPane.INFORMATION_MESSAGE);
	}
}
