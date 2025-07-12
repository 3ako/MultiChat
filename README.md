# MultiChat
Система межсерверного чата, работающего на Redis.

# Команды:
`/multichat <текст>` - Команда, отправляющая ваше сообщение в межсерверный чат. <br>
`/multichattoggle` - Выключить/включить видимость сообщений из межсерверного чата только для Вас.

# Права
multichat.use - для использование чата

# Конфигурация плагина
```
redis:
  host: "127.0.0.1"
  port: 6379
  timeout: 3000

multi-chat:
  permission-use: "multichat.use"
  format: "{sender}: {message}"
  on: "Видимость чата &aвключена"
  off: "Видимость чата &cвыключена"
```

# Разработчики
* <a href="https://github.com/3ako">@3ako</a></h2>
* <a href="https://github.com/Smoup">@Smoup</a></h2>
* <a href="https://github.com/alexec0de">@alexec0de</a></h2>
* <a href="https://github.com/temaosukov">@temaosukov</a></h2>
* <a href="https://t.me/quizie_dev">Квизи</a></h2>