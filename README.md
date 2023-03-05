# java-filmorate
# ERD Диаграмма (Создана в [quickdatabasediagrams.com](https://app.quickdatabasediagrams.com/#/d/XKDa96))
![image](https://user-images.githubusercontent.com/113610062/222962339-76112b1e-22aa-4a51-b4b0-ffea277c46a1.png)

</details>
<details>
  <summary><h3> Пояснение к диаграмме:</h3></summary>

Таблица **film:**
- ***film_id*** - Униккальный ключ. ID фильма  
- ***name*** - Название фильма  
- ***description*** - Описание фильма  
- ***duration*** - Длительность фильма  
- ***relizedate*** - Дата выхода фильма  
- ***mpa_id*** - Уникальный ключ . ID Рейтинга фильма

Таблица **film_genre:**  
- ***film_id*** - Уникальный ключ. ID фильма  
- ***genre_id*** - Уникальный ключ. ID жанра фильма  

Таблица **film_likes:**  
- ***user_id*** - Уникальный  ключ. ID пользователя  
- ***film_id*** - Уникальный ключ. ID фильма  

Таблица **friends:**  
- ***user_id*** -   Уникальный ключ. ID пользователя  
- ***friend_id*** - Уникальный ключ. ID друга пользователя  
- ***status*** - статус дружбы  

Таблица **genre:**   
- ***genre_id*** - Уникальный ключ. ID жанра фильма  
- ***genre*** - жанр фильма  

Таблица **mpa:**   
- ***mpa_id*** - Уникальный ключ . ID Рейтинга фильма  
- ***rating*** - Рейтинг фильма  

Таблица **user:**  
- ***user_id*** - Уникальный  ключ. ID пользователя   
- ***name*** - Имя пользователя  
- ***birthday*** - Дата рождения пользователя  
- ***login*** - Логин пользователя  
- ***email*** - Почтовый адресс пользователя
  
  </details>
<details>
  <summary><h3> Примеры запросов:</h3></summary>
  
  * получение списка всех пользователей
```
SELECT *
FROM user
```
  
  * получение информации о пользователе по его **id**
```
SELECT *
FROM user
WHERE user_id = id?
```



