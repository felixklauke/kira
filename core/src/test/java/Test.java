import de.felixklauke.kira.core.Kira;
import de.felixklauke.kira.core.KiraFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Test {

  public static void main(String[] args) {

    Kira kira = KiraFactory.createKira();

    TestModel audrey = new TestModel("Audrey",new HashMap<>());

    TestModel peter = new TestModel("Peter", new HashMap<>() {{ put("Audrey", audrey); }});
    TestModel paul = new TestModel("Paul", new HashMap<>());

    Map<String, TestModel> modelMap = new HashMap<>();
    modelMap.put("peter", peter);
    modelMap.put("paul", paul);

    TestModel testModel = new TestModel("Celine", modelMap);

    Map<String, Object> serialize = kira.serialize(testModel);
    System.out.println(serialize);

    TestModel deserialize = kira.deserialize(serialize, TestModel.class);
    System.out.println(deserialize);

    Map<String, TestModel> testModels = deserialize.getTestModels();
    System.out.println(testModels.get("peter").getClass());
    System.out.println(testModels.get("peter").getTestModels().get("Audrey").getClass());
  }


  public static final class TestModel {

    private String name;
    private Map<String, TestModel> testModels;

    public TestModel() {
    }

    public TestModel(String name, Map<String, TestModel> testModels) {
      this.name = name;
      this.testModels = testModels;
    }

    public Map<String, TestModel> getTestModels() {
      return testModels;
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", TestModel.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("testModels=" + testModels)
        .toString();
    }
  }
}
