package com.app.device.handler.wwj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
        import java.net.Socket;

public class ClientSendSocket
{
    private  static Logger log = LoggerFactory.getLogger(ClientSendSocket.class);
    private  Socket socket;
    private  OutputStream outputStream;

    public   ClientSendSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputStream = socket.getOutputStream();
    }
    public String sendData(String data)  {
        try {
            byte[] byteArray = data.getBytes();
            outputStream.write(byteArray);
            //刷新缓冲
            outputStream.flush();
            //得到一个输入流，用于接收服务器响应的数据
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1]; // 一次读取一个byte
            String info = "";
            while (true) {
                if (inputStream.available() > 0) {
                    inputStream.read(bytes);
                    String hexStr = ByteArrayToHexStr(bytes);
                    info += HexStrToStr(hexStr);
                    //已经读完
                    if (inputStream.available() == 0) {
                        log.info("收到来自服务端的信息:" + info);
                        if (!info.equals("ok")) {
                            return "没有收到服务器反馈ok";
                        }
                        if (info.equals("ok")) {
                            return "";
                        }
                        break;
                    }
                }
            }
        }catch (Exception e){
            log.error("发送数据错误");
            return "错误";
        }
        return "";
    }

    public void close() throws IOException {
        outputStream.close();
        socket.close();
    }

    public static String SendSocketByPort(String data,Integer port)
    {

        try
        {
            if(null==port){
                port=8838;
            }
            Socket socket = new Socket("localhost", port);
            //得到一个输出流，用于向服务器发送数据
            OutputStream outputStream = socket.getOutputStream();
            while (true)
            {
                if ("exit".equals(data))
                {
                    return "退出";
                }
                //byte[] byteArray = HexStrToByteArray(data);
                byte[] byteArray = data.getBytes();
                outputStream.write(byteArray);
                //刷新缓冲
                outputStream.flush();
                //得到一个输入流，用于接收服务器响应的数据
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1]; // 一次读取一个byte
                String info = "";
                while (true)
                {
                    if (inputStream.available() > 0)
                    {
                        inputStream.read(bytes);
                        String hexStr = ByteArrayToHexStr(bytes);
                        info += HexStrToStr(hexStr);
                        //已经读完
                        if (inputStream.available() == 0)
                        {
                            log.info("收到来自服务端的信息:" + info);
                            if(!info.equals("ok")){
                                return "没有收到服务器反馈ok";
                            }
                            if(info.equals("ok")){
                                return "";
                            }
                            break;
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();

        }
        return "";
    }


    /**
     * 16进制Str转byte[]
     *
     * @param hexStr
     * @return
     */
    public static byte[] HexStrToByteArray(String hexStr)
    {
        if (hexStr == null)
        {
            return null;
        }
        if (hexStr.length() == 0)
        {
            return new byte[0];
        }
        byte[] byteArray = new byte[hexStr.length() / 2];
        for (int i = 0; i < byteArray.length; i++)
        {
            String subStr = hexStr.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }
    /**
     * byte[]转16进制Str
     *
     * @param byteArray
     */
    public static String ByteArrayToHexStr(byte[] byteArray)
    {
        if (byteArray == null)
        {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int i = 0; i < byteArray.length; i++)
        {
            int temp = byteArray[i] & 0xFF;
            hexChars[i * 2] = hexArray[temp >>> 4];
            hexChars[i * 2 + 1] = hexArray[temp & 0x0F];
        }
        return new String(hexChars);
    }
    /**
     * 16进制的Str转Str
     *
     * @param hexStr
     * @return
     */
    public static String HexStrToStr(String hexStr)
    {
        //能被16整除,肯定可以被2整除
        byte[] array = new byte[hexStr.length() / 2];
        try
        {
            for (int i = 0; i < array.length; i++)
            {
                array[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16));
            }
            hexStr = new String(array, "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return hexStr;
    }
}