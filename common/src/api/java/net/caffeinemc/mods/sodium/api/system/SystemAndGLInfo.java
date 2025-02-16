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

            mobileSocPathNumberAndName = objectMapper.readValue(inputStream, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
