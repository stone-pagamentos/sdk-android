package br.com.stone.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Transaction")
public class ReturnOfTransactionXml {

	@XStreamAlias("flag")
	public String flag;

	@XStreamAlias("arn")
	public String arn;

	@XStreamAlias("amount")
	public String amount;

	@XStreamAlias("date")
	public String date;

	@XStreamAlias("ca")
	public String ca;

	@XStreamAlias("parcel")
	public String parcel;

	@XStreamAlias("status")
	public String status;
	
	@XStreamAlias("amountOfInstallments")
	public String amountOfInstallments;
	
	@XStreamAlias("transactionType")
	public String transactionType;

	@XStreamAlias("demandId")
	public long demandId;
	
	@XStreamAlias("emailSent")
	public String emailSent;
	
}