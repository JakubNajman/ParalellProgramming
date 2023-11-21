package org.e1a;


import java.io.File;
import java.io.IOException;
import org.pcj.PCJ;
import org.pcj.RegisterStorage;
import org.pcj.StartPoint;
import org.pcj.Storage;

/**
 *
 * @author Jakub Najman
 */
@RegisterStorage(PcjSumowanieWektorow.Shared.class)
public class PcjSumowanieWektorow implements StartPoint {

    @Storage(PcjSumowanieWektorow.class)
    enum Shared {
        a
    };
    double a;

    public static void main(String[] args) throws IOException {
        PCJ.executionBuilder(PcjSumowanieWektorow.class)
                .addNodes(new File("nodes.txt"))
                .start();
    }

    @Override
    public void main() throws Throwable {

        int n = 1048576;
        int nn = n / PCJ.threadCount();

        double x[] = new double[nn];

        for (int i = 0; i < nn; i++) {
            x[i] = Math.random();
        }

        double suma = 0.0d;
        a = 0.0d;

        for (int i = 0; i < nn; i++) {
            a = a + x[i];
        }

        if (PCJ.myId() == 0) {
            suma = PCJ.reduce(Double::sum, Shared.a);
        }

        System.out.println("suma @" + PCJ.myId() + " " + suma);

    }
}
