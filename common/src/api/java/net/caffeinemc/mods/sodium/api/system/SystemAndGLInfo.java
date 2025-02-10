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

public class SystemAndGLInfo
{
    private static SystemAndGLInfo _instance = new SystemAndGLInfo();

    public static SystemAndGLInfo getInstance()
    {
        return _instance;
    }
    private SystemAndGLInfo()
    {

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
            return CPUName;
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


}

//LZX-Idea2023-2024-12-18-001
//LZX completed this api at 2024-12-18  11；46
