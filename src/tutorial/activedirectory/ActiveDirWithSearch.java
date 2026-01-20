package tutorial.activedirectory;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class ActiveDirWithSearch {

    private static SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(50000);
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
                System.out.println(attrs.get("cn"));
            }
            namingEnum.close();

            // All users - similar to previous but not equal, also prints other things
            namingEnum = ctx.search("cn=users,dc=company,dc=eng", "objectclass=*", getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                System.out.println(attrs.get("cn"));
            }
            namingEnum.close();

            // All sites
            namingEnum = ctx.search("cn=Configuration,dc=company,dc=eng", "(objectclass=site)", getSimpleSearchControls());
            while (namingEnum.hasMore()) {
                SearchResult result = (SearchResult) namingEnum.next();
                Attributes attrs = result.getAttributes();
                System.out.println(attrs.get("cn"));
            }
            namingEnum.close();

            ctx.close();
        } catch (Exception e) {
            //Failed to authenticate user!
            e.printStackTrace();
        }
        System.out.println("DONE");
    }
}
