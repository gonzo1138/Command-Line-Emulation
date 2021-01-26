/*
Schreiben Sie die Emulation eines Command Line Tools in Java auf Grundlage der File-Klasse,
das folgende Linux-Befehle (ggf. auch Windows) simulert:
cd*
mkdir*
ls*
cp*
pwd*
rm*
mv
etc.

* Pflichtfunktionen

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Stream;

// https://github.com/dialex/JColor
import com.diogonunes.jcolor.*;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Attribute[] alertFormat = new Attribute[]{RED_TEXT(), BOLD()};
        Attribute[] promptFormat = new Attribute[]{WHITE_TEXT()};
        System.out.print(colorize("Kommandozeile. Jetzt auch in Farbe. Und bunt!", alertFormat) + "\n\n");
        boolean terminate = false;

        // OS-Check
        String root = "";
        FileSystem fileSystem = FileSystems.getDefault();
        //System.out.println(fileSystem.getRootDirectories());
        char fs = fileSystem.toString().charAt(11);
        if (fs == 'L' || fs =='U') root = "/";
        else if (fs == 'W') root = "c:/";
        else {
            System.out.println(colorize("Unbekanntes Betriebssystem...", alertFormat));
            terminate = true;
        }
        String currentpath = root;
        Scanner command = new Scanner(System.in);
        String[] order;

        while(!terminate){
            //  Prompt
            System.out.print(colorize(currentpath, YELLOW_TEXT()) + colorize(" # ", GREEN_TEXT()));
            String request = command.next();
            order = request.split(" ");

            switch (order[0]){
                case "help":
                    System.out.println(colorize("Mögliche Befehle sind\n" +
                        "pwd\t\t:\tAktuelles Arbetisverzeichnis anzeigen\n" +
                        "ls\t\t:\tVerzeichnisinhalt auflisten\n" +
                        "cd\t\t:\tVerzeichnis wechseln\n" +
                        "mkdir\t:\tVerzeichnis erstellen\n" +
                        "cp\t\t:\tDatei kopieren\n" +
                        "rm\t\t:\tDatei oder Verzeichnis löschen\n" +
                        "mv\t\t:\tDatei oder Verzeichnis verschieben\n" +
                        "help\t:\tDiesen Hilfetext anzeigen\n" +
                        "quit\t:\tDiese wunderschöne, bunte Komandozeilenanwendung beenden", promptFormat));
                    break;
            //  ls  /   dir
                case "ls":
                case "dir":
                    try (Stream<Path> paths = Files.walk(Paths.get(currentpath))) {
                        paths.filter(Files::isRegularFile)
                                .forEach(System.out::println);
                    }
            //  pwd
                case "pwd":
                    System.out.println(colorize(currentpath, promptFormat));
                    break;
            //  cd
                case "cd":
                    //...
                    //if(Files.exists(path.order[1])) currentpath = order[1];
                    break;
            //  mkdir
                case "mkdir":
                    break;
            //  cp
                case "cp":
                    break;
            //  rm
                case "rm":
                    break;
            //  mv
                case "mv":
                    break;
            //  quit
                case "quit":
                    terminate = true;
                    break;
                default:
                    System.out.println(colorize("Befehl nicht gefunden... durchsuche Internet nach dem Sinn des Lebens...", promptFormat));
            }

            Path path = FileSystems.getDefault().getPath(root);
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            // reset
            order = new String[]{""};
        }
        System.out.print(colorize("\n\nFormatiere Festplatte C:\nBitte warten...", alertFormat) + "\n\n");
    }
}
