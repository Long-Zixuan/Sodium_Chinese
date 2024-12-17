<img src="common/src/main/resources/sodium-icon.png" width="128">

# Sodium

Sodium是Minecraft客户端的强大渲染引擎和优化模块，可提高帧率，同时修复Minecraft中的许多图形问题。

**这个模组是数千小时开发的结果，多亏了像你这样的玩家。** 如果你想对我的工作表示感谢，并在此过程中帮助支持钠的发展，那么可以考虑[buying me a coffee](https://caffeinemc.net/donate).

<a href="https://caffeinemc.net/donate"><img src="https://storage.ko-fi.com/cdn/kofi2.png?v=3" width="180"/></a>

---

### 📥 Downloads

#### Stable builds

钠的最新稳定版本可以从我们的官方网站下载[Modrinth](https://modrinth.com/mod/sodium) and
[CurseForge](https://www.curseforge.com/minecraft/mc-mods/sodium) pages.

#### Nightly builds (for developers)

我们还提供最前沿的构建（“噩梦”），这对于在最新更改之前对其进行测试非常有用
打包成一个版本。这些构建仅提供给其他具有专业技能的mod开发人员和用户，并且
不提供任何支持或保修。通常情况下，它们会出现问题，并且缺乏与其他模组的兼容性。

每个当前开发分支的最新夜间构建可以在下面下载。

- Minecraft 1.21.4 (latest): [Download nightly](https://nightly.link/CaffeineMC/sodium/workflows/build-commit/dev/sodium-artifacts-dev.zip) or [View all builds](https://github.com/CaffeineMC/sodium/actions/workflows/build-commit.yml?query=branch%3Adev)
- Minecraft 1.21.3: [Download nightly](https://nightly.link/CaffeineMC/sodium/workflows/build-commit/1.21.3%2Fstable/sodium-artifacts-1.21.3-stable.zip) or [View all builds](https://github.com/CaffeineMC/sodium/actions/workflows/build-commit.yml?query=branch%3A1.21.3%2Fstable)
- Minecraft 1.21.1: [Download nightly](https://nightly.link/CaffeineMC/sodium/workflows/build-commit/1.21.1%2Fstable/sodium-artifacts-1.21.1-stable.zip) or [View all builds](https://github.com/CaffeineMC/sodium/actions/workflows/build-commit.yml?query=branch%3A1.21.1%2Fstable)

### 🖥️ Installation

自Sodium 0.6.0发布以来，支持_Fabric_和_NeoForge_mod加载器。我们一般推荐
新用户更喜欢使用_Fabric_mod加载器，因为它（目前）更轻便、更稳定

有关下载和安装mod的更多信息，请参阅我们的 [Installation Guide](https://github.com/CaffeineMC/sodium/wiki/Installation).

### 🙇 Getting Help

如需技术支持（包括模组安装问题和游戏崩溃的帮助），请使用我们的
[official Discord server](https://caffeinemc.net/discord).

### 📬 Reporting Issues

如果您不需要技术支持，并且想报告问题（错误、崩溃等）或以其他方式请求更改
（为了模组兼容性、新功能等），我们建议您在
[project issue tracker](https://github.com/CaffeineMC/sodium/issues).

请注意，虽然问题跟踪器对功能请求开放，但开发主要集中在
提高兼容性、性能，并完成与平价所需的任何未实现的功能
香草渲染器。

### 💬 Join the Community

We have an [official Discord community](https://caffeinemc.net/discord) for all of our projects. By joining, you can:
- Get installation help and technical support for all of our mods
- Get the latest updates about development and community events
- Talk with and collaborate with the rest of our team
- ... and just hang out with the rest of our community.

## ✅ Hardware Compatibility

我们只对具有与OpenGL 4.5兼容的最新驱动程序的图形卡提供官方支持
或更新。过去12年中发布的大多数图形卡都将满足这些要求，包括以下内容：

-AMD Radeon HD 7000系列（GCN 1）或更新版本
-NVIDIA GeForce 400系列（费米）或更新版本
-Intel HD Graphics 500系列（Skylake）或更新版本

几乎所有已经与Minecraft兼容的显卡（需要OpenGL 3.3）也应该可以工作
与钠。但我们的团队无法确保兼容性或为较旧的图形卡提供支持，他们可能会
不适用于未来版本的Sodium。

#### OpenGL Compatibility Layers

不支持需要使用OpenGL转换层的设备（如GL4ES、ANGLE等），并且很可能
不使用钠。这些翻译层没有实现所需的功能，并且它们受到底层
无法解决的驱动程序错误。

## 🛠️ Building from sources

Sodium uses the [Gradle build tool](https://gradle.org/) and can be built with the `gradle build` command. The build
artifacts (production binaries and their source bundles) can be found in the `build/mods` directory.

The [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:using_wrapper) is provided for ease of use and will automatically download and install the
appropriate version of Gradle for the project build. To use the Gradle wrapper, substitute `gradle` in build commands
with `./gradlew.bat` (Windows) or `./gradlew` (macOS and Linux).

### Build Requirements

- OpenJDK 21
    - We recommend using the [Eclipse Temurin](https://adoptium.net/) distribution as it's regularly tested by our developers and known
      to be of high quality.
- Gradle 8.10.x
    - Typically, newer versions of Gradle will work without issues, but the build script is only tested against the
      version used by the [wrapper script](/gradle/wrapper/gradle-wrapper.properties).

## 📜 License

Except where otherwise stated (see [third-party license notices](thirdparty/NOTICE.txt)), the content of this repository is provided
under the [Polyform Shield 1.0.0](LICENSE.md) license by [JellySquid](https://jellysquid.me).
