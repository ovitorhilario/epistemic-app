# Epistemic App ✔️

## Guia de Instalação 📕 
### 1º Modo 🔗
- Para executar o código na sua máquina, clone o repositório e abra no seu Android Studio
    - `git clone https://github.com/ovitorhilario/epistemic-app.git`

### 2º Modo ⬇️📲 
- Para instalar o apk, Clique [Aqui](https://github.com/ovitorhilario/epistemic-app/releases/download/v1.0.1/epistemic.apk)

## Guia de Uso 🧾 
- Para fazer login, use as credenciais, elas foram arbitrariamente escolhidas para simulação dos casos de uso.
  - e-mail: `epistemic@gmail.com`
  - senha: `123456`

## ScreenShots  📸
| Login | Tela Inicial | Configurações | 
| :--------------------: | :--------------------: | :--------------------: |
| ![01](https://github.com/ovitorhilario/epistemic-app/assets/81326138/ccfc0431-67fb-41d1-8a4e-44a5c952ee12) | ![02](https://github.com/ovitorhilario/epistemic-app/assets/81326138/12df81db-f088-47ef-a72c-14185127d831) | ![03](https://github.com/ovitorhilario/epistemic-app/assets/81326138/265ecee0-4347-4be7-bdfa-135761157830) |

## Vídeo de demonstração do App 📹
- https://youtu.be/UJ8c4Ukc1Kg

## Fluxograma da função `toggle_notifications()`
![epistemic](https://github.com/ovitorhilario/epistemic-app/assets/81326138/f77439de-f823-45df-9de8-7902aff15b6c)

## Função usada para definir os horários das notificações 🔔
```kotlin
private fun getNotifyHours(notifyPerDay: Int): List<Int> {
    val totalHours = 24 - 6 // 24 horas - (0h00 até 6h00 - 6 horas)
    val targetHour = 24 // Limite >> 0h00
    val initHour = 6 // Início >> 6h00

    // Cálculo do intervalo entre cada notificação
    val div = Math.floorDiv(totalHours, notifyPerDay + 1)
    // Lista para armazenar os horários de notificação
    val notifyHours = mutableListOf<Int>()

    /*
        Este laço for adiciona os horários à lista. A expressão:
        `hour in (initHour + div)..targetHour step div`
        cria um range de elementos com um intervalo definido por `div`.

        ex: início: 2, final: 10, div/step: 2, range: 2, 4, 6, 8, 10
     */
    for (hour in (initHour + div)..targetHour step div) {
        // Se a quantidade de notificações é menor que a definida, outro é adicionado
        if (notifyHours.size < notifyPerDay) {
            notifyHours.add(hour)
        } else break // Se a quantidade for sufiente, o `for` é finalizado
    }

    return notifyHours.toList()
}
```

## Tecnologias e Bibliotecas utilizadas
- Android SDK
- Kotlin
- Alarm Manager
- Notification Manager
- Hilt Dagger 2
- Picasso
- DataStore
- Navigation Component + Safe Args

## Arquitetura do App 🗄️
- MVVM - Model-View-ViewModel + Clean Architecture
  
![image](https://github.com/ovitorhilario/epistemic-app/assets/81326138/763929ca-da7c-49f5-b1da-9043838bd12d)
