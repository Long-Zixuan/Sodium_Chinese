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
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.concurrent.*;

public class SystemAndGLInfo
{
    private static SystemAndGLInfo _instance = new SystemAndGLInfo();

    public static SystemAndGLInfo getInstance()
    {
        return _instance;
    }

    private Map<String,String> mobileSocPathNumberToSocNameMap = new HashMap();

    public String doGet(String httpurl)
    {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(3000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(6000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }

    void initmobileSocPathNumberToSocNameMap()
    {
        String jsonFilePath = "/assets/sodium/soc_map/MobileSocPathNumberToName.json";

        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = this.getClass().getResourceAsStream(jsonFilePath)) {
            if (inputStream == null) {
                System.err.println("JSON 文件未找到: " + jsonFilePath);
                return;
            }

            // 使用 Jackson 将 JSON 流直接转换为 Map 对象
            mobileSocPathNumberToSocNameMap = objectMapper.readValue(inputStream, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonStr = doGet("https://gitee.com/zixuan_long/Json/raw/master/sodium/soc_map/MobileSocPathNumberToName.json");
        if(jsonStr != null)
        {
            try
            {
                objectMapper = new ObjectMapper();
                InputStream inStream = new ByteArrayInputStream(jsonStr.getBytes());
                mobileSocPathNumberToSocNameMap = objectMapper.readValue(inStream, Map.class);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //Snapdragon
        //8
        /*mobileSocPathNumberToSocNameMap.put("SM8650-AB","Snapdragon 8 Gen 3");
        mobileSocPathNumberToSocNameMap.put("SM8635","Snapdragon 8s Gen 3");
        mobileSocPathNumberToSocNameMap.put("SM8550-AB","Snapdragon 8 Gen 2");
        mobileSocPathNumberToSocNameMap.put("SM8475","Snapdragon 8+ Gen 1");
        mobileSocPathNumberToSocNameMap.put("SM8450","Snapdragon 8 Gen 1");
        mobileSocPathNumberToSocNameMap.put("SM8350-AC","Snapdragon 888+");
        mobileSocPathNumberToSocNameMap.put("SM8350","Snapdragon 888");
        mobileSocPathNumberToSocNameMap.put("SM8250-AC","Snapdragon 870");
        mobileSocPathNumberToSocNameMap.put("SM8250-AB","Snapdragon 865+");
        mobileSocPathNumberToSocNameMap.put("SM8250","Snapdragon 865");
        //7
        mobileSocPathNumberToSocNameMap.put("SM7675","Snapdragon 7+ Gen 3");
        mobileSocPathNumberToSocNameMap.put("SM7550-AB","Snapdragon 7 Gen 3");
        mobileSocPathNumberToSocNameMap.put("SM7475-AB","Snapdragon 7+ Gen 2");
        mobileSocPathNumberToSocNameMap.put("SM7450-AB","Snapdragon 7 Gen 1");
        mobileSocPathNumberToSocNameMap.put("SM7435-AB","Snapdragon 7s Gen 2");
        mobileSocPathNumberToSocNameMap.put("SM7350-AB","Snapdragon 780G");
        mobileSocPathNumberToSocNameMap.put("SM7325-AE","Snapdragon 778G+");
        mobileSocPathNumberToSocNameMap.put("SM7325","Snapdragon 778G");
        mobileSocPathNumberToSocNameMap.put("SM7250-AC","Snapdragon 768G");
        mobileSocPathNumberToSocNameMap.put("SM7150-AB-","Snapdragon 765G");
        mobileSocPathNumberToSocNameMap.put("SM7250-AB-","Snapdragon 765G");
        mobileSocPathNumberToSocNameMap.put("SM7250-AA","Snapdragon 765");
        mobileSocPathNumberToSocNameMap.put("SM7225","Snapdragon 750G");
        mobileSocPathNumberToSocNameMap.put("SM7150-AC","Snapdragon 732G");
        mobileSocPathNumberToSocNameMap.put("SM7150-AB","Snapdragon 730G");
        mobileSocPathNumberToSocNameMap.put("SM7150-AA","Snapdragon 730");
        mobileSocPathNumberToSocNameMap.put("SM7125","Snapdragon 720G");
        mobileSocPathNumberToSocNameMap.put("SDM712","Snapdragon 712");
        mobileSocPathNumberToSocNameMap.put("SDM710","Snapdragon 710");
        //MTK
        //Dimensity
        mobileSocPathNumberToSocNameMap.put("MT6989","MediaTek Dimensity 9300");
        mobileSocPathNumberToSocNameMap.put("MT6985Z","MediaTek Dimensity 9200+");
        mobileSocPathNumberToSocNameMap.put("MT6985","MediaTek Dimensity 9200");
        mobileSocPathNumberToSocNameMap.put("MT6983Z","MediaTek Dimensity 9000+");
        mobileSocPathNumberToSocNameMap.put("MT6983","MediaTek Dimensity 9000");
        mobileSocPathNumberToSocNameMap.put("MT6896ZB","MediaTek Dimensity 8200-Ultra");
        mobileSocPathNumberToSocNameMap.put("MT6896Z/CZA","MediaTek Dimensity 8200");
        mobileSocPathNumberToSocNameMap.put("MT6895Z","MediaTek Dimensity 8100");
        mobileSocPathNumberToSocNameMap.put("MT6893Z_T/CZA","MediaTek Dimensity 8050");
        mobileSocPathNumberToSocNameMap.put("MT6895","MediaTek Dimensity 8000");
        mobileSocPathNumberToSocNameMap.put("MT6886","MediaTek Dimensity 7200");
        mobileSocPathNumberToSocNameMap.put("MT6893Z","MediaTek Dimensity 1300");
        mobileSocPathNumberToSocNameMap.put("MT6893","MediaTek Dimensity 1200");
        mobileSocPathNumberToSocNameMap.put("MT6891Z","MediaTek Dimensity 1100");
        mobileSocPathNumberToSocNameMap.put("MT6885Z","MediaTek Dimensity 1000L");
        mobileSocPathNumberToSocNameMap.put("MT6883Z/CZA","MediaTek Dimensity 1000C");
        mobileSocPathNumberToSocNameMap.put("MT6889Z/CZA","MediaTek Dimensity 1000+");
        mobileSocPathNumberToSocNameMap.put("MT6889","MediaTek Dimensity 1000");
        mobileSocPathNumberToSocNameMap.put("MT6877T","MediaTek Dimensity 920");
        mobileSocPathNumberToSocNameMap.put("MT6877V/ZA","MediaTek Dimensity 900");
        mobileSocPathNumberToSocNameMap.put("MT6875","MediaTek Dimensity 820");
        mobileSocPathNumberToSocNameMap.put("MT6853V/TNZA","MediaTek Dimensity 800U");
        mobileSocPathNumberToSocNameMap.put("MT6833V","MediaTek Dimensity 810");
        mobileSocPathNumberToSocNameMap.put("MT6853V","MediaTek Dimensity 800U 5G");
        mobileSocPathNumberToSocNameMap.put("MT6873/MT6873V","MediaTek Dimensity 800");
        mobileSocPathNumberToSocNameMap.put("MT6853V/ZA","MediaTek Dimensity 720");
        mobileSocPathNumberToSocNameMap.put("MT6833V/ZA","MediaTek Dimensity 700");
        //G
        mobileSocPathNumberToSocNameMap.put("MT6789","MediaTek Helio G99");
        mobileSocPathNumberToSocNameMap.put("MT6785V/CD","MediaTek Helio G95 Premium 4G Gaming Smartphones");
        mobileSocPathNumberToSocNameMap.put("MT6785V/CC","MediaTek Helio G90T MediaTek HyperEngine Gaming");
        mobileSocPathNumberToSocNameMap.put("MT6785","MediaTek Helio G90 MediaTek HyperEngine Gaming");
        mobileSocPathNumberToSocNameMap.put("MT6769V/CZ","MediaTek Helio G85");
        mobileSocPathNumberToSocNameMap.put("MT6769V/CU","MediaTek Helio G80");*/
    }
    private SystemAndGLInfo()
    {
        CompletableFuture.runAsync(() -> {
            initmobileSocPathNumberToSocNameMap();
        });
    }

    private String getMobileSocNameWithPathNumber(String pathNumber)
    {
        if(mobileSocPathNumberToSocNameMap.containsKey(pathNumber))
        {
            return (String)mobileSocPathNumberToSocNameMap.get(pathNumber);
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
