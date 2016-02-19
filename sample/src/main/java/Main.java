import com.github.fcannizzaro.resourcer.Resourcer;
import com.github.fcannizzaro.resourcer.annotations.ColorRes;
import com.github.fcannizzaro.resourcer.annotations.IntegerRes;
import com.github.fcannizzaro.resourcer.annotations.Json;
import com.github.fcannizzaro.resourcer.annotations.StringRes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;

/**
 * @author Francesco Cannizzaro
 */
public class Main {

    @ColorRes
    Color primary;

    @ColorRes("primary_dark")
    Color primaryDark;

    @ColorRes("pink_accent")
    Color accent;

    @Json("object")
    JSONObject object;

    @Json("object.body.nested.string")
    String string;

    @Json("object.body.something")
    JSONArray something;

    @Json("array")
    JSONArray array;

    @StringRes()
    String app;

    @StringRes("version")
    String appVersion;

    @IntegerRes()
    int width;

    @IntegerRes()
    int height;

    public Main() {

        new Resourcer.Builder("src" + File.separator + "main" + File.separator + "res").build();

        Resourcer.bind(this);

    }

    public static void main(String[] args) {

        Main m = new Main();

        // colors
        System.out.println(" --- COLORS --- ");
        System.out.println("primary: " + m.primary);
        System.out.println("primary dark: " + m.primaryDark);
        System.out.println("accent: " + m.accent);

        // json
        System.out.println("\n --- JSON --- ");
        System.out.println("object: " + m.object.toString());
        System.out.println("string: " + m.string);
        System.out.println("something: " + m.something.toString());
        System.out.println("array: " + m.array.toString());


        // values
        System.out.println("\n --- VALUES --- ");
        System.out.println("app: " + m.app);
        System.out.println("version: " + m.appVersion);
        System.out.println("width: " + m.width);
        System.out.println("height: " + m.height);

    }
}
