package ArcheAge.MarkMe.Ctrl.Helpyshka;

import org.mindrot.jbcrypt.BCrypt;

public class Hash {
    public static void main(String[] args){
        String password = "password123";

        // Генерируем соль и хэш-значение пароля
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        System.out.println(hashedPassword);
        // Проверяем пароль
        /*if (BCrypt.checkpw(password, hashedPassword)) {
            System.out.println("Пароль верный");
        } else {
        $2a$10$Po10Gxu7MuqyOZhnqrEz9.AdzVnwMSy3Q/APpKPKNy6V/Epi2f41O
        $2a$10$0rvHx8/fjeLpU3IcOgUSVuONCGEVsfbQmfw.dX4ROh1fniIABVtV6

            System.out.println("Пароль неверный");
        }*/
    }
}
