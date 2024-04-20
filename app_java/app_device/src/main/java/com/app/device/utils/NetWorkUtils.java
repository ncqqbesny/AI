package com.app.device.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class NetWorkUtils {
    private final static Logger log = LoggerFactory.getLogger(NetWorkUtils.class);

    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                System.out.println("getName获得网络设备现实名称=" + networkInterface.getName());
                System.out.println("getDisplayName获得网络设备现实名称=" + networkInterface.getDisplayName());
                System.out.println("getIndex获得网络接口的索引=" + networkInterface.getIndex());
                System.out.println("isUp是否已经开启并运行=" + networkInterface.isUp());
                System.out.println("isLoopback是否为回调接口=" + networkInterface.isLoopback());
                System.out.println("getMTU获得最大传输单元=" + networkInterface.getMTU());
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    System.out.println("获取此IP地址的完全限定域名=" + inetAddress.getCanonicalHostName());
                    System.out.println("获取此IP地址的主机名=" + inetAddress.getHostName());
                    System.out.println("获取此IP地址的字符串=" + inetAddress.getHostAddress());
                    System.out.print("getAddress返回此InetAddress对象的原始IP地址=");
                    byte[] addressByte = inetAddress.getAddress();
                    for (byte b : addressByte) {
                        System.out.print(b + " ");
                    }

                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    private static InetAddress getLocalHost() throws UnknownHostException {
        return Inet4Address.getLocalHost();
    }

    /**
     * 获取本机IP
     *
     * @return
     * @throws UnknownHostException
     */
    public static String getHostAddress() throws UnknownHostException {
        return getLocalHost().getHostAddress();
    }

    /**
     * 获取本机子网掩码
     *
     * @return
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static String getMaskAddress() throws UnknownHostException, SocketException {
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(getLocalHost());
        String maskAddress = "";
        for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
            InetAddress broadcast = address.getBroadcast();
            if (broadcast instanceof Inet4Address) {
                short mask = address.getNetworkPrefixLength();
                maskAddress = getMaskAddress(mask);
            }
        }
        return maskAddress;
    }

    /**
     * 判断是否同一网段
     *
     * @param sourceAddress
     * @return
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static boolean isLocalAreaNetwork(String sourceAddress) throws UnknownHostException, SocketException {
        int sourceValue = getAddressValue(sourceAddress);
        Map<String, List<String>> allAddressMap = getAllAddressMap();
        for (Map.Entry<String, List<String>> entry : allAddressMap.entrySet()) {
            String maskAddress = entry.getKey();
            int maskValue = getAddressValue(maskAddress);
            for (String hostAddress : entry.getValue()) {
                int hostValue = getAddressValue(hostAddress);
                boolean equal = (maskValue & hostValue) == (maskValue & sourceValue);
                log.debug("boolean:{} mask:{} local:{} source:{}", equal, maskAddress, hostAddress, sourceAddress);
                if (equal) {
                    return equal;
                }
            }
        }

        return false;
    }

    private static String getMaskAddress(short maskLength) {
        String[] maskIps = new String[4];
        for (int i = 0; i < maskIps.length; i++) {
            int node = (maskLength >= 8) ? 255 : (maskLength > 0 ? (maskLength & 0xff) : 0);
            maskIps[i] = String.valueOf(node);
            maskLength -= 8;
        }
        return String.join(".", maskIps);

    }

    private static Map<String, List<String>> hostAddressMapping;

    private static Map<String, List<String>> getAllAddressMap() throws SocketException, UnknownHostException {
        if (null != hostAddressMapping) {
            return hostAddressMapping;
        }
        log.debug("初始化当前主机子网掩码和IP的关系");
        hostAddressMapping = new HashMap<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        List<InterfaceAddress> addressList = new ArrayList<>();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface nextElement = networkInterfaces.nextElement();
            for (InterfaceAddress address : nextElement.getInterfaceAddresses()) {
                InetAddress broadcast = address.getBroadcast();
                if (broadcast instanceof Inet4Address) {
                    addressList.add(address);
                }
            }
        }
        Map<Short, List<InterfaceAddress>> groupAddress = addressList.stream()
                .collect(Collectors.groupingBy(InterfaceAddress::getNetworkPrefixLength));
        for (Map.Entry<Short, List<InterfaceAddress>> entry : groupAddress.entrySet()) {
            List<String> list = entry.getValue().stream().map(a -> a.getAddress().getHostAddress())
                    .collect(Collectors.toList());
            hostAddressMapping.put(getMaskAddress(entry.getKey()), list);
        }
        log.debug("初始化关系完成");
        return hostAddressMapping;
    }

    private static int getAddressValue(String address) {
        byte[] addr = getAddressBytes(address);
        int address1 = addr[3] & 0xFF;
        address1 |= ((addr[2] << 8) & 0xFF00);
        address1 |= ((addr[1] << 16) & 0xFF0000);
        address1 |= ((addr[0] << 24) & 0xFF000000);
        return address1;
    }

    private static byte[] getAddressBytes(String address) {
        String[] addrs = address.split("\\.");
        int length = addrs.length;
        byte[] addr = new byte[length];
        for (int index = 0; index < length; index++) {
            addr[index] = (byte) (Integer.valueOf(addrs[index]) & 0xff);
        }
        return addr;
    }

    public static void exchangeToLocalIp() throws Exception {
        Runtime.getRuntime().exec("netsh    interface    ip    set    addr    \"本地连接\"    static    192.168.0.55    255.255.255.0     192.168.0.1     1");
        Runtime.getRuntime().exec("netsh interface ip set dns    \"本地连接\"    static    114.114.114.114");
        Runtime.getRuntime().exec("netsh interface ip add dns    \"本地连接\"    8.8.8.8");
        Runtime.getRuntime().exec("ipconfig /flushdns");
        String str1 = "192.168.0.201";
        String str2 = "255.255.255.0";
        String[] command1 = {"netsh", "interface", "ip", "set", "address",
                "name=", "Local Area Connection", "source=static", "addr=" + str1,
                "mask=" + str2};
        Process pp = Runtime.getRuntime().exec(command1);
    }

    public static String exchangeToLocalIp(String ip, String mask, String gateway, String networkName) throws IOException {
        Runtime r = Runtime.getRuntime();
        String dosCommand = "netsh interface ip set address name=\"" + networkName + "\" source=static addr="
                + ip + " mask=" + mask + " gateway=" + gateway;
        Process p = r.exec(dosCommand);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String inline;
        while (null != (inline = br.readLine())) {
            sb.append(inline).append("\n");
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    public static List<Map<String, String>> getNetWorkNameAndStatus() throws SocketException, UnknownHostException {
        List<Map<String, String>> list = new ArrayList<>();
        // 获得本机的所有网络接口
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                Map<String, String> map = new HashMap<>();
                InetAddress addr = addresses.nextElement();
                //if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
                System.out.println("网卡接口名称：" + nif.getName());
                map.put("networkName", nif.getName());
                System.out.println("网卡接口地址：" + addr.getHostAddress());
                map.put("InterfaceAddress", nif.getName());
                System.out.println("网卡状态：" + nif.isUp());
                if (nif.isUp()) {
                    map.put("netWorkStatus", "1");
                } else {
                    map.put("netWorkStatus", "0");
                }
                list.add(map);
            }
        }
        return list;

    }

    public static List<Map<String, String>> getNetworkConList(String startkey, String keyword) throws Exception{
        List<Map<String, String>> maps = new ArrayList<>();
        Runtime r = Runtime.getRuntime();
        String dosCommand = "netsh interface show interface";
        Process p = r.exec(dosCommand);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                p.getInputStream(),"GBK"));
        String inline;
        StringBuffer sb = new StringBuffer();
        while (null != (inline = br.readLine())) {
            if(inline.contains(keyword)){
                Map<String,String> map=new HashMap<>();
                String networkName=inline.substring(inline.indexOf(startkey)+startkey.length(),inline.length()).trim();
                map.put("networkName",networkName);
                map.put("keyword",keyword);
                maps.add(map);
                sb.append(inline).append("\n");
            }
        }
        log.info("getNetworkConList info:"+sb.toString());
        return maps;
    }

}
