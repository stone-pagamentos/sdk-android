package br.com.stone.objects;

public final class Transaction {

	private String  amount;
	private Integer typeOfPurchase;
	private Integer numberOfInstalments;
	private Integer typeInstalment;
	private Integer demandId;
	private boolean isNeededConfirm;

	public static int DEBIT  = 1;
	public static int CREDIT = 2;

	public static int ONE_INSTALMENT = 0;
	public static int MERCHANT = 1;

	public Integer getTypeInstalment() {
		return typeInstalment;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Integer getTypeOfPurchase() {
		return typeOfPurchase;
	}

	public void setTypeOfPurchase(Integer typeOfPurchase) {
		this.typeOfPurchase = typeOfPurchase;
	}

	public Integer getNumberOfInstalments() {
		return numberOfInstalments;
	}

	public void setNumberOfInstalments(Integer numberOfInstalments) {
		this.numberOfInstalments = numberOfInstalments;
	}

	public Integer getTypeInstalments() {
		return typeInstalment;
	}

	public void setTypeOfInstalment(Integer typeParcels) {
		this.typeInstalment = typeParcels;
	}

	public Integer getDemandId() {
		return demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

	public boolean isNeededConfirm() {
		return isNeededConfirm;
	}

	public void setNeededConfirm(boolean isNeededConfirm) {
		this.isNeededConfirm = isNeededConfirm;
	}

	public String toString() {

		return "Transaction:"
				+ "\nAmount..............: " + getAmount()
				+ "\nTypeOfPurchase......: " + getTypeOfPurchase()
				+ "\nNumberOfInstalments.: " + getNumberOfInstalments()
				+ "\nInstalmentsType.....: " + getTypeOfPurchase()
				+ "\nDemandId............: " + getDemandId()
				+ "\nAutoSending.........: " + isNeededConfirm();
	}

}
