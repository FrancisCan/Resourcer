import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * @author Francesco Cannizzaro
 */
public class MainTest {

    Main m;

    private void print(String key) {
        System.out.print(" - Binding \"" + key + " valid\n");
    }

    @Before
    public void before() throws Exception {
        m = new Main();
    }

    @Test
    public void testApp() throws Exception {
        Assert.assertEquals(m.app, "Sample");
        print("app");
    }

    @Test
    public void testAppVersion() throws Exception {
        Assert.assertEquals(m.appVersion, "1.0");
        print("appVersion");
    }

    @Test
    public void testPrimary() throws Exception {
        Assert.assertEquals(m.primary, Color.decode("#3f51b5"));
        print("primary");
    }

    @Test
    public void testPrimaryDark() throws Exception {
        Assert.assertEquals(m.primaryDark, Color.decode("#303f9f"));
        print("primaryDark");
    }

    @Test
    public void testAccent() throws Exception {
        Assert.assertEquals(m.accent, Color.decode("#e91e63"));
        print("accent");
    }

    @Test
    public void testObject() throws Exception {
        Assert.assertEquals(m.object.toString(), "{\"body\":{\"nested\":{\"number\":5,\"string\":\"Test\"},\"something\":[\"item\"]}}");
        print("object");
    }

    @Test
    public void testString() throws Exception {
        Assert.assertEquals(m.string, "Test");
        print("string");
    }

    @Test
    public void testSomething() throws Exception {
        Assert.assertEquals(m.something.toString(), "[\"item\"]");
        print("something");
    }

    @Test
    public void testArray() throws Exception {
        Assert.assertEquals(m.array.toString(), "[\"item1\",\"item2\",\"item3\",\"item4\",\"item5\",\"item6\",\"item7\",\"item8\",\"item9\",\"item10\"]");
        print("array");
    }

    @Test
    public void testWidth() throws Exception {
        Assert.assertEquals(m.width, 500);
        print("accent");
    }

    @Test
    public void testHeight() throws Exception {
        Assert.assertEquals(m.height, 300);
        print("height");
    }

}