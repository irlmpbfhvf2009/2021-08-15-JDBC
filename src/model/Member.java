package model;

import java.io.Serializable;

public class Member implements Serializable{
	private static final long serialVersionUID = 1L;

	private int Id;                 
	private String Hospitalname;
	private String Principal;
	private String Address;
	private String Phone;
	
	public int getMemberId() {
		return Id;
	}
	public void setMemberId(int memberId) {
		this.Id = memberId;
	}
	public String getMemberHospitalname() {
		return Hospitalname;
	}
	public void setMemberHospitalname(String memberHospitalname) {
		this.Hospitalname = memberHospitalname;
	}
	public String getMemberPrincipal() {
		return Principal;
	}
	public void setMemberPrincipal(String memberPrincipal) {
		this.Principal = memberPrincipal;
	}
	public String getMemberAddress() {
		return Address;
	}
	public void setMemberAddress(String memberAddress) {
		this.Address = memberAddress;
	}
	public String getMemberPhone() {
		return Phone;
	}
	public void setMemberPhone(String memberPhone) {
		this.Phone = memberPhone;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
