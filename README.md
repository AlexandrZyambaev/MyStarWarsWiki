# StarWarsApp

Приложение для просмотра персонажей вселенной Star Wars с использованием API https://swapi.dev

---

## 📱 Функционал

- Список персонажей
- Поиск по имени
- Детальный экран персонажа
- Кеширование данных (Room)
- Работа без интернета после первой загрузки
- Обработка ошибок
- Повторная попытка загрузки данных
- Индикатор загрузки

---

## 🛠 Технологии

- Kotlin
- Jetpack Compose
- MVVM
- Hilt (DI)
- Retrofit (network)
- Room (local storage)
- Coroutines / Flow
- Navigation Compose

---

## 🧱 Архитектура

Проект построен по принципу Clean Architecture:

- **data**
  - API (Retrofit)
  - база данных (Room)
  - Repository implementation

- **domain**
  - модели
  - интерфейсы репозиториев
  - use cases

- **presentation**
  - ViewModel
  - UI (Jetpack Compose)
  - navigation

---

## 🔄 Работа с данными

1. Данные загружаются с API (swapi.dev)
2. Сохраняются в локальную базу (Room)
3. UI получает данные из базы
4. При отсутствии интернета используется кеш

---

## 🌐 Особенности реализации

- Загружаются все персонажи
- Species и Films загружаются по отдельным endpoint'ам
- При ошибке отображается сообщение и кнопка "Повторить попытку"
- При загрузке отображается progress indicator

