package main.java.model;

import java.util.Date;

public class Loan {
    private int loanID;
    private int bookID;
    private int memberID;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;
 

    // Constructor
    public Loan() {
    }

    public Loan(int loanID, int bookID, int memberID, Date issueDate, Date dueDate, Date returnDate) {
        this.loanID = loanID;
        this.bookID = bookID;
        this.memberID = memberID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
  


    // toString method (optional, for easy printing)
    @Override
    public String toString() {
        return "Loan{" +
                "loanID=" + loanID +
                ", bookID=" + bookID +
                ", memberID=" + memberID +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
