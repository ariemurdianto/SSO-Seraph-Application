package net.ariemurdianto.mule.sso.transformer;

import net.ariemurdianto.mule.sso.ConstantCollection;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class HttpToStringInput extends AbstractTransformer{

	@Override
	protected Object doTransform(Object src, String encoding)
			throws TransformerException {
		System.out.println("raw parameter: "+src);
		if(src instanceof String)
		{
			String parameters = src.toString();
			try {
				parameters = URIUtil.decode(parameters);
			} catch (URIException e1) {
				e1.printStackTrace();
			}
			System.out.println("unprocessed paramaters: "+parameters);
			parameters = parameters.replace(ConstantCollection.INSERT_PATH+"?parameters=", "");
			parameters = parameters.replace(ConstantCollection.CHECK_PATH+"?parameters=", "");
			System.out.println("processed parameter: "+parameters);
			return parameters;
		}
		return "fails";
	}
	

}
