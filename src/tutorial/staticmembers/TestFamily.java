package tutorial.staticmembers;

public class TestFamily {

    public static void main(String[] args) {
        // Instantiate 4 objects of the FamilyDemo class
        FamilyDemo father = new FamilyDemo("Antonio", "12/03/1956");
        FamilyDemo child1 = new FamilyDemo("Marco", "10/08/1985");
        FamilyDemo child2 = new FamilyDemo("Giovanni", "03/11/1989");
        FamilyDemo child3 = new FamilyDemo("Luca", "30/08/1992");

        // Just set the surname once, since it's a static member variable
        FamilyDemo.m_strSurname = "Rossi"; // Access the member variable directly from the CLASS NAME, not from the object name

        father.print();
        child1.print();
        child2.print();
        child3.print();
    }

}