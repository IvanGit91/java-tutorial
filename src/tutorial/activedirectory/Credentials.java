package tutorial.activedirectory;

import com.sun.security.auth.module.NTSystem;

public class Credentials {

    public static void main(String[] args) {

        NTSystem ntSystem = new NTSystem();
        System.out.println(ntSystem.getName());
        System.out.println(ntSystem.getDomain());
        System.out.println(ntSystem.getUserSID());

    }

}
