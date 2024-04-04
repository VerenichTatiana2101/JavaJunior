package org.example.client;



import org.example.messages.Connection;
import org.example.messages.Message;
import org.example.messages.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client{
    private Connection connection;
    private ModelGuiClient model;
    private IClient iClient;

    private volatile boolean isConnect = false; //состояние подключения клиента к серверу

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public Client(IClient iClient) {
        this.iClient = iClient;
        this.model = new ModelGuiClient();
    }

    //подключение клиента к серверу
    protected void connectToServer() {
        if (!isConnect) {
            while (true) {
                try {
                    //вызываем окна ввода адреса, порта сервера
                    String addressServer = iClient.getServerAddressFromOptionPane();
                    int port = iClient.getPortServerFromOptionPane();
                    //создаем сокет и объект connection
                    Socket socket = new Socket(addressServer, port);
                    connection = new Connection(socket);
                    isConnect = true;
                    iClient.addMessage("Сервисное сообщение: Вы подключились к серверу.\n");
                    break;
                } catch (Exception e) {
                    errorDialogWindow("Произошла ошибка! Возможно Вы ввели не верный адрес сервера или порт. Попробуйте еще раз");
                    break;
                }
            }
        } else errorDialogWindow("Вы уже подключены!");
    }

    //метод, реализующий регистрацию имени пользователя со стороны клиентского приложения
    protected void nameUserRegistration() {
        while (true) {
            try {
                Message message = connection.receive();
                //приняли от сервера сообщение, если это запрос имени, то вызываем окна ввода имени, отправляем на сервер имя
                if (message.getTypeMessage() == MessageType.REQUEST_NAME_USER) {
                    String nameUser = iClient.getNameUser();
                    connection.send(new Message(MessageType.USER_NAME, nameUser));
                }
                //если сообщение - имя уже используется, выводим соответствующее окно с ошибкой, повторяем ввод имени
                if (message.getTypeMessage() == MessageType.NAME_USED) {
                    errorDialogWindow("Данное имя уже используется, введите другое");
                    String nameUser = iClient.getNameUser();
                    connection.send(new Message(MessageType.USER_NAME, nameUser));
                }
                //если имя принято, получаем множество всех подключившихся пользователей, выходим из цикла
                if (message.getTypeMessage() == MessageType.NAME_ACCEPTED) {
                    iClient.addMessage("Сервисное сообщение: ваше имя принято!\n");
                    model.setUsers(message.getListUsers());
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorDialogWindow("Произошла ошибка при регистрации имени. Попробуйте переподключиться");
                try {
                    connection.close();
                    isConnect = false;
                    break;
                } catch (IOException ex) {
                    errorDialogWindow("Ошибка при закрытии соединения");
                }
            }

        }
    }

    //метод отправки сообщения предназначенного для других пользователей на сервер
    protected void sendMessageOnServer(String text) {
        try {
            connection.send(new Message(MessageType.TEXT_MESSAGE, text));
        } catch (Exception e) {
            errorDialogWindow("Ошибка при отправке сообщения");
        }
    }

    //метод принимающий с сервера сообщение от других клиентов
    protected void receiveMessageFromServer() {
        while (isConnect) {
            try {
                Message message = connection.receive();
                //если тип TEXT_MESSAGE, то добавляем текст сообщения в окно переписки
                if (message.getTypeMessage() == MessageType.TEXT_MESSAGE) {
                    iClient.addMessage(message.getTextMessage());
                }
                //если сообщение с типо USER_ADDED добавляем сообщение в окно переписки о новом пользователе
                if (message.getTypeMessage() == MessageType.USER_ADDED) {
                    model.addUser(message.getTextMessage());
                    iClient.refreshListUsers(model.getUsers());
                    iClient.addMessage(String.format("Сервисное сообщение: пользователь %s присоединился к чату.\n", message.getTextMessage()));
                }
                //аналогично для отключения других пользователей
                if (message.getTypeMessage() == MessageType.REMOVED_USER) {
                    model.removeUser(message.getTextMessage());
                    iClient.refreshListUsers(model.getUsers());
                    iClient.addMessage(String.format("Сервисное сообщение: пользователь %s покинул чат.\n", message.getTextMessage()));
                }
            } catch (Exception e) {
                errorDialogWindow("Ошибка при приёме сообщения от сервера.");
                setConnect(false);
                iClient.refreshListUsers(model.getUsers());
                break;
            }
        }
    }

    //метод реализующий отключение нашего клиента от чата
    public void disableClient() {
        try {
            if (isConnect) {
                connection.send(new Message(MessageType.DISABLE_USER));
                model.getUsers().clear();
                isConnect = false;
                iClient.refreshListUsers(model.getUsers());
            } else errorDialogWindow("Вы уже отключены.");
        } catch (Exception e) {
            errorDialogWindow("Сервисное сообщение: произошла ошибка при отключении.");
        }
    }

    // метод позволяет показывать изображение
    private void errorDialogWindow(String text) {
        iClient.errorDialogWindow(text + "\n");
    }

}
