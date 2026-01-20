package tutorial.staticmembers;

public class FamilyDemo {

    public static String m_strSurname;
    private final String m_strName;
    private final String m_strBirthDate;

    FamilyDemo(String name, String birthDate) {
        m_strName = name;
        m_strBirthDate = birthDate;
    }

    public void print() {
        System.out.println(m_strName + " " +
                m_strSurname + " born on " + m_strBirthDate);
    }

}