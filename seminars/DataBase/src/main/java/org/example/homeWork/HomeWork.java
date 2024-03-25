package org.example.homeWork;

import org.example.homeWork.annotations.Column;
import org.example.homeWork.annotations.Id;
import org.example.homeWork.annotations.Table;

public class HomeWork {
    /**
     * 0. Разобрать код с семинара
     * 1. Повторить код с семинара без подглядываний на таблице Student с полями:
     * 1.1 id - int
     * 1.2 firstName - string
     * 1.3 secondName - string
     * 1.4 age - int
     * 2.* Попробовать подключиться к другой БД
     * 3.** Придумать, как подружить запросы и reflection:
     * 3.1 Создать аннотации Table, Id, Column
     * 3.2 Создать класс, у которого есть методы:
     * 3.2.1 save(Object obj) сохраняет объект в БД
     * 3.2.2 update(Object obj) обновляет объект в БД
     * 3.2.3 Попробовать объединить save и update (сначала select, потом update или insert)
     */
  @Table(name = "person")
  static class Person {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    public <T> T findById(int id, Class<T> tClass){
      String tableName = tClass.getAnnotation(Table.class).name();

      // select .... from table
        return null;
    }

    public void save(Object obj){
      // obj.
    }


    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

}
