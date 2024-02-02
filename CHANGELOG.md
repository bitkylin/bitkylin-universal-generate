<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Changelog

## [Unreleased]

## [1.2.1 - 2024-01-30

### Added

- feature: more option for protostuff

### Fixed

- Java doc contain 「"」, swagger annotation error

### 添加

- protostuff新增更多配置项

### 修复

- JavaDoc中包含「"」时，Swagger注解错误

## [1.1.4] - 2023-10-18

### Added

- add "Intention Action" Configuration options

### 添加

- 新增 "Intention Action" 配置选项

## [1.1.3] - 2023-10-08

### Fixed

- When the cursor is pointing to the edge of an element, it will be judged as an unselected element, which is used in Intention Actions Fix the bug of displaying "Full Document" and "Current Element" at the same time.

### Changed

- Change since/until build to 223-233.* (2022.3-2023.3.*)
- Update to IntelliJ.Platform.Plugin.Template 1.11.2
- Remove outdated dependencies

### 修复

- 光标指向元素边缘时，判定为未选定元素，用于 Intention Actions 修复同时展示「整个文档」、「当前元素」的bug

### 变更

- 支持的IDEA版本改为了 223-233.* (2022.3-2023.3.*)
- 升级到 IntelliJ.Platform.Plugin.Template 1.11.2
- 移除过期的依赖

## [1.0.7] - 2023-09-11

### Added

- add "Intention Action"、"Intention Action Preview"
- Optimize various error reporting, currently disable handling of interface classes and enum classes
- Remove redundant tri-party dependencies and dramatically reduce plugin size

### 添加

- 新增 "意图"、"意图预览"
- 优化各种报错，目前禁用对枚举类的处理
- 去除冗余的三方依赖，大幅缩减插件体积

## [0.9.12] - 2023-08-15

### Added

- Populate the JavaDoc with class field descriptions or method parameter field descriptions based on the same name field
  in the project, exact or fuzzy matching annotations and Swagger annotations.
- Add the deletion function of JavaDoc, Swagger annotations and Protostuff annotations.
- Optimize the display of the right-click menu

### 添加

- 根据项目中的同名字段，精确或模糊匹配注释及Swagger注解，将类字段的描述或方法入参字段的描述填充到JavaDoc中
- 增加JavaDoc、Swagger注解、Protostuff注解的删除功能
- 优化右键菜单的展示形式

## [0.9.2] - 2023-07-31

### Added

- If you only need to manipulate Swagger annotations and not Protostuff annotations , and vice versa, don't worry, you
  can switch via the "settings -> tools -> Bitkylin Universal Generate" page.
- Add multi-language support, now support English and Chinese, you can configure the language by "settings".
- Right-click in a different location (class, field, method, or elsewhere) to generate a specific personalized
  right-click menu.

### 添加

- 如果你只需要操作Swagger注解而不需要操作Protostuff注解，反之亦然，不用担心，你可以通过 "settings -> tools -> Bitkylin
  Universal Generate" 页面进行切换。
- 添加多语言支持，现在支持英文和中文，可以通过「settings」配置语言。
- 在不同的位置（类、字段、方法或其他地方）点击右键，生成特定的个性化右键菜单。

## [0.8.0] - 2023-07-24

### Added

- Acting on Controller class or POJO class, you can easily generate Protostuff annotation "@Tag" with one click, and can
  directly convert JavaDoc to Swagger2.0 annotations. In turn, you can easily convert Swagger2.0 annotations to JavaDoc
  with a single click.
- More convenient features will be added later, so stay tuned!

### 添加

- 作用于Controller类或POJO类，你可以很方便的一键生成Protostuff注解「@Tag」，并且可以直接将JavaDoc转换为Swagger2.0注解。反过来，你也可以方便的一键将Swagger2.0注解转换为JavaDoc。
- 后续会添加更多方便的功能，敬请期待吧！

[Unreleased]: https://github.com/bitkylin/bitkylin-universal-generate/compare/v1.1.4...main
[1.2.1]: https://github.com/bitkylin/bitkylin-universal-generate/compare/v1.1.4...v1.2.1
[1.1.4]: https://github.com/bitkylin/bitkylin-universal-generate/compare/v1.1.3...v1.1.4
[1.1.3]: https://github.com/bitkylin/bitkylin-universal-generate/compare/v1.0.7...v1.1.3
