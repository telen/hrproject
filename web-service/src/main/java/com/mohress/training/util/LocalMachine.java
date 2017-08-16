package com.mohress.training.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author carson.li(liyong)
 */
public final class LocalMachine {

    private static String localMachine;

    static {
        //获取本地IP
        try {
            String ip = getAddress().getLocalHost().getHostAddress();
            //截取最后一个.之后的数字
            localMachine = ip.substring(ip.lastIndexOf(".") + 1);
        } catch (Throwable ignore) {
            localMachine = String.valueOf(System.currentTimeMillis() % (1000 * 60 * 60 * 24 * 7));
        }
    }

    private static InetAddress getAddress() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements();) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    return addresses.nextElement();
                }
            }
        } catch (SocketException ignore) {
        }
        return null;
    }

    public static String getMachine() {
        return localMachine;
    }

}
