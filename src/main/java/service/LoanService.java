package main.java.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import main.java.dao.LoanDAO;
import main.java.model.Loan;

public class LoanService {

	private LoanDAO loanDAO;
	
	public LoanService() {
		this.loanDAO = new LoanDAO();
	}
	
	public boolean issue(String bookId, String memberId, Date issueDate, Date dueDate) throws SQLException {
		Loan loan = new Loan();
		loan.setBookID(Integer.parseInt(bookId));
		loan.setMemberID(Integer.parseInt(memberId));
		loan.setIssueDate(issueDate);
		loan.setDueDate(dueDate);
		
		return loanDAO.issueBook(loan);
	}
	
	public boolean Return(String loanId, String bookId, String memberId, Date issueDate, Date dueDate, Date returnDate) throws SQLException {
		int bId = Integer.parseInt(bookId);
		int mId = Integer.parseInt(memberId);
		int lId = Integer.parseInt(loanId);
		Loan loan = new Loan(lId, bId, mId, issueDate, dueDate, returnDate);
		return loanDAO.returnBook(loan);
	}
	
	// User for admin only, return unreturned books.
	public List<Loan> getLoans() throws SQLException {
		return loanDAO.getAllLoans();
	}
	
	// get all the unreturned loans by member id.
	public List<Loan> getUserLoan(int memberID) {
		return loanDAO.getLoansByMemberId(memberID);
	}
	
	
	// Get all the returned books by member id.
	public List<Loan> getretunedLoans(int memberID) {
		return loanDAO.getLoansByMemberIdWithReturnDate(memberID);
	}
	
	
	public List<Loan> getAllReturnedBooks() {
		return loanDAO.getLoansWithReturnedDate();
	}
	
	//Also used for  returning book
	public Loan searchLoan(String loanId) throws SQLException {
		return loanDAO.getLoanById(loanId);
	}
	
	public boolean memberIssueCheck(int memberId) {
		return loanDAO.countIssuedBooksByMemberId(memberId) > 0;
	}
	
	
}
