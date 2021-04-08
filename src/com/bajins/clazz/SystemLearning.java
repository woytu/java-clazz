package com.bajins.clazz;

import sun.security.action.GetPropertyAction;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.*;

/**
 * System原生API的使用示例
 *
 * @see System
 */
public class SystemLearning {


    /**
     * 获取所在类的行信息
     *
     * @param thread
     * @return
     */
    public static String getStackTraceInfo(Thread thread) {
        if (thread == null) {
            thread = Thread.currentThread();
        }
        StackTraceElement[] es = thread.getStackTrace();
        if (es.length <= 2) {
            return "";
        }
        StringJoiner joiner = new StringJoiner("->", "", ": ");
        // 由于getStackTrace()返回自身，而getStackTraceInfo()不需要返回，所以在显示时-2
        /*for (int i = 0, len = es.length - 2; i < len; i++) {
            // 层级是自顶向下，所以输出的时候是反过来的
            StackTraceElement ei = es[es.length - i - 1];
            joiner.add(String.format("%s.%s#%d", ei.getClassName(), ei.getMethodName(), ei.getLineNumber()));
        }*/
        StackTraceElement ei = es[es.length - 1];
        joiner.add(String.format("%s.%s#%d", ei.getClassName(), ei.getMethodName(), ei.getLineNumber()));
        return joiner.toString();
    }


    /**
     * 拼接对象并打印
     *
     * @param objects
     * @return
     */
    public static void println(Object... objects) {
        StringBuilder sb = new StringBuilder(getStackTraceInfo(null));
        for (Object o : objects) {
            sb.append(o).append(" ");
        }
        System.out.println(sb.toString());
    }


    public static void main(String[] args) {
        // 从系统中检索主机名为本地主机的地址
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 获取本机名称
        String hostName = addr.getHostName();
        // 获得系统属性集
        Properties props = System.getProperties();
        // 操作系统名称
        String name = props.getProperty("os.name");
        // CPU构架
        String arch = props.getProperty("os.arch");
        // 操作系统版本
        String version = props.getProperty("os.version");
        // 用户的当前工作目录
        String dir = props.getProperty("user.dir");
        // 用户的主目录
        String home = props.getProperty("user.home");
        // 获取系统账户
        String username = System.getProperty("user.name");
        // 获取本机的IP地址
        String hostAddress = addr.getHostAddress();

        // 获取当前系统换行符
        GetPropertyAction getPropertyAction = new GetPropertyAction("line.separator");
        String lineSeparator = AccessController.doPrivileged(getPropertyAction);
        System.err.println("获取当前系统换行符：" + lineSeparator);
        System.err.println("获取当前系统换行符：" + System.getProperty("line.separator"));
        System.err.println("获取当前系统换行符：" + System.lineSeparator());

        try {
            // 获取本机MAC地址
            NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
            //ni.getInetAddresses().nextElement().getAddress();
            byte[] mac = ni.getHardwareAddress();

            String sMAC = null;
            Formatter formatter = new Formatter();
            for (int i = 0; i < mac.length; i++) {
                sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString();
            }
            System.out.println(sMAC);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        Map<String, String> getenv = System.getenv();// 获取所有变量
        System.out.println(getenv);
        Properties properties = System.getProperties();// 获取所有配置
        System.out.println(properties);
        String tmpdir = System.getProperty("java.io.tmpdir");// 系统默认缓存目录


    }
}
