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
//import com.fasterxml.jackson.databind.ObjectMapper;

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

import java.util.Scanner;

public class SystemAndGLInfo
{
    private static SystemAndGLInfo _instance = new SystemAndGLInfo();

    public static SystemAndGLInfo getInstance()
    {
        return _instance;
    }

    private Map<String,String> mobileSocPathNumberToSocNameMap;

    public Map convertJsonToMap(String jsonString) {
        Map<String, Object> map = new HashMap<>();
        if (jsonString == null || jsonString.isEmpty()) {
            return map;
        }

        // 去掉首尾的花括号
        jsonString = jsonString.trim();
        if (!jsonString.startsWith("{") || !jsonString.endsWith("}")) {
            throw new IllegalArgumentException("Invalid JSON string");
        }
        jsonString = jsonString.substring(1, jsonString.length() - 1).trim();

        // 按逗号分隔键值对
        String[] pairs = jsonString.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.trim().split(":");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("Invalid key-value pair: " + pair);
            }

            String key = parseString(keyValue[0].trim());
            Object value = parseValue(keyValue[1].trim());
            map.put(key, value);
        }

        return map;
    }

    /**
     * 解析字符串类型的值（去掉引号）
     *
     * @param str 字符串
     * @return 去掉引号后的字符串
     */
    private String parseString(String str) {
        if (str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    /**
     * 解析JSON中的值
     *
     * @param valueStr 值的字符串表示
     * @return 解析后的值
     */
    private Object parseValue(String valueStr) {
        if (valueStr.equalsIgnoreCase("true") || valueStr.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(valueStr);
        } else if (valueStr.matches("-?\\d+")) { // 整数
            return Integer.parseInt(valueStr);
        } else if (valueStr.matches("-?\\d*\\.\\d+")) { // 浮点数
            return Double.parseDouble(valueStr);
        } else if (valueStr.startsWith("\"") && valueStr.endsWith("\"")) { // 字符串
            return parseString(valueStr);
        } else if (valueStr.equalsIgnoreCase("null")) {
            return null;
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + valueStr);
        }
    }

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
        // ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = this.getClass().getResourceAsStream(jsonFilePath)) {
            if (inputStream == null) {
                System.err.println("JSON 文件未找到: " + jsonFilePath);
                return;
            }
            byte[] bytes = new byte[0];
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            String jsStr = new String(bytes);

            //json对象转Map
            mobileSocPathNumberToSocNameMap = convertJsonToMap(jsStr);
            //mobileSocPathNumberToSocNameMap = objectMapper.readValue(inputStream, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonStr = doGet("https://gitee.com/zixuan_long/Json/raw/master/sodium/soc_map/MobileSocPathNumberToName.json");
        if(jsonStr != null)
        {
            try
            {

                mobileSocPathNumberToSocNameMap = convertJsonToMap(jsonStr);
                //objectMapper = new ObjectMapper();
                //InputStream inStream = new ByteArrayInputStream(jsonStr.getBytes());
                //mobileSocPathNumberToSocNameMap = objectMapper.readValue(inStream, Map.class);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
