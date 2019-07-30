import java.util.ArrayList;
import java.util.List;

public class Test {

  public static void main(String[] args) {

    List<String> stringList = new ArrayList<>();
    stringList.add("Hallo");

    System.out.println(stringList);

    addToList(stringList, "Welt");

    System.out.println(stringList);
  }

  public static void addToList(List<String> stringList, String element) {


  }
}
