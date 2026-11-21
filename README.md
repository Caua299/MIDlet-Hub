# MIDlet Hub

**Português** | [English](#english)

Fork não oficial do [J2ME Loader](https://github.com/nikita36078/J2ME-Loader), um emulador de jogos e apps J2ME (Java ME) para Android, com interface modernizada.

## O que muda neste fork

- Interface em **Material 3**, com cores dinâmicas (Material You) no Android 12+ e modo AMOLED
- Biblioteca de jogos com cartões e ícones maiores
- Abas **Todos / Favoritos / Recentes**
- Painel de **detalhes** do jogo (ícone, autor, versão, última vez jogado)
- **Configurações** reorganizadas em grupos (Aparência, Tela do jogo, Dados e armazenamento), com ícones na cor do tema
- Nova paleta padrão para os **controles virtuais** (vale para perfis novos)
- Relatórios de erro **salvos localmente** em um arquivo, sem envio para nenhum servidor
- Pode ser instalado ao lado do J2ME Loader original (o app tem outro ID: `io.github.caua299.midlethub`)

O emulador em si (compatibilidade com jogos 2D e 3D, teclado virtual, configurações por jogo) vem do projeto original.

## Download

Os APKs de teste são gerados automaticamente pelo GitHub Actions.
Baixe o mais recente em [Releases → Latest debug build](https://github.com/Caua299/MIDlet-Hub/releases/tag/latest-debug).

> São versões de teste (debug). Podem ter erros.

## Como compilar

```
./gradlew assembleDevDebug
```

O APK sai em `app/build/outputs/apk/dev/debug/`. É preciso JDK 21 e o Android SDK com o NDK instalado.

## Em desenvolvimento

- Save states
- Tela de configuração por jogo redesenhada

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
- **Settings** reorganized into groups (Appearance, Game screen, Data and storage), with theme-colored icons
- New default palette for the **virtual controls** (applies to new profiles)
- Crash reports **saved locally** to a file, nothing is sent to any server
- Installs alongside the original J2ME Loader (different app ID: `io.github.caua299.midlethub`)

The emulator itself (2D and 3D game compatibility, virtual keyboard, per-app settings) comes from the original project.

### Download

Test APKs are built automatically by GitHub Actions. Get the latest one from [Releases → Latest debug build](https://github.com/Caua299/MIDlet-Hub/releases/tag/latest-debug). These are debug builds and may contain bugs.

### Building

```
./gradlew assembleDevDebug
```

Requires JDK 21 and the Android SDK with the NDK installed. The APK is written to `app/build/outputs/apk/dev/debug/`.

### Credits and license

Based on [J2ME Loader](https://github.com/nikita36078/J2ME-Loader) by Nikita Shakarun. Thanks to [woesss](https://github.com/woesss) for the Mascot Capsule implementation and other improvements.

> Copyright 2017-2026 Nikita Shakarun. Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
> This fork contains modifications. See the [LICENSE](LICENSE) file for the full license text.
