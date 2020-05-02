package com.paranika.erp.heap_flow.common.models.dos;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
public class VendorDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	@Column(name = "vendor_id", nullable = false, length = 32)
	private String vendorId;

	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@Column(name = "search_name", nullable = true, length = 256)
	private String searchName;

	@Column(name = "gst_number", nullable = true, length = 256)
	private String gstRegNo;

	@Column(name = "pan_number", nullable = true, length = 32)
	private String panNumber;

	@Column(name = "address", nullable = true, length = 512)
	private String address;

	@Column(name = "address2", nullable = true, length = 512)
	private String address2;

	@Column(name = "city", nullable = true, length = 128)
	private String city;

	@Column(name = "state_code", nullable = true, length = 10)
	private String stateCode;

	@Column(name = "contact_person", nullable = true, length = 128)
	private String contactPerson;

	@Column(name = "phone", nullable = true, length = 128)
	private String phone;

	@Column(name = "email", nullable = true, length = 128)
	private String email;

	@OneToMany(mappedBy = "vendor")
	private Collection<IngressLedgerDO> suppliedItems = new ArrayList<IngressLedgerDO>();

	public Collection<IngressLedgerDO> getSuppliedItems() {
		return suppliedItems;
	}

	public void setSuppliedItems(Collection<IngressLedgerDO> suppliedItems) {
		this.suppliedItems = suppliedItems;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getGstRegNo() {
		return gstRegNo;
	}

	public void setGstRegNo(String gstRegNo) {
		this.gstRegNo = gstRegNo;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
