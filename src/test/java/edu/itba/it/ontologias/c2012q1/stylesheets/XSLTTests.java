package edu.itba.it.ontologias.c2012q1.stylesheets;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.io.output.NullWriter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import com.zaubersoftware.leviathan.api.engine.Pipe;
import com.zaubersoftware.leviathan.api.engine.impl.pipe.ToJavaObjectPipe;
import com.zaubersoftware.leviathan.api.engine.impl.pipe.XMLPipe;

import edu.itba.it.ontologias.c2012q1.schema.Links;
import edu.itba.it.ontologias.c2012q1.schema.Map;

/**
 * Prueba de las transformaciones  
 * 
 * 
 * @author Juan F. Codagnone
 * @since Dec 21, 2011
 */
public class XSLTTests {
    private final TransformerFactory tFactory =
            TransformerFactory.newInstance();
    private final Tidy tidy = new Tidy();
    {
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setXHTML(true);
        tidy.setErrfile("foo");
        tidy.setOnlyErrors(true);
        tidy.setErrout(new PrintWriter(new NullWriter()));
        tidy.setInputEncoding("utf-8");
        
    }

    /** carga un html de prueba */
    private Document loadDocument(final String classpath) {
        return tidy.parseDOM(getClass()
                .getResourceAsStream(classpath), 
                new NullOutputStream());
    }
    
    /** levanta un transformador xslt */
    private Pipe<Node, Node> transformer(final String classpath) {
        final InputStream stream = getClass().getResourceAsStream(classpath);
        if(stream == null) {
            throw new IllegalArgumentException("no se pudo cargar la plantilla " + classpath);
        }
        return new XMLPipe(new StreamSource(stream));
    }
    
    /** serializa en un string el contenido de un arbol DOM   */
    private String toString(final Node node) throws TransformerException, UnsupportedEncodingException {
        final Transformer transformer = tFactory.newTransformer();
        
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(os);
        transformer.transform(new DOMSource(node), result);
        return os.toString("utf-8");
    }
    
    /** prueba el template que extrae el link al profile del dueño de un blog  */
    @Test
    public final void testBlogspotToGetProfileLink() throws Exception {
        final Document n = loadDocument("blogenquantoeurespirar.blogspot.com.html"); 
        final Node out = transformer("extract_profile_link.xsl").execute(n);
        assertEquals("<?xml version='1.0' encoding='UTF-8' standalone='no'?><links xmlns='urn:itba:it:ontologias:20121q:tutorial1'><link href='http://www.blogger.com/profile/10546461224000881156'/></links>".replace("'", "\""), 
                toString(out));
        final Links link = new ToJavaObjectPipe<Links>(Links.class).execute(out);
        assertEquals("http://www.blogger.com/profile/10546461224000881156", link.getLink().get(0).getHref());
    }
    
    /** prueba el template que extrae el link al profile del dueño de un blog  */
    @Test
    public final void testBlogspotProfile() throws Exception {
        final Document n = loadDocument("profile-07191916392728529091.html");
        
        final Node out = transformer("profile.xsl").execute(n);
        System.out.println(toString(out));
        final Map map = new ToJavaObjectPipe<Map>(Map.class).execute(out);
        assertTrue(map.getEntry().size() > 1);
    }
}
