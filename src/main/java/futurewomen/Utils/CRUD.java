package futurewomen.Utils;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CRUD<T> {
    public boolean add(T obj, List<T> objList) {
        if (obj == null || objList == null || objList.contains(obj)) return false;
        return objList.add(obj);
    }

    public boolean delete(T obj, List<T> objList) {
        if (obj == null || objList == null || !objList.contains(obj)) return false;
        return objList.remove(obj);
    }

}
