package br.com.stone.methods;

import br.com.stone.utils.StoneUseful;
import br.com.stone.xml.ReturnOfTransactionXml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import android.app.Activity;
import android.os.Bundle;

public class TransactionResponse {

	public static ReturnOfTransactionXml getTransaction(Activity activity, String xmlReceive, Bundle backActivity) {

		ReturnOfTransactionXml transaction;
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(new Class[] { ReturnOfTransactionXml.class });

		transaction = (ReturnOfTransactionXml) xstream.fromXML(xmlReceive);
		StoneUseful.clearBundle(activity);

		return transaction;
	}
}