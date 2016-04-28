package MARC.Administrator;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import MARC.myjavaproject.main;

/**
 *
 * @author PARTHO
 */
public class UserAccInfoPassword {

        public static boolean GUI_disabled = false;
    public static boolean Systray_disabled = false;
    public static String passWord = "marc";

    public static void loadPassword() {
        if (new File(main.PASS_CONFIG_FILE).canRead())
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(main.PASS_CONFIG_FILE));
//                GUI_disabled = Boolean.valueOf(properties.getProperty("GUI-disabled"));
//                Systray_disabled = Boolean.valueOf(properties.getProperty("Systray-disabled"));
                passWord = properties.getProperty("UserInfoPassword").toString();
            }
            catch (Exception e) {
                e.getStackTrace();
            }
       else
            storePassword();
    }

    public static void storePassword () {
        try {
            new File(main.PASS_CONFIG_FILE).createNewFile();
            Properties properties = new Properties();
//            properties.put("GUI-disabled", String.valueOf(GUI_disabled));
//            properties.put("Systray-disabled", String.valueOf(Systray_disabled));
            properties.put("UserInfoPassword", passWord);
            properties.store(new FileOutputStream(main.PASS_CONFIG_FILE),
                "MARC Password file");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


}
