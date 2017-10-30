package br.com.stone.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Cacellation")
public class ReturnOfCancellationXml {

	@XStreamAlias("ca")
	public String ca;

	@XStreamAlias("arn")
	public String arn;

	@XStreamAlias("status")
	public String status;

}
