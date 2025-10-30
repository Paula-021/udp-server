import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {

    public static void main(String[] args) {
        int port = 5004;

        try (DatagramSocket socket = new DatagramSocket(port)) { //localhost
            System.out.println("UDP Server is running on port " + port);

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);

                String receivedData = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + receivedData);

                // verificar checkSum:

                if (receivedData.startsWith(">RCQ01") && receivedData.contains("<")) {
                    int startIndex = receivedData.indexOf(">RCQ01");
                    int endIndex = receivedData.lastIndexOf("<");
                    String dataToCheck = receivedData.substring(startIndex, endIndex - 2); // Exclude checksum and '<'
                    String receivedChecksum = receivedData.substring(endIndex - 2, endIndex);

                    String calculatedChecksum = getCheckSum(dataToCheck, dataToCheck.length());

                    if (calculatedChecksum.equals(receivedChecksum)) {
                        System.out.println("Checksum is valid.");
                    } else {
                        System.out.println("Invalid checksum. Received: " + receivedChecksum + ", Calculated: " + calculatedChecksum);
                    }
                } else {
                    System.out.println("Invalid message format.");
                }


            }
        } catch (Exception e) {
            System.err.println("Error in UDP Server: " + e.getMessage());
        }
    }

    public static String getCheckSum(String partialRCQ, int parse) {
        String hexadecimal = "";
        int c = 0;
        for (int i = 0; i < parse; i++) {
            char ch = partialRCQ.charAt(i);
            byte b1 = (byte) ch;
            c = c ^ b1;
            hexadecimal = Integer.toHexString(c);
            if (hexadecimal.length() < 2) {
                hexadecimal = "0" + hexadecimal;
            }
        }


        return hexadecimal.toUpperCase();
    }
}