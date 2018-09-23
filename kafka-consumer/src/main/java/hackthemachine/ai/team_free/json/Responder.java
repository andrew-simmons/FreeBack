package hackthemachine.ai.team_free.json;

import java.util.UUID;

public class Responder {
	public final long id;
	public UUID evidenceId;
	public String streetAddress;
	public double duration; // seconds
	public String location; // lat / long string
	public double latitude;
	public double longitude;
	public String type;

	public Responder(long id) {
		this.id = id;
	}
}
