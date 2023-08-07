package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class view {
    public static void start() {
        System.out.println("Выберите нужное действие:");
        System.out.println("1) Добавить новое животное\n" +
                "2) Посмотреть список команд, которые выполняет животное\n" +
                "3)  Обучить животное новой команде");
        int action = Integer.parseInt(UserInput(""));
        Action(action);
    }
    private static String UserInput(String msg) {
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        return sc.nextLine();
        }
    private static String GroupCheck(int gr) {
        if (gr == 1) {
            return "Pets";
        } else if (gr == 2) {
            return "Pack_animals";
        } else {
            System.out.println("Неправильный номер. Попробуйте ещё раз");
            return "loop";
        }
    }
    private static String SpeciesHMCheck(int sp) {
        if (sp == 1) {
            return "dogs";
        } else if (sp == 2) {
            return "Cats";
        } else if (sp == 3) {
            return "Hamsters";
        } else {
            System.out.println("Неправильный номер. Попробуйте ещё раз");
            return "loop";
        }
    }
    private static String SpeciesPACheck(int sp) {
        if (sp == 1) {
            return "Horses";
        } else if (sp == 2) {
            return "Camels";
        } else if (sp == 3) {
            return "Donkeys";
        } else {
            System.out.println("Неправильный номер. Попробуйте ещё раз");
            return "loop";
        }
    }
    public static List<String> NameDate () {
        List<String> list = new ArrayList<>(2);
        System.out.println("Введите имя и дату рождения животного");
        list.add(UserInput("Имя животного:"));
        list.add(UserInput("Дата рождения животного (в формате YYYY-MM-DD:"));
        return list;
    }
    public static void AddtoAnimals(Animals addtoani) {
        String sql = "INSERT " + addtoani.species +
                " VALUES " +
                "("+ addtoani.id +", '" + addtoani.animal_name + "'" +
                ", '"+ addtoani.animal_cmd +"'" + ", '"+ addtoani.animal_birthday +"'"+");";
        System.out.println("____________________________________________________________");
        System.out.println();
        try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password)) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void CommandsSerch (String namesearch) {
        String sql = "SELECT animal_id, animal_name, animal_cmd " +
                "FROM dogs WHERE animal_name = \'" + namesearch + "' " +
                "UNION SELECT animal_id, animal_name, animal_cmd FROM cats WHERE animal_name = \'" + namesearch + "' "+
                "UNION SELECT animal_id, animal_name, animal_cmd FROM hamsters WHERE animal_name = \'" + namesearch + "' "+
                "UNION SELECT animal_id, animal_name, animal_cmd FROM horses WHERE animal_name = \'" + namesearch + "' "+
                "UNION SELECT animal_id, animal_name, animal_cmd FROM donkeys WHERE animal_name = \'" + namesearch + "' ";
        System.out.println("____________________________________________________________");
        System.out.println();
        try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("animal_id") +
                        " " + rs.getString("animal_name") + " " + rs.getString("animal_cmd"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void TeachNew(String name, String newtrick) {
        String sql = "SELECT animal_id, animal_cmd FROM dogs WHERE animal_name = \'" + name + "'";

        System.out.println("____________________________________________________________");
        System.out.println(sql);
        System.out.println();
        try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String cmd = rs.getString("animal_cmd") + ", " + newtrick;
                System.out.println(cmd);
                String id = rs.getString("animal_id");
                String sql2 = "SELECT group_name FROM dogs WHERE animal_id = \'" + id + "'";
            }
            String comand = "UPDATE "+ "animal_cmd FROM dogs WHERE animal_name = \'" + name + "'";
            stmt.executeQuery(comand);
            while (rs.next()) {
                System.out.println(rs.getInt("animal_id") +
                        " " + rs.getString("animal_name") + " " + rs.getString("animal_cmd"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private static void Action (int action) {
        switch (action) {
            case 1:
                List<String> list = NameDate();
                System.out.println("Чтобы добавить животное введите группу (1-для домашних и 2 для вьючных):");
                int gr = Integer.parseInt(UserInput(""));
                String group = GroupCheck(gr);
                if (group == "loop") {
                    start();
                }
                int sp;
                String species;
                if (gr == 1) {
                    System.out.println("Введите вид животного (1-для собак, 2 для кошек и 3 для хомяков):");
                    sp = Integer.parseInt(UserInput("Вид животного:"));
                    species = SpeciesHMCheck(gr);
                    if (group == "loop") {
                        start();
                    }
                    if (sp == 1) {
                        AddtoAnimals(new Dogs(group, species, list.get(0), list.get(1)));
                    } else if (sp == 2) {
                        AddtoAnimals(new Cats(group, species, list.get(0), list.get(1)));
                    } else {
                        AddtoAnimals(new Hamsters(group, species, list.get(0), list.get(1)));
                    }
                } else {
                    System.out.println("Введите вид животного (1-для лошадей, 2 для верблюдов и 3 для ослов):");
                    sp = Integer.parseInt(UserInput("Вид животного:"));
                    species = SpeciesPACheck(gr);
                    if (group == "loop") {
                        start();
                    }
                    if (sp == 1) {
                        AddtoAnimals(new Horses(group, species, list.get(0), list.get(1)));
                    } else if (sp == 2) {
                        AddtoAnimals(new Camels(group, species, list.get(0), list.get(1)));
                    } else {
                        AddtoAnimals(new Donkeys(group, species, list.get(0), list.get(1)));
                    }
                }
                start();
                break;
            case 2:
                System.out.println("Введите Имя животного");
                String namesearch = UserInput("->");
                CommandsSerch(namesearch);
                start();
                break;
            case 3:
                System.out.println();
                String name = UserInput("Введите Имя животного");
                String newtrick = UserInput("Введите новую команду");
                TeachNew(name, newtrick);
                start();
                break;
        }

    }
}
