package com.company;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.out;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/ProductMarket&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASSWORD = "12345";

    static String sql;
    static Scanner in = new Scanner(System.in);
    static Connection connection = null;
    static Statement statement = null;
    static PreparedStatement preparedStatement = null;

    public static void CustomerMenu() {
        try {
            boolean customerMenu = true;
            int customerSw;
            while (customerMenu) {
                out.println("""
                        1.Просмотр таблицы.
                        2.Добавить запись.
                        3.Изменить запись.
                        4.Удалить
                        5.Вернуться к выбору таблицы""");
                customerSw = in.nextInt();
                switch (customerSw) {
                    case 1 -> {
                        sql = "SELECT * FROM Customers";
                        ResultSet resultSet = statement.executeQuery(sql);
                        Customer customer = new Customer();
                        while (resultSet.next()) {
                            customer.id = resultSet.getInt("id");
                            customer.customerLogin = resultSet.getString("userLogin");
                            customer.customerPassword = resultSet.getString("userPassword");
                            customer.customerEmail = resultSet.getString("userEmail");
                            out.println("\n================\n");
                            out.println(customer);
                        }
                        resultSet.close();
                        out.println("\n");
                    }
                    case 2 -> {
                        sql = "INSERT INTO Customers VALUE (DEFAULT,?,?,?)";
                        preparedStatement = connection.prepareStatement(sql);
                        out.println("Введите логин нового пользователя:\n");
                        String login = in.next();
                        out.println("Введите пароль:\n");
                        String password = in.next();
                        out.println("Введите почту:");
                        String email = in.next();
                        preparedStatement.setString(1, login);
                        preparedStatement.setString(2, password);
                        preparedStatement.setString(3, email);
                        preparedStatement.execute();
                        out.println("Новая запись добавлена!");
                    }
                    case 3 -> {
                        sql = "UPDATE Customers SET userLogin=?,userPassword=?,userEmail=? where id=?";
                        preparedStatement = connection.prepareStatement(sql);
                        out.println("Введите id:");
                        int id = in.nextInt();
                        out.println("Введите логин:\n");
                        String login = in.next();
                        out.println("Введите пароль:\n");
                        String password = in.next();
                        out.println("Введите почту:");
                        String email = in.next();
                        preparedStatement.setString(1, login);
                        preparedStatement.setString(2, password);
                        preparedStatement.setString(3, email);
                        preparedStatement.setInt(4, id);
                        preparedStatement.executeUpdate();
                        out.println("Запись изменена");
                    }
                    case 4 -> {
                        sql = "DELETE FROM Customers WHERE id=?";
                        preparedStatement = connection.prepareStatement(sql);
                        out.println("Введите id:");
                        int id = in.nextInt();
                        preparedStatement.setInt(1, id);
                        preparedStatement.execute();
                        out.println("запись удалена успешно!");
                    }
                    case 5 -> customerMenu = false;
                    default -> throw new IllegalStateException("Неизвестное значение: " + customerSw);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        boolean mainMenu = true;

        out.println("Registering JDBC driver...");
        Class.forName(JDBC_DRIVER);
        out.println("Creating database connection...");

        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        out.println("Executing statement...");

        statement = connection.createStatement();

        while (mainMenu) {
            int table;
            out.println("""
                    С какой таблицой производить действия:
                    1.Customers
                    2.Category
                    3.Products
                    4.Market
                    5.Warehouse
                    6.Suppliers
                    7.Deliveries
                    8.Sales""");
            table = in.nextInt();
            switch (table) {
                case 1 -> CustomerMenu();

                case 2 -> CategoriesMenu();
            }
        }


        out.println("Closing connection and releasing resources...");

        statement.close();
        connection.close();

    }

    private static void CategoriesMenu() {
        try {
            boolean menu = true;
            int sw;
            while (menu) {
                out.println("""
                        1.Просмотр таблицы.
                        2.Добавить запись.
                        3.Изменить запись.
                        4.Удалить
                        5.Вернуться к выбору таблицы""");
                sw = in.nextInt();
                switch (sw) {
                    case 1 -> {
                        sql = "SELECT * FROM Category";
                        ResultSet resultSet = statement.executeQuery(sql);
                        Category category = new Category();
                        while (resultSet.next()) {
                            category.id = resultSet.getInt("id");
                            category.catergoryName = resultSet.getString("categoryName");
                            out.println("\n================\n");
                            out.println(category);
                        }
                        resultSet.close();
                        out.println("\n");
                    }
                    case 2 -> {
                        sql = "INSERT INTO Category VALUE (DEFAULT,?)";
                        preparedStatement = connection.prepareStatement(sql);
                        out.println("Введите название категории:\n");
                        String categoryName = in.next();
                        preparedStatement.setString(1, categoryName);
                        preparedStatement.execute();
                        out.println("Новая запись добавлена!");
                    }
                    case 3 -> {
                        sql = "UPDATE Category SET categoryName=? where id=?";
                        preparedStatement = connection.prepareStatement(sql);
                        out.println("Введите id:");
                        int id = in.nextInt();
                        out.println("Введите новое название категории:\n");
                        String newCategoryName = in.next();
                        preparedStatement.setString(1, newCategoryName);
                        preparedStatement.setInt(2, id);
                        preparedStatement.executeUpdate();
                        out.println("Запись изменена");
                    }
                    case 4 -> {
                        sql = "DELETE FROM Category WHERE id=?";
                        preparedStatement = connection.prepareStatement(sql);
                        out.println("Введите id:");
                        int id = in.nextInt();
                        preparedStatement.setInt(1, id);
                        preparedStatement.execute();
                        out.println("запись удалена успешно!");
                    }
                    case 5 -> menu = false;
                    default -> throw new IllegalStateException("Неизвестное значение: " + sw);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
