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


            }
        } catch (Exception e) {
            System.err.println("Error in UDP Server: " + e.getMessage());
        }
    }
}