package tutorial.activedirectory;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class ActiveDirWithSearch2 {

    private static SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(50000);
        //String[] attrIDs = {"objectGUID"};
        //searchControls.setReturningAttributes(attrIDs);
        return searchControls;
    }

    public static void main(String[] args) {
        try {

            String user = "pcosmo";
            String psw = "testpassword";

            LdapContext ctx = ActiveDirectoryFacade.getConnection(user, psw);

            ctx.setRequestControls(null);

            // All users
            NamingEnumeration<?> namingEnum = ctx.search("dc=company,dc=eng", "(objectclass=user)", getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
//	            System.out.println(attrs.get("sAMAccountName"));
                if (attrs.get("sAMAccountName").equals("sAMAccountName: ibattimiello"))
                    System.out.println(attrs.get("userPrincipalName"));
            }
            namingEnum.close();

            // All users - similar to previous but not equal, also prints other things
            namingEnum = ctx.search("CN=John Doe,CN=Users,DC=company,DC=eng", "(objectclass=user)", getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                System.out.println(attrs.get("cn"));
            }
            namingEnum.close();


            namingEnum = ctx.search("CN=Users,DC=company,DC=eng", "(sAMAccountName=ibattimiello)", getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                System.out.println(attrs.get("cn"));
            }
            namingEnum.close();

            String name = "johndoe";
            String mail = null;
            namingEnum = ctx.search("CN=Users,DC=company,DC=eng", "(sAMAccountName=" + name + ")", getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                System.out.println(attrs.get("mail").toString());
                mail = attrs.get("mail").toString();
            }
            namingEnum.close();


            mail = mail.replaceAll("mail: ", "");

            System.out.println(mail);


            ctx.close();
        } catch (Exception e) {
            //Failed to authenticate user!
            e.printStackTrace();
        }
        System.out.println("DONE");
    }
}
