package futurewomen.Utils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * SerializeDeserializer was created as part of the Utilities package of Student Management
 * @param <T>
 */
public class SerializeDeserializer <T>{
    public boolean serialize(List<T> obj, Path pathName) {
        try (ObjectOutputStream outObj = new ObjectOutputStream(
                new FileOutputStream(pathName.toFile()))) {
            outObj.writeObject(obj);
            System.out.println("...Object serialized successfully.\n");
            return true;
        } catch (IOException e) {
            System.out.println("Object serialization failed. " + e.getMessage());
        }
        return false;
    }

    public List<T> deserialize(Path pathName) {
        List<T> obj = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(pathName.toFile()))) {
            obj = (List<T>) in.readObject();
            System.out.println("...Object deserialized successfully.\n");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Object deserialization failed. " + e.getMessage());
        }
        return obj;
    }
}
