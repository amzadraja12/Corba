import EchoApp.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
public class EchoClient {
    public static void main(String[] args) {
        ORB orb = null;
        try {
            orb = ORB.init(args, null);
            NamingContextExt naming = NamingContextExtHelper.narrow(
                orb.resolve_initial_references("NameService")
            );
            Echo service = EchoHelper.narrow(
                naming.resolve_str("EchoService")
            );
            String message = "Welcome students";
            String response = service.echo(message);
            System.out.println("Sent to server: " + message);
            System.out.println("Received from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (orb != null) orb.destroy();
        }
    }
}
