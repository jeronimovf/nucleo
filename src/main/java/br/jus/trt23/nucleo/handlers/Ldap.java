package br.jus.trt23.nucleo.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class Ldap 
{
	private static final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String SECURITY_AUTHENTICATION = "Simple";
	
	private String LDAP_USUARIO;
	private String LDAP_SENHA;
	private String LDAP_URL;	
	private String LDAP_RAIZ;

	public Ldap()
	{		
		try 
		{
			Properties properties = getProperties("/META-INF/ldap.properties");
			LDAP_USUARIO = properties.getProperty("LDAP_USUARIO");        		        		
	        LDAP_SENHA = properties.getProperty("LDAP_SENHA");
	        LDAP_URL = properties.getProperty("LDAP_URL");
	        LDAP_RAIZ = properties.getProperty("LDAP_RAIZ");
		}
		catch (IOException e) 
		{
			System.err.println("Arquivo ldap.properties não foi encontrado.");
			System.err.println(e.getMessage());
		}       
	}
	
	public boolean isUsuarioAutenticado(final String usuario, final String senha)
	{
		LdapContext ctx = null;
		LdapContext autenticado = null;
		try
		{
			ctx = getLdapContext(LDAP_USUARIO, LDAP_SENHA);
			SearchControls constraints = new SearchControls();
	        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);	        
	        constraints.setReturningAttributes(new String[]{"CN"});
	        
	        @SuppressWarnings("rawtypes")
			NamingEnumeration answer = ctx.search(LDAP_RAIZ, "sAMAccountName=" + usuario, constraints);
	       
	        if (answer.hasMore()) 
	        {
	            Attributes attrs = ((SearchResult) answer.next()).getAttributes();
	            
	            Attribute cn = attrs.get("cn");
	            autenticado = getLdapContext(cn.get().toString(), senha);
	            return true;
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());		
		}
		finally
		{
			try
			{
				if(ctx != null)
				{
					ctx.close();
				}
				if(autenticado != null)
				{
					autenticado.close();
				}
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
			}
		}
		return false;
	}	
	
	private Properties getProperties(final String nomeArquivo) throws IOException
    {
        Properties properties = new Properties();
        try
        (   InputStream file = getClass().getClassLoader().getResourceAsStream( nomeArquivo )) {
            properties.load( file );
        }
        return properties;
    }
		
    private LdapContext getLdapContext(final String cn, final String password) throws NamingException
    {
	    Hashtable<String, String> env = new Hashtable<String, String>();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
	    env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
	    env.put(Context.SECURITY_PRINCIPAL, cn);
	    env.put(Context.SECURITY_CREDENTIALS, password);
	    env.put(Context.PROVIDER_URL, LDAP_URL);
	    return new InitialLdapContext(env, null);
    }
}