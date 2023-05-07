import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Reseau {


    default byte[] serialize(Object obj) throws IOException { // permet de convertir un objet en tableau de byte
    ByteArrayOutputStream out = new ByteArrayOutputStream(); // permet de stocker les données dans un tableau de byte
    ObjectOutputStream os = new ObjectOutputStream(out); // permet de convertir un objet en tableau de byte
    os.writeObject(obj); // permet de convertir un objet en tableau de byte
    return out.toByteArray();
    }
    
    default Object deserialize(byte[] data) throws IOException, ClassNotFoundException { // permet de convertir un tableau de byte en objet
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream is = new ObjectInputStream(in);
    return is.readObject();
    }
    

}