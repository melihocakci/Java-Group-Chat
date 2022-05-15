package packet;

import java.io.Serializable;

public class Packet implements Serializable {
    public String username;
    public String message;

    public Packet(String username, String message) {
    	this.username = username;
    	this.message = message;
    }
}
