package ArcheAge.MarkMe.Variables;

import org.mindrot.jbcrypt.BCrypt;

public class Hash {
    public static void main(String[] args){
        String password = "password123";

        // Генерируем соль и хэш-значение пароля
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        System.out.println(hashedPassword);
        // Проверяем пароль
        if (BCrypt.checkpw(password, hashedPassword)) {
            System.out.println("Пароль верный");
        } else {
            System.out.println("Пароль неверный");
        }
        System.out.println("$2a$10$6w94T9gyuHYTqF0yofB55em8vfN85B50OgYNeWxvNFypVDxJBF3Aq" == "$2a$10$dPzoSZisU8zyFNcTMxzpaOWzZt469.E2AWzboQ6AEu0/t1VtkkfxW");
    }
}
