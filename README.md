# MultiChat - плагин, добавляющий межсерверный чат!

## Для работы требуется Redis!

# Команды:
`/multichat <message>` - оснавная комманда для отправки сообщения

`/multichattoggle` - Для включение или выключение чата

# Права
multichat.use - для использование чата

# config.yml
```
chat-permission: "multichat.use"
chat-format: "%sender%: %message%"
chat-on: "Чат §aвключён"
chat-off: "Чат §cвыключен"
chatOnlyPlayers: "§cИспользовать мультичат может только игрок!"

redis:
  host: "127.0.0.1"
  port: 6379
  timeout: 3000
```

# Разработчики
* <a href="https://github.com/3ako">@3ako</a></h2>
* <a href="https://github.com/Smoup">@Smoup</a></h2>
* <a href="https://github.com/alexec0de">@alexec0de</a></h2>
* <a href="https://github.com/temaosukov">@temaosukov</a></h2>
