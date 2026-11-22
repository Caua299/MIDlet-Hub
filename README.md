# MIDlet Hub

**Português** | [English](#english)

Fork não oficial do [J2ME Loader](https://github.com/nikita36078/J2ME-Loader), um emulador de jogos e apps J2ME (Java ME) para Android, com interface modernizada.

## O que muda neste fork

- Interface em **Material 3**, com cores dinâmicas (Material You) no Android 12+ e modo AMOLED
- Biblioteca de jogos com cartões e ícones maiores
- Abas **Todos / Favoritos / Recentes**
- Painel de **detalhes** do jogo (ícone, autor, versão, última vez jogado)
- Ordenação da biblioteca por **último jogado**
- Novo **ícone** do app
- **Pontos de save:** guarde cópias do progresso salvo de cada jogo e restaure quando quiser
- **Configurações** reorganizadas em grupos (Aparência, Tela do jogo, Dados e armazenamento), com ícones na cor do tema
- Tela de **configuração por jogo** com novo visual (cartões com mais espaço e títulos de seção destacados)
- Nova paleta padrão para os **controles virtuais** (vale para perfis novos)
- Relatórios de erro **salvos localmente** em um arquivo, sem envio para nenhum servidor
- Novos textos traduzidos para 22 idiomas (traduções feitas com ajuda de IA; correções são bem-vindas)
- Pode ser instalado ao lado do J2ME Loader original (o app tem outro ID: `io.github.caua299.midlethub`)

O emulador em si vem do projeto original: compatibilidade com jogos 2D e 3D, teclado virtual, configurações e perfis por jogo, captura de tela, contador de FPS e **importar/exportar dados** (backup e restauração).

## Download

Baixe a versão oficial mais recente em [Releases](https://github.com/Caua299/MIDlet-Hub/releases/latest) (arquivo `MIDlet_Hub-x.y.z.apk`).

Versões de teste com a mesma assinatura da oficial (tags como `v1.1.0-beta.1`) aparecem em Releases marcadas como **Pre-release**; elas atualizam por cima da versão oficial.

Os APKs de teste, gerados automaticamente a cada mudança, ficam em [Latest debug build](https://github.com/Caua299/MIDlet-Hub/releases/tag/latest-debug). São versões de desenvolvimento e podem ter erros.

## Como compilar

```
./gradlew assembleDevDebug
```

O APK sai em `app/build/outputs/apk/dev/debug/`. É preciso JDK 21 e o Android SDK com o NDK instalado.

## Em desenvolvimento

- Seções recolhíveis na configuração por jogo

## Privacidade

- Sem anúncios, sem análise de uso e sem envio automático de relatórios de erro.
- Relatórios de erro e registros (logs) ficam só no seu aparelho. Você escolhe se e com quem compartilhar. Eles podem conter o modelo do aparelho, a versão do Android e o nome, o fabricante e a versão do jogo.
- O app só acessa a internet quando você instala um jogo a partir de um link, ou quando um jogo usa a rede (como faria num celular da época).
- As permissões de câmera, microfone, localização e Bluetooth existem para jogos que usam esses recursos, e o Android pede a sua autorização.
- O tráfego HTTP sem criptografia é permitido, porque muitos jogos antigos só funcionam assim.
- O backup automático do Android fica desativado para o app.

## Créditos e licença

Baseado no [J2ME Loader](https://github.com/nikita36078/J2ME-Loader) de Nikita Shakarun, que por sua vez é um fork do [J2meLoader](https://github.com/NaikSoftware/J2meLoader). Agradecimentos a [woesss](https://github.com/woesss) ([JL-Mod](https://github.com/woesss/JL-Mod)) pela implementação de Mascot Capsule e outras melhorias.

> Copyright 2017-2026 Nikita Shakarun. Licenciado sob a [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
> Este fork contém modificações. Veja o arquivo [LICENSE](LICENSE) para o texto completo da licença.

---

## English

MIDlet Hub is an unofficial fork of [J2ME Loader](https://github.com/nikita36078/J2ME-Loader), a J2ME (Java ME) games and apps emulator for Android, with a modernized interface.

### What is different in this fork

- **Material 3** interface with dynamic colors (Material You) on Android 12+ and an AMOLED mode
- Game library with cards and larger icons
- **All / Favorites / Recent** tabs
- Game **details** sheet (icon, author, version, last played)
- Library sorting by **last played**
- New app **icon**
- **Save points:** keep copies of each game’s saved progress and restore them any time
- **Settings** reorganized into groups (Appearance, Game screen, Data and storage), with theme-colored icons
- Per-game **configuration screen** with a new look (cards with more room and highlighted section titles)
- New default palette for the **virtual controls** (applies to new profiles)
- Crash reports **saved locally** to a file, nothing is sent to any server
- New texts translated into 22 languages (AI-assisted translations; corrections are welcome)
- Installs alongside the original J2ME Loader (different app ID: `io.github.caua299.midlethub`)

The emulator itself comes from the original project: 2D and 3D game compatibility, virtual keyboard, per-app settings and profiles, screenshots, FPS counter and **import/export data** (backup and restore).

### Download

Get the latest official version from [Releases](https://github.com/Caua299/MIDlet-Hub/releases/latest) (file `MIDlet_Hub-x.y.z.apk`).

Test versions signed with the same key as the official one (tags like `v1.1.0-beta.1`) appear in Releases marked as **Pre-release**; they install over the official version.

Test APKs, built automatically on every change, are published as [Latest debug build](https://github.com/Caua299/MIDlet-Hub/releases/tag/latest-debug). They are development builds and may contain bugs.

### Building

```
./gradlew assembleDevDebug
```

Requires JDK 21 and the Android SDK with the NDK installed. The APK is written to `app/build/outputs/apk/dev/debug/`.

### Privacy

- No ads, no usage analytics and no automatic sending of error reports.
- Error reports and logs stay on your device. You choose whether and with whom to share them. They may contain the device model, the Android version and the game name, vendor and version.
- The app only uses the internet when you install a game from a link, or when a game uses the network (as it would on a phone of the time).
- Camera, microphone, location and Bluetooth permissions exist for games that use those features, and Android asks for your approval.
- Unencrypted HTTP traffic is allowed because many old games only work that way.
- Android automatic backup is disabled for the app.

### Credits and license

Based on [J2ME Loader](https://github.com/nikita36078/J2ME-Loader) by Nikita Shakarun. Thanks to [woesss](https://github.com/woesss) for the Mascot Capsule implementation and other improvements.

> Copyright 2017-2026 Nikita Shakarun. Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
> This fork contains modifications. See the [LICENSE](LICENSE) file for the full license text.
