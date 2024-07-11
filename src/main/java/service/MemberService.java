package main.java.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import main.java.dao.MemberDAO;
import main.java.model.Member;
import main.java.util.ImageUtil;

public class MemberService {

    private MemberDAO memberDAO;
    

    public MemberService() {
        this.memberDAO = new MemberDAO();
    }

    public void addMember(int userID, String firstName, String lastName, String address, String phoneNumber, String email, File selectedFile) throws SQLException, IOException {
        Member member = new Member();
        member.setUserId(userID);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setAddress(address);
        member.setPhoneNumber(phoneNumber);
        member.setEmail(email);

        // Convert File into byte before storing in DB.
        if (selectedFile != null) {
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                byte[] imageBytes = ImageUtil.convertInputStreamToByteArray(fis);
                member.setImageData(imageBytes);
            }
        }

        memberDAO.addMember(member);
    }
    
    public void editMember(Member member, int memberId) throws SQLException {
    	memberDAO.updateMember(member, memberId);
    }
    
    public void Delete(int memberId) throws SQLException {
    	memberDAO.DeleteMemberById(memberId);
    }

    public List<Member> searchMember(String input) throws SQLException {
        return memberDAO.searchMember(input);
    }

    public List<Member> getAllMembers() throws SQLException {
        return memberDAO.getAllMembers();
    }
    
    public boolean checkMembership(int userId) {
    	return memberDAO.membershipValidation(userId);
    }
    
    public Member getMemberdetails(int userId) {
    	return memberDAO.getMemberByUserId(userId);
    }
}
