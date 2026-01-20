package tutorial.activedirectory;

import javax.naming.directory.SearchControls;
import javax.naming.ldap.LdapContext;

public class ActiveDir {

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

            LdapContext ctx = ActiveDirectoryFacade.getConnection("", "");

            ctx.lookup("dc=company,dc=eng");

            // NamingEnumeration<?> namingEnum = ctx.search("dc=company,dc=eng", "(objectclass=user)", getSimpleSearchControls());

            ctx.close();
        } catch (Exception e) {
            //Failed to authenticate user!
            e.printStackTrace();
        }
        System.out.println("DONE");
    }
}
