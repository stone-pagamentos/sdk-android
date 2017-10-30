package br.com.stone.methods;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import android.app.Activity;
import android.os.Bundle;
import br.com.stone.utils.StoneUseful;
import br.com.stone.xml.ReturnOfPrintXml;

public class PrintResponse {

	public static ReturnOfPrintXml getPrint(Activity activity, String xmlPrint, Bundle bundleResponse){
		
		ReturnOfPrintXml printResponse;
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(new Class[] { ReturnOfPrintXml.class });

		printResponse = (ReturnOfPrintXml) xstream.fromXML(xmlPrint);
		StoneUseful.clearBundle(activity);

		return printResponse;
	}
	
}
