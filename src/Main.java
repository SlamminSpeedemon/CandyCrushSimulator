import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Environment environment = new Environment(10,10);
        Scanner scan = new Scanner(System.in);

        scan.nextLine();
        environment.updateComponents();
        //scan.nextLine();
        //environment.updateComponents();
        while (true ) {
            scan.nextLine();
            System.out.println("running update");
            environment.updateComponents();
        }


    }
}
