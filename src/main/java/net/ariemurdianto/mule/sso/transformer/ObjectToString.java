package net.ariemurdianto.mule.sso.transformer;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class ObjectToString extends AbstractTransformer{

	@Override
	protected Object doTransform(Object src, String encoding)
			throws TransformerException {
		System.out.println("this is the object to transform: "+src.toString());
		return src.toString();
	}

}
