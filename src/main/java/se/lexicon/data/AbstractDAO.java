package se.lexicon.data;

public class AbstractDAO {

    public void closeAll(AutoCloseable...closeables) {
        if (closeables != null){
            for (AutoCloseable closable : closeables) {
                try {
                    closable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
