package net.caffeinemc.mods.sodium.api.system;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.HashMap;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SystemAndGLInfo
{
    private static SystemAndGLInfo _instance = new SystemAndGLInfo();

    public static SystemAndGLInfo getInstance()
    {
        return _instance;
    }

    private Map<String,String> mobileSocPathNumberAndName = new HashMap();

    void initMobileSocPathNumberAndName()
    {
        String jsonFilePath = "/assets/sodium/soc_map/MobileSocPathNumberToName.json";

        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = this.getClass().getResourceAsStream(jsonFilePath)) {
            if (inputStream == null) {
                System.err.println("JSON 文件未找到: " + jsonFilePath);
                return;
            }

            // 使用 Jackson 将 JSON 流直接转换为 Map 对象
            mobileSocPathNumberAndName = objectMapper.readValue(inputStream, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Snapdragon
        //8
        /*mobileSocPathNumberAndName.put("SM8650-AB","Snapdragon 8 Gen 3");
        mobileSocPathNumberAndName.put("SM8635","Snapdragon 8s Gen 3");
        mobileSocPathNumberAndName.put("SM8550-AB","Snapdragon 8 Gen 2");
        mobileSocPathNumberAndName.put("SM8475","Snapdragon 8+ Gen 1");
        mobileSocPathNumberAndName.put("SM8450","Snapdragon 8 Gen 1");
        mobileSocPathNumberAndName.put("SM8350-AC","Snapdragon 888+");
        mobileSocPathNumberAndName.put("SM8350","Snapdragon 888");
        mobileSocPathNumberAndName.put("SM8250-AC","Snapdragon 870");
        mobileSocPathNumberAndName.put("SM8250-AB","Snapdragon 865+");
        mobileSocPathNumberAndName.put("SM8250","Snapdragon 865");
        //7
        mobileSocPathNumberAndName.put("SM7675","Snapdragon 7+ Gen 3");
        mobileSocPathNumberAndName.put("SM7550-AB","Snapdragon 7 Gen 3");
        mobileSocPathNumberAndName.put("SM7475-AB","Snapdragon 7+ Gen 2");
        mobileSocPathNumberAndName.put("SM7450-AB","Snapdragon 7 Gen 1");
        mobileSocPathNumberAndName.put("SM7435-AB","Snapdragon 7s Gen 2");
        mobileSocPathNumberAndName.put("SM7350-AB","Snapdragon 780G");
        mobileSocPathNumberAndName.put("SM7325-AE","Snapdragon 778G+");
        mobileSocPathNumberAndName.put("SM7325","Snapdragon 778G");
        mobileSocPathNumberAndName.put("SM7250-AC","Snapdragon 768G");
        mobileSocPathNumberAndName.put("SM7150-AB-","Snapdragon 765G");
        mobileSocPathNumberAndName.put("SM7250-AB-","Snapdragon 765G");
        mobileSocPathNumberAndName.put("SM7250-AA","Snapdragon 765");
        mobileSocPathNumberAndName.put("SM7225","Snapdragon 750G");
        mobileSocPathNumberAndName.put("SM7150-AC","Snapdragon 732G");
        mobileSocPathNumberAndName.put("SM7150-AB","Snapdragon 730G");
        mobileSocPathNumberAndName.put("SM7150-AA","Snapdragon 730");
        mobileSocPathNumberAndName.put("SM7125","Snapdragon 720G");
        mobileSocPathNumberAndName.put("SDM712","Snapdragon 712");
        mobileSocPathNumberAndName.put("SDM710","Snapdragon 710");
        //MTK
        //Dimensity
        mobileSocPathNumberAndName.put("MT6989","MediaTek Dimensity 9300");
        mobileSocPathNumberAndName.put("MT6985Z","MediaTek Dimensity 9200+");
        mobileSocPathNumberAndName.put("MT6985","MediaTek Dimensity 9200");
        mobileSocPathNumberAndName.put("MT6983Z","MediaTek Dimensity 9000+");
        mobileSocPathNumberAndName.put("MT6983","MediaTek Dimensity 9000");
        mobileSocPathNumberAndName.put("MT6896ZB","MediaTek Dimensity 8200-Ultra");
        mobileSocPathNumberAndName.put("MT6896Z/CZA","MediaTek Dimensity 8200");
        mobileSocPathNumberAndName.put("MT6895Z","MediaTek Dimensity 8100");
        mobileSocPathNumberAndName.put("MT6893Z_T/CZA","MediaTek Dimensity 8050");
        mobileSocPathNumberAndName.put("MT6895","MediaTek Dimensity 8000");
        mobileSocPathNumberAndName.put("MT6886","MediaTek Dimensity 7200");
        mobileSocPathNumberAndName.put("MT6893Z","MediaTek Dimensity 1300");
        mobileSocPathNumberAndName.put("MT6893","MediaTek Dimensity 1200");
        mobileSocPathNumberAndName.put("MT6891Z","MediaTek Dimensity 1100");
        mobileSocPathNumberAndName.put("MT6885Z","MediaTek Dimensity 1000L");
        mobileSocPathNumberAndName.put("MT6883Z/CZA","MediaTek Dimensity 1000C");
        mobileSocPathNumberAndName.put("MT6889Z/CZA","MediaTek Dimensity 1000+");
        mobileSocPathNumberAndName.put("MT6889","MediaTek Dimensity 1000");
        mobileSocPathNumberAndName.put("MT6877T","MediaTek Dimensity 920");
        mobileSocPathNumberAndName.put("MT6877V/ZA","MediaTek Dimensity 900");
        mobileSocPathNumberAndName.put("MT6875","MediaTek Dimensity 820");
        mobileSocPathNumberAndName.put("MT6853V/TNZA","MediaTek Dimensity 800U");
        mobileSocPathNumberAndName.put("MT6833V","MediaTek Dimensity 810");
        mobileSocPathNumberAndName.put("MT6853V","MediaTek Dimensity 800U 5G");
        mobileSocPathNumberAndName.put("MT6873/MT6873V","MediaTek Dimensity 800");
        mobileSocPathNumberAndName.put("MT6853V/ZA","MediaTek Dimensity 720");
        mobileSocPathNumberAndName.put("MT6833V/ZA","MediaTek Dimensity 700");
        //G
        mobileSocPathNumberAndName.put("MT6789","MediaTek Helio G99");
        mobileSocPathNumberAndName.put("MT6785V/CD","MediaTek Helio G95 Premium 4G Gaming Smartphones");
        mobileSocPathNumberAndName.put("MT6785V/CC","MediaTek Helio G90T MediaTek HyperEngine Gaming");
        mobileSocPathNumberAndName.put("MT6785","MediaTek Helio G90 MediaTek HyperEngine Gaming");
        mobileSocPathNumberAndName.put("MT6769V/CZ","MediaTek Helio G85");
        mobileSocPathNumberAndName.put("MT6769V/CU","MediaTek Helio G80");*/
    }
    private SystemAndGLInfo()
    {
        initMobileSocPathNumberAndName();
    }

    private String getMobileSocNameWithPathNumber(String pathNumber)
    {
        if(mobileSocPathNumberAndName.containsKey(pathNumber))
        {
            return (String)mobileSocPathNumberAndName.get(pathNumber);
        }
        return pathNumber;
    }

    public String getCPUInfo()
    {
        /*String CPUInfo = "Unknown";
        String OSInfo = System.getProperty("os.name")+" "+System.getProperty("os.version");

        String os = System.getProperty("os.name").toLowerCase();
        try
        {
            // 使用 Runtime 类的 exec 方法执行系统命令
            String command;
            if (os.contains("win"))
            {
                command = "wmic cpu get name";
            }
            else if (os.contains("mac"))
            {
                command = "sysctl -n machdep.cpu.brand_string";
            }
            else if (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0)
            {
                command = "lscpu";
                if(os.contains("andr") || os.contains("harm"))//安卓也是Linux
                {
                    command = "cat /proc/cpuinfo";
                }
            }
            else if(os.contains("andr") || os.contains("harm"))
            {
                command = "cat /proc/cpuinfo";
            }
            else
            {
                command = "unknown";
            }

            // 执行命令并获取输出
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> result = new ArrayList<String>();
            while ((line = reader.readLine()) != null)
            {
                result.add(line.trim());
            }
            System.out.println("CPU信息："+ result.get(2));
            CPUInfo = result.get(2);
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return System.getProperty("os.arch") + " based CPU";
        }
        if(!os.contains("win"))
        {
            CPUInfo = System.getProperty("os.arch") + " based CPU";
        }
        return CPUInfo;*/
        try
        {
            String CPUName = "";
            // 创建 SystemInfo 实例
            SystemInfo systemInfo = new SystemInfo();

            // 获取硬件抽象层
            HardwareAbstractionLayer hardware = systemInfo.getHardware();

            // 获取 CPU 信息
            CentralProcessor processor = hardware.getProcessor();

            CPUName = processor.getProcessorIdentifier().getName();

            // 输出 CPU 名称
            System.out.println("CPU 名称: " + CPUName);

            if(CPUName == null || CPUName.equals(""))
            {
                return System.getProperty("os.arch") + " based CPU";
            }
            return getMobileSocNameWithPathNumber(CPUName);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return System.getProperty("os.arch") + " based CPU";
        }
    }

    public String getGLVersion()
    {
        String glInfo = "Unknown";
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            // 获取OpenGL版本
            String version = GL11.glGetString(GL11.GL_VERSION);

            System.out.println("OpenGL Version: " + version);
            glInfo = version;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Unknown";
        }
        return glInfo;
    }

    public String getOSInfo()
    {
        return System.getProperty("os.name")+" "+System.getProperty("os.version");
    }

    public boolean isUsingPojavLauncher() {
        if (System.getenv("POJAV_RENDERER") != null) {
            //System.out.println("Detected presence of environment variable POJAV_LAUNCHER, which seems to indicate we are running on Android");

            return true;
        }

        var librarySearchPaths = System.getProperty("java.library.path", null);

        if (librarySearchPaths != null) {
            for (var path : librarySearchPaths.split(":")) {
                if (isKnownAndroidPathFragment(path)) {
                   // System.out.println("Found a library search path which seems to be hosted in an Android filesystem: {}", path);

                    return true;
                }
            }
        }

        /*var workingDirectory = System.getProperty("user.home", null);

        if (workingDirectory != null) {
            if (isKnownAndroidPathFragment(workingDirectory)) {
                System.out.println("Working directory seems to be hosted in an Android filesystem: {}", workingDirectory);
            }
        }*/
        return false;
    }

    public boolean isKnownAndroidPathFragment(String path) {
        return path.matches("/data/user/[0-9]+/net\\.kdt\\.pojavlaunch");
    }

}

//LZX-Idea2023-2024-12-18-001
//LZX completed this api at 2024-12-18  11；46
