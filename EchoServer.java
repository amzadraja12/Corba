import EchoApp.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
class EchoImpl extends EchoPOA {
    @Override
    public String echo(String message) {
        System.out.println("Received from client: " + message);
        return message;
    }
}
public class EchoServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootPOA = POAHelper.narrow(
                orb.resolve_initial_references("RootPOA")
            );
            rootPOA.the_POAManager().activate();
            EchoImpl servant = new EchoImpl();
            org.omg.CORBA.Object reference =
                rootPOA.servant_to_reference(servant);
            Echo echoReference = EchoHelper.narrow(reference);
            NamingContextExt naming = NamingContextExtHelper.narrow(
                orb.resolve_initial_references("NameService")
            );
            naming.rebind(
                naming.to_name("EchoService"), echoReference
            );
            System.out.println("Echo Server is ready.");
            orb.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
