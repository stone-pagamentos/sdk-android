package br.com.stone.methods;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import android.app.Activity;
import android.os.Bundle;
import br.com.stone.utils.StoneUseful;
import br.com.stone.xml.ReturnOfCancellationXml;

public class CancellationResponse {

	public static ReturnOfCancellationXml getCancellation(Activity activity, String xmlReceive, Bundle backActivity) {
		
		ReturnOfCancellationXml mReturnOfCancellationXml;
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(new Class[] { ReturnOfCancellationXml.class });
		
		mReturnOfCancellationXml = (ReturnOfCancellationXml) xstream.fromXML(xmlReceive);
		StoneUseful.clearBundle(activity);
		
		return mReturnOfCancellationXml;
	}
}
