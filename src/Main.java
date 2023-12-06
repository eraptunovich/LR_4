import java.io.*;//импорт пакета, содержащего классы для ввода/вывода
import java.net.*;//импорт пакета, содержащего классы для работы в сети Internet



public class Main {

    static int port = 2525;
    public static void main(String[] arg) {

        Sportsman[] athletes = new Sportsman[10];
        athletes[0] = new Sportsman("Иванов", 101, 0);
        athletes[1] = new Sportsman("Петров", 102, 0);
        athletes[2] = new Sportsman("Сидоров", 103, 0);
        athletes[3] = new Sportsman("Козлов", 104, 0);
        athletes[4] = new Sportsman("Смирнов", 105, 0);
        athletes[5] = new Sportsman("Морозов", 106, 0);
        athletes[6] = new Sportsman("Васильев", 107, 0);
        athletes[7] = new Sportsman("Попов", 108, 0);
        athletes[8] = new Sportsman("Новиков", 109, 0);
        athletes[9] = new Sportsman("Борисов", 110, 0);

        ServerSocket serverSocket = null;   //объявление объекта класса ServerSocket
        Socket clientAccepted = null;   //объявление объекта класса Socket
        ObjectInputStream sois = null;  //объявление байтового потока ввода
        ObjectOutputStream soos = null; //объявление байтового потока вывода
        try {
            System.out.println("Сервер запущен!");
            serverSocket = new ServerSocket(port);//создание сокета сервера для заданного порта
            clientAccepted = serverSocket.accept();//выполнение метода, который //обеспечивает реальное подключение сервера к клиенту
            System.out.println("Установлено соединение на порту: "+port);
            sois = new ObjectInputStream(clientAccepted.getInputStream()); //создание потока ввода
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());//создание потока вывода
            String clientMessageRecieved = (String) sois.readObject();
            //объявление строки и присваивание ей данных потока ввода, представленных в виде строки (передано клиентом)

            switch(clientMessageRecieved){
                case "top_3_places":{
                    clientMessageRecieved = Sportsman.getTop3Jumpers(athletes);
                    break;
                }
                default:{
                    String[] parts = clientMessageRecieved.split("/");
                    if (parts.length == 2) {
                        try {
                            for (Sportsman athlete : athletes) {
                                if(athlete.getId() == Integer.parseInt(parts[0])){
                                    athlete.setJumpLength(Double.parseDouble(parts[1]));
                                    clientMessageRecieved = "Данные успешно сохранены на сервере:"+athlete.getId()+"/"+athlete.getLastName()+"/"+athlete.getJumpLength();
                                }
                            }

                        } catch (NumberFormatException e) {
                            clientMessageRecieved = "Ошибка при парсинге номера или длины прыжка.";
                        }
                    } else {
                        clientMessageRecieved = "Некорректный формат строки. Ожидается: номер/длина.";
                    }
                    break;
                }
            }

            soos.writeObject(clientMessageRecieved);//потоку вывода присваивается значение строковой переменной (передается клиенту)
            clientMessageRecieved = (String) sois.readObject();//строке присваиваются данные потока ввода, представленные в виде строки (передано клиентом)

        } catch (Exception ignored) {
        } finally {
            try {
                sois.close();//закрытие потока ввода
                soos.close();//закрытие потока вывода
                clientAccepted.close();//закрытие сокета, выделенного для клиента
                serverSocket.close();//закрытие сокета сервера
            } catch (Exception e) {
                e.printStackTrace();//вызывается метод исключения е
            }
        }
    }
}
