package org.example.client;

import java.util.Set;

public interface IClient {
    void errorDialogWindow(String message);
    void refreshListUsers(Set<String> listUsers);
    String getNameUser();
    int getPortServerFromOptionPane();
    void addMessage(String text);
    String getServerAddressFromOptionPane();

}
