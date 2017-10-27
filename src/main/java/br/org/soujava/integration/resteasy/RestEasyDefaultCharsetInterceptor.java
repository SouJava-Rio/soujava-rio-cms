package br.org.soujava.integration.resteasy;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
/**
 * If charset not given (form multipart upload), default to UTF-8 and not us-ascii (MIME RFC).
 */
@Provider
public class  RestEasyDefaultCharsetInterceptor implements ReaderInterceptor {
    // Using string value instead of constant to limit references to RestEasy (this should be possible to set through web.xml imo)
    // private static final String RESTEASY_DEFAULT_CHARSET_PROPERTY = org.jboss.resteasy.plugins.providers.multipart.InputPart.DEFAULT_CHARSET_PROPERTY;
    private static final String RESTEASY_DEFAULT_CHARSET_PROPERTY = "resteasy.provider.multipart.inputpart.defaultCharset";
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException, WebApplicationException {
        ctx.setProperty(RESTEASY_DEFAULT_CHARSET_PROPERTY, "UTF-8"); 
        return ctx.proceed();
    }
}